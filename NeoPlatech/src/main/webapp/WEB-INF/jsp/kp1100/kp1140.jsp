<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "사용자코드관리";
String curAction = "/kp1100/kp1140.do";
String curGridAction = "/kp1100/kp1140Ajax.do";
String curExcel = "/kp1100/kp1140Excel.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList dataList = RequestUtil.getCommonList(request, "dataList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<link type="text/css" rel="stylesheet" href="/common/jstree/_docs/syntax/!style.css"/>
<script type="text/javascript" src="/common/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/common/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/jstree/_docs/syntax/!script.js"></script>

<script type="text/javascript" src="/common/js/kp1140.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
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
$(document).ready(function(){
	fnInitTreeData();
	fnInitTree('Y');
});
$(window).resize(function(){
	fnInitSize();
});
</script>

<script type="text/javascript">

var widthHip = 10;
var heightHip = 300;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - $('#treeSearch').width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                  '사용자번호','사번','성명','부서','재직상태'
                  ];
var colModel01 = [
    				{name:'userNo', index:'userNo', width:'80px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
    				{name:'empNo', index:'empNo', width:'80px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
    				{name:'userName', index:'userName', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
    				{name:'deptName', index:'deptName', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
    				{name:'userType', index:'userType', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
    				];
var groupHeaders01 = [];

function fnGridList() {

	$("#listInfo01").jqGrid("GridUnload");

	$("#listInfo01").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction%>",
		postData : $('#sForm').serializeObject(),
		mtype:'POST',
		jsonReader : {
			root    : "resultList",
			repeatitems : false
		},
		colNames : colNames01,
		colModel : colModel01,
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: true,
		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: false,
		resizeStop: function() {
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

			fnGridInvalidSession(data.totalRow);

		},
		gridComplete : function() {
			fnGridResize();
		}
	});
}

function fnGridReload(pageIdx){
	var frm = document.sForm;
	if (pageIdx) {
		frm.pageIdx.value = pageIdx;
	}

	$("#listInfo01").setGridParam({
		postData: $('#sForm').serializeObject()
	}).trigger("reloadGrid");
}

function fnGridReload2(pageIdx){
	var frm = document.dForm;
	if (pageIdx) {
		frm.pageIdx.value = pageIdx;
	}

	$("#listInfo01").setGridParam({
		postData: $('#dForm').serializeObject()
	}).trigger("reloadGrid");
}

function fnSearch(){
	fnGridReload("1");
}

function fnSearch2(){
	fnGridReload2("1");
}

$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%= pageTitle %></h2>

	<table style="width:100%;border-collapse:collapse;border:0px;">
	<tr>
	<td width="29%" valign="top">
		<div id="treeSearch">

			<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
			<input type="hidden" id="sUserNo" name="sUserNo" value="" />
			<input type="hidden" id="sDeptNo" name="sDeptNo" value="" />

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

			</form>

		</div>
		<div id="treeDiv" class="mulpum_tree">
			<div id="tree"></div>
		</div>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="69%" valign="top">

		<form id="dForm" name="dForm" method="post" action="<%=curAction%>" onsubmit="fnSearch2(); return false;">
		<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
		<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
		<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />

		<table style="width:100%" class="table-search" border="0">
			<colgroup>
				<col width="100px" />
				<col width="" />
			</colgroup>
			<tr>
			<th class="r">
				<select name="searchGubun2" style="width:80px;">
					<option value="1" <%="1".equals(cmRequest.getString("searchGubun2","1"))?"selected":""%>>성명</option>
					<option value="2" <%="2".equals(cmRequest.getString("searchGubun2"))?"selected":""%>>사번</option>
					<option value="3" <%="3".equals(cmRequest.getString("searchGubun2"))?"selected":""%>>부서</option>
				</select>
			</th>
			<td>
				<input type="text" name="searchKeyword2" value="<%=cmRequest.getString("searchKeyword2")%>" />
				<span class="button2"><input type="button" value="검색" onclick="fnSearch2();"></span>
			</td>
			</tr>
			</table>

		<p><spring:message code="count.total"/> : <span id="spanTotalRow"></span></p>

		<table id="listInfo01" class="scroll"></table>

		<table style="width:100%;border-collapse:collapse;border:0;">
		<tr>
		<td width="">
			<div id="paginate" class="paginate"></div>
		</td>
		<td width="150px" class="r">
			<select id="pageSize" name="pageSize" onchange="fnSearch()" style="border:1px solid gray;">
			<option value="15" <%="15".equals(cmRequest.getString("pageSize","15"))?"selected":""%>><spring:message code="count.paging" arguments="15" /></option>
			<option value="30" <%="30".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="30" /></option>
			<option value="50" <%="50".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="50" /></option>
			<option value="100" <%="100".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="100" /></option>
			<option value="500" <%="500".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="500" /></option>
			</select>
		</td>
		</tr>
		</table>

		</form>
	</td>
	</tr>
	</table>

	<div id="treeData">
		<input type="hidden" name="kp1140TreeData" value="0000000000|부서코드||0|Y" />
		<%
		for(int i=0; i<dataList.size(); i++){
			CommonMap tree = dataList.getMap(i);
			String code = tree.getString("deptNo");
			String codeName = "[" + code + "] " + tree.getString("deptName");
			String pseq = tree.getString("parentDeptNo").trim();

			if ("00000".equals(code)) {
				codeName = tree.getString("deptName");
			}
		%>
		<input type="hidden" name="kp1140TreeData" value="<%=code%>|<%=codeName%>|<%=pseq%>|0|Y" />
		<%
		}
		%>
	</div>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>