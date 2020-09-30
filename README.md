# 小猿管家培训教育行业ERP系统
#### 注意：本系统目前测试服务器被占用暂时不开启测试。代码功能不受影响。

#### 研发的新版宏之博教务系统（非本仓库代码项目），欢迎大家咨询
测试地址：http://erp.hzb-it.com/s.html  账号 cui  密码 111111
THINKPHP 官方服务市场地址：https://market.topthink.com/product/226

#### 介绍
小猿管家培训教育行业ERP系统，包括api端，管理端，小程序端，暂未开发其他端，如有需要自行开发。
支持多组织管理，每个组织有分校，可以为每个组织分配二级域名访问。

#### 介绍和地址
http://www.vhuojia.com/

#### 技术栈
1.  wepy开发的小程序端，学员使用
2.  vue+elementui开发的管理端
3.  laravel开发的api端

#### 目录说明

1.  api_server 为api端
2.  frontend 为管理端（前端）
3.  miniapp 为小程序端 wepy开发

##### api端配置

使用laravel开发，使用前 `composer install`
apikey请自行生成

sql:
需要建立两个数据库表：组织表和总表common
组织表为每个组织的数据，以后新建组织可以以组织编号为表名进行创建，可以另行设计规则。
总表为总管理表，用于组织层面的管理。
配置文件位置在`api_server/.env` 和 `api_server/config/database.php`, 逻辑很简单

##### vuejs管理端配置域名

编辑 frontend/src/common/Api.js里的:
```
...
    return 'https://域名/' + prefix + '/'; // prefix是组织编号  
...
    static serviceDomain = 'https://域名/';
```
##### 小程序配置

编辑 miniapp/src/utils/global.js里的:


#### 作者
冷风崔 QQ 541720500 
sql文件联系作者免费获取
或到qq群153801183里自行下载即可

#### 感谢您的捐赠:) 
