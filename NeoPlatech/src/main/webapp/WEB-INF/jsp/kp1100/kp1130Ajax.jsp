<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
%>
<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:10px">
	* MIS에서 관리되는 정보로 조회만 가능합니다.
</div>

<table width="100%" class="table-register">
<colgroup>
	<col width="120px" />
	<col width="" />
</colgroup>
<tr>
<th>상위위치명 : </th>
<td>
	<%=viewData.getString("parentPosName")%>
	<input type="hidden" id="parentPosNo" name="parentPosNo" value="<%=viewData.getString("parentPosNo")%>" />
	<input type="hidden" id="posLevel" name="posLevel" value="<%=viewData.getInt("posLevel")%>" />
</td>
</tr>
<tr>
<th>위치코드 : </th>
<td>
	<input type="text" id="posNo" name="posNo" value="<%=viewData.getString("posNo")%>" />
</td>
</tr>
<tr>
<th>위치명 : </th>
<td>
	<input type="text" id="posName" name="posName" value="<%=viewData.getString("posName")%>" style="width:95%" />
</td>
</tr>
<tr>
<th>사용여부 : </th>
<td><%=viewData.getString("useYnStr")%></td>
</tr>
</table>