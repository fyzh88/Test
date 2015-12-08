/**
 * 
 */
$("#searchInSite").click(function(){
	var searchKey = $("#searchKeyWord").val();
	if(typeof(searchKey)=="undefined"){return;}
	if(searchKey.length==0){return;}
	windows.location.href=${path}+"/search?key=searchKey";
});