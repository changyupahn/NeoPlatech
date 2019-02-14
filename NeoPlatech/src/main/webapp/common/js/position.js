function fnSetPosition(n){
	
	try{
	if (n >= 1) {
		var cd = $('#sUsePositionCode'+n).val();
		
		if (n==1 || n==2) {
			$.ajax({
				type : "POST",
				url : "/position/optionAjax.do",
				data : {
					position_tcode : cd
				},		
				success:function(data)		
				{
					$('#sPositionLayer').html(data);
				}
			});
		}
		
		$('#sUsePositionCode').val( cd );
	}
	}catch(e){
		alert(e.description);
	}
}
/*
$(document).ready(function(){
	$('#sUsePositionCode1').change(function(){
		$('#sUsePositionCode3').html('<option value="">선택</option>');
		
		$.ajax({
			type : "POST",
			url : "/position/optionAjax.do",
			data : {
				position_tcode : $(this).val()
			},		
			success:function(data)		
			{
				$('#sUsePositionCode2').html(data);
			}
		});
	});
	
	$('#sUsePositionCode2').change(function(){
		$.ajax({
			type : "POST",
			url : "/position/optionAjax.do",
			data : {
				position_tcode : $(this).val()
			},		
			success:function(data)		
			{
				$('#sUsePositionCode3').html(data);
			}
		});
	});
});
*/