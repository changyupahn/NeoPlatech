<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
CommonList viewDataExtList = RequestUtil.getCommonList(request, "viewDataExtList");
CommonMap viewDataIn = RequestUtil.getCommonMap(request, "viewDataIn");
CommonList dataList = RequestUtil.getCommonList(request, "dataList");

String fileName = "반출증_"+ DateUtil.getFormatDate() +".hwp";
fileName = new String(fileName.getBytes("euc-kr"),"8859_1");
%>
<%
//******************************한글(hwp)********************************
//한글(hwp)로 다운로드/실행, filename에 저장될 파일명을 적어준다.
response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
response.setHeader("Content-Description", "JSP Generated Data");

//↓ 이걸 풀어주면 열기/저장 선택창이 뜨는 게 아니라 그냥 바로 저장된다.
response.setContentType("application/hwp");
//*********************************************************************
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>

<HEAD>
<META NAME="Generator" CONTENT="Haansoft HWP 8.0.0.466">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<TITLE></TITLE>
<STYLE>
<!--
P.HStyle0, LI.HStyle0, DIV.HStyle0
	{style-name:"바탕글"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle1, LI.HStyle1, DIV.HStyle1
	{style-name:"본문"; margin-left:17.5pt; margin-right:17.5pt; margin-top:4.2pt; margin-bottom:4.2pt; text-align:justify; text-indent:0.0pt; line-height:165%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.5pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle2, LI.HStyle2, DIV.HStyle2
	{style-name:"개요 1"; margin-left:7.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle3, LI.HStyle3, DIV.HStyle3
	{style-name:"개요 2"; margin-left:17.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle4, LI.HStyle4, DIV.HStyle4
	{style-name:"개요 3"; margin-left:27.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle5, LI.HStyle5, DIV.HStyle5
	{style-name:"개요 4"; margin-left:37.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle6, LI.HStyle6, DIV.HStyle6
	{style-name:"개요 5"; margin-left:47.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle7, LI.HStyle7, DIV.HStyle7
	{style-name:"개요 6"; margin-left:57.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle8, LI.HStyle8, DIV.HStyle8
	{style-name:"개요 7"; margin-left:67.4pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-7.4pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle9, LI.HStyle9, DIV.HStyle9
	{style-name:"쪽 번호"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:한양견고딕; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle10, LI.HStyle10, DIV.HStyle10
	{style-name:"머리말"; margin-left:0.0pt; margin-right:10.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:right; text-indent:0.0pt; line-height:150%; font-size:9.0pt; font-family:한양중고딕; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle11, LI.HStyle11, DIV.HStyle11
	{style-name:"각주"; margin-left:13.2pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.2pt; line-height:130%; font-size:9.5pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle12, LI.HStyle12, DIV.HStyle12
	{style-name:"그림캡션"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:한양중고딕; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle13, LI.HStyle13, DIV.HStyle13
	{style-name:"표캡션"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:한양중고딕; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle14, LI.HStyle14, DIV.HStyle14
	{style-name:"수식캡션"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:한양중고딕; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle15, LI.HStyle15, DIV.HStyle15
	{style-name:"찾아보기"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle16, LI.HStyle16, DIV.HStyle16
	{style-name:"선그리기"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
-->
</STYLE>
</HEAD>

<BODY>

<P CLASS=HStyle0 STYLE='text-align:left;line-height:180%;'>[별지 제5호 서식] </P>

<P CLASS=HStyle0><BR></P>

<P CLASS=HStyle0 STYLE='text-align:center;line-height:150%;'><SPAN STYLE='font-size:15.0pt;font-weight:"bold";line-height:150%;'>자&nbsp;&nbsp; 산&nbsp;&nbsp; 반 <SPAN style='HWP-TAB:1;'>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN>출 <SPAN style='HWP-TAB:1;'>&nbsp;&nbsp;&nbsp;</SPAN>증</SPAN></P>

<P CLASS=HStyle0 STYLE='text-align:center;line-height:100%;'>
<TABLE border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;border:none;'>
<TR>
	<TD width="54" height="57" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'>순번</P>
	</TD>
	<TD colspan="2" width="98" height="57" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'>자산코드</P>
	</TD>
	<TD colspan="2" width="244" height="57" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'>품명</P>
	</TD>
	<TD colspan="2" width="72" height="57" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'>단위</P>
	</TD>
	<TD width="67" height="57" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'>수량</P>
	</TD>
	<TD width="97" height="57" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'>반입 예정일</P>
	</TD>
</TR>
<%
for (int i=0; i<dataList.size(); i++) {
	CommonMap data = dataList.getMap(i);
%>
<TR>
	<TD width="54" height="40" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><%=(i+1)%></P>
	</TD>
	<TD colspan="2" width="98" height="40" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><%=data.getString("assetNo")%></P>
	</TD>
	<TD colspan="2" width="244" height="40" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:100%;'><SPAN STYLE='font-size:9.0pt;line-height:100%;'><%=data.getString("assetName")%></SPAN></P>
	</TD>
	<TD colspan="2" width="72" height="40" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><%=data.getString("assetUnitCd")%></P>
	</TD>
	<TD width="67" height="40" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><%=data.getString("assetCnt")%></P>
	</TD>
	<TD width="97" height="40" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><%=viewData.getString("expInDt")%></P>
	</TD>
</TR>
<%
}
%>
<TR>
	<TD colspan="9" width="631" height="128" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='margin-left:62.0pt;text-indent:-62.0pt;'>
		&nbsp;반출처 : <%=viewData.getString("outPlace")%>
	</P>
	<P CLASS=HStyle0 STYLE='margin-left:62.0pt;text-indent:-62.0pt;'>
		&nbsp;반출사유 : <%=viewData.getString("reason")%>
	</P>
	</TD>
</TR>
<TR>
	<TD rowspan="3" colspan="2" width="74" height="193" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>자산반출</P>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>확&nbsp;&nbsp;&nbsp; 인</P>
	</TD>
	<TD colspan="2" width="138" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>반출자(자산사용자)</P>
	</TD>
	<TD colspan="2" width="227" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>소속:&nbsp;<%=viewData.getString("rqstDeptName")%></P>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>성명:&nbsp;<%=viewData.getString("rqstUserName")%>
		<%
		int loopCnt = 6;

		if (viewData.getString("rqstUserName").length() <= 6) {
			loopCnt = loopCnt - viewData.getString("rqstUserName").length();
		}

		for (int i=0; i<6; i++) {
		%>
		&nbsp;&nbsp;
		<%
		}
		%>
		&nbsp;(인)
	</P>
	</TD>
	<TD colspan="3" width="193" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:right;line-height:200%;'><%=viewData.getString("outDtStr")%></P>
	</TD>
</TR>
<TR>
	<TD colspan="2" width="138" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>관리책임자</P>
	</TD>
	<TD colspan="2" width="227" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>소속:&nbsp;<%=viewData.getString("rqstDeptName")%></P>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>성명:&nbsp;<%=viewData.getString("rqstUserName")%>
		<%
		loopCnt = 6;

		if (viewData.getString("rqstTopUserName").length() <= 6) {
			loopCnt = loopCnt - viewData.getString("rqstTopUserName").length();
		}

		for (int i=0; i<6; i++) {
		%>
		&nbsp;&nbsp;
		<%
		}
		%>
		&nbsp;(인)
	</P>
	</TD>
	<TD colspan="3" width="193" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:right;line-height:200%;'><%=viewData.getString("outDtStr")%></P>
	</TD>
</TR>
<TR>
	<TD colspan="2" width="138" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>경비근무자</P>
	</TD>
	<TD colspan="2" width="227" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>성명:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (인)</P>
	</TD>
	<TD colspan="3" width="193" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:right;line-height:200%;'><%=viewData.getString("outDtStr")%></P>
	</TD>
</TR>
<TR>
	<TD colspan="2" width="74" height="20" valign="middle" style='border-left:none;border-right:none;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>&nbsp;</P>
	</TD>
	<TD colspan="2" width="138" height="20" valign="middle" style='border-left:none;border-right:none;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>&nbsp;</P>
	</TD>
	<TD colspan="2" width="227" height="20" valign="middle" style='border-left:none;border-right:none;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>&nbsp;</P>
	</TD>
	<TD colspan="3" width="193" height="20" valign="middle" style='border-left:none;border-right:none;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:right;line-height:200%;'>&nbsp;</P>
	</TD>
</TR>
<TR>
	<TD colspan="2" width="74" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>자산반입</P>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>확&nbsp;&nbsp;&nbsp; 인</P>
	</TD>
	<TD colspan="2" width="138" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;line-height:200%;'>반출자(자산사용자)</P>
	</TD>
	<TD colspan="2" width="227" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>소속:&nbsp;</P>
	<P CLASS=HStyle0 STYLE='line-height:200%;'>성명:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (인)</P>
	</TD>
	<TD colspan="3" width="193" height="64" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:right;line-height:200%;'>년&nbsp;&nbsp;&nbsp;&nbsp; 월&nbsp;&nbsp;&nbsp;&nbsp; 일</P>
	</TD>
</TR>
</TABLE></P>

<P CLASS=HStyle0 STYLE='line-height:100%;'><SPAN STYLE='font-size:6.0pt;line-height:100%;'><BR></SPAN></P>

<P CLASS=HStyle0>※ 자산반출자는 실제 자산 사용자로서 반드시 정규직원이 서명하여야 함</P>

<P CLASS=HStyle0>※ 자산반입 확인은 당초 자산반출에 서명한 자가 확인 서명하여야 함</P>

</BODY>

</HTML>