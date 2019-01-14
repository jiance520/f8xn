<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>alipay.jsp</title>
    <meta charset="UTF-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <link rel="stylesheet" href="bootstrap/bootstrap.css">
    <link rel="stylesheet" href="bootstrap/bootstrap-theme.css">
    <script type="text/javascript" src="bootstrap/bootstrap.js"></script>
    <%--去除html页面中GET http://localhost:8080/favicon.ico 404 (Not Found)--%>
    <link rel="shortcut icon" href="#" />
</head>
<body>
<a class="btn btn-primary" href="javascript:void(0)">去结账</a>
<%--<input type="text" value="12" onchange="this.value"/>--%>
<script type="text/javascript">
    $(document).ready(function () {

        $(".btn.btn-primary").click(function(){
            var flag = confirm("请确认是否提交订单");
            if(flag===true){
                var total = 0.01;
                $.post("AccountsServlet",{total:total},function(data){
                    if(data='true'){
                        var vNow = new Date();
                        var sNow = "";
                        sNow += String(vNow.getFullYear());
                        sNow += String(vNow.getMonth() + 1);
                        sNow += String(vNow.getDate());
                        sNow += String(vNow.getHours());
                        sNow += String(vNow.getMinutes());
                        sNow += String(vNow.getSeconds());
                        sNow += String(vNow.getMilliseconds());
                        window.location.href='alipay.trade.page.pay.jsp?WIDout_trade_no='+sNow+"&WIDtotal_amount="+total+"&WIDsubject=Subject&WIDbody=userAndPwd";
                    }
                },"json");
            }
            else{
                return false;
            }
        });
    });
</script>

</body>
</html>
