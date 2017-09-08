<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String context = request.getContextPath();
    request.setAttribute("context",context);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户登录</title>
    <%@include file="WEB-INF/jsp/common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
<c:if test="${!empty errorMsg}">
    <div style="color:red">${errorMsg}</div>
</c:if>
<form action="${context}/login/doLogin.html" method="post">
    <table class="table table-hover text-center">
        <tr>
            <td width="50%" align="right">用户名</td>
            <td width="50%" align="left"><input type="text" name="userName"/></td>
        </tr>
        <tr>
            <td width="50%" align="right">密码</td>
            <td width="50%" align="left"><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="登录" class="btn btn-success"/>
                <input type="reset" value="重置" class="btn btn-info">
            </td>
        </tr>
    </table>
</form>
        </div>
    </div>
</div>
</body>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
