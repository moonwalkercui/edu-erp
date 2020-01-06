<?php

namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Gift as GiftModel;
use App\Model\GiftOrder as GiftOrderModel;
use App\Model\MemberProfile;
use App\Service\Api\UserService;
use Illuminate\Support\Facades\DB;

class GiftLogic
{
    public static function save($request, $condition = [])
    {
        $data['name'] = cleanXss($request->name);
        $data['category_id'] = intval($request->category);
        $data['points'] = intval($request->points);
        $data['image'] = cleanImagePrefix($request->image);
        $data['storage'] = intval($request->storage);
        $data['content'] = intval($request->content);
        $data['sort'] = intval($request->sort);
        $data['status'] = getStatusValue($request->input('status', '正常'), 'switch');

        $title = '修改礼品';
        if (empty($condition)) {
            $title = '添加礼品';
        }
        return BaseLogic::save('礼品管理', $title . ':' . $data['name'], '\App\Model\Gift', $data, $condition);
    }

    public static function delete($id)
    {
        if (GiftOrderModel::where('gift_id',$id)->count() > 0)
            throw new ApiException('该商品已经被兑换过，无法删除但可以禁用!');
        $model = GiftModel::find($id);
        return BaseLogic::delete('礼品管理', '删除礼品:' . $model->name, '\App\Model\Gift', ['id' => $id]);
    }

    public static function saveCategory($request, $condition = [])
    {
        $data['name'] = cleanXss($request->name);
        $data['sort'] = intval($request->sort);

        $title = '修改礼品分类';
        if (empty($condition)) {
            $title = '添加礼品分类';
        }
        return BaseLogic::save('礼品管理', $title . ':' . $data['name'], '\App\Model\GiftCategory', $data, $condition);
    }

    public static function approve($id)
    {
        return GiftOrderModel::where('id', $id)->update([
            'stage' => 1,
            'handle_manager' => UserService::getUserName(),
            'handle_at' => now(),
        ]);
    }
    public static function reject($id)
    {
        $log = GiftOrderModel::find($id);
        DB::beginTransaction();
        try {
            GiftOrderModel::where('id', $id)->update([
                'stage' => -1,
                'handle_manager' => UserService::getUserName(),
                'handle_at' => now(),
            ]);
            MemberProfile::where('member_id', $log->member_id)->increment('points', $log->points);
            DB::commit();
        }catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
        return true;
    }

}
