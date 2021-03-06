<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "재물조사내역";
String curAction = "/kp1700/kp1720.do";
String curGridAction = "/kp1700/kp1720Ajax.do";
String curSearchAction = "/kp1700/kp1720Search.do";
String xlsDnAction = "/kp1700/kp1720Excel.do";
String statCateAction = "/kp1700/kp1720CateExcel.do";
String statDeptAction = "/kp1700/kp1720DeptExcel.do";
String statHosilAction = "/kp1700/kp1720HosilExcel.do";
String tagPrintAction = "/kp1000/kp1020Print.do";
String detailAction = "/kp1300/kp1311.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap invLast = RequestUtil.getCommonMap(request, "invLast");

int colbasewid = 340; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 300;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = ["assetSeq"
                  <%
                  CommonList dispMngList = RequestUtil.getCommonList(request, "dispAssetList");
                  if (dispMngList != null) {
                  	for (int k=0; k<dispMngList.size(); k++) {
                  		CommonMap dispMng = dispMngList.getMap(k);
                  		out.println(", '" + dispMng.getString("logicalName") + "'");
                  	}
                  }
                  %>
                  ];

var colModel01 = [
  				{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true}
  <%
  if (dispMngList != null) {
  	for (int k=0; k<dispMngList.size(); k++) {
  		CommonMap dispMng = dispMngList.getMap(k);

  		String logicalName = dispMng.getString("logical_name");
  		String physicalName = dispMng.getString("physical_name");
  		String align = dispMng.getString("default_align", "center");
  		String fommater = "";
  		if ("TEXT".equalsIgnoreCase(dispMng.getString("data_disp_type"))) { //문자형
  			fommater = "";
  		} else if ("NUMBER".equalsIgnoreCase(dispMng.getString("data_disp_type"))) {	//숫자형
  			fommater = ", formatter:'currency', formatoptions:{thousandsSeparator:\",\", decimalPlaces: 0}";
  		} else if ("DATE".equalsIgnoreCase(dispMng.getString("data_disp_type"))) {	//날짜형
  			fommater = ", formatter: fnFormatterDate";
  		}
		out.println("," + String.format("{name:'%s', index:'%s', width:'%spx', align:'%s', columntype:'text', classes:'grid-col-%s'%s}"
				, CamelUtil.convert2CamelCase(physicalName)
				, CamelUtil.convert2CamelCase(physicalName)
				, dispMng.getString("defaultWidth")
				, align
				, dispMng.getString("data_disp_type")
				, fommater
				));
  	}
  }
  %>
  ];
var groupHeaders01 = [];

function fnGridList() {

	$("#listInfo01").jqGrid("GridUnload");

	$("#listInfo01").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction%>",
		postData : fnSerializeObject(),
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
		shrinkToFit: false,
		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: true,
		resizeStop: function() {
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
			location.href = "/";
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		ondblClickRow:function(rowId){

			fnDetail(rowId);
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.totalRow, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.totalRow);

			fnGridInvalidSession(data.totalRow);

		},
		gridComplete : function() {

			//텍스트가 셀 너비보다 넓을 때 자동 줄 바꿈 처리
			$('.ui-jqgrid tr.jqgrow td').css("white-space", "normal");

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

function fnDetail(rowId) {

	var selRowId = "";
	if (rowId) {
		selRowId = rowId;
	} else {
		selRowId = $("#listInfo01").getGridParam('selrow');
	}

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);

		$('#layerPop').click();
		$('#iframe').attr("src", "<%=detailAction%>?assetSeq=" + obj.assetSeq);
		$('#layer_iframe').show();
	} else {
		alert("상세보기 할 자산을 선택해 주세요");
		return;
	}
}

function fnXlsDnCate(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=statCateAction%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnDept(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=statDeptAction%>";
	frm.target = "_self";
	frm.submit();
}

function fnXlsDnHosil(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=statHosilAction%>";
	frm.target = "_self";
	frm.submit();
}

$(document).ready(function(){

	fnGridList();
	fnInitSearchForm();
	fnInitLayerPopup();
});
function fnInitSearchForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curSearchAction%>",
		data : {
			colcnt : colcnt
		},
		success:function(data)
		{
			$('#SearchBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
			fnGridResize();
		}
	});
}
function fnInvChange() {
	var sInv = $('#sInv').val();
	var sInvArr = sInv.split("_");
	$('#sInvYear').val(sInvArr[0]);
	$('#sInvNo').val(sInvArr[1]);
}

function fnPrint() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("태그발행할 행을 선택해주세요.");
		return;
	}

	if (ids.length > 0) {

		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					assetSeq: obj.assetSeq
			};
			saveJsonArray.push(saveJsonObj);
		}

		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=tagPrintAction%>",
			data : {
				saveJsonArray : JSON.stringify(saveJsonArray),
				reqSystem : 'INV'
			},
			dataType : "json",
			//timeout : 30000,
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("태그발행 요청 되었습니다.");
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

//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#sItemSeq').val(obj.seq);
	$('#sItemCd').val(obj.cd);
	$('#sItemName').val(obj.nm);
}

//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	$('#sDeptNo').val(obj.cd);
	$('#sDeptName').val(obj.nm);
}

//사용자 콜백
function fnSetKp9030(obj) {
	$('#sUserNo').val(obj.userNo);
	$('#sUserName').val(obj.userName);
}

//위치 콜백
function fnSetKp9040(obj) {
	$('#sPosNo').val(obj.posNo);
	$('#sPosName').val(obj.posName);
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%=pageTitle%></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="sInvYear" name="sInvYear" value="<%=invLast.getString("invYear")%>" />
	<input type="hidden" id="sInvNo" name="sInvNo" value="<%=invLast.getString("invNo")%>" />

	<div id="divPopupLayer"></div>

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="검색초기화" onclick="fnInitSearchForm();"></span>
		<span class="button"><input type="button" value="상세보기" onclick="fnDetail();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
		<span class="button"><input type="button" value="부서(실)별통계" onclick="fnXlsDnDept();"></span>
		<!-- <span class="button"><input type="button" value="자산구분별통계" onclick="fnXlsDnCate();"></span>
		<span class="button"><input type="button" value="관리부서별통계" onclick="fnXlsDnDept();"></span> -->
		<!-- <span class="button"><input type="button" value="위치별통계" onclick="fnXlsDnHosil();"></span> -->

		<% if (ssAuthManager) { %>
		<span class="button"><input type="button" value="태그발행" onclick="fnPrint();"></span>
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
		<option value="1000" <%="1000".equals(cmRequest.getString("pageSize"))?"selected":""%>><spring:message code="count.paging" arguments="1000" /></option>
		</select>
	</td>
	</tr>
	</table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



