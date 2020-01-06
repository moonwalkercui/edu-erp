<?php

namespace App\Model;

use App\Exceptions\ApiException;

class Setting extends BaseModel
{
    protected $table = 'setting';
    public $timestamps = false;
    public function getOrgLogoAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function getOrgImgAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function getRedPacketImgAttribute($value)
    {
        return addImagePrefix($value);
    }
    public static function getSetting()
    {
        return self::where('code', API_PREFIX)->first();
    }
    public static function saveSetting($data)
    {
        if(self::getSetting()) {
            $res = self::where('code', API_PREFIX)->update($data);
        } else {
            $data['code'] = API_PREFIX;
            $res = self::insert($data);
        }
        if(false === $res) throw new ApiException('提交失败');
        return true;
    }
    public static function getOrgInfo()
    {
        $data = self::getSetting();
        return [
            'qrcode' => $data->qrcode,
            'org_name' => $data->org_name,
            'org_address' => $data->org_address,
            'org_img' => $data->org_img,
            'org_intro' => $data->org_intro,
            'org_phone' => $data->org_phone,
            'org_logo' => $data->org_logo,
            'wxpay' => $data->wxpay,
            'red_packet_img' => $data->red_packet_img,
            'red_packet_max' => $data->red_packet_max,
        ];
    }
    public static function getValue($field_name)
    {
        return self::getSetting()->{$field_name};
    }
    public static function checkWxPay()
    {
        return self::getValue('wxpay') == 0 ? false : true;
    }
}
