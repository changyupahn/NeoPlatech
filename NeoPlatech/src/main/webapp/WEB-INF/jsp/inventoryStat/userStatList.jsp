<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String userStatXlsDnAction = "/inventoryStat/userStatListXls.do";
CommonList list = RequestUtil.getCommonList(request, "userStatList");
String tmpUserId = "";
String tmpUserNm = "";
int assetCnt = 0;
int imgCnt = 0;
%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<script type="text/javascript">
function fnReload(){
	if (confirm("새로고침 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		opener.fnUserStatPop();
	}
}
function fnUserStatXlsDn(){
	if (confirm("엑셀다운로드 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		var frm = opener.document.sForm;
		frm.action = "<%=userStatXlsDnAction%>";
		frm.target = "userStatPop";
		frm.submit();
	}
}
</script>
</head>
<body>

<div style="width:900px; padding:5px;">

	<h2 class="title_left">관리자별 통계</h2>
	
	<div style="text-align:right; padding-bottom:5px;">
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnUserStatXlsDn()" ></span>
	</div>

	<table class="table-list" style="width:100%">
	<tr>
		<th width="7%" class="l">관리자</th>
		<th width="9%" class="l">물품구분</th>
		<th width="28%" class="l">물품분류</th>
		<th width="13%" class="l">물품번호</th>
		<th width="18%" class="l">물품명</th>
		<th width="10%" class="l">취득일</th>
		<th width="7%" class="r">확인여부</th>
		<th width="7%" class="r">수량</th>
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
		<td class="c"><%=idl.getInt("img_cnt")>0?"O":"X"%></td>
		<td class="r"><%=idl.getString("cnt")%></td>		
	</tr>
	<%
		if ((i+1) == list.size()) {
			%>
			<tr>
			<td colspan="8"><strong><%=tmpUserNm%> 총물품 <%=assetCnt%>건, 확인 <%=imgCnt%>건, 미확인 <%=(assetCnt-imgCnt)%></strong></td>
			</tr>
			<%
		}
	}
	%>
	</table>
	
</div>
	
</body>
</html>