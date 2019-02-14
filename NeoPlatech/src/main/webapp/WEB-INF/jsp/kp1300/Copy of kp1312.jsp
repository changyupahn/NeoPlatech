<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "부속품";
String curAction = "/kp1300/kp1312.do";
String curGridAction = "/kp1300/kp1312Ajax.do";
String curTabAction = "/kp1300/kp1311Tab.do";
String writeFormAction = "/kp1300/kp1312Write.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 310; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 140;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	//$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                  'assetSeq','compntNo','상세명칭','영문명','취득일자','수량','단가','취득가액','비고'
                  ];
var colModel01 = [
					{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
					{name:'compntNo', index:'compntNo', width:'0px', hidden:true},
    				{name:'compntName', index:'compntName', width:'250px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'compntEname', index:'compntEname', width:'120px', align:'CENTER', columntype:'text', formatter:fnFormatterDate, sortable:false, classes:'grid-col-TEXT'},
    				{name:'compntAqusitDt', index:'compntAqusitDt', width:'120px', align:'CENTER', columntype:'text', formatter:fnFormatterDate, sortable:false, classes:'grid-col-TEXT'},
    				{name:'compntCnt', index:'compntCnt', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'compntUnitAmt', index:'compntUnitAmt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'compntAqusitAmt', index:'compntAqusitAmt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'compntRemark', index:'compntRemark', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
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
		shrinkToFit: true,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: -1,
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
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

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

function fnSearch() {
	fnGridReload("1");
}

function fnWrite() {
	location.href = "<%=writeFormAction%>?assetSeq=" + $('#assetSeq').val();
}

function fnInitTabForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curTabAction%>",
		data : {
			colcnt : colcnt,
			assetSeq : $('#assetSeq').val()
		},
		success:function(data)
		{
			$('#TabBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}
$(document).ready(function(){

	fnInitTabForm();
	fnGridList();
});
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<div id="TabBox" class="TabBox"></div>

	<table style="width:100%;">
	<colgroup>
		<col width="" />
		<col width="510px" />
	</colgroup>
	<tr>
		<td style="padding:10px 0 0 0;border-collapse:collapse;margin:0;">
			<h2 class="title_left" style="margin-bottom:0px;"><%=pageTitle%></h2>
		</td>
		<td style="text-align:right;">
			<span class="button2"><input type="button" value="부속품등록" onclick="fnWrite();"></span>
			&nbsp;
		</td>
	</tr>
	</table>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>



