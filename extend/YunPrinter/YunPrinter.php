<?php
namespace YunPrinter;

use think\Exception;
use think\facade\Config;

class YunPrinter
{
    private $config;
    private $engine;
    public $error;
    public function __construct($config = [])
    {
        $this->config = $config;
        $this->engine = $this->getEngineClass();
    }
    public function run($deviceNo, $orderNo, $msgDetail)
    {
        $res = $this->engine->runPrinter($deviceNo, $orderNo, $msgDetail);
        $this->error = $this->engine->error;
        return $res;
    }
    public function add($deviceNo, $name='设备名称')
    {
        $res = $this->engine->addPrinter($deviceNo, $name);
        $this->error = $this->engine->error;
        return $res;
    }
    public function del($deviceNo)
    {
        $res = $this->engine->delPrinter($deviceNo);
        $this->error = $this->engine->error;
        return $res;
    }
    public function status($deviceNo)
    {
        return $this->engine->printerStatus($deviceNo);
    }
    public function statusAll()
    {
        return $this->engine->printerStatusAll();
    }
    private function getEngineClass()
    {
        $engineName = isset($this->config['driver']) ? $this->config['driver'] : Config::get('printer.default_driver');
        $classSpace = __NAMESPACE__ . '\\driver\\' . $engineName;
        if (!class_exists($classSpace)) {
            throw new Exception('未找到云打印引擎类: ' . $engineName);
        }
        return new $classSpace();
    }
}