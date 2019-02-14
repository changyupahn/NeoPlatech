<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap sampleView = RequestUtil.getCommonMap(request, "sampleView");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnModify(){
	var frm = document.sForm;
	frm.action = "/sample/sampleModifyForm.do";
	frm.submit();
}
function fnList(){
	var frm = document.sForm;
	frm.action = "/sample/sampleList.do";
	frm.submit();
}
</script>

</head>
<body>
		<form id="sForm" name="sForm" method="post" action="/sample/sampleModifyForm.do">
		<input type="hidden" name="COL1" value="<%=sampleView.getString("COL1")%>" />
		
		<table width="100%" class="table-register" border="0">
		<colgroup>
			<col width="100px" />
			<col width="" />
		</colgroup>
		<tr>
		<th>제목</th>
		<td>
			<%=sampleView.getString("COL2")%>
		</td>
		</tr>
		<tr>
		<th>작성자</th>
		<td>
			<%=sampleView.getString("COL3")%>
		</td>
		</tr>
		<tr>
		<th>첨부파일목록</th>
		<td>
			<c:import url="/assets/fms/selectFileInfs.do" charEncoding="utf-8">
			<c:param name="param_atchFileId" value="${sampleView.ATCH_FILE_ID}" />
			</c:import>
		</td>
		</tr>
		<tr>
		<th>첨부파일목록( 수정용 )</th>
		<td>
			<c:import url="/assets/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
			<c:param name="param_atchFileId" value="${sampleView.ATCH_FILE_ID}" />
			</c:import>
		</td>
		</tr>
		<tr>
		<th>첨부파일목록( 미리보기용 )</th>
		<td>
			<c:import url="/assets/fms/selectImageFileInfs.do" charEncoding="utf-8">
			<c:param name="param_atchFileId" value="${sampleView.ATCH_FILE_ID}" />
			</c:import>
		</td>
		</tr>
		</table>
		
		<div class="tb_btn_c">
			<a class="btn_big" href="javascript:fnModify();"><strong>수정</strong></a>
			<a class="btn_big" href="javascript:fnList();"><span>목록</span></a>
		</div>
		
		</form>

</body>
</html>
