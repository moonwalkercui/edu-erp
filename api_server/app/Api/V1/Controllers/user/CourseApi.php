<?php

namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\CourseLogic;
use App\Logic\ApiUser\RbacLogic;
use App\Model\CourseHomework as CourseHomeworkModel;
use App\Model\Course as CourseModel;
use App\Model\CourseWeekSch;
use App\Model\Resources\CourseResource;
use App\Model\User;
use App\Service\Api\UserService;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Api\V1\Controllers\Base;

class CourseApi extends Base
{
    // 获取所有
    public function getAll(Request $request)
    {
        return $this->getData($request);
    }

    // 获取我的
    public function getMine(Request $request)
    {
        return $this->getData($request, true);
    }

    public function getData(Request $request, $is_mine = false)
    {

        if ($request->filled('export') && $request->export == 'excel') {
            $model = CourseModel::join('products', 'courses.product_id', '=', 'products.id')
                ->join('divisions', 'courses.division_id', '=', 'divisions.id')
                ->select(
                    'courses.sn', 'courses.date', 'courses.start_at', 'courses.end_at', 'courses.user_name',
                    'courses.title', 'courses.status', 'courses.updated_at',
                    'products.name as product_name',
                    'divisions.name as division_name'
                );
            return $this->export(
                $model->get()->toArray(),
                [
                    'sn' => '编号',
                    'product_name' => '课程名称',
                    'division_name' => '门店',
                    'date' => '日期',
                    'start_at' => '开始时间',
                    'end_at' => '结束时间',
                    'user_name' => '老师姓名',
                    'title' => '课题',
                    'status' => '状态',
                    'updated_at' => '更新时间',
                ], '课时列表');
        }

        if ($is_mine) $where[] = ['username', '=', UserService::getUserName()];
        else {
            $where = [];
            if ($request->filled('search_user'))
                array_push($where, ['username', '=', cleanXss($request->search_user)]);
        }
        if ($request->filled('search_sn'))
            array_push($where, ['sn', '=', cleanXss(strtoupper($request->search_sn))]);

        if ($request->filled('search_product'))
            array_push($where, ['product_id', '=', intval($request->search_product)]);

        if ($request->filled('search_status') && $request->search_status != 'all')
            array_push($where, ['status', '=', intval($request->search_status)]);

        if ($request->filled('search_division'))
            array_push($where, ['division_id', '=', intval($request->search_division)]);

        $model = CourseModel::where($where)->with(['product', 'division'])->withCount([
            'deal',
            'signs',
            'homework',
        ]);

        if ($request->filled('sort_type'))
            $model = $model->orderBy('date', $request->sort_type);
        else
            $model = $model->orderBy('date', 'desc');

        if ($request->filled('search_days')) {
            $days = $request->input('search_days');
            $model->whereBetween('date', [Carbon::parse($days[0])->format('Y-m-d 00:00'), Carbon::parse($days[1])->format('Y-m-d 23:59')]);
        }
        if ($request->filled('search_ids'))
            $model->whereIn('id', explode(',', $request->input('search_ids')));

        if ($request->filled('search_division') == false) {
            $internal_ids = RbacLogic::getViewDivisionIds(UserService::getUser());
            if ($internal_ids) $model->whereIn('division_id', $internal_ids);
        }

        if ($request->filled('page')) {
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        } else {
            $res = $model->get();
        }
//        return $this->fetch($res);
        return $this->fetchResource(CourseResource::collection($res));
    }
    //    获取周排课列表
    public function getWeekSchedule(Request $request, $is_mine = false)
    {
        $where = [];
        if ($request->filled('search_division') && $division_id = intval($request->search_division))
            array_push($where, ['division_id', '=', $division_id]);

        if ($request->filled('search_user'))
            array_push($where, ['user_id', '=', User::getUidByUsername($request->search_user)]);

        if ($request->filled('search_product'))
            array_push($where, ['product_id', '=', intval($request->search_product)]);

        $data = CourseWeekSch::where($where)->with('user','product','division')->orderBy('start_time')->get();
        $res = array_pad([],7,[]);
        foreach ($data as $v) {
            $v->start_time = substr($v->start_time,0,-3);
            $v->end_time = substr($v->end_time,0,-3);
            $res[intval($v->week_day)][] = $v;
        }
        return $this->fetch($res);
    }
    //    生成课时记录
    public function generateCourseBySch(Request $request)
    {
        $this->validateParam($request->all(), [
            'type' => 'required',
        ]);
        return $this->success('建设中');
        if (CourseLogic::generate($request->type))
            return $this->success('已生成');
        else return $this->error('生成失败');
    }
    public function delWeekSch(Request $request)
    {
        $this->validateParam($request->all(), ['id' => 'required'], ['id.required' => '课时未选']);
        if (CourseWeekSch::destroy($request->id))
            return $this->success('已删除');
        else return $this->error('删除失败');
    }

    public function getAllByDay(Request $request)
    {
        $this->validateParam($request->all(), [
            'day' => 'required',
        ]);
        return $this->fetchResource(CourseResource::collection(CourseModel::whereDate('date', $request->day)->get()));
    }

    public function getMineByDay(Request $request)
    {
        $this->validateParam($request->all(), [
            'day' => 'required',
        ]);
        return $this->fetchResource(CourseResource::collection(
            CourseModel::whereDate('date', $request->day)->where('username', UserService::getUserName())->get()
        ));
    }

