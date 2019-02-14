<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "반출입내역_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList inoutList = RequestUtil.getCommonList(request, "inoutList");
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

<table border="1">
<thead>
	<tr>
		<th>게이트명</th>
		<th>물품번호</th>
		<th>물품명</th>
		<th>사용자부서</th>
		<th>사용자명</th>
		<th>통과일시</th>
	</tr>
</thead>
<tbody>
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