$(document).ready(function(){
	
	$('#chkAll').click(function(){
		if( $(this).attr("checked") ){
			$('input[name="chk"]').attr("checked", true);
		}else{
			$('input[name="chk"]').attr("checked", false);
		}
	});
	
});