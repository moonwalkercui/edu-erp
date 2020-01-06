<?php
namespace App\Model\ApiMember;

use App\Exceptions\ApiException;
use App\Model\MemberFavorite;
use App\Service\Api\MemberService;

class MemberFavoriteModel extends MemberFavorite
{
//    use  OfMemberTrait;

    // 增加收藏项
    public static function add($target_id, $type = 1)
    {
        $target_type = 'Product'; // 多态关联里的type需要写类名
        $res = self::firstOrCreate([
            'member_mobile' => MemberService::getMember()->mobile,
            'target_id' => $target_id,
            'target_type' => $target_type
        ], ['created_at' => now()]);
        if ($res == false) throw new ApiException('收藏失败');
        return true;
    }
    // 学员端删除收藏
    public static function remove($id)
    {
        $res = self::ofMember()->where('id', $id)->delete();
        if ($res == false) throw new ApiException('取消收藏失败');
        return true;
    }
}
