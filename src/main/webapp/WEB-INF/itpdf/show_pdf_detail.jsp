<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="it pdf download  ">
<meta name="description" content="it行业pdf文件分享下载平台">
<title>ITPDF</title>
<link rel="stylesheet" href="<c:url value='/static/bootstrap-3.3.4/css/bootstrap.min.css'/>"/>
<link rel="stylesheet" href="<c:url value='/static/css/main.css'/>"/>
<link rel="shortcut icon"  href="<c:url value='/static/img/logo.ico'/>"/>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="<c:url value='/static/js/jquery-1.11.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/static/bootstrap-3.3.4/js/bootstrap.min.js'/>"></script>
</head>
<body>
	<div class="row">
	<a>${requestScope.pdf_detail_book_info['id'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['name'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['author'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['md'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['save_name'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['publisher'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['pages'] }</a><br>
	<a>${requestScope.pdf_detail_book_info['file_len'] }</a>
	</div>
</body>
</html>