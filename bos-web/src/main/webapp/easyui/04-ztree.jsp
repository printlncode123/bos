<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs选项卡面板</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<!-- 使用div描述每个区域 -->
	<div style="height:50px" data-options="region:'south'">南</div>
	<div title="系统菜单" style="width:200px" data-options="region:'west'">  
	<!-- 制作 according折叠面板
		fit:自适应，填充父容器
	-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div data-options="iconCls:'icon-cut'" title="面板1">111</div>
			<div title="面板2">
				<!-- 展示 ztree效果-->
				<ul id="ztree1" class="ztree"></ul>
				<!--第一种：使用标准json构建ztree
				 <script type="text/javascript">
					$(function(){
						//页面加载完毕后执行这段代码，创建一个ztree
						var setting={};//参考jquery-ztree的api,官网ztree.me；{}什么都不写表示使用默认值
						//构造节点数据
						var zNodes=[
						           {"name":"节点一","children":[
						                                     {"name":"节点一_1"},
						                                      {"name":"节点一_2"},
						                                      {"name":"节点一_3"}
						                                      ]},//每个json对象表示一个节点数据
						           {"name":"节点二"},
						           {"name":"节点三"} 
						           ];
						//调用api初始化ztree
						$.fn.zTree.init($("#ztree1"), setting,zNodes);
					});
				</script>
 -->			</div>
			<div title="面板3">
				<ul id="ztree2" class="ztree"></ul>
				<!--第二种：使用简单json构建ztree -->
				<script type="text/javascript">
					/* 页面加载完毕执行这段代码 */
					var setting2={
							data:{
								simpleData:{
									enable:true
								}
							}
					};//设置使用简单json
					var zNodes2=[
					             {"id":"1","pId":"0","name":"节点一"},
					             {"id":"2","pId":"1","name":"节点一_1"},
					             {"id":"3","pId":"1","name":"节点一_2"},
					             {"id":"4","pId":"0","name":"节点二"},
					             {"id":"5","pId":"4","name":"节点二_1"},
					             {"id":"6","pId":"4","name":"节点二_2"}
					            ];
					$.fn.zTree.init($("#ztree2"),setting2,zNodes2);
				</script>
			</div>
			<div title="面板4">
				<ul id="ztree3" class="ztree"></ul>
				<!-- 发送ajax请求获取json数据构造ztree -->
				<script type="text/javascript">
					var setting3={//设置使用简单json构造ztree
							data:{
								simpleData:{
									enable:true
								}
							},
							callback:{
								onClick:function(event, treeId, treeNode){//当点击节点时动态添加选项卡
									if(treeNode.page!=undefined){//如果树的节点的page属性不是undefined就动态添加选项卡
										var e=$("#mytabs").tabs("exists",treeNode.name);//判断 title为treeNode.name的选项卡是否存在
										if(e){//如果存在就选中
											$("#mytabs").tabs("select",treeNode.name);
										}else{//如果不存在就创建选项
									 $("#mytabs").tabs("add",{
										title:treeNode.name,
										closable:true,
										content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"/>'
									}); 
										}
									}
									/* alert("你点击了"+treeId+"的"+treeNode.name+"节点"); */
									
								}
							}
					};
					var url="${pageContext.request.contextPath}/json/menu.json";
					$.post(
							url,
							{},
							function(data){
								$.fn.zTree.init($("#ztree3"),setting3,data);
							},
							"json");
				</script>
			</div>
		</div>
	</div>
	<div style="width:100px" data-options="region:'east'">东</div>
	<div data-options="region:'center'">
		<!-- 制作一个tabs选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<div title="one" data-options="closable:true">123</div>
			<div title="two" data-options="iconCls:'icon-edit'">456</div>
			<div title="three" data-options="iconCls:'icon-cut'">789</div>
		</div>
	</div>
	<div title="xxx管理系统" style="height:100px" data-options="region:'north'">北</div>
</body>
</html>