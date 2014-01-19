SSOLogin
========

# 简介 #

这是一个仿照新浪微博登录做的sso系统,用于实验室主页的各种身份认证.点击[这里](http://202.206.64.193/SSOLogin)查看效果.

# 参数 #

- `SSO_state`:Username框失去焦点时候进行一次PreLogin,获得state为1,正式登录之后由服务器写入state为2.
- `login_sid`:本次登录的唯一标识符.
- `pubkey`: PreLogin之后在ContentReader中包含一个Json格式的的`PreLoginInfo`,里面含有这个参数,用于后面的密码的加密.
- `pcid`:对当前机器的生成的一个ID号.
- `su`:对用户名进行Base64编码.
- `sp`:对`password+pubkey`进行SHA1加密.
- `SUSP`:对`su + sp + pubkey`进行SHA1加密.在Login之后由服务器写回本地.

# 各过程对于参数的要求 #

## preLogin  **GET** ##

### 要求 ###
- 有`login_sid`(从cookie中取)

### 返回值 ###
- Json格式的`PreLoginInfo`,含有`pubkey`和`pcid`.
- 向cookie写入`SSO_state`.

## login  **POST** ##

### 要求 ###

一下全部是**POST date**

- `su`
- `sp`
- `pubkey`
- `pcid`
- `SSO_state`
- `login_sid`

### 返回值 ###

URL
并向cookie写入SSO_state=2,`SUSP`,`su`.

## validate **GET** ##

### 要求 ###

- `su`
- `SUSP`
- `login_sid`
- `pubkey`

### 返回 ###

若已经登陆则不做动作,如果未登录则重定向至登录界面.

# 如何使用 #

## 身份验证 ##

- 在需要验证的页面的body的onload方法使用`ssologin.js`里面的`validate`方法.

## 身份识别 ##

- 只需要对于cookie里面的su进行Base64解码即可,`ssologin.js`里面都有现成的方法.