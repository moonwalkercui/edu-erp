<?php
namespace App\Model;

use App\Service\Api\UserService;
use App\Service\Rbac\UserTrait;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Tymon\JWTAuth\Contracts\JWTSubject;

/* 用户可以使用后台 */
class User extends Authenticatable implements JWTSubject
{
    use Notifiable, SoftDeletes, UserTrait, SetConnection;
    protected $table = 'users';
    protected $fillable = ['username', 'password','nickname'];
    public static function getUidByUsername($username)
    {
        return self::where('username', $username)->value('id');
    }
    public function course()
    {
        return $this->hasMany('App\Model\Course','user_id');
    }
    public function profile()
    {
        return $this->hasOne('App\Model\UserProfile','user_id');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'switch');
    }
    public function getJWTIdentifier()
    {
        return $this->getKey();
    }
    public function getJWTCustomClaims()
    {
        return [];
    }
    // 通过微信的unionId返回User实例
    public static function getUserByWxUnionId($unionId)
    {
        return self::where('wx_union_id', $unionId)->first();
    }
    // 通过微信的id获取模型 暂时用小程序openid
    public static function findByWxId($wx_id, $by_field = 'miniapp_openid')
    {
        return self::where($by_field, $wx_id)->first();
    }
    public static function getProfile($username)
    {
        if($username == null) return null;
        return UserProfile::where('username',$username)->first();
    }
    
    public static function updateByWxUserInfo($user_info)
    {
        $user = self::where('miniapp_openid', $user_info['openId'])->first();
        $user->avatar = $user_info['avatarUrl'];
        $user->wx_union_id = isset($user_info['unionId']) ? $user_info['unionId'] : null;
        $user->nickname = $user_info['nickName'];
        $user->gender = $user_info['gender'];
        $user->city = $user_info['city'];
        $user->province = $user_info['province'];
        $user->save();
        return $user;
    }
    public static function register($data)
    {
        $user = new self();
        foreach ($data as $k => $v) {
            $user->$k = $v;
        }
        $user->save();
        return $user;
    }
    public static function createUser($username, $password, $name=null)
    {
        $user = self::create([
            'username' => $username,
            'password' => bcrypt($password),
            'nickname' => $name,
        ]);
        UserProfile::create([
            'user_id' => $user->id,
            'username' => $username,
            'name' => $name,
        ]);

        return $user->save();
    }

}
