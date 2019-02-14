function fnNewAssetNo() {
	var frm = document.sForm;
	var asset_cate = frm.ASSET_CATE.value;
	var aqusit_dt = frm.AQUSIT_DT.value;
	
	if (asset_cate == "" || asset_cate.length < 3) {
		alert("물품분류를 먼저 입력해주세요.");
		return;
	}
	if (aqusit_dt == "" || aqusit_dt.length < 8) {
		alert("취득일자를 먼저 입력해주세요.");
		return;
	}
	
	$.ajax({
		type : "POST",
		url : "/asset/newAssetNoAjax.do",
		dataType : "json",
		data : {
			asset_cate : asset_cate,
			aqusit_dt : aqusit_dt
		},		
		success:function(data)		
		{
			if (data.result == "Y") {
				frm.ASSET_NO.value = data.asset_no;
			}
		}
	});
}