<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "반출반입정보";
String curAction = "/kp1400/kp1421.do";
String curGridAction = "/kp1400/kp1421Ajax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
CommonList viewDataExtList = RequestUtil.getCommonList(request, "viewDataExtList");
CommonMap viewDataIn = RequestUtil.getCommonMap(request, "viewDataIn");
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
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
				'assetSeq',
				'반입여부', '자산코드','품목명','상세명칭', '취득일자', '취득가액', '잔존가액', '위치', '사용자'
				];
var colModel01 = [
   				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
   				{name:'inYnStr', index:'inYnStr', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
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
<body style="overflow:auto;padding:1px;">

	<h2 class="title_left"><%= pageTitle %></h2>

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
		<th class="">신청자</th>
		<td>
			<%=viewData.getString("rqstDeptName")%> / <%=viewData.getString("rqstUserName")%>
		</td>
		<th>신청일자</th>
		<td>
			<%=DateUtil.formatDateTime(viewData.getString("rqstRegtime"), "-", ":", 8)%>
		</td>
	</tr>
	<tr>
		<th>반출일</th>
		<td>
			<%=DateUtil.formatDateTime(viewData.getString("outDt"), "-", ":", 8)%>
		</td>
		<th>반입예정일</th>
		<td>
			<%=DateUtil.formatDateTime(viewData.getString("expInDt"), "-", ":", 8)%>
		</td>
	</tr>
	<tr>
		<th>반출처</th>
		<td colspan="3">
			<%=viewData.getString("outPlace")%>
		</td>
	</tr>
	<tr>
		<th>반출사유</th>
		<td colspan="3">
			<%=viewData.getString("reason")%>
			<% if (!"3".equals(viewData.getString("rqstStatusCd"))) { %>
			<span style="color:blue;font-weight:bold;">[<%=viewData.getString("rqstStatusName")%>]</span>
			<% } %>
		</td>
	</tr>
	<%
	for (int i=0; i<viewDataExtList.size(); i++) {
		CommonMap viewDataExt = viewDataExtList.getMap(i);
	%>
	<tr>
		<th>연장사유</th>
		<td colspan="3">
			<%=viewDataExt.getString("extReason")%>
			<% if (!"3".equals(viewDataExt.getString("rqstStatusCd"))) { %>
			<span style="color:blue;font-weight:bold;">[<%=viewDataExt.getString("rqstStatusName")%>]</span>
			<% } %>
		</td>
	</tr>
	<tr>
		<th>연장 반입예정일</th>
		<td colspan="3">
			<%=DateUtil.formatDateTime(viewDataExt.getString("extDt"), "-", ":", 8)%>
		</td>
	</tr>
	<%
	}
	%>
	<%
	if (!viewDataIn.isEmpty()) {
	%>
	<tr>
		<th>반입일</th>
		<td colspan="3">
			<%=DateUtil.formatDateTime(viewDataIn.getString("inDt"), "-", ":", 8)%>
			<% if (!"3".equals(viewDataIn.getString("rqstStatusCd"))) { %>
			<span style="color:blue;font-weight:bold;">[<%=viewDataIn.getString("rqstStatusName")%>]</span>
			<% } %>
		</td>
	</tr>
	<tr>
		<th>반입비고</th>
		<td colspan="3">
			<%=viewDataIn.getString("inRemark")%>
		</td>
	</tr>
	<%
	}
	%>
	</table>

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>