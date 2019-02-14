<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String excelFileName = "물품분류별통계_"+ DateUtil.getFormatDate() +".xls";
	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
	response.setHeader("Content-type","application/vnd.ms-excel; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename="+excelFileName+";");
	response.setHeader("Content-Description","JSP Generated Data");
%>
<%
String assetCateStatXlsDnAction = "/inventoryStat/assetCateStatListXls.do";
CommonList list = RequestUtil.getCommonList(request, "assetCateStatList");
float sum_total = 0;
float sum_inv_target_cnt = 0;
float sum_tag_print_cnt = 0;
float sum_tag_noprint_cnt = 0;
float sum_img_cnt = 0;
float sum_no_img_cnt = 0;
float sum_tag_print_per = 0;
float sum_tag_noprint_per = 0;
float sum_img_per = 0;
%>
<style type="text/css">
td.str {mso-number-format:\@}
</style>

	<table border="1">
	<tr>
		<th>호실</th>
		<th>조사대상(건)</th>
		<th>확인(건)</th>
		<th>미확인(건)</th>
		<th>실사율(%)</th>
	</tr>
	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(list.totalRow, list.pageIdx, list.pageSize);
	for(int i=0; i<list.size(); i++){
		CommonMap idl = list.getMap(i);
		
		int total = idl.getInt("total");
		int inv_target_cnt = idl.getInt("inv_target_cnt");
		int tag_print_cnt = idl.getInt("tag_print_cnt");
		int tag_noprint_cnt = inv_target_cnt - tag_print_cnt;
		int img_cnt = idl.getInt("img_cnt");
		int no_img_cnt = idl.getInt("no_img_cnt");
		int tag_print_per = Math.round( 1.0f * tag_print_cnt / inv_target_cnt * 100 );
		int tag_noprint_per = Math.round( 1.0f * tag_noprint_cnt / inv_target_cnt * 100 );
		int img_per = Math.round( 1.0f * img_cnt / inv_target_cnt * 100 );
		
		sum_total += total;
		sum_inv_target_cnt += inv_target_cnt;
		sum_tag_print_cnt += tag_print_cnt;
		sum_tag_noprint_cnt += tag_noprint_cnt;
		sum_img_cnt += img_cnt;
		sum_no_img_cnt += no_img_cnt;
		sum_tag_print_per += tag_print_per;
		sum_tag_noprint_per += tag_noprint_per;
		sum_img_per += img_per;
	%>
	<tr>
		<td><%=idl.getString("asset_cate")%></td>
		<td><%=inv_target_cnt%></td>
		<td><%=img_cnt%></td>
		<td><%=no_img_cnt%></td>
		<td><%=img_per%>%</td>
	</tr>
	<%
	}
	%>
	<% if (list.size() > 0) { %>
	<tr>
		<td></td>
		<td><strong><%=sum_inv_target_cnt%></strong></td>
		<td><strong><%=sum_img_cnt%></strong></td>
		<td><strong><%=sum_no_img_cnt%></strong></td>
		<td><strong><%=Math.round( 1.0f * sum_img_cnt / sum_inv_target_cnt * 100 )%>%</strong></td>
	</tr>
	<% } %>
	</table>