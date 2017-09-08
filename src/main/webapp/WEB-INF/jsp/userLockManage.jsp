<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户锁定及解锁</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <form action="<c:url value="/forum/userLockManage.html"/>" method="post">
                <table class="table table-hover">
                    <tr>
                        <th width="20%">用户</th>
                        <td width="80%">
                            <select name="userName">
                                <option>请选择</option>
                                <c:forEach var="user" items="${users}">
                                    <option value="${user.userName}">${user.userName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th width="20%">锁定/解锁</th>
                        <td width="80%">
                            <input type="radio" name="locked" value="1"/>锁定
                            <input type="radio" name="locked" value="0"/>解锁
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="保存" class="btn btn-success">
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
