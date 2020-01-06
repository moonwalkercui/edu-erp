<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Divisions as DivisionsModel;
use App\Model\Divisions;
use App\Service\Api\LogService;
use App\Service\Api\UserService;


class OrganizationLogic
{
    // 退出组织 todo
    public static function quit()
    {
//        $user = UserService::getUserInfo();
//        $org = Organization::find($user->organization_id);
//        $res = User::where('id',$user->id)->update(['organization_id' => null]);
//        if($res) {
//            LogService::userLog('组织管理','退出组织:'.$org->organization_name);
//            return $org->organization_name;
//        }
//        else throw new ApiException('退出组织失败');
    }
    // 注销删除组织
    public static function delete($id)
    {
        return BaseLogic::delete('组织管理', '注销组织' , '\App\Model\ApiUser\OrganizationModel' , ['id' => $id ]);
    }
    public static function save($request ,$condition = [])
    {
        if(empty($condition)){
            $user = UserService::getUserInfo();
            // 判断是否已经创建过org了
            if(UserLogic::canKeepJoinOrg($user->username , $user->org_quantity) == false)
                throw new ApiException('抱歉, 您的组织数量已达上限: '.$user->org_quantity.'个.');
            $data['can_join'] = $request->input('can_join') == 'true' ? 1 : 0;
            $data['manage_username'] = $data['create_username'] = UserService::getUserName();
            $data['code'] = makeSn();
        }
        $data['type'] = intval($request->input('type'));
        $data['organization_name'] = cleanXss($request->input('organization_name'));
        $data['introduction'] = cleanXss($request->input('introduction'));
        $regin = $request->input('region');
        $data['province'] = $regin[0];
        $data['city'] = $regin[1];
        $data['area'] = $regin[2];
        $data['major'] = getStatusValueFromArray( $request->input('major') ,'org_major');
        $data['target'] = getStatusValueFromArray( $request->input('target') ,'org_target');

        return BaseLogic::save(
            '组织管理',
            empty($condition) ? '创建组织: '.$data['organization_name'] : '编辑组织' ,
            '\App\Model\ApiUser\OrganizationModel' ,
            $data ,
            $condition ,
            empty($condition) ? function($id) use ($user,$condition) {
                // 自动生成默认门店
                $division = Divisions::create([
                    'name' => '主门店',
                    'is_default' => 1,
                ]);
                // 更新user的org为创建的org_id 生成profile记录
                UserLogic::joinOrg($id , $user->username , $division->id);
                // 暂时自动审核 todo
                OrganizationModel::where('id',$id)->update(['status'=>1]);
            } : null
        );
    }

    public static function saveDivision($request , $condition = [])
    {
        $data['name'] = cleanXss($request->input('name'));
        $data['address'] = cleanXss($request->input('address'));
        $data['phone'] = cleanXss($request->input('phone'));
        $data['image'] = cleanImagePrefix($request->input('image'));
        $data['region_id'] = intval($request->input('region_id'));
        $data['sort'] = intval($request->sort);

        if(empty($condition)) $title = '添加门店';
        else $title = '更新门店';
        return BaseLogic::save('组织管理',$title.$data['name'] , '\App\Model\Divisions', $data , $condition );
    }
    public static function saveCoordinate($coordinate , $condition = [])
    {
        $arr = explode(',', $coordinate);
        if(count($arr) != 2) throw new ApiException('坐标值错误');
        $data['lat'] = $arr[0];
        $data['lng'] = $arr[1];
        return BaseLogic::save('组织管理', '更新坐标' , '\App\Model\Divisions', $data , $condition );
    }
    public static function deleteDivision($id)
    {
        $internal = DivisionsModel::find($id);
//        if($internal->is_default == 1) throw new ApiException('不能删除默认门店');

        if($internal->delete()){
//            $default_id = DivisionsModel::where('is_default',1)->value('id');
//            DepartmentModel::where('division_id',$id)->update(['division_id'=>$default_id]);
            LogService::userLog('组织管理','删除门店');
            return true;
        }
        else throw new ApiException('删除失败');
    }
    public static function setDefault($id)
    {
        DivisionsModel::where('is_default',1)->update(['is_default'=>0]);
        $internal = DivisionsModel::find($id);
        $internal->is_default = 1;
        if($internal->save()){
            LogService::userLog('组织管理','设置默认门店');
            return true;
        }
        else throw new ApiException('设置失败');
    }
//    // 检查用户是否可以继续创建组织
//    public static function checkCanCreate()
//    {
//        $max = config('system.org_managed_max');
//        if($max === 0) return true;
//        $count = Organization::where('manage_username',UserService::getUserName())->count();
//        if($count >= $max) throw new ApiException("您拥有的组织数量超过限制{$max}，不能继续创建");
//    }
//    // 检查用户是否可以继续加入组织
//    public static function checkCanJoin()
//    {
//        $max = config('system.org_joined_max');
//        if($max === 0) return true;
//        $count = UserProfile::where('username',UserService::getUserName())->count();
//        if($count >= $max) throw new ApiException("您加入的组织数量超过限制{$max}，不能继续创建");
//    }
    // 保存设置
//    public static function saveSetting($name,$value)
//    {
//        if(in_array($name,config('system.org_setting_field')) == false){
//            throw new ApiException('非法操作');
//        }
//        $org = Organization::find(UserService::getOrganizationId());
//        $org->{$name} = $value;
//        if( $org->save()){
//            LogService::userLog('组织管理','修改设置');
//            return true;
//        }
//        else throw new ApiException('设置失败');
//    }
//    // 通过键获取组织设置的值
//    public static function getSettingVal($name)
//    {
//        if(in_array($name,config('system.org_setting_field')) == false){
//            throw new ApiException('非法操作');
//        }
//        $org = Organization::find(UserService::getOrganizationId());
//        return $org->{$name};
//    }
}
