<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加论坛版块</title>
    <%@include file="common/head.jsp" %>
    <script>
        function mySubmit() {
            with (document) {
                var boardName = getElementById("board.boardName");
                if (boardName.value == null || boardName.value.length == 0) {
                    alert("版块名称不能为空，请填上.");
                    boardName.focus();
                    return false;
                } else if (boardName.value.length > 150) {
                    alert("版块名称最大长度不能超过50个字符，请调整.");
                    boardName.focus();
                    return false;
                }

                var boardDesc = getElementById("board.boardDesc");
                if (boardDesc.value != null && boardDesc.value.length > 255) {
                    alert("版块描述最大长度不能超过255个字符，请调整.");
                    boardDesc.focus();
                    return false;
                }

                return true;
            }

        }
    </script>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<form action="<c:url value="/forum/addBoard.html"/>" method="post" onsubmit="return mySubmit()">
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <table class="table table-hover">
                    <tr>
                        <td width="20%">版块名称</td>
                        <td width="80%"><input type="text" name="boardName" style="width:60%;"/></td>
                    </tr>
                    <tr>
                        <td width="20%">版块简介</td>
                        <td width="80%">
                            <textarea style="width:80%;height:120px" name="boardDesc"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" class="btn btn-success" value="保存">
                            <input type="reset" class="btn btn-info" value="重置">
                            <input type="hidden" name="_method" value="PUT">
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
