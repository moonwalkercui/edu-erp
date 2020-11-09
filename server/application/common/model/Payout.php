<?php
namespace app\common\model;

use think\model\concern\SoftDelete;

class Payout extends BaseModel
{
    use BelongsToStaff, SoftDelete;
    protected $table = 'payout';
    protected $deleteTime = 'delete_time';
    function type()
    {
        return $this->belongsTo(PayoutType::class, 'type_id');
    }
    function getStatusTextAttr($value, $data)
    {
        $status = config('status.payout');
        return isset($status[$data['status']]) ? $status[$data['status']] : $data['status'];
    }
    function getImagesAttr($value, $data)
    {
        $imgs = $value ? explode(',', $value) : [];
        foreach ($imgs as &$v) {
            $v = add_image_prefix($v);
        }
        return $imgs;
    }
}