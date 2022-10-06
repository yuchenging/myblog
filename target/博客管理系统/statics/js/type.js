
// ================删除类型操作=======================

function deleteType(typeId){
	// 弹出提示框询问用户是否确认删除
	swal({ 
		  title: "",  // 标题
		  text: "<h3>您确认要删除该记录吗？</h3>", // 内容
		  type: "warning", // 图标  warning
		  showCancelButton: true,  // 是否显示取消按钮
		  confirmButtonColor: "orange", // 确认按钮的颜色
		  confirmButtonText: "确定", // 确认按钮的文本
		  cancelButtonText: "取消" // 取消按钮的文本
	}).then(function(){
		// 如果是，发送ajax请求后台（类型ID）
		$.ajax({
			type:"post",
			url:"type",
			data:{
				actionName:"delete",
				typeId:typeId
			},
			success:function(result){
				// 判断是否删除成功
				if (result.code == 1) {
					// 提示成功
					swal("","<h2>删除成功！</h2>","success");
					// 执行成功的DOM操作
					deleteDom(typeId);
				} else {
					// 提示用户失败   swal("标题","内容","图标")
					swal("","<h3>"+result.msg+"</h3>","error");
				}
			}
		});
		
	});
}

/**
 * 删除类型的DOM操作
 */
function deleteDom(typeId) {
	// 1.通过id属性值，得到表格对象
	var myTable=$("#myTable");
	// 2.得到table元素的子元素tr的值
	var trLength = $("#myTable tr").length;
	// 判断tr的数量是否等于2
	if (trLength == 2) {
		// 如果等于2，表示只有一条类型集合，删除整个表格，并设置提示内容
		$("#myTable").remove();
		// 设置提示信息
		$("#myDiv").html("<h2>未查询到相关数据！</h2>");
	} else {
		// 如果大于2，表示有多条类型记录，删除指定tr对象
		$("#tr_" + typeId).remove();
	}
	
	/* 2、删除左侧类型分组的导航栏列表项 */
	$("#li_" + typeId).remove();
}
//===========修改===============

/**
 *	打开修改模态框
 */
function openUpdateDialog(typeId) {
	// 设置修改模态框标题
	$("#myModalLabel").html("修改类型");
	// 通过id选择器，获取当前的tr对象
	var tr = $("#tr_" + typeId);
	// 得到tr具体单元格的值（第二个td）
	var typeName=tr.children().eq(1).text();
	// 将类型名称设置给模态框的文本框
	$("#typeName").val(typeName);
	// 得到要修改的记录的类型id（第一个td)
	var type_id = tr.children().eq(0).text();
	// 将类型ID设置到模态框的隐藏域中
	$("#typeId").val(type_id);
	// 清空提示信息
	$("#msg").text("");
	// 打开模态框
	$("#myModal").modal("show");
}


/**
 * 打开添加模态框
 */
$("#addBtn").click(function(){
	// 设置添加模态框标题
	$("#myModalLabel").html("新增类型");
	// 清空模态框的文本框和隐藏域的值
	$("#typeId").val("");
	$("#typeName").val("");
	// 清空提示信息
	$("#msg").text("");
	// 打开模态框
	$("#myModal").modal("show");
});

/**
 * 添加或修改的保存按钮
 */		
$("#btn_submit").click(function(){
	// 1.获取参数（文本框：类型名称；隐藏域：类型ID）
	var typeId = document.getElementById("typeId").value;
	var typeName = document.getElementById("typeName").value;
	
	// 2.判断参数是否为空（类型名称）
	if (isEmpty(typeName)) {
		// 如果为空，提示信息，并return
		$("#msg").html("类型名称不能为空！");
		return;
	}
	// 3.发送ajax请求后台，添加或修改类型记录，回调函数返回resultInfo对象
	$.ajax({
		type:"post",
		url:"type",
		data:{
			actionName:"addOrUpdate",
			typeId:typeId,
			typeName:typeName
		},
		success:function(result){
			// 如果code=1，表示更新成功，执行Dom操作
			if (result.code == 1) {
				// 关闭模态框
				$("#myModal").modal("hide");
				// 判断typeId是否为空
 				if (isEmpty(typeId)) {
 					// 为空，表示执行的是添加的DOM操作
 					var key = result.result; // 后台添加记录成功后返回的主键
 					// 如果为空，执行添加的DOM操作
 					addDom(key,typeName);
 				} else {
 					// 如果不为空，执行修改的DOM操作
 					updateDom(typeId,typeName);
 				}
				
			} else {
				$("#msg").html(result.msg);
			}
		}
	});
	
});

/**
 * 修改类型的DOM操作
 */
function updateDom(typeId,typeName) {
	// 通过id选择器获取tr记录
	var tr = $("#tr_" + typeId);
	// 修改指定单元格的文本值
	tr.children().eq(1).text(typeName);
	// 给左侧类型名添加span标签，并设置id属性值，修改该span元素的文本值	
	$("#sp_"+typeId).html(typeName);
}


/**
 * 添加类型的DOM操作
*/
function addDom(typeId,typeName) {
	/* 1.添加tr记录  */
	// 拼接tr元素
	var tr = '<tr id="tr_'+typeId+'"><td>'+typeId+'</td><td>'+typeName+'</td>';
	tr += '<td><button class="btn btn-primary" type="button" onclick="openUpdateDialog('+typeId+')">修改</button>&nbsp;';
	tr += '<button class="btn btn-danger del" type="button" onclick="deleteType('+typeId+')">删除</button></td></tr>';
	
	// 通过表格的id属性获取表格元素对象
	var myTable = $("#myTable");
	// 判断表格对象的length是否大于0 （大于0 ，则表示表格存在；否则表格不存在）
	if (myTable.length > 0) {
		// 如果表格存在，则拼接tr记录，将tr对象追加到table对象中
		myTable.append(tr);
	} else {
		// 如果表格不存在，则拼接表格及tr对象，将表格设置到div中
		myTable =' <table id="myTable" class="table table-hover table-striped">';
		myTable += '<tbody> <tr> <th>编号</th> <th>类型</th> <th>操作</th> </tr>';
		myTable += tr + '</tbody></table>';
		
		$("#myDiv").html(myTable);
	}
	/* 2、左侧类型分组导航栏的列表项 */
	// 拼接li元素
	var li ='<li id="li_'+typeId+'"><a href=""><span id="sp_'+typeId+'">'+typeName+' </span><span class="badge">0</span></a></li>';
	// 追加到ul元素中
	$("#typeUl").append(li);
}


