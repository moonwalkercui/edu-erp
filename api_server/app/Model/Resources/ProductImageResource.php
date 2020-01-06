<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class ProductImageResource extends Resource
{
    public function toArray($request)
    {
        return [
            'name' => $this->name,
            'url' => $this->url,
            'size' => $this->size,
        ];
    }
}