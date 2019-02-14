<%@page import="egovframework.com.cmm.service.EgovProperties"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "ZEUS국가연구시설장비 - 장비별운영현황";
String curAction = "/kp1800/kp1812.do";
String curTabAction = "/kp1800/kp1810Tab.do";
String curGridAction = "/kp1800/kp1812Ajax.do";
String curSearchAction = "/kp1800/kp1810Search.do";
String xlsDnAction = "/kp1800/kp1812Excel.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList statYearList = RequestUtil.getCommonList(request, "statYearList");

String firstStatYear = "";
if (statYearList.size() > 0) {
	firstStatYear = statYearList.getMap(0).getString("year");
}

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
                'equipId'
                ,'순번'
                ,'장비명'
                ,'자산코드'
                ,'장비등록번호'
                ,'취득일자'
                ,'운영시간'
                ,'운영일'
                ,'유지보수시간'
                ,'유지일'
  				];
var colModel01 = [
  				{name:'equipId', index:'equipId', width:'0px', hidden:true},
				{name:'rnum', index:'rnum', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'korNm', index:'korNm', width:'200px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'assetNo', index:'assetNo', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'equipNo', index:'equipNo', width:'150px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'takeDt', index:'takeDt', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
  				{name:'operHour', index:'operHour', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'operDate', index:'operDate', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'asHour', index:'asHour', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'asDate', index:'asDate', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
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
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

		},
		gridComplete : function() {
			//텍스트가 셀 너비보다 넓을 때 자동 줄 바꿈 처리
			$('.ui-jqgrid tr.jqgrow td').css("white-space", "normal");
		}
	});

	if(groupHeaders01.length > 0) {
		$('#listInfo01').jqGrid('setGroupHeaders',{ useColSpanStyle: true, groupHeaders:groupHeaders01 });

		// 크롬멀티 헤더 깨짐방지===
		nonBreakMultiHeader('listInfo01');
	    // =====================
	}
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
		<col width="110px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>작성연도 :</th>
		<td>
			<select id="sYear" name="sYear" style="height:22px; width:80px;">
				<%
				for (int i=0; i<statYearList.size(); i++) {
					CommonMap statYear = statYearList.getMap(i);
					String defaultSelectedYear = "";
					if (i==0) {
						defaultSelectedYear = statYear.getString("year");
					}

					out.print(String.format("<option value=\"%s\" %s>%s</option>"
							, statYear.getString("year")
							, (statYear.getString("year").equals(cmRequest.getString("sYear", defaultSelectedYear)) ? "selected=\"selected\"" : "")
							, statYear.getString("year")+"년")
							);
				}
				%>
			</select>

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



