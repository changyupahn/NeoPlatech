<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/inventory/writeForm.do";
String writeAction = "/inventory/writeProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap view = RequestUtil.getCommonMap(request, "inventoryView");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnWrite(){
	
	if ($('#inv_start_dt').val() == "") {
		alert("조사시작일을 입력해 주세요");
		return;
	}
	if ($('#inv_type').val() == "") {
		alert("조사종류를 입력해 주세요");
		return;
	}
	if ($('#inv_summary').val() == "") {
		alert("설명을 입력해 주세요");
		return;
	}
	
	if( confirm("신규 생성하시겠습니까?\n저장시 10~20초 정도 소요되므로 창을 닫지 마시고 기다려 주세요.") ){
		var frm = document.sForm;
		frm.action = "<%=writeAction%>";
		frm.submit();
	}
}
</script>
</head>
<body>

	<h2 class="title_left">재물조사 등록</h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	
	<div style="border-bottom:1px solid #dadada;padding:10px;">
		<div style="font-size:15px; font-weight:bold; padding-bottom:20px;">
			1. 재물조사 기준일을 입력해주세요.			 
			<!-- * 기준일은 재물조사 관리 차원에서의 날짜 값이며, 어떤 기능에 영향을 주지는 않습니다. -->
			<div style="font-size:12px;font-weight:bold;color:#636363;padding:5px 5px 5px 24px;margin-top:3px;background-color:#f7f7f7;border:1px solid #d2d4d1;">
				기준일 설정에 따라 재물조사 년도와 차수가 결정되며, 그 외에는 어떤 기능에 영향을 주지는 않습니다.
				<br />
				<input type="text" id="inv_start_dt" name="inv_start_dt" value="<%=DateUtil.formatDate(view.getString("inv_start_dt", DateUtil.getFormatDate()), "-")%>" class="datepicker" style="font-size:14px;width:120px;color:#000000" />
			</div>
		</div>
		<div style="font-size:15px; font-weight:bold; padding-bottom:20px;">
			2. 재물조사 종류를 선택해주세요.			
			<div style="font-size:12px;font-weight:bold;color:#636363;padding:5px 5px 5px 24px;margin-top:3px;background-color:#f7f7f7;border:1px solid #d2d4d1;">
				정기재물조사 : 매년 또는 매월 전체 자산을 대상으로 하는 재물조사
				<br />
				수시재물조사 : 상황에 따라 임의로 또는 자주 시행되는 재물조사
				<br />
				<select id="inv_type" name="inv_type" style="font-size:14px;width:120px;height:23px;color:#000000"> 
					<option value="">선택</option>
					<c:import url="/code/optionList.do" charEncoding="utf-8">
					<c:param name="paramCodeId" value="AST006" />
					<c:param name="paramSltValue" value="" />
					</c:import>
				</select>
			</div>
		</div>
		<div style="font-size:15px; font-weight:bold; padding-bottom:10px;">
			3. 설명을 입력해주세요.
			<div style="font-size:12px;font-weight:bold;color:#636363;padding:5px 5px 5px 24px;margin-top:3px;background-color:#f7f7f7;border:1px solid #d2d4d1;">
				EX) OOOO년도 상반기 정기재물조사
				<br />
				<input type="text" id="inv_summary" name="inv_summary" value="<%=view.getString("inv_summary")%>" style="font-size:14px; width:90%; height:20px; color:#000000" />
			</div>
		</div>
		<div style="font-size:15px; font-weight:bold; padding-bottom:10px;">
			4. 물품이 신규등록/삭제 되면 자동으로 [재물조사대상] 설정/해제 되도록 하겠습니까?
			<div style="font-size:12px;font-weight:bold;color:#636363;padding:5px 5px 5px 24px;margin-top:3px;background-color:#f7f7f7;border:1px solid #d2d4d1;">				
				<input type="radio" id="autoSyncYn_Y" name="autoSyncYn" value="Y" checked="checked" /> <label for="autoSyncYn_Y">예</label>
				<input type="radio" id="autoSyncYn_N" name="autoSyncYn" value="N" /> <label for="autoSyncYn_N">아니요</label>
				<br >※ 해당 기능은 "예"로 설정 하더라도 가장 최신의 재물조사에만 작동합니다. 
			</div>
		</div>
	</div>
	
	</form>
	
	<div class="buttonDiv">
		<span class="button"><input type="button" value="저장" onclick="fnWrite();"></span>
		<span class="button"><input type="button" value="닫기" onclick="self.close();"></span>
	</div>
	
</body>
</html>

		

