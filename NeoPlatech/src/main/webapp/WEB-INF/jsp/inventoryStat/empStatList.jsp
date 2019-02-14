<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String empStatXlsDnAction = "/inventoryStat/empStatListXls.do";
CommonList list = RequestUtil.getCommonList(request, "empStatList");
String tmpEmpId = "";
String tmpEmpNm = "";
int assetCnt = 0;
%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<script type="text/javascript">
function fnReload(){
	if (confirm("새로고침 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		opener.fnEmpStatPop();
	}
}
function fnEmpStatXlsDn(){
	if (confirm("엑셀다운로드 하시겠습니까? \n확인 후 10~20초 소요됩니다.")) {
		var frm = opener.document.sForm;
		frm.action = "<%=empStatXlsDnAction%>";
		frm.target = "empStatPop";
		frm.submit();
	}
}
</script>
</head>
<body>

<div style="width:900px; padding:5px;">

	<h2 class="title_left">사용자별 통계</h2>
	
	<div style="text-align:right; padding-bottom:5px;">
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnEmpStatXlsDn()" ></span>
	</div>

	<table class="table-list" style="width:100%">
	<tr>
		<th width="20%" class="l">사용자</th>
		<th width="20%" class="l">구분</th>
		<th width="25%" class="l">물품명</th>
		<th width="15%" class="l">물품번호</th>
		<th width="10%" class="l">취득일</th>
		<th width="10%" class="r">수량</th>
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
			<td class="r"><strong>*****<%//=assetCnt%></strong></td>
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
		<td class="r"><%=idl.getString("CNT")%></td>
	</tr>
	<%
		if ((i+1) == list.size()) {
			%>
			<tr>
			<td colspan="5"><strong><%=idl.getString("EMP_NAME")%> 총물품</strong></td>
			<td class="r"><strong>*****</strong></td>
			</tr>
			<%
		}
	}
	%>
	</table>
	
</div>
	
</body>
</html>