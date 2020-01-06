<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class CategoryResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'parent_id' => $this->parent_id,
            'products' => ProductResource::collection($this->whenLoaded('products')),
            'properties' => $this->whenLoaded('properties')
        ];
    }
}