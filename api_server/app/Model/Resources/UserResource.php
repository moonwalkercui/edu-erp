<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class UserResource extends Resource
{
    public function toArray($request)
    {
        return [
            'username' => $this->username,
            'avatar' => $this->avatar,
            'nickname' => $this->nickname,
            'name' => $this->name,
        ];
    }
}