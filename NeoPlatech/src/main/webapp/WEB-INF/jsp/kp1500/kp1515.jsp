<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "불용처리";
String curAction = "/kp1500/kp1515.do";
String curGridAction = "/kp1500/kp1515Ajax.do";
String procAction = "/kp1500/kp1515Proc.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

//공통코드
String com010Str = RequestUtil.getString(request, "com010Str"); //불용처리상태
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 250;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
				'rqstno','assetSeq',
				'자산코드','품목명','상세명칭','처리일자','처리결과','처분금액','처리내용'
				];
var colModel01 = [
   				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
   				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true},
   				{name:'assetNo', index:'assetNo', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'itemName', index:'itemName', width:'120px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'assetName', index:'assetName', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
				{name:'disuseProcDt', index:'disuseProcDt', align:'CENTER', width:'120px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="disuseProcDt_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="datepicker" style="width:80px" />';
						return value;
					}},
				{name:'disuseProcTypeCd', index:'disuseProcTypeCd', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<select id="disuseProcTypeCd_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><option value="">선택</option><%=com010Str%></select>';
						return value;
					}},
				{name:'disuseProcAmt', index:'disuseProcAmt', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="disuseProcAmt_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" style="text-align:right;" />';
						return value;
					}},
				{name:'disuseProcCont', index:'disuseProcCont', align:'CENTER', width:'400px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="disuseProcCont_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}}
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
		autowidth:true,
		shrinkToFit: false,
		cellEdit : false,
//      scrollOffset: 0,
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
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			//텍스트가 셀 너비보다 넓을 때 자동 줄 바꿈 처리
			$('.ui-jqgrid tr.jqgrow td').css("white-space", "normal");

			fnInitCalc();
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

function fnSave() {
	var ids = jQuery("#listInfo01").jqGrid('getDataIDs');
	var saveJsonArray = [];

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					rqstno: obj.rqstno,
					assetSeq: obj.assetSeq,
					disuseProcDt: $('#disuseProcDt'+'_'+ids[i]).val(),
					disuseProcTypeCd: $('#disuseProcTypeCd'+'_'+ids[i]).val(),
					disuseProcAmt: $('#disuseProcAmt'+'_'+ids[i]).val(),
					disuseProcCont: $('#disuseProcCont'+'_'+ids[i]).val()
			};
			saveJsonArray.push(saveJsonObj);
		}
		//alert( JSON.stringify(saveJsonArray) );
		if (confirm("저장 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=procAction%>",
				data : {
					rqstno : $('#rqstno').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("저장 되었습니다.");
						parent.fnGridList();
						parent.fnCloseLayerPopup();
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

	$('#disuseProcDt').bind("change", function(){
		$('input[id^="disuseProcDt_"]').val($(this).val());
	})

	$('#disuseProcTypeCd').bind("change", function(){
		$('select[id^="disuseProcTypeCd_"]').val($(this).val());
	})

	$('#disuseProcAmt').bind("change", function(){
		$('input[id^="disuseProcAmt_"]').val($(this).val());
	})

	$('#disuseProcCont').bind("change", function(){
		$('input[id^="disuseProcCont_"]').val($(this).val());
	})

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
</style>

</head>
<body style="padding:0px 1px 1px 1px;">

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="fnGridReload(1); return false;">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="pageSize" name="pageSize" value="200" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="saveJsonArray" name="saveJsonArray" value="<%=cmRequest.getString("saveJsonArray")%>" />

	<table style="width:100%">
	<tr>
	<td width="50%">
		<h2 class="title_left"><%= pageTitle %></h2>
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="button" value="불용처리" onclick="fnSave();"></span>
		&nbsp;
	</td>
	</tr>
	</table>

	<table class="table-appr" style="width:100%;">
	<colgroup>
		<col width="120px" />
		<col width="250px" />
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>처리일자</th>
		<td>
			<input type="text" id="disuseProcDt" value="" class="datepicker" style="background-color:#FAF4C0;" />
		</td>
		<th>처리결과</th>
		<td>
			<select id="disuseProcTypeCd" class="impt2" style="min-width:100px;"><option value="">선택</option><%=com010Str%></select>
		</td>
	</tr>
	<tr>
		<th>처분금액</th>
		<td>
			<input type="text" id="disuseProcAmt" value="" class="" style="text-align:right;" />
		</td>
		<th>처리내용</th>
		<td>
			<input type="text" id="disuseProcCont" value="" class="def impt2" />
		</td>
	</tr>
	</table>

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>