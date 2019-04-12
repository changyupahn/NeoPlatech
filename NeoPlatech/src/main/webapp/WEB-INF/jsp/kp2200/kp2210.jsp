<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>    
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>    
<%
String pageTitle = "부자재입고관리";
String curAction = "/kp2200/kp2210.do";
String curGridAction = "/kp2200/kp2210Ajax.do";
String curSearchAction = "/kp2200/kp2210Search.do";
String xlsDnAction = "/kp2200/kp2210Excel.do";
String detailAction = "/kp2200/kp2210DetailAjax.do";
String stockAction = "/kp2200/kp2210Stock.do";
String recallAction = "/kp2200/kp2210Recall.do";
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
                  ,'oper'
                  , 'LG주문번호'                                
                  , '모델명'                   
                  , 'LG일정'
                  , '선행일정'
                  , 'NEO일정'
                  , '업체명'
                  , '실제주문품번'
                  , '부품명칭' 
                  , '발주진행일자'   
                  , '확정일자'  
                  , '제품번호'
                  , '제품명'                               
                  , '단위'
                  , '일련번호'
                  , '사급여부'                                                                                                                      
                  , '공급방향'               
                  , '담당자'
                  , '생산LINE'
                  , '단위소요수량'
                  , '주문수량'
                  , '재고대기량'                  
                  , '현재고량' 
                  , '현재입고량'
                  , '관리'
                  ];
                  var colModel01 = [
                  {name:'rowNum', index:'rowNum', width:'0px', hidden:true}
                  ,{name:'oper', index:'oper', width:'0px', hidden:true}
                  ,{name:'demandId', index:'demandId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                                
                  ,{name:'tool', index:'tool', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                 
                  ,{name:'lgeDate', index:'lgeDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                  ,{name:'gapDay', index:'gapDay', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER' , hidden:true , formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'neoDate', index:'neoDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                  ,{name:'vendor', index:'vendor', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' }
                  ,{name:'subPartNo', index:'subPartNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' }
                  ,{name:'subPartName', index:'subPartName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' }
                  ,{name:'inDate', index:'inDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true} 
                  ,{name:'chkDay', index:'chkDay', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}  
                  ,{name:'mPartNo', index:'mPartNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                  ,{name:'lgmPartName', index:'lgmPartName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                
                  ,{name:'unit', index:'unit', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                  ,{name:'odId', index:'odId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                  ,{name:'osp', index:'osp', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                                                                                                                       
                  ,{name:'outPlace', index:'outPlace', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', hidden:true}                  
                  ,{name:'myCom', index:'myCom', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', hidden:true}
                  ,{name:'lgLine', index:'lgLine', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', hidden:true}
                  ,{name:'bomQty', index:'bomQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', hidden:true , formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'sumQtyCng', index:'sumQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'preQtyOnHand', index:'preQtyOnHand', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'qtyOnHand', index:'qtyOnHand', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}               
                  ,{name:'qtyinvoiced', index:'qtyinvoiced', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER',editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                  ,{name:'mgr', index:'mgr', align:'CENTER', width:'200px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
    					formatter: function(value, options, rData)
    					{
    						if (value == null) value = "";
    						var id = options.rowId;
    						<% if (ssAuthManager) { %>
    						value += ' <span class="button"><input type="button" value="입고" onclick="fnStock('+ options.rowId +', '+ rData.odId +',  '+ rData.qtyinvoiced +');"></span>';    						
    						value += ' <span class="button"><input type="button" value="반품" onclick="fnRecall('+ options.rowId +','+ rData.odId +', '+ rData.qtyinvoiced +');"></span>';
    						<% } %>

    						return value;
    					}}
                  ];
                  
                  var colNames02 = ['rowNum'
                                    , 'LG주문번호'                                
                                    , '모델명'                   
                                    , 'LG일정'
                                    , '선행일정'
                                    , 'NEO일정'
                                    , '업체명'
                                    , '실제주문품번'
                                    , '부품명칭' 
                                    , '발주진행일자'   
                                    , '확정일자'  
                                    , '제품번호'
                                    , '제품명'                               
                                    , '단위'
                                    , '일련번호'
                                    , '사급여부'                                                                                                                      
                                    , '공급방향'               
                                    , '담당자'
                                    , '생산LINE'
                                    , '소요수량'
                                    , '단위소요수량'
                                    , '주문수량'
                                    , '재고대기량'   
                                    , '총소요량'                 
                                    , '현재고량' 
                                    , '현재입고량'                                   
                                    ];
                                    var colModel02 = [
                                    {name:'rowNum', index:'rowNum', width:'0px', hidden:true}
                                    ,{name:'demandId', index:'demandId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                                
                                    ,{name:'tool', index:'tool', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                 
                                    ,{name:'lgeDate', index:'lgeDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                                    ,{name:'gapDay', index:'gapDay', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER' , hidden:true , formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                                    ,{name:'neoDate', index:'neoDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT'}
                                    ,{name:'vendor', index:'vendor', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' }
                                    ,{name:'subPartNo', index:'subPartNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' }
                                    ,{name:'subPartName', index:'subPartName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' }
                                    ,{name:'inDate', index:'inDate', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true} 
                                    ,{name:'chkDay', index:'chkDay', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}  
                                    ,{name:'mPartNo', index:'mPartNo', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                                    ,{name:'lgmPartName', index:'lgmPartName', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                
                                    ,{name:'unit', index:'unit', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}
                                    ,{name:'odId', index:'odId', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true , key:true}
                                    ,{name:'osp', index:'osp', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT' , hidden:true}                                                                                                                       
                                    ,{name:'outPlace', index:'outPlace', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', hidden:true}                  
                                    ,{name:'myCom', index:'myCom', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', hidden:true}
                                    ,{name:'lgLine', index:'lgLine', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-TEXT', hidden:true}
                                    ,{name:'sumQty', index:'sumQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', hidden:true , formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                                    ,{name:'bomQty', index:'bomQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', hidden:true , formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                                    ,{name:'planQty', index:'planQty', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                                    ,{name:'preQtyOnHand', index:'preQtyOnHand', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                                    ,{name:'sumQtyCng', index:'sumQtyCng', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
                                    ,{name:'qtyOnHand', index:'qtyOnHand', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false, formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}               
                                    ,{name:'qtyinvoiced', index:'qtyinvoiced', width:'100px', align:'CENTER', columntype:'text', classes:'grid-col-NUMBER', editable:false,formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}                                   
                                    ];                 
                  
var groupHeaders01 = [];
function fnGridList() {
	$("#listInfo01").jqGrid("GridUnload");
	
	$("#listInfo01").jqGrid({
		datatype : 'json',
		url : "<%=curGridAction%>",
		postData : {					
			sRqstVendorCd : $("select[name=sRqstVendorCd]").val(),
			sRqstItemCd : 	$("select[name=sRqstItemCd]").val(),
			sRqstPNoCd : $("select[name=sRqstPNoCd]").val()	,
			searchDtKeywordS : $("#searchDtKeywordS").val(),
			searchDtKeywordE : $("#searchDtKeywordE").val()	
		},
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
		//width: $(window).width() - widthHip,
		//height: $(window).height() - heightHip,
		width: $(window).width() ,
		height: $(window).height() ,
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
			//fnDetail(rowId);
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
		},
		onSelectRow: function(rowId){
			
			var lastSel = "";
			
			if(rowId && rowId!==lastSel){
				
			        jQuery('#listInfo01').restoreRow(lastSel); 
			        lastSel=rowId;
			        
			     }
			     jQuery('#listInfo01').editRow(rowId, true); 
			     
			$("#listInfo01").jqGrid('setGridParam', {cellEdit: true}); 
			var selRowId = "";
		
			if (rowId) {
				selRowId = rowId;
			} else {
				selRowId = $("#listInfo01").getGridParam('selrow');
			}
			
			if (selRowId) {
				
				var obj = $("#listInfo01").jqGrid('getRowData', selRowId);
				//jQuery("#listInfo01").setColProp('qtyinvoiced',{editable:true});
				//$("#listInfo01").jqGrid('setCell', rowId, 'qtyinvoiced', obj.qtyinvoiced);
			
			}
		},
		afterEditCell:function(rowid, cellname, value, iRow, iCol){
		    $("#" + rowid + "_" + cellname).blur(function(){
		        $("#listInfo01").jqGrid("saveCell",iRow,iCol);
		   });
		},  
		afterInsertRow: function (rowid, rowData, rowelem) {
         // alert(rowid + "|" + rowData.InputMaterial + "|" + rowelem);
			
		},
		afterSaveCell: function (rowid, name, val, iRow, iCol) {
			
			var selRowId = "";
		
			if (rowid) {
				selRowId = rowid;
			} else {
				selRowId = $("#listInfo01").getGridParam('selrow');
			}
		
			if (selRowId) {
			
				var obj = $("#listInfo01").jqGrid('getRowData', selRowId);
		
			}
			
			if ($("#listInfo01").jqGrid('getCell', rowid, "oper") == 'U') {
				$("#listInfo01").jqGrid('setRowData', rowid, { status: "수정", qtyinvoiced: obj.qtyinvoiced });
			}
			
			var changeResultRows =  getChangedGridInfo("#listInfo01");

			function getChangedGridInfo(gridId) {

					var changedData = $.grep($("#listInfo01").jqGrid('getRowData'), function (obj) {

						return obj.oper == 'I' || obj.oper == 'U';

					});

					return changedData;

				}
			
		},
		subGrid: true,
	    subGridRowExpanded: function(subgrid_id, row_id) {
	    // we pass two parameters
	    // subgrid_id is a id of the div tag created within a table
	    // the row_id is the id of the row
	    // If we want to pass additional parameters to the url we can use
	    // the method getRowData(row_id) - which returns associative array in type name-value
	    // here we can easy construct the following
	      var selRowId = "";
	      if (row_id) {
		    selRowId = row_id;
	      } else {
		    selRowId = $("#listInfo01").getGridParam('selrow');
	      }

	if (selRowId) {
		var obj = $("#listInfo01").jqGrid('getRowData', selRowId);
	       
	       var subgrid_table_id;
	       subgrid_table_id = subgrid_id+"_t";
	       jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
	       jQuery("#"+subgrid_table_id).jqGrid({
	    	   datatype : 'json',
	   		   url : "<%=detailAction%>",
	   		   postData :{
	   			odId: obj.odId ,	
	   			demandId : obj.demandId,
				sRqstVendorCd : obj.vendor,
				sRqstItemCd : 	obj.subPartName,
				sRqstPNoCd : obj.subPartNo	,
				searchDtKeywordS : $("#searchDtKeywordS").val(),
				searchDtKeywordE : $("#searchDtKeywordE").val()	
			  },
	   		   mtype:'POST',
	   		   jsonReader : {
	   			root    : "resultList",
	   			repeatitems : false
	   		  },
	      	  colNames : colNames02,
			  colModel : colModel02,
	          height: '100%',
	          rowNum:20,
	          //sortname: 'num',
	          //sortorder: "asc"
	       });
	       
	     } else {
			alert("상세보기 할 내역을 선택해 주세요");
			return;
		 } 
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
	frm.searchDtKeywordS.value = $("#searchDtKeywordS").val();
	frm.searchDtKeywordE.value = $("#searchDtKeywordE").val();
	
	$("#listInfo01").setGridParam({
		postData: $('#sForm').serializeObject()
	}).trigger("reloadGrid");
	
}
function fnSearch(){
	 	
	//fnGridReload("1");	
	fnGridList();
}
function fnXlsDn(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";	
	frm.sRqstVendorCd.value = $("select[name=sRqstVendorCd]").val();
	frm.sRqstItemCd.value = $("select[name=sRqstItemCd]").val();
	frm.sRqstPNoCd.value = $("select[name=sRqstPNoCd]").val();
	frm.searchDtKeywordS.value = $("#searchDtKeywordS").val();
	frm.searchDtKeywordE.value = $("#searchDtKeywordE").val();
		
	
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
		frm.sRqstVendorCd.value = $("select[name=sRqstVendorCd]").val();
		frm.sRqstItemCd.value = $("select[name=sRqstItemCd]").val();
		frm.sRqstPNoCd.value = $("select[name=sRqstPNoCd]").val();
		frm.searchDtKeywordS.value = $("#searchDtKeywordS").val();
		frm.searchDtKeywordE.value = $("#searchDtKeywordE").val();
		frm.sOdId.value = obj.odId;
		
		
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

//위치코드 팝업
function kp2210Pop() {
	
	fnOpenPop("/kp2210/kp2210KeyPad.do", "kp2210Pop", "400", "600","");
	//fnOpenPop("/kp2210/kp2210KeyPad.do", "kp2210Pop");
}

function fnQtyInvoiced(qty){
	var frm = document.sForm;			
	frm.qtyinvoiced.value = qty;
	
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];
	if (ids.length == 0) {
		alert("입고 처리할 행을 선택해주세요.");
		return;
	}
	//alert(frm.qtyinvoiced.value);
    if(frm.qtyinvoiced.value != null){    	
	
	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					odId: odId ,					
					sReceiptCnt : document.sForm.qtyinvoiced.value, 
					sRqstVendorCd : $("select[name=sRqstVendorCd]").val(),
					sRqstItemCd : 	$("select[name=sRqstItemCd]").val(),
					sRqstPNoCd : $("select[name=sRqstPNoCd]").val()						
			};
			saveJsonArray.push(saveJsonObj);
		}
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

function fnStock(row_id, odId, qtyinvoiced){
	var frm = document.sForm;
	kp2210Pop();
	
	
}

//위치코드 팝업
function kp2211Pop() {
	
	fnOpenPop("/kp2210/kp2211KeyPad.do", "kp2211Pop", "400", "600","");
	//fnOpenPop2("/kp2210/kp2211KeyPad.do", "kp2211Pop");
}

function fnQtyRecallInvoiced(qty){
	var frm = document.sForm;			
	frm.qtyinvoiced.value = qty;
	
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];
	if (ids.length == 0) {
		alert("반품 처리할 행을 선택해주세요.");
		return;
	}
		
	//alert(frm.qtyinvoiced.value);
    if(frm.qtyinvoiced.value != null){ 
	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					odId: odId ,					
					sReceiptCnt : document.sForm.qtyinvoiced.value, 
					sRqstVendorCd : $("select[name=sRqstVendorCd]").val(),
					sRqstItemCd : 	$("select[name=sRqstItemCd]").val(),
					sRqstPNoCd : $("select[name=sRqstPNoCd]").val()						
			};
			saveJsonArray.push(saveJsonObj);
		}
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

function fnRecall(row_id, odId,  qtyinvoiced){
	var frm = document.sForm;
	kp2211Pop();
	
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
	<input type="hidden" id="searchDtKeywordS" name="searchDtKeywordS" value="<%=cmRequest.getString("searchDtKeywordS", "")%>" />
	<input type="hidden" id="searchDtKeywordE" name="searchDtKeywordE" value="<%=cmRequest.getString("searchDtKeywordE", "")%>" />
	<input type="hidden" id="odId" name="odId" value="<%=cmRequest.getString("odId", "")%>" />
	<input type="hidden" id="qtyinvoiced" name="qtyinvoiced" value="<%=cmRequest.getString("qtyinvoiced", "")%>" />
	
   
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
        <!-- <span class="button"><input type="button" value="입고처리" onclick="fnStock();"></span>
		<span class="button"><input type="button" value="반품처리" onclick="fnRecall();"></span> -->		
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