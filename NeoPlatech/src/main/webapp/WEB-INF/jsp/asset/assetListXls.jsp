<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "물품현황_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonList assetList = RequestUtil.getCommonList(request, "assetList");
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

	<table border="1" >
		<tr>
			<%
			CommonList dispMngList = (CommonList)session.getAttribute("dispMngList99");
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);
			%>
			<th><%=dispMng.getString("logical_name")%></th>
			<%
				}
			}
			%>
			<th>사진</th>
		</tr>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(assetList.totalRow, assetList.pageIdx, assetList.pageSize);
		for(int i=0; i<assetList.size(); i++){
			CommonMap asset = assetList.getMap(i);
			
			String inv_detail_seq = asset.getString("inv_year") + "-" + asset.getString("inv_no") + "-" + asset.getString("asset_no");
		%>
		<tr>
			<%
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);
					
					String val = asset.getString(dispMng.getString("physical_name"));
					String classVal = "";
					if ("1".equals(dispMng.getString("data_disp_type"))) {			//기본형
						classVal = "";
					} else if ("2".equals(dispMng.getString("data_disp_type"))) {	//문자형
						classVal = "l";
					} else if ("3".equals(dispMng.getString("data_disp_type"))) {	//숫자형
						classVal = "r";
						val = StringUtil.commaNum(val);
					} else if ("4".equals(dispMng.getString("data_disp_type"))) {	//날짜형
						classVal = "";
						val = DateUtil.formatDateTime(val, "-", ":");
					}
			%>
			<td><%=val%></td>
			<%
				}
			}
			%>
			<td>
				<% if (asset.getInt("asset_file_cnt") > 0) { %>
				<%=asset.getInt("asset_file_cnt")%>
				<% } %>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	