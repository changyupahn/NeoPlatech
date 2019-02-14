<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
%>
<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:10px">
	<span style="color:blue;font-weight:bold;"><%=viewData.getString("compName")%></span>의 하위코드를 추가합니다. 아래 항목을 입력 후 등록 버튼을 눌러주세요.
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
<th>상위코드명 : </th>
<td>
	<%=viewData.getString("compName")%>
	<input type="hidden" id="parentCompSeq" name="parentCompSeq" value="<%=viewData.getString("compSeq")%>" />
	<input type="hidden" id="compSeq" name="compSeq" value="" />
	<input type="hidden" id="compLevel" name="compLevel" value="<%=viewData.getInt("compLevel") + 1%>" />
</td>
</tr>
<tr>
<th>제조업체코드 : </th>
<td>
	<input type="text" id="compCd" name="compCd" value="" />
</td>
</tr>
<tr>
<th>제조업체명 : </th>
<td>
	<input type="text" id="compName" name="compName" value="" />
</td>
</tr>
<tr>
<th>국가코드 : </th>
<td>
	<input type="text" id="natnCd" name="natnCd" value="" class="cd3" onclick="kp9050Pop()" />
	<input type="text" id="natnName" name="natnName" value="" class="cd4" />
</td>
</tr>
<tr>
<th>연락처 : </th>
<td>
	<input type="text" id="compTel" name="compTel" value="" />
</td>
</tr>
<tr>
<th>휴대폰 : </th>
<td>
	<input type="text" id="compMobile" name="compMobile" value="" />
</td>
</tr>
<tr>
<th>팩스 : </th>
<td>
	<input type="text" id="compFax" name="compFax" value="" />
</td>
</tr>
<tr>
<th>이메일 : </th>
<td>
	<input type="text" id="compEmail" name="compEmail" value="" />
</td>
</tr>
<tr>
<th>홈페이지 : </th>
<td>
	<input type="text" id="compHomepage" name="compHomepage" value="" />
</td>
</tr>
<tr>
<th>주소 : </th>
<td>
	<input type="text" id="compAddr" name="compAddr" value="" />
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