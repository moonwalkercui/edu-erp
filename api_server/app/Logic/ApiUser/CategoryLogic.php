<?php
namespace App\Logic\ApiUser;

// 加入组织服务
use App\Exceptions\ApiException;
use App\Model\Category as CategoryModel;
use App\Model\Resources\CategoryResource;
class CategoryLogic
{
    public static function save($request , $condition = [])
    {
        $data['name'] = cleanXss($request->input('name'));
        $parent = $request->input('parent');
        $data['parent_ids'] = empty($parent) ? '' : implode(',' ,$parent);
        $data['parent_id'] = empty($parent) ? 0 : end($parent);
        $types = $request->input('types',[]);

        $title = '修改分类';
        if(empty($condition)){
            $title = '添加分类';
        }else{
            if($condition['id'] == $data['parent_id']) throw new ApiException('上级分类选择错误');
        }
        return BaseLogic::save('分类管理', $title.$data['name'] , '\App\Model\Category' , $data , $condition ,function($id) use ($types,$condition){
            $cate = CategoryModel::find($id);
            if( empty($types) ){
                $cate->types()->detach();
            }else{
                $cate->types()->sync($types);
            }
        });
    }
    public static function delete($id)
    {
        return BaseLogic::delete('分类管理', '删除分类' , '\App\Model\Category' , ['id' => $id ] , function($model){
//            $model->types()->detach();
            // todo 是否更新清空product里的相关分类？
        });
    }
    public static function getProperties($id)
    {
        // 建立分类模型
        $cate = CategoryModel::find($id);
        $res= $cate->types()->with('properties')->get();
        // 找到分类对应的类型 关联属性
        return $cate ? CategoryResource::collection($res) : null;
    }
}
