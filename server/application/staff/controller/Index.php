<?php
namespace app\staff\controller;

use app\common\model\Course;
use app\common\model\Payout;
use app\common\model\PayoutType;
use app\common\model\Staff;
use think\facade\Log;

class Index extends Base
{
    function index()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('请登录',[], 2);
        $staff_id = session('login_id');
        $data['staff'] = Staff::field('name,photo')->find($staff_id);
        $clazz = \app\common\model\Clazz::getByStaffId($staff_id);
        $clazz_ids = [];
        foreach ($clazz as $c) {
            $clazz_ids[] = $c['id'];
        }
        $data['zone_count'] = empty($clazz_ids) ? 0 : \app\common\model\Zone::where('clazz_id','in', $clazz_ids)->where('status',0)->count();

        return $this->dataJson($data);
    }

//    // 发布课表
//    function course()
//    {
//        echo '<h1>课表</h1>';
//    }

    function payoutTypes()
    {
        $data = PayoutType::column('name');
        return $this->dataJson($data);
    }
    function payoutApply()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');

        $data['staff_id'] = $staff_id;
        $data['money'] = input('money/f');
        $data['payee'] = input('payee');
        $data['payee_account'] = input('payee_account');
        $data['images'] = empty($images) ? '' : implode(',', $images);
        $data['reason'] = input('reason');
        $data['type_id'] = PayoutType::where("name", input('type'))->value('id');
        $data['status'] = 0;
        $data['add_time'] = now();

        if($data['money'] <= 0 ) return $this->errorJson('金额有误');
        if(!$data['reason']) return $this->errorJson('缺少请款内容');
        if(!$data['payee']) return $this->errorJson('缺少收款人');
        if(!$data['payee_account']) return $this->errorJson('缺少收款账号');

        try{
            Payout::insert($data);
        }catch (\Exception $e) {
            return $this->errorJson($e->getMessage());
        }
        return $this->successJson('请款提交成功');

    }
    function payoutHistory()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
        $data = Payout::with('type')->where(compact('staff_id'))->append(['status_text'])->order('id desc')->paginate(20);
        return $this->dataJson($data);
    }
    function calendar()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
//        Log::error("staff_id");
//        Log::error($staff_id);
        $date = ($d = input('date')) ? $d : date('Y-m-d');
        $where[] = ['date','=', $date ];

        $model = Course::with('clazz,staff,times,section')->where($where);

        if(input('only_me') == 1) {
            $model->where(function ($query) use($staff_id) {
                $query->where('staff_id|assistant_id', $staff_id);
                $clazz = \app\common\model\Clazz::getByStaffId($staff_id)->toArray();
                if(!empty($clazz)) {
                    $query->whereOr('clazz_id', 'in', array_column($clazz, 'id'));
                }
            });
        }

        $list = $model->order('times_id')->select();
        return $this->dataJson($list);
    }
}