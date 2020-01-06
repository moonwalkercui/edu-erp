<?php

namespace App\Model;

class UserProfile extends BaseModel
{

    protected $table='user_profile';
    protected $fillable = [
        'user_id',
        'username',
        'division_id',
        'department_id',
        'department_ids',
        'divisions',
        'name',
        'phone',
        'email',
        'intro',
        'avatar',
        'id_number',
        'city',
        'address',
        'qq',
        'education',
        'idcard_number',
        'native_place',
        'gender',
        'married',
        'birthday',
        'graduation_school',
        'graduation_date',
    ];
    public function getAvatarAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function photo()
    {
        return $this->hasMany('App\Model\UserPhoto','user_id','user_id');
    }
    public function orders()
    {
        return $this->hasMany('App\Model\UserPhoto','user_id','user_id');
    }
    public function zone()
    {
        return $this->hasMany('App\Model\Zone','uid','user_id');
    }
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id');
    }
    public function department()
    {
        return $this->belongsTo('App\Model\Department','department_id');
    }
    public function getIsManagerAttribute($value)
    {
        return getStatusText($value,'is_yes');
    }
//    public function memberPool()
//    {
//        return $this->hasMany('App\Model\MemberPool','owner','username');
//    }
    public static function createEntity(User $user, $org_id, $internal = null)
    {
        return self::Create([
            'user_id' => $user->id,
            'username' => $user->username,
            'name' => $user->nickname,
            'division_id' => $internal ? $internal : 0
        ]);
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'switch');
    }
    public function getGenderAttribute($value)
    {
        return getStatusText($value,'gender');
    }
    public function getMarriedAttribute($value)
    {
        return getStatusText($value,'married');
    }
    // 最新课程
    public function newCourse()
    {
        return $this->hasOne('App\Model\Course','user_id','user_id')->select('id as course_id','user_id','product_id')->orderBy('updated_at','desc');
    }
    public static function checkUserExist($username)
    {
        return self::where('username', $username)->exists();
    }
}
