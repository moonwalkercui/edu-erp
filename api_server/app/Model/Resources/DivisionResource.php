<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class DivisionResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'address' => $this->address,
            'image' => $this->image,
            'phone' => $this->phone,
            'lat' => $this->lat,
            'lng' => $this->lng,
            'sort' => $this->sort,
            'is_default' => $this->is_default,
            'region_id' => $this->region_id,
            'created_at' => $this->created_at->toDateString(),
        ];
    }
}