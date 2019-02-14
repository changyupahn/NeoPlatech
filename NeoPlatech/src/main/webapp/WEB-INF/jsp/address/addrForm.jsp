<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap addressInfo = RequestUtil.getCommonMap(request, "addressInfo");
%>
<table class="table-cell" style="width:100%;">
<colgroup>
	<col width="100px" />
	<col width="" />
</colgroup>
<tr>
	<td colspan="2" style="height:30px; padding-left:10px;">주소 : <%= addressInfo.getString("spingAddress") %></td>
</tr>
<tr>
	<td colspan="2" style="height:30px; padding-left:10px;">연락처 : <%= addressInfo.getString("spingTel") %></td>
</tr>
<tr>
	<td colspan="2" style="height:30px; padding-left:10px;">받는분 성명 : <%= addressInfo.getString("spingName") %></td>
</tr>
<%-- <tr>
	<th class="t_strong" style="height:22px; padding-left:10px; text-align:left;">주소</th>
	<td style="height:30px; padding-left:5px;"><%= addressInfo.getString("spingAddress") %></td>
</tr>
<tr>
	<th class="t_strong" style="height:22px; padding-left:10px; text-align:left;">연락처</th>
	<td style="height:30px; padding-left:5px;"><%= addressInfo.getString("spingTel") %></td>
</tr>
<tr>
	<th class="t_strong" style="height:22px; padding-left:10px; text-align:left;">받는분 성명</th>
	<td style="height:30px; padding-left:5px;"><%= addressInfo.getString("spingName") %></td>
</tr> --%>
</table>