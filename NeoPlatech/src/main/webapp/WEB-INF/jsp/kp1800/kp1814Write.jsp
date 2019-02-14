<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "유지보수일지 등록/수정";
String curAction = "/kp1800/kp1814Write.do";
String curTabAction = "/kp1800/kp1814Tab.do";
String assetTabAction = "/kp1300/kp1311Tab.do";
String procAction = "/kp1800/kp1814Proc.do";
String listAction = "/kp1800/kp1814.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");

int colbasewid = 310; //검색 폼 동적 사이즈 구성을 위한 넓이 값
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnInitTabForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=curTabAction%>",
		data : {
			equipId : $('#equipId').val(),
			equipNo : $('#equipNo').val(),
			assetSeq : $('#assetSeq').val()
		},
		success:function(data)
		{
			$('#TabBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}
function fnSave() {

	if (confirm("저장 하시겠습니까?")) {
		fnLoadingS2();

		$.ajax({
			type : "POST",
			url : "<%=procAction%>",
			data : fnSerializeObject(),
			dataType : "json",
			success:function(data)
			{
				if (data.ret == "OK") {
					alert("저장 되었습니다.");
					location.href = "<%=listAction%>?equipId=<%=viewData.getString("equipId")%>&equipNo=<%=viewData.getString("equipNo")%>&assetSeq=<%=cmRequest.getString("assetSeq")%>";
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

function fnInitAssetTabForm() {
	var hdWinWidth = $(window).width();
	var colcnt = parseInt(hdWinWidth / <%=colbasewid%>);
	$.ajax({
		type : "POST",
		url : "<%=assetTabAction%>",
		data : {
			assetSeq : $('#assetSeq').val(),
			equipId : $('#equipId').val()
		},
		success:function(data)
		{
			$('#AssetTabBox').html(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
			fnInitCalc();
		}
	});
}

$(document).ready(function(){

	<% if (!"".equals(cmRequest.getString("assetSeq"))) { %>
	fnInitAssetTabForm();
	<% } %>
	fnInitTabForm();

	$('#equipStatusCd').bind('change', function(){
		if ($(this).val() == "3") {
			$('#spanEquipStatusEtc').show(100);
		} else {
			$('#spanEquipStatusEtc').hide(100);
		}
	});

	<% if ("3".equals(viewData.getString("equipStatusCd"))) { %>
	$('#spanEquipStatusEtc').show(100);
	<% } %>
});
function fnCanc() {
	location.href = "<%=listAction%>?equipId=" + $('#equipId').val() + "&assetSeq=<%=cmRequest.getString("assetSeq")%>";
}
</script>

</head>
<body style="overflow:auto;padding:1px;">

	<div id="AssetTabBox" class="AssetTabBox"></div>

	<table style="width:100%">
		<tr>
		<td width="50%">
			<% if ("".equals(viewData.getString("journalSeq"))) { %>
			<h2 class="title_left">유지보수일지 등록</h2>
			<% } else { %>
			<h2 class="title_left">유지보수일지 수정</h2>
			<% } %>
		</td>
		<td width="50%" style="text-align: right;">
			<% if ("".equals(viewData.getString("journalSeq"))) { %>
			<span class="button2"><input type="button" value="일지등록" onclick="fnSave();"></span>
			<% } else { %>
			<span class="button2"><input type="button" value="일지수정" onclick="fnSave();"></span>
			<span class="button2"><input type="button" value="취소" onclick="fnCanc();"></span>
			<% } %>
			&nbsp;
		</td>
		</tr>
	</table>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="equipId" name="equipId" value="<%=viewData.getString("equipId")%>" />
	<input type="hidden" id="equipNo" name="equipNo" value="<%=viewData.getString("equipNo")%>" />
	<input type="hidden" id="assetSeq" name="assetSeq" value="<%=cmRequest.getString("assetSeq")%>" />
	<input type="hidden" id="journalSeq" name="journalSeq" value="<%=viewData.getString("journalSeq")%>" />

	<table style="width:100%;height:700px;border-collapse:collapse;border:0;">
	<colgroup>
		<col width="50%" />
		<col width="" />
	</colgroup>
	<tr>
	<td style="vertical-align:top">

		<div class="date">
			<span><%=DateUtil.getFormatDate("yyyy")%>. <%=DateUtil.getFormatDate("MM")%></span>
		</div>

		<table class="table_calendar">
		<tr>
		<th class="sun">일</th>
		<th>월</th>
		<th>화</th>
		<th>수</th>
		<th>목</th>
		<th>금</th>
		<th class="sat">토</th>
		</tr>
		<%
		CalendarUtil cal = new CalendarUtil();
		int[][] myCalendar = cal.getCalendar();
		String today = DateUtil.getFormatDate("yyyyMMdd");
		for (int i=0; i<myCalendar.length; i++) {
		%>
		<tr>
			<%
			for (int j=0; j<7; j++) {
				int day = myCalendar[i][j];
				String ymd = "" + cal.getYear() + StringUtil.lpad(""+cal.getMonth(), 2, "0") + StringUtil.lpad(""+day, 2, "0");
				String todayCls = "";
				if (today.equals(ymd)) {
					todayCls = "background-color:#dbf4ff";
				}
				%>
				<td class="<%=j==0?"sun":j==6?"sat":"day"%>" style="<%=todayCls%>">
				<%
				if (day != 0) {
					%>
					<span class="day"><%=day%></span>
					<%
				}
			}
			%>
		</tr>
		<%
		}
		%>
		</table>
	</td>
	<td style="vertical-align:top;padding-top:0px;">

		<div id="TabBox" class="TabBox"></div>

		<div style="border:1px solid gray;background-color:#FFE08C;padding:10px;margin-top:0px">
			<span style="color:black;font-weight:bold;">■ 유지보수일지 등록</span>
		</div>



		<table class="search01">
		<colgroup>
			<col width="100px" />
			<col width="" />
		</colgroup>
		<tr>
			<th>시설장비명 :</th>
			<td><%=viewData.getString("korNm")%></td>
		</tr>
		<tr>
			<th>장비등록번호 :</th>
			<td><%=viewData.getString("equipNo")%></td>
		</tr>
		<tr>
			<th>장비책임자 :</th>
			<td><%=viewData.getString("registId")%></td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>작성자명 :</th>
			<td>
				<input type="text" id="registrentNm" name="registrentNm" value="<%=viewData.getString("registrentNm")%>" class="def" />
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>수리자명 :</th>
			<td>
				<input type="text" id="journalUserNm" name="journalUserNm" value="<%=viewData.getString("journalUserNm")%>" class="def" />
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>시작일시 :</th>
			<td>
				<input type="text" id="journalSdt" name="journalSdt" value="<%=viewData.getString("journalSdt")%>" class="datepicker impt2" style="width:100px;" />
				<div style="padding-top:5px;">
				<input type="text" id="journalShour" name="journalShour" value="<%=viewData.getString("journalShour")%>" style="width:40px" />시
				<input type="text" id="journalSminute" name="journalSminute" value="<%=viewData.getString("journalSminute")%>" style="width:40px" />분
				</div>
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>종료일시 :</th>
			<td>
				<input type="text" id="journalEdt" name="journalEdt" value="<%=viewData.getString("journalEdt")%>" class="datepicker impt2" style="width:100px;" />
				<div style="padding-top:5px;">
				<input type="text" id="journalEhour" name="journalEhour" value="<%=viewData.getString("journalEhour")%>" style="width:40px" />시
				<input type="text" id="journalEminute" name="journalEminute" value="<%=viewData.getString("journalEminute")%>" style="width:40px" />분
				</div>
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>시설장비상태 :</th>
			<td>
				<select id="equipStatusCd" name="equipStatusCd" class="sel" style="max-width:95%">
				<option value="1" <%="1".equals(viewData.getString("equipStatusCd","1"))?"selected":""%>>정상가동</option>
				<option value="2" <%="2".equals(viewData.getString("equipStatusCd"))?"selected":""%>>작동불량(고장, 소모품 교체필요, 검교정 필요 등)</option>
				<option value="3" <%="3".equals(viewData.getString("equipStatusCd"))?"selected":""%>>기타</option>
				</select>
				<span id="spanEquipStatusEtc" style="display:none">
				기타입력 :
				<input type="text" id="equipStatusEtc" name="equipStatusEtc" value="<%=viewData.getString("equipStatusEtc")%>" style="width:150px;" />
				</span>
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>유지보수내용 :</th>
			<td>
				<textarea id="contents" name="contents" class="def" style="height:120px;"><%=viewData.getString("contents")%></textarea>
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>유지보수결과 :</th>
			<td>
				<select id="resultCd" name="resultCd" class="sel" style="max-width:95%">
				<option value="1" <%="1".equals(viewData.getString("resultCd","1"))?"selected":""%>>정상가동</option>
				<option value="2" <%="2".equals(viewData.getString("resultCd"))?"selected":""%>>가동불가</option>
				<option value="3" <%="3".equals(viewData.getString("resultCd"))?"selected":""%>>기타(일부 수리 포함)</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>유지보수비용 :</th>
			<td>
				<input type="text" id="journalPrc" name="journalPrc" value="<%=viewData.getString("journalPrc")%>" style="width:150px" />
				원
			</td>
		</tr>
		</table>
	</td>
	</tr>
	</table>

	</form>

</body>
</html>



