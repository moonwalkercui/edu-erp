<?php

namespace App\Api\V1\Controllers\user;

use App\Model\Member;
use App\Model\MemberProfile;
use App\Model\Menu;
use App\Model\Setting;
use App\Model\UserProfile;
use App\Service\Api\UserService;
use App\Service\ExcelService;
use App\Service\WeChat\WxUtils;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;
use App\Api\V1\Controllers\Base;

class SystemApi extends Base
{
    // 数据的导入和数据模板导出
    public function importExcel(Request $request)
    {
        // 导出 excel 模板
        if ($request->filled('export') && $request->export == 'excel') {
            switch ($request->type) {
                case 'student' :
                    $xls_filename = '学员信息模板表2';
                    $xls_body = [
                        ['name' => '例子', 'mobile' => '1234567890', 'gender' => '男', 'birthday' => '2010-12-30', 'salesman' => '张三']
                    ];
                    $xls_header = [
                        'name' => '姓名(必填)',
                        'mobile' => '手机号(必填)',
                        'gender' => '性别',
                        'birthday' => '出生日期',
                        'salesman' => '业务员',
                    ];
                    break;
                default:
                    return $this->error('缺少excel模板类型');
            }
            return $this->export($xls_body, $xls_header, $xls_filename);
        }
        $file = $request->file('excelfile');
        $data = ExcelService::importExecl($file);
        array_shift($data);
        foreach ($data as $k => $v) {
            if ($v['A'] == '例子') unset($data[$k]);
        }
//        dump($data);
        switch ($request->type) {
            case 'student' : // 导入学员数据
                $name_username = UserProfile::where('name', $v['E'])->pluck('username', 'name');
                $i = $j = 0;
                foreach ($data as $v) {

                    if (MemberProfile::where('mobile', $v['B'])->first()) {
                        $j++;
                    } else {
                        $mid = Member::insertGetId(['type' => 1]);
                        MemberProfile::create([
                            'member_id' => $mid,
                            'name' => $v['A'],
                            'mobile' => $v['B'],
                            'gender' => getStatusValue($v['C'], 'gender'),
                            'birthday' => $v['D'],
                            'salesman_uname' => isset($name_username[$v['E']]) ? $name_username[$v['E']] : null,
                        ]);
                        $i++;
                    }
                }
                return $this->success('成功导入' . $i . '位学员' . ($j ? '; 导入失败' . $j . '位' : ''));
                break;
            default:
                return $this->error('缺少导入数据类型');
        }

//        dump($request->file('excelfile'));
//        dump($request->all());
    }

    public function getLeftMenu(Request $request)
    {
        $where = [
            ['status', '=', 1],
        ];
        if ($request->filled('position')) array_push($where, ['position', '=', $request->input('position')]);
        $model = Menu::where($where)->orderBy('sort');
        $permission_ids = $this->user()->getPermissions();
        array_push($permission_ids, 0);
        if (UserService::isManager() == false) $model->whereIn('permission_id', $permission_ids); // 0为不限制权限
        return $this->fetch(unlimitedChild($model->get()));
    }

    public function uploader(Request $request)
    {
        $action = $request->input('action');
        switch ($action) {
            case 'uploadimage':
                return $this->uploadImage($request);
                break;/* 上传图片 */
            case 'uploadavatar':
                return $this->uploadImageBase64($request);
                break;/* 上传图片 */
            default:
                $result = json_encode(["status" => "error", 'msg' => '无上传类型']);
        }
        echo $result;
    }

    private function uploadImageBase64(Request $request)
    {
        $base64_url = $request->input('image_data');

//        file_put_contents('666.txt', $base64_url);die;

        if (preg_match('/^(data:\s*image\/(\w+);base64,)/', $base64_url, $result)) {
            $ext = $result[2];
            if ($ext == 'jpeg') {
                $ext = 'jpg';
            }
            if (in_array($ext, array('jpg', 'gif', 'bmp', 'png'))) {
                $upload_path = $this->makeAvatarPath($ext);
                $base64_body = substr(strstr($base64_url, ','), 1);
                $data = base64_decode($base64_body);
                $res = Storage::put('/public' . $upload_path, $data);
                if ($res) {
                    echo json_encode([
                        "status" => "success",
                        "msg" => "已上传",
                        "url" => getResourceUrl() . '/' . $upload_path
                    ]);
                } else {
                    echo json_encode([
                        "status" => "error", "msg" => "上传失败"
                    ]);
                }
            } else {
                //文件类型错误
                echo json_encode([
                    "status" => "error", "msg" => "图片上传类型错误"
                ]);
            }
        } else {
            //文件错误
            echo json_encode([
                "status" => "error", "msg" => "文件错误"
            ]);
        }
    }
//    private function getExeName($fileName)
//    {
//        $pathinfo = pathinfo($fileName);
//        return strtolower($pathinfo['extension']);
//    }
    private function makeAvatarPath($ext = 'jpg')
    {
        $username = UserService::getUserName();
        $config = config('system.upload');
        return $config['imagePath'] . 'u_' . $username . '/' . Str::random(30) . '.' . $ext;
    }

