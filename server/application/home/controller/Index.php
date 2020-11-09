<?php
namespace app\home\controller;

use app\common\model\Article;
use app\common\model\Audio;
use app\common\model\AudioCategory;
use app\common\model\Nav;
use app\common\model\OldStudent;
use app\common\model\Student;
use app\common\model\Teacher;
use app\common\service\Subtitle2Array;
use app\common\service\WeChat\Wechat;
use app\common\service\WxService;
use app\home\model\WxCourse;
use Done\Subtitles\Subtitles;

class Index extends Base
{
    function index()
    {
        $this->assign('cate_id', input('cid', null));
        return $this->fetch();
    }
    function about()
    {
        return $this->fetch();
    }
    function student()
    {
        return $this->fetch();
    }
    function news($nav_id = 0)
    {
        if($nav_id) {
            $list = Article::getByNav($nav_id);
        } else {
            $nav_ids = Nav::where('parent_id', 19)->column('id');
            $list = Article::getByNav($nav_ids);
        }
        $this->assign('list', $list);
        return $this->fetch();
    }
    function studentInfo($id)
    {
        $this->assign('data', OldStudent::get($id));
        return $this->fetch();
    }
    function newsInfo($id)
    {
        $data = Article::get($id);
        $this->assign('page_title', $data['title']);
        $this->assign('page_seo_keyword', $data['seo_keywords']);
        $this->assign('page_seo_description',$data['seo_description']);
        $this->assign('data', $data);
        return $this->fetch();
    }
    function article($id)
    {
        $data = Article::get($id);
        $this->assign('page_title', $data['title']);
        $this->assign('page_seo_keyword', $data['seo_keywords']);
        $this->assign('page_seo_description',$data['seo_description']);
        $this->assign('data', $data);
        return $this->fetch();
    }
    function teacher($id)
    {
        $this->assign('data', Teacher::get($id));
        return $this->fetch();
    }
    function course()
    {
        $cate_id = input('cate_id/d');
        $this->assign('cate_id', $cate_id);
        return $this->fetch();
    }
    function wxConfig()
    {
        $url = input('url');
        $res = WxService::jsSign($url);
        return json([
            'appid' => $res['appId'],
            'timestamp' => $res['timestamp'],
            'noncestr' => $res['nonceStr'],
            'signature' => $res['signature'],
            'url' => $url,
        ]);
    }
}