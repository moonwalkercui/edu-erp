<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class ClassResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'sn' => $this->sn,
            'description' => $this->description,
            'name' => $this->name,
            'created_at' => $this->created_at->toDateString(),
            'members_count' => $this->members_count,
            'courses_count' => $this->courses_count,
            'division_id' => new DivisionResource($this->whenLoaded('division'))
        ];
    }
}