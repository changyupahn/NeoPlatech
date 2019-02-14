<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "재물조사목록_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
CommonList inventoryDetailList = RequestUtil.getCommonList(request, "inventoryDetailList");
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

	<table border="1">
		<tr>
			<%
			CommonList dispMngList = (CommonList)session.getAttribute("dispMngList21");
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);
					
					String as = "";
					if ("1".equals(dispMng.getString("column_type"))) {
						as = "ast.";
					} else if ("2".equals(dispMng.getString("column_type"))) {
						as = "adi.";
					}
					
					String classVal = "";
					if ("1".equals(dispMng.getString("data_disp_type"))) {			//기본형
						classVal = "";
					} else if ("2".equals(dispMng.getString("data_disp_type"))) {	//문자형
						classVal = "l";
					} else if ("3".equals(dispMng.getString("data_disp_type"))) {	//숫자형
						classVal = "r";
					} else if ("4".equals(dispMng.getString("data_disp_type"))) {	//날짜형
						classVal = "";
					}
			%>
			<th><%=dispMng.getString("logical_name")%></th>
			<%
				}
			}
			%>
			<th>변경규격</th>
			<th>변경기관명</th>
			<th>변경부서명</th>
			<th>변경관리자명</th>
			<%-- <th>변경소재지명</th> --%>
			<th>변경호실</th>
			<th>비고</th>
			<th>태그출력회수</th>
			<th>재물조사대상</th>
			<th>조사일시</th>
			<th>사진</th>
			<th>수정일시</th>
		</tr>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(inventoryDetailList.totalRow, inventoryDetailList.pageIdx, inventoryDetailList.pageSize);
		for(int i=0; i<inventoryDetailList.size(); i++){
			CommonMap idl = inventoryDetailList.getMap(i);
			
			//String inv_detail_seq = idl.getString("inv_year") + "-" + idl.getString("inv_no") + "-" + idl.getString("asset_no");
		%>
		<tr>
			<%
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);
					
					String val = idl.getString(dispMng.getString("physical_name"));
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
			<td><%=idl.getString("cng_asset_size")%></td>
			<td><%=idl.getString("cng_org_name")%></td>
			<td><%=idl.getString("cng_dept_name")%></td>			
			<td><%=idl.getString("cng_user_name")%></td>
			<%-- <td><%=idl.getString("cng_pos_name")%></td> --%>
			<td><%=idl.getString("cng_hosil")%></td>
			<td><%=idl.getString("cng_remark")%></td>
			<td><%=idl.getString("tag_print_cnt")%></td>
			<td><%=idl.getString("inv_target_yn")%></td>
			<td><%=DateUtil.formatDateTime(idl.getString("tag_read_dt"),"-",":")%></td>
			<td><%=idl.getString("asset_file_cnt")%></td>
			<td><%=DateUtil.formatDateTime(idl.getString("update_dt"),"-",":")%></td>
		</tr>
		<%
		}
		%>
	</table>