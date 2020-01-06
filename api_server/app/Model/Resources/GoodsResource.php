<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class GoodsResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'category_id' => $this->category_id,
            'name' => $this->name,
            'storage' => $this->storage,
            'sale_quantity' => $this->sale_quantity,
            'price' => $this->price,
            'image' => $this->image,
            'status' => $this->status,
            'category' => $this->whenLoaded('category'),
        ];
    }
}