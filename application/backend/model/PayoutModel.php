<?php

namespace app\backend\model;

use app\common\model\PayoutType;

class PayoutModel extends \app\common\model\Payout
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'radio',
                'name' => 'status',
                'label' => '是否批准',
                'options' => config('status.switch'),
            ],
            [
                'type' => 'textarea',
                'name' => 'verify_remark',
                'label' => '审批说明',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function makeFromData2($default_value = [])
    {
        $data = [
            [
                'type' => 'number',
                'name' => 'money',
                'label' => '请款金额',
            ],
            [
                'type' => 'input',
                'name' => 'payee',
                'label' => '收款人',
            ],
            [
                'type' => 'input',
                'name' => 'payee_account',
                'label' => '收款账号',
            ],
            [
                'type' => 'select',
                'name' => 'type_id',
                'label' => '请款类型',
                'options' => PayoutType::column('name','id'),
            ],
            [
                'type' => 'upimgs',
                'name' => 'images',
                'label' => '附图',
                'size' => 6
            ],
            [
                'type' => 'textarea',
                'name' => 'reason',
                'label' => '请款内容',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

    public function makeFromData3($default_value = [])
    {
        $data = [
            [
                'type' => 'textarea',
                'name' => 'paid_remark',
                'label' => '支付说明',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}