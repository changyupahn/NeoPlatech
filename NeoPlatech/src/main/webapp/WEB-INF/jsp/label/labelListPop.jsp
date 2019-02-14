<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/label/selectList.do";
String curLabelGridAction = "/label/selectLabelListAjax.do";
String curSyscolGridAction = "/label/selectSyscolListAjax.do";
String delAction = "/label/deleteProc.do";
String curAjaxAction = "/label/insertLabelAjax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
int addHeightHip = 0;
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 0;
var heightHip = 0 + <%=addHeightHip%>;
var leftHeightHip = 0;

/* $(window).resize(function(){
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);

	fnResize();
});

function fnResize() {
	$('#divLeft').height($(window).height() - leftHeightHip);
} */

var colNames01 = ['aamSeq', '<spring:message code="column.name"/>'];
var colModel01 = [{name:'aamSeq', index:'labelSeq', width:'100px', align:'center', columntype:'text', hidden:true}
                 ,{name:'logicalName', index:'logicalName', width:'150px', align:'left', columntype:'text'}
                  ];
var groupHeaders01 = [];

function fnGridList() {

	$("#listInfo01").jqGrid("GridUnload");

	$("#listInfo01").jqGrid({
		datatype : 'json',
		url : "<%=curSyscolGridAction%>",
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
		shrinkToFit: true,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: 200,
		height: 200,
		multiselect: false,
		resizeStop: function() {
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload2("1");
		},
		ondblClickRow:function(rowId){
			fnAdd();
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
function fnInit() {
	$('input[name="objTitle"]').val("");
	$('input[name="objAamSeq"]').val("");
	$('input[name="objValue"]').val("");

	$('input[name="objTitle"]').removeClass("color_black");
	$('input[name="objValue"]').removeClass("color_black");

	objCnt = 1;
}
var objCnt = 1;
function fnAdd() {

	if (objCnt > 4) {
		alert("<spring:message code="alert.maxcnt.label.column"/>");
		return;
	}

	var rowId = $('#listInfo01').jqGrid('getGridParam', "selrow" );
	var data = $("#listInfo01").getRowData(rowId);

	if ($('input[name="objAamSeq"][value="'+data.aamSeq+'"]').length > 0) {
		alert("<spring:message code="alert.dupchk.column"/>");
		return;
	}

	//alert(data.aamSeq);
	$('#objTitle'+objCnt).val(data.logicalName);
	$('#objAamSeq'+objCnt).val(data.aamSeq);

	$('#objTitle'+objCnt).addClass("color_black");
	$('#objValue'+objCnt).addClass("color_black");

	objCnt++;

}
function fnSave() {
	var frm = document.sForm;

	if (frm.labelTitle.value == "") {
		alert("<spring:message code="label.required.title"/>");
		return;
	}

	if (objCnt < 2) {
		alert("<spring:message code="label.required.tag"/>");
		return;
	}

	if (confirm("<spring:message code="alert.confirm.save"/>")) {
		$.ajax({
			type : "POST",
			url : "<%=curAjaxAction%>",
			data : $('#sForm').serializeObject(),
			dataType : "json",
			success:function(data)
			{
				if (data.result == "OK") {

					opener.fnCallbackLabel(data.labelSeq);
					alert("<spring:message code="alert.ok.save"/>");
					self.close();
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
$(document).ready(function(){
	//fnResize();
	fnGridList();
});
</script>

</head>
<body>

	<h2 class="title_left"><spring:message code="menu.label"/></h2>

	<div style="border-bottom:1px solid #dadada;padding:10px;">
		<p style="font-size:15px;line-height:170%; font-weight:bold;">
			<spring:message code="label.summary.a" arguments=" " />
			<img src="/images/icon/icon_stairs_right.gif" alt="<spring:message code="title.input.column"/>" />
			<spring:message code="label.summary.b"/>
		</p>
	</div>

	<div style="padding:10px;">

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />

	<table style="height:250px; border:0; border-collapse:collapse;">
	<colgroup>
		<col width="215px" />
		<col width="50px" />
		<col width="" />
	</colgroup>
	<tr>
		<td valign="top" align="center" style="background-color:#ffffff; padding-left:10px; ">

			<h4 style="font-size:14px;color:#000000;">※<spring:message code="label.manage.column"/></h4>

			<table id="listInfo01" class="scroll"></table>
		</td>
		<td align="center">
			<a href="javascript:fnAdd();" title="<spring:message code="title.input.column"/>"><img src="/images/icon/icon_stairs_right.gif" alt="<spring:message code="title.input.column"/>" /></a>
		</td>
		<td valign="top" align="center" style="padding-top:20px;">

			<h4 style="font-size:14px;color:#000000;">※<spring:message code="label.required.title"/></h4>

			<div style="padding-bottom:20px;">
				<input type="text" id="labelTitle" name="labelTitle" value="" class="t_strong" style="width:100%; height:22px; color:black" />
			</div>

			<h4 style="font-size:14px;color:#000000;">※<spring:message code="label.required.tag"/></h4>

			<div id="divTagLabel" class="tag-label rounded-corners" style="width:400px">
			<table class="table-cell" style="width:100%;">
			<colgroup>
				<col width="120px" />
				<col width="" />
			</colgroup>
			<tr>
				<td>
					<input type="text" id="objTitle1" name="objTitle" value="" class="t_strong" style="width:110px; height:22px; border:0; padding-left:10px; color:#000000; background-color:#ffffff" />
					<input type="hidden" id="objLcationNo1" name="objLcationNo" value="1" />
					<input type="hidden" id="objType1" name="objType" value="TEXT" />
					<input type="hidden" id="objAamSeq1" name="objAamSeq" value="" />
				</td>
				<td>
					<input type="text" id="objValue1" name="objValue" value="" class="t_strong" style="width:95%; height:22px; border:0;" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="objTitle2" name="objTitle" value="" class="t_strong" style="width:110px; height:22px; border:0; padding-left:10px; color:#000000; background-color:#ffffff" />
					<input type="hidden" id="objLcationNo2" name="objLcationNo" value="2" />
					<input type="hidden" id="objType2" name="objType" value="TEXT" />
					<input type="hidden" id="objAamSeq2" name="objAamSeq" value="" />
				</td>
				<td>
					<input type="text" id="objValue2" name="objValue" value="" class="t_strong" style="width:95%; height:22px; border:0;" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="objTitle3" name="objTitle" value="" class="t_strong" style="width:110px; height:22px; border:0; padding-left:10px; color:#000000; background-color:#ffffff" />
					<input type="hidden" id="objLcationNo3" name="objLcationNo" value="3" />
					<input type="hidden" id="objType3" name="objType" value="TEXT" />
					<input type="hidden" id="objAamSeq3" name="objAamSeq" value="" />
				</td>
				<td>
					<input type="text" id="objValue3" name="objValue" value="" class="t_strong" style="width:95%; height:22px; border:0;" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="objTitle4" name="objTitle" value="" class="t_strong" style="width:110px; height:22px; border:0; padding-left:10px; color:#000000; background-color:#ffffff" />
					<input type="hidden" id="objLcationNo4" name="objLcationNo" value="4" />
					<input type="hidden" id="objType4" name="objType" value="TEXT" />
					<input type="hidden" id="objAamSeq4" name="objAamSeq" value="" />
				</td>
				<td>
					<input type="text" id="objValue4" name="objValue" value="" class="t_strong" style="width:95%; height:22px; border:0;" readonly="readonly" />
				</td>
			</tr>
			</table>
			</div>

				<%-- <table id="listInfo01" class="scroll"></table> --%>
		</td>
	</tr>
	</table>

	<div class="buttonDiv">
		<span class="button"><input type="button" value="<spring:message code="button.init"/>" onclick="fnInit();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.save"/>" onclick="fnSave();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.close"/>" onclick="javascript:self.close();"></span>
	</div>

	</form>

	</div>

</body>
</html>



