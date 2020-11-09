<?php

namespace app\common\model;

use service\UtilService;

class AttachmentCate extends BaseModel
{
    protected $table = 'attachment_category';
    public static function Add($name,$att_size,$att_type,$att_dir,$satt_dir='',$pid = 0 )
    {
        $data['name'] = $name;
        $data['att_dir'] = $att_dir;
        $data['satt_dir'] = $satt_dir;
        $data['att_size'] = $att_size;
        $data['att_type'] = $att_type;
        $data['time'] = time();
        $data['pid'] = $pid;
        return self::create($data);
    }
    /**
     * 获取分类图
     * */
    public static function getAll(){
        $model = new self;
        return list_to_child($model->select()->toArray(), 0, 'pid');
    }
    /**获取分类下拉列表
     * @return array
     */
    public static function getCateList($id = 10000){
        $model = new self();
        if($id == 0)
            $model->where('pid',$id);
        return UtilService::sortListTier($model->select()->toArray());
    }
}