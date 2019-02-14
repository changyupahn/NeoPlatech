<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
%>

<table width="100%" class="table-search" border="0">
<colgroup>
	<col width="70%" />
	<col width="30%" />
</colgroup>
<tr>
<th style="text-align:right;">
	<% if(ssAuthManager){ %>
	<!-- <span class="button"><input type="button" value="코드추가" onclick="fnRegTreeData()"></span> -->
	<span class="button"><input type="button" value="수정" onclick="fnSave();"></span>
	<span class="button"><input type="button" value="삭제" onclick="fnDel();"></span>
	<% } %>
</th>
</tr>
</table>

<table width="100%" class="table-register">
<colgroup>
	<col width="120px" />
	<col width="" />
</colgroup>
<tr>
<th>물품분류 : </th>
<td>
	<input type="text" id="bldngNo" name="bldngNo" value="<%=viewData.getString("bldngNo")%>" />
	
	<input type="hidden" id="parentBldngSeq" name="parentBldngSeq" value="<%=viewData.getString("parentBldngSeq")%>" />
	<input type="hidden" id="bldngSeq" name="bldngSeq" value="<%=viewData.getString("bldngSeq")%>" />
	<input type="hidden" id="bldngLevel" name="bldngLevel" value="<%=viewData.getInt("bldngLevel")%>" />
</td>
</tr>
<tr>
<th>품목명 : </th>
<td>
	<input type="text" id="bldngName" name="bldngName" value="<%=viewData.getString("bldngName")%>" />
</td>
</tr>
<tr>
<th>사용여부 : </th>
<td>
	<select id="useYn" name="useYn">
	<c:import url="/code/optionList.do" charEncoding="utf-8">
	<c:param name="paramCodeId" value="COM002" />
	<c:param name="paramSltValue" value='<%=viewData.getString("useYn")%>' />
	</c:import>
	</select>
</td>
</tr>
<tr>
<th>비고 : </th>
<td>
	<input type="text" id="remark" name="remark" value="<%=viewData.getString("remark")%>" style="width:90%" />
</td>
</tr>
<tr>
<th>정렬번호 : </th>
<td>
	<input type="text" id="sortNum" name="sortNum" value="<%=viewData.getString("sortNum")%>" />
</td>
</tr>
</table>