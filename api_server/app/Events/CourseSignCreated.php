<?php

namespace App\Events;

use App\Model\CourseSign;
use Illuminate\Queue\SerializesModels;
use Illuminate\Foundation\Events\Dispatchable;
use Illuminate\Broadcasting\InteractsWithSockets;

// 水平分表监听事件
class CourseSignCreated
{
    use Dispatchable, InteractsWithSockets, SerializesModels;

    public function __construct(CourseSign $model)
    {
//        $count = $model->count();
//        echo $count.'---';
//        DB::table('course_signs')->exists();
//        var_dump(DB::table('course_signs')->getGrammar());die;
//        if ($count > 5) {
//            $name = 'course_signs_' . date('YmdH', time());
//            DB::statement("create table {$name} like course_signs_0");
////            DB::statement("alter table test engine=mrg_myisam union=(test1,test2,test3 ) insert_method=last;");
//            DB::statement("alter table course_signs union=(test1,test2,test3)");
//        }
    }
    public function broadcastOn()
    {
//        return new PrivateChannel('channel-name');
    }
}
