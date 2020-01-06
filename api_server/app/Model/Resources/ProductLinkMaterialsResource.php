<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

// 这个是产品表的资源，主要用户查询产品关联的物料列表
class ProductLinkMaterialsResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
//            'materials' => MaterialResource::collection($this->whenLoaded('materials')),
        ];
    }
}