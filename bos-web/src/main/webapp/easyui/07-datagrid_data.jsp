<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid使用</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<!-- 通过发送请求获取json创建datagrid -->
	<%-- <table class="easyui-datagrid" data-options="url:'${pageContext.request.contextPath}/json/datagrid_data.json'">
		<thead>
			<th data-options="field:'id'">编号</th>
			<th data-options="field:'name'">姓名</th>
			<th data-options="field:'age'">性别</th>
		</thead>
	</table> --%>
	<hr>
<!-- 写js,通过easy-datagrid的api创建datagrid-->
<table id="myTable"></table>
<script type="text/javascript">
	$(function(){
		$("#myTable").datagrid({
			columns:[[
			          {title:'编号',field:'id',checkbox:true},
			          {title:'姓名',field:'name'},
			          {title:'年龄',field:'age'}
			         ]],
			url:'${pageContext.request.contextPath}/json/datagrid_data.json',
			rownumbers:true,
			singleSelect:true,//只能选一行
			toolbar:[
			         {text:'添加',iconCls:'icon-add',
						//通过easyui-datagrid提供的handler为工具栏中的按钮绑定时间			        	 
			        	 handler:function(){
			        	 alert("add....");
			         }},
			         {text:'删除',iconCls:'icon-remove'},
			         {text:'修改',iconCls:'icon-edit'},
			         {text:'查询',iconCls:'icon-search'}
			         ],
			 pagination:true
		});
	});
</script>	
</body>
</html>