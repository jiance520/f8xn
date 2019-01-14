<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String token = UUID.randomUUID().toString();//随机字符串
    session.setAttribute("token",token);
%>
<input type="hidden" name="token" value="${token}">
<%--用于防止重复提交登陆--%>