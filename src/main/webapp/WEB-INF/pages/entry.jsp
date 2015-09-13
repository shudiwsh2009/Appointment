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
		<link type="text/css" href="css/style.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
				<style>
		.title{
			background-color: rgb(245,246,251);
			color:rgb(204,207,214);
			width: 100%;
			padding: 4mm 0 4mm 6mm;
		}
		#page_bottom{
			position: fixed;
			bottom: 0px;
			background-color: white;
			padding: 3mm 0 3mm 0;
			width: 100%;
		}
		.choose_mode{
			width: 100%;
			height: 20mm;
		}
		.user_type{
			padding: 8mm 0 0 1em;
			float: left;
			color: #888;
		}
		.enter{
			float: right;
			padding: 8mm 4mm 0 0;
			color: #ddd;
		}
		.main_container{
			background-color: white;
			border-top:0.5mm solid rgb(224,227,224);
			border-bottom:0.5mm solid rgb(224,227,224);
			padding: 0 0 0 3mm;
		}
		body{
			background-color: rgb(243,247,248);
			font-size: 4mm;
		}
		.clearfix{
			clear: both;
		}

		</style>
	</head>
	<body>
		<div class="title">
			学习与发展指导中心预约系统
		</div>
		<div class="main_container">
			<a href="#">
				<div class="choose_mode">
					<img style="float:left;height:20mm;" src="img/student.png"><div class="user_type">学生预约</div><div class="enter">进入 ＞</div>
					<div class="clearfix"></div>
				</div>
			</a>

			<div style="border-bottom:1px solid #ddd;text-align:center;"></div>

			<a href="#">
				<div class="choose_mode">
					<img style="float:left;height:20mm;" src="img/teacher.png"><div class="user_type">咨询师登录</div><div class="enter">进入 ＞</div>
					<div class="clearfix"></div>
				</div>
			</a>

		</div>

		<div id="page_bottom">清华大学学生学习与发展指导中心<br>联系方式：62792453(欧老师)</div>
	</body>
	<script type="text/javascript" src="js/tch_login.js"></script>
	<script type="text/javascript">
		optimize('.tch_login');
	</script>
</html>
