<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList list = RequestUtil.getCommonList(request, "imgList");
%>
<script type="text/javascript">
function fnAstImageDelete(asset_no, file_dt){
	if (confirm("선택하신 물품이미지를 삭제하시겠습니까?")) {
		location.href = "/asset/image/deleteWeb.do?asset_no=" + asset_no + "&file_dt=" + file_dt
	}
}
function fnOpenImg(asset_no, img_src){
	$( "#dialog-message").html("");
	// 상세보기 이미지 생성
	$('<img />', {
		src : img_src
	}).appendTo('#dialog-message');

	var objTitVal = "자산코드 - " + asset_no;
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	$( "#dialog-message" ).dialog({
		modal: true
		, maxWidth:800
		, minWidth:800
		, minHeight:400
		, title: objTitVal
	});
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
<%
for (int i=0; i<list.size(); i++) {
	CommonMap img = list.getMap(i);
%>
<td width="205">
	<a href="javascript:fnOpenImg('<%=img.getString("assetNo")%>','<%=img.getString("webFilePath")%>/<%=img.getString("fileNm")%>')"><img src='<%=img.getString("webFilePath")%>/tn<%=img.getString("fileNm")%>' alt="" width="200" height="200" /></a>
</td>
<%
}
%>
<td>&nbsp;</td>
	</tr>
	<tr>
<%
	for (int i=0; i<list.size(); i++) {
		CommonMap img = list.getMap(i);
%>
<td width="200">
	<img src="/images/icon/ico_file.gif" alt="" />
	<%=img.getString("orignlFileNm")%>
	<a href="javascript:fnAstImageDelete('<%=img.getString("asset_no")%>','<%=img.getString("file_dt")%>')"><img src="/images/icon/ico_file_del.gif" alt="" /></a>
</td>
<%
	}
%>
<td>&nbsp;</td>
	</tr>
</table>
<div id="dialog-message" title="Download complete" style="display:none">
</div>