<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "납품내역";
String curAction = "/kp1200/kp1211.do";
String curGridAction = "/kp1200/kp1211Ajax.do";
String procAction = "/kp1200/kp1211Proc.do";
String delProcAction = "/kp1200/kp1211DelProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

//공통코드
String com001Str = RequestUtil.getString(request, "com001Str"); //상각법
String com002Str = RequestUtil.getString(request, "com002Str"); //사용여부
String com005Str = RequestUtil.getString(request, "com005Str"); //활용범위
String com006Str = RequestUtil.getString(request, "com006Str"); //취득방법
String com013Str = RequestUtil.getString(request, "com013Str"); //통화단위
%>
<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">
var widthHip = 5;
var heightHip = 220;

$(window).resize(function(){
	fnGridResize();
});

function fnGridResize() {
	$("#listInfo01").setGridWidth($(window).width() - widthHip);
	$("#listInfo01").setGridHeight($(window).height() - heightHip);
}

var colNames01 = [
                '검수품목일련번호','구매번호','신청번호','물품일련번호',
				'자산구분코드','품목코드','품목명','표준분류코드','표준분류명','상세명칭','영문명','제조국코드','제조국','제조업체코드','제조업체','판매업체코드','판매업체',
				'수량','수량단위','단가','금액(원)','외환','통화단위','계정번호','내용연수','상각법','규격','취득방법','사용목적','소프트웨어','ZEUS장비','ETUBE장비'
				];
