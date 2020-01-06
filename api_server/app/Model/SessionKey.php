<?php
namespace App\Model;

use App\Exceptions\ApiException;
use Carbon\Carbon;

class SessionKey extends BaseModel
{
    protected $table = 'session_keys';
    public $timestamps = false;
    protected $fillable = [
        'key',
        'expires_at',
        'open_id',
        'data',
    ];
    /*
    * 储存sessionkey到sessions表里，返回session_id
    */
    public static function store($session)
    {
        if (!isset($session['session_key']))
            throw new ApiException('未获取到session_key');
        // $session : array(3) {
        // ["session_key"]=> string(24) "Kh8t5BRYEDk0WN49cdoovQ=="
        // ["expires_in"]=> int(7200)
        // ["openid"]=> string(28) "o16MJ0UF2262nFUopVwpih3tlFag"
        // }
        $sid = makeSn();
        self::insert([
            'sid' => $sid ,
            'key' => $session['session_key'],
            'expires_at' => Carbon::now()->addSeconds(3600),
            'open_id' => $session['openid'],
            'union_id' => isset($session['unionid']) ? $session['unionid'] : null,
            'data' => json_encode($session)
        ]);
//        SessionKey::where('expires_at','<',time())->delete(); // 删除一下过期的
        return $sid;
    }
    public static function getSessionKey($session_id)
    {
        return self::findSession($session_id)->key;
    }
    public static function getSessionOpenid($session_id)
    {
        return self::findSession($session_id)->open_id;
    }
    public static function findSession($session_id)
    {
        $session = self::where('sid', $session_id)->first();

        if (Carbon::parse($session->expires_at) < now())
            throw new ApiException('登录状态过期,请关闭重试');

        return $session;
    }
}
