<?php
namespace App\Logic\ApiUser;

class RoomLogic
{
    public static function save($request , $condition = [])
    {
        $data['name'] = cleanXss($request->input('name'));
        $data['division_id'] = intval($request->input('division'));
        $data['position'] = cleanXss($request->input('position',null));

        $title = '更新教室';
        if(empty($condition)){
            $title = '添加教室';
        }
        return BaseLogic::save('教室管理',$title , '\App\Model\Room' , $data , $condition);
    }
    public static function delete($id)
    {
        return BaseLogic::delete('教室管理','删除教室' ,'\App\Model\Room' ,['id' => $id ]);
    }
}
