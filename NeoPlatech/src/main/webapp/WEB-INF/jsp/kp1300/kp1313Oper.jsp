<%@page import="egovframework.com.cmm.service.EgovProperties"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "운영일지 목록";
String curAction = "/kp1300/kp1313Oper.do";
String curTabAction = "/kp1300/kp1313OperTab.do";
String assetTabAction = "/kp1300/kp1311Tab.do";
String curGridAction = "/kp1300/kp1313OperAjax.do";
String procAction = "/kp1300/kp1313OperProc.do";
String modifyAction = "/kp1300/kp1313OperWrite.do";
String delProcAction = "/kp1300/kp1313OperDelProc.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

String key = EgovProperties.getProperty("Globals.Zeus.apiKey");

int colbasewid = 310; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 250;

$(window).resize(function(){
	fnGridResize()
});

function fnGridResize() {
	$("#listInfo01").setGridWidth(($(window).width() - widthHip) / 2);
	//$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                'operSeq'
                ,'assetSeq'
                ,'자산코드'
                ,'한글장비명'
                ,'이용시작일자'
                ,'이용종료일자'
                ,'이용료'
                ,'등록일자'
  				];
var colModel01 = [
  				{name:'operSeq', index:'journalSeq', width:'0px', hidden:true},
  				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
  				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'assetName', index:'assetName', width:'200px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'journalSdt', index:'journalSdt', width:'130px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'journalEdt', index:'journalEdt', width:'130px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'journalPrc', index:'journalPrc', width:'130px', align:'RIGHT', columntype:'text', sortable:false, classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'registDt', index:'registDt', width:'130px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
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
			root    : "pageList",
			repeatitems : false
		},
		colNames : colNames01,
		colModel : colModel01,
		rownumbers: false,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: true,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: (($(window).width() - widthHip) / 2),
		height: -1,
		multiselect: false,
		resizeStop: function() {
			//헤더 틀어짐 방지
			//nonBreakMultiHeader('listInfo01');
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
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.total, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.total);

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

function fnSearch(){
	fnGridReload("1");
}

function fnInitAssetTabForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=assetTabAction%>",
		data : {
			assetSeq : $('#assetSeq').val()
		},
		success:function(data)
		{
			$('#AssetTabBox').html(data);
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

	<% if (!"".equals(cmRequest.getString("assetSeq"))) { %>
	fnInitAssetTabForm();
	<% } %>
	fnInitTabForm();
	fnGridList();
});
function fnInitTabForm() {
	$.ajax({
		type : "POST",
		url : "<%=curTabAction%>",
		data : {
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
		}
	});
}
function fnModify() {

	var selRowId = $("#listInfo01").getGridParam('selrow');

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		location.href = "<%=modifyAction%>?equipId=" + obj.equipId + "&journalSeq=" + obj.journalSeq + "&assetSeq=<%=cmRequest.getString("assetSeq")%>";
	} else {
		alert("수정할 목록을 선택한 후 버튼을 눌러주세요.");
		return;
	}
}
function fnDel() {
	var selRowId = $("#listInfo01").getGridParam('selrow');

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		if (confirm("삭제 하시겠습니까?")) {
			fnLoadingS2();

			$('#journalSeq').val(obj.journalSeq);

			$.ajax({
				type : "POST",
				url : "<%=delProcAction%>",
				data : fnSerializeObject(),
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("삭제 되었습니다.");
						location.href = "<%=curAction%>?equipId=" + $('#equipId').val() + "&assetSeq=<%=cmRequest.getString("assetSeq")%>";
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
	} else {
		alert("삭제할 목록을 선택한 후 버튼을 눌러주세요.");
		return;
	}
}
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<div id="AssetTabBox" class="AssetTabBox"></div>

	<table style="width:100%">
		<tr>
		<td width="50%">
			<h2 class="title_left"><%= pageTitle %></h2>
		</td>
		<td width="50%" style="text-align: right;">
			<span class="button2"><input type="button" value="일지수정" onclick="fnModify();"></span>
			<span class="button2"><input type="button" value="삭제" onclick="fnDel();"></span>
			&nbsp;
		</td>
		</tr>
	</table>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="equipId" name="equipId" value="<%=viewData.getString("equipId")%>" />
	<input type="hidden" id="equipNo" name="equipNo" value="<%=viewData.getString("equipNo")%>" />
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />
	<input type="hidden" id="journalSeq" name="journalSeq" value="" />

	<table style="width:100%;border-collapse:collapse;border:0;">
	<colgroup>
		<col width="50%" />
		<col width="" />
	</colgroup>
	<tr>
	<td style="vertical-align:top">

		<div class="date">
			<span><%=DateUtil.getFormatDate("yyyy")%>. <%=DateUtil.getFormatDate("MM")%></span>
		</div>

		<table class="table_calendar">
		<tr>
		<th class="sun">일</th>
		<th>월</th>
		<th>화</th>
		<th>수</th>
		<th>목</th>
		<th>금</th>
		<th class="sat">토</th>
		</tr>
		<%
		CalendarUtil cal = new CalendarUtil();
		int[][] myCalendar = cal.getCalendar();
		String today = DateUtil.getFormatDate("yyyyMMdd");
		for (int i=0; i<myCalendar.length; i++) {
		%>
		<tr>
			<%
			for (int j=0; j<7; j++) {
				int day = myCalendar[i][j];
				String ymd = "" + cal.getYear() + StringUtil.lpad(""+cal.getMonth(), 2, "0") + StringUtil.lpad(""+day, 2, "0");
				String todayCls = "";
				if (today.equals(ymd)) {
					todayCls = "background-color:#dbf4ff";
				}
				%>
				<td class="<%=j==0?"sun":j==6?"sat":"day"%>" style="<%=todayCls%>">
				<%
				if (day != 0) {
					%>
					<span class="day"><%=day%></span>
					<%
				}
			}
			%>
		</tr>
		<%
		}
		%>
		</table>
	</td>
	<td style="vertical-align:top;padding-top:0px;">

		<div id="TabBox" class="TabBox"></div>

		<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:0px">
			<span style="color:black;font-weight:bold;">■ 운영일지 목록</span>
		</div>

		<table id="listInfo01" class="scroll"></table>

	</td>
	</tr>
	</table>

	</form>

</body>
</html>



