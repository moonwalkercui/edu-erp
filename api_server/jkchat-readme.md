
php /home/wwwroot/sshop/Server/start.php start -d
php /home/wwwroot/sshop/Server/start.php restart

# JkChat启动方法

## 启动GatewayWorker

php /home/wwwroot/sshop/Server/start.php start

## 确认启动redis

service redis status

## redis 数据存储格式



## 接口

获取用户信息的接口
如：url: xxx.com/getuserinfo?uid=open_id
返回json: {"name":"JKKKK","avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM6z7QS1kRyRBibkyLhAc7OB59c6YXxHquibBj50KuBiczfOwtEna4pZGFIyjq6msOhVG0nAQMKoC64Ag/132","gender":1}

## 小程序端设置

### wxml文件增加按钮
```
 <button open-type="contact" session-from="{{sessionFormData}}">在线客服</button>
```
### js文件

在data里加变量`sessionFormData: ''`：
```
Page({
  data: {
    sessionFormData: ''
    ...
  }
```
加入sessionFormData处理函数makeSessionFromData。code和nickName为必填其他为选填。注意对nickName和avatarUrl有必要进行`encodeURIComponent`编码，防止数据字符错误导致异常。

```
    makeSessionFromData: function (userInfo) {
        var data = {
          code: "Et1AjnCqi", // 必填
          nickName: encodeURIComponent(userInfo.nickName), // 必填
          avatarUrl: encodeURIComponent(userInfo.avatarUrl) || "", 
          gender: userInfo.gender || "",
          city: userInfo.city || ""
        }
        this.setData({
          sessionFormData: JSON.stringify(data)
        });
    }

```
执行微信的用户信息获取接口并回调执行makeSessionFromData, 参数为获取到的userInfo:
```
    wx.getUserInfo({
      withCredentials: true,
      success: function (res) {
        console.log('获取用户信息', res)
        that.makeSessionFromData(res.userInfo)
      }
    })
    ...
```

