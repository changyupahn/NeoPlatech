<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "사용자별통계_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonList list = RequestUtil.getCommonList(request, "empStatList");
String tmpEmpId = "";
String tmpEmpNm = "";
int assetCnt = 0;
%>
<style type="text/css">
td {mso-number-format:\@}
</style>

	<table border="1">
	<tr>
		<th>사용자</th>
		<th>구분</th>
		<th>물품명</th>
		<th>물품번호</th>
		<th>취득일</th>
		<th>수량</th>
	</tr>
	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(list.totalRow, list.pageIdx, list.pageSize);
	for(int i=0; i<list.size(); i++){
		CommonMap idl = list.getMap(i);
		
		String useEmp = idl.getString("USE_EMP_ID") + idl.getString("EMP_NAME");
		
		if (i == 0) {
			tmpEmpId = useEmp;
			tmpEmpNm = idl.getString("EMP_NAME");
		}
		if (!tmpEmpId.equals(useEmp) && i > 0) {			
			%>
			<tr>
			<td colspan="5"><strong>*****<%//=tmpEmpNm%> 총물품</strong></td>
			<td><strong>*****<%//=assetCnt%></strong></td>
			</tr>
			<%
			
			tmpEmpId = useEmp;
			tmpEmpNm = idl.getString("EMP_NAME");
			assetCnt = 0;			
		}
		
		assetCnt += idl.getInt("CNT");
	%>
	<tr>
		<td><%=idl.getString("EMP_NAME")%></td>
		<td><%=idl.getString("ACCOUNT_NAME")%></td>
		<td><%=idl.getString("FIXASSET_NAME")%></td>
		<td><%=idl.getString("FIXASSET_CODE")%></td>
		<td><%=idl.getString("AQUSIT_DAY")%></td>
		<td><%=idl.getString("CNT")%></td>
	</tr>
	<%
		if ((i+1) == list.size()) {
			%>
			<tr>
			<td colspan="5"><strong><%=idl.getString("EMP_NAME")%> 총물품</strong></td>
			<td><strong>*****</strong></td>
			</tr>
			<%
		}
	}
	%>
	</table>