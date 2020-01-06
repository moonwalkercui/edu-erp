<?php
namespace App\Model\Resources;
use Carbon\Carbon;
use Illuminate\Http\Resources\Json\Resource;
class MemberProductResource extends Resource
{
    public function toArray($request)
    {
        return [
            'member_id' => $this->member_id,
            'product_id' => $this->product_id,
            'total_quantity' => $this->total_quantity,
            'remaining_quantity' => $this->remaining_quantity,
            'member' => $this->whenLoaded('member'),
            'product' => $this->whenLoaded('product'),
        ];
    }
}