<?php
namespace app\backend\controller;

use app\backend\model\AdvertisementModel;
use app\backend\model\AdvModel;
use app\backend\model\ArticleCategoryModel;
use app\backend\model\ArticleModel;
use app\backend\model\NavModel;
use app\backend\model\OldStudentModel;
use app\backend\model\UserModel;
use app\common\model\Advertisement;
use app\common\model\ArticleCategory;
use app\common\model\Message;
use app\common\model\Notification;
use think\facade\Request;

class Article extends Base
{
    public function articles()
    {
        $where = [];
        if(input('title'))
            array_push($where, ['title', 'like', '%' . input('title') . '%' ]);
        $type = input('type', 1);
        array_push($where, ['type', '=', $type]);
        $is_del = input('is_del', 0);
        array_push($where, ['is_del', '=', $is_del]);

        $flag = $type == 1 ? '/newsinfo/' : '/article/';

        $data = [
            'title' => '文章列表',
            'collection' => ArticleModel::where($where)->with('category')->order(ArticleModel::$sort_map)->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
            'thead' => ['ID','图片','标题', '栏目','编辑时间', 'URL'],
            'fields' => [
                'id',
                function($row) {
                    return $row['image'] ? '<image src="'.$row['image'].'" class="ui tiny image">' : '';
                },function($row) use ($flag) {
                    return '<a href="'. $flag . $row['id'] .'.html" target="_blank">'. $row['title'] .'<a>';
                },
                function($row) {
                    return $row->nav? $row->nav->name : '';
                },'edit_time',
                function($row) use ($flag) {
                    return $flag.$row['id'] .'.html';
                }
            ],
            'buttons' => [
                ['title'=>'修改', 'onclick' => 'openBigWin', 'url'=> url('createArticle',['type' => $type])],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteArticle')],
            ],
            'mbuttons' => [
                ['title'=>'分类管理', 'onclick' => 'openBigWin', 'url'=> url('category'),'class_name'=>''],
                ['title'=>'添加文章', 'onclick' => 'openBigWin', 'url'=> url('createArticle',['type' => $type])],
            ],
            'searcher' => [
                ['type'=>'input','name'=>'title','placeholder'=>'标题'],
            ],
        ];
        if($is_del == 1) {
            $data['buttons'][1] = ['title'=>'彻底删除', 'onclick' => 'handleConfirm', 'url'=> url('deleteArticle',['destroy'=>1])];
        }
        $this->assign('data', $data);

        return $this->fetch('public/table_builder');
    }
    public function createArticle()
    {
        $type = input('type', 1);
        if(request()->isPost()) {
            $data = input('post.');
            $data['image'] = isset($data['image']) ? $data['image'][0] : null;
            $data['banner'] = isset($data['banner']) ? $data['banner'][0] : null;
            $data['type'] = $type;

            if(isset($data['category_id']) && $data['category_id'])
                $data['category_path'] = ArticleCategory::getPathPids($data['category_id']);

            if(isset($_POST['content']) && $_POST['content'])
                $data['summary'] = msubstr(strip_tags($_POST['content']),0, 80);

            $data['edit_time'] = now();
            if($id = input('id')) {
                $this->modelUpdate('app\backend\model\ArticleModel', $data, ['id' => $data['id']], 'article_saving', '修改文章');
            } else {
                $data['add_time'] = now();
                $this->modelCreate('app\backend\model\ArticleModel', $data, 'article_saving', '添加文章');
            }
        }else {
            $data = [];
            if(input('id')) {
                $data = ArticleModel::find(input('id'));
                $data->content = html_entity_decode($data->content);
            }
            $action = $type ==1 ? 'makeFromData' : 'makeFromData2';
            return $this->fetchFormPageHtml('app\backend\model\ArticleModel', url('createArticle',['type' => $type]), $data, $action);
        }
    }
    public function deleteArticle()
    {
        $id = input('id/d');
        if(input('destroy/d') == 1)
            echo ArticleModel::destroy($id) ? 1 : 0;
        else
            echo ArticleModel::where('id',$id)->update(['is_del'=> 1]) ? 1 : 0;
    }
    function category()
    {
        $list = ArticleCategoryModel::getTree();
        $this->assign('data', [
            'title' => '文章分类',
            'collection' => $list,
            'thead' => ['ID', '标题', '排序值'],
            'fields' => [
                'id',function($row) {return $row['_html'].$row['name'];}, 'sort_num'
            ],
            'buttons' => [
                ['title'=>'修改', 'url'=> url('createCategory')],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteCategory')],
            ],
            'mbuttons' => [
                ['title'=>'添加文章分类', 'url'=> url('createCategory')],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function createCategory()
    {
        if(request()->isPost()) {
            $data = input('post.');
            if($id = input('id')) {
                $this->modelUpdate('app\backend\model\ArticleCategoryModel', $data, ['id' => $data['id']], 'article_category_saving', '修改文章分类');
            } else {
                $this->modelCreate('app\backend\model\ArticleCategoryModel', $data, 'article_category_saving', '添加文章分类');
            }
        }else {
            return $this->fetchFormPageHtml('app\backend\model\ArticleCategoryModel', __FUNCTION__);
        }
    }
    function deleteCategory()
    {
        $id = input('id/d');
        if( ArticleModel::where('category_id', $id)->count() > 0)
            $this->error('该分类里有文章，无法删除。');
        if( ArticleCategoryModel::where('parent_id', $id)->count() > 0 )
            $this->error('该分类有子分类，无法删除。');
        echo ArticleCategoryModel::destroy($id) ? 1 : 0;
    }
    function advertisement()
    {
        $where = [];
        $this->assign('data', [
            'title' => '广告管理',
            'collection' => AdvertisementModel::where($where)->order('edit_time desc, sort_num desc')->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
            'thead' => ['ID', '图片', '标题', '显示位置', 'URL', '状态'],
            'fields' => [
                'id',
                function($row) { return '<image src="'.$row['image'].'" class="ui tiny image">';},
                'title', 'position',
                function($row) { return $row['url']; },'status'
            ],
            'buttons' => [
                ['title'=>'修改', 'onclick' => 'openBigWin', 'url'=> url('createAdvertisement')],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteAdvertisement')],
            ],
            'mbuttons' => [
                ['title'=>'添加广告', 'onclick' => 'openBigWin', 'url'=> url('createAdvertisement')],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function createAdvertisement()
    {
        if(request()->isPost()) {
            $data = input('post.');
            $data['image'] = isset($data['image']) ? $data['image'][0] : null;
            $data['edit_time'] = now();
            if($id = input('id')) {
                $this->modelUpdate('app\backend\model\AdvertisementModel', $data, ['id' => $data['id']], 'advertisement_saving', '修改广告');
            } else {
                $data['add_time'] = now();
                $this->modelCreate('app\backend\model\AdvertisementModel', $data, 'advertisement_saving', '添加广告');
            }
        }else {
            return $this->fetchFormPageHtml('app\backend\model\AdvertisementModel', __FUNCTION__);
        }
    }
    function deleteAdvertisement()
    {
        echo AdvertisementModel::destroy(input('id/d')) ? 1 : 0;
    }
    public function notifications()
    {
        $where = [];
        if(input('title'))
            array_push($where, ['title', 'like', '%' . input('title') . '%' ]);
        $this->assign('data', [
            'title' => '公告列表',
            'collection' => Notification::where($where)->order('id desc')->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
            'thead' => [
                '#',
                '标题',
                '内容',
                '时间',
            ],
            'fields' => [
                'id','title','content','add_time'
            ],
            'buttons' => [
                ['title'=>'修改', 'url'=> url('createNotice')],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteNotice')],
            ],
            'mbuttons' => [
                ['title'=>'添加系统公告', 'url'=> url('createNotice')],
            ],
            'searcher' => [
                ['type'=>'input','name'=>'title','placeholder'=>'标题'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function createNotice()
    {
        if(request()->isPost()) {
            $data = input('post.');
            $data['edit_time'] = now();
            if($id = input('id')) {
                $this->modelUpdate('app\backend\model\NotificationModel', $data, ['id' => $data['id']], 'notification_saving', '修改公告');
            } else {
                $data['add_time'] = now();
                $this->modelCreate('app\backend\model\NotificationModel', $data, 'notification_saving', '添加公告');
            }
        }else {
            return $this->fetchFormPageHtml('app\backend\model\NotificationModel', __FUNCTION__);
        }
    }
    public function deleteNotice()
    {
        echo NotificationModel::destroy(input('id/d')) ? 1 : 0;
    }
    public function adv()
    {
        $where = [];
        if(input('title'))
            array_push($where, ['title', 'like', '%' . input('title') . '%' ]);
        $this->assign('data', [
            'title' => '小程序首页广告',
            'collection' => Advertisement::where($where)->order('id desc')->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
            'thead' => [
                ['#','width:60px'],
                '标题',
                '时间',
            ],
            'fields' => [
                function($row) {
                    return '<image src="'.$row['image'].'" class="ui tiny image">';
                },'title','edit_time'
            ],
            'buttons' => [
                ['title'=>'修改', 'onclick' => 'openBigWin', 'url'=> url('createAdv')], // 'onclick'=>'redirect',
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteAdv')], // 'onclick'=>'redirect',
            ],
            'mbuttons' => [
                ['title'=>'添加广告', 'onclick' => 'openBigWin', 'url'=> url('createAdv')],
            ],
            'searcher' => [
                ['type'=>'input','name'=>'title','placeholder'=>'标题'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    public function createAdv()
    {
        if(request()->isPost()) {
            $data = input('post.');
            $data['image'] = isset($data['image'][0]) ? $data['image'][0] : "";
            if($id = input('id')) {
                $data['edit_time'] = now();
                $this->modelUpdate('app\backend\model\AdvModel', $data, ['id' => $data['id']], 'adv_saving', '修改广告');
            } else {
                $data['add_time'] = now();
                $data['position'] = 'home';
                $this->modelCreate('app\backend\model\AdvModel', $data, 'adv_saving', '添加广告');
            }
        }else {
            return $this->fetchFormPageHtml('app\backend\model\AdvModel', __FUNCTION__);
        }
    }
    public function deleteAdv()
    {
        echo AdvModel::destroy(input('id/d')) ? 1 : 0;
    }
    public function messages()
    {
        $where = [];
        if(input('title'))
            array_push($where, ['title', 'like', '%' . input('title') . '%' ]);
        $this->assign('data', [
            'title' => '发送消息记录',
            'collection' => MessageModel::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query'=>$this->request->param() ]),
            'thead' => [
                '#', '类型', '标题', '内容', '发送时间', '接收人数'],
            'fields' => [
                'id',
                function($row){return $row->type == 0 ? '系统消息': '个人消息';},
                'title','content','send_time',
                function($row){
                    return $row->type == 1 ?
                        '<a class="ui tiny label" data-title="接收者列表" data-url="article/messageToUsers.html" onclick="openWin(this, \''.$row['id'].'\')">'.count(explode(',', $row->to_users)).'</a> ':
                        '全体';
                    }
            ],
            'mbuttons' => [
                ['title'=>'发送消息', 'onclick' => 'openBigWin', 'url'=> url('sendMessage'),'height'=> 580],
            ],
            'searcher' => [
                ['type'=>'input','name'=>'title','placeholder'=>'标题'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function sendMessage()
    {
        if(request()->isPost()) {
            $data = input('post.');
            $data['send_time'] = now();
            if($data['type'] == 1) {
                $mob = str_replace(['，', ' '],[',', ''], $data['to_users']);
                $data['to_users'] = $mob ? implode(',', UserModel::where('mobile','in', $mob)->column('id')) : '';
            } else $data['to_users'] = '';
            $this->modelCreate('app\backend\model\MessageModel', $data, 'message_sending', '发送消息', function($data){
                Message::handleSend($data);
            });
        }else {
            return $this->fetchFormPageHtml('app\backend\model\MessageModel', __FUNCTION__);
        }
    }
    public function messageToUsers()
    {
        $id = input('id/d');
        $message = MessageModel::find($id);
        $this->assign('data', [
            'title' => '接收者',
            'collection' => UserModel::where('id','in', $message->to_users)->select(),
            'thead' => [ '姓名', '手机号'],
            'fields' => [ 'name', 'mobile' ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function messageTemplate()
    {
        $where = [];
        $this->assign('data', [
            'title' => '消息模板',
            'collection' => MessageTemplateModel::all(),
            'thead' => [ '标题', '内容', '通过站内信', '通过邮件', '通过短信', '通过微信公众号' ],
            'fields' => [
                'title','content',
                function($row){return $row->by_msg == 0 ? '-': '是';},
                function($row){return $row->by_email == 0 ? '-': '是';},
                function($row){return $row->by_sms == 0 ? '-': '是';},
                function($row){return $row->by_wx == 0 ? '-': '是';},
            ],
            'buttons' => [
                ['title'=>'修改', 'url'=> url('createMsgTemplate'),'height'=> 580],
            ],
            'mbuttons' => [
                ['title'=>'新建模板', 'url'=> url('createMsgTemplate'),'height'=> 580],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function createMsgTemplate()
    {
        if(request()->isPost()) {
            $data = input('post.');
            if($id = input('id')) {
                $data['edit_time'] = now();
                $this->modelUpdate('app\backend\model\MessageTemplateModel', $data, ['id' => $data['id']], 'msg_template_saving', '修改消息模板');
            } else {
                $data['add_time'] = now();
                $this->modelCreate('app\backend\model\MessageTemplateModel', $data, 'msg_template_saving', '添加消息模板');
            }
        }else {
            return $this->fetchFormPageHtml('app\backend\model\MessageTemplateModel', __FUNCTION__);
        }
    }
    public function nav()
    {
        $position = input('position');
        $list = NavModel::getTree($position);
        $this->assign('data', [
            'title' => '导航管理',
            'collection' => $list,
            'thead' => [ '名称', 'URL', '排序值'],
            'fields' => [
                function($row){return $row['_html'] . $row['name'];},
                function($row){return $row['url'];},'sort_num',
            ],
            'buttons' => [
                ['title'=>'修改', 'url'=> url('article/createNav', ['position' => $position]),'height'=> 580],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteNav')],
            ],
            'mbuttons' => [
                ['title'=>'新建导航', 'url'=> url('article/createNav', ['position' => $position]),'height'=> 580],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    public function createNav()
    {
        $position = input('position');
        if(request()->isPost()) {
            $data = input('post.');
            $data['position'] = $position;
            if($id = input('id')) {
                $this->modelUpdate('app\backend\model\NavModel', $data, ['id' => $data['id']], 'nav_saving', '修改导航');
            } else {
                $this->modelCreate('app\backend\model\NavModel', $data, 'nav_saving', '添加导航');
            }
        }else {
            if($position == 'top') $action = 'makeFromDataTop';
            if($position == 'bottom') $action = 'makeFromDataBottom';
            if($position == 'home') $action = 'makeFromDataHome';
            return $this->fetchFormPageHtml('app\backend\model\NavModel', url('article/createNav', ['position' => $position]), [], $action);
        }
    }
    public function deleteNav()
    {
        echo NavModel::destroy(input('id/d')) ? 1 : 0;
    }
    // 片段内容
//    public function segment()
//    {
//        return $this->settingPage( __FUNCTION__, [
//            'about' => '企业信息',
//            'ui_text' => '界面文字',
//        ]);
//    }


    public function students()
    {
        $this->assign('data', [
            'title' => '学生列表',
            'collection' => OldStudentModel::order('sort_num desc')->paginate(config('paginate.per_page'), false, ['query'=>$this->request->param() ]),
            'thead' => ['#',['头像','width:60px'],['生活照','width:60px'],'姓名','学习时间','简介','排序值'],
            'fields' => ['id',
                function($row) {
                    return '<image src="'.$row['avatar'].'" class="ui tiny image">';
                },
                function($row) {
                    return '<image src="'.$row['photo'].'" class="ui tiny image">';
                },'name','study_time','intro','sort_num'],
            'buttons' => [
                ['title'=>'修改', 'onclick' => 'openBigWin', 'url'=> url('addStudent')],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('delStudent')],
            ],
            'mbuttons' => [
                ['title'=>'添加学员', 'onclick' => 'openBigWin','url'=> url('addStudent')],
            ],
        ]);

        return $this->fetch('public/table_builder');
    }
    public function addStudent()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['avatar'] = isset($data['avatar']) ? implode('|', $data['avatar']) : null;
            $data['photo'] = isset($data['photo']) ? implode('|', $data['photo']) : null;
            $data['intro'] = isset($data['description']) ? msubstr(strip_tags($_POST['description']), 0, 50) : '...';
            if ($id = input('id')) {
                $this->modelUpdate(OldStudentModel::class, $data, ['id' => $data['id']], 'student_saving', '修改学员信息');
            } else {
                $this->modelCreate(OldStudentModel::class, $data, 'student_saving', '添加学员信息');
            }
        } else {
            $data = [];
            if(input('id')) {
                $data = OldStudentModel::find(input('id'));
                $data->description = html_entity_decode($data->description);
            }
            return $this->fetchFormPageHtml(OldStudentModel::class, __FUNCTION__, $data);
        }
    }
    public function delStudent()
    {
        echo OldStudentModel::where('id', input('id/d'))->delete() ? 1 : 0;
    }
}
