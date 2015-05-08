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
		<div id="page_maintable">
			<div class="table_col" id="col1">
				<div class="table_head table_cell">时间</div>
			</div>
			<div class="table_col" id="col2">
				<div class="table_head table_cell">咨询师</div>
			</div>
			<div class="table_col" id="col3">
				<div class="table_head table_cell">状态</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div id="page_bottom">清华大学学习发展中心</div>
	</body>
	<script type="text/javascript" src="js/main_tch.js"></script>
	<script type="text/javascript">
		feedbackdata={
			teacherName:"",teacherId:"",studentName:"",problem:"",solution:"",advice:"",appId:""
		};
		getData();
	</script>
</html>
