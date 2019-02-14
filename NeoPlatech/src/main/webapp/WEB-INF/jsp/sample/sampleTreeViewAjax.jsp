<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap sampleView = RequestUtil.getCommonMap(request, "sampleView");
%>
<table width="100%" class="table-register">
<colgroup>
	<col width="120px" />
	<col width="" />
</colgroup>
<tr>
<th>제목 : </th>
<td><%=sampleView.getString("COL2")%></td>
</tr>
<tr>
<th>작성자 : </th>
<td><%=sampleView.getString("COL3")%></td>
</tr>
</table>