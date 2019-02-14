<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList treeList = RequestUtil.getCommonList(request, "treeList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<link type="text/css" rel="stylesheet" href="/common/jstree/_docs/syntax/!style.css"/>
<script type="text/javascript" src="/common/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/common/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/jstree/_docs/syntax/!script.js"></script>

<script type="text/javascript" src="/common/js/tree.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		fnInitTreeData();
		fnInitTree();
	});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">트리구조 샘플</h2>
	
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td width="30%">
		<div id="treeDiv">
			<div id="tree"></div>
		</div>
	</td>
	<td width="70%">
		<div id="contDiv">
		</div>
	</td>
	</tr>
	</table>
	
	<div id="treeData">
		<!-- <input type="hidden" name="kp110TreeData" value="물품코드|물품명|상위코드|비고" /> -->
		<%
		for(int i=0; i<treeList.size(); i++){
			CommonMap tree = treeList.getMap(i);
			String treeNo = tree.getString("TREE_N0");
			String parentNo = tree.getString("PARENT_NO");
			String tmpParentNo = "";
		%>
		<input type="hidden" name="kp110TreeData" value="물품코드|물품명|상위코드|비고" />
		<%
		}
		%>
		
		<input type="hidden" name="kp110TreeData" value="100|최상위||비고" />
		<input type="hidden" name="kp110TreeData" value="110|코드110|100|비고" />
		<input type="hidden" name="kp110TreeData" value="5|코드111|110|비고" />
		<input type="hidden" name="kp110TreeData" value="6|코드112|110|비고" />
		<input type="hidden" name="kp110TreeData" value="113|코드113|110|비고" />
		<input type="hidden" name="kp110TreeData" value="120|코드120|100|비고" />
		
	</div>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

