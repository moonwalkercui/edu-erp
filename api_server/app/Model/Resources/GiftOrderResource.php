<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class GiftOrderResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'gift_num' => $this->gift_num,
            'points' => $this->points,
            'created_at' => $this->created_at,
            'stage' => $this->stage,
            'handle_manager' => $this->handle_manager,
            'handle_at' => $this->handle_at,
            'gift' => $this->whenLoaded('gift'),
            'member' => $this->whenLoaded('member'),
        ];
    }
}