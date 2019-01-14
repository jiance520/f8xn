<%@ page language="java" contentType="text/html; charset=UTF-8"
import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding( "utf-8" );
response.setHeader("Content-Type" , "text/html");

String rootPath = application.getRealPath( "/" );
System.out.println("-------"+rootPath);

out.write( new ActionEnter( request, rootPath).exec() );
%>

<%--<%@ page language="java" contentType="text/html; charset=UTF-8"  import="com.piesat.util.UeditorActionEnter"    pageEncoding="UTF-8"%>--%>
<%--<%@ page trimDirectiveWhitespaces="true" %>--%>
<%--<%--%>
<%--request.setCharacterEncoding( "utf-8" );--%>
<%--response.setHeader("Content-Type" , "text/html");--%>
<%--String rootPath = application.getRealPath( "/" );--%>
<%--out.write( new UeditorActionEnter( request, rootPath ).exec() );--%>
<%--%>--%>

