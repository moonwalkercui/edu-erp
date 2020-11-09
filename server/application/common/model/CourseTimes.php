<?php
namespace app\common\model;

use think\model\concern\SoftDelete;

class CourseTimes extends BaseModel
{
    use SoftDelete;
    protected $table = 'course_times';
    protected $deleteTime = 'delete_time';

}