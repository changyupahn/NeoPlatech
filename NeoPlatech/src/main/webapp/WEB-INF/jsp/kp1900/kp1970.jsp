<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>    
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>    
<%
String pageTitle = "창고그룹관리";
String curAction = "/kp1900/kp1970.do";
String curGridAction = "/kp1900/kp1970Ajax.do";
String curSearchAction = "/kp1900/kp1970Search.do";
String xlsDnAction = "/kp1900/kp1970Excel.do";
String detailAction = "/kp1900/kp1971.do";
String targetAction = "/address/AddressAPI.do";
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
                  , '일련번호'
                  , '창고코드'   
                  , '창고명'
                  , '창고상세주소'
                  , '우편번호지번'
                  , '기본주소지번'
                  , '상세주소지번'
                  , '우편번호도로'
                  , '기본주소도로'                  
                  , '상세주소도로'                  
                  , '비고'                  
                  ];

                  var colModel01 = [
                  				{name:'rowNum', index:'assetSeq', width:'0px', hidden:true}
                  				,{name:'mWarehouseId', index:'mWareHouseId', width:'100px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
                  				,{name:'warehouseCode', index:'warehouseCode', width:'100px', align:'CENTER', columntype:'text', sortable:false,  classes:'grid-col-TEXT'}
                  				,{name:'name', index:'name', width:'200px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
                  				,{name:'warehouseAddr', index:'warehouseAddr', width:'200px', align:'CENTER', columntype:'text', sortable:false, classes:'grid-col-TEXT'}
                                ,{name:'postalD', index:'postalD', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT',
                                	formatter: function(value, options, rData)
                					{
                						if (value == null) value = "";
                						value = '<input type="hidden" id="postalD_'+options.rowId+'" value="'+ value +'" />';
                						return value;
                					}
                                }	                                	                                                               
                                ,{name:'address1D', index:'address1D', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT',
                					formatter: function(value, options, rData)
                    				{
                    					if (value == null) value = "";
                    					value = '<input type="hidden" id="address1D_'+options.rowId+'" value="'+ value +'" />';
                    					return value;
                    				}
                				 }	
                                ,{name:'address2D', index:'address2D', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT',
                    				formatter: function(value, options, rData)
                        			{
            						    if (value == null) value = "";
            						    value = '<input type="hidden" id="address1D_'+options.rowId+'" value="'+ value +'" />';
            						    return value;
            					     }
                    			  }
                                ,{name:'postalR', index:'postalR', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT',	
            					    formatter: function(value, options, rData)
                					{
            						    if (value == null) value = "";
            						    value = '<input type="hidden" id="postalR_'+options.rowId+'" value="'+ value +'" />';
            						    return value;
            					    }
            					  }	  
                                ,{name:'address1R', index:'address2R', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT',
            					    	formatter: function(value, options, rData)
                    				{
                						    if (value == null) value = "";
                						    value = '<input type="hidden" id="postalR_'+options.rowId+'" value="'+ value +'" />';
                						    return value;
                					}
            					   }
                                ,{name:'address2R', index:'address2R', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT',
                                	formatter: function(value, options, rData)
                    				{
                						    if (value == null) value = "";
                						    value = '<input type="hidden" id="postalR_'+options.rowId+'" value="'+ value +'" />';
                						    return value;
                					}
            					   }
                                ,{name:'description', index:'description', width:'200px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false ,
                                	formatter: function(value, options, rData)
                					{
                						if (value == null) value = "";

                						<% if (ssAuthManager) { %>
                						value += ' <span class="button"><input type="button" value="우편번호" onclick="fnAddress();"></span>';                  						
                						<% } %>

                						return value;
                					}}
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
		shrinkToFit: true,
		cellEdit : false,
// 		sortname: 'repoDt',
// 		sortorder: 'desc',
		sortable : true,
		width: $(window).width() - widthHip,
		height: $(window).height() - heightHip,
		multiselect: false,
		loadError: function(xhr,status,error){
			alert(status+'\n'+error);
		},
		onSortCol : function(index, columnIndex, sortOrder) {
 			//alert(index + " : " + columnIndex + " : " + sortOrder);
 			$('#dataOrder').val(index);
 			$('#dataOrderArrow').val(sortOrder);
 			fnGridReload("1");
		},
        onSelectRow: function(ids){
		  	
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

function fnAddress() {		
	fnOpenPop("/address/AddressAPI.do", "kp1970Pop", "650", "750","");
}

//사용자 콜백
function fnSetKp1970(postcode,roadAddress,jibunAddress,detailAddress,extraAddress) { 
	
    $('#postalD_' + $('#kp1970rowid').val()).val(postcode);
	$('#postalR_' + $('#kp1970rowid').val()).val(postcode);
	$('#address1D_' + $('#kp1970rowid').val()).val(jibunAddress);
	$('#address2D_' + $('#kp1970rowid').val()).val(extraAddress);
	$('#address1R_' + $('#kp1970rowid').val()).val(roadAddress);
	$('#address2R_' + $('#kp1970rowid').val()).val(extraAddress);
	
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

