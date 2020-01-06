<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class MaterialLogResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'username' => $this->username,
            'user_name' => $this->user_name,
            'material_name' => $this->material_name,
            'quantity' => $this->quantity,
            'money' => $this->money,
            'remark' => $this->remark,
            'status' => $this->status,
            'created_at' => $this->created_at,
            'handle_remark' => $this->handle_remark,
            'handle_at' => $this->handle_at,
            'handle_username' => $this->handle_username,
        ];
    }
}