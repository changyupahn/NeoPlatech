<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
%>
<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:10px">
	부서코드를 추가합니다. 아래 항목을 입력 후 등록 버튼을 눌러주세요.
</div>

<table width="100%" class="table-register">
<colgroup>
	<col width="120px" />
	<col width="" />
</colgroup>
<tr>
<th>부서코드 : </th>
<td>
	<input type="text" id="deptNo" name="deptNo" value="<%=viewData.getString("deptNo")%>" />
	
	<input type="hidden" id="parentDeptNo" name="parentDeptNo" value="<%=viewData.getString("parentDeptNo")%>" />
	<input type="hidden" id="deptLevel" name="deptLevel" value="<%=viewData.getInt("deptLevel")%>" />
	<input type="hidden" id="deptHeadUserName" name="deptHeadUserName" value="<%=viewData.getString("deptHeadUserName")%>" />
</td>
</tr>
<tr>
<th>부서명 : </th>
<td>
	<input type="text" id="deptName" name="deptName" value="<%=viewData.getString("deptName")%>" />
</td>
</tr>
</table>