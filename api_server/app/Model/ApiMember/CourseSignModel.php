<?php
namespace App\Model\ApiMember;

use App\Exceptions\ApiException;
use App\Model\Course;
use App\Model\CourseSign;
use App\Model\Setting;

class CourseSignModel extends CourseSign
{
//    use  OfMemberTrait;
    // 获取可签到列表
    public static function getSigning()
    {
        return self::ofMember()
            ->where('course_start_at', '<=', self::getSignTimeLimit())
            ->where('status',0)
            ->get();
    }
    // member签到逻辑
    public static function sign($sn, $member_id)
    {
        $course = Course::where('sn', $sn)->first();
        if($course == null) throw new ApiException('失败:无效课时');
        if( Setting::getValue('only_sign_today') == 1 && $course->date != date('Y-m-d',time()) )
            throw new ApiException('只能签到今日课程, 请联系老师补签');

        return CourseSign::handleSign($member_id, $course->id, $course->product_id);
    }

    // member签到逻辑
//    public static function sign($sn, $member_id)
//    {
//        $course = CourseModel::where('sn', $sn)->first();
//        if($course == null) throw new ApiException('失败:无效课时');
//        if($course->date != date('Y-m-d',time())) throw new ApiException('只能签到今天的课程');
//        $res = self::where([
//            'course_id' => $course->id,
//            'member_id' => $member_id,
//        ])->update([
//            "status" => 1,
//            "sign_at" => now()
//        ]);
//        if ($res === 0){
//            throw new ApiException('未签到');
//        } else {
//            return true;
//        }
//    }

}
