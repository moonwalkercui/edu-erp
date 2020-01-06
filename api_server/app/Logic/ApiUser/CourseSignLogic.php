<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Course as CourseModel;
use App\Model\CourseSign as CourseSignModel;
use App\Model\MemberProfile;
use App\Model\Order;
use App\Model\OrderItem;
use App\Service\Api\LogService;
use App\Service\Api\UserService;

class CourseSignLogic
{
    // user代签

    public static function allograph($course_id, $member_id)
    {
        $username = UserService::getUserName();
        $res = CourseSignModel::allograph($member_id, $course_id, $username);
        if ($res){
            LogService::userLog('签到管理','代签:学员-'.MemberProfile::where('member_id', $member_id)->value('nick_name'));
            return true;
        } else {
            throw new ApiException('代签失败');
        }
    }
    public static function givePoints($course_id, $member_id, $points)
    {
        $map = [
            'member_id' => $member_id,
            'course_id' => $course_id,
        ];
        $sign = CourseSignModel::where($map)->first();
        if($sign == null) throw new ApiException('未查询到签到记录');

        return CourseSignModel::givePoint(UserService::getUserName(),$sign->id, $points);
    }
    public static function delete($ids)
    {
        if(empty($ids)) throw new ApiException('未选择记录');
        if( CourseSignModel::whereIn('id',$ids)->delete()){
            LogService::userLog('签到管理','删除签到记录');
            return true;
        } else throw new ApiException('删除失败');
    }
//    // 刷新学员的签到记录 用于班级内学员变动后或签到记录删除后 重新刷新学员签到记录
//    public static function refreshCourseSigns($course_id)
//    {
//        $course = CourseModel::find($course_id);
//        return self::synchClassSigns($course->class_id);
////        // 获取现在的所有签到记录
////        $signs = $course->signs()->pluck('member_name','member_mobile');
////        // 获取班级的学员记录
////        $members = $course->classes->members()->where('status',1)->pluck('name','mobile');
////        if($members->isEmpty())   return true;        // 如果班级内没有成员则什么都不干
////
////        $sign_members = array_keys($signs->toArray());
////        $class_members = array_keys($members->toArray());
////
////        // 判断有没有没有编排签到记录的
////        $attr = [];
////        foreach ($class_members as $m){
////            if($signs->isEmpty() || in_array($m , $sign_members) == false){
////                $attr[] = [
////                    'organization_id' => UserService::getOrgInternalId() ,
////                    'member_name' => $members[$m],
////                    'member_mobile' => $m,
////                    'course_id' => $course_id,
////                ];
////            }
////        }
////        if(empty($attr)) return true;
////
////        if( CourseSignModel::insert($attr) ){
////            LogService::userLog('课时管理','刷新课时学员');
////            return true;
////        }
////        else throw new ApiException('刷新失败');
//    }

    // 通过查询谁买过这个课程来增加签到记录
    public static function addByOrders($product_spec_id, $course_sn)
    {
        $course_id = CourseModel::where('sn', $course_sn)->value('id');
        $order_ids = OrderItem::where('item_spec', $product_spec_id)->pluck('order_id');
        $member_ids = $insert = [];
        if ($order_ids->isNotEmpty()) {
            $member_ids = array_unique(Order::whereIn('id', $order_ids)->pluck('member_id')->toArray());
        }
        if (!empty($member_ids)) {
            foreach ($member_ids as $mid) {
                // 如果此学员没有这个课
                $insert[] = [
                    'member_id' => $mid,
                    'course_id' => $course_id,
                    'product_spec_id' => $product_spec_id,
                    'created_at' => now(),
                ];
            }
        }
        if(!empty($insert))  CourseSignModel::insert($insert);
    }

    // 更新班级内学员的签到记录，已签到的和请假的记录不受影响
//    public static function synchClassSigns($class_id)
//    {
//        $class = ClassesModel::find($class_id);
//        $members = $class->members()->where('status',1)->pluck('name','mobile')->toArray();
////        if(empty($members)) throw new ApiException('该班级内无学员');
//        // 找到班级的所有课程
//        $courses = CourseModel::whereDate('start_at', '>', Carbon::now())->where('class_id', $class_id)->get();
//
//        if($courses->isNotEmpty())
//            CourseSignModel::whereIn('course_id', $courses->pluck('id'))->where('status',0)->delete(); // 不包括已签到的和请假的
//
//        if(!empty($members) && $courses->isNotEmpty()){
//            $attr = [];
//            foreach($members as $mobile => $name) {
//                foreach($courses as $l) {
//                    $attr[] = [
//                        'member_mobile' =>$mobile ,
//                        'course_id' => $l->id ,
//                        'product_spec_id' => '?',
//                        'member_name' => $name,
//                        'course_start_at' => $l->start_at,
//                    ];
//                }
//            }
//            if(!empty($attr)) CourseSignModel::insert($attr);
//        }
//        LogService::userLog('签到处理','更新班级成签到记录:'.implode(',',$members));
//        return true;
//    }

}