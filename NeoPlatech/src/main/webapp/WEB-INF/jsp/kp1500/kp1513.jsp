<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "불용처분및폐기 (자산별)";
String curAction = "/kp1500/kp1513.do";
String curGridAction = "/kp1500/kp1513Ajax.do";
String curSearchAction = "/kp1500/kp1510Search.do";
String xlsDnAction = "/kp1500/kp1510Excel.do";
String xlsDnDtlAction = "/kp1500/kp1510DtlExcel.do";
String detailAction = "/kp1500/kp1511.do";
String curTabAction = "/kp1500/kp1510Tab.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 320; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 400;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
}

var colNames01 = [
                'rqstno','자산코드','품목명','상세명칭','신청일자','승인일자','처리일자','신청부서','신청자','자산건수','승인상태'
  				];
var colModel01 = [
  				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
  				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'itemName', index:'itemName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'assetName', index:'assetName', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'rqstRegtime', index:'rqstRegtime', width:'100px', align:'CENTER', columntype:'text', formatter:fnFormatterDate, classes:'grid-col-TEXT'},
  				{name:'disuseCompDt', index:'disuseCompDt', width:'100px', align:'CENTER', columntype:'text', formatter:fnFormatterDate, classes:'grid-col-TEXT'},
  				{name:'disuseProcDt', index:'disuseProcDt', width:'100px', align:'CENTER', columntype:'text', formatter:fnFormatterDate, classes:'grid-col-TEXT'},
  				{name:'rqstDeptName', index:'rqstDeptName', width:'250px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'rqstUserName', index:'rqstUserName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'rqstAssetCnt', index:'rqstAssetCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'rqstStatusName', index:'rqstStatusName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
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
		rownumbers: false,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
//		shrinkToFit: false,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
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

			var rqstno = $('#listInfo01').jqGrid('getRowData', rowId).rqstno;

			fnDetail(rqstno);
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

			fnGridInvalidSession(data.totalRow);

		},
		gridComplete : function() {
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
function fnXlsDn(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction%>";
	frm.target = "_self";
	frm.submit();
}
function fnXlsDn2(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnDtlAction%>";
	frm.target = "_self";
	frm.submit();
}
function fnDetail(rqstno){
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=detailAction%>?rqstno=" + rqstno);
	$('#layer_iframe').show();
}
$(document).ready(function(){

	fnInitTabForm();
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
function fnDisusePop(rqstno) {
	$('#layerPop').click();
	$('#iframe').attr("src", "/kp1500/kp1514.do");
	$('#layer_iframe').show();
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
//신청자 선택 콜백
function fnSetKp9030(obj) {
	$('#sRqstUserNo').val(obj.userNo);
	$('#sRqstUserName').val(obj.userName);
}
//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#sItemSeq').val(obj.seq);
	$('#sItemCd').val(obj.cd);
	$('#sItemName').val(obj.nm);
}
//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	$('#sRqstDeptNo').val(obj.cd);
	$('#sRqstDeptName').val(obj.nm);
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%=pageTitle%></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />

	<div id="divPopupLayer"></div>

	<div id="TabBox" class="TabBox"></div>

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
		<span class="button"><input type="button" value="엑셀다운로드(자산별)" onclick="fnXlsDn2();"></span>
		<span class="button"><input type="button" value="불용신청" onclick="kp8010Pop('3');"></span>
		<% if (ssAuthManager) { %>
		<span class="button"><input type="button" value="불용처리" onclick="fnDisusePop();"></span>
		<% } %>
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

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



