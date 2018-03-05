<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		alert("查看...");
	}
	
	function doDelete(){
		var rows=$("#grid").datagrid("getSelections");//getSelections返回表格中选中的行(数组)
		if(rows.length==0){
			$.messager.alert("提示信息","请选择要删除的取派人员","warning");
		}else{//弹出确认框是否删除
			$.messager.confirm("确认删除","是否要删除选中的取派员",function(r){
				if(r){//确认发送请求删除
					var array=new array();
					for(var k=0;k<rows.length;k++){//循环遍历选中的行
						var id=rows[k].id;
						array.push(id);//将选中行的id循环压到array数组中
					}
					var ids=array.join(",");//使用逗号，将数组中的id链接
					location.href="staffAction_deleteBatch.action?ids="+ids;//不适合发送ajax请求因为ajax不刷新页面，所以这里用同步请求来刷新页面
				}
			});
		}
	}
	
	function doRestore(){
		alert("将取派员还原...");
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, 
	 <shiro:hasPermission name="staff-delete">
	{
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},
	</shiro:hasPermission> 
	
	/* {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	} */
	
	{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="0"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [10],
			pagination : true,
			toolbar : toolbar,
			url : "staffAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 设置修改取派员窗口的属性
		$('#editStaffWindow').window({
	        title: '修改取派员信息',
	        width: 400,
	        modal: true,//弹出框时遮罩底部，底色灰暗
	        shadow: true,//是否有阴影
	        closed: true,//默认是关闭的
	        height: 400,
	        resizable:false//是否可以变换大小
	    });
	});

	function doDblClickRow(rowIndex, rowData){
		$('#editStaffWindow').window("open");//双击表格时打开修改窗口
		$('#editStaffForm').form("load",rowData);//将选中行的数据回显在弹出框中
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm" action="staffAction_save.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
						<!-- 自定义校验规则 -->
						<script>
							$(function(){
								/*为保存按钮添加点击事件  */
								$("#save").click(function(){
									//判断表单中的内容是否有效
									var v=$("#addStaffForm").form("validate");
									if(v){
										//$("#addStaffForm").form("submit");//不会刷新页面
										$("#addStaffForm").submit();//刷新页面
									}
								});
								var reg=/^1[3|4|5|7|8][0-9]{9}$/;
								$.extend($.fn.validatebox.defaults.rules, { 
									telephone: { //telephone校验的规则名称
									validator: function(value, param){ 
									return reg.test(value); 
									}, 
									message: '手机号输入错误' 
									} 

									}); 
							});
						</script>
					    <!-- data-options="validType:'telephone'"确定使用的校验规则 -->
						<input type="text" name="telephone" data-options="validType:'telephone'" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<!-- 弹出修改取派员窗口 -->
	<div class="easyui-window" title="对收派员进行添加或者修改" id="editStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
			</div>
		</div>
	
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm" action="staffAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
						<!-- 自定义校验规则 -->
						<script>
							$(function(){
								/*为保存按钮添加点击事件  */
								$("#edit").click(function(){
									//判断表单中的内容是否有效
									var v=$("#editStaffForm").form("validate");
									if(v){
										//$("#addStaffForm").form("submit");//不会刷新页面
										$("#editStaffForm").submit();//刷新页面
									}
								});
								var reg=/^1[3|4|5|7|8][0-9]{9}$/;
								$.extend($.fn.validatebox.defaults.rules, { 
									telephone: { //telephone校验的规则名称
									validator: function(value, param){ 
									return reg.test(value); 
									}, 
									message: '手机号输入错误' 
									} 

									}); 
							});
						</script>
					    <!-- data-options="validType:'telephone'"确定使用的校验规则 -->
						<input type="text" name="telephone" data-options="validType:'telephone'" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
		
			</form>
		</div>
	</div>
</body>
</html>	