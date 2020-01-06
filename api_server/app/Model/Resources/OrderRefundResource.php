<?php
namespace App\Model\Resources;
use Illuminate\Http\Resources\Json\Resource;
class OrderRefundResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'order_id' => $this->order_id,
            'refund_sn' => $this->refund_sn,
            'total_fee' => $this->total_fee,
            'refund_fee' => $this->refund_fee,
            'created_time' => $this->created_time,
            'reject_reason' => $this->reject_reason,
            'status' => $this->status,
            'order' => new OrderResource($this->whenLoaded('order')),
        ];
    }
}