<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String posStatXlsDnAction = "/inventoryStat/deptStatListXls.do";
CommonList list = RequestUtil.getCommonList(request, "deptStatList");
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
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<script type="text/javascript">
function fnReload(){
	if (confirm("새로고침 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		opener.fnDeptStatPop();
	}
}
function fnDeptStatXlsDn(){
	if (confirm("엑셀다운로드 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		var frm = opener.document.sForm;
		frm.action = "<%=posStatXlsDnAction%>";
		frm.target = "deptStatPop";
		frm.submit();
	}
}
</script>
</head>
<body>

<div style="width:900px; padding:5px;">

	<h2 class="title_left">부서별 통계</h2>
	
	<div style="text-align:right; padding-bottom:5px;">
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnDeptStatXlsDn()" ></span>
	</div>

	<table class="table-list" style="width:100%">
	<tr>
		<th width="16%" class="l">부서</th>
		<th width="12%" class="r">조사대상(건)</th>
		<th width="12%" class="r">확인(건)</th>
		<th width="12%" class="r">미확인(건)</th>
		<th width="12%" class="r">실사율(%)</th>
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
		<td class="l"><%=idl.getString("dept_name")%></td>
		<td class="r"><%=inv_target_cnt%></td>
		<td class="r"><%=img_cnt%></td>
		<td class="r"><%=no_img_cnt%></td>
		<td class="r"><%=img_per%>%</td>
	</tr>
	<%
	}
	%>
	<% if (list.size() > 0) { %>
	<tr>
		<td class="l"></td>
		<td class="r"><strong><%=sum_inv_target_cnt%></strong></td>
		<td class="r"><strong><%=sum_img_cnt%></strong></td>
		<td class="r"><strong><%=sum_no_img_cnt%></strong></td>
		<td class="r"><strong><%=Math.round( 1.0f * sum_img_cnt / sum_inv_target_cnt * 100 )%>%</strong></td>
	</tr>
	<% } %>
	</table>

</div>
	
</body>
</html>