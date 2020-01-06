<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class MemberFavoriteResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'target_id' => $this->target_id,
            'target_type' => $this->target_type,
            'target' => new ProductResource($this->whenLoaded('target')),
            'created_at' => $this->created_at,
        ];
    }
}