<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/asset/selectRow.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap view = RequestUtil.getCommonMap(request, "asset");
%>
<tr class="trDetailClass" style="display:none;">
<td colspan="12">
	<c:import url="/asset/getImageList.do" charEncoding="utf-8">
	<c:param name="param_asset_no" value='<%=view.getString("asset_no")%>' />
	</c:import>
	
	<table width="100%" class="table-search" border="0" id="tabCont0">
	<colgroup>
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
	</colgroup>
	<tr>
		<th>물품번호</th>
		<td><%= view.getString("asset_no") %></td>
		<th>물품명</th>
		<td><%= view.getString("asset_name") %></td>
		<th>물품유형코드</th>
		<td><%= view.getString("account_index") %></td>
		<th>물품유형</th>
		<td><%= view.getString("account_name") %></td>
	</tr>
	<tr>
		<th>물품분류코드</th>
		<td><%= view.getString("at_kind_code") %></td>
		<th>물품분류명</th>
		<td><%= view.getString("at_kind_name") %></td>
		<th>사용자사번</th>
		<td><%= view.getString("use_emp_id") %></td>
		<th>사용자부서명</th>
		<td><%= view.getString("dept_name") %></td>
	</tr>
	<tr>
		<th>사용자명</th>
		<td><%= view.getString("user_name") %></td>
		<th>소재지코드</th>
		<td><%= view.getString("pos_no") %></td>
		<th>소재지명</th>
		<td><%= view.getString("pos_name") %></td>
		<th>감가상각여부</th>
		<td><%= view.getString("deprec_yn") %></td>
	</tr>
	<tr>
		<th>구입처</th>
		<td><%= view.getString("cust_name") %></td>
		<th>취득일자</th>
		<td><%= view.getString("aqusit_day") %></td>
		<th>규격</th>
		<td><%= view.getString("asset_size") %></td>
		<th>취득가</th>
		<td><%= StringUtil.commaNum(view.getString("acq_amt")) %></td>
	</tr>
	<tr>
		<th>내용연수</th>
		<td><%= view.getString("use_life") %></td>
		<th>IT물품여부</th>
		<td><%= view.getString("it_fixasset_yn") %></td>
		<th></th>
		<td></td>
		<th></th>
		<td></td>
	</tr>
	</table>
	
	<table width="100%" class="table-search" border="0">
	<tr>
		<td style="text-align:center;">
			<span class="button"><input type="button" value="닫기" onclick="javascript:self.close();"></span>
		</td>
	</tr>	
	</table>	
</td>
</tr>