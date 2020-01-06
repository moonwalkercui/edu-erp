<?php

namespace App\Model\ApiUser;

use App\Model\User;
use App\Service\Api\UserService;
class UserModel extends User
{
//    use OrgScopeTrait;
    protected $hidden = ['password', 'remember_token' ,'department_id','wx_union_id','org_quantity'];

    public function profile()
    {
        return $this->hasOne('App\Model\UserProfile','user_id');
    }
}
