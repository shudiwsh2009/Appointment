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
	</head>
	<body>
			<div id="page_title">
				学习与发展指导中心预约系统
			</div>

			<div class="tch_login">
				请选择用户类型
				<br><br>
				<a href="/student">
				<button type="button" style="width:100px;font-size:16px;margin-bottom:10px;">我是学生</button></a>
				<br>
				<a href="/teacher">
				<button type="button" style="width:100px;font-size:16px;">我是咨询师</button></a>
				<br>
			</div>
			<div id="page_bottom"><br><br>清华大学学生学习与发展指导中心<br>联系方式：62792453(欧老师)</div>
		</body>
	<script type="text/javascript" src="js/tch_login.js"></script>
	<script type="text/javascript">
		optimize('.tch_login');
	</script>
</html>
