<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "검수정보";
String curAction = "/kp1300/kp1314.do";
String curGridAction = "/kp1300/kp1314Ajax.do";
String curTabAction = "/kp1300/kp1311Tab.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
CommonMap assetDetail = RequestUtil.getCommonMap(request, "assetDetail");

int colbasewid = 310; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">

var widthHip = 5;
var heightHip = 340;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                  '검수품목일련번호','구매번호','신청번호','물품일련번호',
  				'자산구분코드','품목코드','품목명','상세명칭','영문명','제조국코드','제조국','제조업체코드','제조업체','판매업체코드','판매업체',
  				'수량','단가','금액','외환','통화단위','계정번호','내용연수','상각법','규격','취득방법','사용목적','소프트웨어','ZEUS장비','ETUBE장비','활용범위'
  				];
  var colModel01 = [
  				{name:'inspItemSeq', index:'inspItemSeq', width:'0px', hidden:true},
  				{name:'purno', index:'purno', width:'0px', hidden:true},
  				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
  				{name:'prodno', index:'prodno', width:'0px', hidden:true},
  				{name:'assetTypeCd', index:'assetTypeCd', width:'0px', hidden:true},
  				{name:'itemCd', index:'itemCd', width:'0px', hidden:true},
  				{name:'itemName', index:'itemName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'assetName', index:'assetName', width:'300px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'assetEname', index:'assetEname', width:'120px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'mkNationCd', index:'mkNationCd', width:'0px', hidden:true},
  				{name:'mkNationName', index:'mkNationName', width:'90px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'mkCompanyCd', index:'mkCompanyCd', width:'0px', hidden:true},
  				{name:'mkCompanyName', index:'mkCompanyName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'slCompanyCd', index:'slCompanyCd', width:'0px', hidden:true},
  				{name:'slCompanyName', index:'slCompanyName', width:'150px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'assetCnt', index:'assetCnt', width:'70px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'aqusitAmt', index:'aqusitAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'aqusitForeignAmt', index:'aqusitForeignAmt', width:'100px', align:'RIGHT', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}},
  				{name:'capiTypeCd', index:'capiTypeCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'accntNum', index:'accntNum', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'usefulLife', index:'usefulLife', width:'70px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'depreCd', index:'depreCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'assetSize', index:'assetSize', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'aqusitTypeName', index:'aqusitTypeName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'purposeOfUse', index:'purposeOfUse', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'swYn', index:'swYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'zeusYn', index:'zeusYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'etubeYn', index:'etubeYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'aplctnRangeCd', index:'aplctnRangeCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
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
		shrinkToFit: false,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: -1,
		multiselect: false,
		resizeStop: function() {
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
		},
		onSortCol : function(index, columnIndex, sortOrder) {
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

function fnSearch() {
	fnGridReload("1");
}

function fnInitTabForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curTabAction%>",
		data : {
			colcnt : colcnt,
			assetSeq : $('#assetSeq').val()
		},
		success:function(data)
		{
			$('#TabBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}
$(document).ready(function(){

	fnInitTabForm();
	fnGridList();
});
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<div id="TabBox" class="TabBox"></div>

	<table style="width:100%;">
	<colgroup>
		<col width="" />
		<col width="510px" />
	</colgroup>
	<tr>
		<td style="padding:10px 0 0 0;border-collapse:collapse;margin:0;">
			<h2 class="title_left" style="margin-bottom:0px;"><%=pageTitle%></h2>
		</td>
		<td style="text-align:right;">
		</td>
	</tr>
	</table>

	<table class="table-search" style="width:100%;">
	<colgroup>
		<col width="120px" />
		<col width="250px" />
		<col width="120px" />
		<col width="" />
	</colgroup>
	<tr>
		<th class="r">계약번호 :</th>
		<td><%=viewData.getString("contrno")%></td>
		<th class="r">구매신청자 :</th>
		<td><%=viewData.getString("userhisname")%></td>
	</tr>
	<tr>
		<th class="r">계약명 :</th>
		<td><%=viewData.getString("purnm")%></td>
		<th class="r">계약일자 :</th>
		<td><%=DateUtil.formatDateTime(viewData.getString("contrdt"), "-", ":", 8)%></td>
	</tr>
	<tr>
		<th class="r">거래처 :</th>
		<td><%=viewData.getString("custhisname")%></td>
		<th class="r">계약금액 :</th>
		<td><%=StringUtil.commaNum(viewData.getString("contramt"))%></td>
	</tr>
	<tr>
		<th class="r">검수자 :</th>
		<td><%=viewData.getString("inspUserName")%></td>
		<th class="r">검수일자 :</th>
		<td><%=DateUtil.formatDateTime(viewData.getString("inspDt"),"-",":",8)%></td>
	</tr>
	</table>

	<h2 class="title_left">납품내역</h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=assetDetail.getString("assetSeq")%>" />
	<input type="hidden" id="purno" name="purno" value="<%=assetDetail.getString("purno")%>" />

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>



