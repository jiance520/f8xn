<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.action.*,com.dao.*,com.entity.*,com.service.*,com.service.impl.*" %>
<%-- <%@ include file="loginControl.jsp" %> --%>
	<c:if test="${empty sessionScope.tel }">
	    <!-- sessionID失效或者没有登陆成功，则不能访问个人主页。不为null，不需要再加else，停留在当前页面 -->
		<c:redirect url="SMS-test.jsp"></c:redirect>
	</c:if>
  	
