<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>    
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>  
<%
String pageTitle = "포장재입고확인";
String curAction = "/kp2100/kp2131.do";
String curGridAction = "/kp2100/kp2131DetailAjax.do";
String curSearchAction = "/kp2100/kp2131Search.do";
String xlsDnAction = "/kp2100/kp2131Excel.do";
String detailAction = "/kp2100/kp2131.do";
String stockAction = "/kp2100/kp2131Stock.do";
String recallAction = "/kp2100/kp2131Recall.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

int colbasewid = 220; //검색 폼 동적 사이즈 구성을 위한 넓이 값
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

var colNames01 = ['rowNum'
                  <%
                  CommonList dispMngList = RequestUtil.getCommonList(request, "packingReceiptDetailList");
                  if (dispMngList != null) {
                  	for (int k=0; k<dispMngList.size(); k++) {
                  		CommonMap dispMng = dispMngList.getMap(k);
                  		out.println(", '" + dispMng.getString("logicalName") + "'");
                  	}
                  }
                  %>
                  ];

                  var colModel01 = [
                  				{name:'rowNum', index:'rowNum', width:'0px', hidden:true}
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
		shrinkToFit: false,
		cellEdit : true,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: true,
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
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
	
	frm.sRqstVendorCd.value = $("select[name=sRqstVendorCd]").val();
	frm.sRqstItemCd.value = $("select[name=sRqstItemCd]").val();
	frm.sRqstPNoCd.value = $("select[name=sRqstPNoCd]").val();
	
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
	frm.sRqstVendorCd.value = $("select[name=sRqstVendorCd]").val();
	frm.sRqstItemCd.value = $("select[name=sRqstItemCd]").val();
	frm.sRqstPNoCd.value = $("select[name=sRqstPNoCd]").val();
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

		var frm = document.sForm;			
		frm.action = "<%=detailAction%>";
		frm.target = "_self";
		frm.submit();
	} else {
		alert("상세보기 할 내역을 선택해 주세요");
		return;
	}
	
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
	
function fnStock(){
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("입고 처리할 행을 선택해주세요.");
		return;
	}
	
	if($("#sReceiptCnt").val() == ""){
		alert("입고수량을 입력해주세요.");
		return;
	}
     
	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
						 
			var saveJsonObj = {
					odId: obj.odId ,
					demandId : obj.demandId	,
					sReceiptCnt : $("#sReceiptCnt").val(), 
					sRqstVendorCd : $("select[name=sRqstVendorCd]").val(),
					sRqstItemCd : 	$("select[name=sRqstItemCd]").val(),
					sRqstPNoCd : $("select[name=sRqstPNoCd]").val()						
			};
			saveJsonArray.push(saveJsonObj);
		}

	
	
		if (confirm("입고 처리 하시겠습니까?")) {
			fnLoadingS2();
           
			$.ajax({
				type : "POST",
				url : "<%=stockAction%>",
				data : {
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("처리 되었습니다.");
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
}

function fnRecall(){
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("반품 처리할 행을 선택해주세요.");
		return;
	}

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					odId: obj.odId ,
					demandId : obj.demandId					
			};
			saveJsonArray.push(saveJsonObj);
		}

		if (confirm("반품 처리 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=recallAction%>",
				data : {
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("처리 되었습니다.");
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
    <input type="hidden" id="sRqstVendorCd" name="sRqstVendorCd" value="<%=cmRequest.getString("sRqstVendorCd", "")%>" />
	<input type="hidden" id="sRqstItemCd" name="sRqstItemCd" value="<%=cmRequest.getString("sRqstItemCd", "")%>" />
	<input type="hidden" id="sRqstPNoCd" name="sRqstPNoCd" value="<%=cmRequest.getString("sRqstPNoCd", "")%>" />
	<div id="divPopupLayer"></div>

	<div id="SearchBox" class="SearchBox"></div>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<span class="button"><input type="submit" value="<spring:message code="button.search"/>" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="검색초기화" onclick="fnInitSearchForm();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.download.excel"/>" onclick="fnXlsDn();"></span>
		<!--  <span class="button"><input type="button" value="입고처리" onclick="fnStock();"></span>
		<span class="button"><input type="button" value="반품처리" onclick="fnRecall();"></span>-->	
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

