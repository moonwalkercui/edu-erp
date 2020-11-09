# 宏之博教务系统（原小猿管家培训教育行业ERP系统）

### 介绍
宏之博教务系统主要针对教育培训行业，有三个部分组成：教务系统、公众号端和官方网站，是一套整合了教育行业的课程课时管理、排课管理、课表管理、试卷管理打印、老师请假请款、微信打卡考勤、学员管理、权限管理等。
包括pc端和公众号端，暂未开发其他端，如有需要自行开发或联系我们进行二开，`二开价格公道哦~`

### 测试地址
测试地址：http://erp.hzb-it.com/s.html  账号 cui  密码 111111
产品介绍地址：http://www.hzb-it.com/eduerp.html
THINKPHP 官方服务市场地址：https://market.topthink.com/product/226



### 安装配置方法

系统要求：php7.2、mysql 5.6+，框架是TP5.2最新版。TP6.0版本近期更新
公众号前端是vue（uniapp开发）

### 项目目录

根据tp的定义application里是模块
api是定义通用接口的
backend是管理端
common是通用模块，不对外访问
home是官网的
staff是老师前端数据接口
student是学生前端数据接口

### 配置参数

微信公众号：文件路径\application\common\service\WxService.php 里的 $config
阿里短信：配置在config 表里的sms_开头的
腾讯云点播： 文件路径\application\common\service\TencentCloud.php 里的 $config

### 微信公众号底部菜单设置

后台可以发布底部菜单。另外请自行配置一下微信公众号里的服务器配置等。
微信响应地址：http://您的域名/wx

微信公众号接口处理控制器地址：\application\api\controller\Wx.php，
其中access方法是处理底部菜单链接地址请求的。因为需要获取用户的微信信息，所以需要走这个方法。
底部菜单的链接前缀：http://您的域名/wxauth.html?state=标记， 
标记是自定义的，用上面access方法处理跳转，跳转后自动登录和注册

微信主要逻辑在 \application\common\service\WxService.php

### 前端文件

在根目录 uniapp 文件夹里。使用uniapp开发的。
 staff-center 为教师端
 student-center 为学生端
 api接口的路径在 \route 里定义
 
 sql文件在根目录里

### 作者
冷风崔 QQ 541720500 
qq交流群 153801183

### 感谢您的捐赠:) 
