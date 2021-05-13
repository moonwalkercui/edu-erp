<?php
namespace app\common\model;

class Zone extends BaseModel
{
    use BelongsToStudent,BelongsToClazz;
    protected $table = 'zone';
    // 评论
    function comments()
    {
        return $this->hasMany(ZoneComment::class, 'zone_id');
    }
    function likes()
    {
        return $this->hasMany(ZoneLike::class, 'zone_id');
    }
    // 评价人
    function verifier()
    {
        return $this->belongsTo(Staff::class, 'verifier_id')->bind(['verifier_name' => 'name','verifier_photo' => 'photo']);
    }
    static function getList($where = [], $student_id = null, $order = 'verify_content, verify_time desc, id desc')
    {
        return Zone::with('comments.publisher,studentBind,verifier')->withCount('likes')->where($where)->order($order)->paginate(20)->each(function($item) use ($student_id){
            // 1 文字 2 语音 3 图片
            if($item['type'] == 3) {
                $item['attach'] = explode(',', $item['attach']);
            }
            foreach ($item->comments as $c) {
                $c->is_my = ($student_id && $c['publisher_id'] == $student_id) ? 1 : 0;
            }
        });
    }
    static function addOne($student_id, $task_id, $content, $attach, $voice)
    {
        $data = compact('student_id','voice','content','attach','task_id');
        $data['status'] = 1;
        $data['clazz_id'] = Student::where('id', $student_id)->value('clazz_id');
        $data['add_time'] = now();
        return self::insert($data);
    }
    static function verify($id, $staff_id, $content, $score)
    {
        $find = self::find($id);
        $find->verifier_id = $staff_id;
        $find->verify_content = $content;
        $find->verify_score = $score;
        $find->verify_time = now();
        $find->status = 1;
        $account = StudentAccount::getByStudentId($find['student_id']);
        $account->point += $score;

        if($account->save() && $find->save()) {
            StudentAccountLog::addOne($find['student_id'], StudentAccountLog::TYPE['point'], $score, "作业点评");

            ClazzEvent::addOne(
                session('login_name') . "老师更新了积分",
                \app\common\model\Student::where('id', $find['student_id'])->value('clazz_id'),
                session('login_id'),
                ClazzEvent::TYPE_POINT,
                $id,
                $find['student_id']
            );
            return true;
        } else return false;
    }
}