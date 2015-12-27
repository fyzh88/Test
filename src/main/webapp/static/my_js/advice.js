/**
 * 
 */
$(function(){
	$("#sendAdvice").click(function(){
		var content = $("#adviceContent").val();
		if(typeof(content)=="undefined"){return;}
		if(content.length==0){return;}
		window.location.href=path+"/send_advice?content="+content+"&name=";
	});
	
})
