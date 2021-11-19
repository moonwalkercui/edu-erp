## 部署
首先要有个服务器，装好nginx和java8，mysql

#### 文件结构

 - 前端
   - index.html 管理端前端首页
   - /s/index.html 学生端首页
   - /t/index.html 教师端首页
 - 服务端
   - base-api-1.0.0.jar 服务端运行包
   - application.yml 服务端配置
   
#### 前端安装方法

将三个前端解压到站点跟目录里，访问方式如下：

- 管理后台前端：http://域名
- 老师手机端：http://域名/t/
- 家长手机端：http://域名/s/

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
可以通过这个地址访问后端是否正常启动：http://域名/app/common/open

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
mysql的版本是5.7，不过去掉一个参数配置： ONLY_FULL_GROUP_BY ，否则会有group by的错误。或者用5.5也可以，未测试5.5

## 微信配置

#### 微信配置
公众号配置入口：http://域名/app/wx/portal/default
自定义菜单：http://域名/app/wx/menu/default/create
获取微信登录按钮跳转地址接口: http://域名/app/wx/portal/default/loginUrl?state=student

#### 模板消息配置：

