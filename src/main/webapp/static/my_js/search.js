/**
 * 
 */
$(function(){
	$("#searchInSite").click(function(){
		var searchKey = $("#searchKeyWord").val();
		if(typeof(searchKey)=="undefined"){return;}
		if(searchKey.length==0){return;}
		window.location.href=path+"/search?key="+searchKey+"&start=0&curPageIndex=0";
	});
	
})
