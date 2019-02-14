<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonList labelList = RequestUtil.getCommonList(request, "labelList");
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequst");
%>
<%
for (int i=0; i<labelList.size(); i++) {
	CommonMap label = labelList.getMap(i);
	String selected = "";
	if ("".equals(cmRequest.getString("labelSeq")) && i == 0) {
		selected = "selected=\"selected\"";
	} else if (label.getString("labelSeq").equals(cmRequest.getString("labelSeq"))) {
		selected = "selected=\"selected\"";
	}
		
%>
<option value="<%=label.getString("labelSeq")%>" <%=selected%>><%=label.getString("labelTitle")%></option>
<%
}
%>