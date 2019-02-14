<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "자산상세정보";
String curAction = "/kp1300/kp1311.do";
String procAction = "/kp1300/kp1311Proc.do";
String delAction = "/kp1300/kp1311DelProc.do";
String curDetailAction = "/kp1300/kp1311Detail.do";
String curDisuseAction = "/kp1300/kp1311Disuse.do";
String curTabAction = "/kp1300/kp1311Tab.do";
String curImageAction = "/kp1300/kp1311ImageForm.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

int colbasewid = 310; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnInitTabForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curTabAction%>",
		data : {
			assetSeq : $('#assetSeq').val(),
			equipId : $('#equipId').val()
		},
		success:function(data)
		{
			$('#TabBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}
function fnInitDetailForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curDetailAction%>",
		data : {
			colcnt : colcnt,
			menuDiv : $('#menuDiv').val(),
			assetSeq : $('#assetSeq').val()
		},
		success:function(data)
		{
			$('#DetailBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}
function fnInitDisuseForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curDisuseAction%>",
		data : {
			colcnt : colcnt,
			menuDiv : $('#menuDiv').val(),
			assetSeq : $('#assetSeq').val()
		},
		success:function(data)
		{
			$('#DisuseBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}
function fnInitImageForm() {
	$.ajax({
		type : "POST",
		url : "<%=curImageAction%>",
		data : {
			assetSeq : $('#assetSeq').val(),
			equipId : $('#equipId').val()
		},
		success:function(data)
		{
			$('#ImageBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
		}
	});
}
function fnSave() {

	if (confirm("저장 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=procAction%>",
			data : fnSerializeObject(),
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("저장 되었습니다.");
					parent.fnGridList();
					location.href = "<%=curAction%>?assetSeq=" + $('#assetSeq').val();
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
function fnCard() {
	fnOpenPop("/kp1300/kp1318.do?assetSeq="+$('#assetSeq').val(), "KP1318CardPop", "1000", "2000", "menubar=no,status=no,titlebar=no,scrollbars=yes,location=no,toolbar=no,resizable=yes,left=0,top=0");
}
function fnDel() {
	if (confirm("삭제 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=delAction%>",
			data : fnSerializeObject(),
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("삭제 되었습니다.");
					parent.fnGridList();
					location.href = "<%=curAction%>?assetSeq=" + $('#assetSeq').val();
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

	fnInitImageForm();
	fnInitTabForm();
	fnInitDetailForm();

	<% if ("Y".equals(viewData.getString("disuseYn"))) { %>
	fnInitDisuseForm();
	<% } %>

	$('#assetTypeCd').bind('change', function() {
		$('#assetTypeName').val($('#assetTypeCd >option:selected').html());
	})
});

//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#itemCd').val(obj.cd);
	$('#itemName').val(obj.nm);
	$('#assetTypeCd').val(obj.atc);
	$('#assetTypeName').val($('#assetTypeCd >option:selected').html());
	$('#itemAssetTypeCd').val(obj.atc);
	$('#depreCd').val(obj.dc);
	$('#usefulLife').val(obj.ny);
}

//품목코드 팝업 콜백 함수
function fnSetKp9011(obj) {
	$('#itemCd').val(obj.cd);
	$('#itemName').val(obj.nm);
	$('#assetTypeCd').val(obj.atc);
	$('#assetTypeName').val($('#assetTypeCd >option:selected').html());
	$('#itemAssetTypeCd').val(obj.atc);
	$('#depreCd').val(obj.dc);
	$('#usefulLife').val(obj.ny);
}

//표준분류 팝업 콜백 함수
function fnSetKp9012(obj) {
	$('#zeusItemCd').val(obj.cd);
	$('#zeusItemName').val(obj.nm);
}

//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	if ($('#deptDiv').val() == "2") {
		$('#posNo').val(obj.cd);
		$('#posName').val(obj.nm);
	} else {
		$('#deptNo').val(obj.cd);
		$('#deptName').val(obj.nm);
		$('#topDeptNo').val(obj.pcd);
	}
}

//사용자 콜백
function fnSetKp9030(obj) {
	$('#userNo').val(obj.userNo);
	$('#userName').val(obj.userName);
	$('#deptNo').val(obj.deptNo);
	$('#deptName').val(obj.deptName);
	$('#posNo').val(obj.deptNo);
	$('#posName').val(obj.deptName);
	$('#spanDeptName').html(obj.deptName);
}

//위치 콜백
function fnSetKp9040(obj) {
	$('#posNo').val(obj.posNo);
	$('#posName').val(obj.posName);
}

//제조국 콜백
function fnSetKp9050(obj) {
	$('#mkNationCd').val(obj.natnCd);
	$('#mkNationName').val(obj.natnName);
}

//제조업체 콜백
function fnSetKp9060(obj) {
	$('#mkCompanyCd').val(obj.compCd);
	$('#mkCompanyName').val(obj.compName);
}

//판매업체 콜백
function fnSetKp9070(obj) {
	$('#slCompanyCd').val(obj.compCd);
	$('#slCompanyName').val(obj.compName);
}
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<% if (!"I".equals(cmRequest.getString("gbn"))) { %>
	<div id="TabBox" class="TabBox"></div>
	<% } %>

	<table style="width:100%;">
	<colgroup>
		<col width="" />
		<col width="510px" />
	</colgroup>
	<tr>
		<td style="padding:10px 0 0 0;border-collapse:collapse;margin:0;">
			<% if (!"I".equals(cmRequest.getString("gbn"))) { %>
			<h2 class="title_left" style="margin-bottom:0px;">물품이미지</h2>
			<% } %>
		</td>
		<td style="text-align:right;padding-right:20px;">
			<% if (ssAuthManager) { %>
			<!-- <span class="button"><input type="button" value="저 장" onclick="fnSave();"></span> -->
			<% } %>
			<%-- <% if (!"I".equals(cmRequest.getString("gbn"))) { %>
				<span class="button"><input type="button" value="자산관리카드" onclick="fnCard();"></span>
				<% if (ssAuthManager) { %>
					<span class="button"><input type="button" value="삭제" onclick="fnDel();"></span>
				<% } %>
			<% } %> --%>
		</td>
	</tr>
	</table>

	<% if (!"I".equals(cmRequest.getString("gbn"))) { %>
	<div id="ImageBox" class="ImageBox" style="overflow:auto;"></div>
	<% } %>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="gbn" name="gbn" value="<%=cmRequest.getString("gbn")%>" />
	<input type="hidden" id="menuDiv" name="menuDiv" value="<%=cmRequest.getString("menuDiv")%>" />
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />
	<input type="hidden" id="equipId" name="equipId" value="<%=viewData.getString("equipId")%>" />
	<input type="hidden" id="deptDiv" name="deptDiv" value="" />


	<% if ("Y".equals(viewData.getString("disuseYn"))) { %>
	<h2 class="title_left">불용정보</h2>
	<div id="DisuseBox" class="DisuseBox" style="padding-bottom:20px;"></div>
	<% } %>

	<% if (!"I".equals(cmRequest.getString("gbn"))) { %>
	<h2 class="title_left">자산상세정보</h2>
	<% } else { %>
	<h2 class="title_left">수기등록</h2>
	<% } %>

	<div id="DetailBox" class="DetailBox"></div>

	</form>

</body>
</html>



