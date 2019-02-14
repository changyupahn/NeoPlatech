<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/reqst/print/selectList.do";
String curGridAction = "/reqst/print/selectListAjax.do";
String curProcAction = "/reqst/print/saveProc.do";
String detailAction = "/asset/select.do";
String delAction = "/reqst/print/deleteProc.do";
String delAllAction = "/reqst/print/deleteAllProc.do";
String xlsDnAction = "/reqst/print/selectListXls.do";
String labelAction = "/label/selectList.do";
String labelFormAction = "/label/selectLabelFormAjax.do";
String labelOptListAction = "/label/selectLabelOptListAjax.do";
String addrAction = "/address/selectList.do";
String addrFormAction = "/address/selectAddressFormAjax.do";
String addrOptListAction = "/address/selectAddressOptListAjax.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int totalRow = RequestUtil.getInt(request, "totalRow");
CommonList addcolMngList = RequestUtil.getCommonList(request, "addcolMngList");
int searchCnt = RequestUtil.getInt(request, "searchCnt");
int addHeightHip = 0;
if (searchCnt > 0) {
	int searchRow = ((searchCnt - 1) / 4) + (searchCnt % 4 > 0 ? 1 : 0);
	addHeightHip = searchRow * (searchRow == 1 ? 28 : 28);
}
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 500;
var heightHip = 220 + <%=addHeightHip%>;
var leftHeightHip = 160;

$(window).resize(function(){
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);

	fnResize();
});

function fnResize() {
	$('#divLeft').height($(window).height() - leftHeightHip);
}

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
			align = "center";
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

