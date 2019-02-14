<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "일괄수정";
String curAction = "/kp1300/kp1311ModifyAll.do";
String curGridAction = "/kp1300/kp1311ModifyAllAjax.do";
String procAction = "/kp1300/kp1311ModifyAllProc.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

int colbasewid = 310; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 200;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	//$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
				'assetSeq',
				'자산번호','품목명','상세명칭', '취득일자', '취득가액', '잔존가액', '위치', '사용자'
				];
var colModel01 = [
   				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
   				{name:'assetNo', index:'assetNo', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'itemName', index:'itemName', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'aqusitDt', index:'aqusitDt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter: fnFormatterDate},
   				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', sortable:false, classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'aqusitRemainAmt', index:'aqusitRemainAmt', width:'100px', align:'RIGHT', columntype:'text', sortable:false, classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'posName', index:'posName', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'userName', index:'userName', width:'100px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
   				];
var groupHeaders01 = [];

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
		autowidth:true,
		shrinkToFit: false,
		cellEdit : false,
//      scrollOffset: 0,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
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
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			//텍스트가 셀 너비보다 넓을 때 자동 줄 바꿈 처리
			$('.ui-jqgrid tr.jqgrow td').css("white-space", "normal");
		}
	});
}

function fnGridReload(pageIdx){
	var frm = document.sForm;
	if (pageIdx) {
		frm.pageIdx.value = pageIdx;
	}

	$("#listInfo01").setGridParam({
		postData: $('#sForm').serializeObject()
	}).trigger("reloadGrid");
}

function fnSearch() {
	fnGridReload("1");
}

function fnSave() {

	if (confirm("저장 하시겠습니까?")) {
		fnLoadingS2();

		var frm = document.sForm;
		frm.action = "<%=procAction%>";
		frm.target = "modifyProcFrame";
		frm.submit();
	}
}

function fnSaveCallback() {
	parent.fnGridList();
	parent.fnCloseLayerPopup();
}

//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#itemCd').val(obj.cd);
	$('#itemName').val(obj.nm);
	$('#assetTypeCd').val(obj.atc);
	$('#assetTypeName').val($('#assetTypeCd >option:selected').html());
	$('#itemAssetTypeCd').val(obj.atc);
	$('#depreCd').val(obj.dc);
	$('#usefulLife').val(obj.ny);
}

//품목코드 팝업 콜백 함수
function fnSetKp9011(obj) {
	$('#itemCd').val(obj.cd);
	$('#itemName').val(obj.nm);
	$('#assetTypeCd').val(obj.atc);
	$('#assetTypeName').val($('#assetTypeCd >option:selected').html());
	$('#itemAssetTypeCd').val(obj.atc);
	$('#depreCd').val(obj.dc);
	$('#usefulLife').val(obj.ny);
}

//표준분류 팝업 콜백 함수
function fnSetKp9012(obj) {
	$('#zeusItemCd').val(obj.cd);
	$('#zeusItemName').val(obj.nm);
}

//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	$('#deptNo').val(obj.cd);
	$('#deptName').val(obj.nm);
	$('#topDeptNo').val(obj.pcd);
}

//사용자 콜백
function fnSetKp9030(obj) {
	$('#userNo').val(obj.userNo);
	$('#userName').val(obj.userName);
	$('#deptNo').val(obj.deptNo);
	$('#spanDeptName').html(obj.deptName);
}

//위치 콜백
function fnSetKp9040(obj) {
	$('#posNo').val(obj.posNo);
	$('#posName').val(obj.posName);
}

//제조국 콜백
function fnSetKp9050(obj) {
	$('#mkNationCd').val(obj.natnCd);
	$('#mkNationName').val(obj.natnName);
}

//제조업체 콜백
function fnSetKp9060(obj) {
	$('#mkCompanyCd').val(obj.compCd);
	$('#mkCompanyName').val(obj.compName);
}

//판매업체 콜백
function fnSetKp9070(obj) {
	$('#slCompanyCd').val(obj.compCd);
	$('#slCompanyName').val(obj.compName);
}

$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" enctype="multipart/form-data">
	<input type="hidden" id="gbn" name="gbn" value="<%=cmRequest.getString("gbn")%>" />
	<input type="hidden" id="menuDiv" name="menuDiv" value="<%=cmRequest.getString("menuDiv")%>" />
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />
	<input type="hidden" id="equipId" name="equipId" value="<%=viewData.getString("equipId")%>" />
	<input type="hidden" id="saveJsonArray" name="saveJsonArray" value="<%=cmRequest.getString("saveJsonArray")%>" />

	<h2 class="title_left">일괄수정</h2>

	<table style="width:100%;border-collapse:collapse;border:0px;">
	<tr>
	<td width="47%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td>
			&nbsp;
		</td>
		</tr>
		</table>

		<table id="listInfo01" class="scroll"></table>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="47%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td class="r2">
			<% if (ssAuthManager) { %>
			<span class="button"><input type="button" value="저장" onclick="fnSave()" ></span>
			<% } %>
		</td>
		</tr>
		</table>

		<table class="search01">
		<colgroup>
			<col width="120px" />
			<col width="" />
		</colgroup>
		<tr>
			<th>자산관리자 :</th>
			<td>
				<input type="hidden" id="userNo" name="userNo" value="" />
				<input type="text" id="userName" name="userName" value="" class="cd2 impt" />
				<input type="hidden" id="deptNo" name="deptNo" value="" />
			</td>
		</tr>
		<tr>
			<th>위치 :</th>
			<td>
				<input type="hidden" id="posNo" name="posNo" value="" />
				<input type="text" id="posName" name="posName" value="" class="cd2 impt" />
			</td>
		</tr>
		<tr>
			<th>사진1 :</th>
			<td>
				<input type="file" id="file1" name="file1" style="height:22px" />
			</td>
		</tr>
		<tr>
			<th>사진2 :</th>
			<td>
				<input type="file" id="file2" name="file2" style="height:22px" />
			</td>
		</tr>
		<tr>
			<th>사진3 :</th>
			<td>
				<input type="file" id="file3" name="file3" style="height:22px" />
			</td>
		</tr>
		</table>
	</td>
	</tr>
	</table>

	</form>

	<iframe id="modifyProcFrame" name="modifyProcFrame" src="" style="width:0;height:0;display:none;"></iframe>

</body>
</html>



