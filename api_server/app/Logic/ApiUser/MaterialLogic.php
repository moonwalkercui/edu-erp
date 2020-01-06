<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\MaterialLog as MaterialLogModel;
use App\Model\Material as MaterialModel;
use App\Service\Api\LogService;
use App\Service\Api\UserService;

class MaterialLogic
{
    public static function save($request , $condition = [])
    {
        $data['name'] = cleanXss($request->input('name'));
        $data['division_id'] = intval($request->input('internal'));
        $data['position'] = cleanXss($request->input('position'));
        $data['price'] = floatval($request->input('price',0));
        $data['status'] = getStatusValue($request->input('status','正常'),'switch');
        $title = '修改物料';
        if(empty($condition)){
            $data['quantity'] = intval($request->input('quantity',0));
            $title = '添加物料';
        }
        return BaseLogic::save('物料管理',$title.':'.$data['name'] , '\App\Model\Material' , $data , $condition );
    }
    public static function delete($id)
    {
        $material = MaterialModel::find($id);
        return BaseLogic::delete('物料管理','删除物料:'. $material->name  ,'\App\Model\Material' ,['id' => $id ] , function($id){
            // 删除的物料申请，全部驳回
            $log_ids = MaterialLogModel::where('material_id',$id)->pluck('id');
            self::handleApplication($log_ids,-1,'物料被删除');
        });
    }
    // 申请物料
    public static function apply($id, $quantity, $money = 0, $remark)
    {
        $material = MaterialModel::find($id);
        if( $quantity > $material->quantity ) throw new ApiException('库存不足');
        return MaterialLogModel::makeLog($material , $quantity * (-1) , $money , 0 , $remark);
    }
    // 变更物料库存数量 状态 [ 0=>'报单审核中', 1=>'出库中', 2=>'已出库', -1=>'被驳回' , 9=>'已入库']
    // ID，数量，出库收益，备注
    public static function changeQuantity($id, $number, $money = 0 ,$remark = '')
    {
        $material = MaterialModel::find($id);
        if($material == null) throw new ApiException('未查询到库存信息');

        $residue = $material->quantity + $number;

        if($number < 0){
            $status = 1;
            if($residue <= 0) throw new ApiException('物料库存不足');
        }else{
            $status = 9;
        }
        $title = getStatusText( $status , 'material');
        if($remark){
            $title .= ':' . $remark;
        }
        return BaseLogic::save('物料管理',$title, '\App\Model\Material' , [
            'quantity' => $residue
        ] , ['id' => $id] ,function($id) use ($material, $number, $money, $status , $title){
            MaterialLogModel::makeLog($material , $number , $money , $status , $title);
        });
    }
    // 批量处理申请
    public static function handleApplication($ids,$status,$remark='')
    {
        $res = 0;
        if($status == 1){
            // 审核通过
            MaterialLogModel::whereIn('id' , $ids )->where('status',0)->get()->each(function ($item, $key) use ( &$res,$remark,$status) {
                $material = MaterialModel::where('id',$item->material_id)->first();
                if($item->quantity >= 0){
                    throw new ApiException('申请数量有误');
                } elseif ( $material->quantity >= $item->quantity*(-1)) {
                    // 如果库存量足够
                    $material->quantity -= $item->quantity*(-1);
                    $res += $material->save();

                    $item->status = $status;
                    $item->handle_remark = $remark;
                    $item->handle_username = UserService::getUserName();
                    $item->save();
                }else{
                    throw new ApiException('库存不足');
                }
            });
        }elseif ($status == -1){
            // 审核驳回
            $res = MaterialLogModel::whereIn('id' , $ids)->where('status',0)->update([
                'status' => $status,
                'handle_remark' => $remark,
                'handle_username' => UserService::getUserName(),
            ]);
        }else{
            throw new ApiException('操作状态错误');
        }

        if($res){
            LogService::userLog('物料管理', getStatusText($status,'material').' '.$remark);
            return $res;
        }else{
            return false;
        }
    }
}
