<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "재물조사대상설정";
String curAction = "/kp1700/kp1712.do";
String curGridAction = "/kp1700/kp1712Ajax.do";
String curSearchAction = "/kp1300/kp1310Search.do";
String procAction = "/kp1700/kp1712Proc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

int colbasewid = 280; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">
var widthHip = 5;
var heightHip = 150;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - $('#SearchBox').height() - heightHip);
}

var colNames01 = ['invYear','invNo','assetSeq','재물조사대상'
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
				{name:'invYear', index:'invYear', width:'0px', hidden:true}
				,{name:'invNo', index:'invNo', width:'0px', hidden:true}
				,{name:'assetSeq', index:'assetSeq', width:'0px', hidden:true}
				,{name:'invTargetYn', index:'invTargetYn', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
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

  		if ("TOP_ASSET_NO".equals(physicalName)) {
  			%>
  			,{name:'topAssetNo', index:'topAssetNo', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:true, editable:false,
  				formatter: function(value, options, rData)
  				{
  					if (value == "부속품") {
  						value = '<img src="/images/icon/reply_arrow.png" />';
  						//value = '<img src="/images/icon/arrow01.png" />';
  					} else {
  						value = value;
  					}

  					return value;
  				}}
  			<%
  		} else {
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
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: false,
		cellEdit : true,
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: true,
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

function fnSearch(){
	fnGridReload("1");
}

function fnSetTarget(targetYn) {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];
	$('#saveJsonArray').val("");

	$('#invTargetYn').val(targetYn);

	var objVal = "";
	var comfirmMsg = "";
	if (targetYn == "Y") {
		objVal = "설정";
	} else {
		objVal = "해제";
	}

	var selcnt = ids.length;
	var totalcnt = parseInt($('#spanTotalRow').html());

	if (selcnt > 0) {
		comfirmMsg = "선택내역("+ selcnt +" 건) 을 모두 재물조사대상 "+ objVal +" 하시겠습니까?";
	} else if (totalcnt > 0) {
		comfirmMsg = "전체내역("+ totalcnt +" 건) 을 모두 재물조사대상 "+ objVal +" 하시겠습니까?";
	} else {
		alert("재물조사대상 설정할 내역이 없습니다.");
		return;
	}

	for (var i=0; i<selcnt; i++) {
		var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
		var saveJsonObj = {
				assetSeq: obj.assetSeq
		};
		saveJsonArray.push(saveJsonObj);
		$('#saveJsonArray').val(JSON.stringify(saveJsonArray));
	}

	if (confirm(comfirmMsg)) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=procAction%>",
			data : fnSerializeObject(),
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert(objVal+" 되었습니다.");
					fnGridList();
					parent.fnGridList();
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
	fnInitSearchForm();
});
function fnInitSearchForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curSearchAction%>",
		data : {
			colcnt : colcnt,
			sAssetDiv : "1",
			sEtcDiv : "1"
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

<style type="text/css">
<%-- /* selrow highlight disabled */
.ui-state-highlight{
    background:none!important;
}
/* mouseover highlight disabled */
.ui-state-hover{
    background:none!important;
}
/* vertical-align : middle */
.ui-jqgrid input,select {
	border:1px solid gray;
	position:relative;
	top:1px;
}
.ui-jqgrid input[type="checkbox"] {
	position:relative;
	top:3px;
} --%>
</style>

</head>
<body>

	<h2 class="title_left"><%=pageTitle%></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false;">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="asset_no" name="asset_no" value="<%=cmRequest.getString("asset_no")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	<input type="hidden" id="printType" name="printType" value="" />
	<input type="hidden" id="chkAssetNo" name="chkAssetNo" value="" />
	<input type="hidden" id="invYear" name="invYear" value="<%=cmRequest.getString("invYear")%>" />
	<input type="hidden" id="invNo" name="invNo" value="<%=cmRequest.getString("invNo")%>" />
	<input type="hidden" id="saveJsonArray" name="saveJsonArray" value="" />
	<input type="hidden" id="invTargetYn" name="invTargetYn" value="" />

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
		<span class="button"><input type="button" value="재물조사대상(설정)" onclick="fnSetTarget('Y');"></span>
		<span class="button"><input type="button" value="재물조사대상(해제)" onclick="fnSetTarget('N');"></span>
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

</body>
</html>



