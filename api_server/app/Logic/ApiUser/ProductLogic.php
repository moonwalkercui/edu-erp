<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\MemberProduct;
use App\Model\Product as ProductModel;
use App\Model\ProductImage;
use App\Model\ProductSpecification;
use App\Service\Api\LogService;
use App\Service\Api\UserService;
use Illuminate\Http\Request;

class ProductLogic
{

    public static function save(Request $request , $condition = [])
    {
        $data['name'] = cleanXss($request->input('name'));
        $data['slogan'] = cleanXss($request->input('slogan'));
        $data['status'] = getStatusValue($request->input('status'),'product');
        $data['type'] = getStatusValue($request->input('type'),'product_type');
        $data['quantity'] = intval($request->quantity);
        $data['description'] = cleanXss($request->input('description'),true);
        $data['purpose'] = cleanXss($request->input('purpose'),true);
        $data['target'] = cleanXss($request->input('target'),true);
        $data['faq'] = cleanXss($request->input('faq'),true);
        $data['attention'] = cleanXss($request->input('attention'),true);
        $data['expired_at'] = $request->expire ? cleanXss($request->expire, false) : null;
        $data['recommend'] = $request->filled('recommend') ? getStatusValue($request->input('recommend'),'is_yes') : 0;
        $data_specs = cleanXss($request->input('specifications'));

        // 验证规格
        if (empty($data_specs)) throw new ApiException('规格设置错误');
        $specs = [];
        foreach ($data_specs as $k => $v){
            if(isset($v['id'])) $specs[$k]['id'] = intval($v['id']);
            $specs[$k]['name'] = cleanXss($v['name']);
            $specs[$k]['price'] = floatval($v['price']);
            $specs[$k]['courses_quantity'] = intval($v['courses_quantity']);
            if ($v['price'] < 0) throw new ApiException('价格设置错误');
            if ($v['courses_quantity'] <= 0) throw new ApiException('课时数设置错误');
        }
        $images = $request->input('images' , []);
        if(!empty($images)){
            $data['image'] = isset($images[0]['url']) ? cleanImagePrefix($images[0]['url']) : null;
        }
        $cate = $request->input('cate');
        if($cate){
            $data['category_ids'] = implode(',',$cate);
            $data['category_id'] = end($cate);
        }

        $date = $request->input('date');
        if($date){
            if($date[0] && $date[1]){
                $data['start_at'] = $date[0];
                $data['end_at'] = $date[1] . ' 23:59:59';
            }
        }else{
            $data['start_at'] = null;
            $data['end_at'] = null;
        }
        $data['user_id'] = UserService::getUserId();
        $division = $request->input('division');
        $properties = $request->input('properties',[]);

        $logo_title = empty($condition) ?  '添加产品:' : '修改产品' ;
//        var_dump($condition);
//        var_dump($images);die;
        return BaseLogic::save('产品管理',$logo_title.$data['name'] , '\App\Model\Product' , $data , $condition ,function($id) use ($specs, $division,$images,$properties){
            $product = ProductModel::find($id);
            // 更新产品与属性的关联
            ProductModel::saveProperties($product, $properties);
            // 新增规格
            ProductSpecification::handelSave($product, $specs);
            // 处理产品图
            ProductImage::handleSave($id, $images);
            // 门店和产品关联
            $model = ProductModel::find($id);
            $model->divisions()->sync($division);
        });
    }
    public static function saveGroupbuy(Request $request , $condition = [])
    {
//        'id' => 'required',
//         'is_groupbuy' => 'required',
//         'number' => 'required',
//         'start' => 'required',
//         'end' => 'required',
//         'groupbuy' => 'required',

        $data['is_groupbuy'] = getStatusValue($request->input('is_groupbuy'),'is_yes');
        $data['groupbuy_num'] = intval($request->number);
        $data['groupbuy_start'] = cleanXss($request->start);
        $data['groupbuy_end'] = cleanXss($request->end);

        $groupbuy = $request->groupbuy;

        var_dump($data);die;

        return BaseLogic::save('产品管理', '团购设置' , '\App\Model\Product' , $data , $condition , function($id) use ($groupbuy){

        });

    }
    public static function delete($id)
    {
        return BaseLogic::delete('产品管理', '删除课程' , '\App\Model\Product' ,['id' => $id ] );
    }
    // 下架
    public static function stop($request)
    {
        $ids = $request->input('ids');

        if(is_array($ids)){
            $product_ids = ProductSpecification::whereIn('id',$ids)->pluck('product_id');
            $res = ProductModel::whereIn('id',$product_ids)->update(['status' => 0]);
        }else{
            $product_id = ProductSpecification::where('id',$ids)->value('product_id');
            $res = ProductModel::where('id',$product_id)->update(['status' => 0]);
        }

        if ($res){
            LogService::userLog('产品管理','产品下架');
            return true;
        }
        else throw new ApiException('下架操作失败');
    }
    // 上架
    public static function open($request)
    {
        $ids = $request->input('ids');
        if(is_array($ids)){
            $product_ids = ProductSpecification::whereIn('id',$ids)->pluck('product_id');
            $res = ProductModel::whereIn('id',$product_ids)->update(['status' => 1]);
        }else{
            $product_id = ProductSpecification::where('id',$ids)->value('product_id');
            $res = ProductModel::where('id',$product_id)->update(['status' => 1]);
        }

        if ($res){
            LogService::userLog('产品管理','产品上架');
            return true;
        }
        else throw new ApiException('上架操作失败');
    }

    public static function editMemberProduct($post)
    {
        $total = intval($post['total']);
        $remaining = intval($post['remaining']);
        if($total < $remaining) throw new ApiException('剩余课时数不能大于总课时数');

        $res = MemberProduct::where([
            'member_id' => $post['member'],
            'product_id' => $post['product'],
        ])->update([
            'total_quantity' => $total,
            'remaining_quantity' => $remaining,
        ]);
        if(false !== $res) {
            LogService::userLog('课程管理', '调整学员的课时[' . $post['member']. ',' . $post['total'] . ',' . $post['remaining'] . ']');
            return true;
        } else {
            throw new ApiException('调整失败');
        }
    }
    public static function addMemberProduct($post)
    {
        $total = intval($post['total']);
        $remaining = intval($post['remaining']);
        if($total < $remaining) throw new ApiException('剩余课时数不能大于总课时数');

        $map = [
            'member_id' => $post['member'],
            'product_id' => $post['product'],
        ];
        if(MemberProduct::where($map)->first())
            throw new ApiException('该学员已经预定了该课程，请进行调整操作。');

        $res = MemberProduct::insert(array_merge( $map, [
            'total_quantity' => intval($post['total']),
            'remaining_quantity' => intval($post['remaining']),
        ]));
        if($res) {
            LogService::userLog('课程管理', '添加学员的课时[' . $post['member']. ',' . $post['total'] . ',' . $post['remaining'] . ']');
            return true;
        } else {
            throw new ApiException('添加失败');
        }
    }
}
