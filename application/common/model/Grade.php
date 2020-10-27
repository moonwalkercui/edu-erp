<?php
namespace app\common\model;

use think\model\concern\SoftDelete;

class Grade extends BaseModel
{
    use SoftDelete;
    protected $table = 'grade';
    protected $deleteTime = 'delete_time';
}