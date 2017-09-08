<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="baobaotao" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${topic.topicTitle}</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <table class="table table-hover">
                <c:forEach var="post" items="${pagedPost.result}">
                    <tr>
                        <th colspan="2">${post.postTitle}</th>
                    </tr>
                    <tr>
                        <td width="20%">
                            用户：${post.user.userName}<br>
                            积分：${post.user.credit}<br>
                            时间：<fmt:formatDate value="${post.createTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>${post.postText}</td>
                    </tr>
                </c:forEach>
            </table>
            <div align="center">
                <baobaotao:PageBar pageUrl="/board/listTopicPosts-${topic.topicId}.html"
                                   pageAttrKey="pagedPost"/>
            </div>
            <script>
                function mySubmic() {
                    with (document) {
                        var postTitle = getElementById("post.postTile");
                        if (postTitle.value != null && postTitle.value.length > 50) {
                            alert("帖子标题最大长度不能超过50个字符，请调整");
                            postTitle.focus();
                            return false;
                        }

                        var postText = getElementById("post.postText");
                        if (postText.value == null || postText.value.length < 10) {
                            alert("回复帖子内容不能小于10个字符。");
                            postText.focus();
                            return false;
                        }

                        return true;
                    }
                }
            </script>
            <form action="<c:url value="/board/addPost.html"/> " method="post" onsubmit="return mySubmic()">
                <h3 align="center">回复</h3>
                <hr>
                <table class="table table-hover">
                    <tr>
                        <th width="20%">标题</th>
                        <td width="80%"><input type="text" name="postTitle" style="width:100%"/></td>
                    </tr>
                    <tr>
                        <th width="20%">内容</th>
                        <td width="80%"><textarea style="width:100%;height:100px" name="postText"></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="保存" class="btn btn-success">
                            <input type="reset" value="重置" class="btn btn-info">
                            <input type="hidden" name="boardId" value="${topic.boardId}">
                            <input type="hidden" name="topicId" value="${topic.topicId}"/>
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
