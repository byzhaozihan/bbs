<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>发表新话题</title>
    <%@ include file="common/head.jsp" %>
    <script>
        function mySubmit() {
            with (document) {
                var topicTitle = getElementById("topicTitle");
                if (topicTitle.value == null || topicTitle.value.length == 0) {
                    alert("话题标题不能为空，请填上.");
                    topicTitle.focus();
                    return false;
                } else if (topicTitle.value.length > 100) {
                    alert("话题标题最大长度不能超过100个字符，请调整.");
                    boardName.focus();
                    return false;
                }

                var postText = getElementById("mainPost.postText");
                if (postText.value == null || postText.value.length < 10) {
                    alert("话题的内容必须大于10个字符");
                    postText.focus();
                    return false;
                }

                return true;
            }

        }
    </script>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <form action="<c:url value="/board/addTopic.html" />" method="post" onsubmit="return mySubmit()">
                <table class="table table-hover">
                    <tr>
                        <th width="20%">标题</th>
                        <td width="80%">
                            <input style="width:80%;" name="topicTitle" value="${topic.topicTitle}">
                    </tr>
                    <tr>
                        <th width="20%">内容</th>
                        <td width="80%">
                            <textarea style="width:100%;height:400px"
                                      name="mainPost.postText">${topic.mainPost.postText}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="保存" class="btn btn-success">
                            <input type="reset" value="重置" class="btn btn-info">
                            <input type="hidden" name="boardId" value="${boardId}">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
<!-- jquery 是 bootstrap的底层依赖，需要先引入jquery -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
