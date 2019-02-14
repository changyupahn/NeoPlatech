<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "감가상각처리";
String curAction = "/kp1600/kp1610.do";
String curGridAction = "/kp1600/kp1610Ajax.do";
String curProc = "/kp1600/kp1610Proc.do";
String delProc = "/kp1600/kp1610DelProc.do";
String delYearProc = "/kp1600/kp1610DelYearProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
String depreYear = cmRequest.getString("depreYear");
String depreMonth = cmRequest.getString("depreMonth");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 400;

$(window).resize(function(){
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
});

var colNames01 = [
                '상각월','상각건수','관리'
  				];
var colModel01 = [
  				{name:'depreMonth', index:'depreMonth', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'depreCnt', index:'depreCnt', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'mgr', index:'mgr', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";

						<% if (ssAuthManager) { %>
						if (options.rowId == 1)
							value = '<span class="button"><input type="button" value="감가상각처리내역삭제('+ rData.depreMonth +')" onclick="fnDepreDelProc(\''+ rData.depreDt +'\');"></span>';
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

function fnDepreProc() {
	if (confirm("감가상각처리 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=curProc%>",
			data : {
				depreYear : $('#depreYear').val(),
				depreMonth : $('#depreMonth').val()
			},
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("상각 처리 되었습니다.");
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
				fnLoadingE2();
			}
		});
	}
}

function fnDepreDelProc(depreDt) {
	if (confirm("감가상각 내역을 삭제 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=delProc%>",
			data : {
				depreDt : depreDt
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
				fnLoadingE2();
			}
		});
	}
}

function fnDepreDelYearProc() {
	if (confirm("<%=depreYear%>년 감가상각 내역을 삭제 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=delYearProc%>",
			data : {
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
				fnLoadingE2();
			}
		});
	}
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

	<div class="SearchBox">
	<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="" />
	</colgroup>
	<tr>
		<th>상각연도 :</th>
		<td>
			<select id="sDepreYear" name="sDepreYear" style="height:22px; width:80px;">
				<option value="">전체</option>
				<%
				int curYear = Integer.parseInt(DateUtil.getFormatDate("yyyy"));
				for (int i=curYear; i>=1956; i--) {
					out.print(String.format("<option value=\"%s\">%s</option>", i, i+"년"));
				}
				%>
			</select>

			<span class="button2"><input type="submit" value="검색" onclick="fnSearch();"></span>
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
			<select id="depreYear" name="depreYear" style="height:22px; width:80px;">
				<%
				for (int i=curYear; i>=1956; i--) {
					%><option value="<%=i%>" <%=(i+"").equals(depreYear)?"selected=\"selected\"":""%>><%=i%>년</option><%
				}
				%>
			</select>
			<select id="depreMonth" name="depreMonth" style="height:22px; width:60px;">
				<%
				for (int i=1; i<=12; i++) {
					String month = StringUtil.lpad(""+i, 2, "0");
					%><option value="<%=month%>" <%=month.equals(depreMonth)?"selected=\"selected\"":""%>><%=month%>월</option><%
				}
				%>
			</select>
			<span class="button2"><input type="button" value="감가상각처리(수동)" onclick="fnDepreProc();"></span>
			<span class="button2"><input type="button" value="감가상각처리내역삭제(<%=depreYear%>년)" onclick="fnDepreDelYearProc();"></span>
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



