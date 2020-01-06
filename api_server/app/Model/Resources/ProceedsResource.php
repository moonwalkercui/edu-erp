<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class ProceedsResource extends Resource
{
    public function toArray($request)
    {
        return [
            'sn' => $this->sn,
            'money_receivable' => $this->money_receivable,
            'pay_type' => $this->pay_type,
            'created_at' => $this->created_at->toDateTimeString(),
            'updated_at' => $this->updated_at->toDateTimeString(),
            'paid_at' =>  $this->paid_at,
            'confirmed_at' => $this->confirmed_at,
            'user_name' => $this->user_name,
            'target_type' => getStatusText($this->target_type,'morph_names'),
//            'money_received' => $this->whenLoaded('items')? $this->whenLoaded('items')->sum('money_received') : 0.00 ,
            'member' => $this->whenLoaded('member'),
            'division' => $this->whenLoaded('division'),
            'log' => $this->whenLoaded('log'),
            'remark' => $this->remark,
            'status' => $this->status,
        ];
    }
}