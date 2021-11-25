# 宏之博·HZB教务管理系统

#### 介绍
这是一套支持私有化部署的教培行业教务管理系统，专为教培行业提供云化管理解决方案，是一套纯springboot+mysql微服务项目。系统在功能上注重教务管理，具有灵活的排课、消课等核心业务功能；系统采用稳定的微服务架构开发，运行流畅，易于部署扩展，支持私有化部署。应用端包括PC管理端、老师手机端、学生手机端。
#### 这是一个单纯的微服务，主要实现了教务相关业务逻辑部分。未做分布式架构，如有分布式和容器化处理，大家视情况自行装配。
#### 系统演示地址：http://erp2.hzb-it.com
#### 作者Ryan：15666323771 (手机+微信)
#### 下载与安装前请阅读软件许可说明
![输入图片说明](%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20211121100307.png)
## 技术栈

#### 框架
- 核心框架：sprinboot2.4.1
- JS框架：vue2 + Element
- 移动端框架：uniapp
- 数据库：MySQL5.7
- 缓存：Redist、Guava Cache
- java工具包：Hutool
- Api管理：Swagger + knife4j
- Mail：javamail

#### 开发环境

- IDE: idea
- DB: Mysql5.7
- JDK: JAVA 8

#### 技术特色

- RBAC角色与权限管理
- 自研基于岗位的数据权限管理，扩展性强
- Security + JWT 多角色权限认证模式
- vue使用自研CUI组件，极大减少页面代码量

## 部署说明
首先装好nginx和java1.8，mysql5.7

#### 文件结构
 - server
   - base-api-1.0.0.jar 服务端运行包
   - application.yml 服务端配置
   - sql文件
 - uniapp
   - student-center 家长端  构建部署后用/s/index.html访问
   - teacher-center 教师端  构建部署后用/t/index.html访问
 - ui
   - 静态前端运行包
   
#### 前端部署后访问方法

管理后台前端：http://域名
老师手机端：http://域名/t/
家长手机端：http://域名/s/

#### 后端服务配置
服务端默认开启上下文是app，后台服务端需要用nginx做一下代理设置如下：
```
location /app {
    proxy_pass   http://localhost:8106/app; # 端口要和application.yml里的一致
    proxy_redirect    off;
    proxy_set_header  Host $host;
    proxy_set_header  X-real-ip $remote_addr;
    proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
}
```
服务端地址前缀：http://域名/app/

#### 后端服务启动方法

1，导入sql到数据库里。
2，修改application.yml里的配置 里面有具体说明
3，然后cmd，切换到在jar包所在的目录执行（注意配置文件application.yml要和jar包在一个目录里，否则默认加载不到）：
```
直接运行：
sudo java -jar ./base-api-1.0.0.jar

后台模式：
sudo nohup java -jar base-api-1.0.0.jar > output.log 2>&1 &
```
运行完成

## 数据库版本
mysql的版本是5.7，不过去掉一个参数配置： `ONLY_FULL_GROUP_BY` ，否则会有group by的错误，解决方案百度搜ONLY_FULL_GROUP_BY很多。或者用mysql5.6也可以。

## 微信配置

#### 微信配置
公众号配置入口：http://域名/app/wx/portal/default
自定义菜单：http://域名/app/wx/menu/default/create
获取微信登录按钮跳转地址接口: http://域名/app/wx/portal/default/loginUrl?state=student

#### 模板消息配置：
管理端可配置模板

## 家长端和老师端体验公众号：
![输入图片说明](HZB%E5%85%AC%E4%BC%97%E5%8F%B7.jpg)
本公众号可以体验手机端，同时不定期发布最新版本公告。

## 未来版本方案计划：

- 文档资料库
- 工作流
- 积分商城
- 在线购课
- electron
- 视频在线学习
- 物料管理

#### 欢迎捐赠支持让我们做的更好！