<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "갱신요청내용_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonList requestCon = RequestUtil.getCommonList(request, "requestCon");
%>
<style type="text/css">
td {mso-number-format:\@}
</style>

	<table border="1" >
	<thead>
		<tr>    
			<th scope="col" class="title" nowrap>번호</th>
			<th scope="col" class="title" nowrap>물품번호</th>
			<th scope="col" class="title" nowrap>물품명</th>
			<th scope="col" class="title" nowrap>기관명</th>
			<th scope="col" class="title" nowrap>관리자</th>
			<th scope="col" class="title" nowrap>호실</th>
			<th scope="col" class="title" nowrap>변경기준일</th>
			<th scope="col" class="title" nowrap>생성일</th>
		</tr>
	</thead>
	<tbody>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(requestCon.totalRow, requestCon.pageIdx, requestCon.pageSize);
		for(int i=0; i<requestCon.size(); i++){
			CommonMap rcmap = requestCon.getMap(i);
		%>
		<tr>
			<td><%=(pagingFristNo - i)%></td>
			<td><%=rcmap.getString("asset_no")%></td>
			<td><%=rcmap.getString("asset_name")%></td>
			<td><%=rcmap.getString("org_name")%></td>
			<td><%=rcmap.getString("user_name")%></td>
			<td><%=rcmap.getString("hosil")%></td>
			<td><%=DateUtil.formatDate(rcmap.getString("cng_basic_dt"), "-") %></td>
			<td><%=DateUtil.formatDate(rcmap.getString("create_dt"), "-") %></td>
		</tr>
		<%
		}
		%>
	</tbody>	
	</table>