var colModel01 = [
				{name:'inspItemSeq', index:'inspItemSeq', width:'0px', hidden:true},
				{name:'purno', index:'purno', width:'0px', hidden:true},
				{name:'rqstno', index:'rqstno', width:'0px', hidden:true},
				{name:'prodno', index:'prodno', width:'0px', hidden:true},
				{name:'assetTypeCd', index:'assetTypeCd', width:'0px', hidden:true, sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="hidden" id="assetTypeCd_'+options.rowId+'" value="'+ value +'" />';
						return value;
					}},
				{name:'itemCd', index:'itemCd', width:'0px', hidden:true, sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="hidden" id="itemCd_'+options.rowId+'" value="'+ value +'" />';
						return value;
					}},
				{name:'itemName', index:'itemName', align:'CENTER', width:'150px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="itemName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def srch1" onclick="kp9011PopCustom('+options.rowId+');" />';
						return value;
					}},
				{name:'zeusItemCd', index:'zeusItemCd', width:'0px', hidden:true, sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="hidden" id="zeusItemCd_'+options.rowId+'" value="'+ value +'" />';
						return value;
					}},
				{name:'zeusItemName', index:'zeusItemName', align:'CENTER', width:'150px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="zeusItemName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def srch2" onclick="kp9012PopCustom('+options.rowId+');" />';
						return value;
					}},
				{name:'assetName', index:'assetName', align:'CENTER', width:'300px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="assetName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'assetEname', index:'assetEname', align:'CENTER', width:'120px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="assetEname_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'mkNationCd', index:'mkNationCd', width:'0px', hidden:true, sortable:false, editable:false,
						formatter: function(value, options, rData)
						{
							if (value == null) value = "";
							value = '<input type="hidden" id="mkNationCd_'+options.rowId+'" value="'+ value +'" />';
							return value;
						}},
				{name:'mkNationName', index:'mkNationName', align:'CENTER', width:'90px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="mkNationName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def srch2" onclick="kp9050Pop(\''+options.rowId+'\')" />';
						return value;
					}},
				{name:'mkCompanyCd', index:'mkCompanyCd', width:'0px', hidden:true, sortable:false, editable:false,
						formatter: function(value, options, rData)
						{
							if (value == null) value = "";
							value = '<input type="hidden" id="mkCompanyCd_'+options.rowId+'" value="'+ value +'" />';
							return value;
						}},
				{name:'mkCompanyName', index:'mkCompanyName', align:'CENTER', width:'150px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="mkCompanyName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def srch2" onclick="kp9060Pop(\''+options.rowId+'\')" />';
						return value;
					}},
				{name:'slCompanyCd', index:'slCompanyCd', width:'0px', hidden:true, sortable:false, editable:false,
						formatter: function(value, options, rData)
						{
							if (value == null) value = "";
							value = '<input type="hidden" id="slCompanyCd_'+options.rowId+'" value="'+ value +'" />';
							return value;
						}},
				{name:'slCompanyName', index:'slCompanyName', align:'CENTER', width:'150px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="slCompanyName_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def srch2" onclick="kp9070Pop(\''+options.rowId+'\')" />';
						return value;
					}},
				{name:'assetCnt', index:'assetCnt', align:'CENTER', width:'70px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="assetCnt_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="amt" />';
						return value;
					}},
				{name:'assetUnitCd', index:'assetUnitCd', align:'CENTER', width:'70px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="assetUnitCd_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'aqusitUnitAmt', index:'aqusitUnitAmt', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="aqusitUnitAmt_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="amt" />';
						return value;
					}},
				{name:'aqusitAmt', index:'aqusitAmt', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="aqusitAmt_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="amt" />';
						return value;
					}},
				{name:'aqusitForeignAmt', index:'aqusitForeignAmt', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="aqusitForeignAmt_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="amt" />';
						return value;
					}},
				{name:'capiTypeCd', index:'capiTypeCd', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<select id="capiTypeCd_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><option value="">선택</option><%=com013Str%></select>';
						return value;
					}},
				{name:'accntNum', index:'accntNum', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="accntNum_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'usefulLife', index:'usefulLife', align:'CENTER', width:'70px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="usefulLife_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'depreCd', index:'depreCd', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<select id="depreCd_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><%=com001Str%></select>';
						return value;
					}},
				{name:'assetSize', index:'assetSize', align:'CENTER', width:'200px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="assetSize_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'aqusitTypeCd', index:'aqusitTypeCd', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<select id="aqusitTypeCd_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><%=com006Str%></select>';
						return value;
					}},
				{name:'purposeOfUse', index:'purposeOfUse', align:'CENTER', width:'200px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "";
						value = '<input type="text" id="purposeOfUse_'+options.rowId+'" custrowid="'+options.rowId+'" value="'+ value +'" class="def" />';
						return value;
					}},
				{name:'swYn', index:'swYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "N";
						value = '<select id="swYn_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><%=com002Str%></select>';
						return value;
					}},
				{name:'zeusYn', index:'zeusYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "N";
						value = '<select id="zeusYn_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><%=com002Str%></select>';
						return value;
					}},
				{name:'etubeYn', index:'etubeYn', align:'CENTER', width:'100px', columntype:'text', classes:'grid-col-TEXT', sortable:false, editable:false,
					formatter: function(value, options, rData)
					{
						if (value == null) value = "N";
						value = '<select id="etubeYn_'+options.rowId+'" custrowid="'+options.rowId+'" class="def"><%=com002Str%></select>';
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
		rownumbers: true,
	    rowNum:-1,
		gridview:true,
		viewrecords: true,
		shrinkToFit: false,
		//cellEdit : true,
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
		},
		onSelectRow: function(rowid) {
		},
		ondblClickRow:function(rowId){
		},
		loadComplete : function(data) {
			fnGridInvalidSession(data.totalRow);
		},
		gridComplete : function() {
			/* $('.def').focus(function(){
				var rowid = $(this).attr("custrowid");
				$("#listInfo01").setSelection(rowid, true);
			}); */
		}
	});

}

