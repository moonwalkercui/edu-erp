<?php

namespace app\common\model;

class User extends BaseModel
{

    protected $table = 'user';
    static $category;

    function account()
    {
        return $this->hasOne(UserAccount::class, 'user_id')->bind(['point']);
    }

    public function getStatusTextAttr($value, $data)
    {
        return $this->transferStatusText($data['status'], 'normal');
    }

    function activateCard()
    {
        return $this->hasMany(CardStatus::class, 'user_id')->where('type', CardTypes::TYPE_ACTIVATE);
    }

    function consumeCard()
    {
        return $this->hasMany(CardStatus::class, 'user_id')->where('type', CardTypes::TYPE_CONSUME);
    }

    function couponCard()
    {
        return $this->hasMany(CardStatus::class, 'user_id')->where('type', CardTypes::TYPE_COUPON);
    }

    public function getMajorAttr($value)
    {
        $items = explode(",", $value);
        if (isset(self::$category)) {
            $cate_list = self::$category;
        } else {
            $cate_list = self::$category = CaseCategory::column("name", 'id');
        }
        foreach ($items as &$i) {
            if (isset($cate_list[$i])) $i = $cate_list[$i];
        }
        return $items;
    }

    // 级别
    public function getGradeTextAttr($value, $data)
    {
        return $this->transferStatusText($data['grade'], 'lawyer_grade');
    }

    // 从业时间
    public function getStartYearTextAttr($value, $data)
    {
        if ($data['start_year'] == false) return 1;
        return date("Y") - date("Y", strtotime($data['start_year'])) + 1;
    }

    public function getGenderTextAttr($value, $data)
    {
        return $this->transferStatusText($data['gender'], 'gender');
    }

    public function getAgeAttr($value, $data)
    {
        if ($data['birthday'] == false) return 1;
        return date("Y") - date("Y", strtotime($data['birthday']));
    }

    static function addOne()
    {
        $user_id = self::insertGetId([
            'reg_time' => now(),
            'status' => 1,
        ]);
        UserAccount::insert([
            'user_id' => $user_id
        ]);
        return $user_id;
    }

    static function findOrCreateByMob($mobile)
    {
        $map = compact('mobile');
        $find = self::where($map)->find();
        if ($find) {
            $user_id = $find['id'];
        } else {
            $map['reg_time'] = now();
            $user_id = self::insertGetId($map);
            if(!$user_id) exception('创建失败');
            UserAccount::insert([ 'user_id' => $user_id  ]);
        }
        return $user_id;
    }
}