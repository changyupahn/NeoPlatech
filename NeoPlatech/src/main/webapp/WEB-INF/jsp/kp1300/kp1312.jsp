<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "부속품";
String curAction = "/kp1300/kp1312.do";
String curGridAction = "/kp1300/kp1312Ajax.do";
String curTabAction = "/kp1300/kp1311Tab.do";
String writeFormAction = "/kp1300/kp1312Write.do";
String delProcAction = "/kp1300/kp1312DelProc.do";
String topAction = "/kp1300/kp1311.do";

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
var heightHip = 140;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	//$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                  'assetSeq','자산코드','MIS자산코드','품목명','취득일자','수량','취득가액','자산상태'
                  ];
var colModel01 = [
					{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
    				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'etisAssetNo', index:'etisAssetNo', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'itemName', index:'itemName', width:'150px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'aqusitDt', index:'aqusitDt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:fnFormatterDate},
    				{name:'assetCnt', index:'assetCnt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
    				{name:'assetAmt', index:'assetAmt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
    				{name:'assetStatusCd', index:'assetStatusCd', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
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

function fnDelRow() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("삭제할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
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
				assetSeq : $('#assetSeq').val(),
				saveJsonArray : JSON.stringify(saveJsonArray)
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("삭제 되었습니다.");
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
			<%
			if (!"".equals(viewData.getString("topAssetSeq"))
	    			&& !viewData.getString("assetSeq").equals(viewData.getString("topAssetSeq"))) {
			%>
			<span style="color:blue;font-weight:bold;position:relative;top:3px;">* 부속품으로 등록된 자산입니다.</span>
			<span class="button"><a href="<%=topAction%>?assetSeq=<%=viewData.getString("topAssetSeq")%>">주자산(<%=viewData.getString("topAssetNo")%>) 바로가기</a></span>
			<% } else { %>
			<span class="button2"><input type="button" value="부속품등록" onclick="fnWrite();"></span>
			<span class="button2"><input type="button" value="부속품삭제" onclick="fnDelRow();"></span>
			<% } %>
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



