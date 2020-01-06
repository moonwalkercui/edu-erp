<?php
namespace App\Model;

class MemberFavorite extends BaseModel
{
    public $timestamps = false;
    protected $table = 'member_favorites';
    protected $fillable = [ 'member_mobile', 'target_id','target_type','created_at'];
    public function target()
    {
        return $this->morphTo();
    }
}
