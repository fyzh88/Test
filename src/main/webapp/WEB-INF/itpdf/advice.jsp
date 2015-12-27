<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
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
		<div class="col-md-12">
			<div class="panel panel-primary">
			   <div class="panel-heading">
			      <h3 class="panel-title">宝贵意见</h3>
			   </div>
			   <div class="panel-body">
			   <c:forEach items="${requestScope.advices }" varStatus="id" var="advice">
			    <div class="well">
				   	<h5><fmt:formatDate value="${advice['date']}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/> &nbsp;${advice['author'] }&nbsp;匿名</h5>
				   	<h4> &nbsp; ${advice['content'] }</h4>
				   	
			     </div>
			   </c:forEach>
			    <div class="input-group">
			    	<input type="text" class="form-control" id="adviceContent" placeholder="我们希望听到你的声音">
			         <span class="input-group-btn">
			         	  <button class="btn btn-default" type="button" id="sendAdvice">发表</button>
			         </span>
			      </div>
			   </div>			
		</div>
	</div>
	</div>
	<script type="text/javascript">
		var path="${path}";
	</script>
 	<script type="text/javascript" src="<c:url value='/static/my_js/advice.js'/>"></script>
 	
</body>
</html>