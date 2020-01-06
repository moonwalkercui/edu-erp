<?php
namespace App\Model\Resources;
use Illuminate\Http\Resources\Json\Resource;
class OrderItemResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'item_type' =>  getStatusText($this->item_type,'morph_names'),
            'item_name' => $this->item_name,
            'deal_price' => $this->deal_price,
//            'courses_quantity' => $this->courses_quantity,
            'num' => $this->num,
            'remark' => $this->remark,
            'courses_quantity' => $this->courses_quantity,
        ];
    }
}