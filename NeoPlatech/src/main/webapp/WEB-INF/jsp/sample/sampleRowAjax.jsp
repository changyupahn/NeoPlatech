<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
String num = cmRequest.getString("num");
%>
<tr id="trRow_<%= num %>">
	<td>
		<input type="hidden" id="COL1_<%= num %>" name="COL1" value="" />
		<%= num %>
	</td>
	<td><input type="text" id="COL2_<%= num %>" name="COL2" value="" /></td>
	<td><input type="text" id="COL3_<%= num %>" name="COL3" value="" /></td>
	<td><input type="text" id="COL4_<%= num %>" name="COL4" value="" /></td>
	<td><a href="javascript:fnDelRow('<%= num %>');">행삭제</a></td>
</tr>