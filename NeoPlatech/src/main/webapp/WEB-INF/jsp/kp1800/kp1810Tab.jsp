<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1800/kp1810Tab.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>
	<table border="0" cellspacing="0" cellpadding="0" summary="ZEUS 장비목록, 월별작성현황, 장비별운영현황 보기탭 입니다.">
	<tr>
		<td height="20px" width="140px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu0">
			<a href="/kp1800/kp1810.do"><b>장비목록</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="140px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1800/kp1811.do"><b>월별작성현황</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="140px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1800/kp1812.do"><b>장비별운영현황</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF"> </td>
	</tr>
	</table>
