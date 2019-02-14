<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1300/kp1313AsTab.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>
	<table border="0" cellspacing="0" cellpadding="0" summary="유지보수일지등록, 유지보수일지목록 보기탭 입니다.">
	<tr>
		<td height="20px" width="130px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu0">
			<a href="/kp1300/kp1313AsWrite.do?assetSeq=<%=cmRequest.getString("assetSeq")%>"><b>유지보수일지 등록</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="130px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1300/kp1313As.do?operSeq=<%=cmRequest.getString("operSeq")%>&assetSeq=<%=cmRequest.getString("assetSeq")%>"><b>유지보수일지 목록</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF"> </td>
	</tr>
	</table>
