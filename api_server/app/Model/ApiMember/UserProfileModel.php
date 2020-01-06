<?php
namespace App\Model\ApiMember;

use App\Model\UserProfile;

class UserProfileModel extends UserProfile
{
    protected $hidden = ['is_manager'];
}