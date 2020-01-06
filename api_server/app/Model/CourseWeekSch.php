<?php
namespace App\Model;

// 课时周编排
class CourseWeekSch extends BaseModel
{
    protected $table='course_week_schedule';
    public $timestamps = false;
    protected $fillable = [
        'user_id' ,
        'product_id' ,
        'start_time' ,
        'end_time' ,
        'division_id' ,
        'week_days' ,
        'editor_id' ,
        'add_time' ,
    ];
    public function user()
    {
        return $this->belongsTo('App\Model\UserProfile','user_id','user_id')->select('user_id','name','username','avatar');
    }
    public function product()
    {
        return $this->belongsTo('App\Model\Product','product_id')->select('id','name');
    }
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id')->select('id','name');
    }
}
