<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList commonCodeList = RequestUtil.getCommonList(request, "commonCodeList");
String sltValue = cmRequest.getString("sltValue");
String lang = cmRequest.getString("lang");
%>
<%
for(int i=0; i<commonCodeList.size(); i++){
	CommonMap commonCode = commonCodeList.getMap(i);
	String selected = sltValue.equals(commonCode.getString("code")) ? "selected=\"selected\"" : "";
%>
	<option value="<%=commonCode.getString("code")%>" <%=selected%>><%=commonCode.getString("codeName")%></option>
<%
}
%>