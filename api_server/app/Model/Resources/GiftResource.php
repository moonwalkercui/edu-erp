<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class GiftResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'image' => $this->image,
            'category_id' => $this->category_id,
            'content' => $this->content,
            'storage' => $this->storage,
            'status' => $this->status,
            'points' => $this->points,
            'exchange_num' => $this->exchange_num,
            'sort' => $this->sort,
            'category' => $this->whenLoaded('category'),
        ];
    }
}