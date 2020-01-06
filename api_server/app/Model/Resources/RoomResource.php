<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class RoomResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'position' => $this->position,
            'division_id' => $this->divisionId,
        ];
    }
}