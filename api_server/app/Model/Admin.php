<?php

namespace App\Model;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
/* 用户可以使用后台 */
class Admin extends Authenticatable
{
    use Notifiable;
    protected $table='admins';
    protected $fillable = [ 'account', 'password' ];

}
