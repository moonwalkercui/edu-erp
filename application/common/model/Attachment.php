<?php

namespace app\common\model;

class Attachment extends BaseModel
{
    protected $table = 'attachment';

    public function getAttDirAttr($value)
    {
        return add_image_prefix($value);
    }

    public function getSattDirAttr($value)
    {
        return add_image_prefix($value);
    }

    /**添加附件记录
     */
    public static function attachmentAdd($name, $att_size, $att_type, $att_dir, $satt_dir = '', $pid = 0, $f_name = null)
    {
        $data['name'] = $name;
        $data['f_name'] = $f_name;
        $data['att_dir'] = $att_dir;
        $data['satt_dir'] = $satt_dir;
        $data['att_size'] = intval($att_size);
        $data['att_type'] = $att_type;
        $data['time'] = now();
        $data['pid'] = $pid;
        return self::create($data);
    }

    /**
     * 获取分类图
     * */
    public static function getAll($id)
    {
        $model = new self;
        $where['pid'] = $id;
        return $model = $model->where($where)->order('att_id desc')->paginate(32);
//        return self::pager($model, [], null, 24);
    }

    /**
     * 获取单条信息
     * */
    public static function getinfo($att_id)
    {
        $model = new self;
        $where['att_id'] = $att_id;
        return $model->where($where)->select()->toArray()[0];
    }

    public static function deleteItem($where)
    {
//        var_dump($where);
        $attinfo = self::where($where)->find();
        if ($attinfo == null) exception('未查询到记录');
        self::where($where)->delete();
        @unlink(env('root_path') . 'public' . remove_image_prefix($attinfo['att_dir']));
        @unlink(env('root_path') . 'public' . remove_image_prefix($attinfo['satt_dir']));
    }

    // 通过uri查找记录
    public static function getAttByUri($uri)
    {
        if(!$uri) return null;
        $p = strpos($uri, MAIN_DOMAIN);
        if (false !== $p) $uri = substr($uri, $p + strlen(MAIN_DOMAIN));
        return self::where('att_dir', $uri)->find();
    }
}