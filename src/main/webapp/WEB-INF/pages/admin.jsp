<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>


<!--预约_student-->
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
	    <meta name="HandheldFriendly" content="True">
	    <meta name="MobileOptimized" content="320">
	    <meta content="telephone=no" name="format-detection">
	    <meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=no">
		<title>
			欢迎使用XX预约系统
		</title>
		<link type="text/css" href="css/style.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	</head>
	<body>
		<div id="page_title">
			XX预约系统-教师管理
		</div>
		<button onclick="admin_add();">新增</button>
		<button onclick="admin_delete();">删除</button>
		<button onclick="admin_cancel();">取消预约</button>
		<button onclick="admin_export();">导出选定预约</button>
		<div id="page_maintable">				
			
		</div>
		<div id="page_bottom">清华大学学习发展中心</div>
	</body>
	<script type="text/javascript" src="js/admin.js"></script>
	<script type="text/javascript">
		feedbackdata={
			teacherName:"",teacherId:"",studentName:"",problem:"",solution:"",advice:"",appId:""
		};	
		getData();
		//addInfo();
		//optimize();
	</script>
</html>