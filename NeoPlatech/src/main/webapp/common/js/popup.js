
//품목코드 팝업
function kp9010Pop() {
	fnOpenPop("/kp9000/kp9010.do", "kp9010Pop", "650", "750","");
}

//품목코드 팝업
function kp9011Pop() {
	fnOpenPop("/kp9000/kp9011.do", "kp9011Pop", "650", "750","");
}

//품목코드 팝업
function kp9012Pop() {
	fnOpenPop("/kp9000/kp9012.do", "kp9012Pop", "650", "750","");
}

//부서코드 팝업
function kp9020Pop(deptDiv) {

	if (deptDiv) {
		$('#deptDiv').val(deptDiv);
	}

	fnOpenPop("/kp9000/kp9020.do", "kp9020Pop", "650", "750","");
}

//사용자코드 팝업
function kp9030Pop(no) {

	var noval = "";

	if (no == "1" || no == "2" || no == "3") {
		noval = no;
	}

	fnOpenPop("/kp9000/kp9030.do?no="+noval, "kp9030Pop", "650", "750","");
}
function kp9030Pop2(rowid) {
	if (rowid) {
		$('#kp9030rowid').val(rowid);
	}
	fnOpenPop("/kp9000/kp9030.do", "kp9030Pop", "650", "750","");
}

//위치코드 팝업
function kp9040Pop() {
	fnOpenPop("/kp9000/kp9040.do", "kp9040Pop", "650", "750","");
}

//제조국코드 팝업
function kp9050Pop(rowid) {
	if (rowid) {
		$('#kp9050rowid').val(rowid);
	}
	fnOpenPop("/kp9000/kp9050.do", "kp9050Pop", "650", "750","");
}

//제조업체코드 팝업
function kp9060Pop(rowid) {
	if (rowid) {
		$('#kp9060rowid').val(rowid);
	}
	fnOpenPop("/kp9000/kp9060.do", "kp9060Pop", "650", "750","");
}

//판매업체코드 팝업
function kp9070Pop(rowid) {
	if (rowid) {
		$('#kp9070rowid').val(rowid);
	}
	fnOpenPop("/kp9000/kp9070.do", "kp9070Pop", "650", "750","");
}

//자산조회 팝업
function kp9080Pop() {
	//fnOpenPop("/kp9000/kp9080.do", "kp9080Pop", "650", "750","");
	fnOpenPop("/kp9000/kp9080.do", "kp9080Pop", "1000", "750", "menubar=no,status=no,titlebar=no,scrollbars=yes,location=no,toolbar=no,resizable=yes,left=0,top=0");
}

//자산선택 팝업 (전자결재)
function kp8010Pop(type) {
	$('#layerPop').click();
	$('#iframe').attr("src", "/kp8000/kp8010.do?apprType="+type);
	$('#layer_iframe').show();
}

function fnInitLayerPopup() {

	$('#divPopupLayer').html(''
			+ '<div id="popupLayer">'
			+ '<div id="close"></div>'
			+ '<div style="clear:both"></div>'
			+ '<div class="description">'
			+ '	<div id="layer_iframe" style="position:absolute; top:40px; left:0%;margin-left:0px; width:100%; height:100%;">'
			+ '		<iframe id="iframe" name="iframe" style="width:100%; height:90%" frameborder="0" scrolling="auto"></iframe>'
			+ '	</div>'
			+ '</div>'
			+ '</div>'
			+ '<a id="layerPop" href="#" class="popup"></a>');

	//options add
	var opts = {
		'closeButton' :'#close',
		'active' : 'skyblue' ,
		'backgroundDisplay' : true
	};

	$(".popup").layerPopup("#popupLayer",opts);

	if(jQuery.ui) $("#popupLayer").draggable();
}

function fnCloseLayerPopup() {
	$('#divPopupLayer').html("");
	$('#backgroundPopup').fadeOut(300);
	setTimeout(fnCloseBackground, 300);
}

function fnCloseBackground() {
	$('#backgroundPopup').remove();

	fnInitLayerPopup();
}