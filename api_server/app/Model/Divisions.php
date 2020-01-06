<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Divisions extends BaseModel
{
    use SoftDeletes;
    protected $table = 'divisions';
    protected $fillable = ['name', 'address', 'introduction', 'image', 'phone'];
    public function rooms()
    {
        return $this->hasMany('App\Model\Room' , 'division_id');
    }
    public function courses()
    {
        return $this->hasMany('App\Model\Course','division_id');
    }
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
//    public function classes()
//    {
//        return $this->hasMany('App\Model\Classes','division_id');
//    }
//    public function proceeds()
//    {
//        return $this->hasMany('App\Model\Proceeds','division_id');
//    }
//    public function material()
//    {
//        return $this->hasMany('App\Model\Material','division_id');
//    }
    public function department()
    {
        return $this->hasMany('App\Model\Department','division_id');
    }
    public function product()
    {
        return $this->belongsToMany('App\Model\Product' , 'product_division', 'division_id', 'product_id');
    }
    public static function getDepartmentTree()
    {
        $internal_list = self::all()->toArray();

        $departments = Department::select('id', 'department_name as name','parent_id','division_id')->get()->toArray();
        $departments = unlimitedChild($departments);
        foreach ($internal_list as &$internal){
            $internal['is_internal'] = true;
            foreach ($departments as $depart){
                if( isset( $depart['division_id'] ) && $depart['division_id'] == $internal['id'] ){
                    $internal['_child'][] = $depart;
                }
            }
            if(isset($internal['_child']) ==false) {
                $internal['_child'] = [];
            }
        }
        return $internal_list;
    }
}
