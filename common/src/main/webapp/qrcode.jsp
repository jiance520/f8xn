<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>
 <%--<%@ include file="loginControl.jsp" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>qrcode.jsp</title>
    <meta charset="UTF-8">
    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/qrcode/jquery.qrcode.js"></script>
    <script type="text/javascript" src="js/qrcode/qrcode.js"></script>?
    <script type="text/javascript" src="js/qrcode/utf.js"></script>
    <%--去除html页面中GET http://localhost:8080/favicon.ico 404 (Not Found)--%>
    <link rel="shortcut icon" href="#" />
</head>
<body>
<p>Render in table在表中渲染</p>
<div id="qrcodeTable"></div>
<br>
<br>
<p>二维码的作用就是，使用任何二维码扫瞄工具扫瞄，会打开一个链接，这个连接可以是网站，可以是下载，可以带有参数的连接</p>
<br>
<br>
<p>Render in canvas在画布中渲染</p>
<div id="qrcodeCanvas"></div>
<button id="qrCode" type="button">点击生成二维码</button>
<br>
<img id="showCode" src="">
<script>
    $(document).ready(function () {
        jQuery('#qrcodeTable').qrcode({
            render: "table", <!--二维码生成方式 -->
            text: "http://1553ve6443.imwork.net:11534/userAndPwd/alipay.jsp", <!-- 二维码内容? -->
            width: "200", //二维码的宽度
            height: "200"
        });
        jQuery('#qrcodeCanvas').qrcode({
            render: "canvas",
            text: "http://1553ve6443.imwork.net:11534/userAndPwd/alipay.jsp",
            width: "200", //二维码的宽度
            height: "200", //二维码的高度
            background: "#74fbff", //二维码的后景色
            foreground: "#264416",  //二维码的前景色
            src: 'js/qrcode/img/qrImg.jpg' //二维码中间的图片
        });
        $('#qrCode').click(function () {
            $.post('qrCodeAction',{usr:"xiao"},function (data) {
                if(data!==false&&data!==null){
                   $('#showCode').attr('src',data);
                }
            },'text');
        });
    });
</script>
</body>
</html>