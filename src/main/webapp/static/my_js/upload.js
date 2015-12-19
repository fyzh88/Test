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
	 * 提取分享地址
	 */
	$("#shared_addr_").change(function(){
		var addr_w = $(this).val();
		var addr_12 = addr_w.split(" ");
		var addr_1 = addr_12[0];
		var addr_2 = addr_12[1];
		$(this).val(addr_1);
		var addr_code = addr_2.split("：");
		var realcode=addr_code[1];
		var lastPart = realcode.substr(0,4);
		$("#share_code").val(lastPart);
	});
	/**
	 * 提取分享地址
	 */
	$("#shared_addr").change(function(){
		var addr_w = $(this).val();
		var addr_12 = addr_w.split(" ");
		var addr_1 = addr_12[1];
		var addr_2 = addr_12[3];
		$(this).val(addr_1);
		$("#share_code").val(addr_2);
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
		var md = $("#md").val(); 
		var save_name=$("#saved_name").val();
		
		var dict={"name":name,"author":author,"publisher":publisher,
				"pages":pages,"file_len":fileLen,"category":category,
				"shared_addr":shared_addr,"share_code":share_code,"id":"",
				"user_id":"","md":md,"save_name":save_name};
		var post =JSON.stringify(dict);
		$.ajax({
			type:'POST',
			url:path+"/share_pdf",
			data:post,
			dataType:'json',
			success:function(ret){},
			contentType:"application/json",
			complete:function(data){
				$("#pdf_file").text("分享完成！");
			}
		});
		
	});
});