<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
%>
<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:10px">
	코드를 추가합니다. 아래 항목을 입력 후 등록 버튼을 눌러주세요.
</div>
<table width="100%" class="table-search" border="0">
<colgroup>
	<col width="70%" />
	<col width="30%" />
</colgroup>
<tr>
<th style="text-align:left;padding-left:10px;">
</th>
<th style="text-align:right;">
	<% if(ssAuthManager){ %>
	<span class="button"><input type="button" value="등록" onclick="fnSave();"></span>
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
	<input type="text" id="itemCd" name="itemCd" value="" />
</td>
</tr>
<tr>
<th>품목명 : </th>
<td>
	<input type="text" id="itemName" name="itemName" value="" />
</td>
</tr>
<tr>
<th>사용여부 : </th>
<td>
	<select id="useYn" name="useYn">
	<c:import url="/code/optionList.do" charEncoding="utf-8">
	<c:param name="paramCodeId" value="COM002" />
	<c:param name="paramSltValue" value="Y" />
	</c:import>
	</select>
</td>
</tr>
<tr>
<th>비고 : </th>
<td>
	<input type="text" id="remark" name="remark" value="" style="width:90%" />
</td>
</tr>
<tr>
<th>정렬번호 : </th>
<td>
	<input type="text" id="sortNum" name="sortNum" value="" />
</td>
</tr>
</table>