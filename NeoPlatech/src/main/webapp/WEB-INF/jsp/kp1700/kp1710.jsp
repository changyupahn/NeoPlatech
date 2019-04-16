<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "재물조사관리";
String curAction = "/kp1700/kp1710.do";
String curGridAction = "/kp1700/kp1710Ajax.do";
String writeAction = "/kp1700/kp1711.do";
String delProcAction = "/kp1700/kp1710DelProc.do";
String targetAction = "/kp1700/kp1712.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

CommonMap invLast = RequestUtil.getCommonMap(request, "invLast");
CommonList invYearList = RequestUtil.getCommonList(request, "invYearList");
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
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
});

var colNames01 = [
				'조사연도','조사차수','기준일','비고','조사대상','관리'
				];
var colModel01 = [
   				{name:'invYear', index:'invYear', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'invNo', index:'invNo', width:'80px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'invStartDt', index:'invStartDt', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'invSummary', index:'invSummary', width:'250px', align:'LEFT', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'targetCount', index:'targetCount', width:'120px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'},
   				{name:'mgr', index:'mgr', align:'CENTER', width:'200px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";

						<% if (ssAuthManager) { %>
						value += ' <span class="button"><input type="button" value="조사대상설정" onclick="fnInvTargetSetting('+ rData.invYear +', '+ rData.invNo +');"></span>';
						value += ' <span class="button"><input type="button" value="삭제" onclick="fnDel('+ rData.invYear +', '+ rData.invNo +');"></span>';
						<% } %>

						return value;
					}},
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
//		shrinkToFit: false,
		cellEdit : false,
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

function fnWrite() {
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=writeAction%>");
	$('#layer_iframe').show();
}

function fnInvTargetSetting(invYear, invNo) {
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=targetAction%>?invYear="+invYear+"&invNo="+invNo);
	$('#layer_iframe').show();
}

function fnDel(invYear, invNo) {
	if (confirm("재물조사관리 내역을 삭제 하시겠습니까?")) {

		$.ajax({
			type : "POST",
			url : "<%=delProcAction%>",
			data : {
				invYear : invYear,
				invNo : invNo
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("삭제 되었습니다.");
					fnGridList();
				} else {
					alert(data.retmsg);
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("[ERROR] 처리 중 오류가 발생하였습니다.");
			},
			complete:function()
			{
			}
		});
	}
}

$(document).ready(function(){

	fnGridList();
	fnInitLayerPopup();
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

	<div id="divPopupLayer"></div>

	<div class="SearchBox">
	<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>재물조사연도 :</th>
		<td>
			<select id="sInvYear" name="sInvYear" style="height:22px; width:80px;">
				<option value="">전체</option>
				<%
				for (int i=0; i<invYearList.size(); i++) {
					CommonMap invYear = invYearList.getMap(i);
					out.print(String.format("<option value=\"%s\">%s</option>", invYear.getString("invYear"), invYear.getString("invYear")+"년"));
				}
				%>
			</select>

			<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		</td>
	</tr>
	</table>
	</div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<% if (ssAuthManager) { %>
		<span class="button"><input type="button" value="신규생성 (<%=invLast.getString("invYear")%>년도 <%=(invLast.getInt("invNo")+1)%>차수)" onclick="fnWrite();"></span>
		<% } %>
		&nbsp;
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

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



