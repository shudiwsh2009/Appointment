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
			学习与发展指导中心预约系统
		</title>
		<link rel="stylesheet" href="css/datepicker.css" type="text/css" />
		<link type="text/css" href="css/style.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="js/datepicker.js"></script>
	</head>
	<body background="img/bg.png" style="text-align:center;">
		<div id="page_title">
			学习与发展指导中心预约系统-咨询师管理
		</div>
		<button onclick="teacher_add();">新增</button>
		<button onclick="teacher_delete();">删除</button>
		<button onclick="teacher_cancel();">取消预约</button>
		<button onclick="teacher_logout();" style="float:right;margin-right:10px">退出登录</button>

		<div id="page_maintable" style="font-size:10px;">
			<div class="table_col" id="col0">
				<div class="table_head table_cell" id="head_0"><button onclick="$('.checkbox').click();" style="padding:0px;">全选</button></div>
			</div>
			<div class="table_col" id="col1">
				<div class="table_head table_cell">时间</div>
			</div>
			<div class="table_col" id="col2">
				<div class="table_head table_cell">咨询师</div>
			</div>
			<div class="table_col" id="col5">
				<div class="table_head table_cell">电话</div>
			</div>
			<div class="table_col" id="col3">
				<div class="table_head table_cell">状态</div>
			</div>
			<div class="table_col" id="col4">
				<div class="table_head table_cell">状态</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div id="page_bottom"><br><br>清华大学学生学习发展指导中心<br>联系方式：62792453(欧老师)</div>

	</body>
	<script type="text/javascript" src="js/main_tch.js"></script>
	<script type="text/javascript">
		feedbackdata={
			teacherName:"",teacherId:"",studentName:"",problem:"",solution:"",advice:"",appId:""
		};
		getData();
	</script>
</html>
