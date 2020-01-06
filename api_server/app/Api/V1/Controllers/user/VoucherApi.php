<?php
namespace App\Api\V1\Controllers\user;

use App\Model\MemberVoucher as MemberVoucherModel;
use App\Model\Voucher as VoucherModel;
use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class VoucherApi extends Base
{
    public function getAll(Request $request)
    {
        $where = [];
        $model = VoucherModel::where($where)->get();
        foreach ($model as $v) {
            $v->code = VoucherModel::encodeId($v->id);
        }
        return $this->fetch($model);
    }
    public function save(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
            'catch_price' => 'required',
            'price' => 'required',
            'invalid_at' => 'required',
        ]);

        $data['name'] = cleanXss($request->name);
        $data['catch_price'] = floatval($request->catch_price);
        $data['price'] = floatval($request->price);
        $data['image'] = cleanImagePrefix($request->image);
        $data['term'] = cleanXss($request->term);
        $data['created_at'] = now();
        $data['invalid_at'] = Carbon::parse($request->invalid_at);
        $data['status'] = getStatusValue($request->input('status'),'switch');

        if($request->filled('id')) {
            if(VoucherModel::where('id', $request->id )->update($data))
                return $this->success('已更新');
        } else {
            if(VoucherModel::create($data))
                return $this->success('已添加');
        }
        return $this->error('操作失败');
    }
    public function delete(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
        if(VoucherModel::destroy($input['id'])) return $this->success('已删除');
    }

    public function log(Request $request)
    {
        $where = [];

        if($request->filled('order_sn'))
            array_push($where, ['order_sn' , '=' , cleanXss($request->order_sn)]) ;

        if($request->filled('voucher'))
            array_push($where, ['voucher_id' , '=' , cleanXss($request->voucher)]) ;

        $model = MemberVoucherModel::with('voucher','member')->where($where)->orderBy('got_time','desc')->get();
        return $this->fetch($model);
    }

}