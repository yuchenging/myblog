<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/8/27
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-eye-open"></span>&nbsp;查看博客
        </div>
        <div>
            <div class="note_title"><h2>${blog.title}</h2></div>
            <div class="note_info">
                发布时间：<fmt:formatDate value="${blog.pubTime}" pattern="yyyy-MM-dd HH:mm"/> &nbsp;&nbsp;
                云记类别：${blog.typeName}
            </div>
            <div class="note_content">
                <p>${blog.content}</p>
            </div>
            <div class="note_btn">
                <button class="btn btn-primary" type="button" onclick="updateBlog(${blog.noteId})">修改</button>
                <button class="btn btn-danger" type="button" onclick="deleteBlog(${blog.noteId})">删除</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function deleteBlog(noteId) {
        swal({
            title: "",  // 标题
            text: "<h3>您确认要删除该记录吗？</h3>", // 内容
            type: "warning", // 图标  warning
            showCancelButton: true,  // 是否显示取消按钮
            confirmButtonColor: "orange", // 确认按钮的颜色
            confirmButtonText: "确定", // 确认按钮的文本
            cancelButtonText: "取消" // 取消按钮的文本
        }).then(function () {
            // 用户确认删除，发送ajax请求
            $.ajax({
                type: "post",
                url:"blog",
                data:{
                    actionName:"delete",
                    noteId:noteId,
                },
                success:function (code) {
                    // 判断是否删除成功
                    if(code==1){
                        // 跳转到首页
                        window.location.href="index";
                    }else {
                        swal("","<h3>删除失败！</h3>","error");
                    }
                }
            })
        });
    }

    /*
    * 进入发布博客页面
    * */
    function updateBlog(noteId) {
        window.location.href="blog?actionName=view&noteId="+noteId;
    }
</script>