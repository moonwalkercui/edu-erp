<?php
namespace app\common\model;

use think\model\concern\SoftDelete;

class Course extends BaseModel
{
    use SoftDelete,BelongsToGrade,BelongsToClazz,BelongsToStaff;
    protected $table = 'course';
    protected $deleteTime = 'delete_time';
    // 助教
    public function assistant()
    {
        return $this->belongsTo(Staff::class, 'assistant_id')->field('id,name,photo');
    }
    function times()
    {
        return $this->belongsTo(CourseTimes::class, 'times_id');
    }
   function section()
   {
       return $this->belongsTo(Section::class, 'section_id');
   }
    public function getStatusTextAttr($value, $data)
    {
        return $this->transferStatusText($data['status'], 'course');
    }

    // 检查冲突
    static function checkCrash($items)
    {
        if(count($items) <= 1) return null;

        $check_func = function($v1, $v2) {
            // 如果一个老师在同一天有多个一样的课次安排，则冲突
            if(
                $v1['staff_id'] == $v2['staff_id'] &&
                $v1['date'] == $v2['date'] &&
                $v1['times_id'] == $v2['times_id']
            )
                return  '【冲突】'. \app\common\model\Staff::where('id', $v1['staff_id'])->value('name')
                    . '在' . $v1['date'] . '设置了相同课次：'
                    . CourseTimes::where('id', $v1['times_id'])->value('name');

            // 一个班在同一天同一时间有多门课,则冲突
            if(
                $v1['clazz_id'] == $v2['clazz_id'] &&
                $v1['date'] == $v2['date'] &&
                $v1['times_id'] == $v2['times_id']
            )
                return  '【冲突】'. \app\common\model\Clazz::where('id', $v1['clazz_id'])->value('name')
                    . '在' . $v1['date'] . '设置了相同课次：'
                    . CourseTimes::where('id', $v1['times_id'])->value('name');

            return null;
        };
        $start_date = 0;
        $end_date = 0;
        foreach ($items as $k1=>$v1) {
            foreach ($items as $k2=>$v2) {
                if($v1['key'] != $v2['key']) {
                    if($res = $check_func($v1, $v2)) return $res;
                }
            }
            $start_date = ( $start_date != 0 && $start_date < strtotime($v1['date'])) ? $start_date : strtotime($v1['date']) ;
            $end_date = $end_date > strtotime($v1['date']) ? $end_date : strtotime($v1['date']) ;
        }
        $item_indb = self::where('date','between', [date('Y-m-d', $start_date), date('Y-m-d', $end_date)])->select()->toArray();
        foreach ($items as $k1=>$v1) {
            foreach ($item_indb as $k2=>$v2) {
                if($res = $check_func($v1, $v2)) return $res;
            }
        }
        return null;
    }
}