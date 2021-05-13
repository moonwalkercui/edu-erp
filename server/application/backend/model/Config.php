<?php

namespace app\backend\model;

class Config extends \app\common\model\Config
{
    use FormDateTrait;
    static $maps = [
        'system' => [
            'title' => '系统设置',
            'list' => [
                ['label' => '站点域名', 'name' => 'site_domain', 'type' => 'input'],
                ['label' => '站点名称', 'name' => 'site_name', 'type' => 'input'],
                ['label' => '站点LOGO', 'name' => 'site_logo', 'type' => 'image'],
                ['label' => 'SEO关键字', 'name' => 'seo_keyword', 'type' => 'input'],
                ['label' => 'SEO描述', 'name' => 'seo_description', 'type' => 'input'],
                ['label' => 'ICP备案号', 'name' => 'icp', 'type' => 'input'],
                ['label' => '站长统计代码', 'name' => 'analytics', 'type' => 'textarea'],
            ]
        ],
        'attendance' => [
            'title' => '考勤设置',
            'list' => [
                ['label' => '签到开始时间', 'name' => 'sign_am_start', 'type' => 'input'],
                ['label' => '签到结束时间', 'name' => 'sign_am_end', 'type' => 'input'],
                ['label' => '签退开始时间', 'name' => 'sign_pm_start', 'type' => 'input'],
                ['label' => '签退结束时间', 'name' => 'sign_pm_end', 'type' => 'input'],
                ['label' => '微信签到最大距离(米)', 'name' => 'sign_max_distance', 'type' => 'input', 'info' => '设置考勤坐标后生效'],
            ]
        ],
        'about' => [
            'title' => '官网设置',
            'list' => [
                ['label' => '企业名称', 'name' => 'org_name', 'type' => 'input'],
                ['label' => '企业简介', 'name' => 'org_intro', 'type' => 'textarea'],
                ['label' => '企业官网', 'name' => 'org_website', 'type' => 'input'],
                ['label' => '客服热线', 'name' => 'hotline', 'type' => 'input'],
                ['label' => '企业地址', 'name' => 'org_address', 'type' => 'input'],
                ['label' => '官方邮箱', 'name' => 'org_email', 'type' => 'input'],
                ['label' => '公众号二维码', 'name' => 'wx_qr', 'type' => 'image'],
                ['label' => '小助手二维码', 'name' => 'wx_qr1', 'type' => 'image'],
                ['label' => '首页线上课程下方文字', 'name' => 'home_course_best', 'type' => 'input'],
                ['label' => '首页往期学员下方文字', 'name' => 'home_student_remark', 'type' => 'input'],
            ]
        ],
    ];
    public function makeFromData($default_value = [], $label = 'system')
    {

        return self::mergeDefaultFormData(self::$maps[$label]['list'], $default_value);
    }
}