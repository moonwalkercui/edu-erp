<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class ProductSpecificationResource extends Resource
{
    public function toArray($request)
    {
        $product = $this->whenLoaded('product');
        return [
            'id' => $this->id,
            'product_id' => $this->product_id,
            'price' => $this->price,
            'quantity' => $this->quantity,
            'courses_count' => $this->courses_count,
            'sn' => $this->sn,
            'name' => $product ? $this->product->name . ' ' . $this->name : null,
            'slogan' =>  $product ? $this->product->slogan : null,
            'image' =>  $product ? $this->product->image : null,
            'start_at' =>  $product ? $this->product->start_at : null,
            'end_at' =>  $product ? $this->product->end_at : null,
            'type' =>  $product ? $this->product->type : null,
            'status' =>  $this->whenLoaded('status') ? $this->product->status : null,
            'order_remark' =>  $product ? $this->product->order_remark : null,
            'product_link' => new ProductLinkMaterialsResource($this->whenLoaded('product')),
        ];
    }
}