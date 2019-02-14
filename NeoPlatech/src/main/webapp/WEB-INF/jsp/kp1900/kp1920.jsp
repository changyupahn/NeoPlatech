<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "권한관리";
String curAction = "/kp1900/kp1920.do";
String curGridAction = "/kp1900/kp1920GrantAjax.do";
String curGrid2Action = "/kp1900/kp1920MenuAjax.do";
String procAction = "/kp1900/kp1920Proc.do";

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
	$("#listInfo02").setGridWidth(parseInt($(window).width() * 0.7) - widthHip);
	$("#listInfo02").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
				'grantNo','권한명'
				];
var colModel01 = [
   				{name:'grantNo', index:'grantNo', width:'0px', hidden:true},
   				{name:'grantName', index:'grantName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				];
var groupHeaders01 = [];

var colNames02 = [
				'grantNo','menuNo','메뉴명','읽기권한','쓰기권한','부서장읽기권한','부서장쓰기권한','관리자권한'
				];
var colModel02 = [
   				{name:'grantNo', index:'grantNo', width:'0px', hidden:true},
   				{name:'menuNo', index:'menuNo', width:'0px', hidden:true},
   				{name:'menuName', index:'menuName', align:'LEFT', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";

						if (rData.menuNo.substring(4,5) == "0") {
							//value = value;
						} else {
							value = "&nbsp;&nbsp;└" + value;
						}

						return value;
					}},
				{name:'grantReadYn', index:'grantReadYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						var checkedStr = value=="Y"?"checked":"";
						value = '<input type="checkbox" id="grantReadYn_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" '+ checkedStr +' class="chk" />읽기';
						return value;
					}},
				{name:'grantWriteYn', index:'grantWriteYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						var checkedStr = value=="Y"?"checked":"";
						value = '<input type="checkbox" id="grantWriteYn_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" '+ checkedStr +' class="chk" />쓰기';
						return value;
					}},
				{name:'grantHreadYn', index:'grantHreadYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						var checkedStr = value=="Y"?"checked":"";
						value = '<input type="checkbox" id="grantHreadYn_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" '+ checkedStr +' class="chk" />읽기';
						return value;
					}},
				{name:'grantHwriteYn', index:'grantHwriteYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						var checkedStr = value=="Y"?"checked":"";
						value = '<input type="checkbox" id="grantHwriteYn_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" '+ checkedStr +' class="chk" />쓰기';
						return value;
					}},
				{name:'grantManagerYn', index:'grantManagerYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						var checkedStr = value=="Y"?"checked":"";
						value = '<input type="checkbox" id="grantManagerYn_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" '+ checkedStr +' class="chk" />관리자';
						return value;
					}}
   				];
var groupHeaders02 = [];

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
		onSelectAll: function(rowids, status) {
			for (var i=0; i<rowids.length; i++) {
				var rowid = rowids[i];
				if ($('#jqg_listInfo02_' + rowid + ':checked').length == 0) {
					fnRowSelected(rowid, false);
				} else {
					fnRowSelected(rowid, true);
				}
			}
		},
		onSelectRow: function(rowid) {
			if ($('#jqg_listInfo02_' + rowid + ':checked').length == 0) {
				fnRowSelected(rowid, false);
			} else {
				fnRowSelected(rowid, true);
			}
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			fnGridResize();
			
			$('.hcolpos').remove();
			
			$('#jqgh_listInfo02_grantReadYn').append('<img src="/images/btn/btn_check.gif" alt="읽기권한 전체 선택" class="hcolpos" onclick="fnColSelected(11)" />');
			$('#jqgh_listInfo02_grantReadYn').append('<img src="/images/btn/btn_nocheck.gif" alt="읽기권한 전체 선택 해제" class="hcolpos" onclick="fnColSelected(10)" />');
			
			$('#jqgh_listInfo02_grantWriteYn').append('<img src="/images/btn/btn_check.gif" alt="읽기권한 전체 선택" class="hcolpos" onclick="fnColSelected(21)" />');
			$('#jqgh_listInfo02_grantWriteYn').append('<img src="/images/btn/btn_nocheck.gif" alt="읽기권한 전체 선택 해제" class="hcolpos" onclick="fnColSelected(20)" />');
			
			$('#jqgh_listInfo02_grantHreadYn').append('<img src="/images/btn/btn_check.gif" alt="읽기권한 전체 선택" class="hcolpos" onclick="fnColSelected(31)" />');
			$('#jqgh_listInfo02_grantHreadYn').append('<img src="/images/btn/btn_nocheck.gif" alt="읽기권한 전체 선택 해제" class="hcolpos" onclick="fnColSelected(30)" />');
			
			$('#jqgh_listInfo02_grantHwriteYn').append('<img src="/images/btn/btn_check.gif" alt="읽기권한 전체 선택" class="hcolpos" onclick="fnColSelected(41)" />');
			$('#jqgh_listInfo02_grantHwriteYn').append('<img src="/images/btn/btn_nocheck.gif" alt="읽기권한 전체 선택 해제" class="hcolpos" onclick="fnColSelected(40)" />');
			
			$('#jqgh_listInfo02_grantManagerYn').append('<img src="/images/btn/btn_check.gif" alt="읽기권한 전체 선택" class="hcolpos" onclick="fnColSelected(51)" />');
			$('#jqgh_listInfo02_grantManagerYn').append('<img src="/images/btn/btn_nocheck.gif" alt="읽기권한 전체 선택 해제" class="hcolpos" onclick="fnColSelected(50)" />');
		}
	});
}

