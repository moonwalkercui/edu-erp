<?php
//
//namespace app\backend\model;
//
//use app\common\model\Clazz;
//use app\common\model\CourseTimes;
//use app\common\model\Grade;
//use app\common\model\Section;
//
//class CourseScheduleModel extends \app\common\model\CourseSchedule
//{
//    use FormDateTrait;
//
//    private static function normalData()
//    {
//        return [
//            ['type' => 'select', 'require'=> true, 'name' => 'clazz_id', 'label' => '班级', 'options' => Clazz::order('id desc')->column('name', 'id')],
//            ['type' => 'select', 'require'=> true, 'name' => 'staff_id', 'label' => '老师', 'options' => StaffModel::getKV()],
//            ['type' => 'select', 'require'=> true, 'name' => 'times_id', 'label' => '课次', 'options' => CourseTimes::column('name', 'id')]
//        ];
//    }
//
//    public function makeFromData1($default_value = [])
//    {
//        $data = self::normalData();
//        $data[] = ['type' => 'number', 'name' => 'loop_interval', 'label' => '日间隔频率', 'info' => '输入数字；0或留空表示连续不间隔,每天都有；其他数字表示间隔，如1表示间隔1天'];
//        return self::mergeDefaultFormData($data, $default_value);
//    }
//
//    public function makeFromData2($default_value = [])
//    {
//        $data = self::normalData();
//        $data[] = ['type' => 'checkbox', 'name' => 'loop_values', 'label' => '选择星期', 'require'=> true,
//            'options' => [1 => '星期一', 2 => '星期二', 3 => '星期三', 4 => '星期四', 5 => '星期五', 6 => '星期六', 7 => '星期日'],
//        ];
//        $data[] = ['type' => 'number', 'name' => 'loop_interval', 'label' => '周间隔频率', 'info' => '输入数字；0或留空表示连续不间隔；其他数字表示间隔，如1表示间隔1周'];
//        return self::mergeDefaultFormData($data, $default_value);
//    }
//
//    public function makeFromData3($default_value = [])
//    {
//        $days = [];
//        for ($i = 1; $i< 32; $i ++) {
//            $days[$i] = $i . '日';
//        }
//        $data = self::normalData();
//        $data[] = ['type' => 'checkbox', 'name' => 'loop_values', 'label' => '选择日', 'require'=> true,
//            'options' => $days,
//        ];
//        $data[] = ['type' => 'number', 'name' => 'loop_interval', 'label' => '月间隔频率', 'info' => '输入数字；0或留空表示连续不间隔；其他数字表示间隔，如1表示间隔1月'];
//        return self::mergeDefaultFormData($data, $default_value);
//    }
//}