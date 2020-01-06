<?php
namespace App\Model\Resources;
use Illuminate\Http\Resources\Json\Resource;
class NotificationResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'content' => $this->content,
            'image' => $this->image,
            'is_popup' => $this->is_popup,
            'is_top' => $this->is_top,
            'sort' => $this->sort,
            'title' => $this->title,
            'created_at' => $this->created_at->toDatetimeString(),
            'updated_at' => $this->updated_at->toDatetimeString(),
        ];
    }
}