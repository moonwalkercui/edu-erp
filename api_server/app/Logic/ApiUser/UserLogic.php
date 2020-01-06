<?php
namespace App\Logic\ApiUser;

// 加入组织服务
use App\Exceptions\ApiException;
use App\Model\ApiUser\UserModel;
use App\Model\Organization;
use App\Model\User;
use App\Model\UserProfile;
use App\Service\Api\UserService;

class UserLogic
{
    public static function update($request)
    {
//        $update['division_id'] = $request->input('internal');
//        $update['department_id'] = $request->input('department');
//        $update['department_ids'] = DepartmentModel::getParentsIds($update['department_id']);
        if ($request->has('name'))          $update['name'] = $request->input('name');
        if ($request->has('phone'))         $update['phone'] = $request->input('phone');
        if ($request->has('email'))         $update['email'] = $request->input('email');
        if ($request->has('intro'))         $update['intro'] = $request->input('intro');
        if ($request->has('avatar'))        $update['avatar'] = cleanImagePrefix($request->input('avatar'));
        if ($request->has('address'))       $update['address'] = $request->input('address');
        if ($request->has('qq'))            $update['qq'] = $request->input('qq');
        if ($request->filled('sort'))            $update['sort'] = intval($request->sort);
        if ($request->has('education'))     $update['education'] = $request->input('education');
        if ($request->has('idcard_number')) $update['idcard_number'] = $request->input('idcard_number');
        if ($request->has('gender'))        $update['gender'] = $request->filled('gender') ? getStatusValue($request->input('gender'),'gender') : 0;
        if ($request->has('married'))       $update['married'] = $request->filled('married') ? getStatusValue($request->input('married'),'married') : 0;
        if ($request->has('birthday'))      $update['birthday'] =  $request->input('birthday');
        if ($request->has('graduation_school'))     $update['graduation_school'] = $request->input('graduation_school');
        if ($request->has('graduation_date'))       $update['graduation_date'] = $request->input('graduation_date');
        if ($request->has('native_place'))      $update['native_place'] = $request->input('native_place');
//        if ($request->has('is_manager'))      $update['is_manager'] = $request->filled('is_manager') ? getStatusValue($request->input('is_manager'),'is_yes') : 0;
        if ($request->has('status'))      $update['status'] = $request->filled('status') ? getStatusValue($request->input('status'),'switch') : 0;

        $update = cleanXss($update);
        if (count($update) == 0) throw new ApiException('未设置更新数据');
        $user = UserModel::where('username',$request->input('uname'))->first();
        if($user == false) throw new ApiException('未知用户');

        return BaseLogic::save('员工管理','更新会员资料'.$request->input('uname') , '\App\Model\UserProfile' , $update , [
            'user_id' => $user->id ,
        ]);
    }
    // 处理加入组织逻辑 第二个参数如果为null表示登录者自己加入
    // @param org_id 加入的组织id
    // @param internal 加入的门店
    public static function joinOrg($org_id , $username , $internal)
    {
        $user = User::where('username',$username)->first();
        $user->save();
        if($profile = UserProfile::where('user_id',$user->id)->first()){
            $profile->division_id = $internal;
            $profile->save();
        }else{
            UserProfile::createEntity($user, $org_id, $internal);
        };
    }
    // 判断是否加入组织超过上限 如果超过返回false 如果没有超过返回true
    // 第二个参数可以直接传入最大可加入数量 避免重复查询
    public static function canKeepJoinOrg($username = null , $max = false)
    {
        if($username == null) $username = UserService::getUserName();
        if($max == false) $max = User::where('username',$username)->value('org_quantity');
        if($max == -1) return true;
//        $count = Organization::where('manage_username',$username)->count();
        $count = UserProfile::where('username',$username)->count();
        if($count >= $max) return false;
        else return true;
    }
    // 判断创建组织是否超过上限
    public static function canKeepCreateOrg()
    {
        $username = UserService::getUserName();
        $max = User::where('username',$username)->value('org_quantity');
        if($max == -1) return true;
        $count = UserProfile::where('username',$username)->count();
        if($count >= $max) return false;
        else return true;
    }
    public static function switchover( $code )
    {
        $user = UserService::getUser();
        // 查询$code对应的org
        $org = Organization::where('code',$code)->first();
        // 查询userprofile里username和org_id记录是否存在
        $profile = UserProfile::where('username',$user->username)->first();
        if($profile == false) throw new ApiException('非法操作');
        // 如果存在，那么更新user表里的org_id
        return $user->save();
        // 重新刷新刷新userinfo cookie ?
    }
    public static function create($username, $password, $name)
    {
        $res = User::createUser($username, $password, $name);
        if ($res === false) throw new ApiException('创建失败');
        return true;
    }
    public static function changePw($username, $password)
    {
        return UserModel::where('username' , $username)->update([
            'password' => bcrypt($password)
        ]);
    }
}