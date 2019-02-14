<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "자산상세정보";
String curAction = "/kp1300/kp1321.do";
String procAction = "/kp1300/kp1321Proc.do";
String curDetailAction = "/kp1300/kp1321Detail.do";
String curTabAction = "/kp1300/kp1321Tab.do";
String curImageAction = "/kp1300/kp1321ImageForm.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

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
			colcnt : colcnt,
			assetSeq : $('#assetSeq').val()
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
function fnInitImageForm() {
	$.ajax({
		type : "POST",
		url : "<%=curImageAction%>",
		data : {
			assetSeq : $('#assetSeq').val()
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
}

//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	$('#deptNo').val(obj.cd);
	$('#deptName').val(obj.nm);
	$('#topDeptNo').val(obj.pcd);
}

//사용자 콜백
function fnSetKp9030(obj) {
	$('#userNo').val(obj.userNo);
	$('#userName').val(obj.userName);
	$('#deptNo').val(obj.deptNo);
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

	<div id="TabBox" class="TabBox"></div>

	<table style="width:100%;">
	<colgroup>
		<col width="" />
		<col width="510px" />
	</colgroup>
	<tr>
		<td style="padding:10px 0 0 0;border-collapse:collapse;margin:0;">
			<h2 class="title_left" style="margin-bottom:0px;">물품이미지</h2>
		</td>
		<td style="text-align:right;">
			<span class="button"><input type="button" value="저 장" onclick="fnSave();"></span>
			<span class="button"><input type="button" value="자산관리카드" onclick="fnCard();"></span>
			<span class="button"><input type="button" value="삭제" onclick="fnDeleteForm();"></span>
		</td>
	</tr>
	</table>

	<div id="ImageBox" class="ImageBox" style="overflow:auto;"></div>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">

	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />

	<h2 class="title_left">자산상세정보</h2>

	<div id="DetailBox" class="DetailBox"></div>

	</form>

</body>
</html>



