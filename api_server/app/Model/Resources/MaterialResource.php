<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class MaterialResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'quantity' => $this->quantity,
            'position' => $this->position,
            'price' => $this->price,
            'cost' => $this->cost,
            'status' => $this->status,
            'division_id' => new DivisionResource($this->divisionId),
            'num' => $this->whenPivotLoaded('product_materials', function () {
                return $this->pivot->quantity; // 商品关联物料的数量
            }),
        ];
    }
}