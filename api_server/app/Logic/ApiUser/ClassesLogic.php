<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Classes as ClassesModel;
use App\Model\MemberProfile;
class ClassesLogic
{
    public static function save($request , $condition = [])
    {
        $data['sn'] = makeSn();
        $data['name'] = cleanXss($request->input('name'));
        $data['division_id'] = $request->input('division');
        $data['description'] = cleanXss($request->input('description'));
        if(empty($condition)){
            $title = '添加班级';
        }else{
            $title = '更新班级';
        }
        return BaseLogic::save('班级管理',$title.':'.$data['name'] , '\App\Model\Classes' , $data , $condition );
    }
    public static function delete($id)
    {
        $class = ClassesModel::find($id);
        $class->members()->detach();
        return BaseLogic::delete('班级管理','删除班级' ,'\App\Model\Classes' ,['id' => $id ]);
    }
    // 更新班级的学员
    public static function saveMembers($class_id,$members_mobiles)
    {
        $class = ClassesModel::find($class_id);
        if($members_mobiles == null) {
            $class->members()->detach();
        }else{
            $members_ids = MemberProfile::whereIn('mobile',$members_mobiles)->pluck('id');
            if($members_ids->isEmpty()) throw new ApiException('未选择学员');
            $class->members()->sync($members_ids);
        }
//        CourseSignLogic::synchClassSigns($class_id);
    }
}
