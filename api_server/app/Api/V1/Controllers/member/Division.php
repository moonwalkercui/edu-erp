<?php
namespace App\Api\V1\Controllers\member;

use App\Model\ApiMember\DivisionsModel;
use App\Model\MemberProfile;
use App\Model\Region;
use App\Model\Resources\DivisionResource;
use App\Model\Resources\ProductResource;
use App\Model\Product as ProductModel;
use App\Service\Api\MemberService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Division extends Base
{
    /**
     * division tree
     * @Get("/mdivision/tree")
     */
    public function tree()
    {
        $regions = DivisionsModel::getRegionTree();
        return $this->fetch($regions);
    }
    /**
     * division列表
     * @Get("/mdivision/list")
     */
    public function getAll(Request $request)
    {

        $where = [];
        if($request->filled('region')){
            $where['region_id'] = $request->region;
        }
        $data = DivisionsModel::where($where)->orderBy('sort','desc')->get();
        foreach ($data as $v) {
            $v->is_colleted = 0;
        }
        if (MemberService::isLoggedIn()) {
            $collection_ids = MemberProfile::getCollectionIds(MemberService::getMemberId())->toArray();
            foreach ($data as $v) {
                if(in_array($v->id, $collection_ids)){
                    $v->is_colleted = 1;
                }
            }
        }
        $data = $data->sortByDesc('is_colleted');

        return $this->fetch( $data->values()->all() );

    }
    /**
     * division
     * @Get("/mdivision/detail")
     */
    public function detail(Request $request)
    {
        $data = DivisionsModel::find($request->input('id'));
        return $this->fetch($data);
    }

}