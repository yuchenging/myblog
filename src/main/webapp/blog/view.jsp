<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/8/25
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-cloud-upload"></span>&nbs
            <c:if test="${empty blogInfo}">
                发布博客
            </c:if>
            <c:if test="${!empty blogInfo}">
                修改博客
            </c:if>
        </div>
        <div class="container-fluid">
            <div class="container-fluid">
                <div class="row" style="padding-top: 20px;">
                    <div class="col-md-12">
                        <%--判断类型列表是否为空，如果为空，提示用户先添加类型--%>
                        <c:if test="${empty typeList}">
                            <h2>未查询到博客类型！</h2>
                            <h4 href="type?actionName=list">添加类型</h4>
                        </c:if>
                        <c:if test="${!empty typeList}">
                        <form class="form-horizontal" method="post" action="blog">
                            <%--设置隐藏域，存放用户行为--%>
                            <input type="hidden" name="actionName" value="addOrUpdate">
                            <%--设置隐藏域：用来存放noteId--%>
                            <input type="hidden" name="nodeId" value="${blogInfo.noteId}">
                            <div class="form-group">
                                <label for="typeId" class="col-sm-2 control-label">类别:</label>
                                <div class="col-sm-8">
                                    <select id="typeId" class="form-control" name="typeId">
                                        <option value="">请选择博客类别...</option>
                                        <c:forEach var="item" items="${typeList}">
                                            <c:choose>
                                                <c:when test="${!empty resultInfo}">
                                                    <option <c:if test="${resultInfo.result.typeId==item.typeId}">selected</c:if>value="${item.typeId}">${item.typeName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option <c:if test="${blogInfo.typeId==item.typeId}">selected</c:if>value="${item.typeId}">${item.typeName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">标题:</label>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${!empty resultInfo}">
                                            <input class="form-control" name="title" id="title" placeholder="博客标题" value="${resultInfo.result.title}">
                                        </c:when>
                                        <c:otherwise>
                                            <input class="form-control" name="title" id="title" placeholder="博客标题" value="${blogInfo.title}">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">内容:</label>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${!empty resultInfo}">
                                            <%--准备容器加载富文本编辑器--%>
                                            <textarea class="col-sm-12" id="content" name="content">${resultInfo.result.content}</textarea>
                                        </c:when>
                                        <c:otherwise>
                                            <%--准备容器加载富文本编辑器--%>
                                            <textarea class="col-sm-12" id="content" name="content">${noteInfo.content}</textarea>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-4">
                                    <input type="submit" class="btn btn-primary" onclick="return checkForm();" value="保存">
                                    &nbsp;<span id="msg" style="font-size: 12px;color: red"></span>
                                </div>
                            </div>
                        </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        
         $(function () {
             var ue=UE.getEditor("content");
         });

         /*表单校验*/
         function checkForm() {
             /*获取表单元素的值*/
             var typeId=$("#typeId").val();
             /*获取文本框的值*/
             var title=$("#title").val();
             alert(title)
             /*获取文本框的值*/
             var content=document.getElementById("content").value;
             alert(content)
             /*参数的非空判断*/
             if(isEmpty(typeId)){
                 $("#msg").html("请选择博客类型！");
                 return false;
             }
             if(isEmpty(title)){
                 $("#msg").html("博客标题不能为空！");
                 return false;
             }
             if(isEmpty(content)){
                 $("#msg").html("博客内容不能为空！");
                 return false;
             }
             return true;
         }
    </script>
</div>