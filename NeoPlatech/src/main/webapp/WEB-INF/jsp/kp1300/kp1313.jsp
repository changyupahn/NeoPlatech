<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "수리내역";
String curAction = "/kp1300/kp1313.do";
String curGridAction = "/kp1300/kp1313Ajax.do";
String curTabAction = "/kp1300/kp1311Tab.do";

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
                  'journalSeq','equipId','수리자명','수리시작일자','수리종료일자','수리비용','수리내용','작성자명','작성일자'
                  ];
var colModel01 = [
					{name:'journalSeq', index:'journalSeq', width:'0px', hidden:true},
					{name:'equipId', index:'equipId', width:'0px', hidden:true},
    				{name:'journalUserNm', index:'journalUserNm', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'journalSdt', index:'journalSdt', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'journalEdt', index:'journalEdt', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'journalPrc', index:'journalPrc', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
    				{name:'contents', index:'contents', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'registrentNm', index:'registrentNm', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'registDt', index:'registDt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
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
		</td>
	</tr>
	</table>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>



