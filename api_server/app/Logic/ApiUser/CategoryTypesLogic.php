<?php
namespace App\Logic\ApiUser;

use App\Model\CategoryProperty;
class CategoryTypesLogic
{
    public static function save($request , $condition = [])
    {
        $data['name'] = cleanXss($request->input('name'));
        $title = '修改类型 ';
        if(empty($condition)){
            $title = '添加类型 ';
        }
        return BaseLogic::save('分类管理', $title.$data['name'] , '\App\Model\CategoryTypes' , $data , $condition );
    }
    public static function delete($id)
    {
        return BaseLogic::delete('分类管理', '删除类型' , '\App\Model\CategoryTypes' , ['id' => $id ]  , function($model) use ($id){
            CategoryProperty::where('type_id',$id)->delete();
        });
    }
    public static function createProperty($request)
    {
        $data['type_id'] = intval($request->input('id'));
        $data['name'] = cleanXss($request->input('name'));
        return BaseLogic::save('分类管理','添加类型属性'.$data['name'] , '\App\Model\CategoryProperty' , $data);
    }
    public static function updateProperty($request)
    {
        $condition['id'] = intval($request->input('id'));
        $data['name'] = cleanXss($request->input('name'));
//        $property = CategoryPropertyModel::where($condition)->first();
        return BaseLogic::save('分类管理','修改类型属性为 '.$data['name'] , '\App\Model\CategoryProperty' , $data , $condition);
    }
    public static function deleteProperty($id)
    {
        $condition['id'] = intval($id);
//        $property = CategoryPropertyModel::where($condition)->first();
        return BaseLogic::delete('分类管理', '删除类型属性' , '\App\Model\CategoryProperty' , ['id' => $id ] );

    }

}
