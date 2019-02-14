<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonList addrList = RequestUtil.getCommonList(request, "addrList");
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequst");
%>
<%
for (int i=0; i<addrList.size(); i++) {
	CommonMap addr = addrList.getMap(i);
	String selected = "";
	if ("".equals(cmRequest.getString("addrSeq")) && i == 0) {
		selected = "selected=\"selected\"";
	} else if (addr.getString("addrSeq").equals(cmRequest.getString("addrSeq"))) {
		selected = "selected=\"selected\"";
	}
		
%>
<option value="<%=addr.getString("addrSeq")%>" <%=selected%>><%=addr.getString("title")%></option>
<%
}
%>