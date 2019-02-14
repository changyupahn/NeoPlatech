<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "감가상각내역";
String curAction = "/kp1600/kp1620.do";
String curGridAction = "/kp1600/kp1620Ajax.do";
String xlsDnAction = "/kp1600/kp1620Excel.do";
String xlsDnDtlAction = "/kp1600/kp1620DtlExcel.do";
String xlsDnD1Action = "/kp1600/kp1620D1Excel.do";
String xlsDnD2Action = "/kp1600/kp1620D2Excel.do";
String xlsDnD3Action = "/kp1600/kp1620D3Excel.do";
String xlsDnD4Action = "/kp1600/kp1620D4Excel.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
String depreYear = cmRequest.getString("depreYear");
String depreMonth = cmRequest.getString("depreMonth");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 360;

$(window).resize(function(){
	fnGridResize()
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = [
                '자산구분코드','구분','수량','기초액(a)','수량','증가(b)','수량','감소(c)','수량','금액(d=a+b-c)','전년도상각누계액(a\')','증가(b\')','감소(c\')','당년도상각누계액(d\'=a\'+b\'-c\')','잔존가액'
  				];
var colModel01 = [
				{name:'자산구분코드', index:'자산구분코드', width:'0px', hidden:true},
  				{name:'결산과목명', index:'결산과목명', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', sortable : false},
  				{name:'당년도기초수량', index:'당년도기초수량', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도기초금액', index:'당년도기초금액', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도증가수량', index:'당년도증가수량', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도증가금액', index:'당년도증가금액', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도불용수량', index:'당년도불용수량', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도불용금액', index:'당년도불용금액', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도기말수량', index:'당년도기말수량', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도기말금액', index:'당년도기말금액', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'전년도누계감가상각비', index:'전년도누계감가상각비', width:'140px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도증가감가상각비', index:'당년도증가감가상각비', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도감소감가상각비', index:'당년도감소감가상각비', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'당년도누계감가상각비', index:'당년도누계감가상각비', width:'200px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'잔존가액', index:'잔존가액', width:'120px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', sortable : false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
  				];
var groupHeaders01 = [[
				{startColumnName:'당년도기초수량',numberOfColumns:2,skipInit:0,titleText:'당년도기초액'},
				{startColumnName:'당년도증가수량',numberOfColumns:2,skipInit:0,titleText:'당년도증가액'},
				{startColumnName:'당년도불용수량',numberOfColumns:2,skipInit:0,titleText:'당년도감소액(불용처분)'},
				{startColumnName:'당년도기말수량',numberOfColumns:2,skipInit:0,titleText:'당년도기말잔액'},
				{startColumnName:'전년도누계감가상각비',numberOfColumns:4,skipInit:0,titleText:'감가상각'}
                ]];

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
		shrinkToFit: false,
		cellEdit : false,
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

			var assetSeq = $('#listInfo01').jqGrid('getRowData', rowId).assetSeq;

			fnDetail(assetSeq);
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

			fnGridInvalidSession(data.totalRow);

			//합계 스타일 변경
			var ids = $("#listInfo01").getDataIDs();
			var rowData = $("#listInfo01").setRowData(ids[ids.length-1], false, {background: '#EEEEEF'});

		},
		gridComplete : function() {
			fnGridResize();
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

function fnXlsDnTotal() {
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnDetail() {
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnDtlAction%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnD1() {
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnD1Action%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnD2() {
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnD2Action%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnD3() {
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnD3Action%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnD4() {
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnD4Action%>";
	frm.target = "_self";
	frm.submit();
}

$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%=pageTitle%></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />

	<div class="SearchBox">
	<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>상각연도 :</th>
		<td>
			<select id="sDepreYear" name="sDepreYear" style="height:22px; width:80px;">
				<%
				int curYear = Integer.parseInt(DateUtil.getFormatDate("yyyy"));
				for (int i=curYear; i>=1956; i--) {
					%><option value="<%=i%>" <%=(i+"").equals(depreYear)?"selected=\"selected\"":""%>><%=i%>년</option><%
				}
				%>
			</select>
			<select id="sDepreMonth" name="sDepreMonth" style="height:22px; width:60px;">
				<%
				for (int i=1; i<=12; i++) {
					String month = StringUtil.lpad(""+i, 2, "0");
					%><option value="<%=month%>" <%=month.equals(depreMonth)?"selected=\"selected\"":""%>><%=month%>월</option><%
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
	<td width="50%" style="text-align: right;">
		<span class="button2"><input type="button" value="엑셀다운로드(총괄표)" onclick="fnXlsDnTotal();"></span>
		<span class="button2"><input type="button" value="엑셀다운로드(세부내역)" onclick="fnXlsDnDetail();"></span>
		<span class="button2"><input type="button" value="유형고정자산명세서" onclick="fnXlsDnD1();"></span>
		<span class="button2"><input type="button" value="감가상각비명세서" onclick="fnXlsDnD2();"></span>
		<span class="button2"><input type="button" value="고정자산처분명세서" onclick="fnXlsDnD3();"></span>
		<span class="button2"><input type="button" value="고정자산처분현황" onclick="fnXlsDnD4();"></span>

		&nbsp;
	</td>
	</tr>
	</table>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right; padding-top:5px;">
		(단위: 원)
	</td>
	</tr>
	</table>

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



