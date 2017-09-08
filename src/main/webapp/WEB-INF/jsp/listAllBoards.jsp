<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>论坛首页</title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <c:if test="${USER_CONTEXT.userType == 2}">
                    <th>
                        <script>
                            function switchSelectBox() {
                                var selectBoxs = document.all("boardIds");
                                if (!selectBoxs) return;
                                if (typeof (selectBoxs.length) == "undefined") {
                                    selectBoxs.checked = event.srcElement.checked;
                                } else {
                                    for (var i = 0; i < selectBoxs.length; i++) {
                                        selectBoxs[i].checked = event.srcElement.checked;
                                    }
                                }
                            }
                        </script>
                        <input type="checkbox" onclick="switchSelectBox()"/>
                    </th>
                </c:if>
                <th width="20%">版块名称</th>
                <th width="70%">版块简介</th>
                <th width="10%">主题帖数</th>
                </thead>
                <tbody>
                <c:forEach var="board" items="${boards}">
                    <tr>
                        <c:if test="${USER_CONTEXT.userType == 2}">
                        <td><input type="checkbox" name="boardIds" value="${board.boardId}"/>
                            </c:if>
                        <td>
                            <a href="<c:url value="/board/listBoardTopics-${board.boardId}.html"/> ">${board.boardName}</a>
                        </td>
                        <td>
                                ${board.boardDesc}
                        </td>
                        <td>
                                ${board.topicNum}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
    <script>
        function getSelectedBoardIds() {
            var selectBoxs = document.all("boardIds");
            if (!selectBoxs) return null;
            if (typeof(selectBoxs.length) == "undefined" && selectBoxs.checked) {
                return selectBoxs.value;
            } else {//many checkbox ,so is a array
                var ids = "";
                var split = ""
                for (var i = 0; i < selectBoxs.length; i++) {
                    if (selectBoxs[i].checked) {
                        ids += split + selectBoxs[i].value;
                        split = ",";
                    }
                }
                return ids;
            }
        }
        function deleteBoards() {
            var ids = getSelectedBoardIds();
            if (ids) {
                var url = "<c:url value="/board/removeBoard.html"/>?boardIds=" + ids + "";
                //alert(url);
                location.href = url;
            }
        }

    </script>
    <input type="button" value="删除" class="btn btn-danger center-block"  onclick="deleteBoards()"/>
</c:if>
</body>
<!-- jquery 是 bootstrap的底层依赖，需要先引入jquery -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
