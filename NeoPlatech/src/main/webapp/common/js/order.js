$(document).ready(function(){
	
	$('.dataOrder').click(function(){		
		$('#dataOrder').val( $(this).attr("dataOrder") );
		if ($(this).find(".ord_asc").length == 1) {
			$('#dataOrderArrow').val("desc");
		} else {
			$('#dataOrderArrow').val("asc");
		}
		fnSearch();
	});
	
	var dataOrderVal = $('#dataOrder').val();	
	if (dataOrderVal != "") {
		var dataOrderArrowVal = $('#dataOrderArrow').val();
		var dataOrderArrowIcon = "";
		if (dataOrderArrowVal == "asc") {
			dataOrderArrowIcon = "▲"; //오름차순
		} else {
			dataOrderArrowIcon = "▼"; //내림차순
		}
		$('th[dataOrder="'+ dataOrderVal +'"]').append('<span class="ord_'+dataOrderArrowVal+'">'+dataOrderArrowIcon+'</span>');
	}
});