function fnSave() {
	var ids = jQuery("#listInfo01").jqGrid('getDataIDs');
	var saveJsonArray = [];

	if (ids.length > 0) {
		for (var i=0; i<ids.length; i++) {
			var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
			var saveJsonObj = {
					inspItemSeq: obj.inspItemSeq,
					purno: obj.purno,
					rqstno: obj.rqstno,
					prodno: obj.prodno,
					assetTypeCd: $('#assetTypeCd'+'_'+ids[i]).val(),
					itemCd: $('#itemCd'+'_'+ids[i]).val(),
					itemName: $('#itemName'+'_'+ids[i]).val(),
					zeusItemCd: $('#zeusItemCd'+'_'+ids[i]).val(),
					zeusItemName: $('#zeusItemName'+'_'+ids[i]).val(),
					assetName: $('#assetName'+'_'+ids[i]).val(),
					assetEname: $('#assetEname'+'_'+ids[i]).val(),
					mkNationCd: $('#mkNationCd'+'_'+ids[i]).val(),
					mkNationName: $('#mkNationName'+'_'+ids[i]).val(),
					mkCompanyCd: $('#mkCompanyCd'+'_'+ids[i]).val(),
					mkCompanyName: $('#mkCompanyName'+'_'+ids[i]).val(),
					slCompanyCd: $('#slCompanyCd'+'_'+ids[i]).val(),
					slCompanyName: $('#slCompanyName'+'_'+ids[i]).val(),
					assetCnt: $('#assetCnt'+'_'+ids[i]).val(),
					assetUnitCd: $('#assetUnitCd'+'_'+ids[i]).val(),
					aqusitUnitAmt: $('#aqusitUnitAmt'+'_'+ids[i]).val(),
					aqusitAmt: $('#aqusitAmt'+'_'+ids[i]).val(),
					aqusitForeignAmt: $('#aqusitForeignAmt'+'_'+ids[i]).val(),
					capiTypeCd: $('#capiTypeCd'+'_'+ids[i]).val(),
					accntNum: $('#accntNum'+'_'+ids[i]).val(),
					usefulLife: $('#usefulLife'+'_'+ids[i]).val(),
					depreCd: $('#depreCd'+'_'+ids[i]).val(),
					assetSize: $('#assetSize'+'_'+ids[i]).val(),
					aqusitTypeCd: $('#aqusitTypeCd'+'_'+ids[i]).val(),
					purposeOfUse: $('#purposeOfUse'+'_'+ids[i]).val(),
					swYn: $('#swYn'+'_'+ids[i]).val(),
					zeusYn: $('#zeusYn'+'_'+ids[i]).val(),
					etubeYn: $('#etubeYn'+'_'+ids[i]).val()
			};
			saveJsonArray.push(saveJsonObj);
		}
		//alert( JSON.stringify(saveJsonArray) );
		if (confirm("저장 하시겠습니까?")) {
			fnLoadingS2();

			$.ajax({
				type : "POST",
				url : "<%=procAction%>",
				data : {
					purno : $('#purno').val(),
					saveJsonArray : JSON.stringify(saveJsonArray)
				},
				dataType : "json",
				success:function(data)
				{
					if (data.ret == "OK") {
						alert("저장 되었습니다.");
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

//행복사
function fnCopyRow() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var ids2 = jQuery("#listInfo01").jqGrid('getDataIDs');

	if (ids.length == 0) {
		alert("복사할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
		//$("#listInfo01").addRowData(ids2.length+1, obj, position, srcrowid);
		var addRowid = ids2.length + 1 + i;
		$("#listInfo01").jqGrid('addRowData', addRowid, this);
		//$("#listInfo01").jqGrid('setRowData', addRowid, obj);
		//$('#listInfo02').setCell(no, 'crud', "C"); //crud
		$('#listInfo01').setCell(addRowid, 'inspItemSeq', obj.inspItemSeq);
		$('#listInfo01').setCell(addRowid, 'purno', obj.purno);
		$('#listInfo01').setCell(addRowid, 'rqstno', obj.rqstno);
		$('#listInfo01').setCell(addRowid, 'prodno', obj.prodno);
		$('#assetTypeCd'+'_'+addRowid).val( $('#assetTypeCd'+'_'+ids[i]).val());
		$('#itemCd'+'_'+addRowid).val( $('#itemCd'+'_'+ids[i]).val());
		$('#itemName'+'_'+addRowid).val( $('#itemName'+'_'+ids[i]).val());
		$('#zeusItemCd'+'_'+addRowid).val( $('#zeusItemCd'+'_'+ids[i]).val());
		$('#zeusItemName'+'_'+addRowid).val( $('#zeusItemName'+'_'+ids[i]).val());
		$('#assetName'+'_'+addRowid).val( $('#assetName'+'_'+ids[i]).val());
		$('#assetEname'+'_'+addRowid).val( $('#assetEname'+'_'+ids[i]).val());
		$('#mkNationCd'+'_'+addRowid).val( $('#mkNationCd'+'_'+ids[i]).val());
		$('#mkNationName'+'_'+addRowid).val( $('#mkNationName'+'_'+ids[i]).val());
		$('#mkCompanyCd'+'_'+addRowid).val( $('#mkCompanyCd'+'_'+ids[i]).val());
		$('#mkCompanyName'+'_'+addRowid).val( $('#mkCompanyName'+'_'+ids[i]).val());
		$('#slCompanyCd'+'_'+addRowid).val( $('#slCompanyCd'+'_'+ids[i]).val());
		$('#slCompanyName'+'_'+addRowid).val( $('#slCompanyName'+'_'+ids[i]).val());
		$('#assetCnt'+'_'+addRowid).val( $('#assetCnt'+'_'+ids[i]).val());
		$('#assetUnitCd'+'_'+addRowid).val( $('#assetUnitCd'+'_'+ids[i]).val());
		$('#aqusitUnitAmt'+'_'+addRowid).val( $('#aqusitUnitAmt'+'_'+ids[i]).val());
		$('#aqusitAmt'+'_'+addRowid).val( $('#aqusitAmt'+'_'+ids[i]).val());
		$('#aqusitForeignAmt'+'_'+addRowid).val( $('#aqusitForeignAmt'+'_'+ids[i]).val());
		$('#capiTypeCd'+'_'+addRowid).val( $('#capiTypeCd'+'_'+ids[i]).val());
		$('#accntNum'+'_'+addRowid).val( $('#accntNum'+'_'+ids[i]).val());
		$('#usefulLife'+'_'+addRowid).val( $('#usefulLife'+'_'+ids[i]).val());
		$('#depreCd'+'_'+addRowid).val( $('#depreCd'+'_'+ids[i]).val());
		$('#assetSize'+'_'+addRowid).val( $('#assetSize'+'_'+ids[i]).val());
		$('#aqusitTypeCd'+'_'+addRowid).val( $('#aqusitTypeCd'+'_'+ids[i]).val());
		$('#purposeOfUse'+'_'+addRowid).val( $('#purposeOfUse'+'_'+ids[i]).val());
		$('#swYn'+'_'+addRowid).val( $('#swYn'+'_'+ids[i]).val());
		$('#zeusYn'+'_'+addRowid).val( $('#zeusYn'+'_'+ids[i]).val());
		$('#etubeYn'+'_'+addRowid).val( $('#etubeYn'+'_'+ids[i]).val());
	}
}

//저장 버튼을 눌러야 실제 삭제 됨.
function fnDelRow2() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');

	if (ids.length == 0) {
		alert("삭제할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		$('#listInfo01').delRowData(ids[i]);
	}
}

//바로삭제
function fnDelRow() {
	var ids = $('#listInfo01').jqGrid('getGridParam', 'selarrrow');
	var saveJsonArray = [];

	if (ids.length == 0) {
		alert("삭제할 행을 선택해주세요.");
		return;
	}

	for (var i=0; i<ids.length; i++) {
		var obj = $("#listInfo01").jqGrid('getRowData', ids[i]);
		var saveJsonObj = {
				inspItemSeq: obj.inspItemSeq,
				purno: obj.purno,
				rqstno: obj.rqstno,
				prodno: obj.prodno
		};
		saveJsonArray.push(saveJsonObj);
	}

	if (confirm("삭제 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=delProcAction%>",
			data : {
				purno : $('#purno').val(),
				saveJsonArray : JSON.stringify(saveJsonArray)
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

//품목코드 팝업
var rowid9010 = 0;
function kp9010PopCustom(rowid) {
	rowid9010 = rowid;
	kp9010Pop();
}

//품목코드 팝업
var rowid9011 = 0;
function kp9011PopCustom(rowid) {
	rowid9011 = rowid;
	kp9011Pop();
}

//표준분류코드 팝업
var rowid9012 = 0;
function kp9012PopCustom(rowid) {
	rowid9012 = rowid;
	kp9012Pop();
}

//품목코드 팝업 콜백 함수
function fnSetKp9010(obj) {
	$('#itemCd_'+rowid9010).val(obj.cd);
	$('#itemName_'+rowid9010).val(obj.nm);
	$('#usefulLife_'+rowid9010).val(obj.ny);
	$('#depreCd_'+rowid9010).val(obj.dc);
	$('#assetTypeCd_'+rowid9010).val(obj.atc);
}

//품목코드 팝업 콜백 함수
function fnSetKp9011(obj) {
	$('#itemCd_'+rowid9011).val(obj.cd);
	$('#itemName_'+rowid9011).val(obj.nm);
	$('#usefulLife_'+rowid9011).val(obj.ny);
	$('#depreCd_'+rowid9011).val(obj.dc);
	$('#assetTypeCd_'+rowid9011).val(obj.atc);
}

//품목코드(표준분류체계) 팝업 콜백 함수
function fnSetKp9012(obj) {
	$('#zeusItemCd_'+rowid9012).val(obj.cd);
	$('#zeusItemName_'+rowid9012).val(obj.nm);
}

$(document).ready(function(){

	fnGridList();
});

//제조국 콜백
function fnSetKp9050(obj) {
	$('#mkNationCd_' + $('#kp9050rowid').val()).val(obj.natnCd);
	$('#mkNationName_' + $('#kp9050rowid').val()).val(obj.natnName);
}

//제조업체 콜백
function fnSetKp9060(obj) {
	$('#mkCompanyCd_' + $('#kp9060rowid').val()).val(obj.compCd);
	$('#mkCompanyName_' + $('#kp9060rowid').val()).val(obj.compName);
}

//판매업체 콜백
function fnSetKp9070(obj) {
	$('#slCompanyCd_' + $('#kp9060rowid').val()).val(obj.compCd);
	$('#slCompanyName_' + $('#kp9060rowid').val()).val(obj.compName);
}
</script>

<style type="text/css">
/* selrow highlight disabled */
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
}
</style>

</head>
<body style="overflow:auto;">

	<h2 class="title_left"><%=pageTitle%></h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="purno" name="purno" value="<%=cmRequest.getString("purno")%>" />
	<input type="hidden" id="kp9050rowid" name="kp9050rowid" value="" />
	<input type="hidden" id="kp9060rowid" name="kp9060rowid" value="" />
	<input type="hidden" id="kp9070rowid" name="kp9070rowid" value="" />

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
	</table>

	<table style="width:100%">
	<tr>
	<td width="50%">
		&nbsp;
	</td>
	<td width="50%" style="text-align: right;">
		<% if(ssAuthManager){ %>
		<span class="button"><input type="button" value="저장" onclick="fnSave();"></span>
		<span class="button"><input type="button" value="행복사" onclick="fnCopyRow();"></span>
		<span class="button"><input type="button" value="행삭제" onclick="fnDelRow();"></span>
		<% } %>
		&nbsp;
	</td>
	</tr>
	</table>

	<table id="listInfo01" class="scroll"></table>

	</form>

</body>
</html>



