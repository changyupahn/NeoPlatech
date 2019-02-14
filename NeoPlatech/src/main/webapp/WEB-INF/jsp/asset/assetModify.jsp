<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/asset/writeForm.do";
String procAction = "/asset/writeFormProc.do";
String xlsDnAction = "/asset/excelDn.do";
String xlsUpAction = "/asset/excelUp.do";
String xlsUpStatusAction = "/asset/excelUpStatus.do";
String xlsUpStatusCloseAction = "/asset/excelUpStatusClose.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap view = RequestUtil.getCommonMap(request, "asset");
CommonList assetList = RequestUtil.getCommonList(request, "assetList");
CommonList dispMngList = RequestUtil.getCommonList(request, "dispMngList99");

int no = 0;
if (assetList.size() > 0) {
	no = assetList.size();
}
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
var rowNum = <%=no%>;
function fnSubmit() {
	try{
		var sw = 1;

		$('input[null_yn="N"]').each(function(){
			if ($(this).val() == "") {
				sw = 0;
				alert( $('#th_' + $(this).attr("name")).val()  + "은 필수 입력입니다.");
				return false;
			}
		});

		if (sw == 1) {
			fnXlsUp("<%=procAction%>?proc_mode=multi");
		}
	}catch(e){
		alert(e.description);
	}
}
function fnAdd() {
	try{
		var sw = 1;

		$('input[one_null_yn="N"]').each(function(){
			if ($(this).val() == "") {
				sw = 0;
				alert( $('#th_' + $(this).attr("name")).val()  + "은 필수 입력입니다.");
				return false;
			}
		});

		if (sw == 1) {
			fnXlsUp("<%=procAction%>?proc_mode=one");
		}
	}catch(e){
		alert(e.description);
	}
}
function fnInitOne() {
	$('#objRegOneTbl tbody tr input').val("");
}
function fnRowAdd() {

	if (rowNum >= 100) {
		alert("더이상 추가할 수 없습니다.\n100건 이상의 일괄수정은 엑셀업로드 기능을 이용하여 수정해주세요");
		return;
	}

	//var objLastTR = $('#objRegMultiTbl tbody tr:last').html();
	var objLastTR = $('#objRegTempTbl tbody tr:last').html();
	objLastTR = objLastTR.replace(/namearea/gi, "name");
	objLastTR = objLastTR.replace(/nullynarea/gi, "null_yn");
	objLastTR = objLastTR.replace(/chkrowarea/gi, "chk_row");
	objLastTR = objLastTR.replace(/datepickerarea/gi, "class=\"datepicker2\"");

	$('#objRegMultiTbl tbody').append('<tr></tr>');
	$('#objRegMultiTbl tbody tr:last').append( objLastTR );
	$('#objRegMultiTbl tbody tr:last span.spanRowNum').html( (rowNum+1) );
	$('#objRegMultiTbl tbody tr:last input').val("");
	rowNum++;

	fnInitCalc();
}
function fnRowDel() {
	if ($('#objRegMultiTbl tbody tr').length > 1) {
		if (confirm("마지막 행을 삭제 하시겠습니까?")) {
			$('#objRegMultiTbl tbody tr:last').remove();
			rowNum--;
		}
	} else {
		alert("더 이상 삭제할 수 없습니다.");
	}
}
function fnRowSelChange(ths){
	$(ths).prev().val($(ths).val());
}
function fnXlsDn(){
	location.href = "/asset/excelDn.do";
}
var gFromAction = "";
function fnXlsUp(action){

	var formURL = "<%=xlsUpAction%>";

	if (action != "") {
		formURL = action;
	}
	gFromAction = formURL;

	if (action == "" && $('#xls_file').val() == "") {
		alert("업로드 할 엑셀파일을 선택해 주세요");
		return;
	}

	if (confirm("데이터가 많으면 1분 이상 소요될 수 있습니다.\r\n창을 닫지 마시고 완료될 때까지 기다려 주십시오.\r\n계속 진행하시겠습니까?")) {

		$('#statusArea').append("\r\n처리 진행 중 입니다...\r\n");
		fnLoadingS("처리 진행 중 입니다...", "<%=xlsUpStatusAction%>");

		document.sForm.action = gFromAction;
		document.sForm.target = "amProcFrame";
		document.sForm.submit();

		$('#spanXlsFile').html('<input type="file" name="xls_file" id="xls_file" />');

	}
}
function fnXlsUpCallback(forwardMsg) {
	fnLoadingE("<%=xlsUpStatusCloseAction%>");
	$('#statusArea').append(forwardMsg);
	$('#statusArea').scrollTop($('#statusArea')[0].scrollHeight);
}
$(document).ready(function(){
	/*
	$("#sForm").ajaxForm({
		dataType: "json",
		contentType: false,
		cache: false,
		processData:false,
		beforeSubmit: function(data, frm, opt)
		{
			$('#statusArea').append("처리 진행 중 입니다...\r\n");
			return true;
		},
		success: function(data, textStatus, jqXHR)
		{
			if (data.errorMsg != "") {
				$('#statusArea').append("[ERROR] : " + data.errorMsg + "\r\n");
				alert("[ERROR] : " + data.errorMsg);
			} else {

				var msg = "";
				msg += "저장 완료 되었습니다.\r\n";
				msg += "[처리 결과]\r\n";
				msg += "신규 : " + data.regCnt + " 건\r\n";
				msg += "수정 : " + data.modCnt + " 건\r\n";
				msg += "실패 : " + data.failCnt + " 건\r\n";

				$('#statusArea').append(msg);

				$('#statusArea').scrollTop($('#statusArea')[0].scrollHeight);

				$('#spanXlsFile').html('<input type="file" name="xls_file" id="xls_file" />');

				alert(msg);
			}
		}
	});
	*/

	if (rowNum == 0) {
		fnRowAdd();
	}
});
</script>