    /**
     * 获取课次日历里的日期
     */
    public function getMyCalendarDate()
    {
        $data = CourseModel::select(array(DB::Raw('date as day')))
            ->where('username', UserService::getUserName())
            ->groupBy('day')
            ->pluck('day');
        return $this->fetch($data);
    }

    public function create(Request $request)
    {
        $this->validateParam($request->all(), [
            'division' => 'required',
            'product' => 'required',
            'data.*.date' => 'required',
            'data.*.start_at' => 'required',
            'data.*.end_at' => 'required',
            'data.*.user' => 'required',
//            'data.*.class' => 'required',
//            'data.*.room' => 'required',
        ], [
            'division.required' => '未设置门店',
            'product.required' => '未选择课程',
            'data.*.date.required' => '有日期未输入',
            'data.*.start_at.required' => '有开始时间未输入',
            'data.*.end_at.required' => '有结束日期未输入',
            'data.*.user.required' => '有教师未选择',
//            'data.*.class.required' => '有班级未选择',
//            'data.*.room.required' => '有教室未选择',
        ]);
        if (CourseLogic::save($request))
            return $this->success('已创建');
        else return $this->error('创建失败');
    }

    // 创建周排课计划
    public function createByWeek(Request $request)
    {
        $this->validateParam($data = $request->all(), [
            'user' => 'required',
            'product' => 'required',
            'startTime' => 'required',
            'endTime' => 'required',
            'division' => 'required',
            'week' => 'required',
        ], [
            'user.required' => '未设置老师',
            'product.required' => '未选择课程',
            'division.required' => '未设置门店',
            'startTime.required' => '未设置开始时间',
            'endTime.required' => '未设置结束时间',
            'week.required' => '未设置星期',
        ]);
        if(count($data['week']) == 0) {
            return $this->error('星期设置有误');
        }
        if (CourseLogic::saveByWeek($data))
            return $this->success('已创建');
        else return $this->error('创建失败');
    }

    public function stop(Request $request)
    {
        $this->validateParam($request->all(), ['ids' => 'required'], ['ids.required' => '课时未选']);
        if (CourseLogic::stop($request))
            return $this->success('已停课');
    }

    public function open(Request $request)
    {
        $this->validateParam($request->all(), ['ids' => 'required'], ['ids.required' => '课时未选']);
        if (CourseLogic::open($request))
            return $this->success('已开课');
    }

    public function getOne(Request $request)
    {
        $this->validateParam($input = $request->all(), [
            'id' => 'required',
        ]);
        $data = CourseModel::where('id', $input['id'])->with(['product', 'division'])->withCount([
            'signs',
            'signs as signed'
        ])->first();
        return $this->fetchResource(new CourseResource($data));
    }

    public function edit(Request $request)
    {
        $this->validateParam($request->all(), [
            'id' => 'required',
//            'class' => 'required',
            'product' => 'required',
//            'room' => 'required',
            'user' => 'required',
            'day' => 'required',
            'start' => 'required',
            'end' => 'required',
            'status' => 'required',
        ], [
            'id.required' => '参数错误',
//            'class.required'=> '班级未选' ,
//            'room.required'=> '教室未选' ,
            'product.required' => '课程未选',
            'user.required' => '教师未选',
            'day.required' => '日期未选',
            'start.required' => '开始时间未选',
            'end.required' => '结束未选',
            'status.required' => '状态未选',
        ]);
        if (CourseLogic::update($request, ['id' => $request->input('id')]))
            return $this->success('已更新');
    }

    public function editTitle(Request $request)
    {
        $this->validateParam($request->all(), ['id' => 'required', 'title' => 'required'], ['title.required' => '请输入标题']);
        if (CourseLogic::editTitle($request->input('id'), $request->input('title')))
            return $this->success('已更新');
    }

    public function delete(Request $request)
    {
        $this->validateParam($request->all(), ['id' => 'required']);
        if (CourseLogic::delete($request->input('id')))
            return $this->success('已删除');
    }
    // 刷新学员 用于班级内学员变动后刷新学员签到记录
//    public function refreshMember(Request $request)
//    {
//        $this->validateParam($request->all() , [ 'id' => 'required' ]);
//        if( CourseSignLogic::refreshCourseSigns( intval($request->input('id')) ) )
//            return $this->success('已刷新');
//    }
    // 刷新冲突
    public function refreshClash()
    {
        if (CourseLogic::refreshClash())
            return $this->success('已刷新');
    }

    /**
     * 查看作业
     * @Get("/course/homework")
     */
    public function homework(Request $request)
    {
        $this->validateParam($request->all(), ['course_id' => 'required']);
        $homework = CourseHomeworkModel::where('course_id', $request->course_id)->first();
        if ($homework) $data = [
            'id' => $homework->id,
            'caption' => $homework->caption,
            'remark' => $homework->remark,
            'images' => [$homework->image1, $homework->image2, $homework->image3]
        ];
        else $data = [
            'id' => '',
            'caption' => '',
            'remark' => '',
            'images' => []
        ];
        return $this->fetch($data);
    }

    /**
     * 修改作业
     * @Post("/course/homeworkcreate")
     */
    public function homeworkCreate(Request $request)
    {
        $this->validateParam($request->all(), [
            'course_id' => 'required',
            'caption' => 'required',
        ]);
        if (CourseHomeworkModel::saveHomework(
            UserService::getUserName(),
            intval($request->course_id),
            cleanXss($request->caption),
            $request->input('images', null),
            cleanXss($request->input('remark', null))
        ))
            return $this->success('已保存');
        else
            return $this->error('保存失败');
    }
}