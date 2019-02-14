<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "태그출력내역_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList printHistoryList = RequestUtil.getCommonList(request, "printHistoryList");
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

	<table border="1" >
		<tr>
			<th>No</th>
			<th>물품번호</th>
			<th>물품명</th>
			<th>규격</th>
			<th>물품구분</th>
			<th>취득일</th>
			<th>비고</th>
			<th>출력요청일시</th>
			<th>상태</th>
		</tr>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(printHistoryList.totalRow, printHistoryList.pageIdx, printHistoryList.pageSize);
		for(int i=0; i<printHistoryList.size(); i++){
			CommonMap inout = printHistoryList.getMap(i);
			
			String print_st_name = "";
			if ("N".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄대기중";
			} else if ("R".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄중";
			} else if ("Y".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄완료";
			} else if ("F".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄실패";
			}
		%>
		
		<tr>
			<td><%=(pagingFristNo - i)%></td>
			<td><%=inout.getString("asset_no")%></td>
			<td><%=inout.getString("asset_name")%></td>
			<td><%=inout.getString("asset_size")%></td>
			<td><%=inout.getString("asset_type")%></td>
			<td>
				<%=DateUtil.formatDate(inout.getString("aqusit_dt"), "-")%>
			</td>
			<td><%=inout.getString("tmp_title")%></td>
			<td>
				<%=DateUtil.formatDate(inout.getString("print_dt"), "-")%>
				<%=DateUtil.formatTime(inout.getString("print_dt"), ":")%>
			</td>
			<td><%=print_st_name%></td>
		</tr>
		<%
		}
		%>
	</table>