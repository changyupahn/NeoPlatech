<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap sampleView = RequestUtil.getCommonMap(request, "sampleView");
%>
<tr id="trView_<%=sampleView.getString("COL1")%>" class="trDetailClass">
	<td colspan="5">
		<div>
		내용 : <%=sampleView.getString("COL2")%>
		<br />
		<a href="javascript:fnView('<%=sampleView.getString("COL1")%>');">팝업창호출</a>
		</div>
	</td>
</tr>