colNames01.push('reqstSeq');
colModel01.push({name:'reqstSeq', index:'reqstSeq', width:'100px', columntype:'text', hidden:true});

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
			deleteRows.push(data.reqstSeq);
		}

		$.ajax({
			type : "POST",
			url : "<%=delAction%>",
			data : {
				reqstSeq : deleteRows
			},
			dataType : "json",
			success:function(data)
			{
				if (data.result == "OK") {
					/* for (var i=0; i<cnt; i++) {
						$("#listInfo01").delRowData(rowIds[0]);
					} */

					alert("<spring:message code="alert.ok.delete"/>");

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
}
function fnDelAll(){

	if (!confirm("<spring:message code="alert.confirm.delete.all"/>")) {
		return;
	}

	$.ajax({
		type : "POST",
		url : "<%=delAllAction%>",
		data : {
		},
		dataType : "json",
		success:function(data)
		{
			if (data.result == "OK") {

				alert("<spring:message code="alert.ok.delete"/>");

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

function fnLabelPop() {

	var labelDetail = fnOpenPop("", "labelDetail", "800", "400", "");

	var frm = document.sForm;
	frm.action = "<%=labelAction%>";
	frm.target = "labelDetail";
	frm.submit();
	labelDetail.focus();
}

function fnCallbackLabel(labelSeq) {
	$.ajax({
		type : "POST",
		url : "<%=labelOptListAction%>",
		data : {
			labelSeq : labelSeq
		},
		dataType : "html",
		success:function(data)
		{
			$('#labelSeq').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
			alert("<spring:message code="alert.error.fail"/>");
		},
		complete:function()
		{
			fnSetLabelForm($('#labelSeq').val());
		}
	});
}

function fnSetLabelForm(labelSeq) {

	if (labelSeq) {
		//labelSeq = labelSeq;
	} else {
		labelSeq = $('#labelSeq').val();
	}

	$.ajax({
		type : "POST",
		url : "<%=labelFormAction%>",
		data : {
			labelSeq : labelSeq
		},
		dataType : "html",
		success:function(data)
		{
			$('#divTagLabel').html(data);
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

function fnAddrPop() {

	var addrDetail = fnOpenPop("", "addrDetail", "500", "300", "");

	var frm = document.sForm;
	frm.action = "<%=addrAction%>";
	frm.target = "addrDetail";
	frm.submit();
	addrDetail.focus();
}

function fnCallbackAddr(addrSeq) {
	$.ajax({
		type : "POST",
		url : "<%=addrOptListAction%>",
		data : {
			addrSeq : addrSeq,
			addrTypeCd : '2'
		},
		dataType : "html",
		success:function(data)
		{
			$('#addrSeq').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
			alert("<spring:message code="alert.error.fail"/>");
		},
		complete:function()
		{
			fnSetAddrForm($('#addrSeq').val());
		}
	});
}

function fnSetAddrForm(addrSeq) {

	if (addrSeq) {
		//addrSeq = addrSeq;
	} else {
		addrSeq = $('#addrSeq').val();
	}

	$.ajax({
		type : "POST",
		url : "<%=addrFormAction%>",
		data : {
			addrSeq : addrSeq
		},
		dataType : "html",
		success:function(data)
		{
			$('#divTagAddr').html(data);
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

function fnSave() {

	if ($('#labelSeq').val() == "") {
		alert("<spring:message code="reqstprint.required.label"/>");
		return;
	}

	if ($('#addrSeq').val() == "") {
		alert("<spring:message code="reqstprint.required.addr"/>");
		return;
	}

	if ($('#authChk:checked').length == 0) {
		alert("<spring:message code="reqstprint.required.authchk"/>");
		return;
	}

	$.ajax({
		type : "POST",
		url : "<%=curProcAction%>",
		data : $('#sForm').serializeObject(),
		dataType : "json",
		success:function(data)
		{
			if (data.result == "OK") {

				alert("<spring:message code="alert.ok.reqstprint"/>");

				fnGridReload("1");
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

$(document).ready(function(){
	fnResize();
	fnGridList();
	fnCallbackLabel();
	fnCallbackAddr();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><spring:message code="menu.print.list"/></h2>

	<%-- <div style="border-bottom:1px solid #dadada;padding:10px;">
		<p style="font-size:12px;line-height:170%">
			1. 라벨양식 설정
			<br />2. 배송지 설정
			<br />3. 출력요청목록 확인 후 요청내역 등록
		</p>
	</div>  --%>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />

	<table style="border:0; border-collapse:collapse; background-color:#e4eaf8">
	<colgroup>
		<col width="450px" />
		<col width="" />
	</colgroup>
	<tr>
		<td align="left" valign="top" style="padding-left:24px;">
			<div id="divLeft" style="overflow: auto;">

			<table style="width:95%; border:0; border-collapse:collapse;">
			<tr>
				<td style="padding-top:25px;">
					<h4 style="font-size:14px;color:#000000;">1. <spring:message code="reqstprint.required.label"/></h4>
				</td>
			</tr>
			<tr>
				<td>
					<select id="labelSeq" name="labelSeq" style="width:200px;" onchange="fnSetLabelForm()">
					</select>
					<span class="button"><input type="button" value="<spring:message code="button.select"/>" onclick="fnSetLabelForm();"></span>
					<span class="button"><input type="button" value="<spring:message code="button.create"/>" onclick="fnLabelPop();"></span>
				</td>
			</tr>
			<tr>
				<td style="padding-top:10px;">
					<div id="divTagLabel" class="tag-label rounded-corners"></div>
				</td>
			</tr>
			</table>

			<table style="width:95%; border:0; border-collapse:collapse;">
			<tr>
				<td style="padding-top:25px;">
					<h4 style="font-size:14px;color:#000000;">2. <spring:message code="reqstprint.required.addr"/></h4>
				</td>
			</tr>
			<tr>
				<td>
					<select id="addrSeq" name="addrSeq" style="width:200px" onchange="fnSetAddrForm()">
					</select>
					<span class="button"><input type="button" value="<spring:message code="button.select"/>" onclick="fnSetAddrForm();"></span>
					<span class="button"><input type="button" value="<spring:message code="button.create"/>" onclick="fnAddrPop();"></span>
				</td>
			</tr>
			<tr>
				<td style="padding-top:10px;">
					<div id="divTagAddr"></div>
				</td>
			</tr>
			</table>

			<table style="border:0; border-collapse:collapse;">
			<tr>
				<td style="padding-top:25px;">
					<h4 style="font-size:14px;color:#000000;">3. <spring:message code="reqstprint.required.assets"/></h4>
				</td>
			</tr>
			</table>

			<table style="border:0; border-collapse:collapse;">
			<tr>
				<td style="padding-top:25px;">
					<h4 style="font-size:14px;color:#000000;">4. <spring:message code="title.reqstprint"/></h4>
				</td>
			</tr>
			<tr>
				<td style="padding-top:10px; ">
					<input type="checkbox" id="authChk" name="authChk" value="Y" style="width:16px; height:16px; background-color:#ffffff; position:relative; top:2px;" />
					<spring:message code="reqstprint.summary.a"/>
				</td>
			</tr>
			</table>

			<div style="text-align:center; padding-top:20px; padding-right:50px;">
				<a href="javascript:fnSave();" class="button_blue2" style="color:white; font-size:15px;"><spring:message code="button.reqstprint"/></a>
			</div>

			</div>
		</td>
		<td valign="top" style="background-color:#ffffff; padding-left:10px; padding-top:25px;">

			<h4 style="font-size:14px;color:#000000;"><spring:message code="title.reqstprint.list"/></h4>

			<table style="width:100%">
			<tr>
			<td width="50%">
				<p><spring:message code="count.total"/> : <span id="spanTotalRow"><%=totalRow%></span></p>
			</td>
			<td width="50%" style="text-align: right; padding-bottom:2px;">
				<span class="button"><input type="button" value="<spring:message code="button.download.excel"/>" onclick="fnXlsDn();"></span>
				<span class="button"><input type="button" value="<spring:message code="button.delete.chk"/>" onclick="fnDel();"></span>
				<span class="button"><input type="button" value="<spring:message code="button.delete.all"/>" onclick="fnDelAll();"></span>
			</td>
			</tr>
			</table>

			<table id="listInfo01" class="scroll"></table>

			<table style="width:100%; border-collapse:collapse; border:0;">
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
		</td>
	</tr>
	</table>

	</form>


	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



