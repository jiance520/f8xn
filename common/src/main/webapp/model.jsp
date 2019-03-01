<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<%-- <%@ include file="loginControl.jsp" %> --%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>model.jsp</title>
    <meta charset="UTF-8">
    <meta name="keywords" content="帐号,账号,密码,竞拍,拍卖,估价,收藏,信用,二手,捐赠,password,哲学,f8xn"><!--网站关键字-->
    <meta name="description" content="提供在线帐号密码服务"><!--网站内容描述-->
    <meta name="Robots" Content="all"><!--默认all，文件将被检索，且页面上的链接可以被查询；其它none|index|noindex|follow|nofollow-->
    <meta name="expires" content="Mon,14 Oct 2020 00:20:00 GMT"><!--设定网页的到期时间，一旦过期则必须到服务器上重新调用。必须使用GMT时间格式-->
    <meta name="author" content="jiance520"><!--网站作者-->
    <meta name="generator" content="javascript,html,jsp"><!--网站使用的工具-->
    <meta name="coryright" content="未经许可，不得转载">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"><!--设置移动端自适应-->
    <meta http-equiv="content-type" content="text/html;charset=utf-8"><!--网站显示语言编码-->
    <!--<meta http-equiv="Content-Language" contect="zh-CN">&lt;!&ndash;网站显示语言编码&ndash;&gt;-->
    <meta http-equiv="refresh" content="3600;url=http://www.f8xn.top"><!--定时网站3600秒后，跳转到http://www.f8xn.top，达到更新-->
    <meta http-equiv="Pragma" contect="no-cache"><!--设定禁止从缓存中调阅页面内容，一旦离开网页就无法从Cache中再调出，后退需要重加载，用于jsp动态页面-->
    <meta http-equiv="set-cookie" contect="Mon,14 Oct 2020 00:20:00 GMT"><!--cookie设定，如果网页过期，存盘的cookie将被删除。必须使用GMT时间格式，用于jsp-->
    <!--<meta http-equiv="Pics-label" contect="">&lt;!&ndash;网页等级评定，在IE的internet选项中有一项内容设置，可以防止浏览一些受限制的网站&ndash;&gt;-->
    <!--<meta http-equiv="windows-Target" contect="_top">&lt;!&ndash;强制页面在当前窗口中以独立页面显示，可以防止自己的网页被别人当作一个frame页调用；&ndash;&gt;-->
    <!--<meta http-equiv="Page-Enter" contect="revealTrans(duration=10,transtion=50)">和<meta    http-equiv="Page-Exit"    contect="revealTrans(duration=20，transtion=6)">&lt;!&ndash;设定进入和离开页面时的特殊效果，不过所加的页面不能够是一个frame页面。&ndash;&gt;-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <%--百度ueditor的css、js引用文件--%>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>
    <link href="ueditor/themes/default/css/ueditor.css" rel="stylesheet"><!--非必须 导入js或者css的顺序一定不要互换，-->
    <%--boostrap的css、js引用文件，与ueditor有冲突--%>
    <script type="text/javascript" src="bootstrap/bootstrap.js"></script>
    <link rel="stylesheet" href="bootstrap/bootstrap.css">
    <link rel="stylesheet" href="bootstrap/bootstrap-theme.css">
    <%--&lt;%&ndash;easyUI引用的css、js文件&ndash;%&gt;--%>
    <script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyUI/datagrid-detailview.js"></script>
    <link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">
    <%--&lt;%&ndash;echarts引用的css、js文件&ndash;%&gt;--%>
    <script src="echarts/dist/echarts-all.js"></script>
    <%--vue引用的css、js文件--%>
    <script type="text/javascript" src="vue/vue.js" ></script>
    <script type="text/javascript" src="vue/axios.js" ></script>
    <script type="text/javascript" src="vue/vue-resource.js" ></script>
    <%--&lt;%&ndash;二维码引用的js&ndash;%&gt;--%>
    <script type="text/javascript" src="js/qrcode/jquery.qrcode.js" ></script>
    <script type="text/javascript" src="js/qrcode/qrcode.js" ></script> 
    <script type="text/javascript" src="js/qrcode/utf.js" ></script>
    <%--去除html页面中GET http://localhost:8080/favicon.ico 404 (Not Found)--%>
    <link rel="shortcut icon" href="#" />
    <style type="text/css"></style>
</head>
<body>
<input type="text" value="12" placeholder="请输入"/>
<script type="text/javascript">
    var obj = (1);
    var ob = /2/;
</script>
<br>
<div style="display:flex;justify-content: center;padding:20px 0;font: normal 20px/20px 微软雅黑;">
    <div style="display: inline-block;">
        <a style="display:inline-block;text-decoration: none;" target="_blank"
           href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=43050302000163">
            <img style="float:left;line-height: 20px" src="img/beian.png"/><span
                style="float:left;display:inline-block;color:#939393;">湘公网安备 43050302000163号</span></a>
        <a style="vertical-align:top;display:inline-block;text-decoration: none" target="_blank"
           href="http://www.miitbeian.gov.cn/">
            <span style="float:left;color:#939393;">湘ICP备18023926号-1</span></a>
    </div>
</div>
</body>
</html>
