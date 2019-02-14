<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "ETUBE장비 관리";
String curAction = "/kp1800/kp1820.do";
String curGridAction = "/kp1800/kp1820Ajax.do";
String curSearchAction = "/kp1800/kp1820Search.do";
String xlsDnAction = "/kp1800/kp1820Excel.do";
String xlsDnDtlAction = "/kp1800/kp1820DtlExcel.do";
String detailAction = "/kp1800/kp1821.do";
String writeAction = "/kp1800/kp1823.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 320; //검색 폼 동적 사이즈 구성을 위한 넓이 값
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
	fnGridResize()
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = [
                '줄번호'
                ,'장비설치기관번호'
                ,'기관위치(경도+위도)'
                ,'장비번호'
                ,'한글장비명'
                ,'영문장비명'
                ,'모델명'
                ,'장비자산코드'
                ,'대분류코드'
                ,'중분류코드'
                ,'소분류코드'
                ,'장비용도코드'
                ,'설치형태코드'
                ,'장비이미지경로'
                ,'경도장비보유기관위치X'
                ,'위도장비보유기관위치Y'
                ,'등록일'
                ,'수정일'
                ,'장비설치기관주소'
                ,'장비설치기관주소1'
                ,'장비설치기관주소2'
                ,'장비설치기관전체주소'
                ,'장비설치기관명'
                ,'장비보유기관명'
                ,'장비보유기관전화번호'
  				];
var colModel01 = [
  				{name:'rnum', index:'rnum', width:'0px', hidden:true},
  				{name:'equipInstCpnyNo', index:'equipInstCpnyNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'dist', index:'dist', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipNo', index:'equipNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipKorNm', index:'equipKorNm', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipEngNm', index:'equipEngNm', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipModelNm', index:'equipModelNm', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipAssetNo', index:'equipAssetNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipClassCd1', index:'equipClassCd1', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipClassCd2', index:'equipClassCd2', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipClassCd3', index:'equipClassCd3', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipUseCd', index:'equipUseCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipUseMoveCd', index:'equipUseMoveCd', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipImg', index:'equipImg', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipInstCpnyPosx', index:'equipInstCpnyPosx', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipInstCpnyPosy', index:'equipInstCpnyPosy', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'regDate', index:'regDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'modDate', index:'modDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'cpnyAddr', index:'cpnyAddr', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipInstCpnyAddr1', index:'equipInstCpnyAddr1', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'equipInstCpnyAddr2', index:'equipInstCpnyAddr2', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'cpnyFullAddr', index:'cpnyFullAddr', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'cpnyNm', index:'cpnyNm', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'haveCpnyNm', index:'haveCpnyNm', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'},
  				{name:'cpnyTelNo', index:'cpnyTelNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
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
			root    : "equipList",
			repeatitems : false
		},
		colNames : colNames01,
		colModel : colModel01,
		rownumbers: false,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: false,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: false,
		resizeStop: function() {
			//헤더 틀어짐 방지
			//nonBreakMultiHeader('listInfo01');
		},
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		beforeSaveCell : function(rowid, cellname, value, irow, icol) {
// 			var crud = $("#listInfo01").jqGrid('getRowData',rowid).crud;
// 			if (crud != "C") {
// 				$("#listInfo01").jqGrid('setRowData',rowid,{crud: "U"});
// 			}
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
		ondblClickRow:function(rowId){

			var equipId = $('#listInfo01').jqGrid('getRowData', rowId).equipId;

			fnDetail(equipId);
		},
		loadComplete : function(data) {
			//paging 처리
			pagingUtil.setHTML($('#pageIdx').val(), $('#pageSize').val(), data.equipListTotCnt, 'paginate');

			//totalRow
			$('#spanTotalRow').html(data.equipListTotCnt);

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
function fnDetail(equipId){
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=detailAction%>?equipId=" + equipId);
	$('#layer_iframe').show();
}
function fnWrite() {
	$('#layerPop').click();
	$('#iframe').attr("src", "<%=writeAction%>");
	$('#layer_iframe').show();
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
//인계자 선택 콜백
function fnSetKp9030_1(obj) {
	$('#sRqstUserNo').val(obj.userNo);
	$('#sRqstUserName').val(obj.userName);
}
//인수자 선택 콜백
function fnSetKp9030_2(obj) {
	$('#sAucUserNo').val(obj.userNo);
	$('#sAucUserName').val(obj.userName);
}
//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#sItemSeq').val(obj.seq);
	$('#sItemCd').val(obj.cd);
	$('#sItemName').val(obj.nm);
}
//부서코드 팝업 콜백 함수
function fnSetKp9020(obj) {
	$('#sRqstDeptNo').val(obj.cd);
	$('#sRqstDeptName').val(obj.nm);
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

	<div id="divPopupLayer"></div>

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
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
		</select>
	</td>
	</tr>
	</table>

	</form>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



