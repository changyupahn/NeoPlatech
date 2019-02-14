<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "신규등록내역_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonList list = RequestUtil.getCommonList(request, "newRegisterStatList");
String asset_no = "";
String dept_name = "";
String asset_name = "";
int asset_cnt = 0;
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

	<table border="1">
	<tr>
		<th>물품코드</th>
		<th>부서명</th>
		<th>물품명</th>
		<th>물품수</th>
	</tr>

	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(list.totalRow, list.pageIdx, list.pageSize);
	for(int i=0; i<list.size(); i++){
		CommonMap idl = list.getMap(i);
		
 		String userStr = idl.getString("asset_no");
	%>	
	<tr>
		<td><%=idl.getString("asset_no")%></td>
		<td><%=idl.getString("dept_name")%></td>
		<td><%=idl.getString("asset_name")%></td>
		<td><%=idl.getInt("asset_cnt")%></td>
	</tr>
	<%
		if ((i+1) == list.size()) {
			%>

			<%
		}
	}
	%>
	</table>
	
</div>
	
</body>
</html>
	