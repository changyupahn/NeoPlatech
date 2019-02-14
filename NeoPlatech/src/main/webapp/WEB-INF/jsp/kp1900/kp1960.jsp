<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "담당지역관리";
String curAction = "/kp1900/kp1960.do";
String curGridAction = "/kp1900/kp1960UserAjax.do";
String curGrid2Action = "/kp1900/kp1960DeptChkAjax.do";
String curGrid3Action = "/kp1900/kp1960DeptAllAjax.do";
String procAction = "/kp1900/kp1960Proc.do";
String delProcAction = "/kp1900/kp1960DelProc.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 280; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<style type="text/css">
.hcolpos {
	position:relative;
	top:-1px;
	left:2px;
}
</style>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 340;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth(parseInt($(window).width() * 0.3) - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
	$("#listInfo02").setGridWidth(parseInt($(window).width() * 0.3) - widthHip);
	$("#listInfo02").setGridHeight($(window).height() - heightHip);
	$("#listInfo03").setGridWidth(parseInt($(window).width() * 0.4) - widthHip);
	$("#listInfo03").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
				'관리자ID','관리자명','매칭'
				];
var colModel01 = [   				
   				{name:'userId', index:'userId', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'userName', index:'userName', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'userDeptCnt', index:'userDeptCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
   				];
var groupHeaders01 = [];

var colNames02 = [
				'담당부서'
				];
var colModel02 = [
   				{name:'deptName', index:'deptName', align:'LEFT', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";

						return value;
					}}
   				];
var groupHeaders02 = [];

var colNames03 = [
  				'전체부서','매칭'
  				];
var colModel03 = [
     				{name:'deptName', index:'deptName', align:'LEFT', width:'200px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
  					formatter: function(value, options, rData)
  					{
  						if (value == null) value = "";

  						return value;
  					}},
  					{name:'userDeptCnt', index:'userDeptCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
     				];
var groupHeaders03 = [];

function fnGridList() {

	$("#listInfo01").jqGrid("GridUnload");

	$("#listInfo01").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction%>",
		postData : fnSerializeObject(),
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
		sortable : false,
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
		onSelectRow: function(rowid) {
			fnDetail(rowid);
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			fnGridResize();

			var ids = $("#listInfo01").jqGrid("getDataIDs");
			if (ids.length > 0) {
				$("#listInfo01").setSelection(ids[0], true);
				fnDetail(ids[0]);
			}
		}
	});
}

function fnGridList2() {

	$("#listInfo02").jqGrid("GridUnload");

	$("#listInfo02").jqGrid({
		datatype : 'json',
		url : "<%=curGrid2Action%>",
		postData : fnSerializeObject(),
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
		shrinkToFit: true,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : false,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
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
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			fnGridResize();
		}
	});
}

function fnGridList3() {

	$("#listInfo03").jqGrid("GridUnload");

	$("#listInfo03").jqGrid({
		datatype : 'json',
		url : "<%=curGrid3Action%>",
		postData : fnSerializeObject(),
		mtype:'POST',
		jsonReader : {
			root    : "resultList",
			repeatitems : false
		},
		colNames : colNames03,
		colModel : colModel03,
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: true,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : false,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
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
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			fnGridResize();
		}
	});
}

function fnColSelected(num) {
	if (num == 10) {
		$('input[id^="grantDeptYn_"]').attr("checked", false);
	} else if (num == 11) {
		$('input[id^="grantDeptYn_"]').attr("checked", true);
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

function fnGridReload2(pageIdx){
	var frm = document.sForm;
	if (pageIdx) {
		frm.pageIdx.value = pageIdx;
	}

	$("#listInfo02").setGridParam({
		postData: $('#sForm').serializeObject()
	}).trigger("reloadGrid");
}

function fnGridReload3(pageIdx){
	var frm = document.sForm;
	if (pageIdx) {
		frm.pageIdx.value = pageIdx;
	}

	$("#listInfo03").setGridParam({
		postData: $('#sForm').serializeObject()
	}).trigger("reloadGrid");
}

function fnSearch2(){
	fnGridReload("1");
}

function fnDetail(rowid){
	var userId = $("#listInfo01").jqGrid("getRowData", rowid).userId;
	$('#userId').val(userId);
	fnGridList2();
}

function fnSave() {
	var ids = $('#listInfo03').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];
	
	if (ids.length == 0) {
		alert("추가할 부서를 선택해주세요.");
		return;
	}

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo03").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					deptName: obj.deptName,
					grantDeptYn: "Y"
			};
			saveJsonArray.push(saveJsonObj);
		}

		if (confirm("저장 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=procAction%>",
				data : {
					userId : $('#userId').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("저장 되었습니다.");
						fnGridList2();
						fnGridList3();
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
}

function fnDel() {
	var ids = $('#listInfo02').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];
	
	if (ids.length == 0) {
		alert("삭제할 부서를 선택해주세요.");
		return;
	}

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo02").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					deptName: obj.deptName
			};
			saveJsonArray.push(saveJsonObj);
		}

		if (confirm("저장 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=delProcAction%>",
				data : {
					userId : $('#userId').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("삭제 되었습니다.");
						fnGridList2();
						fnGridList3();
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
}

$(document).ready(function(){

	fnGridList();
	fnGridList3();
	fnInitLayerPopup();
});
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
	<input type="hidden" id="userId" name="userId" value="" />

	<div id="divPopupLayer"></div>

	<table style="width:100%;border-collapse:collapse;border:0px;">
	<tr>
	<td width="29%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td>
			&nbsp;
		</td>
		</tr>
		</table>

		<table id="listInfo01" class="scroll"></table>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="29%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td class="r2">
			<% if (ssAuthManager) { %>
			<span class="button"><input type="button" value="담당부서제거" onclick="fnDel()" ></span>
			<% } %>
		</td>
		</tr>
		</table>

		<table id="listInfo02" class="scroll"></table>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="38%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td class="r2">
			<% if (ssAuthManager) { %>
			<span class="button"><input type="button" value="담당부서추가" onclick="fnSave()" ></span>
			<% } %>
		</td>
		</tr>
		</table>

		<table id="listInfo03" class="scroll"></table>
	</td>
	</tr>
	</table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



