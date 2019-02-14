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
CommonList dispMngList = RequestUtil.getCommonList(request, "dispMngList98");

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
function fnAdd() {
	try{
		var sw = 1;
		
		$('input[one_null_yn="N"]').each(function(){
			if ($(this).val() == "") {
				sw = 0;
				alert( $('#th_' + $(this).attr("name")).val()  + "<spring:message code="common.required" arguments=" " />");
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
		alert("<spring:message code="common.required.file.excel"/>");
		return;
	}
	
	if (confirm("<spring:message code="alert.confirm.excel.upload"/>")) {
		
		$('#statusArea').append("\r\n<spring:message code="common.process"/>\r\n");
		if (action == "") {
			fnLoadingS("<spring:message code="common.process"/>", "<%=xlsUpStatusAction%>");
		}
		
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
</script>

</head>
<body style="margin:15px;">

	<h2 class="title_left"><spring:message code="menu.assets.write"/></h2>
	
	<h4 style="font-size:14px;color:blue;"><spring:message code="excel.title.upload"/></h4>
	
	<form id=sForm name="sForm" method="post" action="<%=curAction%>" enctype="multipart/form-data">
	<!--  <input type="hidden" name="proc_mode" id="proc_mode" value="" /> -->
	
	<table width="100%" class="table-search" border="0">
	<tr>
		<th width="650px" class="l" style="height:200px; font-size:13px;padding:10px;line-height:190%;">
			<spring:message code="excel.upload.summary.a"/>
			<br />- <spring:message code="excel.upload.summary.b"/>
			<br />- <spring:message code="excel.upload.summary.c"/>		
			<br />- <spring:message code="excel.upload.summary.d"/> <span class="button"><input type="button" value="<spring:message code="common.download"/>" onclick="fnXlsDn();"></span>
			<br />- <spring:message code="excel.title.upload"/> : <span id="spanXlsFile"><input type="file" name="xls_file" id="xls_file" /></span>
			<span class="button"><input type="button" value="<spring:message code="common.upload"/>" onclick="fnXlsUp('');"></span>
		</th>
		<td class="l" style="font-size:13px;">
			<textarea id="statusArea" rows="9" cols="50" style="border:0;width:80%;height:200px"></textarea>
		</td>
	</tr>
	</table>
	
	<h4 style="font-size:14px;color:blue;padding-top:30px;"><spring:message code="title.input.direct"/></h4>
	
	<input type="hidden" name="chk_row" value="1" />
	
	<table id="objRegOneTbl" class="table-cell" style="width:100%">
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
			String modify_yn = dispMng.getString("modify_yn");
			
			String val = "";
	%>
	<th>
		<%=logical_name%>
		<input type="hidden" id="th_<%=dispMng.getString("physical_name").toLowerCase()%>" value="<%=dispMng.getString("logical_name")%>" />
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
				<option value=""><spring:message code="common.select"/></option>
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
		<span class="button"><input type="button" value="<spring:message code="button.init"/>" onclick="fnInitOne();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.add"/>" onclick="fnAdd();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.close"/>" onclick="self.close();"></span>
	</div>
	
	</form>
	
	<iframe id="amProcFrame" name="amProcFrame" width="0" height="0"></iframe>
	
</body>
</html>

		

