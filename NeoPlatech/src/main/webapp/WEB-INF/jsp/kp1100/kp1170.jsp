<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "판매업체관리";
String curAction = "/kp1100/kp1170.do";
String curGridAction = "/kp1100/kp1170Ajax.do";
String curSearchAction = "/kp1100/kp1170Search.do";
String xlsDnAction = "/kp1100/kp1170Excel.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 330;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = [
                 'compSeq',
                 '업체코드','업체명','사업자등록번호','대표자','업태','종목','주소','연락처','이메일'
   				 ];
var colModel01 = [
   				{name:'compSeq', index:'compSeq', width:'0px', hidden:true},
   				{name:'compCd', index:'compCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compName', index:'compName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compBizNo', index:'compBizNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compOwnerName', index:'compOwnerName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compBizType1', index:'compBizType1', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compBizType2', index:'compBizType2', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compAddr', index:'compAddr', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compTel', index:'compTel', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
   				{name:'compEmail', index:'compEmail', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
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
		rownumbers: false,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
//		shrinkToFit: false,
//		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
//		multiselect: true,
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

			var assetSeq = $('#listInfo01').jqGrid('getRowData', rowId).assetSeq;

			fnDetail(assetSeq);
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

			fnGridInvalidSession(data.totalRow);

		},
		gridComplete : function() {
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

function fnSearch(){
	fnGridReload("1");
}
function fnXlsDn(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction%>";
	frm.target = "_self";
	frm.submit();
}
$(document).ready(function(){

	fnGridList();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%=pageTitle%></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />

	<div id="SearchBox" class="SearchBox">
		<table class="search01">
			<colgroup>
				<col width="140px" />
				<col width="" />
			</colgroup>
			<tr>
				<th>
					<select id="searchGubun" name="searchGubun" style="height:22px">
						<option value="1" <%="1".equals(cmRequest.getString("searchGubun"))?"selected":""%>>업체명</option>
						<option value="2" <%="2".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>업체코드</option>
						<option value="3" <%="3".equals(cmRequest.getString("searchGubun"))?"selected":""%>>사업자등록번호</option>
						<option value="4" <%="4".equals(cmRequest.getString("searchGubun"))?"selected":""%>>대표자</option>
						<option value="5" <%="5".equals(cmRequest.getString("searchGubun"))?"selected":""%>>업태</option>
						<option value="6" <%="6".equals(cmRequest.getString("searchGubun"))?"selected":""%>>종목</option>
						<option value="7" <%="7".equals(cmRequest.getString("searchGubun"))?"selected":""%>>주소</option>
						<option value="8" <%="8".equals(cmRequest.getString("searchGubun"))?"selected":""%>>연락처</option>
						<option value="9" <%="9".equals(cmRequest.getString("searchGubun"))?"selected":""%>>이메일</option>
					</select>
				</th>
				<td>
					<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" style="width:200px;" />

					<span class="button2"><input type="submit" value="검색" onclick="fnSearch();"></span>
				</td>
			</tr>
		</table>
	</div>

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

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



