<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>


<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
	    <meta name="HandheldFriendly" content="True">
	    <meta name="MobileOptimized" content="320">
	    <meta content="telephone=no" name="format-detection">
	    <meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=no">
		<title>
			学习与发展指导中心预约系统
		</title>
		<link rel="stylesheet" href="css/datepicker.css" type="text/css" />
		<script type="text/javascript" src="js/datepicker.js"></script>
		<link type="text/css" href="css/style.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	</head>
	<body background="img/bg.png"  style="text-align:center;">
		<div id="page_title">
			学习与发展指导中心预约系统-教师管理
		</div>
		<button onclick="admin_add();">新增</button>
		<button onclick="admin_delete();">删除</button>
		<button onclick="admin_cancel();">取消预约</button>
		<button onclick="admin_export();">导出选定预约</button>
		<div id="page_maintable">

		</div>
		<div id="page_bottom"><br><br>清华大学学生学习发展指导中心<br>联系方式：62792453(欧老师)</div>

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
