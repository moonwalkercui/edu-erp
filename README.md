# 宏之博·HZB教务管理系统

#### 介绍
这是一套支持私有化部署的教培行业教务管理系统，专为教培行业提供云化管理解决方案，是一套纯springboot+mysql微服务项目。系统在功能上注重教务管理，具有灵活的排课、消课等核心业务功能；系统采用稳定的微服务架构开发，运行流畅，易于部署扩展，支持私有化部署。应用端包括PC管理端、老师手机端、学生手机端。
#### 这是一个单纯的微服务，主要实现了教务相关业务逻辑部分。未做分布式架构，如有分布式和容器化处理，大家视情况自行装配。
#### 系统演示地址：http://erp2.hzb-it.com
#### 美女客服Monica：13009401779 (手机+微信)
#### 下载与安装前请阅读软件许可说明
![输入图片说明](%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20211119235728.png)
## 技术栈

#### 框架
- 核心框架：sprinboot2.4.1
- JS框架：vue2 + Element
- 移动端框架：uniapp
- 数据库：MySQL5.6
- 缓存：Redist、Guava Cache
- java工具包：Hutool
- Api管理：Swagger + knife4j
- Mail：javamail

#### 开发环境

- IDE: idea
- DB: Mysql5.6
- JDK: JAVA 8

#### 技术特色

- RBAC角色与权限管理
- 自研基于岗位的数据权限管理，扩展性强
- Security + JWT 多角色权限认证模式
- vue使用自研CUI组件，极大减少页面代码量，前端CUI组件开发说明wiki: https://gitee.com/ryan1981/vue-element-cui/wikis

## 文件结构
#### 目录说明
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

#### 后端服务配置 和 微信公众号配置 
见server里的readme.md

#### 推荐一个服务器优惠链接
https://www.aliyun.com/minisite/goods?userCode=hk2fn0gu

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

## 常见问题

##### ui部署后出现“权限不足”等登录失败的问题。
答，这个问题问的比较多，原因有可能不是真的权限不足，而是登录接口没有返回权限数据导致的。一般和接口地址设置错误或跨域问题未解决有关。方法如下：
a：检查config.js里的api接口是否跨域访问，跨域通过一个开放测试接口进行浏览器请求：http://ip:端口号/app/common/open/ 如果有返回内容说明后端服务已经部署好了。
b: 在服务器上运行的话，config.js不能设置为localhost,要设置为ip或域名，至于端口号留不留，要看情况，如果服务端做了nginx的反向代理，就不用加，如果没做就加端口号。反向代理代码上面有。
c: 跨域导致的无法请求，可以在application.yml的 allowOrigin: 参数里，配置上前端访问用的ip或域名。或者干脆不限制跨域，就是把这个参数底下带-的的内容删除掉。
即可解决。


## 软件著作权证书
![输入图片说明](%E8%91%97%E4%BD%9C%E6%9D%83%E8%AF%81%E4%B9%A6.png)


#### 欢迎捐赠支持让我们做的更好！


