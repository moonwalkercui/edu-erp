<?php
namespace app\backend\model;
class AttachmentModel extends \app\common\model\Attachment
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'select',
                'name' => 'family_name',
                'label' => 'Family Name',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}