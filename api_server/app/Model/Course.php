<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Course extends BaseModel
{
    use SoftDeletes;
    protected $table = 'courses';
    protected $fillable = [
        'sn',
        'division_id',
        'editor_id',
        'class_id',
        'class_name',
        'room_id',
        'room_name',
        'product_id',
        'product_name',
        'type',
        'day',
        'start_at',
        'clash_ids',
    ];
    public static function getTodayNumber()
    {
        return self::whereDate('start_at', date('Y-m-d', time()))->count();
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'course');
    }
    public function homework()
    {
        return $this->hasMany('App\Model\CourseHomework','course_id');
    }
    // 课程的成交人数
    public function deal()
    {
        return $this->hasMany('App\Model\MemberProduct','product_id','product_id');
    }
//    public function classes()
//    {
//        return $this->belongsTo('App\Model\Classes','class_id');
//    }
    public function user()
    {
        return $this->belongsTo('App\Model\UserProfile','user_id','user_id')->select('user_id','name','intro','username','email','avatar');
    }
    public function product()
    {
        return $this->belongsTo('App\Model\Product','product_id');
    }
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id');
    }
    public function productSpec()
    {
        return $this->hasOne('App\Model\ProductSpecification','product_id','product_id');
    }
    public function productSpecs()
    {
        return $this->hasMany('App\Model\ProductSpecification','product_id','product_id');
    }
    public function signs()
    {
        return $this->hasMany('App\Model\CourseSign', 'course_id');
    }
    public function members()
    {
        return $this->belongsToMany('App\Model\CourseSign', 'course_signs', 'course_id', 'member_mobile');
    }
    public function userProfile()
    {
        return $this->hasOne('App\Model\UserProfile', 'user_id', 'user_id');
    }
}
