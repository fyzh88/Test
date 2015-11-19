<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="it pdf download  ">
<meta name="description" content="it行业pdf文件分享下载平台">
<title>ITPDF</title>
<link rel="stylesheet" href="<c:url value='/static/bootstrap-3.3.4/css/bootstrap.min.css'/>"/>
<link rel="stylesheet" href="<c:url value='/static/css/upload.css'/>"/>
<link rel="stylesheet" href="<c:url value='/static/css/font.css'/>"/>
<link rel="shortcut icon" href="<c:url value='/static/img/logo.ico'/>"/>
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
		<div id="file-box" class="col-md-3">
			<form class="form-inline" method="post" enctype="multipart/form-data"
				id="form_upload_pdf" action="${path}/upload_pdf">
					<div class="new-contentarea tc">
					<a href="javascript:void(0)" class="upload-img">
					<label for="upload-file">上传PDF</label></a>
					<input type="file" class="" name="file" id="upload-file" />
					<input	type="hidden" name="token" value="${sessionScope.protect_repeat_submit}" />
					</div>					
			</form>
		</div>
		<div class="col-md-9">
			<label id="pdf_file"></label>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-7">
			<img alt="" src="${path}/static/img/dummy.png" class="img-rounded" width="400">
		</div>
		<div class="col-md-5">
			 <div class="input-group">
	        	 <span class="input-group-addon">名称&nbsp;</span>
		         <input id="name" type="text" class="form-control" placeholder="PDF文件名称" value="${requestScope.upload_return_param['name'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">作者&nbsp;</span>
		         <input id="author" type="text" class="form-control" placeholder="作者" value="${requestScope.upload_return_param['author'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">出版社</span>
		         <input id="publisher" type="text" class="form-control" placeholder="出版社" value="${requestScope.upload_return_param['publisher'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">页数&nbsp;</span>
		         <input id="pages" type="text" class="form-control" placeholder="PDF文件页数" value="${requestScope.upload_return_param['pages'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">文件大小&nbsp;</span>
		         <input id="file_len"type="text" class="form-control" placeholder="PDF文件大小" value="${requestScope.upload_return_param['file_len'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">分类&nbsp;</span>
		         <input id="category" type="text" class="form-control" placeholder="PDF文件分类" value="${requestScope.upload_return_param['category'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">分享地址</span>
		         <input id="shared_addr" type="text" class="form-control" placeholder="PDF文件的分享地址" value="${requestScope.upload_return_param['shared_addr'] }">
		     </div><br>
		     <div class="input-group">
	        	 <span class="input-group-addon">分享提取码</span>
		         <input id="share_code" type="text" class="form-control" placeholder="PDF文件的分享提取码" value="${requestScope.upload_return_param['share_code'] }">
		     </div><br>
		     <input id="md" type="hidden" value="${requestScope.upload_return_param['md'] }">
		     
		     <button class="btn btn-success btn-lg pull-right" id="share_pdf">我要分享</button>	
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			
		</div>
	</div>
	<script type="text/javascript">
		var path="${path}";
	</script>
	<script type="text/javascript" src="<c:url value='/static/my_js/upload.js'/>"></script>
	
</body>
</html>