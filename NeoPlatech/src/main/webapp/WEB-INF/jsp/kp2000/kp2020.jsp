<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "태그발행내역";
String curAction = "/kp2000/kp2020.do";
String curGridAction = "/kp2000/kp2020Ajax.do";
String curSearchAction = "/kp2000/kp2020Search.do";
String xlsDnAction = "/kp2000/kp2020Excel.do";
String detailAction = "/kp1300/kp1311.do";
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
var heightHip = 300;

$(window).resize(function(){
	fnGridResize()
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = [
                  'printSeq','assetSeq','assetNo','자산번호','품명','규격','출력요청일시','상태'
                  ];
var colModel01 = [
					{name:'printSeq', index:'printSeq', width:'0px', hidden:true},
					{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
					{name:'assetNo', index:'assetNo', width:'0px', hidden:true},
					{name:'etc2', index:'etc2', width:'200px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
					{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
					{name:'assetSize', index:'assetSize', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'printDt', index:'printDt', width:'120px', align:'CENTER', columntype:'text', formatter:fnFormatterDate, sortable:false, classes:'grid-col-TEXT'},
    				{name:'printYnStr', index:'printYnStr', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
    					formatter: function(value, options, rData)
    					{
    						if (value == null) value = "";
    						else if (value == "출력성공") value = '<span style="font-weight:bold;color:blue">출력성공</span>';
    						else if (value == "출력실패") value = '<span style="font-weight:bold;color:red">출력실패</span>';
    						return value;
    					}}
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
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		ondblClickRow:function(rowId){
			fnDetail(rowId);
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
function fnDetail(rowId) {

	var selRowId = "";
	if (rowId) {
		selRowId = rowId;
	} else {
		selRowId = $("#listInfo01").getGridParam('selrow');
	}

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		$('#layerPop').click();
		$('#iframe').attr("src", "<%=detailAction%>?assetSeq=" + obj.assetSeq);
		$('#layer_iframe').show();
	} else {
		alert("상세보기 할 내역을 선택해 주세요");
		return;
	}
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

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="<spring:message code="button.search"/>" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.download.excel"/>" onclick="fnXlsDn();"></span>
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



