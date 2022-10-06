<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/8/21
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--导入核心标签库--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Myblog</title>
    <link rel="shortcut icon" href="#"/>
    <link href="statics/css/note.css" rel="stylesheet">
    <link href="statics/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="statics/css/login.css" rel="stylesheet" type="text/css" />
    <script src="statics/js/jquery-1.11.3.js" type=text/javascript></script>
    <script src="statics/bootstrap/js/bootstrap.js"></script>
    <script src="statics/js/config.js" type=text/javascript></script>
    <script src="statics/js/util.js" type=text/javascript></script>
    <script src="statics/js/regist.js" type="text/javascript"></script>
</head>
<body>

<%--<!--login box-->--%>
<div class="wrapper">
    <div id="box">
        <div class="loginbar">用户登录</div>
        <div id="tabcon">
            <div class="box show">
                <form action="user" method="post" id="loginForm">
                    <!--actionName用来识别用户行为-->
                    <input type="hidden" name="actionName" value="login"/>
                    <input type="text" class="user yahei16" id="userName" name="userName" value="${resultInfo.result.uname}" /><br /><br />
                    <input type="password" class="pwd yahei16" id="userPwd" name="userPwd" value="${resultInfo.result.upwd}" /><br /><br />
                    <input name="rem" type="checkbox" value="1"  class="inputcheckbox"/> <label>记住我</label>&nbsp; &nbsp;
                    <span id="msg" style="color: #ff0000;font-size: 12px;">${resultInfo.msg}</span><br /><br />
                    <input type="button" class="log jc yahei16" value="登 录" onclick="checkLogin()" /> <input type="button" value="注 册" class="log jc yahei16"  onclick="regist()"/>
                </form>
            </div>
        </div>
    </div>
</div>

<%--用户注册模态框--%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">用户注册</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="uname">用户名称</label>
                    <input type="text" name="uname" id="uname"><br>
                    <label for="upwd">用户密码</label>
                    <input type="password" name="upwd" id="upwd"><br>
                    <label for="nick">用户昵称</label>
                    <input type="text" name="nick" id="nick"><br>
                    <label for="mood">个性签名</label>
                    <input type="text" name="mood" id="mood"><br>
                </div>
            </div>
            <div class="modal-footer">
                <span id="tip" style="font-size: 12px;color: red"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>关闭</button>
                <button type="button" id="btn_submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-floppy-disk"></span>保存</button>
            </div>
        </div>
    </div>
</div>
<div id="flash">
    <div class="pos">
        <a bgUrl="statics/images/banner-bg2.jpg" id="flash1" ><img src="statics/images/banner-pic2.jpg"></a>
    </div>
</div>
</body>
</html>

