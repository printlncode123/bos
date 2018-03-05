<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>messager的使用</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		//当页面加载完毕时触发事件
		//alert提示框
		//$.messager.alert("标题","内容","error");//三个参数分别是标题，内容，图标
		//confirm消息确认框
		//$.messager.confirm("提示信息","确定要删除此记录吗？",function(r){if(r)alert(r)});
		//show--欢迎框
		$.messager.show({
			  title:'欢迎信息',  	
			  msg:'欢迎【admin】登录成功',  	
			  timeout:4000,  	
			  showType:'slide'  
		});
	});
</script>
</head>
<body>
	
</body>
</html>