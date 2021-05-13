<?php

namespace app\common\model;

use think\model\concern\SoftDelete;

class Clazz extends BaseModel
{
    use SoftDelete, BelongsToGrade;
    protected $table = 'clazz';
    protected $deleteTime = 'delete_time';

    // 签到阶段
    const STAGE1 = 1; // 第1次签到
    const STAGE2 = 2; // 第1次签退
    const STAGE3 = 3; // 第2次签到
    const STAGE4 = 4; // 第2次签退

    // 签到状态列表
    const TOO_EARLY = 0; // 未到签到时间
    const FIRST_SIGN = 10; // 第1次签到成功
    const FIRST_SIGN_LATE = 11; // 第1次签到迟到
    const FIRST_LEAVE = 20; // 第1次签退成功 可以重复签
    const SECOND_SIGN = 30; // 第2次签到成功
    const SECOND_SIGN_LATE = 31; // 第2次签到迟到
    const SECOND_LEAVE = 40; // 第2次签退成功 可以重复签

    // 获取负责人ids
    static function getStaffIds(int $id)
    {
        $ids = self::where(compact('id'))->value('staff_in_charge');
        return explode(',', $ids);
    }
//    static function getByStudentId($student_id)
//    {
//        $cid = Student::where('id', $student_id)->value('clazz_id');
//        if(!$cid) return null;
//        return self::find($cid);
//    }
    static function getStudents($clazz_id)
    {
        return Student::where(compact('clazz_id'))->select();
    }

    static function getByStaffId(int $staff_id, $where = [])
    {
        return self::where("FIND_IN_SET({$staff_id},`staff_in_charge`)")->where($where)->field('id,name')->select();
    }

    /*
     * 验证签到时间 不包含验证是否已签过
     * sign_start1 和 sign_end1 必须设置
     * */
    static function validateSignTime($class_id, $time = null)
    {

        $limit_minute = 30; // 签到30分钟内有效

        if ($time == null) $time = time();

        $clazz = self::get($class_id);

        $sign_start1 = $clazz->sign_start1;
        $sign_end1 = $clazz->sign_end1;
        $sign_start2 = $clazz->sign_start2;
        $sign_end2 = $clazz->sign_end2;

//        dump($time);
//        dump($clazz);
//        dump( $sign_start1);
//        dump( $sign_end1);
//        dump( $sign_start2);
//        dump( $sign_end2);
        $sign_start1 = strtotime(date("Y-m-d ") . $sign_start1);
        $sign_end1 = strtotime(date("Y-m-d ") . $sign_end1);
        $sign_start2 = intval($sign_start2) == 0 || intval($sign_end2) == 0 ? 0 : strtotime(date("Y-m-d ") . $sign_start2);
        $sign_end2 = intval($sign_start2) == 0 || intval($sign_end2) == 0 ? 0 : strtotime(date("Y-m-d ") . $sign_end2);
//        dump( $sign_start1);
//        dump( $sign_end1);
//        dump( $sign_start2);
//        dump( $sign_end2);
//        dump("=============" . date("Y-m-d H:i:s", $time) . "=============");
//        dump(date('Y/m/d H:i:s', $sign_start1));
//        dump(date('Y/m/d H:i:s', $sign_end1));
//        dump(date('Y/m/d H:i:s', $sign_start2));
//        dump(date('Y/m/d H:i:s', $sign_end2));

        if ($sign_start2 != 0 && $sign_end2 != 0) {
            if ($time >= $sign_end2) {
//                echo '第2次签退';
                return [self::SECOND_LEAVE, self::STAGE4];
            }

            if ($time > $sign_start2 && $time < $sign_end2) {
//                echo '第2次签到迟到';
                return [self::SECOND_SIGN_LATE, self::STAGE3];
            }

            if ($time >= ($sign_start2 - $limit_minute * 60) && $time <= $sign_start2) {
//                echo '第2次签到';
                return [self::SECOND_SIGN, self::STAGE3];
            }
        }

        if ($time >= $sign_end1) {
//            echo '第1次签退';
            return [self::FIRST_LEAVE, self::STAGE2];
        }

        if ($time > $sign_start1 && $time < $sign_end1) {
//            echo '第1次签到迟到';
            return [self::FIRST_SIGN_LATE, self::STAGE1];
        }

        if ($time >= ($sign_start1 - $limit_minute * 60) && $time <= $sign_start1) {
//            echo '第1次签到';
            return [self::FIRST_SIGN, self::STAGE1];
        }

//        echo "未到签到时间";
        return [self::TOO_EARLY, 0];

    }

    // 签到文字
    static function makeRemark($state)
    {
        return self::getStates()[$state];
    }

    static function getStates()
    {
        return [
            self::TOO_EARLY => '未到签到时间',
            self::FIRST_SIGN => '第1次签到正常',
            self::FIRST_SIGN_LATE => '第1次签到迟到',
            self::FIRST_LEAVE => '第1次签退正常',
            self::SECOND_SIGN => '第2次签到正常',
            self::SECOND_SIGN_LATE => '第2次签到迟到',
            self::SECOND_LEAVE => '第2次签退正常',
        ];
    }

    // 通过状态获取阶段
    static function getStageByState($state)
    {
        if($state == self::FIRST_SIGN || $state == self::FIRST_SIGN_LATE ) {
            return self::STAGE1;
        } elseif ($state == self::FIRST_LEAVE ) {
            return self::STAGE2;
        } elseif ($state == self::SECOND_SIGN || $state == self::SECOND_SIGN_LATE) {
            return self::STAGE3;
        } elseif ($state == self::SECOND_LEAVE ) {
            return self::STAGE4;
        } else {
            return null;
        }
    }

}