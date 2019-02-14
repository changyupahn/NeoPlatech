<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String posStatXlsDnAction = "/inventoryStat/positionStatListXls.do";
CommonList list = RequestUtil.getCommonList(request, "positionStatList");
float sum_total = 0;
float sum_tag_print_cnt = 0;
float sum_tag_noprint_cnt = 0;
float sum_img_cnt = 0;
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
		opener.fnPosStatPop();
	}
}
function fnPosStatXlsDn(){
	if (confirm("엑셀다운로드 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		var frm = opener.document.sForm;
		frm.action = "<%=posStatXlsDnAction%>";
		frm.target = "posStatPop";
		frm.submit();
	}
}
</script>
</head>
<body>

<div style="width:900px; padding:5px;">

	<h2 class="title_left">부서별 통계</h2>
	
	<div style="text-align:right; padding-bottom:5px;">
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnPosStatXlsDn()" ></span>
	</div>

	<table class="table-list" style="width:100%">
	<tr>
		<th width="16%" class="l">부서</th>
		<th width="12%" class="r">물품수량</th>
		<th width="12%" class="r">태그발행(건)</th>
		<th width="12%" class="r">미발행(건)</th>
		<th width="12%" class="r">사진(건)</th>
		<th width="12%" class="r">태그발행(%)</th>
		<th width="12%" class="r">미발행(%)</th>
		<th width="12%" class="r">사진(%)</th>
	</tr>
	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(list.totalRow, list.pageIdx, list.pageSize);
	for(int i=0; i<list.size(); i++){
		CommonMap idl = list.getMap(i);
		
		int total = idl.getInt("TOTAL");
		int tag_print_cnt = idl.getInt("TAG_PRINT_CNT");
		int tag_noprint_cnt = total - tag_print_cnt;
		int img_cnt = idl.getInt("IMG_CNT");
		int tag_print_per = Math.round( 1.0f * tag_print_cnt / total * 100 );
		int tag_noprint_per = Math.round( 1.0f * tag_noprint_cnt / total * 100 );
		int img_per = Math.round( 1.0f * img_cnt / total * 100 );
		
		sum_total += total;
		sum_tag_print_cnt += tag_print_cnt;
		sum_tag_noprint_cnt += tag_noprint_cnt;
		sum_img_cnt += img_cnt;
		sum_tag_print_per += tag_print_per;
		sum_tag_noprint_per += tag_noprint_per;
		sum_img_per += img_per;
	%>
	<tr>
		<td class="l"><%=idl.getString("USE_POSITION_NAME")%></td>
		<td class="r">*****<%//=total%></td>
		<td class="r">*****<%//=tag_print_cnt%></td>
		<td class="r">*****<%//=tag_noprint_cnt%></td>
		<td class="r">*****<%//=img_cnt%></td>
		<td class="r">*****<%//=tag_print_per%>%</td>
		<td class="r">*****<%//=tag_noprint_per%>%</td>
		<td class="r">*****<%//=img_per%>%</td>
	</tr>
	<%
	}
	%>
	<% if (list.size() > 0) { %>
	<tr>
		<td class="l"></td>
		<td class="r"><strong>*****<%//=sum_total%></strong></td>
		<td class="r"><strong>*****<%//=sum_tag_print_cnt%></strong></td>
		<td class="r"><strong>*****<%//=sum_tag_noprint_cnt%></strong></td>
		<td class="r"><strong>*****<%//=sum_img_cnt%></strong></td>
		<td class="r"><strong>*****<%//=Math.round(sum_tag_print_per / list.size())%>%</strong></td>
		<td class="r"><strong>*****<%//=Math.round(sum_tag_noprint_per / list.size())%>%</strong></td>
		<td class="r"><strong>*****<%//=Math.round(sum_img_per / list.size())%>%</strong></td>
	</tr>
	<% } %>
	</table>

</div>
	
</body>
</html>