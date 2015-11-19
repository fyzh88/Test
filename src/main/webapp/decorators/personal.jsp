<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="绘本 绘本馆 网上 借阅 ">
<meta name="description" content="在线借阅绘本，提供上门绘本配送服务">
<title>XXXXXXXXXXXXX</title>
<link rel="stylesheet" href="<c:url value='/static/bootstrap-3.3.4/css/bootstrap.min.css'/>"/>
<link rel="stylesheet" href="<c:url value='/static/css/main.css'/>"/>
<link rel="shortcut icon"  href="<c:url value='/static/img/logo.ico'/>"/>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<decorator:head></decorator:head>
<script type="text/javascript" src="<c:url value='/static/js/jquery-1.11.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/static/bootstrap-3.3.4/js/bootstrap.min.js'/>"></script>

</head>
<body>
	<div class="navbar navbar-default" role="navigation">
			<div class="navbar-header">
				<a href="<c:url value='/show_main'/>" class="navbar-brand">XXXX </a>
			</div>
			<div class="pull-right">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value='/show_main'/>">返回</a></li>
				</ul>
			</div>
	</div>

	<div class="container">
		<div class="row"style="margin-top:10%;">
			<div class="col-md-3">
				<ul class="list-group">
					<li class="list-group-item"><a href="#">借阅信息</a></li>
					<li class="list-group-item"><a href="#">归还记录</a></li>
					<li class="list-group-item"><a href="<c:url value='/personal_info'/>">用户信息</a></li>
					<li class="list-group-item"><a href="#">修改密码</a></li>
				</ul>
			</div>
			<div class="col-md-9">
				<decorator:body></decorator:body>
			</div>
		</div>
	</div>
</body>
</html>