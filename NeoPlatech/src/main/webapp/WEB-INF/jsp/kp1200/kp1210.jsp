<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String detailAction = "/kp1200/kp1211.do";
String curAction = "/kp1200/kp1210.do";
String curGridAction = "/kp1200/kp1210Ajax.do";
String curGridAction2 = "/kp1200/kp1210DtlAjax.do";
String curSearchAction = "/kp1200/kp1210Search.do";
String xlsDnAction = "/kp1200/kp1210Excel.do";
String detailPop1Action = "/kp1200/kp1211.do";
String detailPop2Action = "/kp1200/kp1212.do";
String detailPop3Action = "/kp1200/kp1213.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 280; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 380;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	var searchBoxHeight = $('#SearchBox').height() / 2;
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight(($(window).height() - searchBoxHeight - heightHip) / 2);
	$("#listInfo02").setGridWidth($(window).width() - widthHip);
	$("#listInfo02").setGridHeight(($(window).height() - searchBoxHeight - heightHip) / 2);
}

var colNames01 = [
                '구매번호',
				'계약구분','계약번호','계약명','구매신청자','거래처','계약금액','계약일자','계약종료일자','납품장소','납품일자','검수일자','검수상태'
				];
var colModel01 = [
				{name:'purno', index:'purno', width:'0px', hidden:true},
				{name:'contrdiv', index:'contrdiv', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'contrno', index:'contrno', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'purnm', index:'purnm', width:'300px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
				{name:'userhisname', index:'userhisname', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'custhisname', index:'custhisname', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'contramt', index:'contramt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'contrdt', index:'contrdt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-DATE', formatter: fnFormatterDate},
				{name:'contrenddt', index:'contrenddt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-DATE', formatter: fnFormatterDate},
				{name:'deliveryplace', index:'deliveryplace', width:'150px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
				{name:'deliverydt', index:'deliverydt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-DATE', formatter: fnFormatterDate},
				{name:'inspDt', index:'inspDt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
				{name:'inspStatusCdStr', index:'inspStatusCdStr', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
				];
var groupHeaders01 = [];

var colNames02 = [
  				'물품명','수량','가자산생성','태그발행','검수(자산확정)','검수일자'
  				];
var colModel02 = [
  				{name:'prodnm', index:'prodnm', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'qty', index:'qty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'virtAssetCnt', index:'virtAssetCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'tagPrintCnt', index:'tagPrintCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'inspAssetCnt', index:'inspAssetCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'inspDt', index:'inspDt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate}
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
		// cellEdit : true,
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
// 			var crud = $("#listInfo01").jqGrid('getRowData',rowid).crud;
// 			if (crud != "C") {
// 				$("#listInfo01").jqGrid('setRowData',rowid,{crud: "U"});
// 			}
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		onSelectRow: function(rowid) {
			fnDetail(rowid);
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

			fnGridInvalidSession(data.totalRow);

		},
		gridComplete : function() {
			//alert($("#listInfo01").getGridParam("records"));
			var ids = $("#listInfo01").jqGrid("getDataIDs");
			if (ids.length > 0) {
				$("#listInfo01").setSelection(ids[0], true);
				fnDetail(ids[0]);
			}

			fnGridResize();
		}
	});
}

function fnGridList2(purno) {

	$("#listInfo02").jqGrid("GridUnload");

	$("#listInfo02").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction2%>",
		postData : {
			purno: purno
		},
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
		shrinkToFit: true,
		// cellEdit : true,
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
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload2(purno);
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

function fnGridReload(pageIdx){
	var frm = document.sForm;
	if (pageIdx) {
		frm.pageIdx.value = pageIdx;
	}

	$("#listInfo01").setGridParam({
		postData: $('#sForm').serializeObject()
	}).trigger("reloadGrid");
}

function fnGridReload2(purno){
	fnGridList2(purno);
}

function fnSearch(){
	fnGridReload("1");
}
function fnXlsDn(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction%>";
	frm.target = "_self";
	frm.submit();
}
function fnDetail(rowid){
	var purno = $("#listInfo01").jqGrid("getRowData", rowid).purno;
	var contrno = $("#listInfo01").jqGrid("getRowData", rowid).contrno;
	fnGridList2(purno);
	$('#spanContrno').html(contrno);
	$('#purno').val(purno);

}
function fnDetailPop1(){
	var purno = $('#purno').val();

	$('#layerPop').click();
	$('#iframe').attr("src", "<%=detailPop1Action%>?purno=" + purno);
	$('#layer_iframe').show();

}
function fnDetailPop2(){
	var purno = $('#purno').val();

	$('#layerPop').click();
	$('#iframe').attr("src", "<%=detailPop2Action%>?purno=" + purno);
	$('#layer_iframe').show();

}
function fnDetailPop3(){
	var purno = $('#purno').val();

	$('#layerPop').click();
	$('#iframe').attr("src", "<%=detailPop3Action%>?purno=" + purno);
	$('#layer_iframe').show();

}
$(document).ready(function(){

	fnGridList();
	fnInitSearchForm();
	fnInitLayerPopup();
});
function fnInitSearchForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curSearchAction%>",
		data : {
			colcnt : colcnt
		},
		success:function(data)
		{
			$('#SearchBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
			fnGridResize();
		}
	});
}
//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#sItemSeq').val(obj.seq);
	$('#sItemCd').val(obj.cd);
	$('#sItemName').val(obj.nm);
}

//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	$('#sDeptNo').val(obj.cd);
	$('#sDeptName').val(obj.nm);
}

//사용자 콜백
function fnSetKp9030(obj) {
	$('#sUserNo').val(obj.userNo);
	$('#sUserName').val(obj.userName);
}

//위치 콜백
function fnSetKp9040(obj) {
	$('#sPosNo').val(obj.posNo);
	$('#sPosName').val(obj.posName);
}

//판매업체 콜백
function fnSetKp9070(obj) {
	$('#sCompCd').val(obj.compCd);
	$('#sCompName').val(obj.compName);
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">계약검수 관리</h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="purno" name="purno" value="" />

	<div id="divPopupLayer"></div>

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
		&nbsp;
	</td>
	</tr>
	</table>

	<p><spring:message code="count.total"/> : <span id="spanTotalRow"></span></p>

	<table id="listInfo01" class="scroll"></table>

	<table style="width:100%;border-collapse:collapse;border:0;">
	<tr>
	<td width="">
		<div id="paginate" class="paginate"></div>
	</td>
	<td width="150px" class="r">
		<select id="pageSize" name="pageSize" onchange="fnSearch()" style="border:1px solid gray;">
		<option value="15" <%="15".equals(cmRequest.getString("pageSize","15"))?"selected":""%>><spring:message code="count.paging" arguments="15" /></option>
		<option value="30" <%="30".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="30" /></option>
		<option value="50" <%="50".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="50" /></option>
		<option value="100" <%="100".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="100" /></option>
		<option value="500" <%="500".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="500" /></option>
		</select>
	</td>
	</tr>
	</table>

	<table style="width:100%;border-collapse:collapse;">
		<tr>
			<td style="text-align:left; padding-bottom:5px;">
				<span style="position:relative;top:3px;">■ 상세내역 (계약번호 : <span id="spanContrno" style="font-weight:bold;color:blue;"></span>)</span>
			</td>
			<td style="text-align:right; padding-bottom:5px;">
				<span class="button"><input type="button" value="납품내역" onclick="fnDetailPop1();" style="font-weight:bold;color:blue;"></span>
				<span class="button"><input type="button" value="태그발행" onclick="fnDetailPop2();" style="font-weight:bold;color:blue;"></span>
				<span class="button"><input type="button" value="검수" onclick="fnDetailPop3();" style="font-weight:bold;color:blue;"></span>
				&nbsp;
			</td>
		</tr>
	</table>


	<table id="listInfo02" class="scroll"></table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