function fnColSelected(num) {
	if (num == 10) {
		$('input[id^="grantReadYn_"]').attr("checked", false);
	} else if (num == 11) {
		$('input[id^="grantReadYn_"]').attr("checked", true);
	} else if (num == 20) {
		$('input[id^="grantWriteYn_"]').attr("checked", false);
	} else if (num == 21) {
		$('input[id^="grantWriteYn_"]').attr("checked", true);
	} else if (num == 30) {
		$('input[id^="grantHreadYn_"]').attr("checked", false);
	} else if (num == 31) {
		$('input[id^="grantHreadYn_"]').attr("checked", true);
	} else if (num == 40) {
		$('input[id^="grantHwriteYn_"]').attr("checked", false);
	} else if (num == 41) {
		$('input[id^="grantHwriteYn_"]').attr("checked", true);
	} else if (num == 50) {
		$('input[id^="grantManagerYn_"]').attr("checked", false);
	} else if (num == 51) {
		$('input[id^="grantManagerYn_"]').attr("checked", true);
	}
}

function fnRowSelected(rowid, b) {
	if (b) {
		$('#grantReadYn_' + rowid).attr("checked", true);
		$('#grantWriteYn_' + rowid).attr("checked", true);
		$('#grantHreadYn_' + rowid).attr("checked", true);
		$('#grantHwriteYn_' + rowid).attr("checked", true);
		$('#grantManagerYn_' + rowid).attr("checked", true);
	} else {
		$('#grantReadYn_' + rowid).attr("checked", false);
		$('#grantWriteYn_' + rowid).attr("checked", false);
		$('#grantHreadYn_' + rowid).attr("checked", false);
		$('#grantHwriteYn_' + rowid).attr("checked", false);
		$('#grantManagerYn_' + rowid).attr("checked", false);
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

function fnSearch2(){
	fnGridReload("1");
}

function fnDetail(rowid){
	var grantNo = $("#listInfo01").jqGrid("getRowData", rowid).grantNo;
	$('#grantNo').val(grantNo);
	fnGridList2();
}

function fnSave() {
	var ids = $("#listInfo02").jqGrid('getDataIDs');
	var saveJsonArray = [];

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo02").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					menuNo: obj.menuNo,
					grantReadYn: $('#grantReadYn'+'_'+ids[i]).attr("checked")?"Y":"N",
					grantWriteYn: $('#grantWriteYn'+'_'+ids[i]).attr("checked")?"Y":"N",
					grantHreadYn: $('#grantHreadYn'+'_'+ids[i]).attr("checked")?"Y":"N",
					grantHwriteYn: $('#grantHwriteYn'+'_'+ids[i]).attr("checked")?"Y":"N",
					grantManagerYn: $('#grantManagerYn'+'_'+ids[i]).attr("checked")?"Y":"N"
			};
			saveJsonArray.push(saveJsonObj);
		}

		if (confirm("저장 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=procAction%>",
				data : {
					grantNo : $('#grantNo').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("저장 되었습니다.");
						fnGridList2();
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
	<input type="hidden" id="grantNo" name="grantNo" value="" />

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
		<%-- <table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="100px" />
			<col width="" />
		</colgroup>
		<tr>
		<th class="r">권한명 :</th>
		<td>
			<input type="text" id="sGrantName" name="sGrantName" value="<%=cmRequest.getString("sGrantName")%>" />
			<span class="button"><input type="button" value="등록" onclick="fnGrantWrite()" ></span>
		</td>
		</tr>
		</table> --%>

		<table id="listInfo01" class="scroll"></table>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="69%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td class="r2">
			<% if (ssAuthManager) { %>
			<span class="button"><input type="button" value="권한설정저장" onclick="fnSave()" ></span>
			<% } %>
		</td>
		</tr>
		</table>
		<%-- <table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="100px" />
			<col width="" />
		</colgroup>
		<tr>
		<th class="r">권한명 :</th>
		<td>
			<input type="text" id="grantName" name="grantName" value="" />
			<span class="button"><input type="button" value="권한설정저장" onclick="fnSave()" ></span>
		</td>
		</tr>
		</table> --%>

		<table id="listInfo02" class="scroll"></table>
	</td>
	</tr>
	</table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



