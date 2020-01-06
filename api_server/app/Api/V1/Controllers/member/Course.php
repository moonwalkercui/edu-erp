<?php

namespace App\Api\V1\Controllers\member;

use App\Model\Course as CourseModel;
use App\Model\ApiMember\CourseSignModel;
use App\Model\MemberProduct;
use App\Service\Api\MemberService;
use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;
use Illuminate\Support\Facades\DB;
use SimpleSoftwareIO\QrCode\Facades\QrCode;

class Course extends Base
{
    public function calendarDays()
    {
        $product_ids = MemberProduct::getProductIds(MemberService::getMemberId());
        $data['day'] = CourseModel::select(DB::Raw('date as day'))
            ->whereIn('product_id', $product_ids)
            ->groupBy('day')
            ->pluck('day');
//        $data['signed'] = CourseSignModel::select(DB::Raw('DATE(sign_at) as day'))
//            ->whereIn('product_id', $product_ids)
//            ->groupBy('day')
//            ->pluck('day');
        $data['signed'] = CourseSignModel::leftJoin('courses as c', 'course_signs.course_id', '=', 'c.id')
            ->whereIn('course_signs.product_id', $product_ids)
            ->select(DB::Raw('DATE(c.date) as day'))
            ->groupBy('day')
            ->pluck('day');
        return $this->fetch($data);
//        $signs = CourseSignModel::where('member_id',MemberService::getMemberId())->get();
//        $course_ids = $signed_ids = [];
//        foreach ($signs as $k => $v) {
//            $course_ids[] = $v->course_id;
//            if($v->getOriginal('status') == 1){
//                $signed_ids[] = $v->course_id;
//            }
//        }
//        $data['day'] = CourseModel::select(array(DB::Raw('date as day')))
//            ->whereIn('id', array_unique($course_ids))
//            ->groupBy('day')
//            ->pluck('day');
//        $data['signed'] = CourseModel::select(array(DB::Raw('date as day')))
//            ->whereIn('id', array_unique($signed_ids))
//            ->groupBy('day')
//            ->pluck('day');
//        return $this->fetch($data);
    }
    // 通过日期获取 会员的课程列表
    public function getByDay(Request $request)
    {
        $this->validateParam($request->all() , [
            'day' => 'required',
        ]);
        $member_id = MemberService::getMemberId();
        $product_ids = MemberProduct::getProductIds($member_id);
        return $this->fetch(
            CourseModel::whereIn('product_id', $product_ids)
                ->with(['product'  => function($query){
                    $query->select('id','name');
                }])
                ->withCount(['signs as signed_count'  => function($query){
                    $query->where('member_id', MemberService::getMemberId());
                }])
                ->withCount('homework')
                ->whereDate('date',$request->day)
                ->get()
        );
//        CourseModel::where('signs', function ($query) {
//            $query->where('member_id', MemberService::getMemberId());
//        })->with(['product'  => function($query){
//            $query->select('id','name');
//        }])->withCount(['signs as signed_count'  => function($query){
//            $query->where('member_id', MemberService::getMemberId())->where('status',1);
//        }])->withCount('homework')->whereDate('date',$request->day)->get()
    }

    /**
     * 列表按周查
     *
     * @Get("/mcourse/listbyday")
     * @Parameters({
     *      @Parameter("day", description="日期"),
     * })
     *
     */
    public function getByWeek(Request $request)
    {
        $model = CourseModel::with('productSpec', 'division:id,name', 'userProfile:user_id,name,avatar')
//            ->whereHas('product', function ($query) {
////                $query->where('type', 0);
//            })
            ->select(['id', 'sn', 'user_id', 'division_id', 'title', 'product_id', 'product_name', 'quantity_member', 'date', 'start_at', 'end_at'])
            ->orderBy('start_at')->where('status', '1');

        // divisions
//        if($request->filled('divisions')){
//            $model->whereIn('division_id', $request->input('divisions'));
//        }

        if ($request->filled('divisions')) {
            $divisions = explode(',', $request->divisions);
            $model->whereIn('division_id', $divisions);
        }

        if ($request->filled('user')) {
            $model->where('user_id', $request->input('user'));
        }
        // products
        if ($request->filled('products')) {
            $products = explode(',', $request->products);
            $model->whereIn('product_id', $products);
        }
        // times
        if ($request->filled('times')) {
            $temp = explode(',', $request->input('times'));
            $times_where = [];
            foreach ($temp as $t) {
                $times_where[] = getStatusText($t, 'time_stage');
            }
//            var_dump($times_where);die;
            $model->where(function ($query) use ($times_where) {
                $sql = '';
                foreach ($times_where as $t) {
                    $sql .= "(time(start_at) > '$t[0]' AND time(start_at) < '$t[1]') OR";
                }
                $query->whereRaw(rtrim($sql, 'OR'));
            });
        }

        $today = Carbon::today()->format('Y-m-d');
        $after7 = Carbon::today()->addDay(6)->format('Y-m-d');
        $model->whereBetween('date', [$today, $after7]);

        $res[str_replace("-", "", $today)] = [];
        for ($i = 1; $i < 6; $i++) {
            $res[str_replace("-", "", Carbon::today()->addDay($i)->format('Y-m-d'))] = [];
        }

        $data = $model->orderBy('date')->get()->toArray();
        foreach ($data as $k => $v) {
            if (isset($v['division'])) {
                $v['start_at'] = substr($v['start_at'], 0, 5);
                $v['end_at'] = substr($v['end_at'], 0, 5);
                $v['is_expired'] = time() > strtotime($v['date'] . ' ' . $v['start_at']) ? 1 : 0;
                $res[str_replace("-", "", $v['date'])][$v['division']['id'] . '_' . $v['division']['name']][] = $v;
            }
        }
        $res2 = [];

        foreach ($res as $date => $d) {
            $temp2 = [];
            foreach ($d as $k => $item) {
                $temp = explode('_', $k);
                $temp2[] = [
                    'id' => $temp[0],
                    'name' => $temp[1],
                    'list' => $item,
                ];
            }
            $res2[] = [
                'date' => $date,
                'list' => $temp2
            ];
        }
        return $this->fetch($res2);

//        $keyed = $model->get()->groupBy(function ($item, $key) {
//            return date('Y-m-d', strtotime($item->start_at));
//        });
//        $keyed->all();
//        return $this->fetch($keyed);
    }
    public function getAll(Request $request)
    {
        $where = [];
        if($request->filled('product'))
        array_push($where, ['product_id' , '=' , intval($request->product)]) ;

        $model = CourseModel::with('user','division')->where($where)->orderBy('date','desc')->orderBy('start_at');

        if($request->filled('page')){
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        }else{
            $data = $model->get();
            foreach ($data as $k => &$v) {
                $v['is_expired'] = time() > strtotime($v['date'] . ' ' . $v['start_at']) ? 1 : 0;
            }
        }
        return $this->fetch($data);
    }

