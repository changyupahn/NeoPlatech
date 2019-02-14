<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String curAction = "/inout/selectList.do";
String xlsDnAction = "/inout/selectListXls.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList inoutList = RequestUtil.getCommonList(request, "inoutList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=curAction%>";
	frm.target = "_self";
	frm.submit();
}
function fnXlsDn(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction%>";
	frm.target = "_self";
	frm.submit();
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">반출입내역관리</h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	
	<table width="100%" class="table-search" border="0">
	<colgroup>
		<col width="100px" />
		<col width="140px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="250px" />
		<col width="" />
	</colgroup>
	<tr>
	<th>게이트번호</th>
	<td>
		<select id="sGateNo" name="sGateNo" style="width:100px">
		<option value="">선택</option>
		<option value="1" <%="1".equals(cmRequest.getString("sGateNo"))?"selected=\"selected\"":""%>>**</option>
		<option value="2" <%="2".equals(cmRequest.getString("sGateNo"))?"selected=\"selected\"":""%>>**</option>	
		<option value="3" <%="3".equals(cmRequest.getString("sGateNo"))?"selected=\"selected\"":""%>>**</option>	
		</select>
	</td>
	<th>반출입일자</th>
	<td>
		<input type="text" id="sInoutDayS" name="sInoutDayS" value="<%=DateUtil.formatDate(cmRequest.getString("sInoutDayS"),"-")%>" class="datepicker" style="width:80px;" />
		~ <input type="text" id="sInoutDayE" name="sInoutDayE" value="<%=DateUtil.formatDate(cmRequest.getString("sInoutDayE"),"-")%>" class="datepicker" style="width:80px;" />
	</td>
	<th>검색어</th>
	<td>
		<select id="searchGubun" name="searchGubun" style="width:100px;">
			<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>물품번호</option>
			<option value="2" <%="2".equals(cmRequest.getString("searchGubun"))?"selected":""%>>사용자</option>
			<option value="3" <%="3".equals(cmRequest.getString("searchGubun"))?"selected":""%>>부서명</option>
		</select>
		<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
	</td>
	<td></td>
	</tr>
	</table>
	
	<table width="100%">
	<tr>
	<td width="60%">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
	</td>
	<td width="40%" style="text-align: right;">
	</td>
	</tr>
	</table>
	
	<p>총 <%=inoutList.totalRow%> 건</p>

	<table width="100%" class="table-line" summary="" >	
	<colgroup>
		<col width="80px" />
		<col width="120px" />
		<col width="200px" />
		<col width="150px" />
		<col width="120px" />
		<col width="120px" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col" class="title dataOrder" dataOrder="gateno" nowrap>게이트명</th>
			<th scope="col" class="title dataOrder" dataOrder="asset_no" nowrap>물품번호</th>
			<th scope="col" class="title dataOrder" dataOrder="asset_name" nowrap>물품명</th>
			<th scope="col" class="title dataOrder" dataOrder="dept_name" nowrap>사용자부서</th>
			<th scope="col" class="title dataOrder" dataOrder="user_name" nowrap>사용자명</th>
			<th scope="col" class="title dataOrder" dataOrder="create_dt" nowrap>통과일시</th>
		</tr>
	</thead>
	<tbody>
		<% if(inoutList.size() == 0){ %>
		<tr>
			<td colspan="50">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(inoutList.totalRow, inoutList.pageIdx, inoutList.pageSize);
		for(int i=0; i<inoutList.size(); i++){
			CommonMap inout = inoutList.getMap(i);
		%>
		<tr>
			<td><%=inout.getString("gatenm")%></td>
			<td><%=inout.getString("asset_no")%></td>
			<td><%=inout.getString("asset_name")%></td>
			<td><%=inout.getString("dept_name")%></td>
			<td><%=inout.getString("user_name")%></td>
			<td>
				<%=DateUtil.formatDate(inout.getString("create_dt"), "-")%>
				<%=DateUtil.formatTime(inout.getString("create_dt"), ":")%>
			</td>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>
	
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td width="">
		<div class="paginate">
		<!-- pageing -->
		<%=PagingUtil.PagingDataB(inoutList,"sForm",curAction)%>
		<!-- pageing -->
	</div>
	</td>
	<td width="150px" class="r">
		<select id="pageSize" name="pageSize" onchange="fnSearch()" style="border:1px solid gray;">
		<option value="15" <%="15".equals(cmRequest.getString("pageSize","15"))?"selected":""%>>페이지당 15개</option>
		<option value="30" <%="30".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 30개</option>
		<option value="50" <%="50".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 50개</option>
		<option value="100" <%="100".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 100개</option>
		<option value="200" <%="200".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 200개</option>
		<option value="300" <%="300".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 300개</option>
		<option value="1000" <%="1000".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 1000개</option>
		</select>
	</td>
	</tr>
	</table>
	
	</form>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

