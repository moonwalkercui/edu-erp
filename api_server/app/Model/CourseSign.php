<?php

namespace App\Model;

use App\Exceptions\ApiException;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Support\Facades\DB;

class CourseSign extends BaseModel
{
    use SoftDeletes;
    protected $table='course_signs';
    public $timestamps = false;
    protected $fillable = [
        'member_mobile',
        'member_name',
        'course_id'
    ];

    public function getHomeworkAttribute($value)
    {
        return getStatusText($value,'course_homework');
    }
    // 后期实现分表逻辑
//    protected $dispatchesEvents = [
//        'created' => CourseSignCreated::class,
//    ];
    // 课程的成交人数
    public function deal()
    {
        return $this->hasMany('App\Model\MemberProduct','product_id','product_id');
    }
    public function course()
    {
        return $this->belongsTo('App\Model\Course','course_id');
    }
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile','member_id','member_id')->select('member_id', 'name', 'nick_name', 'mobile');
    }
    public function product()
    {
        return $this->belongsTo('App\Model\Product','product_id');
    }
    // 获取可签到课时的点 参数$org_id
    public static function getSignTimeLimit()
    {
        // 可签到时间，需要完成定制化 todo
//         见配置 'sign_limit'             => [
//            0 => '半小时内上课和已上课的所有课时（默认）',
//            1 => '1小时内上课和已上课的所有课时',
//            2 => '2小时内上课和已上课的所有课时',
//            3 => '当天上课和已上课的所有课时',
//        ],
        $sign_limit = 0;
        switch ($sign_limit) {
            case 1: $time = Carbon::now()->addMinutes(60); break;
            case 2: $time = Carbon::now()->addMinutes(120); break;
            case 3: $time = Carbon::tomorrow(); break;
            default: $time = Carbon::now()->addMinutes(30);
        }
        return $time;
    }

    // 根据订单号，给学员生成签到记录
    public static function generateByOrderId($order_id, $member_id)
    {
        $product_ids = OrderItem::where('order_id', $order_id)->pluck('item_id');
        return self::generateByCourses(
            Course::whereIn('product_id', $product_ids)->get()->toArray(),
            $member_id
        );
    }
    private static function generateByCourses($courses_array,$member_id)
    {
        if(!empty($courses_array)) {
            $insert = [];
            foreach ($courses_array as $course) {
                $insert[] = [
                    'member_id' => $member_id,
                    'course_id' => $course['id'],
                    'product_id' => $course['product_id'],
                    'created_at' => now(),
                ];
            }
            return self::insert($insert);
        } else {
            return null;
        }
    }
    // 发放小星星
    public static function givePoint($uname, $id, $point)
    {
        if ($point < 1 || $point > 5) {
            throw new ApiException('小星星数量错误');
        }
        $course_sign = self::find($id);
        if(Setting::getValue('only_award_others') == 0 && $course_sign->course->username != $uname)
            throw new ApiException('仅本课老师可操作');
        if($course_sign->points > 0) throw new ApiException('不能重复奖励');
        $course_sign->points = $point;
        $course_sign->save();

        MemberProfile::where('member_id', $course_sign->member_id)->increment('points', $point);

        return true;
    }
    public static function allograph($member_id,$course_id,$username)
    {
        $course = Course::where('id', $course_id)->first();
        if($course == null) throw new ApiException('失败:无效课时');
        if(Setting::getValue('only_sign_myself') == 1 && $username != $course->username)
            throw new ApiException('您不能代签别人的课');
        return self::handleSign($member_id, $course_id, $course->product_id, $username);
    }
    public static function handleSign($member_id, $course_id, $product_id, $username=null)
    {
        if(self::where([
            'member_id' => $member_id,
            'course_id' => $course_id,
        ])->first()) throw new ApiException('不能重复签到');

        $member_product = MemberProduct::where([
            'member_id' => $member_id,
            'product_id' => $product_id,
        ])->first();
        if ($member_product->remaining_quantity <= 0) throw new ApiException('课时数不足');

        DB::beginTransaction();
        try {
            self::insert([
                'member_id' => $member_id,
                'course_id' => $course_id,
                'product_id' => $product_id,
                'sign_at' => now(),
                'sign_username' => $username,
            ]);
            MemberProduct::decQuantity($member_id, $product_id);
            DB::commit();
        }catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
        return true;
    }
}
