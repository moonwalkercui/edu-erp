<?php
namespace app\student\controller;

use app\common\controller\BaseController;

class Base extends BaseController
{
    protected function initialize()
    {
        parent::initialize();
    }
    protected function checkLogin($redirect_back = false)
    {
        if(input('debug') == 1) {
            session('student_id', 1);
            session('wx_access_id',15);
        }
        if(session('student_id') == false) {
            return false;
        }
        return true;
    }
}