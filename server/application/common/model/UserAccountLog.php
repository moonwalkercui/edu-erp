<?php

namespace app\common\model;

class UserAccountLog extends BaseModel
{
    protected $table = 'user_account_log';
    const TYPE = [
        'balance' => 1,
        'point' => 2
    ];
    public function getStageTextAttr($value, $data)
    {
        return $this->transferStatusText($data['stage'], 'account_log_stage');
    }
    // 写日志
    public static function addOne($user_id, $type, $amount, $remark='')
    {
        return self::insert([
            'user_id' => $user_id,
            'type' => $type,
            'amount' => $amount,
            'add_time' => now(),
            'remark' => $remark,
            'remaining_point' => UserAccount::where(compact('user_id'))->value('point'),
        ]);
    }
    // 积分列表
    static function integralList($user_id, $where = [])
    {
        $type = 2;
        return self::where(compact('user_id','type'))->where($where)->order('id desc')->paginate(30);
    }
    // 余额列表
    static function balanceList($user_id, $where = [])
    {
        $type = 1;
        return self::where(compact('user_id','type'))->where($where)->order('id desc')->paginate(30);
    }
}