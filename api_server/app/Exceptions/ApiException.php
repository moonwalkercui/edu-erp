<?php
namespace App\Exceptions;

use Symfony\Component\HttpKernel\Exception\HttpException;

class ApiException extends HttpException
{
    function __construct($msg = '', $code = 599)
    {
        parent::__construct($code , $msg);
    }
}