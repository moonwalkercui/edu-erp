<?php
namespace App\Model\Resources;
use Illuminate\Http\Resources\Json\Resource;
class ZoneResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'uid' => $this->content,
            'user' => new UserResource($this->whenLoaded('user')),
            'avatar' => $this->avatar,
            'content' => $this->content,
            'images' => $this->images,
            'edit_time' => $this->edit_time,
            'logs_count' => $this->logs_count,
        ];
    }
}