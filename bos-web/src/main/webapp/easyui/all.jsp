<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<!-- 为整个页面布局用到样式easyui-layout -->
<body class="easyui-layout">
	<!-- 分为5个区域北南西东 中心 -->
	<div data-options="region:'north'" title="xxx管理系统" style="height:100px">北</div>
	<div data-options="region:'south'" style="height:50px">南</div>
	<div data-options="region:'west'" title="系统菜单" style="width:200px">
		<!-- 制作一个折叠面板使用样式easyui-accordion 
			fit:自适应，填充父容器
		-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 子div为每一个面板 -->
			<div title="面板1" data-options="iconCls:'icon-save'">
				<a id="linkb" class="easyui-linkbutton">添加一个选项卡按钮</a>
				<script type="text/javascript">
					/* 当页面加载完毕时，触发改事件 （动态添加选项卡）*/
					$(function(){
						$("#linkb").click(function(){
							/* 判断系统管理选项卡是否存在 */
							var e=$("#mytabs").tabs("exists","系统管理");
							if(e){
								//已存在，选中即可
								$("#mytabs").tabs("select","系统管理");
							}else{
								//不存在，就添加选项卡
							$("#mytabs").tabs("add",{
								title:'系统管理',
								iconCls:'icon-edit',
								closable:true,
								content:'<iframe frameborder="0" height="100%" width="100%" src="03-tabs.jsp"/>'
							});
							}
						});
					});
				</script>
			</div>
			<div title="面板2" data-options="iconCls:'icon-edit'">222</div>
			<div title="面板3" data-options="iconCls:'icon-cut'">333</div>
		</div>
	</div>
	<div data-options="region:'east'" style="width:100px">东</div>
	<div data-options="region:'center'">
		<!-- 制作一个选项卡使用样式easyui-tabss -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
		 <!-- 子div为每一个面板 -->
			<div title="面板1" data-options="iconCls:'icon-save'">111</div>
			<div title="面板2" data-options="iconCls:'icon-edit'">222</div>
			<div title="面板3" data-options="iconCls:'icon-cut'">333</div>
			<div title="面板4" data-options="closable:true">444</div>
		</div>
	</div>
</body>
</html>