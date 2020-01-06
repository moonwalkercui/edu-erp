<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Illuminate\Foundation\Auth\User as Authenticatable;
/* 学员会员表，会员不能使用后台 */
class MemberPool extends Authenticatable implements JWTSubject
{
    use SoftDeletes, SetConnection;
    protected $table='member_pool';
    protected $fillable = ['name', 'mobile','owner','company_name','email','industry','source'];

//    // 多对多关联用户
//    public function users()
//    {
//        return $this->belongsToMany('App\Model\User', 'user_members','member_id','user_id');
//    }
//    // 一对多个用户顾客表：一个顾客可以有多个用户user创建记录
//    public function userMembers()
//    {
//        return $this->hasMany('App\Model\UserMember','member_id');
//    }
    public function info()
    {
        return $this->belongsTo('App\Model\Member','mobile','mobile');
    }
    public function getJWTIdentifier()
    {
        return $this->getKey();
    }
    public function getJWTCustomClaims()
    {
        return [];
    }
    public static function updateParentId($mobile, $parent_id)
    {
        return self::where('mobile', $mobile)->update([
            'parent_id' => $parent_id
        ]);
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'switch');
    }
    public function getIndustryAttribute($value)
    {
        return getStatusText($value,'industry');
    }
    public function getSourceAttribute($value)
    {
        return getStatusText($value,'source');
    }
    public function user()
    {
        return $this->belongsTo('App\Model\UserProfile','owner','username');
    }
    public function memberClasses()
    {
        return $this->belongsToMany('App\Model\Classes' , 'class_member' , 'member_id' , 'class_id');
    }
    public function signs()
    {
        return $this->hasMany('App\Model\CourseSign','member_mobile','mobile'); // 因为有可能课程软删除了
    }

}
