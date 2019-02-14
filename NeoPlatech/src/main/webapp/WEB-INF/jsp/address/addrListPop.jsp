<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/address/selectList.do";
String curLabelGridAction = "/address/selectAddressListAjax.do";
String delAction = "/address/deleteProc.do";
String curAjaxAction = "/address/insertAddressAjax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
int addHeightHip = 0;
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>

<script type="text/javascript">
function fnSave() {
	var frm = document.sForm;
	
	if (frm.title.value == "") {
		alert("<spring:message code="addr.required.title"/>");
		return;
	}
	
	if (frm.spingAddress.value == "") {
		alert("<spring:message code="addr.required.address"/>");
		return;
	}
	
	if (frm.spingTel.value == "") {
		alert("<spring:message code="addr.required.tel"/>");
		return;
	}
	
	if (frm.spingName.value == "") {
		alert("<spring:message code="addr.required.name"/>");
		return;
	}
	
	if (confirm("<spring:message code="alert.confirm.save"/>")) {
		$.ajax({
			type : "POST",
			url : "<%=curAjaxAction%>",
			data : $('#sForm').serializeObject(),
			dataType : "json",
			success:function(data)		
			{
				if (data.result == "OK") {
					
					opener.fnCallbackAddr(data.addrSeq);
					alert("<spring:message code="alert.ok.save"/>");
					self.close();
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("<spring:message code="alert.error.fail"/>");
			},
			complete:function()
			{
			}
		});
	}
}
</script>

</head>
<body>
	
	<h2 class="title_left"><spring:message code="menu.addr"/></h2>
	
	<%-- <div style="border-bottom:1px solid #dadada;padding:10px;">
		<p style="font-size:15px;line-height:170%; font-weight:bold;">
			인쇄한 RFID Tag 의 배송지 정보를 입력합니다.
		</p>
	</div> --%>
	
	<div style="padding-left:10px;">
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>" onsubmit="return false">
	<input type="hidden" id="pageIdx" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="addrTypeCd" name="addrTypeCd" value="2" />
	
	<table style="width:98%; border:0; border-collapse:collapse;">
	<tr>
		<td valign="top" align="center" style="padding-top:20px;">
		
			<h4 style="font-size:14px;color:#000000;">※<spring:message code="addr.required.title"/></h4>
			
			<div style="padding-bottom:25px;padding-top:5px;">
			<table class="table-cell" style="width:100%;">
			<colgroup>
				<col width="100px" />
				<col width="" />
			</colgroup>
			<tr>
				<th><spring:message code="column.title"/></th>
				<td>
					<input type="text" id="title" name="title" value="" class="t_strong" style="width:98%; height:20px; border:0; padding-left:5px; color:black" />
				</td>
			</tr>
			</table>
			</div>
		
			<h4 style="font-size:14px;color:#000000;">※<spring:message code="addr.summary.a"/></h4>
		
			<div id="divTagLabel" style="padding-top:5px;">
			<table class="table-cell" style="width:100%;">
			<colgroup>
				<col width="100px" />
				<col width="" />
			</colgroup>
			<tr>
				<th><spring:message code="column.addr"/></th>
				<td>
					<input type="text" id="spingAddress" name="spingAddress" value="" class="t_strong" style="width:98%; height:20px; border:0; padding-left:5px; color:black" />
				</td>
			</tr>
			<tr>
				<th><spring:message code="column.tel"/></th>
				<td>
					<input type="text" id="spingTel" name="spingTel" value="" class="t_strong" style="width:98%; height:20px; border:0; padding-left:5px; color:black" />
				</td>
			</tr>
			<tr>
				<th><spring:message code="column.addr.name"/></th>
				<td>
					<input type="text" id="spingName" name="spingName" value="" class="t_strong" style="width:98%; height:20px; border:0; padding-left:5px; color:black" />
				</td>
			</tr>
			</table>
			</div>
		</td>
	</tr>
	</table>
	
	<div class="buttonDiv">
		<span class="button"><input type="button" value="<spring:message code="button.save"/>" onclick="fnSave();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.close"/>" onclick="javascript:self.close();"></span>
	</div>
	
	</form>
	
	</div>
	
</body>
</html>

		

