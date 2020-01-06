<?php
namespace App\Api\V1\Controllers\member;

use App\Model\ApiMember\MemberFavoriteModel;
use App\Model\Resources\MemberFavoriteResource;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Favorite extends Base
{
    /**
     * 我的收藏
     *
     * @Get("/mfavorite/mine")
     */
    public function mine()
    {
        return MemberFavoriteResource::collection(MemberFavoriteModel::with('target')->get());
    }

    /**
     * 收藏
     *
     * @Get("/mfavorite/add")
     * @Parameters({
     *      @Parameter("id", description="收藏对象id", required=true),
     *      @Parameter("type", description="类型1课程2老师", required=false, default=1),
     * })
     */
    public function add(Request $request)
    {
        $this->validateParam($input = $request->all() , ['id' => 'required']);
        if ($request->filled('type')) {
            $type = intval($request->type);
        } else {
            $type = 1;
        }
        if (MemberFavoriteModel::add(intval($request->id), $type))
            return $this->success('收藏成功');
    }
    /**
     * 取消收藏
     *
     * @Get("/mfavorite/remove")
     * @Parameters({
     *      @Parameter("id", description="收藏对象id", required=true),
     * })
     */
    public function remove(Request $request)
    {
        $this->validateParam($input = $request->all() , ['id' => 'required']);
        if (MemberFavoriteModel::remove(intval($request->id)))
            return $this->success('已取消');
    }

}