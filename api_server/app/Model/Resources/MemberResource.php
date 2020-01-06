<?php
namespace App\Model\Resources;
use Carbon\Carbon;
use Illuminate\Http\Resources\Json\Resource;
class MemberResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'member_id' => $this->member_id,
            'mobile' => $this->mobile,
            'name' => $this->name,
            'avatar' => $this->avatar,
            'nick_name' => $this->nick_name,
            'email' => $this->email,
            'industry' => $this->industry,
            'occupation' => $this->occupation,
            'source' => $this->source,
            'deal_course' => $this->deal_course,
            'remaining_course' => $this->remaining_course,
            'signs_count' => $this->signs_count,
            'points' => $this->points,
            'salesman' => $this->whenLoaded('salesman'),
            'created_at' => Carbon::parse($this->created_at)->toDateString(),
        ];
    }
}