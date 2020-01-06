<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;

class ProceedsMoneyResource extends Resource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'money_received' => $this->money_received,
            'user_receiver' => $this->user_receiver,
            'member_payer' => $this->member_payer,
            'payment_mode' => $this->payment_mode,
            'remark' => $this->remark,
            'received_at' => $this->received_at,
            'verify_status' => getStatusText($this->verify_status,'proceeds_verify'),
            'verify_user' => $this->verify_user,
            'user_receiver_name' => $this->user_receiver_name,
            'member_payer_name' => $this->member_payer_name,
            'verify_user_name' => $this->verify_user_name,
            'verify_at' => $this->verify_at,
        ];
    }
}