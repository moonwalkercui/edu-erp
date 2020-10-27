<?php
namespace app\backend\controller;
use app\backend\model\AuthRole;

/**
 * 权限类
 * @author Ray 541720500@qq.com
 */
class Auth extends Base
{
//	//管理员列表
//    public function admList()
//    {
//        $where = [];
//        if(input('title')) array_push($where, ['name', '=', input('name')]);
//        if(input('status') == -1) array_push($where, ['status', '=', -1]);
//        else  array_push($where, ['status', '=', 1]);
//        $this->assign('data', [
//            'title' => '管理员列表',
//            'collection' => \app\backend\model\Auth::where($where)->with('role')->order('status desc')->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
//            'thead' => ['#','账号','管理组','登录次数','上次登录时间','上次登录IP','状态'],
//            'fields' => ['id','account',
//                function($row){
//                $str = '';
//                foreach ($row->role as $v) {
//                    $str .=  '[' . $v->name . '] ';
//                }
//                return $str;
//            },'login_num','login_time','ip','status'],
//            'buttons' => [
//                ['title'=>'修改','onclick' => 'openBigWin','url'=> url('auth/newadm'), 'height' => 600],
//                ['title'=>'改密码','url'=> url('auth/changePw'),'onclick'=>'handlePrompt'],
//            ],
//            'mbuttons' => [
//                ['title'=>'添加账号','onclick' => 'openBigWin','url'=> url('auth/newAdm'), 'height' => 600],
//            ],
//            'searcher' => [
//                ['type'=>'input','name'=>'name','placeholder'=>'姓名'],
//                ['type'=>'select','name'=>'status','placeholder'=>'状态','options'=>config('status.normal')],
//            ],
//        ]);
//
//    	return $this->fetch('public/table_builder');
//	}
	public function newAdm(){

        $data = [];
        if($id = input('id')){
            $data = \app\backend\model\Auth::find($id);
        }
        $data['role_id'] = db('auth_role_staff')->where('staff_id',$id)->column('role_id');

        $this->assign('data', \app\backend\model\Auth::makeFromData($data));
        $this->assign('action', 'addAdm');
        return $this->fetch('public/form_builder');
	}
	public function addAdm(){
		if(request()->isPost()){
			$adm = model('auth');
			$roles= input('role_id/a');
			$post = input('post.');
            $post['photo'] = isset($post['photo']) ? $post['photo'][0] : null;
			unset($post['role_id']);
			unset($post['__token__']);
			if(empty($roles)) $this->error('操作出错：请选择管理组！');
			// 编辑模式
			if($id = input('id')){
			    $this->dataValidate($post, 'staff_edit');
				db('staff')->where('id',$id )->update($post);
				db('auth_role_staff')->where('staff_id',$id)->delete();
			}else{
                $post['password'] = md500($_POST['password']);
                $this->dataValidate($post, 'staff_add');
				$id = $adm->insertGetId($post);
			}
            $rel = [];
            foreach ($roles as $r){
                $rel[] = [
                    'role_id' => $r,
                    'staff_id' => $id,
                ];
            }
            db('auth_role_staff')->insertAll($rel) ?
                $this->success('操作成功', "auth/admlist") :
                $this->error('操作失败！');
		}else
			$this->error('非法操作！');
	}
	//检查账号是否唯一
	public function checkAcountUnique(){

		$result = $this->validate(
			[
		    	'account'  => $_POST["param"],
		    ],
		    [
		        'account|该账号'  => 'unique:adm',
		    ]);
		if (true !== $result) 
			echo json_encode(['info'=>$result,'status'=>"n"]);
		else 
			echo json_encode(['info'=>"该账号可以使用",'status'=>"y"]);
	}
	public function changePw(){
        $this->error('测试目的已经关闭');
//        $id = input('post.id');
//        $pw = input('post.value');
//        if(!$pw || strlen($pw) <= 4) $this->error('密码须大于4位');
//		$newpw = md500($pw);
//		$adm = model('auth');
//		$res = $adm->save(['password' => $newpw],['id'=>$id]);
//		if($res) 				$this->success('密码修改成功');
//		else 					$this->error('密码修改失败！');
	}
	//角色列表
	public function roleList(){
    	$list = db('auth_role')->paginate(config('num_each_page'),false, ['query'=>$this->request->param() ]);
        $this->assign('data', [
            'title' => '管理组',
            'collection' => $list,
            'thead' => ['#','管理组','备注','状态'],
            'fields' => ['id','name','remark',function($row){
                return config('status.normal')[$row['status']];
            }],
            'buttons' => [
                ['title'=>'修改','url'=> url('auth/newrole'),'height' => 330],
                ['title'=>'编辑权限','onclick' => 'openBigWin', 'url'=> url('auth/roleNode'),'height' => 630],
                ['title'=>'删除','url'=> url('auth/delRole'),'onclick'=>'delOne'],
            ],
            'mbuttons' => [
                ['title'=>'创建管理组','url'=> url('auth/newrole'),'height' => 330],
            ],
        ]);

        return $this->fetch('public/table_builder');
	}
	public function newRole(){
        $data = [];
		if($id = input('id')){
			$data = db('auth_role')->find($id);
		}

        $this->assign('data', AuthRole::makeFromData($data));
        $this->assign('action', 'addRole');
    	return $this->fetch('public/form_builder');
	}
	public function addRole(){
		$data = input('post.');
		if($id = input('post.id')){
			$condition['id'] = $id;
			$this->makeLog("[角色更新] [ID] ".$condition['id']);
			$this->modelUpdate('AuthRole',$data, $condition,"auth_role_add");
		}else{
			return $this->modelCreate("AuthRole",$data, "auth_role_add");
		}
	}
	//角色权限设定
	public function roleNode($id){
		$list = model('AuthNode')->getNodeTree();
//		 dump($list);die;
		$this->assign('list', $list);
		$role = db('auth_role')->where('id',$id)->field('id,name')->find();
		$this->assign('role', $role);
		$this->assign('nodes', model('AuthRole')->getNodeIds($id));
    	return $this->fetch();
	}
	//给角色编辑权限
	public function editRoleNode(){

        $id = input('post.id');

        session(config('status.auth.user_auth_nodes'),null);//清空权限列表session
		$db = db('auth_node_role');
		$map = ['role_id'=>$id];

		if(count(input('node/a')) == 0) {
			$this->error("您未设置任何权限！如需禁用管理员，请到管理员列表中操作。");
		}else{
			$nodes = input('node/a');
			$db->where($map)->delete();
            $insert = [];
            foreach ($nodes as $n){
                $insert[] = [
                    'role_id' => $id,
                    'node_id' => $n,
                ];
            }
            $res = db('auth_node_role')->insertAll($insert);
			if($res !== false) 	$this->success("操作成功","auth/roleList");
			else 				$this->error("操作失败");
		}
	}
	//权限列表
	public function nodeList(){
    	$db = db('auth_node'); 
		if(request()->isPost()){
			$id = input('id');
			$res = $db->where('parent_id',$id)
				->where('level','<>',9)
				->field('id,name,title,level,sort,is_menu')
				->order('sort desc')
				->select();
			return $res;
		}
    	
		$list = model('AuthNode')->getNodeTree();
		$this->assign('list', $list);
    	return $this->fetch();
	}
	//新权限点
	public function newNode(){
		$db = db('auth_node');
		if($id = input('id')){
			$data = $db->find($id);
			$this->assign('data', $data);
		}
		$node_list = $db->select();
		$n_list = unlimitedLevel($node_list);
		$this->assign('n_list', $n_list);
    	return $this->fetch();
	}
	public function addNode(){
		cache('system_menu',null);
		$url = input('post.url');
		$pid_lv = input('post.parent_id');// 'id-level-name'
		$p_l = explode("-",$pid_lv);

		$data['name'] 		= input('post.name');
		$data['title'] 		= input('post.title');
		$data['parent_id'] 	= $p_l[0];
		$data['level']		= $p_l[1];
		$data['url'] 		= $url ? $url : $p_l[2]."/".input('post.name');
		$data['is_menu'] 	= input('post.is_menu');
		$data['sort'] 	= input('post.sort');
		$data['remark'] 	= input('post.remark');

		if(input('?post.id')){
			$condition['id'] = intval(input("post.id"));
			$this->makeLog("[权限更新] [ID] ".$condition['id']);
			$this->modelUpdate('AuthNode', $data,$condition, "auth_node_add");
		}else{
			$this->modelCreate("AuthNode",$data,"auth_node_add");
		}
	}
    public function delRole(){
        if($id = input('id')){
            $this->modelDelete('AuthRole',['id' => $id]);
        }
    }
	//ajax的一些方法
	public function adm_Do(){
		if(input('post.id')!=NULL){
			$id = intval(input("post.id"));
			$type = input('post.type');
			$condition['id'] = $id;
			if($type=="del"){
				$this->modelDelete('adm',$condition);
			}
			//解锁
			if($type=="unlock"){
				$update['status'] = 1;
				$this->makeLog("[账号解锁] [ID] ".$id);
				$this->modelUpdate('adm', $update, $condition);
			}
		}
	}
//	public function delNode(){
//		if($id = input('id')){
//            return $this->moDelete('AuthNode',['id' => $id]);
//		}
//	}
	// 管理日志
	public function admLog()
    {
        $where = [];
        $this->assign('data', [
            'title' => '操作日志',
            'collection' => db('system_log')->where($where)->order('id desc')->paginate(50,false, ['query'=>$this->request->param() ]),
            'thead' => ['时间','姓名','操作','IP'],
            'fields' => ['add_time','login_name','content','ip'],
        ]);
        return $this->fetch('public/table_builder');
	}

}