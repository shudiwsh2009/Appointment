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
			XX预约系统-咨询师登录
		</title>
		<link type="text/css" href="css/style.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	</head>
	<body>
		<div id="page_title">
			XX预约系统-咨询师登录
		</div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<div id="page_bottom">清华大学学习发展中心</div>
		<div class="tch_login">
			请输入用户名、密码
			<br><br>
			用户名：<input id="user"></input>
			<br>
			密　码：<input id="pwd" type="password"></input>
			<br><br>
			<button type="button" onclick="commit();">确定</button>
		</div>
	</body>
	<script type="text/javascript" src="js/tch_login.js"></script>
	<script type="text/javascript">
		optimize('.tch_login');
	</script>
</html>