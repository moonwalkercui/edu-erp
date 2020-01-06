<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class ProductResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'slogan' => $this->slogan,
            'category_id' => $this->category_id,
            'category_ids' => $this->category_ids,
            'description' => $this->description,
            'courses_count' => $this->courses_count,
            'deal_count' => $this->deal_count,
            'members_count' => $this->members_count,
            'status' => $this->status,
            'type' => $this->type,
            'purpose' => $this->purpose,
            'target' => $this->target,
            'faq' => $this->faq,
            'attention' => $this->attention,
            'quantity' => $this->quantity,
            'image' => $this->image,
            'start_at' => $this->start_at,
            'end_at' => $this->end_at,
            'expired_at' => $this->expired_at,
            'recommend' => $this->recommend,
            'sort' => $this->sort,
            'images' => $this->whenLoaded('images'),
            'users' => $this->whenLoaded('users'),
            'divisions' => DivisionResource::collection($this->whenLoaded('divisions')),
            'courses' => CourseResource::collection($this->whenLoaded('courses')),
            'favorite' => new MemberFavoriteResource($this->whenLoaded('favorite')),
            'specs' => $this->whenLoaded('specs'),
            // 团购参数
            'is_groupbuy' => $this->is_groupbuy,
            'groupbuy_num' => $this->groupbuy_num,
            'groupbuy_count' => $this->groupbuy_count,
            'groupbuy' => $this->whenLoaded('groupbuy'),
            'groupbuy_start' => $this->groupbuy_start,
            'groupbuy_end' => $this->groupbuy_end,
            'red_packet_max' => $this->red_packet_max,
        ];
    }
}