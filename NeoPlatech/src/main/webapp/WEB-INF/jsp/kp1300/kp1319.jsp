<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "등록/수정 (엑셀업로드)";
String curAction = "/kp1300/kp1319.do";
String xlsUpAction = "/kp1300/kp1319Proc.do";
String xlsDnAction = "/kp1300/kp1319Excel.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnXlsUp(){

	if ($('#xls_file').val() == "") {
		alert("업로드 할 엑셀파일을 선택해 주세요");
		return;
	}

	if (confirm("데이터가 많으면 1분 이상 소요될 수 있습니다.\r\n창을 닫지 마시고 완료될 때까지 기다려 주십시오.\r\n계속 진행하시겠습니까?")) {

		$('#statusArea').append("\r\n처리 진행 중 입니다...\r\n");
		fnLoadingS2();

		document.sForm.action = "<%=xlsUpAction%>";
		document.sForm.target = "amProcFrame";
		document.sForm.submit();

		$('#spanXlsFile').html('<input type="file" name="xls_file" id="xls_file" />');

	}
}
function fnXlsUpCallback(forwardMsg) {
	fnLoadingE2();
	$('#statusArea').append(forwardMsg);
	$('#statusArea').scrollTop($('#statusArea')[0].scrollHeight);
	parent.fnGridList();
}
function fnXlsDn() {
	location.href = "<%=xlsDnAction%>";
}
$(document).ready(function(){
});
</script>

</head>
<body style="overflow:auto;margin:15px;">

	<h2 class="title_left"><%=pageTitle%></h2>

	<h4 style="font-size:14px;color:blue;">엑셀 업로드</h4>

	<form id=sForm name="sForm" method="post" action="<%=curAction%>" enctype="multipart/form-data">

	<table width="100%" class="table-search" border="0">
	<tr>
		<th width="650px" class="l" style="height:200px; font-size:13px;padding:10px;">
			엑셀 업로드를 이용하여 물품을 신규등록 또는 수정 합니다.
			<br /><br />- 아래의 양식파일을 다운로드하여 이용해 주세요.
			<br /><br />- 양식파일의 <strong>항목을 삭제하거나 위치를 변경하시면 안됩니다.</strong>
			<br /><br />- 양식파일(xls) <span class="button"><input type="button" value="다운로드" onclick="fnXlsDn();"></span>
			<br /><br />- 엑셀업로드 : <span id="spanXlsFile"><input type="file" name="xls_file" id="xls_file" style="height:22px;" /></span>
			<span class="button"><input type="button" value="업로드" onclick="fnXlsUp();"></span>
		</th>
		<td class="l" style="font-size:13px;">
			<textarea id="statusArea" rows="9" cols="50" style="border:0;width:80%;height:200px"></textarea>
		</td>
	</tr>
	</table>

	</form>

	<iframe id="amProcFrame" name="amProcFrame" width="0" height="0"></iframe>

</body>
</html>



