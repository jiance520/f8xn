<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">

    <title>forward.jsp</title>

  </head>

  <body>
    <!-- 用于打开网页时，先进入工程action读取数据，再跳转到展示页面 -->
    <%--<jsp:forward page="loginAction"></jsp:forward>--%>
  </body>
</html>
