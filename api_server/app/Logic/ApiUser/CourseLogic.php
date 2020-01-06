<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Course as CourseModel;
use App\Model\CourseSign as CourseSignModel;
use App\Model\CourseWeekSch;
use App\Model\UserProfile as UserProfileModel;
use App\Service\Api\LogService;
use App\Service\Api\UserService;
use Carbon\Carbon;

class CourseLogic
{
    // 只新增

    public static function save($request)
    {

        $input = cleanXss($request->input('data'));
        if( count($input) > 50) throw new ApiException('单次发布数量超限');
        $internal_id = intval($request->division);
        $product_id = intval($request->product);
        $data = $usernames = [];
        foreach ($input as $v) {
            $usernames[] = $v['user'];
        }
        $users = UserProfileModel::whereIn('username', array_unique($usernames))->get();
        $user_ids = $user_names = [];
        foreach ($users as $user) {
            $user_ids[$user->username] = $user->user_id;
            $user_names[$user->username] = $user->name;
        }

//        $product = ProductModel::where('id', $product_id)->first();
        foreach ($input as $v) {
            $date = Carbon::parse($v['date'])->format('Y-m-d');
            $data[] = [
                'sn' => makeSn(),
                'division_id' => $internal_id,
                'editor_id' => 0,
                'product_id' => $product_id,
//                'product_name' => $product->name,
//                'product_type' => $product->type,
                'user_id' => $user_ids[$v['user']],
                'username' => $v['user'],
                'user_name' => $user_names[$v['user']],
                'date' => $date,
                'start_at' => $v['start_at'],
                'end_at' => $v['end_at'],
                'created_at' => Carbon::now(),
                'updated_at' => Carbon::now(),
            ];
        }
        if ($res = CourseModel::insert($data)){
            LogService::userLog('课时管理','创建课时'.$res.'个');
            $sns = [];
            foreach ($data as $d){
                $sns[] = $d['sn'];
                // 创建签到记录 不要了
//                CourseSignLogic::addByOrders($d['product_spec_id'],$d['sn']);
            }
            self::markClashAll($sns);
            return true;
        }
        else
            throw new ApiException('创建失败');
    }
    public static function saveByWeek($data)
    {
        $insert_data = [];
        foreach ($data['week'] as $day) {
            $user_id = \App\Model\User::getUidByUsername($data['user']);
            $insert_data[] = [
                'user_id' => $user_id,
                'product_id' => $data['product'],
                'start_time' => $data['startTime'],
                'end_time' => $data['endTime'],
                'division_id' => $data['division'],
                'week_day' => $day,
                'editor_id' => UserService::getUserId(),
                'add_time' => now(),
            ];
            if(self::checkClashByWeek($user_id, $day, $data['startTime'], $data['endTime']) !== false){
                throw new ApiException('课时有冲突请重新编辑');
            }
        }
        if (CourseWeekSch::insert($insert_data)){
            LogService::userLog('课时管理','按周编排课时');
            return true;
        } else return false;
    }
    // 按周排课生成课时记录
    public static function generate($type)
    {
        switch ($type) {
            case "next_month":  break;
            case "next_week":  break;
            default: throw new ApiException("Type参数错误");
        }
        var_dump($type);
        return null;
    }

    public static function update($request , $condition = [])
    {
        $course = CourseModel::find($request->input('id'));
        $course->username = cleanXss($request->input('user'));
        $course->title = cleanXss($request->input('title'));
        $course->date = cleanXss($request->day);
        $course->start_at = cleanXss($request->start);
        $course->end_at = cleanXss($request->end);
        $course->status = getStatusValue($request->input('status'),'course');
        $course->user_name = UserProfileModel::where('username',$course->username)->value('name');
        $course->product_id = intval($request->product);
        // 自己检查是否冲突
        $course->clash_ids = self::checkClash($course->id , $course->date, $course->start_at, $course->end_at, $course->username );
        if ($res = $course->save()){
            LogService::userLog('课时管理','修改课时');
            // 检查其他有关冲突
            $clashes = CourseModel::whereRaw('FIND_IN_SET(?,clash_ids)', [$course->id])->get();
            if($clashes->isEmpty() == false)
                $clashes->each(function ($course) {
                    self::updateClash($course);
                });

            return true;
        }
        else throw new ApiException('更新失败');
    }
    public static function editTitle($id,$title)
    {
        $res = CourseModel::where('id',$id)->update([
            'title' => $title
        ]);
        if ($res){
            LogService::userLog('课时管理','修改备注');
            return true;
        }else
            throw new ApiException('更新失败');
    }
    public static function delete($id)
    {
        return BaseLogic::delete('课时管理', '删除课时' ,'\App\Model\Course' ,['id' => $id ] ,function($id){
            CourseSignModel::where('course_id',$id)->delete(); //->where('status',0)
        });
    }
    // 停课
    public static function stop($request)
    {
        $ids = $request->input('ids');
        if(is_array($ids)){
            $res = CourseModel::whereIn('id',$ids)->update(['status' => 0]);
        }else{
            $res = CourseModel::where('id',$ids)->update(['status' => 0]);
        }
        if ($res){
            LogService::userLog('课时管理','设置停课');
            return true;
        }
        else throw new ApiException('停课操作失败');
    }
    // 开课
    public static function open($request)
    {
        $ids = $request->input('ids');
        if(is_array($ids)){
            $res = CourseModel::whereIn('id',$ids)->update(['status' => 1]);
        }else{
            $res = CourseModel::where('id',$ids)->update(['status' => 1]);
        }
        if ($res){
            LogService::userLog('课时管理','设置开课');
            return true;
        }
        else throw new ApiException('开课操作失败');
    }