</head>
<body style="margin:15px;">

	<h2 class="title_left">물품 수정</h2>

	<h4 style="font-size:14px;color:blue;">엑셀 업로드</h4>

	<form id=sForm name="sForm" method="post" action="<%=curAction%>" enctype="multipart/form-data">
	<!--  <input type="hidden" name="proc_mode" id="proc_mode" value="" /> -->

	<table width="100%" class="table-search" border="0">
	<tr>
		<th width="650px" class="l" style="height:200px; font-size:13px;padding:10px;line-height:190%;">
			엑셀 업로드를 이용하여 물품의 신규등록이나 수정을 할 수 있습니다.
			<br />- 엑셀에서 <strong>물품코드란을 비워두시면 신규등록</strong>으로 인식하며, 그 외에는 수정으로 인식합니다.
			<br />- 아래의 양식파일을 이용하거나 물품현황의 엑셀다운로드한 엑셀 파일을 이용해 주세요.
			<br />- 양식파일의 <strong>항목을 삭제하거나 위치를 변경하시면 안됩니다.</strong>	 충분히 기능을 숙지하시고 사용하시기 바랍니다.
			<br />- 양식파일(xls) <span class="button"><input type="button" value="다운로드" onclick="fnXlsDn();"></span>
			<br />- 엑셀업로드 : <span id="spanXlsFile"><input type="file" name="xls_file" id="xls_file" /></span>
			<span class="button"><input type="button" value="업로드" onclick="fnXlsUp('');"></span>
		</th>
		<td class="l" style="font-size:13px;">
			<textarea id="statusArea" rows="9" cols="50" style="border:0;width:80%;height:200px"></textarea>
		</td>
	</tr>
	</table>

	<h4 style="font-size:14px;color:blue;padding-top:30px;">직접 입력</h4>

	<input type="hidden" name="chk_row" value="1" />

	<table id="objRegOneTbl" width="100%" class="table-cell" border="0" id="tabCont0">
	<colgroup>
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
		<col width="15%" />
	</colgroup>
	<tr>
	<%
	int colCnt = 4;
	if (dispMngList != null) {
		for (int k=0; k<dispMngList.size(); k++) {
			CommonMap dispMng = dispMngList.getMap(k);

			String logical_name = dispMng.getString("logical_name");
			String physical_name = dispMng.getString("physical_name").toLowerCase();
			String data_disp_type = dispMng.getString("data_disp_type");
			String cmcode_yn = dispMng.getString("cmcode_yn");
			String cmcode_id = dispMng.getString("cmcode_id");
			String default_value = dispMng.getString("default_value");
			int default_width = dispMng.getInt("default_width", 100) - 0;
			String null_yn = dispMng.getString("null_yn");

			String val = "";
	%>
	<th>
		<%=logical_name%>
		<% if ("N".equals(null_yn)) { %>
		(*)
		<% } %>
	</th>
	<td>
		<div style="position:relative;">
			<% if ("asset_no".equals(physical_name)) { %>
			<input type="text" name="<%=physical_name%>" value="<%=val%>" readonly="readonly" style="width:98%;background-color:#FAF4C0;" />
			<% } else if ("Y".equals(cmcode_yn) && !"".equals(cmcode_id)) { %>
			<input type="text" name="<%=physical_name%>" value="<%=val%>" one_null_yn="<%=null_yn%>" style="width:98%;" />
			<select style="width:27px;position:absolute;right:0px;top:0px;z-index:99" onchange="fnRowSelChange(this)">
				<option value="">선택</option>
				<c:import url="/code/optionList.do" charEncoding="utf-8">
				<c:param name="param_codeId" value="<%=cmcode_id%>" />
				<c:param name="param_sltValue" value='<%=val%>' />
				</c:import>
			</select>
			<% } else if ("1".equals(data_disp_type)) { %>
			<input type="text" name="<%=physical_name%>" value="<%=val%>" one_null_yn="<%=null_yn%>" style="width:98%;" />
			<% } else if ("2".equals(data_disp_type)) { %>
			<input type="text" name="<%=physical_name%>" value="<%=val%>" one_null_yn="<%=null_yn%>" style="width:98%;" />
			<% } else if ("3".equals(data_disp_type)) { %>
			<input type="text" name="<%=physical_name%>" value="<%=val%>" one_null_yn="<%=null_yn%>" style="width:150px;" />
			<% } else if ("4".equals(data_disp_type)) { %>
			<input type="text" name="<%=physical_name%>" value="<%=val%>" one_null_yn="<%=null_yn%>" class="datepicker" style="width:150px;" />
			<% } %>
		</div>
	</td>
	<%
			if ((k+1) % colCnt == 0 && (k+1) < dispMngList.size()) {
				out.print("</tr><tr>");
			}
		}
		if (dispMngList.size() % colCnt > 0) {
			for (int k=0; k<(colCnt-(dispMngList.size()%colCnt)); k++) {
				out.print("<th></th><td></td>");
			}
		}
	}
	%>
	</tr>
	</table>

	<div class="buttonDiv">
		<span class="button"><input type="button" value="초기화" onclick="fnInitOne();"></span>
		<span class="button"><input type="button" value="추가" onclick="fnAdd();"></span>
	</div>

	<h4 style="font-size:14px;color:blue;padding-top:30px;">직접 입력(Multi-row)</h4>

	<div class="buttonDiv" style="text-align:left">
		<span class="button"><input type="button" value="행추가" onclick="fnRowAdd();"></span>
		<span class="button"><input type="button" value="행삭제" onclick="fnRowDel();"></span>
		<span class="button"><input type="button" value="저장" onclick="fnSubmit();"></span>
	</div>

	<table id="objRegMultiTbl" class="table-cell" summary="" style="width:3010px;table-layout: fixed;" >
	<colgroup>
		<col width="60px" />
		<%
		if (dispMngList != null) {
			for (int k=0; k<dispMngList.size(); k++) {
				CommonMap dispMng = dispMngList.getMap(k);
				int default_width = dispMng.getInt("default_width", 100);
		%>
		<col width="<%=default_width%>px" />
		<%
			}
		}
		%>
	</colgroup>
	<thead>
		<tr>
			<th class="title">No</th>
			<%
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);
					int default_width = dispMng.getInt("default_width", 100);
					String null_yn = dispMng.getString("null_yn");
			%>
			<th class="title dispType<%=dispMng.getString("data_disp_type")%>">
				<%=dispMng.getString("logical_name")%>
				<input type="hidden" id="th_<%=dispMng.getString("physical_name").toLowerCase()%>" value="<%=dispMng.getString("logical_name")%>" />
				<% if ("N".equals(null_yn)) { %>
				(*)
				<% } %>
			</th>
			<%
				}
			}
			%>
		</tr>
	</thead>
	<tbody>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(assetList.totalRow, assetList.pageIdx, assetList.pageSize);
		for(int i=0; i<assetList.size(); i++){
			CommonMap asset = assetList.getMap(i);
		%>
		<tr>
			<th class="title">
				<span class="spanRowNum"><%=i+1%></span>
				<input type="hidden" name="chk_row" value="1" />
			</th>
			<%
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);

					String logical_name = dispMng.getString("logical_name");
					String physical_name = dispMng.getString("physical_name").toLowerCase();
					String data_disp_type = dispMng.getString("data_disp_type");
					String cmcode_yn = dispMng.getString("cmcode_yn");
					String cmcode_id = dispMng.getString("cmcode_id");
					String default_value = dispMng.getString("default_value");
					int default_width = dispMng.getInt("default_width", 100) - 0;
					String null_yn = dispMng.getString("null_yn");

					String val = asset.getString(dispMng.getString("physical_name"));
					if ("1".equals(dispMng.getString("data_disp_type"))) {			//기본형

					} else if ("2".equals(dispMng.getString("data_disp_type"))) {	//문자형

					} else if ("3".equals(dispMng.getString("data_disp_type"))) {	//숫자형
						val = StringUtil.commaNum(val);
					} else if ("4".equals(dispMng.getString("data_disp_type"))) {	//날짜형
						val = DateUtil.formatDateTime(val, "-", ":");
					}
			%>
			<td class="dispType<%=dispMng.getString("data_disp_type")%>">
				<div style="position:relative;">
					<% if ("asset_no".equals(physical_name)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" readonly="readonly" style="width:98%;background-color:#FAF4C0;" />
					<% } else if ("Y".equals(cmcode_yn) && !"".equals(cmcode_id)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" null_yn="<%=null_yn%>" style="width:98%;" />
					<select style="width:27px;position:absolute;right:0px;top:0px;z-index:99" onchange="fnRowSelChange(this)">
						<option value="">선택</option>
						<c:import url="/code/optionList.do" charEncoding="utf-8">
						<c:param name="param_codeId" value="<%=cmcode_id%>" />
						<c:param name="param_sltValue" value='<%=val%>' />
						</c:import>
					</select>
					<% } else if ("1".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" null_yn="<%=null_yn%>" style="width:98%;" />
					<% } else if ("2".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" null_yn="<%=null_yn%>" style="width:98%;" />
					<% } else if ("3".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" null_yn="<%=null_yn%>" style="width:98%;" />
					<% } else if ("4".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" null_yn="<%=null_yn%>" data_type="4" class="datepicker2" style="width:98%;" />
					<% } %>
				</div>
			</td>
			<%
				}
			}
			%>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>

	<div style="display:none">
	<table id="objRegTempTbl">
	<tbody>
		<tr>
			<th class="title">
				<span class="spanRowNum">#no#</span>
				<input type="hidden" name="chkrowarea" value="1" />
			</th>
			<%
			if (dispMngList != null) {
				for (int k=0; k<dispMngList.size(); k++) {
					CommonMap dispMng = dispMngList.getMap(k);

					String logical_name = dispMng.getString("logical_name");
					String physical_name = dispMng.getString("physical_name").toLowerCase();
					String data_disp_type = dispMng.getString("data_disp_type");
					String cmcode_yn = dispMng.getString("cmcode_yn");
					String cmcode_id = dispMng.getString("cmcode_id");
					String default_value = dispMng.getString("default_value");
					int default_width = dispMng.getInt("default_width", 100) - 0;
					String null_yn = dispMng.getString("null_yn");

					String val = "";
			%>
			<td class="dispType<%=dispMng.getString("data_disp_type")%>">
				<div style="position:relative;">
					<% if ("asset_no".equals(physical_name)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" readonly="readonly" style="width:98%;background-color:#FAF4C0;" />
					<% } else if ("Y".equals(cmcode_yn) && !"".equals(cmcode_id)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" nullynarea="<%=null_yn%>" style="width:98%;" />
					<select style="width:27px;position:absolute;right:0px;top:0px;z-index:99" onchange="fnRowSelChange(this)">
						<option value="">선택</option>
						<c:import url="/code/optionList.do" charEncoding="utf-8">
						<c:param name="param_codeId" value="<%=cmcode_id%>" />
						<c:param name="param_sltValue" value="<%=val%>" />
						</c:import>
					</select>
					<% } else if ("1".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" nullynarea="<%=null_yn%>" style="width:98%;" />
					<% } else if ("2".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" nullynarea="<%=null_yn%>" style="width:98%;" />
					<% } else if ("3".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" nullynarea="<%=null_yn%>" style="width:98%;" />
					<% } else if ("4".equals(data_disp_type)) { %>
					<input type="text" name="<%=physical_name%>" value="<%=val%>" nullynarea="<%=null_yn%>" datepickerarea style="width:98%;" />
					<% } %>
				</div>
			</td>
			<%
				}
			}
			%>
		</tr>
	</tbody>
	</table>
	</div>

	</form>

	<div class="buttonDiv" style="padding-top:50px;">
		<span class="button"><input type="button" value="닫기" onclick="self.close();"></span>
	</div>

	<iframe id="amProcFrame" name="amProcFrame" width="0" height="0"></iframe>

</body>
</html>



