<?php namespace App\Model;

class RedPacketLog extends BaseModel
{
    protected $table='red_packet_log';
    protected $fillable = [ 'member_id', 'amount', 'created_at' ,'expired_at','friend_id','stage'];
    public $timestamps = false;
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile', 'member_id', 'member_id')->select("nick_name","member_id");
    }
    public function referral()
    {
        return $this->belongsTo('App\Model\MemberProfile', 'friend_id', 'member_id')->select("nick_name","member_id");
    }
    public function order()
    {
        return $this->belongsTo('App\Model\Order', 'order_id')->select("id","sn");
    }
    public function getStageAttribute($value)
    {
        return getStatusText($value,'red_packet_stage');
    }
    public static function getHelpFriends($member_id)
    {
        $friend_ids = self::where('member_id', $member_id)->where('created_at', '>', now()->subMonth(6))
            ->pluck('friend_id')->toArray();
        if(empty($friend_ids))
            return [];
        else
            return MemberProfile::whereIn("member_id", array_unique($friend_ids))->select("avatar","nick_name")->limit(100)->get();
    }
}
