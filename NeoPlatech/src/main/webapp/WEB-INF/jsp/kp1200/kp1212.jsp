<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "태그발행";
String curAction = "/kp1200/kp1212.do";
String curGridAction = "/kp1200/kp1212ItemAjax.do";
String curGridAction2 = "/kp1200/kp1212VirtAjax.do";
String procAction = "/kp1200/kp1212Proc.do";
String delProcAction = "/kp1200/kp1212DelProc.do";
String printAction = "/kp1200/kp1212PrintProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

//공통코드
String com001Str = RequestUtil.getString(request, "com001Str"); //상각법
String com002Str = RequestUtil.getString(request, "com002Str"); //사용여부
String com005Str = RequestUtil.getString(request, "com005Str"); //활용범위
String com006Str = RequestUtil.getString(request, "com006Str"); //취득방법
%>
<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">
var widthHip = 5;
var heightHip = 300;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight(($(window).height() - heightHip) / 2);
	$("#listInfo02").setGridWidth($(window).width() - widthHip);
	$("#listInfo02").setGridHeight(($(window).height() - heightHip) / 2);
}

var colNames01 = [
                '검수품목일련번호','구매번호','신청번호','물품일련번호',
				'자산구분코드','분할등록','수량','품목코드','품목명','상세명칭','영문명','제조국코드','제조국','제조업체코드','제조업체','판매업체코드','판매업체',
				'단가','금액','외환','계정번호','내용연수','상각법','규격','취득방법','사용목적','소프트웨어','ZEUS장비','ETUBE장비','활용범위'
				];
var colModel01 = [
				{name:'inspItemSeq', index:'inspItemSeq', width:'0px', hidden:true},
				{name:'purno', index:'purno', width:'0px', hidden:true},
				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
				{name:'prodno', index:'prodno', width:'0px', hidden:true},
				{name:'assetTypeCd', index:'assetTypeCd', width:'0px', hidden:true},
				{name:'divRegiYn', index:'divRegiYn', align:'CENTER', width:'80px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "N";
						value = '<select id="divRegiYn_'+options.rowId+'" custrowid="'+options.rowId+'" class="def impt"><%=com002Str%></select>';
						return value;
					}},
				{name:'assetCnt', index:'assetCnt', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'itemCd', index:'itemCd', width:'0px', hidden:true},
				{name:'itemName', index:'itemName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'assetName', index:'assetName', width:'300px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'assetEname', index:'assetEname', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'mkNationCd', index:'mkNationCd', width:'0px', hidden:true},
				{name:'mkNationName', index:'mkNationName', width:'90px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'mkCompanyCd', index:'mkCompanyCd', width:'0px', hidden:true},
				{name:'mkCompanyName', index:'mkCompanyName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'slCompanyCd', index:'slCompanyCd', width:'0px', hidden:true},
				{name:'slCompanyName', index:'slCompanyName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'aqusitForeignAmt', index:'aqusitForeignAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'accntNum', index:'accntNum', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'usefulLife', index:'usefulLife', width:'70px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'depreCd', index:'depreCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'assetSize', index:'assetSize', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'aqusitTypeCd', index:'aqusitTypeCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'purposeOfUse', index:'purposeOfUse', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'swYn', index:'swYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'zeusYn', index:'zeusYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'etubeYn', index:'etubeYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'aplctnRangeCd', index:'aplctnRangeCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
				];
var groupHeaders01 = [];

var colNames02 = [
   				'assetSeq','사용자번호','사용자설정','태그발행여부','자산코드','품목명','상세명칭','취득일자','수량','단가','금액','자산확정여부'
   				];
var colModel02 = [
				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
				{name:'userNo', index:'userNo', width:'0px', hidden:true, sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="hidden" id="userNo_'+options.rowId+'" value="'+ value +'" />';
						return value;
					}},
				{name:'userName', index:'userName', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="userName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def srch1" onclick="kp9030Pop2(\''+options.rowId+'\')" />';
						return value;
					}},
				{name:'printYn', index:'printYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'itemName', index:'itemName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'aqusitDt', index:'aqusitDt', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter:fnFormatterDate},
   				{name:'assetCnt', index:'assetCnt', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'assetRegiYn', index:'assetRegiYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
   				];
var groupHeaders02 = [];

function fnGridList() {

	$("#listInfo01").jqGrid("GridUnload");

	$("#listInfo01").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction%>",
		postData : $('#sForm').serializeObject(),
		mtype:'POST',
		jsonReader : {
			root    : "resultList",
			repeatitems : false
		},
		colNames : colNames01,
		colModel : colModel01,
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: false,
		//cellEdit : true,
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: false,
		resizeStop: function() {
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
		},
		onSortCol : function(index, columnIndex, sortOrder) {
		},
		onSelectRow: function(rowid) {
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			fnGridResize();
		}
	});
}

function fnGridList2() {

	$("#listInfo02").jqGrid("GridUnload");

	$("#listInfo02").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction2%>",
		postData : $('#sForm').serializeObject(),
		mtype:'POST',
		jsonReader : {
			root    : "resultList",
			repeatitems : false
		},
		colNames : colNames02,
		colModel : colModel02,
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: false,
		//cellEdit : true,
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: true,
		resizeStop: function() {
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
		},
		onSortCol : function(index, columnIndex, sortOrder) {
		},
		onSelectRow: function(rowid) {
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			fnGridResize();
		}
	});
}

