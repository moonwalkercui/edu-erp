<?php
namespace YunPrinter\driver;


abstract class Driver
{
    public $error;
    protected function __construct()
    {
    }
    // 打印  设备号，订单号，打印内容
    abstract protected function runPrinter($deviceNo, $msgNo, $msgDetail);
    // 添加打印机
    abstract protected function addPrinter($deviceNo,$deviceName='');
    // 打印机列表
    abstract protected function delPrinter($deviceNo);
    // 打印机状态
    abstract protected function printerStatus($deviceNo);
    abstract protected function printerStatusAll();
}