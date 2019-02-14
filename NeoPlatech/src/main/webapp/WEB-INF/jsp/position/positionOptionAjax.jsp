<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList positionList01 = RequestUtil.getCommonList(request, "positionList01");
CommonList positionList02 = RequestUtil.getCommonList(request, "positionList02");
CommonList positionList03 = RequestUtil.getCommonList(request, "positionList03");
String position_tcode_01 = RequestUtil.getString(request, "position_tcode_01");
String position_tcode_02 = RequestUtil.getString(request, "position_tcode_02");
String position_tcode_03 = RequestUtil.getString(request, "position_tcode_03");
%>
<select id="sUsePositionCode1" name="sUsePositionCode1" style="width:130px;" onchange="fnSetPosition(1);">
	<option value="">선택</option>
	<option value="NULL" <%="NULL".equals(position_tcode_01)?"selected=\"selected\"":""%>>NULL</option>
	<%
	for (int i=0; i<positionList01.size(); i++) {
		CommonMap position = positionList01.getMap(i);
	%>
	<%
	}
	%>
</select>
<select id="sUsePositionCode2" name="sUsePositionCode2" style="width:130px;" onchange="fnSetPosition(2);">
	<option value="">선택</option>
	<%
	for (int i=0; i<positionList02.size(); i++) {
		CommonMap position = positionList02.getMap(i);
	%>
	<%
	}
	%>
</select>
<select id="sUsePositionCode3" name="sUsePositionCode3" style="width:130px;" onchange="fnSetPosition(3);">
	<option value="">선택</option>
	<%
	for (int i=0; i<positionList03.size(); i++) {
		CommonMap position = positionList03.getMap(i);
	%>
	<%
	}
	%>
</select>