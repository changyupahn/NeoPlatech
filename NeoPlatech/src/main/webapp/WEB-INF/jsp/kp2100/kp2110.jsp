<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>    
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>    
<%
String pageTitle = "부자재입고관리";
String curAction = "/kp2100/kp2110.do";
String curGridAction = "/kp2100/kp2110Ajax.do";
String curSearchAction = "/kp2100/kp2110Search.do";
String xlsDnAction = "/kp2100/kp2110Excel.do";
String detailAction = "/kp2100/kp2111.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 220; //검색 폼 동적 사이즈 구성을 위한 넓이 값
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
                  'rowNum'
                 , '구분ID'
                 , '고유key'
                 , '구매처'
                 , '모델번호'
                 , '제품일련번호'
                 , '원재료명'
                 , 'LG측제번'
                 , 'LG투입라인명'
                 , '제품구분기호'
                 , '조립수량'
                 , '사출수량'
                 , '생산잔량'                                  
                 , '주문수량'
                 , '확정일'
                 ];
                  

var colModel01 = [  
                  {name:'rowNum', index:'rowNum', width:'0px', hidden:true}
                 ,{name:'odKey', index:'odKey', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'} 
                 ,{name:'primKey', index:'primKey', width:'400px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'buyer', index:'buyer', width:'100px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'model', index:'model', width:'100px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'partNo', index:'partNo', width:'100px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'resinCore', index:'resinCore', width:'300px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'demandId', index:'demandId', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'lgLine', index:'lgLine', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'item', index:'item', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                 ,{name:'qty', index:'qty', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                 ,{name:'injectionQty', index:'injectionQty', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                 ,{name:'residualQty', index:'residualQty', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}                                 
                 ,{name:'planQty', index:'planQty', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                 ,{name:'fixDay', index:'fixDay', width:'100px', hidden:true, align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
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
		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: true,
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

		var frm = document.sForm;			
		frm.action = "<%=detailAction%>";
		frm.target = "_self";
		frm.submit();
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
		<span class="button"><input type="button" value="검색초기화" onclick="fnInitSearchForm();"></span>
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

