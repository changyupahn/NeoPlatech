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
	<input type="text" id="itemCd" name="itemCd" value="<%=viewData.getString("itemCd")%>" />
	
	<input type="hidden" id="parentItemSeq" name="parentItemSeq" value="<%=viewData.getString("parentItemSeq")%>" />
	<input type="hidden" id="itemSeq" name="itemSeq" value="<%=viewData.getString("itemSeq")%>" />
	<input type="hidden" id="itemLevel" name="itemLevel" value="<%=viewData.getInt("itemLevel")%>" />
	<input type="hidden" id="assetTypeCd" name="assetTypeCd" value="<%=viewData.getString("assetTypeCd")%>" />
	<input type="hidden" id="usefulLife" name="usefulLife" value="<%=viewData.getInt("usefulLife")%>" />
	<input type="hidden" id="depreCd" name="depreCd" value="<%=viewData.getInt("depreCd")%>" />
</td>
</tr>
<tr>
<th>품목명 : </th>
<td>
	<input type="text" id="itemName" name="itemName" value="<%=viewData.getString("itemName")%>" />
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