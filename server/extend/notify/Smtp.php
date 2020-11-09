<?php
/**
 * ZIHAIMX [ WE CAN DO IT JUST ENJOY ]
 * 发送邮件类
 * @since   2016-12-08
 * @author  TechLee <767049337@qq.com>
 */
namespace notify;

use app\common\model\LogMailer;
use think\Config;

class Smtp
{
    /**
     * @param $address 收件人地址，多人以';'隔开，
     * @param $body    发送内容
     * @param $subject 发送标题 不填则截取 内容
     */
    public static function send($address, $body, $subject = '')
    {
        $mail = new \PHPMailer;
        $mail->isSMTP(); // Set mailer to use SMTP
        $mail->Host     = Config::get('mailer.host'); // Specify main and backup SMTP servers
        $mail->Username = Config::get('mailer.username'); // SMTP username
        $mail->Password = Config::get('mailer.password'); // SMTP password
        $mail->setFrom(Config::get('mailer.username'), Config::get('mailer.from_name')); //发件人
        $mail->addReplyTo(Config::get('mailer.username'), Config::get('mailer.from_name')); //回复
        $mail->Port = Config::get('mailer.port'); // TCP port to connect to

        $mail->CharSet    = 'UTF-8'; //设定邮件编码，默认ISO-8859-1，如果发中文此项必须设置为 UTF-8
        $mail->SMTPAuth   = true; // Enable SMTP authentication
        $mail->SMTPSecure = 'tls'; // Enable TLS encryption, `ssl` also accepted
        // $mail->addCC('cc@example.com'); //抄送
        // $mail->addBCC('bcc@example.com');//抄送
        // $mail->addAttachment('/var/tmp/file.tar.gz');         // 附件
        // $mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // 附件
        $mail->isHTML(true); // Set email format to HTML
        $addressArr = explode(';', str_replace('；', ';', $address));
        foreach ($addressArr as $key => $value) {
            $mail->addAddress(trim($value)); //收件人
        }
        $mail->Subject = $subject ? $subject : substr_cn($body, 20);
        $mail->Body    = $body;
        // $mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
        // dump($mail);die;

        $LogMailer            = new LogMailer;
        $LogMailer->from      = $mail->From;
        $LogMailer->from_name = $mail->FromName;
        $LogMailer->subject   = $mail->Subject;
        $LogMailer->body      = $mail->Body;

        $LogMailer->address = $address;
        $LogMailer->save();
        $res = '发送失败！';
        if (!$mail->send()) {
            $LogMailer->error = $mail->ErrorInfo;
            $res              = $LogMailer->error;
        } else {
            $LogMailer->status = 1;
            $res               = true;
        }
        $LogMailer->save();
        return $res;
    }
}
