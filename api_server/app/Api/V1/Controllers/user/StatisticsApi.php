<?php
namespace App\Api\V1\Controllers\user;
use App\Model\MemberProfile as MemberProfileModel;
use App\Model\Classes as ClassesModel;
use App\Model\Course as CourseModel;
use App\Model\CourseSign as CourseSignModel;
use App\Model\ApiUser\MessageModel;
use App\Model\Order as OrderModel;
use App\Model\Proceeds as ProceedsModel;
use App\Model\Product as ProductModel;
use App\Model\UserProfile as UserProfileModel;
use App\Model\OrderItem;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Api\V1\Controllers\Base;

class StatisticsApi extends Base
{
    // 汇总：员工数 学员数 课程数 报单数 班级数 课时数
    public function total()
    {
        $users = UserProfileModel::count();
        $members = MemberProfileModel::count();
        $courses = CourseModel::count();
        $orders = OrderModel::count();
        $classes = ClassesModel::count();
        $products = ProductModel::count();
        $msgUnread = MessageModel::getMyUnreadNumber();
        $todayCourses = CourseModel::getTodayNumber();
//        $homework =
        return $this->fetch(compact('users','members','courses','orders','classes','products','msgUnread','todayCourses'));
    }
    // 本月订单统计 日报单量曲线（预约曲线 成交曲线）
    // @param $month 格式：2018-4 或 2018-4-1 默认不传为当前月
    public function orderDay(Request $request)
    {
//        if($request->filled('date')) $time = Carbon::parse($request->input('date'))->addHours(Carbon::createFromTimestamp(0)->offsetHours);
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        // 提交金额
        $per_order = OrderModel::selectRaw("date(created_at) as 'date',sum(total_price) as 'money'")
            ->whereBetween('created_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            -> groupBy('date')-> orderBy('id','asc')-> pluck('money','date') ->toArray();
        // 成交金额
        $done_order = OrderModel::selectRaw("date(done_at) as 'date',sum(total_price) as 'money'")
            ->whereBetween('done_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            -> groupBy('date')-> orderBy('id','asc')-> pluck('money','date') ->toArray();
        $title = '日订单金额统计';
        $columns = ['日期','提交金额','支付金额'];
        foreach ($this->getThisMonthDays($month) as $d){
            $rows[] = [
                $columns[0] => str_replace($y.'-','',$d),
                $columns[1] => isset($per_order[$d]) ? $per_order[$d] : 0,
                $columns[2] => isset($done_order[$d]) ? $done_order[$d] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
    // 日订单数统计
    public function orderDayCount(Request $request)
    {
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        // 提交金额
        $per_order = OrderModel::selectRaw("date(created_at) as 'date',count(id) as 'count'")
            ->whereBetween('created_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            -> groupBy('date')-> orderBy('id','asc')-> pluck('count','date') ->toArray();
        // 支付金额
        $done_order = OrderModel::selectRaw("date(done_at) as 'date',count(id) as 'count'")
            ->whereBetween('done_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            -> groupBy('date')-> orderBy('id','asc')-> pluck('count','date') ->toArray();
        $title = '日订单数统计';
        $columns = ['日期','提交单数','成交单数'];
        foreach ($this->getThisMonthDays($month) as $d){
            $rows[] = [
                $columns[0] => str_replace($y.'-','',$d),
                $columns[1] => isset($per_order[$d]) ? $per_order[$d] : 0,
                $columns[2] => isset($done_order[$d]) ? $done_order[$d] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
    // 12个月内的订单量统计 月报单量曲线（预约曲线 成交曲线）
    public function orderMonth(Request $request)
    {
        if($request->filled('date')) $year = $request->input('date');
        else $year = Carbon::now()->year;

        $per_order = OrderModel::selectRaw("year(created_at) as y , month(created_at) as m,sum(total_price) as 'money'")
            -> whereYear('created_at',$year)
            -> groupBy('y','m')-> orderBy('id','asc')-> pluck('money',"m") ->toArray();
        $done_order = OrderModel::selectRaw("year(done_at) as y , month(done_at) as m,sum(total_price) as 'money'")
            -> whereYear('done_at',$year)
            -> groupBy('y','m')-> orderBy('id','asc')-> pluck('money',"m") ->toArray();
        $title = '月订单统计';
        $columns = ['月份','提交金额','支付金额'];
        $rows = [];
        foreach ($this->getThisYearMonths() as $m){
            $rows[] = [
                $columns[0] => $m . '月',
                $columns[1] => isset($per_order[$m]) ? $per_order[$m] : 0,
                $columns[2] => isset($done_order[$m]) ? $done_order[$m] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
    // 签到 //日签到数曲线（签到数,未签到数 双线）
    public function signDay(Request $request)
    {
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;

        $sign_count = CourseSignModel::join('courses', 'course_signs.course_id', '=', 'courses.id')
            ->selectRaw("courses.date , sum(case when course_signs.sign_at is not null then 1 else 0 end) as signed ,count(course_signs.id) as count ")
            ->groupBy('courses.date')
            ->get()->toArray();

        $total = [];
        foreach ($sign_count as $s){
            $total[$s['date']] = $s;
        }
        $title = '日签到统计';
        $columns = ['日期','签到数','应签到数'];
        foreach ($this->getThisMonthDays($month) as $d){
            $rows[] = [
                $columns[0] => str_replace($y.'-','',$d),
                $columns[1] => isset($total[$d]) ? $total[$d]['signed'] : 0,
                $columns[2] => isset($total[$d]) ? $total[$d]['count'] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
     // 日课时曲线
    public function courseDay(Request $request)
    {
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        $course_count = CourseModel::selectRaw("date, count(id) as count ")
            ->whereBetween('date',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            ->groupBy('date')-> orderBy('date','asc')-> pluck('count',"date")->toArray();
        $title = '日课时统计';
        $columns = ['日期','课时数'];
        foreach ($this->getThisMonthDays($month) as $d){
            $rows[] = [
                $columns[0] => str_replace($y.'-','',$d),
                $columns[1] => isset($course_count[$d]) ? $course_count[$d] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
    // 月课时数柱状图
    public function courseMonth(Request $request)
    {
        if($request->filled('date')) $year = $request->input('date');
        else $year = Carbon::now()->year;
        $course_count = CourseModel::selectRaw("year(date) as y , month(date) as m , count(id) as count ")
            -> whereYear('created_at',$year)
            -> groupBy('y','m')-> orderBy('id','asc')-> pluck('count',"m") ->toArray();
        $title = '月课时统计';
        $columns = ['月份','课时数'];
        $rows = [];
        foreach ($this->getThisYearMonths() as $m){
            $rows[] = [
                $columns[0] => $m . '月',
                $columns[1] => isset($course_count[$m]) ? $course_count[$m] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
    // 员工课程数排名
    public function userCourses(Request $request)
    {
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        $title = '员工课时统计';
        $columns = ['姓名','数量'];
        $rows = CourseModel::select("username",
                DB::raw("user_name as {$columns[0]}"),
                DB::raw("COUNT(id) as {$columns[1]}")
            )
            ->whereBetween('date',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            ->groupBy('username',$columns[0])-> orderBy($columns[1],'desc')-> get() ->toArray();
        return $this->fetch(compact('rows','columns','title'));
    }
    // 员工成交量
    public function userOrders(Request $request)
    {
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        $columns = ['姓名','数量','金额'];
        $rows = OrderModel::select("username",
                DB::raw("user_name as {$columns[0]}") ,
                DB::raw("COUNT(id) as {$columns[1]}") ,
                DB::raw("SUM(total_price) as {$columns[2]}")
            )
            ->whereBetween('done_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            ->groupBy('username',$columns[0])->orderBy($columns[1],'desc')-> get() ->toArray();
        return $rows;
    }
    public function userOrdersMoney(Request $request)
    {
        $list = $this->userOrders($request);
        $rows = [];
        $columns = ['姓名','数量'];
        foreach ($list as $v){
            $rows[] = [
                '姓名' => $v['姓名'],
                '数量' => $v['数量'],
            ];
        }
        $title = '员工成单量统计';
        return $this->fetch(compact('rows','columns','title'));
    }
    public function userOrdersCount(Request $request)
    {
        $list = $this->userOrders($request);
        $rows = [];
        $columns = ['姓名','金额'];
        foreach ($list as $v){
            $rows[] = [
                '姓名' => $v['姓名'],
                '金额' => $v['金额'],
            ];
        }
        $title = '员工成单金额统计';
        return $this->fetch(compact('rows','columns','title'));
    }
    // 产品成交量排名
    public function productOrders(Request $request)
    {
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        $title = '产品订单量统计';
        $columns = ['产品名','数量'];
        $rows = OrderItem::select(
                DB::raw("item_name as {$columns[0]}"),
                DB::raw("SUM(num) as {$columns[1]}")
            )
            ->whereBetween('created_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            ->groupBy($columns[0])->orderBy($columns[1],'desc')-> get() ->toArray();
        return $this->fetch(compact('rows','columns','title'));
    }
    // 学员签到率
//    public function memberSigns(Request $request)
//    {
//        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
//        else $time = Carbon::now();
//        $y = $request->input('y',$time->year);
//        $m = $request->input('m',$time->month);
//        $month = $y.'-'.$m;
//        $title = '学员签到统计';
//        $columns = ['姓名','签到率','签到数'];
//        $rows = CourseSignModel::select(
//                'member_mobile',DB::raw("member_name as {$columns[0]}"),
//                DB::raw("sum(case when sign_at is not null then 1 else 0 end) as signed"),
//                DB::raw("COUNT('course_start_at') as total"),
//                DB::raw("sum(case when sign_at is not null then 1 else 0 end) / COUNT('course_start_at') as {$columns[1]}"),
//                DB::raw("sum(case when sign_at is not null then 1 else 0 end) as {$columns[2]}")
//            )
//            ->whereBetween('course_start_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
//            ->groupBy('member_mobile',$columns[0])-> orderBy($columns[1],'desc')-> get() ->toArray();
//        return $this->fetch(compact('rows','columns','title'));
//    }

    private function getThisMonthDays($month = null)
    {
        if($month == null) $time = Carbon::now();
        else $time = Carbon::parse($month);
        $days = $time->daysInMonth;
        $res = [];
        for ($i = 1; $i <= $days ; $i++){
            $res[] = $time->year . '-' . str_pad($time->month,2,"0",STR_PAD_LEFT) . '-' .str_pad($i,2,"0",STR_PAD_LEFT);
        }
        return $res;
    }
    private function getThisYearMonths()
    {
        $months = 12;
        for ($i = 1; $i <= $months ; $i++){
            $res[] = $i;
        }
        return $res;
    }
    public function proceedsDay(Request $request)
    {
//        if($request->filled('date')) $time = Carbon::parse($request->input('date'))->addHours(Carbon::createFromTimestamp(0)->offsetHours);
        if($request->filled('date')) $time = Carbon::parse($request->input('date'));
        else $time = Carbon::now();
        $y = $request->input('y',$time->year);
        $m = $request->input('m',$time->month);
        $month = $y.'-'.$m;
        // 提交金额
        $per_order = ProceedsModel::selectRaw("date(paid_at) as 'date',sum(money_receivable) as 'money'")
            ->whereBetween('paid_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            ->groupBy('date')->orderBy('id','asc')-> pluck('money','date') ->toArray();
        // 成交金额
        $done_order = ProceedsModel::selectRaw("date(confirmed_at) as 'date',sum(money_receivable) as 'money'")
            ->whereBetween('confirmed_at',[Carbon::parse($month),Carbon::parse($month)->addMonth()])
            ->groupBy('date')-> orderBy('id','asc')-> pluck('money','date') ->toArray();
        $title = '日收款金额曲线';
        $columns = ['日期','收款金额','认款金额'];
        foreach ($this->getThisMonthDays($month) as $d){
            $rows[] = [
                $columns[0] => str_replace($y.'-','',$d),
                $columns[1] => isset($per_order[$d]) ? $per_order[$d] : 0,
                $columns[2] => isset($done_order[$d]) ? $done_order[$d] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
    public function proceedsMonth(Request $request)
    {
        if($request->filled('date')) $year = $request->input('date');
        else $year = Carbon::now()->year;

        $per_order = ProceedsModel::selectRaw("year(paid_at) as y , month(paid_at) as m,sum(money_receivable) as 'money'")
            -> whereYear('paid_at',$year)
            -> groupBy('y','m')-> orderBy('id','asc')-> pluck('money',"m") ->toArray();
        $done_order = ProceedsModel::selectRaw("year(confirmed_at) as y , month(confirmed_at) as m,sum(money_receivable) as 'money'")
            -> whereYear('confirmed_at',$year)
            -> groupBy('y','m')-> orderBy('id','asc')-> pluck('money',"m") ->toArray();
        $title = '月收款';
        $columns = ['月份','收款金额','认款金额'];
        $rows = [];
        foreach ($this->getThisYearMonths() as $m){
            $rows[] = [
                $columns[0] => $m . '月',
                $columns[1] => isset($per_order[$m]) ? $per_order[$m] : 0,
                $columns[2] => isset($done_order[$m]) ? $done_order[$m] : 0,
            ];
        }
        return $this->fetch(compact('rows','columns','title'));
    }
}