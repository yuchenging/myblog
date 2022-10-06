/*
*  打开用户注册模态框
* */
function regist() {
    // 设置修改模态框标题
    $("#myModal").html("用户注册");
    // 清空文本框
    $("#uname").val("");
    $("#upwd").val("");
    $("#myModal").modal("show");
}

/*
* 保存按钮
* */
$("#btn_submit").click(function(){
    // 1.获取参数（文本框：类型名称；隐藏域：类型ID）
    var uname = document.getElementById("uname").value;
    var upwd = document.getElementById("upwd").value;
    var nick=document.getElementById("nick").value;
    var mood=document.getElementById("mood").value;

    // 2.判断参数是否为空（类型名称）
    if (isEmpty(uname)) {
        // 如果为空，提示信息，并return
        $("#tip").html("用户名称不能为空！");
        return;
    }else if(isEmpty(upwd)){
        $("#tip").html("用户密码不能为空！");
    }
    // 3.发送ajax请求后台，添加或修改类型记录，回调函数返回resultInfo对象
    $.ajax({
        type:"post",
        url:"regist",
        data:{
            uname:uname,
            upwd:upwd,
            nick:nick,
            mood:mood
        },
        success:function(result){
            // 如果code=1，表示更新成功，执行Dom操作
            if (result.code == 1) {
                // 关闭模态框
                $("#myModal").modal("hide");
            } else {
                $("#tip").html(result.msg);
            }
        }
    });
});