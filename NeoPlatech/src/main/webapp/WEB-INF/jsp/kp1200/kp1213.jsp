<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "검수";
String curAction = "/kp1200/kp1213.do";
String curGridAction = "/kp1200/kp1213VirtAjax.do";
String procAction = "/kp1200/kp1213Proc.do";
String delProcAction = "/kp1200/kp1213DelProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

//공통코드
String com001Str = RequestUtil.getString(request, "com001Str"); //상각법
String com002Str = RequestUtil.getString(request, "com002Str"); //사용여부
String com005Str = RequestUtil.getString(request, "com005Str"); //활용범위
String com006Str = RequestUtil.getString(request, "com006Str"); //취득방법
%>
<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">
var widthHip = 5;
var heightHip = 350;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
   				'assetSeq','purno','rqstno','prodno','태그발행여부','자산확정여부','자산코드','품목명','상세명칭','수량','단가','금액','계정번호','사용자번호','사용자','검수일자','검수자','비고'
   				];
var colModel01 = [
				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
				{name:'purno', index:'purno', width:'0px', hidden:true},
				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
				{name:'prodno', index:'prodno', width:'0px', hidden:true},
				{name:'printYn', index:'printYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'assetRegiYn', index:'assetRegiYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetNo', index:'assetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'itemName', index:'itemName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'assetCnt', index:'assetCnt', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
   				{name:'accntNum', index:'accntNum', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'userNo', index:'userNo', width:'0px', hidden:true, sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="hidden" id="userNo_'+options.rowId+'" value="'+ value +'" />';
						return value;
					}},
				{name:'userName', index:'userName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'inspDt', index:'inspDt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', formatter:fnFormatterDate},
				{name:'inspUserName', index:'inspUserName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'inspRemark', index:'inspRemark', width:'250px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
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
		shrinkToFit: false,
		//cellEdit : true,
		sortable : true,
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
		onSelectRow: function(rowid) {
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
		}
	});
}

function fnSave() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("검수 처리할 행을 선택해주세요.");
		return;
	}

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					purno: obj.purno,
					rqstno: obj.rqstno,
					prodno: obj.prodno,
					assetSeq: obj.assetSeq
			};
			saveJsonArray.push(saveJsonObj);
		}

		if (confirm("검수(자산확정) 처리 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=procAction%>",
				data : {
					purno : $('#purno').val(),
					inspDt : $('#inspDt').val(),
					aqusitDt : $('#aqusitDt').val(),
					inspUserName : $('#inspUserName').val(),
					inspRemark : $('#inspRemark').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("처리 되었습니다.");
						fnGridList();
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

//검수취소
function fnDelRow() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("검수취소할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
		var saveJsonObj = {
				purno: obj.purno,
				rqstno: obj.rqstno,
				prodno: obj.prodno,
				assetSeq: obj.assetSeq
		};
		saveJsonArray.push(saveJsonObj);
	}

	if (confirm("검수취소 처리 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=delProcAction%>",
			data : {
				purno : $('#purno').val(),
				inspDt : $('#inspDt').val(),
				inspUserName : $('#inspUserName').val(),
				inspRemark : $('#inspRemark').val(),
				saveJsonArray : JSON.stringify(saveJsonArray)
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("취소 처리 되었습니다.");
					fnGridList();
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

$(document).ready(function(){

	fnGridList();
});
</script>

<style type="text/css">
/* selrow highlight disabled */
.ui-state-highlight{
    background:none!important;
}
/* mouseover highlight disabled */
.ui-state-hover{
    background:none!important;
}
/* vertical-align : middle */
.ui-jqgrid input,select {
	border:1px solid gray;
	position:relative;
	top:1px;
}
.ui-jqgrid input[type="checkbox"] {
	position:relative;
	top:3px;
}
</style>

</head>
<body style="overflow:auto;">

	<h2 class="title_left">계약정보</h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="purno" name="purno" value="<%=cmRequest.getString("purno")%>" />

	<table class="table-search" style="width:100%;">
	<colgroup>
		<col width="120px" />
		<col width="250px" />
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
		<th class="r">계약번호 :</th>
		<td><%=viewData.getString("contrno")%></td>
		<th class="r">구매신청자 :</th>
		<td><%=viewData.getString("userhisname")%></td>
	</tr>
	<tr>
		<th class="r">계약명 :</th>
		<td><%=viewData.getString("purnm")%></td>
		<th class="r">계약일자 :</th>
		<td><%=DateUtil.formatDateTime(viewData.getString("contrdt"), "-", ":", 8)%></td>
	</tr>
	<tr>
		<th class="r">거래처 :</th>
		<td><%=viewData.getString("custhisname")%></td>
		<th class="r">계약금액 :</th>
		<td><%=StringUtil.commaNum(viewData.getString("contramt"))%></td>
	</tr>
	</table>

	<div style="padding-top:30px;"></div>

	<table style="width:100%;border-collapse:collapse;padding:0px;magin:0px;">
	<tr>
	<td width="50%">
		<h2 class="title_left">검수</h2>
	</td>
	<td width="50%" style="text-align: right;">
		&nbsp;
	</td>
	</tr>
	</table>

	<table class="table-search" style="width:100%;margin:0px;">
	<colgroup>
		<col width="120px" />
		<col width="250px" />
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
		<th class="r">검수일자 :</th>
		<td>
			<input type="text" id="inspDt" name="inspDt" value="<%=DateUtil.getFormatDate("yyyy-MM-dd")%>" class="datepicker" />
		</td>
		<th class="r">검수자 :</th>
		<td>
			<input type="text" id="inspUserName" name="inspUserName" value="<%=SessionUtil.getString("userName")%>" />
		</td>
	</tr>
	<tr>
		<th class="r">취득일자 :</th>
		<td>
			<input type="text" id="aqusitDt" name="aqusitDt" value="<%=DateUtil.getFormatDate("yyyy-MM-dd")%>" class="datepicker" />
		</td>
		<th class="r">비고 :</th>
		<td>
			<input type="text" id="inspRemark" name="inspRemark" value="" class="def" />
		</td>
	</tr>
	</table>

	<div style="padding-top:30px;"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		<h2 class="title_left">가자산 목록</h2>
	</td>
	<td width="50%" style="text-align: right;">
		<% if(ssAuthManager){ %>
		<span class="button"><input type="button" value="검수(자산확정)" onclick="fnSave();"></span>
		<span class="button"><input type="button" value="검수취소" onclick="fnDelRow();;"></span>
		<% } %>
		&nbsp;
	</td>
	</tr>
	</table>

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>



