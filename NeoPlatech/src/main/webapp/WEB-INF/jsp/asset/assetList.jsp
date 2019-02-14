<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String detailAction = "/asset/select.do";
String curAction = "/asset/selectList.do";
String curGridAction = "/asset/selectListAjax.do";
String detailRowAction = "/asset/selectRow.do";
String tagPrintAction = "/asset/print/exec.do";
String tagPrintReqAction = "/asset/print/req.do";
String tagPrintAjaxAction = "/reqst/print/execAjax.do";
String tagPrintCheckAction = "/asset/print/exec.do";
String xlsDnAction = "/asset/selectListXls.do";
String regManageAction = "/asset/writeForm.do";
String delAction = "/asset/deleteProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
//CommonList assetList = RequestUtil.getCommonList(request, "assetList");
int totalRow = RequestUtil.getInt(request, "totalRow");
CommonList addcolMngList = RequestUtil.getCommonList(request, "addcolMngList");
int searchCnt = RequestUtil.getInt(request, "searchCnt");
int searchColCount = 4;
int addHeightHip = 0;
if (searchCnt > 0) {
	int searchRow = ((searchCnt - 1) / searchColCount) + (searchCnt % searchColCount > 0 ? 1 : 0);
	addHeightHip = searchRow * (searchRow == 1 ? 28 : 28);
}
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 320 + <%=addHeightHip%>;

$(window).resize(function(){
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
});

var colNames01 = [
<%
CommonList dispMngList = RequestUtil.getCommonList(request, "dispMngList11");
if (dispMngList != null) {
	for (int k=0; k<dispMngList.size(); k++) {
		CommonMap dispMng = dispMngList.getMap(k);
		out.println((k>0?",":"") + "'" + dispMng.getString("logicalName") + "'");
	}
}
%>
];

