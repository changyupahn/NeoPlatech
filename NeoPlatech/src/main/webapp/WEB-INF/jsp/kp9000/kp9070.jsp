<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "판매업체선택";
String curAction = "/kp9000/kp9070.do";
String curGridAction = "/kp9000/kp9070Ajax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
String no = cmRequest.getString("no").replaceAll("\\D","");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 200;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                  'compSeq','판매업체코드','판매업체명'
                  ];
var colModel01 = [
					{name:'compSeq', index:'compSeq', width:'0px', hidden:true},
    				{name:'compCd', index:'compCd', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
    				{name:'compName', index:'compName', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
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
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		ondblClickRow:function(rowId){
			fnSelect(rowId);
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

function fnSelect(rowId) {

	var selRowId = "";
	if (rowId) {
		selRowId = rowId;
	} else {
		selRowId = $("#listInfo01").getGridParam('selrow');
	}

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		opener.fnSetKp9070<%=("".equals(no)?"":"_"+no)%>(obj);
		window.close();
	} else {
		alert("업체를 선택해 주세요");
		return;
	}
}

$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body>

	<h2 class="title_left"><%= pageTitle %></h2>

	<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:10px">
		<span style="font-weight:bold;">업체를 검색하여 선택 버튼을 누르거나 더블 클릭해주세요.</span>
	</div>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="fnGridReload(1); return false;">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="pageSize" name="pageSize" value="100" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />

	<div class="SearchBox">
	<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>
			<select id="searchGubun" name="searchGubun" style="height:22px">
				<option value="1" <%="1".equals(cmRequest.getString("searchGubun"))?"selected":""%>>업체명</option>
				<option value="2" <%="2".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>업체코드</option>
				<option value="3" <%="3".equals(cmRequest.getString("searchGubun"))?"selected":""%>>사업자등록번호</option>
				<option value="4" <%="4".equals(cmRequest.getString("searchGubun"))?"selected":""%>>대표자</option>
				<option value="5" <%="5".equals(cmRequest.getString("searchGubun"))?"selected":""%>>업태</option>
				<option value="6" <%="6".equals(cmRequest.getString("searchGubun"))?"selected":""%>>종목</option>
				<option value="7" <%="7".equals(cmRequest.getString("searchGubun"))?"selected":""%>>주소</option>
				<option value="8" <%="8".equals(cmRequest.getString("searchGubun"))?"selected":""%>>연락처</option>
				<option value="9" <%="9".equals(cmRequest.getString("searchGubun"))?"selected":""%>>이메일</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />

			<span class="button2"><input type="button" value="검색" onclick="fnSearch();"></span>
			<span class="button2"><input type="button" value="선택" onclick="fnSelect();"></span>
		</td>
	</tr>
	</table>
	</div>

	<table id="listInfo01" class="scroll"></table>

	<table style="width:100%;border-collapse:collapse;border:0;">
	<tr>
	<td class="c">
		<div id="paginate" class="paginate"></div>
	</td>
	</tr>
	</table>

	</form>

</body>
</html>