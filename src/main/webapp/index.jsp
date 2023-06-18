<%--
  Created by IntelliJ IDEA.
  User: Valar Morghulis
  Date: 2023/6/5
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <title>index界面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="register.css">
    <base href="<%=basePath%>">
</head>

<body>

<div class="container">
    <div class="form-box">
        <!-- 注册 -->
        <div class="register-box hidden">
            <h1>register</h1>
            <form id="register_form" name="register_form">
                <input type="text" id="register_username" name="register_username" placeholder="用户名">
                <input type="password" id="register_password" name="register_password" placeholder="密码">
                <input type="password" id="register_password_2" placeholder="确认密码">
                <button type="submit" id="check_register">注册</button>
            </form>
        </div>
        <!-- 登录 -->
        <div class="login-box">
            <h1>login</h1>
            <form id="login_form" name="login_form">
                <input type="text" id="login_username" name="login_username" placeholder="用户名">
                <input type="password" id="login_password" name="login_password" placeholder="密码">
                <button type="submit" id="check_login">登录</button>
            </form>
        </div>
    </div>
    <div class="con-box left">
        <h2>和皮卡丘<span>打个招呼</span></h2>
        <p>快来<span>充电</span>吧</p>
        <img src="./WEB-INF/static/picture/pika1.jpeg" alt="">
        <p>已有账号</p>
        <button id="login">去登录</button>
    </div>
    <div class="con-box right">
        <h2>和皮卡丘<span>打个招呼</span></h2>
        <p>快来<span>充电</span>吧</p>
        <img src="./WEB-INF/static/picture/pika2.jpeg" alt="">
        <p>没有账号？</p>
        <button id="register">去注册</button>
    </div>
</div>
<script type="text/javascript" src="./WEB-INF/static/register.js"></script>
</body>


</html>
