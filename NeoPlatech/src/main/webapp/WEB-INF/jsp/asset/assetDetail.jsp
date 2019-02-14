<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/asset/select.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap view = RequestUtil.getCommonMap(request, "asset");
CommonList assetHistoryList = RequestUtil.getCommonList(request, "assetHistoryList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

</head>
<body>

	<h2 class="title_left"><spring:message code="menu.assets.detail"/></h2>
	
	<table class="table-search" style="width:100%">
	<tr>
		<th width="10%"><spring:message code="column.assets.image.list"/></th>
		<td>
			<c:import url="/asset/getImageList.do" charEncoding="utf-8">
			<c:param name="param_asset_no" value='<%=view.getString("asset_no")%>' />
			</c:import>
		</td>
		<td width="25%" valign="bottom" style="padding:0 15px 15px 0;text-align:right;">
			<script type="text/javascript">
			function fnImgUp(){
				var frm = document.imgForm;
				frm.submit();
			}
			</script>
			<form name="imgForm" method="post" action="/asset/image/uploadWeb.do" enctype="multipart/form-data">
			<input type="hidden" name="asset_no" value="<%=view.getString("asset_no")%>" />
			<input type="file" name="file" style="height:18px;" />
			<span class="button"><input type="button" value="<spring:message code="button.image.new"/>" onclick="fnImgUp();"></span>
			</form>
		</td>
	</tr>	
	</table>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	
	<table id="tabCont0" class="table-search" style="width:100%">
	<colgroup>
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
	</colgroup>
	<tr>
	<%
	CommonList dispMngList = RequestUtil.getCommonList(request, "dispMngList97");
	if (dispMngList != null) {
		for (int k=0; k<dispMngList.size(); k++) {
			CommonMap dispMng = dispMngList.getMap(k);
			
			String tit = dispMng.getString("logical_name");
			String val = view.getString(dispMng.getString("physical_name"));
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
	<th><%=tit%></th>
	<td><%=val%></td>
	<%
			if ((k+1) % 4 == 0) {
				out.print("</tr><tr>");
			}
		}
		for (int i=0; i<(4-(dispMngList.size()%4)); i++) {
			out.print("<th></th><td></td>");
		}
	}
	%>
	</tr>
	</table>
	
	<h2 class="title_left"><spring:message code="menu.inventory.detail.list"/></h2>
	<table class="table-line" style="width:100%;">
	<colgroup>
		<col width="30%" />
		<col width="70%" />
	</colgroup>
	<tr>
		<th><spring:message code="column.type"/></th>
		<th><spring:message code="column.inventory.tagreaddt"/></th>
	</tr>
	<% if(assetHistoryList.size() == 0){ %>
	<tr>
		<td colspan="50"><spring:message code="common.no.list"/></td>
	</tr>
	<% } %>
	<%
	int pagingFristNo = PagingUtil.getPagingFristNo(assetHistoryList.totalRow, assetHistoryList.pageIdx, assetHistoryList.pageSize);
	for(int i=0; i<assetHistoryList.size(); i++){
		CommonMap assetHistory = assetHistoryList.getMap(i);
		
		String title = assetHistory.getString("inv_year") + "," + assetHistory.getString("inv_no") + "," + assetHistory.getString("inv_type_name");
	%>
	<tr>
		<td><spring:message code="column.inventory.main.title" arguments="<%=title%>" /></td>
		<td><%=DateUtil.formatDateTime(assetHistory.getString("tag_read_dt"), "-", ":", 12)%></td>
	</tr>
	<%
	}
	%>
	</table>
	
	<div class="buttonDiv">
		<span class="button"><input type="button" value="<spring:message code="button.close"/>" onclick="javascript:self.close();"></span>
	</div>
	
	</form>
	
</body>
</html>

		

