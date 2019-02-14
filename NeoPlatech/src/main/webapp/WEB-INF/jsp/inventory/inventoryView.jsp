<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/asset/assetView.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap view = RequestUtil.getCommonMap(request, "assetView");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

</head>
<body>

	<h2 class="title_left">물품 상세</h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	
	<table width="100%" class="table-search" border="0">
	<colgroup>
		<col width="70%">
		<col width="30%">
	</colgroup>
	<tr>
		<td>
			<c:import url="/assets/fms/selectImageFileInfs.do" charEncoding="utf-8">
			<c:param name="param_org" value='<%=view.getString("org")%>' />
			<c:param name="param_atchFileId" value='<%=view.getString("asset_no")%>' />
			</c:import>
		</td>
	</tr>	
	</table>
	
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
		<th>취득일자</th>
		<td><%= view.getString("acquisition_date") %></td>
		<th>모델</th>
		<td><%= view.getString("model") %></td>
	</tr>
	<tr>
		<th>취득구분코드</th>
		<td><%= view.getString("asset_classification_code") %></td>
		<th>취득구분</th>
		<td><%= view.getString("asset_classification") %></td>
		<th>장소</th>
		<td><%= view.getString("place") %></td>
		<th>사용목적</th>
		<td><%= view.getString("use_purpose") %></td>
	</tr>
	<tr>
		<th>시리얼번호</th>
		<td><%= view.getString("serial_no") %></td>
		<th>초기취득가</th>
		<td><%= view.getString("init_acquisition_price") %></td>
		<th>증감취득가</th>
		<td><%= view.getString("change_acquisition_price") %></td>
		<th>취득가합계</th>
		<td><%= view.getString("acquisition_price") %></td>
	</tr>
	<tr>
		<th>장부가</th>
		<td><%= view.getString("ledger_price") %></td>
		<th>부서코드</th>
		<td><%= view.getString("dept") %></td>
		<th>부서명</th>
		<td><%= view.getString("dept_name") %></td>
		<th>사용자번호</th>
		<td><%= view.getString("user_no") %></td>
	</tr>
	<tr>
		<th>사용자명</th>
		<td><%= view.getString("user") %></td>
		<th>연락처</th>
		<td><%= view.getString("tel") %></td>
		<th>취득구분</th>
		<td><%= view.getString("acquisition_div") %></td>
		<th>발의번호</th>
		<td><%= view.getString("motion_no") %></td>
	</tr>
	<tr>
		<th>공동활용</th>
		<td><%= view.getString("joint_use") %></td>
		<th>관련계정</th>
		<td><%= view.getString("related_accounts") %></td>
		<th>취득업체</th>
		<td><%= view.getString("acquisition_biz") %></td>
		<th>업체번호</th>
		<td><%= view.getString("biz_no") %></td>
	</tr>
	<tr>
		<th>배정일</th>
		<td><%= view.getString("disposal_date") %></td>
		<th>배정이력</th>
		<td><%= view.getString("disposal_history") %></td>
		<th>비고</th>
		<td><%= view.getString("remark") %></td>
		<th>마지막재물조사일자</th>
		<td><%= view.getString("last_inventory_date") %></td>
	</tr>
	<tr>
		<th>불용여부</th>
		<td><%= view.getString("diuse_div") %></td>
		<th>상태</th>
		<td><%= view.getString("state") %></td>
		<th>태그출력여부</th>
		<td><%= view.getString("print_div") %></td>
		<th>등록일</th>
		<td><%= view.getString("reg_dt") %></td>
	</tr>
	</table>
	
	</form>
	
</body>
</html>

		

