<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/inventory/selectList.do";
String detailAction = "/inventory/selectDetailList.do";
String writeFormAction = "/inventory/writeForm.do";
String deleteAction = "/inventory/deleteProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList inventoryList = RequestUtil.getCommonList(request, "inventoryList");
CommonList inventoryYearList = RequestUtil.getCommonList(request, "inventoryYearList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=curAction%>";
	frm.target = "_self";
	frm.submit();
}
function fnReload(){
	fnSearch();
}
function fnNew(){
	var oWidth = 750;
	var oHeight = 520;
	var oLeft = ($(window).width() - oWidth) / 2;
	var oTop = ($(window).height() - oHeight) / 2;
	
	var invenWrite = fnOpenPop("<%=writeFormAction%>", "invenWrite", oWidth, oHeight, "left="+oLeft+",top="+oTop+",menubar=no,status=no,titlebar=no,scrollbars=yes,location=no,toolbar=no,resizable=yes");
	invenWrite.focus();
}
function fnDelete(invYear, invNo){
	if (confirm("<spring:message code="alert.confirm.delete"/>")) {
		var frm = document.sForm;
		frm.inv_year.value = invYear;
		frm.inv_no.value = invNo;
		frm.action = "<%=deleteAction%>";
		frm.target = "_self";
		frm.submit();
	}
}
function fnDetail(invYear, invNo){
	var frm = document.dForm;
	frm.sInvYear.value = invYear;
	frm.sInvNo.value = invNo;
	frm.action = "<%=detailAction%>";
	frm.target = "_self";
	frm.submit();	
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><spring:message code="menu.inventory.list"/></h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	
	<input type="hidden" name="inv_year" value="" />
	<input type="hidden" name="inv_no" value="" />
	
	<table class="table-search" style="width:100%">
	<colgroup>
		<col width="100px" />
		<col width="240px" />
		<col width="" />
	</colgroup>
	<tr>
	<th><spring:message code="column.inventory.year"/></th>
	<td>
		<select id="sInvYear" name="sInvYear" style="width:100px;">
			<option value=""><spring:message code="common.select"/></option>
			<%
			for (int i=0; i<inventoryYearList.size(); i++) {
				CommonMap iy = inventoryYearList.getMap(i);
			%>
			<option value="<%=iy.getString("inv_year")%>" <%=iy.getString("inv_year").equals(cmRequest.getString("sInvYear"))?"selected":""%>><%=iy.getString("inv_year")%></option>
			<%
			}
			%>
		</select>
	</td>
	<th class="l" style="height:43px;">
		<span class="button"><input type="submit" value="<spring:message code="button.search"/>" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.create.new"/>" onclick="fnNew();"></span>
	</th>
	</tr>
	</table>

	<table class="table-line" style="width:100%" >	
	<colgroup>
		<col width="15%" />
		<col width="15%" />
		<col width="24%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col" class="title" nowrap><spring:message code="column.inventory.type"/></th>
			<th scope="col" class="title" nowrap><spring:message code="column.inventory.basicdt"/></th>
			<th scope="col" class="title" nowrap><spring:message code="column.inventory.summary"/></th>			
			<th scope="col" class="title" nowrap><spring:message code="column.inventory.autosync"/></th>
			<th scope="col" class="title" nowrap><spring:message code="column.inventory.targetcnt"/></th>
			<th scope="col" class="title" nowrap><spring:message code="common.manage"/></th>
		</tr>
	</thead>
	<tbody>
		<% if(inventoryList.size() == 0){ %>
		<tr>
			<td colspan="50"><spring:message code="common.no.list"/></td>
		</tr>
		<% } %>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(inventoryList.totalRow, inventoryList.pageIdx, inventoryList.pageSize);
		for(int i=0; i<inventoryList.size(); i++){
			CommonMap inventory = inventoryList.getMap(i);
			
			String title = inventory.getString("inv_year") + "," + inventory.getString("inv_no") + "," + inventory.getString("inv_type_name");
			
			String autoSyncYn = "N";
			if (inventoryList.pageIdx == 1 && i == 0) {
				//가장 최신 재물조사관리만 자동동기화 되므로, 데이터는 건드리지 않고 조회할때만 값을 가공해서 보여줌.
				autoSyncYn = inventory.getString("auto_sync_yn", "N");
			}
		%>
		<tr>
			<td>
				<spring:message code="column.inventory.main.title" arguments="<%=title%>" /></td>
			<td>
				<%=DateUtil.formatDateTime(inventory.getString("inv_start_dt"), "-", ":")%></td>
			<td>
				<%=inventory.getString("inv_summary")%></td>
			<td>
				<%=autoSyncYn%></td>
			<td>
				<%=inventory.getString("target_count")%> / <%=inventory.getString("total_count") %></td>
			<td>
				<span class="button"><input type="button" value="<spring:message code="button.detail"/>" onclick="fnDetail('<%=inventory.getString("inv_year")%>','<%=inventory.getString("inv_no")%>');"></span>
				<span class="button"><input type="button" value="<spring:message code="button.delete"/>" onclick="fnDelete('<%=inventory.getString("inv_year")%>','<%=inventory.getString("inv_no")%>');"></span>
			</td>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>
	
	<table style="width:100%;border-collapse:collapse;border:0;">
	<tr>
	<td width="">
		<div class="paginate">
		<!-- pageing -->
		<%=PagingUtil.PagingDataB(inventoryList,"sForm",curAction)%>
		<!-- pageing -->
	</div>
	</td>
	<td width="150px" class="r">
		<select id="pageSize" name="pageSize" onchange="fnSearch()" style="border:1px solid gray;">
		<option value="15" <%="15".equals(cmRequest.getString("pageSize","15"))?"selected":""%>><spring:message code="count.paging" arguments="15" /></option>
		<option value="30" <%="30".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="30" /></option>
		<option value="50" <%="50".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="50" /></option>
		<option value="100" <%="100".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="100" /></option>
		</select>
	</td>
	</tr>
	</table>
	
	</form>
	
<form name="dForm" method="post" action="<%=detailAction%>">
<input type="hidden" name="sInvYear" value="" />
<input type="hidden" name="sInvNo" value="" />
</form>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

