<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1800/kp1813Tab.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>
	<table border="0" cellspacing="0" cellpadding="0" summary="운영일지등록, 운영일지목록 보기탭 입니다.">
	<tr>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu0">
			<a href="/kp1800/kp1813Write.do?equipId=<%=cmRequest.getString("equipId")%>&assetSeq=<%=cmRequest.getString("assetSeq")%>"><b>운영일지 등록</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1800/kp1813.do?equipId=<%=cmRequest.getString("equipId")%>&equipNo=<%=cmRequest.getString("equipNo")%>&assetSeq=<%=cmRequest.getString("assetSeq")%>"><b>운영일지 목록</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF"> </td>
	</tr>
	</table>
