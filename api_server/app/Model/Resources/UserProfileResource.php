<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class UserProfileResource extends Resource
{
    public function toArray($request)
    {
        return [
            'username' => $this->username,
            'name' => $this->name,
            'phone' => $this->phone,
            'email' => $this->email,
            'intro' => $this->intro,
            'avatar' => $this->avatar,
//            'is_manager' => $this->is_manager,
            'status' => $this->status,
        ];
    }
}