<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<c:set var="rowCnt" value="5"></c:set>
	<c:set var="totalAll" value="${requestScope.total_in_category }"></c:set>
	<c:set var="total" value="${fn:length(requestScope.main_books_of_category_perpage)}"></c:set>
	
	<c:set var="totalOnePage" value="${colCnt*rowCnt }"></c:set>
	<c:set var="modeV" value="${(totalAll%totalOnePage)}"></c:set>
	<c:set var="result" value="${(totalAll-modeV)/totalOnePage }"></c:set>
	<c:set var="pageCnt" value="${result+(modeV==0?0:1)}"></c:set>
	<fmt:formatNumber type="number" value="${pageCnt}" maxFractionDigits="0" var="pageCntInt"></fmt:formatNumber>
	<c:set var="rowCntReal" value="${total/colCnt+((total%colCnt==0)?0:1)}"></c:set>
	<c:forEach var="rowIndex"  begin="1" end="${rowCntReal }" step ="1" >
	<div class="row">
		<c:forEach var="colIndex"  begin="1" end="${colCnt }" step ="1">
			<c:set var="index" value="${colCnt*(rowIndex-1)+(colIndex-1) }"></c:set>
			<c:if test="${total>index }">
				<div class="col-md-3">
					<a href="show_pdf?bookId=${requestScope.main_books_of_category_perpage[index]['id'] }">
					<img src="getPdfImageOf?bookId=${requestScope.main_books_of_category_perpage[index]['id'] }&pageIndex=0" 
					           class="img-thumbnail" alt="${requestScope.main_books_of_category_perpage[index]['name'] }">
						${requestScope.main_books_of_category_perpage[index]['name'] }
					</a>
				</div>
			</c:if>
		</c:forEach>
	</div>
	</c:forEach>
	
	<c:choose>
		<c:when test="${totalAll > (totalOnePage) && total>0}">
			<div class="col-md-12">
				 <ul class="pagination pagination">
				 <li><a href="show_category?category=${requestScope.main_books_of_category_perpage[0]['category'] }&start=0&curPageIndex=0">首页</a></li>
				 <c:set var="curPageIndex" value="${requestScope.current_page_index }"></c:set>
				 <c:set var="prePageIndex" value="${((curPageIndex-1>=0)?(curPageIndex-1):0) }"></c:set>
				  <li><a href="show_category?category=${requestScope.main_books_of_category_perpage[0]['category'] }&start=${prePageIndex*totalOnePage }&curPageIndex=${prePageIndex}">上一页</a></li>
				  <c:forEach var="number"  begin="1" end="${pageCnt }" step="1">
				 	 <li><a href="show_category?category=${requestScope.main_books_of_category_perpage[0]['category'] }&start=${(number-1)*totalOnePage }&curPageIndex=${number}">${number }</a></li>
				  </c:forEach>
				  <c:set var="nextPageIndex" value="${ ((curPageIndex+1>=(pageCntInt-1))?(pageCntInt-1):(curPageIndex+1))}"></c:set>
				  <li><a href="show_category?category=${requestScope.main_books_of_category_perpage[0]['category'] }&start=${nextPageIndex*totalOnePage}&curPageIndex=${nextPageIndex}">下一页</a></li>
				   <li><a href="show_category?category=${requestScope.main_books_of_category_perpage[0]['category'] }&start=${(pageCntInt-1)*totalOnePage }&curPageIndex=${pageCntInt-1}">末页</a></li>
				</ul><br>
			</div>
		</c:when>
		<c:otherwise>
			
		</c:otherwise>
	</c:choose>
	
</body>
</html>