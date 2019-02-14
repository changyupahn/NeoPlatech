
//프린트관련 전역변수
var globalPrintResult = 1;

//물품현황 프린트 호출 함수
function fnTagPrintAsset(printType) {
	fnTagPrintChkAjax(printType, "ASSET", "", "");
}
//재물조사상세 프린트 호출 함수
function fnTagPrintInven(printType, jaemulYear, jaemulNo) {
	fnTagPrintChkAjax(printType, "INVEN", "", "");
}
//공통 프린트 호출
function fnTagPrintChkAjax(printType, gubun, jaemulYear, jaemulNo){
	
	var jsonUrl = "";
	if (gubun == "ASSET") {
		jsonUrl = "/asset/print/info.do";
	} else if (gubun == "INVEN") {
		jsonUrl = "/inventory/print/info.do";
	}	
	
	if( $('input[name="chk"]:checked').length == 0 ){
		alert("출력할 물품을 선택해 주세요.");
		return;
	}
	
	var bEmpPrintChk = "";
	if (document.getElementById("bEmpPrintChk") && $('#bEmpPrintChk').length == 1 && $('#bEmpPrintChk').attr("checked")) {
		bEmpPrintChk = "Y";
	}
	var bRePrintChk = "";
	if (document.getElementById("bRePrintChk") && $('#bRePrintChk').length == 1 && $('#bRePrintChk').attr("checked")) {
		bRePrintChk = "Y";
	}
	var tmpPrintPosition = "";
	if (document.getElementById("tmpPrintPosition")) {
		tmpPrintPosition = document.getElementById("tmpPrintPosition").value;
	}
	
	var chk_fixasset_code;
	var chk_obj_td;
	
	$('input[name="chk"]:checked').each(function(){
		
		for (var i=0; i<10; i++) {
			if (globalPrintResult == 1) {				
				break;
			} else {
				sleep(1000);
			}
		}
		
		if (globalPrintResult != 1) {
			$(chk_obj_td).css("background-color", "gray");
			alert("태그출력실패");
			return false;
		} else {
			$(chk_obj_td).css("background-color", "#FFE08C");
		}
		
		chk_fixasset_code = $(this).val();
		chk_obj_td = $(this).parent().parent().find('TD');
		
		$.ajax({
			type : "POST",
			url : jsonUrl,
			async : false,
			data : {
				printType : printType,
				chk_fixasset_code : chk_fixasset_code,
				bEmpPrintChk : bEmpPrintChk,
				bRePrintChk : bRePrintChk,
				tmpPrintPosition : tmpPrintPosition
			},
			dataType : "json",
			success:function(data)		
			{
				if (data.result == "1") 
				{
					globalPrintResult = 0;
					globalTagPrint(data.ip, data.port, data.format, data.fixasset_code, data.rfid_no, data.fixasset_name, data.fixasset_size, data.emp_name, data.use_position_name);
					
					//alert("성공");
				} 
				else if (data.result == "2") 
				{
					//alert("재출력 미포함");
				} 
				else 
				{
					sw = 0;					
					if (data.result == "-1") {
						alert("DATA 조회 실패");
					} else if (data.result == "-2") {
						alert("프린터 설정 정보 누락");
					} else if (data.result == "-3") {
						alert("인쇄 실패, 프린터 상태를 확인해 주세요.");
					} else if (data.result == "-4") {
						alert("인쇄 실패, 프린터 상태를 확인해 주세요.");
					} else {
						alert("ERROR");
					}
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				sw = 0;
			},
			complete:function()
			{
			}
		});
	});
}