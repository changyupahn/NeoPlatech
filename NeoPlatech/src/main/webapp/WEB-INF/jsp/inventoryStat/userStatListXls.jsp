<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "관리자별통계_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonList list = RequestUtil.getCommonList(request, "userStatList");
String tmpUserId = "";
String tmpUserNm = "";
int assetCnt = 0;
int imgCnt = 0;
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

	<table border="1">
	<tr>
		<th>관리자</th>
		<th>물품구분</th>
		<th>물품분류</th>
		<th>물품번호</th>
		<th>물품명</th>
		<th>취득일</th>
		<th>확인여부</th>
		<th>수량</th>
	</tr>
	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(list.totalRow, list.pageIdx, list.pageSize);
	for(int i=0; i<list.size(); i++){
		CommonMap idl = list.getMap(i);
		
		String userStr = idl.getString("user_no") + idl.getString("user_name");
		
		if (i == 0) {
			tmpUserId = userStr;
			tmpUserNm = idl.getString("user_name");
		}
		if (!tmpUserId.equals(userStr) && i > 0) {			
			%>
			<tr>
			<td colspan="8"><strong><%=tmpUserNm%> 총물품 <%=assetCnt%>건, 확인 <%=imgCnt%>건, 미확인 <%=(assetCnt-imgCnt)%></strong></td>
			</tr>
			<%
			
			tmpUserId = userStr;
			tmpUserNm = idl.getString("user_name");
			assetCnt = 0;
			imgCnt = 0;
		}
		
		assetCnt += idl.getInt("cnt");
		imgCnt += idl.getInt("img_cnt");
	%>
	<tr>
		<td><%=idl.getString("user_name")%></td>
		<td><%=idl.getString("asset_type")%></td>
		<td><%=idl.getString("asset_cate")%></td>
		<td><%=idl.getString("asset_no")%></td>
		<td><%=idl.getString("asset_name")%></td>
		<td><%=DateUtil.formatDate(idl.getString("aqusit_dt"),"-")%></td>
		<td><%=idl.getInt("img_cnt")>0?"O":"X"%></td>
		<td><%=idl.getString("cnt")%></td>		
	</tr>
	<%
		if ((i+1) == list.size()) {
			%>
			<tr>
			<td colspan="8"><strong><%=tmpUserNm%> 총물품 중 <%=assetCnt%>건, 확인 <%=imgCnt%>건, 미확인 <%=(assetCnt-imgCnt)%></strong></td>
			</tr>
			<%
		}
	}
	%>
	</table>
	
	