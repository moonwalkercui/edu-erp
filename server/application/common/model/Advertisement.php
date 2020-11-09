<?php

namespace app\common\model;

class Advertisement extends BaseModel
{
    protected $table = 'advertisement';
    static $json_field = 'title,image,url';
    public static function listByPosition($position)
    {
        return self::where(compact('position'))->order('sort desc')->select();
    }
    public function getImageAttr($value)
    {
        return add_image_prefix($value);
    }
    public function setImageAttr($value)
    {
        return remove_image_prefix($value);
    }
    public function getStatusTextAttr($value, $data)
    {
        return $this->transferStatusText($data['status'], 'switch');
    }

    public function getPositionAttr($value)
    {
        $status = config('status.ad_position');
        return isset($status[$value]) ? $status[$value] : $value;
    }
    static function getByPosition($position)
    {
        return self::field(self::$json_field)->where(compact('position'))->where('status',1)->order('sort_num desc')->select()->toArray();
    }
}