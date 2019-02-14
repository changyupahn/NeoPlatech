<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "인수인계정보";
String curAction = "/kp1400/kp1411.do";
String curGridAction = "/kp1400/kp1411Ajax.do";
String procAction = "/kp1400/kp1411Proc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
%>

<html>
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
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
    				'assetSeq',
    				'자산구분','자산코드','MIS자산코드','품목명','취득일자','취득가액','수량','단가','잔존가액','사용자'
    				];
    var colModel01 = [
       				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
       				{name:'assetTypeName', index:'assetTypeName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
       				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
       				{name:'etisAssetNo', index:'etisAssetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
       				{name:'itemName', index:'itemName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
       				//{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
       				{name:'aqusitDt', index:'aqusitDt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
       				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
       				{name:'assetCnt', index:'assetCnt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
       				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
       				{name:'aqusitRemainAmt', index:'aqusitRemainAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
       				//{name:'posName', index:'posName', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
       				{name:'userName', index:'userName', width:'100px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
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

	var saveJsonObj = {
			rqstno: $('#rqstno').val()
	}

	if (confirm("승인처리 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=procAction%>",
			data : saveJsonObj,
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("승인처리 되었습니다.\n");
					parent.fnGridList();
					parent.fnCloseLayerPopup();
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
});
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
</style>

</head>
<body style="padding:10px 1px 1px 1px;">

	<table style="width:100%">
	<tr>
	<td width="50%">
		<h2 class="title_left"><%= pageTitle %></h2>
	</td>
	<td width="50%" style="text-align: right;">
		<% if (ssAuthManager && "2".equals(viewData.getString("rqstStatusCd"))) { %>
		<span class="button"><input type="button" value="승인처리" onclick="fnSave();"></span>
		<% } %>
		&nbsp;
	</td>
	</tr>
	</table>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="fnGridReload(1); return false;">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="pageSize" name="pageSize" value="200" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="rqstno" name="rqstno" value="<%=cmRequest.getString("rqstno")%>" />

	<table class="table-appr" style="width:100%;">
	<colgroup>
		<col width="120px" />
		<col width="250px" />
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>신청(인계)자</th>
		<td>
			<%=viewData.getString("rqstDeptName")%> / <%=viewData.getString("rqstUserName")%>
		</td>
		<th>신청일자</th>
		<td>
			<%=DateUtil.formatDateTime(viewData.getString("rqstRegtime"), "-", ":", 8)%>
		</td>
	</tr>
	<tr>
		<th>신청사유</th>
		<td colspan="3">
			<%=viewData.getString("reason")%>
		</td>
	</tr>
	<tr>
		<th>인수자</th>
		<td colspan="3">
			<%=viewData.getString("aucDeptName")%> / <%=viewData.getString("aucUserName")%>
		</td>
	</tr>
	</table>

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>