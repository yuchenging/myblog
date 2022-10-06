<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/8/22
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
            博客列表
        </div>
        <%--判断博客列表是否存在--%>
        <c:if test="${empty page}">
            <h2>未查询到博客记录！</h2>
        </c:if>
        <c:if test="${!empty page}">
            <%--遍历获取博客列表--%>
            <div class="note_datas">
                <ul>
                    <c:forEach items="${page.dateList}" var="item">
                        <li>
                            <fmt:formatDate value="${item.pubTime}" pattern="yyyy-MM-dd"/>
                            <a href="blog?actionName=detail&noteId=${item.noteId}">${item.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <%--设置分页导航--%>
            <nav style="text-align: center">
                <ul class="pagination   center">
                    <%--如果当前页不是第一页，则显示上一页按钮--%>
                    <c:if test="${page.pageNum>1}">
                        <li>
                            <a href="index?actionName=${action}&title=${title}&pageNum=${page.prePage}&date=${date}&typeId=${typeId}">
                                <span>«</span>
                            </a>
                        </li>
                    </c:if>
                    <%--导航页数--%>
                    <c:forEach begin="${page.startNavPage}" end="${page.endNavPage}" var="p">
                        <li <c:if test="${page.pageNum==p}">class="active"</c:if>>
                            <a href="index?actionName=${action}&title=${title}&pageNum=${p}&date=${date}&typeId=${typeId}">${p}</a>
                        </li>
                    </c:forEach>
                    <%--如果当前页不是最后一页，则显示下一页按钮--%>
                    <c:if test="${page.pageNum<page.totalPages}">
                        <li>
                            <a href="index?actionName=${action}&title=${title}&pageNum=${page.nextPage}&date=${date}&typeId=${typeId}">
                                <span>»</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
    </div>

</div>