    /**
     * course detail
     *
     * @Get("/mcourse/days")
     */
    public function find(Request $request)
    {
        $data = CourseModel::with('user', 'product','product.images', 'productSpecs', 'division')->find($request->id);
        return $this->fetch($data);
    }
    // 获取课程二维码，返回base64图片数据
    public function qrcode(Request $request)
    {
        $id = $request->id;

        $couse_sign = CourseSignModel::find($id);
        $course = CourseModel::with('user')->where('id', $couse_sign->course_id)->select('id','user_id','username','product_name','date','start_at')->first();
        $data['info'] = $course;
        $size = request('size', 400);
        $url = makeSn(). '_' . $id . '_' . $course->user->username;
        $gen = QrCode::format('png')->size($size)->generate($url);
        $data['image_data'] = 'data:image/png;base64,' . base64_encode($gen);
//        echo '<img src="'.$data.'"/>';
        return $this->fetch($data);
    }

//    /**
//     * 获取课次日历里的日期
//     *
//     * @Get("/mcourse/days")
//     */
//    public function getCalendarDate()
//    {
//        $classes = MemberService::getClasses();
//        if ($classes->isEmpty()) {
//            $data = null;
//        } else {
//            $data = CourseModel::select([DB::Raw('DATE(start_at) day')])
//                ->whereIn('class_id',$classes->pluck('id'))
//                ->groupBy('day')
//                ->pluck('day');
//        }
//        return $this->fetch($data);
//    }
//    /**
//     * 课时列表按天查
//     *
//     * @Get("/mcourse/listbyday")
//     * @Parameters({
//     *      @Parameter("day", description="日期"),
//     * })
//     *
//     */
//    public function getByDay(Request $request)
//    {
//        return $this->getData($request, 'by_day');
//    }
//    /**
//     * 我的课时列表
//     *
//     * @Get("/mcourse/list")
//     * @Parameters({
//     *      @Parameter("page", description="页码"),
//     * })
//     */
//    public function getAll(Request $request)
//    {
//        return $this->getData($request);
//    }
//    private function getData(Request $request , $type = 'all')
//    {
//        $model = CourseModel::with('user')->orderBy('start_at');
//        if ( $type == 'by_day') {
//            $this->validateParam($request->all() , ['day' => 'required' ]);
//            $model->whereDate('start_at',$request->day);
//        }
//        if($request->filled('page')){
//            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        }else{
//            $data = $model->get();
//        }
//
//        return $this->fetchResource(CourseResource::collection($data));
//    }
//    /**
//     * 课时列表按产品
//     *
//     * @Get("/mcourse/listbyproduct")
//     * @Parameters({
//     *      @Parameter("pid", description="课程id"),
//     * })
//     */
//    public function getByProduct(Request $request)
//    {
//        $this->validateParam($request->all() , ['pid' => 'required' ]);
//        $model = CourseModel::where('product_id',$request->pid)->orderBy('start_at','desc');
//        if($request->filled('page')){
//            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        }else{
//            $data = $model->get();
//        }
//        return $this->fetchResource(CourseResource::collection($data));
//    }
//    /**
//     * 我的签到记录
//     *
//     * @Get("/mcourse/signs")
//     * @Parameters({
//     *      @Parameter("page", description="页码", default=1),
//     * })
//     */
//    public function signs(Request $request)
//    {
//        $where = [];
//        $model = CourseSignModel::ofMember()->where($where)->with('course')->orderBy('id','desc');
//        if($request->filled('page')){
//            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        }else{
//            $res = $model->get();
//        }
//        return $this->fetchResource(CourseSignResource::collection($res));
//    }
//    /**
//     * 待签到列表
//     *
//     * @Post("/mcourse/signing")
//     */
//    public function signing()
//    {
//        // 扫码签到：查询符合条件的待签到列表，返回给前端。
//        return $this->fetchResource(CourseSignResource::collection(
//            CourseSignModel::getSigning()
//        ));
//    }
    /**
     * 签到逻辑
     *
     * @Post("/mcourse/sign")
     * @Parameters({
     *      @Parameter("ids", description="签到的id"),
     * })
     */
    public function sign(Request $request)
    {
        // 扫码签到：查询符合条件的待签到列表，返回给前端，确认后，即可完成签到。
        $this->validateParam($request->all() , [ 'sn' => 'required' ]);
        if (CourseSignModel::sign($request->sn, MemberService::getMemberId()))
            return $this->success('已签到');
    }

}