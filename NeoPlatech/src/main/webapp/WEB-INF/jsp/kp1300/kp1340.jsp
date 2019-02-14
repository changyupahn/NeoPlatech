<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "불용자산";
String detailAction = "/kp1300/kp1311.do";
String curAction = "/kp1300/kp1340.do";
String curGridAction = "/kp1300/kp1340Ajax.do";
String curSearchAction = "/kp1300/kp1310Search.do";
String xlsDnAction = "/kp1300/kp1340Excel.do";
String detailRowAction = "/asset/selectRow.do";
String tagPrintAction = "/asset/print/exec.do";
String tagPrintReqAction = "/asset/print/req.do";
String tagPrintAjaxAction = "/reqst/print/execAjax.do";
String tagPrintCheckAction = "/asset/print/exec.do";
String regManageAction = "/asset/writeForm.do";
String delAction = "/asset/deleteProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 280; //검색 폼 동적 사이즈 구성을 위한 넓이 값
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
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = ["assetSeq"
<%
CommonList dispMngList = RequestUtil.getCommonList(request, "dispAssetList");
if (dispMngList != null) {
	for (int k=0; k<dispMngList.size(); k++) {
		CommonMap dispMng = dispMngList.getMap(k);
		out.println(", '" + dispMng.getString("logicalName") + "'");
	}
}
%>
];

var colModel01 = [
				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true}
<%
if (dispMngList != null) {
	for (int k=0; k<dispMngList.size(); k++) {
		CommonMap dispMng = dispMngList.getMap(k);

		String logicalName = dispMng.getString("logical_name");
		String physicalName = dispMng.getString("physical_name");
		String align = dispMng.getString("default_align", "center");
		String fommater = "";
		if ("TEXT".equalsIgnoreCase(dispMng.getString("data_disp_type"))) { //문자형
			fommater = "";
		} else if ("NUMBER".equalsIgnoreCase(dispMng.getString("data_disp_type"))) {	//숫자형
			fommater = ", formatter:'currency', formatoptions:{thousandsSeparator:\",\", decimalPlaces: 0}";
		} else if ("DATE".equalsIgnoreCase(dispMng.getString("data_disp_type"))) {	//날짜형
			fommater = ", formatter: fnFormatterDate";
		}

		if ("TOP_ASSET_NO".equals(physicalName)) {
			%>
			,{name:'topAssetNo', index:'topAssetNo', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:true, editable:false,
				formatter: function(value, options, rData)
				{
					if (value == "부속품") {
						value = '<img src="/images/icon/reply_arrow.png" />';
						//value = '<img src="/images/icon/arrow01.png" />';
					} else {
						value = value;
					}

					return value;
				}}
			<%
		} else {
			out.println("," + String.format("{name:'%s', index:'%s', width:'%spx', align:'%s', columntype:'text', classes:'grid-col-%s'%s}"
					, CamelUtil.convert2CamelCase(physicalName)
					, CamelUtil.convert2CamelCase(physicalName)
					, dispMng.getString("defaultWidth")
					, align
					, dispMng.getString("data_disp_type")
					, fommater
					));
		}
	}
}
%>
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

			/* var ids = $("#listInfo01").jqGrid('getDataIDs');

			for (var i=0; i<ids.length; i++) {
				var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
				$("#listInfo01").jqGrid('setCell' ,i + 1 , 'repoDt', obj.repoDt, 'not-editable-cell' );
				//$("#jqg_listInfo1_" + (k+1)).attr("disabled", "disabled");
				//$("#jqg_listInfo1_" + (k+1)).attr("style", "display:none");
				//$("#jqg_listInfo1_" + (k+1)).remove();
			} */


			//텍스트가 셀 너비보다 넓을 때 자동 줄 바꿈 처리
			//$('.ui-jqgrid tr.jqgrow td').css("white-space", "normal");
			//헤더 틀어짐 방지
			//nonBreakMultiHeader('listInfo01');

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
function fnRegManage(){

	var assetManage = fnOpenPop2("", "assetManage");

	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=regManageAction%>";
	frm.target = "assetManage";
	frm.submit();
	assetManage.focus();
}
function fnDel(){

	var rowIds = $("#listInfo01").jqGrid('getGridParam', "selarrrow" );
	var cnt = rowIds.length;
	var deleteRows = [];

	if (cnt == 0) {
		alert("<spring:message code="alert.nochk.delete.target"/>");
	} else {

		if (!confirm("<spring:message code="alert.confirm.delete"/>")) {
			return;
		}

		for (var i=0; i<cnt; i++) {
			var data = $("#listInfo01").getRowData(rowIds[i]);
			deleteRows.push(data.assetNo);
		}

		$.ajax({
			type : "POST",
			url : "<%=delAction%>",
			data : {
				assetNo : deleteRows
			},
			dataType : "json",
			success:function(data)
			{
				if (data.result == "OK") {
					for (var i=0; i<cnt; i++) {
						$("#listInfo01").delRowData(rowIds[0]);
					}

					alert("<spring:message code="alert.ok.delete"/>");
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("<spring:message code="alert.error.fail"/>");
			},
			complete:function()
			{
			}
		});
	}
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
		alert("자산을 선택해 주세요");
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
			colcnt : colcnt,
			sAssetDiv : '<%=cmRequest.getString("sAssetDiv")%>'
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
	<input type="hidden" id="asset_no" name="asset_no" value="<%=cmRequest.getString("asset_no")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="printType" name="printType" value="" />
	<input type="hidden" id="chkAssetNo" name="chkAssetNo" value="" />

	<div id="divPopupLayer"></div>

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="검색초기화" onclick="fnInitSearchForm();"></span>
		<span class="button"><input type="button" value="상세보기" onclick="fnDetail();"></span>
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
		<option value="500" <%="500".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="500" /></option>
		</select>
	</td>
	</tr>
	</table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



