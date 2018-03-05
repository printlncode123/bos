<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>layout布局</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>

<body class="easyui-layout">
	<!--
	 <select>
	 	<option value="id">zhangsna</option>
	</select>
	 -->
	<!--valueField相当于select下拉框option标签的value的值(id)；textField相当于select下拉框页面显示的值 (zhangsna) -->
	<input class="easyui-combobox" 
	data-options="url:'${pageContext.request.contextPath}/json/combobox.json',valueField:'id',textField:'name',editable:false">
</body>
</html>