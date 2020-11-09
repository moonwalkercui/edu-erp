<?php

namespace app\common\service;

use PHPMailer\PHPMailer\PHPMailer;
use think\Log;

class Email
{
    /**
     * 邮件发送
     * @param $to 接收人
     * @param string $subject 邮件标题
     * @param string $content 邮件内容(html模板渲染后的内容)
     * @throws Exception
     * @throws phpmailerException
     */
    public static function send($to, $subject = '', $content = '')
    {
//       require_once 'vendor/phpmailer/PHPMailerAutoload.php';
        $mail = new PHPMailer();
        $config = db('config')->where('group', 'email')->column('value','key');
//        var_dump($config);
        if (
            isset($config['smtp_server']) == false ||
            isset($config['smtp_port']) == false ||
            isset($config['smtp_user']) == false ||
            isset($config['smtp_pwd']) == false ||
            isset($config['email_from']) == false
        ) {
            throw new \Exception('缺少邮件发送参数');
        }
        $mail->CharSet = 'UTF-8';
        $mail->isSMTP();
        // Enable SMTP debugging
        // 0 = off (for production use)
        // 1 = client messages
        // 2 = client and service messages
        $mail->SMTPDebug = 0;
        // $mail->Debugoutput = 'html';        // 调试输出格式
        $mail->Host = $config['smtp_server'];
        $mail->Port = intval($config['smtp_port']);  // 端口 - likely to be 25, 465 or 587
        if ($mail->Port === 465) $mail->SMTPSecure = 'ssl';// 使用安全协议
        $mail->SMTPAuth = true;     // Whether to use SMTP authentication
        $mail->Username = $config['smtp_user'];         // 发送邮箱
        $mail->Password = 'wxepfqrmhuntdied';
//        $mail->Password = $config['smtp_pwd'];
        $mail->setFrom($config['smtp_user'], $config['email_from']); // Set who the message is to be sent from     $mail->setFrom('from@example.com', 'Mailer');
        //$mail->addReplyTo('replyto@example.com', 'First Last');       //回复地址
        //接收邮件方
        if (is_array($to)) {
            foreach ($to as $v) {
                $mail->addAddress($v);
            }
        } else {
            $mail->addAddress($to);
        }
        $mail->isHTML(true); // send as HTML
        $mail->Subject = $subject;        //标题
        $mail->msgHTML($content);        //HTML内容转换
        // Replace the plain text body with one created manually
        // $mail->AltBody = 'This is a plain-text message body';
        // 添加附件
        // $mail->addAttachment('images/phpmailer_mini.png');
        // send the message, check for errors
        $res = $mail->send();
        if($res == false) Log::error('[发送邮件失败][to]'. $to. '[subject]'. $subject. '[content]'. $content);
        return $res;
    }
}