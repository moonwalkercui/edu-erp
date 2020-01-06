<?php namespace App\Model;

// 模板消息id收集器
class TempMsgFormId extends BaseModel
{
    protected $table='tempmsg_form_id';
    protected $connection = 'common';
    public $timestamps = false;
    public static function createItem($org_code, $member_id, $form_id)
    {
        self::insert([
            'member_id' => $member_id,
            'form_id' => $form_id,
            'org_code' => $org_code,
            'add_time' => now(),
        ]);
    }
}
