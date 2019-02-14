<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList assetPositionList = RequestUtil.getCommonList(request, "assetPositionList"); //부서
%>
<option value="">선택</option>
<option value="NULL" <%="NULL".equals(cmRequest.getString("sltValue"))?"selected=\"selected\"":""%>>NULL</option>