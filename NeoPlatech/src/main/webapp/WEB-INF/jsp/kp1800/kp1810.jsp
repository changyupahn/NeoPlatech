<%@page import="egovframework.com.cmm.service.EgovProperties"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "ZEUS국가연구시설장비 관리";
String curAction = "/kp1800/kp1810.do";
String curTabAction = "/kp1800/kp1810Tab.do";
String curGridAction = "/kp1800/kp1810Ajax.do";
String curSearchAction = "/kp1800/kp1810Search.do";
String xlsDnAction = "/kp1800/kp1810Excel.do";
String xlsDnDtlAction = "/kp1800/kp1810DtlExcel.do";
String detailAction = "/kp1800/kp1810Read.do";
String writeAction = "/kp1800/kp1810Write.do";
String operWriteAction = "/kp1800/kp1813Write.do";
String asWriteAction = "/kp1800/kp1814Write.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

String key = EgovProperties.getProperty("Globals.Zeus.apiKey");

int colbasewid = 320; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 350;

$(window).resize(function(){
	fnGridResize()
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = [
                '장비아이디'
                ,'장비등록번호'
                //,'장비구분코드'
                ,'자산코드'
                ,'한글장비명'
                ,'영문장비명'
                ,'장비상태코드'
                ,'장비상태'
                //,'활용범위코드'
                ,'활용범위'
                //,'장비상태코드'
                ,'활용상태'
                ,'기관명'
                //,'재원구분여부'
                ,'재원구분'
                ,'승인여부'
                ,'삭제여부'
                ,'등록자아이디'
                ,'등록자성명'
                ,'등록일자'
                ,'API등록'
  				];
var colModel01 = [
  				//{name:'equipId', index:'equipId', width:'0px', hidden:true},
  				{name:'equipId', index:'equipId', width:'140px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'equipNo', index:'equipNo', width:'140px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				//{name:'equipCd', index:'equipCd', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'fixedAsetNo', index:'fixedAsetNo', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'korNm', index:'korNm', width:'200px', align:'LEFT', columntype:'text', sortable:true, classes:'grid-col-TEXT'},
  				{name:'engNm', index:'engNm', width:'200px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'statusCd', index:'statusCd', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'statusNm', index:'statusNm', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				//{name:'useScopeCd', index:'useScopeCd', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'useScopeNm', index:'useScopeNm', width:'130px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				//{name:'idleDisuseCd', index:'idleDisuseCd', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'idleDisuseNm', index:'idleDisuseNm', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'organNm', index:'organNm', width:'130px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				//{name:'rndYn', index:'rndYn', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'rndNm', index:'rndNm', width:'200px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'confirmYn', index:'confirmYn', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'deleteYn', index:'deleteYn', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'registId', index:'registId', width:'130px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'registNm', index:'registNm', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'registDt', index:'registDt', width:'100px', align:'CENTER', columntype:'text', sortable:true, classes:'grid-col-TEXT'},
  				{name:'apiYn', index:'apiYn', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
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
		shrinkToFit: false,
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

			var equipId = $('#listInfo01').jqGrid('getRowData', rowId).equipId;

			fnDetail(equipId);
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.total, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.total);

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
function fnDetail(equipId){
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=detailAction%>?equipId=" + equipId + "&key=<%=key%>");
	$('#layer_iframe').show();
}
function fnWrite() {
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=writeAction%>?key=<%=key%>");
	$('#layer_iframe').show();
}
function fnOperWrite() {
	var selRowId = $("#listInfo01").getGridParam('selrow');

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		$('#layerPop').click();
		$('#iframe').attr("src", "<%=operWriteAction%>?equipId=" + obj.equipId);
		$('#layer_iframe').show();
	} else {
		alert("장비 목록을 선택한 후 버튼을 눌러주세요.");
		return;
	}
}
function fnAsWrite() {
	var selRowId = $("#listInfo01").getGridParam('selrow');

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		$('#layerPop').click();
		$('#iframe').attr("src", "<%=asWriteAction%>?equipId=" + obj.equipId);
		$('#layer_iframe').show();
	} else {
		alert("장비 목록을 선택한 후 버튼을 눌러주세요.");
		return;
	}
}
$(document).ready(function(){

	fnInitTabForm();
	fnGridList();
	fnInitLayerPopup();
});
function fnInitTabForm() {
	$.ajax({
		type : "POST",
		url : "<%=curTabAction%>",
		data : {
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
//인계자 선택 콜백
function fnSetKp9030_1(obj) {
	$('#sRqstUserNo').val(obj.userNo);
	$('#sRqstUserName').val(obj.userName);
}
//인수자 선택 콜백
function fnSetKp9030_2(obj) {
	$('#sAucUserNo').val(obj.userNo);
	$('#sAucUserName').val(obj.userName);
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

	<div class="SearchBox">
	<table class="search01">
	<colgroup>
		<col width="330px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>자산코드 / 장비명 / 등록자아이디 / 성명 / 장비등록번호 :</th>
		<td>
			<input type="text" id="keywords" name="keywords" value="<%=cmRequest.getString("keywords")%>" style="width:150px;" />

			<span class="button2"><input type="submit" value="검색" onclick="fnSearch();"></span>
		</td>
	</tr>
	</table>
	</div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="button" value="장비등록" onclick="fnWrite();"></span>
		<span class="button"><input type="button" value="운영일지" onclick="fnOperWrite();"></span>
		<span class="button"><input type="button" value="유지보수일지" onclick="fnAsWrite();"></span>
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
		</select>
	</td>
	</tr>
	</table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



