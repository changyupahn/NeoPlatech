<%@page import="java.net.InetAddress"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap sampleView = RequestUtil.getCommonMap(request, "sampleView");
%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<script type="text/javascript">
function fnWrite(){
	var frm = document.sForm;
	frm.action = "/sample/sampleWriteProc.do";
	frm.submit();
}
function fnModify(){
	var frm = document.sForm;
	frm.action = "/sample/sampleModifyProc.do";
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

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">샘플 상세</h2>
	
	<form id="sForm" name="sForm" method="post" action="/sample/sampleWriteProc.do">
	<input type="hidden" name="COL1" value="<%=sampleView.getString("COL1")%>" />

	<table width="100%" class="table-register">
	<colgroup>
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
	<th>제목 : </th>
	<td>
		<input type="text" id="COL2" name="COL2" value="<%=sampleView.getString("COL2")%>" size="50" maxlength="200" />
	</td>
	</tr>
	<tr>
	<th>작성자 : </th>
	<td>
		<input type="text" id="COL3" name="COL3" value="<%=sampleView.getString("COL3")%>" size="15" maxlength="20" />
	</td>
	</tr>
	</table>
	
	<div class="buttonDiv">
		<% if( sampleView.isEmpty()){ %>
		<span class="button"><input type="button" value="등록" onclick="fnWrite();"></span>
		<% } else { %>
		<span class="button"><input type="button" value="수정" onclick="fnModify();"></span>
		<% } %>
		<span class="button"><input type="button" value="취소" onclick="fnList();"></span>
	</div>
	
	</form>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

