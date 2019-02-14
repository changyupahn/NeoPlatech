<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1300/kp1311Tab.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>
	<table border="0" cellspacing="0" cellpadding="0" summary="자산상세정보, 검수정보, 자산이력, ZEUS, E-TUBE 보기탭 입니다.">
	<tr>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu0">
			<a href="/kp1300/kp1311.do?assetSeq=<%=cmRequest.getString("assetSeq") %>"><b>자산상세정보</b></a>
		</td>
		<%-- <td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1300/kp1315.do?assetSeq=<%=cmRequest.getString("assetSeq") %>"><b>자산이력</b></a>
		</td> --%>
		<td height="20px" width="1x" bgcolor="#FFFFFF"> </td>
	</tr>
	</table>
