<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "불용처리 - 자산선택";
String curAction = "/kp1500/kp1514.do";
String curGridAction = "/kp1500/kp1514Ajax.do";
String curGridAction2 = "/kp1500/kp1514SelAjax.do";
String procAction = "/kp1500/kp1514Proc.do";
String apprDisuseAssetAction = "/kp1500/kp1515.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

String nextBtnStr = "불용처리";
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 130;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() / 2 - $('#SearchBox').height() - heightHip);
	$("#listInfo02").setGridWidth($(window).width() - widthHip);
	$("#listInfo02").setGridHeight($(window).height() / 2 - $('#SearchBox').height() - heightHip);
}

var colNames01 = [
				'rqstno','assetSeq',
				'신청부서','신청자','신청일자','승인일자','불용사유','창고반납(예정)일','자산구분','자산코드','품목명','상세명칭','취득일자','취득가액','수량','단가'
				];
var colModel01 = [
   				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
   				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
   				{name:'rqstDeptName', index:'rqstDeptName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'rqstUserName', index:'rqstUserName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'rqstRegtime', index:'rqstRegtime', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
   				{name:'disuseCompDt', index:'disuseCompDt', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
   				{name:'reason', index:'reason', width:'200px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'returnReseDt', index:'returnReseDt', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
   				{name:'assetTypeName', index:'assetTypeName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'itemName', index:'itemName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'aqusitDt', index:'aqusitDt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
   				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'assetCnt', index:'assetCnt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				];
var groupHeaders01 = [];

var colNames02 = [
  				'rqstno','assetSeq',
  				'신청부서','신청자','신청일자','불용사유','창고반납(예정)일','자산구분','자산코드','품목명','상세명칭','취득일자','취득가액','수량','단가'
  				];
var colModel02 = [
				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
				{name:'rqstDeptName', index:'rqstDeptName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'rqstUserName', index:'rqstUserName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'rqstRegtime', index:'rqstRegtime', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
				{name:'reason', index:'reason', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'returnReseDt', index:'returnReseDt', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
				{name:'assetTypeName', index:'assetTypeName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'itemName', index:'itemName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
				{name:'aqusitDt', index:'aqusitDt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter: fnFormatterDate},
				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'assetCnt', index:'assetCnt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
				];
var groupHeaders02 = [];

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
		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() / 2 - heightHip,
		multiselect: true,
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

			fnAdd(rowId);
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

function fnGridList2() {

	$("#listInfo02").jqGrid("GridUnload");

	$("#listInfo02").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction2%>",
		postData : $('#sForm').serializeObject(),
		mtype:'POST',
		jsonReader : {
			root    : "resultList",
			repeatitems : false
		},
		colNames : colNames02,
		colModel : colModel02,
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: false,
		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() / 2 - heightHip,
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

			fnDel(rowId);
		},
		loadComplete : function(data) {
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

function fnSearch() {
	fnGridReload("1");
}

function fnAdd(rowId) {
	var ids2 = $("#listInfo02").jqGrid('getDataIDs');

	if (rowId) {
		var sw = true;

		var obj = $("#listInfo01").jqGrid('getRowData', rowId);

		//이미 등록되어 있는지 확인
		for (var k=0; k<ids2.length; k++) {
			var obj2 = $("#listInfo02").jqGrid('getRowData', ids2[k]);
			if (obj.assetSeq == obj2.assetSeq) {
				sw = false;
			}
		}

		if (sw) {
			var addRowid = ids2.length + 1;
			$("#listInfo02").jqGrid('addRowData', addRowid, this);
			$("#listInfo02").jqGrid('setRowData', addRowid, obj);
		}

	} else {
		var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');

		if (ids.length == 0) {
			alert("추가할 행을 선택해주세요.");
			return;
		}

		var cnt = 0;
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var sw = true;

			//이미 등록되어 있는지 확인
			for (var k=0; k<ids2.length; k++) {
				var obj2 = $("#listInfo02").jqGrid('getRowData', ids2[k]);
				if (obj.assetSeq == obj2.assetSeq) {
					sw = false;
				}
			}

			if (sw) {
				var addRowid = ids2.length + 1 + cnt;
				$("#listInfo02").jqGrid('addRowData', addRowid, this);
				$("#listInfo02").jqGrid('setRowData', addRowid, obj);
				cnt++;
			}
		}
	}
}

function fnDel(rowId) {
	if (rowId) {

		$("#listInfo02").jqGrid('delRowData', rowId);

	} else {
		var ids = $('#listInfo02').jqGrid('getGridParam', 'selarrrow');
		var rowcnt = ids.length;

		if (rowcnt == 0) {
			alert("삭제할 행을 선택해주세요.");
			return;
		}

		for (var i=0; i<rowcnt; i++) {
			$("#listInfo02").jqGrid('delRowData', ids[0]);
		}
	}
}

function fnSave() {
	var ids = jQuery("#listInfo02").jqGrid('getDataIDs');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("자산을 추가해 주세요.");
		return;
	}

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo02").jqGrid('getRowData', ids[i]);
			/*var saveJsonObj = {
					rqstno: obj.rqstno,
					assetSeq: obj.assetSeq
			};*/
			saveJsonArray.push(obj);
		}
		//alert( JSON.stringify(saveJsonArray) );
		//if (confirm("저장 하시겠습니까?")) {
		if (true) {

			$('#saveJsonArray').val(JSON.stringify(saveJsonArray));

			var frm = document.sForm;
			frm.pageIdx.value = "1";
			frm.action = "<%=apprDisuseAssetAction%>";
			frm.target = "_self";
			frm.submit();
		}
	}
}

$(document).ready(function(){

	fnGridList();
	fnGridList2();
});
</script>

</head>
<body>

	<table style="width:100%">
	<tr>
	<td width="50%">
		<h2 class="title_left"><%= pageTitle %></h2>
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button2"><input type="button" value="추가" onclick="fnAdd();"></span>
		<span class="button2"><input type="button" value="<%=nextBtnStr%>" onclick="fnSave();"></span>
		<span class="button2"><input type="button" value="삭제" onclick="fnDel();"></span>
		&nbsp;
	</td>
	</tr>
	</table>

	<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:10px">
		<span style="font-weight:bold;">대상 자산을 체크 후 추가 버튼을 누르거나 더블 클릭해주세요.</span>
	</div>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="fnGridReload(1); return false;">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="pageSize" name="pageSize" value="100" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="apprType" name="apprType" value="<%=cmRequest.getString("apprType")%>" />
	<input type="hidden" id="saveJsonArray" name="saveJsonArray" value="" />


	<div class="SearchBox">
	<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>
			<select id="searchGubun" name="searchGubun" style="max-width:80px;">
				<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>자산코드</option>
				<option value="2" <%="2".equals(cmRequest.getString("searchGubun"))?"selected":""%>>상세명칭</option>
				<option value="3" <%="3".equals(cmRequest.getString("searchGubun"))?"selected":""%>>규격</option>
				<option value="4" <%="4".equals(cmRequest.getString("searchGubun"))?"selected":""%>>계정번호</option>
				<option value="5" <%="5".equals(cmRequest.getString("searchGubun"))?"selected":""%>>계약번호</option>
				<option value="6" <%="6".equals(cmRequest.getString("searchGubun"))?"selected":""%>>구매신청번호</option>
				<option value="8" <%="8".equals(cmRequest.getString("searchGubun"))?"selected":""%>>사용자</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />

			<span class="button2"><input type="button" value="검색" onclick="fnSearch();"></span>
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

	<table style="width:100%">
	<tr>
	<td>
		<h2 class="title_left">선택한 자산 목록</h2>
	</td>
	<td style="text-align: right;">
		&nbsp;
	</td>
	</tr>
	</table>

	<table id="listInfo02" class="scroll"></table>


	</form>

</body>
</html>