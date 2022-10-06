<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/8/22
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心 </div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-8">
                    <%--
                        表单类型 enctype="multipart/form-date"
                        提交方式 method="post"
                    --%>
                    <form class="form-horizontal" method="post" action="user" enctype="multipart/form-data">
                        <div class="form-group">
                            <%--设置隐藏域存放用户行为actionName--%>
                            <input type="hidden" name="actionName" value="updateUser">
                            <label for="nickName" class="col-sm-2 control-label">昵称:</label>
                            <div class="col-sm-3">
                                <input class="form-control" name="nick" id="nickName" placeholder="昵称" value="${user.nick}">
                            </div>
                            <label for="img" class="col-sm-2 control-label">头像:</label>
                            <div class="col-sm-2">
                                <input type="file" id="img" name="img">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="mood" class="col-sm-2 control-label">心情:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" name="mood" id="mood" rows="3">${user.mood}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" id="btn" class="btn btn-success" onclick="return updateUser();">修改</button>&nbsp;&nbsp;
                                <span style="color:red;font-size: 12px" id="msg"></span>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4"><img style="width:200px;height:180px" src="user?actionName=userHead&imageName=${user.head}"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $("#nickName").blur(function () {
        // 1.获取昵称文本框的值
        var nickName=$("#nickName").val();
        // 2.判断昵称是否为空
        if(isEmpty(nickName)){
            $("#msg").html("用户昵称不能为空！");
            $("#btn").prop("disabled",true);
            return;
        }

        // 3.从session作用域中获取用户昵称，判断昵称是否做了修改
        var nick='${user.nick}';
        if(nickName==nick){
            return;
        }

        // 4.如果昵称做了修改，向后台请求ajax请求，验证昵称是否可用
        $.ajax({
            type:"get",
            url:"user",
            data:{
                actionName:"checkNick",
                nick:nickName
            },
            success:function (code) {
                //如果可用，清空提示信息，按钮可用
                if(code==1){
                    $("#msg").html("");
                    $("#btn").prop("disabled",false);
                    return;
                }else {
                    //如果不可用，提示用户，并禁用按钮
                    $("#msg").html("该昵称已存在，请重新输入！");
                    $("#btn").prop("disabled",true);
                    return;
                }
            }
        })
    }).focus(function () {
        $("#msg").html("");
        $("#btn").prop("disabled",false);
        return;
    })
    /*
    *  表单提交校验：
    *  1. 满足条件，返回true,提交表单
    *  2. 不满足条件，返回false,不提交表单
    * */
    function updateUser() {
        // 1.获取昵称文本框的值
        var nickName=$("#nickName").val();
        // 2.判断昵称是否为空
        if(isEmpty(nickName)) {
            $("#msg").html("用户昵称不能为空！");
            $("#btn").prop("disabled", true);
            return false;
        }
    }
</script>
