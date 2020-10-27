<?php
namespace app\common\model;

use think\model\concern\SoftDelete;

class Student extends BaseModel
{
    use BelongsToUser, SoftDelete, BelongsToClazz, BelongsToGrade;
    protected $table = 'student';
    protected $deleteTime = 'delete_time';
    function account()
    {
        return $this->hasOne(StudentAccount::class, 'student_id');
    }
    public function getGenderTextAttr($value, $data)
    {
        return $this->transferStatusText($data['gender'], 'gender');
    }
    public function getStatusTextAttr($value, $data)
    {
        return $this->transferStatusText($data['status'], 'normal');
    }
    static function getNameStrByIds($ids)
    {
        $names = self::where('id', 'in', $ids)->column('name');
        return implode(',', $names);
    }
    // 关联微信
    function wxAccount()
    {
        return $this->hasOne(WxAccess::class, 'student_id');
    }
}