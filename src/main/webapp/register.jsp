<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户注册</title>
    <%@include file="WEB-INF/jsp/common/head.jsp" %>
    <script type="javascript">
        function mycheck() {
            if (document.all("user.password").value != document.all("again").value) {
                alert("两次输入密码不正确，请更正");
                return false;
            } else {
                return true;
            }
        }
    </script>
</head>
<body>
<div class="container">
    <div class="panel panel-default">

        <div class="panel-heading text-center">
            <h2>用户注册信息:</h2>
        </div>
        <div class="panel-body">
            <form action="<c:url value="/register.html"/> " method="post" onsubmit="return mycheck()">
                <c:if test="${!empty errorMsg}">
                    <div style="color=red">${errorMsg}</div>
                </c:if>
                <table class="table table-hover text-center">
                    <tr>
                        <td width="50%" align="right">用户名</td>
                        <td width="50%" align="left">
                            <input type="text" name="userName"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">密码</td>
                        <td width="50%" align="left">
                            <input type="password" name="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">密码确认</td>
                        <td width="50%" align="left"><input type="password" name="again"/>
                    </tr>
                    <tr>
                        <td colspan="2">
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
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
