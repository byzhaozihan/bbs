<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>指定论坛版块管理员</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <form action="<c:url value="/forum/setBoardManager.html" />" method="post">
                <table class="table table-hover">
                    <tr>
                        <th width="20%">论坛模块</th>
                        <td width="80%">
                            <select name="boardId">
                                <option>请选择</option>
                                <c:forEach var="board" items="${boards}">
                                    <option value="${board.boardId}">${board.boardName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
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
                        <td colspan="2" align="center">
                            <input type="submit" value="保存" class="btn btn-success"/>
                            <input type="reset" value="重置" class="btn btn-info"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>
