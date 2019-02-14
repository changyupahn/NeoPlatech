<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String newRegisterStatXlsDnAction = "/inventoryStat/newRegisterStatListXls.do";
CommonList list = RequestUtil.getCommonList(request, "newRegisterStatList");
String asset_no = "";
String dept_name = "";
String asset_name = "";
int asset_cnt = 0;
%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<script type="text/javascript">
function fnReload(){
	if (confirm("새로고침 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		opener.fnNewRegisterStatPop();
	}
}
function fnNewRegisterStatXlsDn(){
	if (confirm("엑셀다운로드 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		var frm = opener.document.sForm;
		frm.action = "<%=newRegisterStatXlsDnAction%>";
		frm.target = "newRegisterStatPop";
		frm.submit();
	}
}
</script>
</head>
<body>

<div style="width:900px; padding:5px;">

	<h2 class="title_left">신규등록내역</h2>
	
	<div style="text-align:right; padding-bottom:5px;">
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnNewRegisterStatXlsDn()" ></span>
	</div>

	<table class="table-list" style="width:100%">
	<tr>
		<th width="20%" class="l">물품코드</th>
		<th width="20%" class="l">부서명</th>
		<th width="20%" class="l">물품명</th>
		<th width="20%" class="l">물품수</th>
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