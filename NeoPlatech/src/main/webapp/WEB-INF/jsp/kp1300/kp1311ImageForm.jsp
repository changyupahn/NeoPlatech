<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1300/kp1311ImageForm.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList list = RequestUtil.getCommonList(request, "imgList");

int idx = 0;
int colmax = 0;
int colcnt = cmRequest.getInt("colcnt");
%>
	<table class="table-search" style="width:100%">
	<tr>
		<td>

<script type="text/javascript">
function fnAstImageDelete(asset_no, file_dt){
	if (confirm("선택하신 물품이미지를 삭제하시겠습니까?")) {
		location.href = "/kp1300/kp1311ImageDelete.do?asset_no=" + asset_no + "&file_dt=" + file_dt + "&assetSeq=<%=cmRequest.getString("assetSeq")%>"
	}
}
function fnOpenImg(asset_no, img_src){
	$( "#dialog-message").html("");
	// 상세보기 이미지 생성
	$('<img />', {
		src : img_src
	}).appendTo('#dialog-message');

	var objTitVal = "자산번호 - " + asset_no;
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

		</td>
		<td width="25%" valign="bottom" style="padding:0 15px 15px 0;text-align:right;">
			<script type="text/javascript">
			function fnImgUp(){
				var frm = document.imgForm;
				frm.submit();
			}
			</script>
			<form name="imgForm" method="post" action="/kp1300/kp1311ImageUpload.do" enctype="multipart/form-data">
			<input type="hidden" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />
			<input type="file" name="file" style="height:18px;" />
			<span class="button"><input type="button" value="이미지등록" onclick="fnImgUp();"></span>
			</form>
		</td>
	</tr>
	</table>
