<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "동영상매뉴얼 - 시스템관리";
String curAction = "/kp1900/kp1940.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 280; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%=pageTitle%></h2>

	<table border="0" cellspacing="0" cellpadding="0" summary="자산상세정보, 검수정보, 자산이력, ZEUS, E-TUBE 보기탭 입니다.">
	<tr>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu0">
			<a href="/kp1900/kp1940.do"><b>기준정보관리</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1900/kp1941.do"><b>자산취득관리</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1900/kp1942.do"><b>자산유지관리</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1900/kp1943.do"><b>재물조사관리</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1900/kp1944.do"><b>시스템관리</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center" id="tabMenu1">
			<a href="/kp1900/kp1945.do"><b>어플메뉴얼</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF"> </td>
	</tr>
	</table>

	<div>
		<video controls src="/manual/05.mp4" poster="/manual/05.png"> <%=pageTitle%> </video>
	</div>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



