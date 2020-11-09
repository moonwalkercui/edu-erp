<?php
namespace app\backend\model;

class StaffOffdutyModel extends \app\common\model\StaffOffduty
{
    use FormDateTrait;

    // 审批
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
                'label' => '说明',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    // 请假
    public function makeFromData2($default_value = [])
    {
        $data = [
            [
                'type' => 'select',
                'name' => 'type_id',
                'label' => '请假类型',
                'options' => OffdutyTypeModel::column('name','id'),
            ],
            [
                'type' => 'textarea',
                'name' => 'reason',
                'label' => '请假原因',
            ],
            [ 'type' => 'date', 'name' => 'start_date', 'label' => '开始日期'  ],
            [ 'type' => 'input', 'name' => 'start_date_time', 'label' => '开始点钟', 'info'=>'可填0-24的数字，不填表示全天'  ],
            [ 'type' => 'date', 'name' => 'end_date', 'label' => '结束日期'],
            [ 'type' => 'input', 'name' => 'end_date_time', 'label' => '结束点钟', 'info'=>'可填0-24的数字，不填表示全天' ],
            [
                'type' => 'upimgs',
                'name' => 'images',
                'label' => '附图',
                'size' => 6
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}