<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonList labelColList = RequestUtil.getCommonList(request, "labelColList");
%>
<table class="table-cell" style="width:100%;">
<colgroup>
	<col width="120px" />
	<col width="" />
</colgroup>
<%
for (int i=0; i<labelColList.size(); i++) {
	CommonMap labelCol = labelColList.getMap(i);
%>
<tr>
	<td>
		<input type="text" id="objTitle<%=(i+1)%>" name="objTitle" value="<%=labelCol.getString("objTitle")%>" class="t_strong" style="width:110px; height:22px; border:0; padding-left:10px; color:#000000; background-color:#ffffff" readonly="readonly" />
		<input type="hidden" id="objLcationNo<%=(i+1)%>" name="objLcationNo" value="<%=labelCol.getString("objLcationNo")%>" />
		<input type="hidden" id="objType<%=(i+1)%>" name="objType" value="<%=labelCol.getString("objType")%>" />
		<input type="hidden" id="objAamSeq<%=(i+1)%>" name="objAamSeq" value="<%=labelCol.getString("objAamSeq")%>" />
	</td>
	<td>
		<input type="text" id="objValue<%=(i+1)%>" name="objValue" value="" class="t_strong" style="width:95%; height:22px; border:0; background-color:#ffffff" readonly="readonly" />
	</td>
</tr>
<%
}
%>
</table>