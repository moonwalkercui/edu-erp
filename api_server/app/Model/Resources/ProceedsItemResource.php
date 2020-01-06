<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class ProceedsItemResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'money' => $this->money,
            'remark' => $this->remark,
            'status' => $this->status,
        ];
    }
}