    // 传入课时编号数组查询冲突
    public static function markClashAll($sns)
    {
        foreach ($sns as $s){
            $course = CourseModel::where('sn',$s)->first();
            self::updateClash($course);
        }
    }
    /* 检查课时冲突
        @param $check_id 要检查的id
        @param $start_time 要检查的开始时间
        @param $end_time 结束时间
        @return null(无冲突) 或 冲突的ids字符串
    */
//    public static function checkClash($check_id , $start_time, $end_time ,$username , $class_id = null ,$room_id = null)
    public static function checkClash($check_id , $date, $start_time, $end_time ,$username )
    {

        $ids = CourseModel::where('id', '<>' , $check_id)->where('date', $date)
            ->where(function ($query) use ($start_time,$end_time) {
                $query->where([
                    ['start_at', '<', $start_time] , ['end_at', '>', $start_time]
                ])->orWhere([
                    ['start_at', '<', $end_time] , ['end_at', '>', $end_time]
                ]);
//            })->where(function($query) use ($username ,$class_id , $room_id) {
            })->where(function($query) use ($username ) {
                $query->where('username', '=' , $username);         // 检查起始时间段内的教师冲突
//                if($class_id)
//                $query->orWhere('class_id', '=' , $class_id);         // 检查起始时间段内的班级冲突
//                if($room_id)
//                $query->orWhere('room_id', '=' , $room_id);        // 检查起始时间段内的教室冲突
            })->pluck('id');

        if($ids->isEmpty()){
            return null;
        }else{
            return implode( ',' , $ids->toArray() );
        }
    }
    // 检查课时周编排冲突 false 为无冲突
    public static function checkClashByWeek($user_id, $week_day, $start_time, $end_time )
    {
        $ids = CourseWeekSch::where('user_id', $user_id)->where('week_day', $week_day)
            ->where(function ($query) use ($start_time, $end_time) {
                $query->where([
                    ['start_time', '<=', $start_time] , ['end_time', '>=', $start_time]
                ])->orWhere([
                    ['start_time', '<=', $end_time] , ['end_time', '>=', $end_time]
                ]);
            })->pluck('id');

        if($ids->isEmpty()){
            return false;
        }else{
            return implode( ',' , $ids->toArray() );
        }
    }

    // 刷新冲突
    public static function refreshClash()
    {
        CourseModel::whereNotNull('clash_ids')->get()->each(function ($course) {
            self::updateClash($course);
        });
    }
    //    更新冲突记录
    private static function updateClash(CourseModel $course)
    {
        $res = self::checkClash($course->id ,$course->date, $course->start_at, $course->end_at ,$course->username );
        $course->update([ 'clash_ids' => $res ]);
    }
    // 布置作业
//    public static function saveHomework($request)
//    {
//        $content = htmlspecialchars($request->input('content', null),ENT_QUOTES);
//        $course = CourseModel::find($request->id);
//        if (!$course) throw new ApiException('未知错误');
//
//        if ($content) {
//            $res = Homework::updateOrCreate(
//                ['course_id' => $request->id],
//                ['content' => $content]
//            );
//            if ($res) CourseSignModel::where('course_id',$request->id)->update(['homework'=>1]);
//        } else {
//            $res = $course->homework()->delete();
//            if ($res) CourseSignModel::where('course_id',$request->id)->update(['homework'=>0]);
//        }
//        if ($res) return true;
//        else throw new ApiException('未设置作业');
//    }
}