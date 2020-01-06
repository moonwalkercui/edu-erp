<?php
namespace App\Model\Resources;
use Illuminate\Http\Resources\Json\Resource;
class UserLogResourse extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'type' => $this->type,
            'username' => $this->username,
            'remark' => $this->remark,
            'created_at' => $this->created_at,
        ];
    }
}