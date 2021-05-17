<?php
namespace app\common\model;

class Vcourse extends BaseModel
{
    use BelongsToClazz, BelongsToStaff;
    protected $table = 'vcourse';
    function vod()
    {
        return $this->belongsTo(Vod::class, 'vod_id');
    }

}