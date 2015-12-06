<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	<c:set var="colCnt" value="4"></c:set>
	<c:set var="total" value="${fn:length(requestScope.main_books_of_category_perpage)}"></c:set>
	<c:set var="pages"></c:set>
	<c:set var="rowCnt" value="${((total/colCnt) > 4 )? 4:( total/colCnt)}"></c:set>
	<c:forEach var="rowIndex"  begin="1" end="${rowCnt+1 }" step ="1" >
	<div class="row">
		<c:forEach var="colIndex"  begin="1" end="${colCnt }" step ="1">
			<div class="col-md-3">
				<c:set var="index" value="${4*(rowIndex-1)+(colIndex-1) }"></c:set>
				<a href="show_pdf?bookId=${requestScope.main_books_of_category_perpage[index]['id'] }">
				<img src="getPdfImageOf?bookId=${requestScope.main_books_of_category_perpage[index]['id'] }&pageIndex=0" 
				           class="img-thumbnail" alt="${requestScope.main_books_of_category_perpage[index]['name'] }">
					${requestScope.main_books_of_category_perpage[index]['name'] }
				</a>
			</div>
		</c:forEach>
	</div>
	</c:forEach>
	
	<c:choose>
		<c:when test="${total > (colCnt*rowCnt) }">
			<div class="col-md-12">
				 <ul class="pagination pagination">
				  <li><a href="#">&laquo;</a></li>
				  <c:forEach varStatus="number"  begin="1" end="${total/(colCnt*rowCnt) }" step="1">
				 	 <li><a href="#">${number.count }</a></li>
				  </c:forEach>
				  <li><a href="#">&raquo;</a></li>
				</ul><br>
			</div>
		</c:when>
		<c:otherwise>
			
		</c:otherwise>
	</c:choose>
	
</body>
</html>