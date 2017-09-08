<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pageUrl" required="true" rtexprvalue="true" description="分页页面对应的URl" %>
<%@ attribute name="pageAttrKey" required="true" rtexprvalue="true" description="Page对象在Request域中的键名称" %>
<c:set var="pageUrl" value="${pageUrl}" />
<%
    /**
     * 原有的pageUrl中没有？那么就返回-1,sepatator的取值就为？，
     * 相当于给后面的pageNo条件添加进pageUrl
     * 如果pageUrl中有？，就相当于separator为&
     * 向原有的url条件后再添加条件就要使用&
     */
    String separator = pageUrl.indexOf("?") > -1?"&":"?";

    //之前japContext中没有相应的方法是因为只有servlet-api包
    //导入jsp-api的包后正常
    jspContext.setAttribute("pageResult", request.getAttribute(pageAttrKey));
    jspContext.setAttribute("pageUrl",pageUrl);
    jspContext.setAttribute("separator",separator);
%>

<div style="font: 12px;background-color: #DDDDDD">
    共${pageResult.totalPageCount}页，第${pageResult.currentPageNo}页
    <c:if test="${pageResult.currentPageNo <=1}">
        首页&nbsp;&nbsp;
    </c:if>
    <c:if test="${pageResult.currentPageNo >1 }">
        <a href="<c:url value="${pageUrl}"/>${separator}pageNo=1">首页</a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${pageResult.hasPreviousPage}">
        <a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.currentPageNo -1 }">上一页</a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${!pageResult.hasPreviousPage}">
        上一页&nbsp;&nbsp;
    </c:if>
    <c:if test="${pageResult.hasNextPage}">
        <a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.currentPageNo +1 }">下一页</a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${!pageResult.hasNextPage}">
        下一页&nbsp;&nbsp;
    </c:if>
    <c:if test="${pageResult.currentPageNo >= pageResult.totalPageCount}">
        末页&nbsp;&nbsp;
    </c:if>
    <c:if test="${pageResult.currentPageNo < pageResult.totalPageCount}">
        <a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.totalPageCount }">末页</a>&nbsp;&nbsp;
    </c:if>
</div>