<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/inventory/selectDetailList.do";
String curGridAction = "/inventory/selectDetailListAjax.do";
String curView = "/inventory/selectDetail.do";
String curProc = "/inventory/targetSetting.do";
String tagPrintAction = "/inventory/print/exec.do";
String tagPrintAjaxAction = "/reqst/print/execAjax.do";
String xlsDnAction = "/inventory/selectDetailListXls.do";
String posStatAction = "/inventoryStat/deptStatList.do";
String userStatAction = "/inventoryStat/userStatList.do";
String hosilStatAction = "/inventoryStat/hosilStatList.do";
String assetCateStatAction = "/inventoryStat/assetCateStatList.do";
String newRegisterStatAction = "/inventoryStat/newRegisterStatList.do";
String deptStatXlsDnAction = "/inventoryStat/deptStatListXls.do";
String userStatXlsDnAction = "/inventoryStat/userStatListXls.do";
String hosilStatXlsDnAction = "/inventoryStat/hosilStatListXls.do";
String assetCateStatXlsDnAction = "/inventoryStat/assetCateStatListXls.do";
String newRegisterStatXlsDnAction = "/inventoryStat/newRegisterStatListXls.do";
String inventoryDetailView = "/inventory/selectDetail.do";
String detailAction = "/asset/select.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap inventoryMaster = RequestUtil.getCommonMap(request, "inventoryMaster");
CommonList inventoryDetailList = RequestUtil.getCommonList(request, "inventoryDetailList");

int totalRow = RequestUtil.getInt(request, "totalRow");
CommonList dispMngList99 = RequestUtil.getCommonList(request, "dispMngList99");
int searchCnt = RequestUtil.getInt(request, "searchCnt");
int addHeightHip = 0;
if (searchCnt > 0) {
	int searchRow = ((searchCnt - 1) / 4) + (searchCnt % 4 > 0 ? 1 : 0);
	addHeightHip = searchRow * (searchRow == 1 ? 28 : 28);
}

String title = inventoryMaster.getString("inv_year") + "," + inventoryMaster.getString("inv_no") + "," + inventoryMaster.getString("inv_type_name");
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
CommonList dispMngList = RequestUtil.getCommonList(request, "dispMngList21");
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
		//String val = cmRequest.getString(dispMng.getString("physical_name"));
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
			//, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}
			//fommater = ", formatter:fnFormatterMoney";
			//val = StringUtil.commaNum(val);
		} else if ("4".equals(dispMng.getString("data_disp_type"))) {	//날짜형
			align = "center";
			fommater = ", formatter:fnFormatterDate";
			//val = DateUtil.formatDateTime(val, "-", ":");
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
			//TODO
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
			//TODO
		},
		onSortCol : function(index, columnIndex, sortOrder) {
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
			//TODO
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
function fnUpdate(targetYn){

	var rowIds = $("#listInfo01").jqGrid('getGridParam', "selarrrow" );
	var cnt = rowIds.length;
	var updatRows = [];
	var totalRow = $('#spanTotalRow').html();

	var confirmMsg = "";
	if (cnt == 0) {
		if (targetYn == "Y") {
			confirmMsg = "<spring:message code="alert.confirm.inventory.target.all"/>";
		} else {
			confirmMsg = "<spring:message code="alert.confirm.inventory.no.target.all"/>";
		}
	} else {
		if (targetYn == "Y") {
			confirmMsg = "<spring:message code="alert.confirm.inventory.target.chk"/>";
		} else {
			confirmMsg = "<spring:message code="alert.confirm.inventory.no.target.chk"/>";
		}
	}

	if (cnt == 0) {
		if (confirm(confirmMsg) == false) {
			return;
		}
	} else {
		if (confirm(confirmMsg) == false) {
			return;
		}

		var chkList = "";
		for (var i=0; i<cnt; i++) {
			chkList += "," + $("#listInfo01").getRowData(rowIds[i]).assetNo;
		}
		chkList = chkList.replace(/^[,]/, "");
	}

	$('#uTargetYn').val(targetYn);

	var postData = $('#sForm').serializeObject();
	postData['chkList'] = chkList;

	$.ajax({
		type : "POST",
		url : "<%=curProc%>",
		data : postData,
		dataType : "json",
		success:function(data)
		{
			if (data.result == "OK") {
				alert("<spring:message code="alert.ok.success"/>");
				fnGridReload();
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
function fnDetail(assetNo){

	var assetDetail = fnOpenPop2("", "assetDetail");

	var frm = document.sForm;
	frm.asset_no.value = assetNo;
	frm.action = "<%=detailAction%>";
	frm.target = "assetDetail";
	frm.submit();
	assetDetail.focus();
}
$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><spring:message code="column.inventory.main.title" arguments="<%=title%>" /></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="sInvYear" name="sInvYear" value="<%=cmRequest.getString("sInvYear")%>" />
	<input type="hidden" id="sInvNo" name="sInvNo" value="<%=cmRequest.getString("sInvNo")%>" />
	<input type="hidden" id="asset_no" name="asset_no" value="" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="printType" name="printType" value="" />
	<input type="hidden" id="uTargetYn" name="uTargetYn" value="" />

	<table id="tableSearchLayer" class="table-search" style="width:100%;">
	<tr>
<%
if (dispMngList99 != null) {
	int colcnt = 0;
	for (int k=0; k<dispMngList99.size(); k++) {
		CommonMap dispMng = dispMngList99.getMap(k);

		String logicalName = dispMng.getString("logical_name");
		String physicalName = dispMng.getString("physical_name");
		String dataDispType = dispMng.getString("data_disp_type");
		String val = cmRequest.getString("s_"+dispMng.getString("physical_name"));
		String vals = cmRequest.getString("s_"+dispMng.getString("physical_name")+"_s");
		String vale = cmRequest.getString("s_"+dispMng.getString("physical_name")+"_e");
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

		if ("Y".equals(dispMng.getString("searchYn"))) {
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

			if ((colcnt) % 4 == 0 && colcnt < searchCnt) {
				out.print("</tr><tr>");
			}
		}
	}
	if ((colcnt%4) > 0) {
		for (int i=0; i<(4-(colcnt%4)); i++) {
			out.print("<th></th><td></td>");
		}
	}
}
%>
	</tr>
	</table>

	<table width="100%">
	<tr>
	<td width="70%">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
	</td>
	<td width="30%" style="text-align: right;">
		<span class="button"><input type="button" value="태그발행요청(우편)" onclick="fnTagPrintChkPost();"></span>
	</td>
	</tr>
	</table>

	<p><spring:message code="count.total"/> : <span id="spanTotalRow"><%=totalRow%></span></p>

	<table id="listInfo01" class="scroll"></table>

	<table style="width:100%;border-collapse:collapse;border:0;">
	<tr>
	<td width="450px" class="l" style="padding-top:5px;">
		<span class="button"><input type="button" value="재물조사대상(설정)" onclick="fnUpdate('Y')"></span>
		<span class="button"><input type="button" value="재물조사대상(해제)" onclick="fnUpdate('N')"></span>
	</td>
	<td width="" style="padding-top:5px;">
		<div id="paginate" class="paginate"></div>
	</td>
	<td width="250px" class="r" style="padding-top:5px;">
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



