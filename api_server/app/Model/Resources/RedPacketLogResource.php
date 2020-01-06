<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class RedPacketLogResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'member_id' => $this->member_id,
            'amount' => $this->amount,
            'order_id' => $this->order_id,
            'created_at' => $this->created_at,
            'expired_at' => $this->expired_at,
            'stage' => $this->stage,
            'handle_status' => $this->handle_status,
            'friend_id' => $this->friend_id,
            'member' =>  $this->whenLoaded('member'),
            'order' =>  $this->whenLoaded('order'),
            'referral' =>  $this->whenLoaded('referral'),
        ];
    }
}