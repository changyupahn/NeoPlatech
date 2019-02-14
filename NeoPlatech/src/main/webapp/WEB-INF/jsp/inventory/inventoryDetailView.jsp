<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/inventory/selectDetail.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap view = RequestUtil.getCommonMap(request, "inventoryDetail");
CommonList assetHistoryList = RequestUtil.getCommonList(request, "assetHistoryList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

</head>
<body>

	<h2 class="title_left">물품 상세</h2>
	
	<table width="100%" class="table-search" border="0">
	<tr>
		<th width="10%">물품이미지</th>
		<td>
			<c:import url="/asset/getImageList.do" charEncoding="utf-8">
			<c:param name="param_asset_no" value='<%=view.getString("asset_no")%>' />
			</c:import>
		</td>
		<td width="25%" valign="bottom" style="padding:0 15px 15px 0;text-align:right;">
			<script type="text/javascript">
			function fnImgUp(){
				var frm = document.imgForm;
				frm.submit();
			}
			</script>
			<form name="imgForm" method="post" action="/asset/image/uploadWeb.do" enctype="multipart/form-data">
			<input type="hidden" name="asset_no" value="<%=view.getString("asset_no")%>" />
			<input type="file" name="file" style="height:18px;" />
			<span class="button"><input type="button" value="이미지등록" onclick="fnImgUp();"></span>
			</form>
		</td>
	</tr>	
	</table>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	
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
		<td><%= view.getString("acq_amt") %></td>
	</tr>
	<tr>
		<th>내용연수</th>
		<td><%= view.getString("use_life") %></td>
		<th>IT물품여부</th>
		<td><%= view.getString("it_fixasset_yn") %></td>
		<th>취득재원(과제코드)</th>
		<td><%= view.getString("project_name") %>)</td>
		<th></th>
		<td></td>
	</tr>
	</table>
	
	<h2 class="title_left">물품 히스토리 (재물조사)</h2>
	<table width="100%" class="table-line" border="0">
	<colgroup>
		<col width="20%" />
		<col width="40%" />
		<col width="30%" />
		<col width="10%" />
	</colgroup>
	<tr>
		<th>구분</th>
		<th>사용자 (부서)</th>
		<th>소재지</th>
		<th>변경일시</th>
	</tr>
	<% if(assetHistoryList.size() == 0){ %>
	<tr>
		<td colspan="50">목록이 없습니다.</td>
	</tr>
	<% } %>
	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(assetHistoryList.totalRow, assetHistoryList.pageIdx, assetHistoryList.pageSize);
	for(int i=0; i<assetHistoryList.size(); i++){
		CommonMap assetHistory = assetHistoryList.getMap(i);
	%>
	<tr>
		<td>
			<%=assetHistory.getString("inv_year")%>차 재물조사
		</td>
		<td>
			<%=assetHistory.getString("user_name")%>)
			<% if (!"".equals(assetHistory.getString("cng_user_name"))) { %>
			<span style="color:blue">&nbsp;&nbsp;&nbsp;&nbsp;=&gt; <%=assetHistory.getString("cng_user_name")%>)</span>
			<% } %>
		</td>
		<td>
			<%=assetHistory.getString("pos_name")%>
			<% if (!"".equals(assetHistory.getString("cng_pos_name"))) { %>
			<span style="color:blue">&nbsp;&nbsp;&nbsp;&nbsp;=&gt; <%=assetHistory.getString("cng_pos_name")%></span>
			<% } %>
		</td>
		<td><%=DateUtil.formatDateTime(assetHistory.getString("cng_gijun_dt"), "-", ":")%></td>
	</tr>
	<%
	}
	%>
	</table>
	
	<div class="buttonDiv">
		<span class="button"><input type="button" value="닫기" onclick="javascript:self.close();"></span>
	</div>
	
	</form>
	
</body>
</html>

		