    // 参数中filename是区别上传类型的，同时这个文件的文件名也是以filename值为key的
    private function uploadImage(Request $request)
    {

        $user = UserService::getUserInfo();
        $username = $user->username;

        $config = config('system.upload');
        $file_name = $request->input('filename');

        switch ($file_name) {
            case 'useravatar' : // user头像 这个用base64方法代替了
                $upload_path = $config['imagePath'] . 'u_avatar/' . $username . date('Ymd', time());
                break;
            case 'productimg' : // 产品介绍内容图
                $upload_path = $config['imagePath'] . 'p_desc/' . date('Ymd', time()); // 上传路径o代表组织
                break;
            case 'productalbum' : // 产品相册图 主图等
                $upload_path = $config['imagePath'] . 'p_album/' . date('Ymd', time()); // 上传路径o代表组织
                break;
            default :
                $upload_path = $config['imagePath'];
        }
        if ($request->hasFile($file_name) == false) {
            echo json_encode(["status" => "error", "msg" => "上传文件丢失"]);
        } else {
            $validator = Validator::make($request->all(), [
                $file_name => $config['imageValidate']
            ]);
            $errors = $validator->errors();
            if (!empty($err = $errors->get($file_name))) {
                echo $state = $err[0];
            } else {
                $file = request()->file($file_name);
                $res_upload = $file->store($upload_path, 'public');
                if ($file->isValid() == false)
                    echo json_encode([
                        "status" => "error", "msg" => "无效文件"
                    ]);
                elseif ($res_upload == false) {
                    echo json_encode([
                        "status" => "error", "msg" => "上传失败"
                    ]);
                } else {
                    echo json_encode([
                        "status" => "success",
                        "msg" => "已上传",
                        "title" => $file->getClientOriginalName(),
                        "type" => $file->guessExtension(),
                        "size" => $file->getSize(),
                        "url" => getResourceUrl() . '/' . $res_upload
                    ]);
                }
//                echo json_encode([
//                    "state" => 'SUCCESS',
//                    "url" => "http://www.xxx.cui/resource?p=" . $file->store($upload_path,'public'),
//                    "title" => $file->getClientOriginalName(),
//                    "original" => $file->getClientOriginalName(),
//                    "type" => $file->guessExtension(),
//                    "size" => $file->getClientSize()
//                ]);
//                if($file->isValid())
//                //检验一下上传的文件是否有效.
//                var_dump($file->getError());
//                var_dump($file->getErrorMessage());
//                $clientName = $file -> getClientOriginalName();        //文件原始名称
//                $tmpName = $file ->getFileName();                     //文件名称
//                $realPath = $file -> getRealPath();                    //文件路径
//                $extension = $file->getClientOriginalExtension();    //文件扩展名
//                $size = $file->getSize();                            //文件大小
//                $mime = $file->getMimeType();                        //文件MIME类型
//                $path = $file -> move('storage/uploads');            //文件保存

            }
        }
    }

    // 发送邮件服务
    public function sentEmail(Request $request)
    {
        $this->validateParam($request->all(), [
            'toemail' => 'required|email',
            'type' => 'required',
            'title' => 'required_if:type,normal',
            'content' => 'required_if:type,normal'
        ], [
            'toemail.required' => '缺少接收邮箱地址',
            'title.required_if' => '缺少邮件标题',
            'content.required_if' => '缺少邮件内容',
        ]);

        // todo 发送邮件 邮件日志

        return $this->success('邮件已发送');
    }

    public function getQrcode()
    {
        $wx = new WxUtils();
        return $this->fetch([
            'url' => $wx->getQrcodeImg(UserService::getUserName()),
            'info' => '新会员通过微信扫码可成为关注者; 您将成为其业务员。'
        ]);
    }

    public function valueUpdate(Request $request)
    {

        switch ($request->type) {
            case 'wx_cert':
                $upl = Storage::put('cert/' . API_PREFIX . '/apiclient_cert.pem', strip_tags(trim($request->value)));
                if (!$upl) return $this->error('上传失败');
                $res = Setting::saveSetting(['has_apiclient_cert' => 1]);
                break;
            case 'wx_key':
                $upl = Storage::put('cert/' . API_PREFIX . '/apiclient_key.pem', strip_tags(trim($request->value)));
                if (!$upl) return $this->error('KEY上传失败');
                $res = Setting::saveSetting(['has_apiclient_key' => 1]);
                break;
            default:
                $res = false;
        }
        if (false === $res) return $this->error('提交失败');
        else return $this->success('已上传');
    }
}