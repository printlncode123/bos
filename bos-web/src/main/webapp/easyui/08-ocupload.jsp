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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>
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
<input type="button" value="提交" id="up">
<script type="text/javascript">
	$(function(){
		$("#up").upload({
			action:"zxxxx",
			name:"myfile"
		});
	});
</script>	
</body>
</html>