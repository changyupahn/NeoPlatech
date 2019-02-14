<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "제조국코드관리";
String curAction = "/kp1100/kp1150.do";
String curExcel = "/kp1100/kp1150Excel.do";
String curProc = "/kp1100/kp1150Proc.do";
String curDelProc = "/kp1100/kp1150DelProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList dataList = RequestUtil.getCommonList(request, "dataList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<link type="text/css" rel="stylesheet" href="/common/jstree/_docs/syntax/!style.css"/>
<script type="text/javascript" src="/common/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/common/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/jstree/_docs/syntax/!script.js"></script>

<script type="text/javascript" src="/common/js/kp1150.js"></script>
<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.target = "_self";
	frm.submit();
}
function fnExcel(){
	if( confirm("<%= pageTitle %>" + "을 엑셀로 저장하시겠습니까?") ){
		var frm = document.sForm;
		frm.action = "<%=curExcel%>";
		frm.submit();
	}
}
function fnDel(){
	if( confirm("삭제 하시겠습니까?") ){
		$.ajax({
			type : "POST",
			url : "<%=curDelProc%>",
			data : {
				natnSeq : $('#natnSeq').val(),
				natnCd : $('#natnCd').val()
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("삭제되었습니다.");
					location.href = "<%=curAction%>";
				} else {
					alert(data.retmsg);
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("[ERROR] 저장 중 오류가 발생하였습니다.");
			},
			complete:function()
			{
			}
		});
	}
}
function fnSave(){
	if( confirm("저장 하시겠습니까?") ){

		$.ajax({
			type : "POST",
			url : "<%=curProc%>",
			data : fnSerializeObject(),
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("저장되었습니다.");
					location.href = "<%=curAction%>";
				} else {
					alert(data.retmsg);
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("[ERROR] 저장 중 오류가 발생하였습니다.");
			},
			complete:function()
			{
			}
		});
	}
}
$(document).ready(function(){
	fnInitTreeData();
	fnInitTree('Y');
});
$(window).resize(function(){
	fnInitSize();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%= pageTitle %></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="sNatnSeq" name="sNatnSeq" value="" />

	<table style="width:100%;border-collapse:collapse;border:0px;">
	<tr>
	<td width="29%" valign="top">
		<div id="treeSearch">
			<table style="width:100%" class="table-search" border="0">
			<colgroup>
				<col width="100px" />
				<col width="" />
			</colgroup>
			<tr>
			<th class="r">코드/명 :</th>
			<td>
				<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
				<span class="button"><input type="submit" value="검색" onclick="fnSearch()" ></span>
				<span class="button"><input type="button" value="펼치기" onclick="fnInitTree('Y')"></span>
				<span class="button"><input type="button" value="접기" onclick="fnInitTree('N')"></span>
			</td>
			</tr>
			</table>
		</div>
		<div id="treeDiv" class="mulpum_tree">
			<div id="tree"></div>
		</div>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="69%" valign="top">
		<div id="contDiv">
		</div>
	</td>
	</tr>
	</table>

	<div id="treeData">
		<input type="hidden" name="kp1150TreeData" value="0|제조국코드||0|Y" />
		<%
		for(int i=0; i<dataList.size(); i++){
			CommonMap tree = dataList.getMap(i);
			String seq = tree.getString("natnSeq");
			String code = tree.getString("natnCd");
			String codeName = "[" + code + "] " + tree.getString("natnName");
			String pseq = tree.getString("parentNatnSeq").trim();
			String useYn = tree.getString("useYn");

			if ("0000".equals(code)) {
				codeName = tree.getString("natnName");
			}
		%>
		<input type="hidden" name="kp1150TreeData" value="<%=seq%>|<%=codeName%>|<%=pseq%>|0|<%=useYn%>" />
		<%
		}
		%>
	</div>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>