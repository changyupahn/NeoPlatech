<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "관리자관리";
String curAction = "/kp1900/kp1910.do";
String curGridAction = "/kp1900/kp1910UserAjax.do";
String curGrid2Action = "/kp1900/kp1910LogAjax.do";
String procAction = "/kp1900/kp1910Proc.do";
String delProcAction = "/kp1900/kp1910DelProc.do";
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
var heightHip = 340;
var heightHip2 = 490;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth(parseInt($(window).width() * 0.4) - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
	$("#listInfo02").setGridWidth(parseInt($(window).width() * 0.6) - widthHip);
	$("#listInfo02").setGridHeight($(window).height() - heightHip2);
}

var colNames01 = [
				'userNo','grantNo','로그인ID','관리자명','권한'
				];
var colModel01 = [
				{name:'userNo', index:'userNo', width:'0px', hidden:true},
				{name:'grantNo', index:'grantNo', width:'0px', hidden:true},
   				{name:'userId', index:'userId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'userName', index:'userName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'grantName', index:'grantName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
   				];
var groupHeaders01 = [];

var colNames02 = [
				'로그인ID','접근일시','접근IP','로그인성공여부'
				];
var colModel02 = [
     			{name:'userId', index:'userId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'frstRegistPnttm', index:'frstRegistPnttm', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-DATE', formatter:fnFormatterDate},
   				{name:'accessIp', index:'accessIp', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
				{name:'loginYnStr', index:'loginYnStr', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						else if (value == "정상로그인") value = '<span style="font-weight:bold;color:blue">정상로그인</span>';
						else if (value == "로그인실패") value = '<span style="font-weight:bold;color:red">로그인실패</span>';
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

			/* var ids = $("#listInfo01").jqGrid("getDataIDs");
			if (ids.length > 0) {
				$("#listInfo01").setSelection(ids[0], true);
				fnDetail(ids[0]);
			} */
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
	var obj = $("#listInfo01").jqGrid("getRowData", rowid);
	$('#userNo').val(obj.userNo);
	$('#userId').val(obj.userId);
	$('#userPw').val(obj.userPw);
	$('#userName').val(obj.userName);
	$('#grantNo').val(obj.grantNo);
	$('.spanModify').show();
	$('.spanWrite').hide();
	$('#userId').attr("readonly", true);
}

function fnSave() {

	if (!confirm("저장 하시겠습니까?")) {
		return;
	}
	
	var frm = document.sForm;
	frm.action = "<%=procAction%>";
	frm.target = "usrProcFrame";
	frm.submit();

	<%-- $.ajax({
		type : "POST",
		url : "<%=procAction%>",
		data : fnSerializeObject(),
		dataType : "json",
		success:function(data)
		{
			if (data.ret == "OK") {
				alert("저장 되었습니다.");
				$('#userNo').val("");
				$('#userId').val("");
				$('#userPw').val("");
				$('#userName').val("");
				fnGridList();
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
	}); --%>
}

function fnSaveCallback() {
	$('#userNo').val("");
	$('#userId').val("");
	$('#userPw').val("");
	$('#userName').val("");
	$('#grantNo').val("DHD");
	fnGridList();
	fnGridList2();
}

function fnDel() {

	if (!confirm("삭제 하시겠습니까?")) {
		return;
	}

	$.ajax({
		type : "POST",
		url : "<%=delProcAction%>",
		data : fnSerializeObject(),
		dataType : "json",
		success:function(data)
		{
			if (data.ret == "OK") {
				alert("삭제 되었습니다.");
				fnGridList();
				fnGridList2();
				fnCanc();
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

function fnCanc() {
	$('.spanModify').hide();
	$('.spanWrite').show();
	$('#userNo').val("");
	$('#userId').val("");
	$('#userPw').val("");
	$('#userName').val("");
	$('#grantNo').val("DHD");
	$('#userId').attr("readonly", false);
}

$(document).ready(function(){

	fnGridList();
	fnGridList2();
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
	<input type="hidden" id="pageSize" name="pageSize" value="999" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />

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
	<td width="69%" valign="top">
		<table style="width:100%" class="table-search" border="0">
		<colgroup>
			<col width="" />
		</colgroup>
		<tr>
		<td class="r2">
			<% if (ssAuthManager) { %>
			<span class="button spanWrite"><input type="button" value="신규등록" onclick="fnSave()" ></span>
			<span class="button spanModify" style="display:none"><input type="button" value="수정" onclick="fnSave()" ></span>
			<span class="button spanModify" style="display:none"><input type="button" value="삭제" onclick="fnDel()" ></span>
			<span class="button spanModify" style="display:none"><input type="button" value="취소" onclick="fnCanc()" ></span>
			<% } %>
		</td>
		</tr>
		</table>

		<table class="search01">
		<colgroup>
			<col width="120px" />
			<col width="" />
		</colgroup>
		<tr>
			<th>로그인ID :</th>
			<td>
				<input type="text" id="userId" name="userId" value="" style="width:250px;" />
				<input type="hidden" id="userNo" name="userNo" value="" />
			</td>
		</tr>
		<tr>
			<th>비밀번호 :</th>
			<td>
				<input type="password" id="userPw" name="userPw" value="" style="width:250px;" />
			</td>
		</tr>
		<tr>
			<th>관리자명 :</th>
			<td>
				<input type="text" id="userName" name="userName" value="" style="width:250px;" />
			</td>
		</tr>
		<tr>
			<th>권한 :</th>
			<td>
				<select id="grantNo" name="grantNo">
				<option value="DHD" selected="selected">부서담당자</option>
				<option value="MGR">총괄관리자</option>
				</select>
			</td>
		</tr>
		</table>

		<div style="padding-bottom:30px"></div>

		<p>■ 접근기록</p>

		<table id="listInfo02" class="scroll"></table>
	</td>
	</tr>
	</table>

	</form>
	
	<iframe id="usrProcFrame" name="usrProcFrame" width="0" height="0" style="display:none"></iframe>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



