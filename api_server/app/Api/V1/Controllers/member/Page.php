<?php
namespace App\Api\V1\Controllers\member;

use App\Model\Advertisement;
use App\Model\ApiMember\DivisionsModel;
use App\Model\CategoryTypes;
use App\Model\GiftCategory;
use App\Model\GoodsCategory;
use App\Model\Setting;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Page extends Base
{

    public function homeData()
    {
        $org_info = Setting::getOrgInfo();
        $products = \App\Model\Product::getRecommend();
        return $this->fetch(compact('org_info', 'products'));
    }

    public function courses()
    {
        $banners = Advertisement::getByPosition('coursesbanner');
        $regions = DivisionsModel::getRegionTree();
        $properties = CategoryTypes::whereHas('categories')->with('properties')->get();
        return $this->fetch(compact('regions','banners','properties'));
    }
    public function giftList(Request $request)
    {
        $where = [];
        if ($request->filled('category')) {
            $where['category_id'] = $request->category;
        }
        $list = \App\Model\Gift::where($where)->where('storage','>',0)->where('status',1)->orderBy('sort','desc')->paginate(20);
        return $this->fetch($list);
    }
    public function giftCategories(Request $request)
    {
        $list = GiftCategory::orderBy('sort','desc')->get();
        return $this->fetch($list);
    }
    public function goodsList(Request $request)
    {
        $list = GoodsCategory::with(['goods' => function($query){
            $query->where('status',1)->orderBy('sort','desc');
        }])->orderBy('sort','desc')->get();
        return $this->fetch($list);
    }
    public function zone(Request $request)
    {
        $list = GoodsCategory::with(['goods' => function($query){
            $query->where('status',1)->orderBy('sort','desc');
        }])->orderBy('sort','desc')->get();
        return $this->fetch($list);
    }
}