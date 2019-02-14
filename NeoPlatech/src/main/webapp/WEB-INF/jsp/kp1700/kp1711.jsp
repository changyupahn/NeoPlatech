<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "재물조사관리 생성";
String curAction = "/kp1700/kp1711.do";
String procAction = "/kp1700/kp1711Proc.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
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
$(document).ready(function(){
});
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false;">

	<table style="width:100%;">
	<colgroup>
		<col width="" />
		<col width="510px" />
	</colgroup>
	<tr>
		<td style="padding:10px 0 0 0;border-collapse:collapse;margin:0;">
			<h2 class="title_left" style="margin-bottom:0px;"><%=pageTitle%></h2>
		</td>
		<td style="text-align:right;padding-right:20px;">
			<span class="button"><input type="button" value="저장" onclick="fnSave();"></span>
		</td>
	</tr>
	</table>

	<table class="search01">
	<colgroup>
		<col width="150px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>재물조사 기준일 :</th>
		<td>
			<input type="text" id="invStartDt" name="invStartDt" value="<%=DateUtil.getFormatDate("yyyy-MM-dd")%>" class="datepicker impt" />
		</td>
	</tr>
	<tr>
		<th>조사구분 :</th>
		<td>
			<select id="invType" name="invType" class="sel impt">
				<option value="">선택</option>
				<c:import url="/code/optionList.do" charEncoding="utf-8">
				<c:param name="paramCodeId" value="COM012" />
				<c:param name="paramSltValue" value='' />
				</c:import>
			</select>
		</td>
	</tr>
	<tr>
		<th>비고 :</th>
		<td>
			<input type="text" id="invSummary" name="invSummary" value="" class="def" />
		</td>
	</tr>
	</table>


	</form>

</body>
</html>



