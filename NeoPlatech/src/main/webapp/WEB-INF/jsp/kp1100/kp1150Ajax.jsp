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
	<span class="button"><input type="button" value="하위코드추가" onclick="fnRegTreeData()"></span>
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
<th>상위코드명 : </th>
<td>
	<%=viewData.getString("parentNatnName")%>
	<input type="hidden" id="parentNatnSeq" name="parentNatnSeq" value="<%=viewData.getString("parentNatnSeq")%>" />
	<input type="hidden" id="natnSeq" name="natnSeq" value="<%=viewData.getString("natnSeq")%>" />
	<input type="hidden" id="natnLevel" name="natnLevel" value="<%=viewData.getInt("natnLevel")%>" />
</td>
</tr>
<tr>
<th>제조국코드 : </th>
<td>
	<input type="text" id="natnCd" name="natnCd" value="<%=viewData.getString("natnCd")%>" />
</td>
</tr>
<tr>
<th>제조국명 : </th>
<td>
	<input type="text" id="natnName" name="natnName" value="<%=viewData.getString("natnName")%>" />
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