<?php
namespace app\common\model;

use think\Exception;
use think\facade\Log;

class ClazzSign extends BaseModel
{
    use BelongsToClazz, BelongsToStudent;
    protected $table = 'clazz_sign';

    /*
    * 处理签到逻辑 包括验证是否已签过
    * @param student_id 学生id
    * @param time 签到时间戳
    * */
    static function addOne($student_id, $clazz_id)
    {
        $time = time();

        list($sign_state, $sign_stage) = Clazz::validateSignTime($clazz_id, $time);

        if($sign_state == Clazz::TOO_EARLY) {
            throw new Exception("未到签到时间");
        }
        $record = self::where(compact('student_id','clazz_id','sign_stage'))->whereTime("sign_time", "today")->order("sign_time desc")->find();

        if(!$record) {
            // 如果没找到现阶段的签到或签退，那么新增
            $data = [
                'student_id' => $student_id,
                'clazz_id' => $clazz_id,
                'sign_time' => now(),
                'sign_state' => $sign_state,
                'sign_stage' => $sign_stage,
                'remark' => Clazz::makeRemark($sign_state),
            ];
            self::insert($data);
        } else {

            // 如果已经第一次签到，并且这次也是签到，那么提示不能重复签到了
            if(($record->sign_stage == Clazz::STAGE1 && $sign_stage == Clazz::STAGE1) || ($record->sign_stage == Clazz::STAGE3 && $sign_stage == Clazz::STAGE3)) {
                throw new Exception("不能重复签到");
            }
            // 处理重复签退，更新签退日期即可
            if($sign_state == Clazz::FIRST_LEAVE || $sign_state == Clazz::SECOND_LEAVE) {
                $record->sign_time = now();
                $record->save();
            }
        }
        return $sign_state;
    }

    /*
     * 最新签到状态
     * */
    static function lastSignState($student_id, $clazz_id, $check_state)
    {
        $lastOne = self::where(compact('student_id','clazz_id'))->whereTime("sign_time",'today')->order('sign_time desc')->find();
        if(!$lastOne) return 0; // 未签到
        if($lastOne['sign_stage'] >= $check_state) return 1; // 已签到
        else return 0; // 未签到
    }
}