function fnSave() {
	var ids = jQuery("#listInfo01").jqGrid('getDataIDs');
	var saveJsonArray = [];

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					inspItemSeq: obj.inspItemSeq,
					purno: obj.purno,
					rqstno: obj.rqstno,
					prodno: obj.prodno,
					divRegiYn: $('#divRegiYn'+'_'+ids[i]).val()
			};
			saveJsonArray.push(saveJsonObj);
		}

		if (confirm("가자산을 생성 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=procAction%>",
				data : {
					purno : $('#purno').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("생성 되었습니다.");
						fnGridList2();
					} else {
						alert(data.retmsg);
					}
				},
				error:function(xhr, ajaxOptions, thrownError)
				{
					alert("[ERROR] 처리 중 오류가 발생하였습니다.");
				},
				complete:function()
				{
					fnLoadingE2();
				}
			});
		}
	}
}

//가자산삭제
function fnDelRow() {
	var ids = $('#listInfo02').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("삭제할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		var obj = $("#listInfo02").jqGrid('getRowData', ids[i]);
		var saveJsonObj = {
				assetSeq: obj.assetSeq
		};
		saveJsonArray.push(saveJsonObj);
	}

	if (confirm("삭제 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=delProcAction%>",
			data : {
				purno : $('#purno').val(),
				saveJsonArray : JSON.stringify(saveJsonArray)
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("삭제 되었습니다.");
					fnGridList2();
				} else {
					alert(data.retmsg);
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("[ERROR] 처리 중 오류가 발생하였습니다.");
			},
			complete:function()
			{
				fnLoadingE2();
			}
		});
	}
}

//태그출력
function fnRfidPrint() {
	var ids = $('#listInfo02').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("출력할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		var obj = $("#listInfo02").jqGrid('getRowData', ids[i]);
		var saveJsonObj = {
				assetSeq: obj.assetSeq
		};
		saveJsonArray.push(saveJsonObj);
	}

	if (confirm("출력 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=printAction%>",
			data : {
				purno : $('#purno').val(),
				saveJsonArray : JSON.stringify(saveJsonArray)
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("출력 요청 되었습니다.");
					fnGridList();
				} else {
					alert(data.retmsg);
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("[ERROR] 처리 중 오류가 발생하였습니다.");
			},
			complete:function()
			{
				fnLoadingE2();
			}
		});
	}
}

$(document).ready(function(){

	fnGridList();
	fnGridList2();
});

//사용자 콜백
function fnSetKp9030(obj) {
	$('#userNo_' + $('#kp9030rowid').val()).val(obj.userNo);
	$('#userName_' + $('#kp9030rowid').val()).val(obj.userName);
}
</script>

<style type="text/css">
/* selrow highlight disabled */
.ui-state-highlight{
    background:none!important;
}
/* mouseover highlight disabled */
.ui-state-hover{
    background:none!important;
}
/* vertical-align : middle */
.ui-jqgrid input,select {
	border:1px solid gray;
	position:relative;
	top:1px;
}
.ui-jqgrid input[type="checkbox"] {
	position:relative;
	top:3px;
}
</style>

</head>
<body style="overflow:auto;">

	<h2 class="title_left">계약정보</h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="purno" name="purno" value="<%=cmRequest.getString("purno")%>" />
	<input type="hidden" id="kp9030rowid" name="kp9030rowid" value="" />

	<table class="table-search" style="width:100%;">
	<colgroup>
		<col width="120px" />
		<col width="250px" />
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
		<th class="r">계약번호 :</th>
		<td><%=viewData.getString("contrno")%></td>
		<th class="r">구매신청자 :</th>
		<td><%=viewData.getString("userhisname")%></td>
	</tr>
	<tr>
		<th class="r">계약명 :</th>
		<td><%=viewData.getString("purnm")%></td>
		<th class="r">계약일자 :</th>
		<td><%=DateUtil.formatDateTime(viewData.getString("contrdt"), "-", ":", 8)%></td>
	</tr>
	<tr>
		<th class="r">거래처 :</th>
		<td><%=viewData.getString("custhisname")%></td>
		<th class="r">계약금액 :</th>
		<td><%=StringUtil.commaNum(viewData.getString("contramt"))%></td>
	</tr>
	</table>

	<div style="padding-top:20px;"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		<h2 class="title_left">납품내역</h2>
	</td>
	<td width="50%" style="text-align: right;">
		<% if(ssAuthManager){ %>
		<span class="button"><input type="button" value="가자산생성" onclick="fnSave();"></span>
		<% } %>
		&nbsp;
	</td>
	</tr>
	</table>

	<table id="listInfo01" class="scroll"></table>

	<div style="padding-top:30px;"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		<h2 class="title_left">가자산 목록</h2>
	</td>
	<td width="50%" style="text-align: right;">
		<% if(ssAuthManager){ %>
		<span class="button"><input type="button" value="RFID태그출력" onclick="fnRfidPrint();"></span>
		<span class="button"><input type="button" value="가자산삭제" onclick="fnDelRow();;"></span>
		<% } %>
		&nbsp;
	</td>
	</tr>
	</table>

	<table id="listInfo02" class="scroll"></table>

	</form>

</body>
</html>



