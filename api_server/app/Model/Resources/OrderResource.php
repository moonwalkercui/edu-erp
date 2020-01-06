<?php
namespace App\Model\Resources;
use Illuminate\Http\Resources\Json\Resource;
class OrderResource extends Resource
{
    public function toArray($request)
    {
        return [
            'sn' => $this->sn,
            'member' => new MemberResource($this->whenLoaded('member')),
            'user_name' => $this->user_name,
            'total_price' => $this->total_price,
            'type' => $this->type,
            'status' => $this->status,
            'remark' => $this->remark,
            'pay_type' => $this->pay_type,
            'pay_sn' => $this->pay_sn,
            'pay_money' => $this->pay_money,
            'paid_at' => $this->paid_at,
            'created_at' => $this->created_at->toDatetimeString(),
            'items' => OrderItemResource::collection($this->whenLoaded('items')),
        ];
    }
}