<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1300/kp1310Search.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

if ("2".equals(cmRequest.getString("menuDiv"))) {
	viewData.put("assetTypeCd", "BU");
}

int idx = 0;
int colcnt = cmRequest.getInt("colcnt");
int colmax = colcnt;
%>
	<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
	</colgroup>
	<tr>
	<%
	CommonList dispMngList = RequestUtil.getCommonList(request, "dispAssetList");
	if (dispMngList != null) {
		for (int k=0; k<dispMngList.size(); k++) {
			CommonMap dispMng = dispMngList.getMap(k);
			//out.println(", '" + dispMng.getString("logicalName") + "'");
			String logicalName = dispMng.getString("logical_name");
			String physicalName = dispMng.getString("physical_name");
			String dataDispType = dispMng.getString("data_disp_type");
			String align = dispMng.getString("default_align", "center");
			%>
			<th><%=logicalName%> :</th>
			<td>
				<%
				if ("TEXT".equalsIgnoreCase(dispMng.getString("data_disp_type"))) { //문자형
					out.print(viewData.getString(physicalName));
				} else if ("NUMBER".equalsIgnoreCase(dispMng.getString("data_disp_type"))) {	//숫자형
					out.print(StringUtil.commaNum(viewData.getString(physicalName)));
				} else if ("DATE".equalsIgnoreCase(dispMng.getString("data_disp_type"))) {	//날짜형
					out.print(DateUtil.formatDateTime(viewData.getString(physicalName), "-", ":", 14));
				}
				%>
			</td>
			<% if ((k+1) < dispMngList.size()) { %>
			<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
			<% } %>
			<%
		}
	}
	%>
	<% idx++; if (idx % colcnt > 0) { %><td width="340px" colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>
	</tr>
	</table>
