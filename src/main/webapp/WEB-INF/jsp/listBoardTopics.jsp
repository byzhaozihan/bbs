<%@ page import="java.util.Iterator" %>
<%@ page import="com.zzh.domain.Board" %>
<%@ page import="com.zzh.domain.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="baobaotao" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>论坛版块页面</title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <table class="table table-hover">
                <c:set var="isboardManager" value="${false}" scope="request"/>

                <%
                    User user = (User) session.getAttribute("USER_CONTEXT");
                    if (user == null) {
                        
                        response.sendRedirect("/forum/login.jsp");
                    } else {
                        Board board = (Board) request.getAttribute("board");
                        Iterator<Board> boardIterator = user.getManBoards().iterator();
                        while (boardIterator.hasNext()) {
                            if (board.getBoardId() == boardIterator.next().getBoardId()) {
                                request.setAttribute("isboardManager", true);
                            }
                        }
                    }


                    /**
                     *  之前用这个版块管理员不起作用，怀疑Set集合跟forEach有冲突
                     *  <c:forEach items="${USER_CONTEXT.manBoards}" var="manBoard">
                     *      <c:if test="${manBoard.boardId} == board.boardId">
                     *          <c:set var="isboardManager" value="${true}"/>
                     *      </c:if>
                     *   </c:forEach>
                     */
                %>
                <thead>
                <c:if test="${USER_CONTEXT.userType == 2||isboardManager}">
                    <td></td>
                </c:if>
                <td bgcolor="#EEEEEE">
                    ${board.boardName}
                </td>
                <td colspan="4" bgcolor="#EEEEEE" align="right">
                    <a href="<c:url value="/board/addTopicPage-${board.boardId}.html"/> ">发表新话题</a>
                </td>
                </thead>
                <tbody>
                <tr>
                    <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
                        <td>
                            <script>
                                function switchSelectBox() {
                                    var selectBoxs = document.all("topicIds");
                                    if (!selectBoxs) return;
                                    if (typeof(selectBoxs.length) == "undefined") {//only one checkbox
                                        selectBoxs.checked = event.srcElement.checked;
                                    } else {//many checkbox ,so is a array
                                        for (var i = 0; i < selectBoxs.length; i++) {
                                            selectBoxs[i].checked = event.srcElement.checked;
                                        }
                                    }
                                }
                            </script>
                            <input type="checkbox" onclick="switchSelectBox()"/>
                        </td>
                    </c:if>
                    <td width="50%">
                        标题
                    </td>
                    <td width="10%">
                        发表人
                    </td>
                    <td width="10%">
                        回复数
                    </td>
                    <td width="15%">
                        发表时间
                    </td>
                    <td width="15%">
                        最后回复时间
                    </td>
                </tr>


                <!-- pagedTopic中没有result属性，只有他的getter方法 -->
                <c:forEach var="topic" items="${pagedTopic.result}">
                    <tr>
                        <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
                            <td><input type="checkbox" name="topicIds" value="${topic.topicId}"/></td>
                        </c:if>
                        <td>
                            <a href="<c:url value="/board/listTopicPosts-${topic.topicId}.html"/>">
                                <c:if test="${topic.digest > 0}">
                                    <font color=red>★</font>
                                </c:if>
                                    ${topic.topicTitle}
                            </a>
                        </td>
                        <td>
                                ${topic.user.userName}
                            <br>
                            <br>
                        </td>
                        <td>
                                ${topic.replies}
                            <br>
                            <br>
                        </td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MM-dd"
                                            value="${topic.createTime}"/>
                        </td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MM-dd"
                                            value="${topic.lastPost}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div align="center">
<baobaotao:PageBar pageUrl="/board/listBoardTopics-${board.boardId}.html" pageAttrKey="pagedTopic"/>
<c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
    <script>
        function getSelectedTopicIds() {
            var selectBoxs = document.all("topicIds");
            if (!selectBoxs) return null;
            if (typeof(selectBoxs.length) == "undefined" && selectBoxs.checked) {
                return selectBoxs.value;
            } else {//many checkbox ,so is a array
                var ids = "";
                var split = "";
                for (var i = 0; i < selectBoxs.length; i++) {
                    if (selectBoxs[i].checked) {
                        ids += split + selectBoxs[i].value;
                        split = ",";
                    }
                }
                return ids;
            }
        }
        function deleteTopics() {
            var ids = getSelectedTopicIds();
            if (ids) {
                var url = "<c:url value="/board/removeTopic.html"/>?topicIds=" + ids + "&boardId=${board.boardId}";
                //alert(url);
                location.href = url;
            }
        }
        function setDefinedTopis() {
            var ids = getSelectedTopicIds();
            if (ids) {
                var url = "<c:url value="/board/makeDigestTopic.html"/>?topicIds=" + ids + "&boardId=${board.boardId}";
                location.href = url;
            }
        }
    </script>

        <input type="button" value="删除" class="btn btn-danger" onclick="deleteTopics()">
        <input type="button" value="置精华帖" class="btn btn-info" onclick="setDefinedTopis()">
    </div>
</c:if>
</body>
<!-- jquery 是 bootstrap的底层依赖，需要先引入jquery -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
