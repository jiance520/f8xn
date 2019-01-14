<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" +  request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%--去除html页面中GET http://localhost:8080/favicon.ico 404 (Not Found)--%>
    <link rel="shortcut icon" href="#" />
    <title>SMS-test.jsp</title>
</head>
<body>
<script type="text/javascript"  src="js/jquery-1.12.4.js"></script>
<script>
    function sendCode() {
        var Number = $("#phoneNumber").val();
        $.post("code",{"phoneNumber":Number},function (data) {
            alert(data);
        },'json');
    }
    function loginSubmit() {
        var ser_form = $("#verycode_form").serialize();
        $.post("login",ser_form,function (data) {
            if(data=="登陆成功!"){
                alert(data);
                window.location.href="index.jsp";
            }else {
                alert(data);
            }
        },'json');
    }
</script>
<!--等短信模板申请下来后就可以使用这个API了，以下演示如何使用Java来调用这个API，
制作一个简单的短信验证码登录： 首先是页面的index.jsp代码：-->
<form name="verycode_form" id="verycode_form" action="login" method="post">
    <input type="text" id="phoneNumber" name="phoneNumber"  placeholder="手机号码" pattern="^1[358]\d{9}"
           required="required"/>
    <br/>
    <br/>
    <input type="text" name="code" placeholder="验证码"  required="required">
    <button type="button" onclick="sendCode()">发送验证码</button>
    <br/>
    <br/>
    <button type="button" onclick="loginSubmit()">登陆</button>
</form>
关于本站服务协议常见问题全站地图意见反馈百度推广京公网安备 11000002000001号京ICP证030173号违法和不良信息举报电话：010-59922128
</body>
</html>