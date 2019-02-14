<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList list = RequestUtil.getCommonList(request, "imgList");
%>
<script type="text/javascript">
function fnAssetImageView(asset_no, file_dt) {
	var assetImageView = fnOpenPop("/asset/getImageView.do?asset_no="+ asset_no +"&file_dt="+ file_dt, "assetImageView", "800", "600", "");
	assetImageView.focus();
}
function fnAstImageDelete(asset_no, file_dt){
	if (confirm("선택하신 물품이미지를 삭제하시겠습니까?")) {
		location.href = "/asset/image/deleteWeb.do?asset_no=" + asset_no + "&file_dt=" + file_dt
	}
}
function fnOpenImg(asset_no,file_dt){
	$( "#dialog-message").html("");
	// 상세보기 이미지 생성
	$('<img />', {
		src : "/asset/getImage.do?asset_no="+ asset_no +"&file_dt="+ file_dt
	}).appendTo('#dialog-message');
	
	var objTitVal = "물품번호 - " + asset_no;
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	$( "#dialog-message" ).dialog({
		modal: true
		, maxWidth:830
		, minWidth:670
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
<td width="230">
	<!-- <a href="javascript:fnAssetImageView('<%=img.getString("asset_no")%>','<%=img.getString("file_dt")%>')"><img id="IM_<%=img.getString("asset_no")%>_<%=img.getString("file_dt")%>" src='/asset/getImage.do?asset_no=<%=img.getString("asset_no")%>&file_dt=<%=img.getString("file_dt")%>' alt="" width="225" height="165" /></a> -->
	<!-- <a href="/asset/getImageView.do?asset_no=<%=img.getString("asset_no")%>&file_dt=<%=img.getString("file_dt")%>" target="_blank"><img id="IM_<%=img.getString("asset_no")%>_<%=img.getString("file_dt")%>" src='/asset/getImage.do?asset_no=<%=img.getString("asset_no")%>&file_dt=<%=img.getString("file_dt")%>' alt="" width="225" height="165" /></a> -->
	<a href="javascript:fnOpenImg('<%=img.getString("asset_no")%>','<%=img.getString("file_dt")%>')"><img id="IM_<%=img.getString("asset_no")%>_<%=img.getString("file_dt")%>" src='/asset/getImage.do?asset_no=<%=img.getString("asset_no")%>&file_dt=<%=img.getString("file_dt")%>' alt="" width="225" height="165" /></a>
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
	<%=img.getString("orignl_file_nm")%>	
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