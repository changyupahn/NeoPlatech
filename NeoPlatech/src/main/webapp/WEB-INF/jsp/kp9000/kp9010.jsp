<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "품목코드";
String curAction = "/kp9000/kp9010.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList dataList = RequestUtil.getCommonList(request, "dataList");
CommonList assetTypeCdList = RequestUtil.getCommonList(request, "assetTypeCdList");

%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<link type="text/css" rel="stylesheet" href="/common/jstree/_docs/syntax/!style.css"/>
<script type="text/javascript" src="/common/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/common/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/jstree/_docs/syntax/!script.js"></script>

<script type="text/javascript" src="/common/js/kp9010.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.target = "_self";
	frm.submit();
}
$(document).ready(function(){
	fnInitTreeData();
	fnInitTree('N');
});
$(window).resize(function(){
	fnInitSize();
});
</script>

</head>
<body>

	<h2 class="title_left"><%= pageTitle %></h2>

	<table style="width:100%; border-collapse:collapse; height:1000px;">
	<tr>
	<td valign="top">
		<div id="treeSearch">
			<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false;">
			<table width="100%" class="table-search" border="0">
			<colgroup>
				<col width="100px" />
				<col width="" />
			</colgroup>
			<tr>
			<th>검색</th>
			<td>
				<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
				<span class="button"><input type="submit" value="검색" onclick="fnSearch()" ></span>
				<span class="button"><input type="button" value="펼치기" onclick="fnInitTree('Y')"></span>
				<span class="button"><input type="button" value="접기" onclick="fnInitTree('N')"></span>
			</td>
			</tr>
			</table>
			</form>
		</div>
		<div id="treeDiv" style="overflow:scroll; width:500px; height:600px; padding:10px;">
			<div id="tree"></div>
		</div>
	</td>
	</tr>
	</table>

	<div id="treeData">
		<input type="hidden" name="kp9010TreeData" value="0000|품목코드||0000|Y||||0" />

		<%
		for (int i=0; i<assetTypeCdList.size(); i++) {
			CommonMap atc = assetTypeCdList.getMap(i);
			String seq = atc.getString("code");
			String codeName = atc.getString("codeName");
			String pseq = "0000";
			String code = atc.getString("code");
			String useYn = "Y";
			String usefulLife = "";
			String depreCd = "";
			String assetTypeCd = atc.getString("code");
			%>
			<input type="hidden" name="kp9010TreeData" value="<%=seq%>|<%=codeName%>|<%=pseq%>|<%=code%>|<%=useYn%>|<%=usefulLife%>|<%=depreCd%>|<%=assetTypeCd%>|0" />
			<%
		}
		%>

		<%
		for(int i=0; i<dataList.size(); i++){
			CommonMap tree = dataList.getMap(i);
			String seq = tree.getString("itemSeq");
			String code = tree.getString("itemCd");
			String codeName = "[" + code + "] " + tree.getString("itemName");
			String pseq = tree.getString("parentItemSeq").trim();
			String useYn = tree.getString("useYn");
			String usefulLife = tree.getString("usefulLife");
			String depreCd = tree.getString("depreCd");
			String assetTypeCd = tree.getString("assetTypeCd");

			if ("0000".equals(code)) {
				codeName = tree.getString("itemName");
			}
		%>
		<input type="hidden" name="kp9010TreeData" value="<%=seq%>|<%=codeName%>|<%=pseq%>|<%=code%>|<%=useYn%>|<%=usefulLife%>|<%=depreCd%>|<%=assetTypeCd%>|0" />
		<%
		}
		%>
	</div>

</body>
</html>