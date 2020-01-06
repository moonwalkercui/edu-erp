<?php
namespace App\Model\ApiMember;
use App\Service\Api\MemberService;

trait OfMemberTrait
{
    // 默认字段为member_mobile
    public function scopeOfMember($query, $field = 'member_mobile')
    {
        return $query->where($field, MemberService::getMember()->mobile);
    }

}