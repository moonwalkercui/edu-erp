<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class CourseResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'sn' => $this->sn,
            'class_name' => $this->class_name,
            'room_name' => $this->room_name,
            'title' => $this->title,
            'product_id' => $this->product_id,
            'product_spec_id' => $this->product_spec_id,
//            'product_name' => $this->product_name,
            'quantity_member' => $this->quantity_member,
            'username' => $this->username,
            'user_name' => $this->user_name,
            'status' => $this->status,
            'date' => $this->date,
            'start_at' => $this->start_at ? substr($this->start_at,0,-3) : '',
            'end_at' => $this->end_at ? substr($this->end_at,0,-3) : '',
            'signs_count' => $this->signs_count,
            'deal_count' => $this->deal_count,
            'homework_count' => $this->homework_count,
            'clash_ids' => $this->clash_ids,
            'user' => new UserResource($this->whenLoaded('user')),
            'division_name' => $this->whenLoaded('division') ? $this->division->name : '',
            'product_name' => $this->whenLoaded('product') ? $this->product->name : '',
        ];
    }
}