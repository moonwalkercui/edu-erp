<?php

namespace App\Model;

use App\Exceptions\ApiException;
use Illuminate\Database\Eloquent\SoftDeletes;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Illuminate\Foundation\Auth\User as Authenticatable;
/* 学员会员表，会员不能使用后台 */
class MemberProfile extends Authenticatable implements JWTSubject
{
    use SoftDeletes, SetConnection;
    protected $table='member_profile';
    protected $fillable = ['member_id','referrer_id','salesman_uname','mobile','name', 'gender','occupation','birthday','height','avatar','nick_name','city','address','mobile','email','industry','source','red_packet','red_packet_temp'];
    public function vouchers()
    {
        return $this->belongsToMany('App\Model\Voucher' , 'member_voucher' , 'member_id', 'voucher_id', 'member_id')->withPivot('be_used','got_time');
    }
    public function signs()
    {
        return $this->hasMany('App\Model\CourseSign','member_id','member_id'); // 因为有可能课程软删除了
    }
    public function deal()
    {
        return $this->hasMany('App\Model\MemberProduct','member_id','member_id');
    }
    public function salesman()
    {
        return $this->belongsTo('App\Model\UserProfile','salesman_uname','username')->select('username','name');
    }
    public function member()
    {
        return $this->belongsTo('App\Model\Member');
    }
//    public function badges()
//    {
//        return $this->belongsToMany('App\Model\Badge' , 'member_badge', 'member_id', 'badge_id', 'member_id')->withPivot('got_time');
//    }
    public function order()
    {
        return $this->belongsTo('App\Model\Order','member_id','member_id');
    }
    public function orders()
    {
        return $this->hasMany('App\Model\Order','member_id','member_id');
    }
    public function getJWTIdentifier()
    {
        return $this->getKey();
    }
    public function getJWTCustomClaims()
    {
        return [];
    }
    public function getGenderAttribute($value)
    {
        return getStatusText($value,'gender');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'switch');
    }
    public function getOccupationAttribute($value)
    {
        return getStatusText($value,'occupation');
    }
    public function getIndustryAttribute($value)
    {
        return getStatusText($value,'industry');
    }
    public function getSourceAttribute($value)
    {
        return getStatusText($value,'source');
    }
    public static function getCollectionIds($member_id, $type='division')
    {
        return MemberCollection::where('member_id',$member_id)->where('type', $type)->pluck('target_id');
    }
    public static function storeCollection($member_id, $target_id, $type='division')
    {
        $where = [
            'member_id' => $member_id,
            'target_id' => $target_id,
            'type' => $type,
        ];
        $exist = MemberCollection::where($where)->first();
        if($exist == false) {
            $res = MemberCollection::insert(array_merge($where, ['created_time' => now()]));
            return $res ? true : false;
        }
        return null;
    }
    public static function removeCollection($member_id, $target_id, $type='division')
    {
        $where = [
            'member_id' => $member_id,
            'target_id' => $target_id,
            'type' => $type,
        ];
        $exist = MemberCollection::where($where)->delete();
        return $exist ? true : false;
    }
    public static function becomeStudent($member_id)
    {
        self::where('member_id', $member_id)->update([
            'is_student' => 1
        ]);
    }
    public static function makeProfileRes($profile)
    {
        return [
            'member_id' => $profile->member_id,
            'avatar' => $profile->avatar,
            'name' => $profile->name,
            'mobile' => $profile->mobile
        ];
    }
    public static function makeToken($profile)
    {
        if (! $token = auth('api_member')->login($profile)) {
            throw new ApiException('获取Token失败');
        } return $token;
    }

}
