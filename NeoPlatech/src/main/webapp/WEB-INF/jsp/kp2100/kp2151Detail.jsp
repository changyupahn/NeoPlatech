<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "부자재출고상세";
String curAction = "/kp2100/kp2151Detail.do";
String curGridAction = "/kp2100/kp2151DetailAjax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList goodsShipmentOutDetailList = RequestUtil.getCommonList(request, "goodsShipmentOutDetailList");


int idx = 0;
int colcnt = cmRequest.getInt("colcnt");
int colmax = colcnt;
%>
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

var colNames01 = [ 'rowNum'
                  , '소요수량'
                  , '고유ID'
                  , '출고수량'
                  , '주문번호'
                  , '사급구분'
                  , '제품수량'
                  , '공정명'
                  , '발행일시'
                  , '발주구분'
                  , '품번'
                  , '생산지정보'
                  , '관리단위'
                  , '상세아이템ID'
                  , '출고구분'
                  , '제품번호'
                  , '생산현장분류'
                  , '고유Key'
                  , '발주컴퓨터이름'
                  , '재발행이력'
                  , '규격번호'
                  ];

                  var colModel01 = [
                  	{name:'rowNum', index:'rowNum', width:'0px', hidden:true}
                  ,{name:'bomQty', index:'bomQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'ptOdId', index:'ptOdId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'subSumQty', index:'subSumQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'demandId', index:'demandId', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'osp', index:'osp', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'lgOdQty', index:'lgOdQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'neoLine', index:'neoLine', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'sendTime', index:'sendTime', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'goWith', index:'goWith', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'subPartNo', index:'subPartNo', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'vendor', index:'vendor', width:'200px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'subUnit', index:'subUnit', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'subPtOdId', index:'subPtOdId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'demandFloor', index:'demandFloor', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'lgPartNo', index:'lgPartNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'prodType', index:'prodType', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'neoPlanKey', index:'neoPlanKey', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'sendingPcName', index:'sendingPcName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'rePrint', index:'rePrint', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'subPartDesc', index:'subPartDesc', width:'200px', align:'LEFT', columntype:'text', classes:'grid-col-TEXT'}

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

$(document).ready(function(){

	fnGridList();
});
</script>
	</head>
<body>
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
		<span class="button"><input type="submit" value="<spring:message code="button.search"/>" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="검색초기화" onclick="fnInitSearchForm();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.download.excel"/>" onclick="fnXlsDn();"></span>
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