var colModel01 = [
<%
if (dispMngList != null) {
	for (int k=0; k<dispMngList.size(); k++) {
		CommonMap dispMng = dispMngList.getMap(k);

		String logicalName = dispMng.getString("logical_name");
		String physicalName = dispMng.getString("physical_name");
		String val = cmRequest.getString(dispMng.getString("physical_name"));
		String align = "cener";
		String fommater = "";
		if ("1".equals(dispMng.getString("data_disp_type"))) {			//기본형
			align = "center";
			fommater = "";
		} else if ("2".equals(dispMng.getString("data_disp_type"))) {	//문자형
			align = "left";
			fommater = "";
		} else if ("3".equals(dispMng.getString("data_disp_type"))) {	//숫자형
			align = "right";
			fommater = ", formatter:'currency', formatoptions:{thousandsSeparator:\",\", decimalPlaces: 0}";
			val = StringUtil.commaNum(val);
		} else if ("4".equals(dispMng.getString("data_disp_type"))) {	//날짜형
			align = "center";
			fommater = "";
			val = DateUtil.formatDateTime(val, "-", ":");
		}

		out.println((k>0?",":"") + String.format("{name:'%s', index:'%s', width:'%spx', align:'%s', columntype:'text', classes:'grid-col-%s'%s}"
				, CamelUtil.convert2CamelCase(physicalName)
				, CamelUtil.convert2CamelCase(physicalName)
				, dispMng.getString("defaultWidth")
				, align
				, dispMng.getString("data_disp_type")
				, fommater
				));
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

			var assetNo = $('#listInfo01').jqGrid('getRowData', rowId).assetNo;

			fnDetail(assetNo);
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
function fnTagPrintChkPost(){
	try {
		var rowIds = $("#listInfo01").jqGrid('getGridParam', "selarrrow" );
		var cnt = rowIds.length;
		var insertRows = [];

		if (cnt == 0) {
			alert("<spring:message code="alert.nochk.print.post.target"/>");
		} else {

			for (var i=0; i<cnt; i++) {
				var data = $("#listInfo01").getRowData(rowIds[i]);
				insertRows.push(data.assetNo);
			}

			$.ajax({
				type : "POST",
				url : "<%=tagPrintAjaxAction%>",
				data : {
					assetNo : insertRows
				},
				dataType : "json",
				success:function(data)
				{
					if (data.result == "OK") {
						alert("<spring:message code="alert.ok.print.post.target"/>");
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
	}catch(e){
		alert(e.description);
	}
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

function fnDetail(assetNo){
	var assetDetail = fnOpenPop2("<%=detailAction%>?assetNo=" + assetNo, "assetDetail");
	assetDetail.focus();
}
$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">고정자산 목록</h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="asset_no" name="asset_no" value="<%=cmRequest.getString("asset_no")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="printType" name="printType" value="" />
	<input type="hidden" id="chkAssetNo" name="chkAssetNo" value="" />

	<div class="SearchBox">
	<table class="search01">
	<tr>
<%
if (addcolMngList != null) {
	int colcnt = 0;
	for (int k=0; k<addcolMngList.size(); k++) {
		CommonMap addcolMng = addcolMngList.getMap(k);

		String logicalName = addcolMng.getString("logical_name");
		String physicalName = addcolMng.getString("physical_name");
		String dataDispType = addcolMng.getString("data_disp_type");
		String val = cmRequest.getString("s_"+addcolMng.getString("physical_name"));
		String vals = cmRequest.getString("s_"+addcolMng.getString("physical_name")+"_s");
		String vale = cmRequest.getString("s_"+addcolMng.getString("physical_name")+"_e");
		String classVal = "";
		if ("1".equals(dataDispType)) {			//기본형
			classVal = "";
		} else if ("2".equals(dataDispType)) {	//문자형
			classVal = "l";
		} else if ("3".equals(dataDispType)) {	//숫자형
			classVal = "r";
			val = StringUtil.commaNum(val);
		} else if ("4".equals(dataDispType)) {	//날짜형
			classVal = "";
			val = DateUtil.formatDateTime(val, "-", ":");
		}

		if ("Y".equals(addcolMng.getString("searchYn"))) {
			if ("3".equals(dataDispType)) {
			%>
			<th><%=logicalName%></th>
			<td>
				<input type="text" id="s_<%=physicalName%>_s" name="s_<%=physicalName%>_s" value="<%=vals%>" style="width:80px;" />
				- <input type="text" id="s_<%=physicalName%>_e" name="s_<%=physicalName%>_e" value="<%=vale%>" style="width:80px;" />
			</td>
			<%
			} else if ("4".equals(dataDispType)) {
			%>
			<th><%=logicalName%></th>
			<td>
				<input type="text" id="s_<%=physicalName%>_s" name="s_<%=physicalName%>_s" value="<%=vals%>" class="datepicker" style="width:80px;" />
				- <input type="text" id="s_<%=physicalName%>_e" name="s_<%=physicalName%>_e" value="<%=vale%>" class="datepicker" style="width:80px;" />
			</td>
			<%
			} else {
			%>
			<th><%=logicalName%></th>
			<td><input type="text" id="s_<%=physicalName%>" name="s_<%=physicalName%>" value="<%=val%>" /></td>
			<%
			}

			colcnt++;

			if ((colcnt) % searchColCount == 0 && colcnt < searchCnt) {
				out.print("</tr><tr>");
			}
		}
	}
	if ((colcnt%searchColCount) > 0) {
		for (int i=0; i<(searchColCount-(colcnt%searchColCount)); i++) {
			out.print("<th></th><td></td>");
		}
	}
}
%>
	</tr>
	</table>
	</div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		<span class="button"><input type="submit" value="<spring:message code="button.search"/>" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.download.excel"/>" onclick="fnXlsDn();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.create.new"/>" onclick="fnRegManage();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.delete"/>" onclick="fnDel();"></span>
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="button" value="<spring:message code="button.print.post"/>" onclick="fnTagPrintChkPost();"></span>
	</td>
	</tr>
	</table>

	<p><spring:message code="count.total"/> : <span id="spanTotalRow"><%=totalRow%></span></p>

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



