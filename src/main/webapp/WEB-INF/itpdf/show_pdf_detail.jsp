<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<div class="col-md-6">
			<c:forEach varStatus="imgIndex" begin="0" end="0" step= "1" >
				<img alt="${requestScope.pdf_detail_book_info['name'] }" src="getPdfImageOf?bookId=${requestScope.pdf_detail_book_info['id'] }&pageIndex=${imgIndex.count}" class="img-thumbnail">
			</c:forEach>
		</div>
		<div class="col-md-6">
			<div class="well">
				<h4>书籍名称：${requestScope.pdf_detail_book_info['name'] }</h4>
				<h3>作者：${requestScope.pdf_detail_book_info['author'] }</h3>
				<h4>出版社：${requestScope.pdf_detail_book_info['publisher'] }</h4>
				<h4>页数：${requestScope.pdf_detail_book_info['pages'] } 页</h4>
				<c:set var="fileSize" value="${requestScope.pdf_detail_book_info['file_len'] }"></c:set>
				<c:if test="${fileSize>0 && fileSize< 1024 }">
					<fmt:formatNumber  var="fileSizeFormat" value="${fileSize }" pattern="#.00字节"></fmt:formatNumber>
					<h4>文件大小：${fileSizeFormat }</h4>
				</c:if>
				<c:if test="${fileSize>1024 && fileSize< 1024*1024 }">
					<fmt:formatNumber  var="fileSizeFormat" value="${fileSize/1024 }" pattern="#.00KB"></fmt:formatNumber>
					<h4>文件大小：${fileSizeFormat }</h4>
				</c:if>
				<c:if test="${fileSize>1024*1024 && fileSize< 1024*1024*1024 }">
					<fmt:formatNumber  var="fileSizeFormat" value="${fileSize/1024/1024 }" pattern="#.00MB"></fmt:formatNumber>
					<h4>文件大小：${fileSizeFormat }</h4>
				</c:if>
				<h4>分类标签：${requestScope.pdf_detail_book_info['category'] }</h4>
			</div>
			
			<div class="well">
				<h3>分享地址：<a href="${requestScope.pdf_detail_book_info['shared_addr'] }" target="_blank">获取</a></h3>
				<h3>提取码：${requestScope.pdf_detail_book_info['share_code'] }</h3>
			</div>
		</div>
	</div>
	<div class="row"><div class="col-md-12"><h4>&nbsp;</h4></div></div>
	<div class="row">
		<div class="col-md-12">
			<ul class="list-group">
				<li class="list-group-item active">
					<span class="glyphicon glyphicon-pencil"></span>
					&nbsp;简短书评</li>
				<li class="list-group-item">
					<textarea style="width:100%;" rows="5" placeholder="256个汉字以内"></textarea>
				</li>
			</ul>
			<button class="btn btn-primary pull-right">发表</button>
		</div>
	</div>
	<div class="row"><div class="col-md-12"><h4>&nbsp;</h4></div></div>
</body>
</html>