$(function(){
	/**
	 *上传PDF文件
	 */
	$("#form_upload_pdf input[type='file']").change(function() {
		var fn = $(this).val();
		if (fn != "") {
			$('#pdf_file').text(fn);
			//提交表单
			var form = $("#form_upload_pdf");
			form.attr("action", path + "/upload_pdf");
			form.submit();
		} else {
			
		}	
	});
	/**
	 * 分享文件
	 */
	$("#share_pdf").click(function(){
		var name = $("#name").val();
		var author = $("#author").val();
		var publisher = $("#publisher").val();
		var pages = $("#pages").val();
		var fileLen = $("#file_len").val();
		var category=$("#category").val();
		var shared_addr=$("#shared_addr").val();
		var share_code=$("#share_code").val();
		var md = $("md").val();
		
		var dict={"name":name,"author":author,"publisher":publisher,
				"pages":pages,"file_len":fileLen,"category":category,
				"shared_addr":shared_addr,"share_code":share_code,id:"",
				user_id:"",md:"md"};
		var post =JSON.stringify(dict);
		$.ajax({
			type:'POST',
			url:path+"/share_pdf",
			data:post,
			dataType:'json',
			success:function(ret){},
			contentType:"application/json"
		});
		
	});
});