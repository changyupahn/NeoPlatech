<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "운영일지 등록/수정";
String curAction = "/kp1800/kp1813Write.do";
String curTabAction = "/kp1800/kp1813Tab.do";
String assetTabAction = "/kp1300/kp1311Tab.do";
String procAction = "/kp1800/kp1813Proc.do";
String listAction = "/kp1800/kp1813.do";

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
$(document).ready(function(){

	<% if (!"".equals(cmRequest.getString("assetSeq"))) { %>
	fnInitAssetTabForm();
	<% } %>
	fnInitTabForm();

	$('#useTypeCd').bind('change', function(){
		if ($(this).val() == "5") {
			$('#spanUseTypeEtc').show(100);
		} else {
			$('#spanUseTypeEtc').hide(100);
		}
	});

	<% if ("5".equals(viewData.getString("useTypeCd"))) { %>
	$('#spanUseTypeEtc').show(100);
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
			<h2 class="title_left">운영일지 등록</h2>
			<% } else { %>
			<h2 class="title_left">운영일지 수정</h2>
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
			<span style="color:black;font-weight:bold;">■ 운영일지 등록</span>
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
			<th><span style="color:red">*</span>이용자명 :</th>
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
			<th><span style="color:red">*</span>이용구분 :</th>
			<td>
				<select id="useOrganClassCd" name="useOrganClassCd" class="sel">
				<option value="01" <%="01".equals(viewData.getString("useOrganClassCd","01"))?"selected":""%>>장비구축부서</option>
				<option value="02" <%="02".equals(viewData.getString("useOrganClassCd"))?"selected":""%>>기관 내 타 부서</option>
				<option value="03" <%="03".equals(viewData.getString("useOrganClassCd"))?"selected":""%>>타기관</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>이용기관명 :</th>
			<td>
				<input type="text" id="useOrganNm" name="useOrganNm" value="<%=viewData.getString("useOrganNm")%>" class="def" />
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>이용부서명 :</th>
			<td>
				<input type="text" id="useDeptNm" name="useDeptNm" value="<%=viewData.getString("useDeptNm")%>" class="def" />
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>시료수 :</th>
			<td>
				<input type="text" id="sampleCnt" name="sampleCnt" value="<%=viewData.getString("sampleCnt")%>" style="width:100px" />
			</td>
		</tr>
		<tr>
			<th><span style="color:red">*</span>인력투입시간 :</th>
			<td>
				<input type="text" id="inputManHour" name="inputManHour" value="<%=viewData.getString("inputManHour")%>" style="width:100px" />
				시간
			</td>
		</tr>
		<tr>
			<th>이용료 :</th>
			<td>
				<input type="text" id="journalPrc" name="journalPrc" value="<%=viewData.getString("journalPrc")%>" style="width:150px" />
				원
			</td>
		</tr>
		<tr>
			<th>이용유형 :</th>
			<td>
				<select id="useTypeCd" name="useTypeCd" class="sel">
				<option value="1" <%="1".equals(viewData.getString("useTypeCd","1"))?"selected":""%>>계측·분석·시험·실험·검교정</option>
				<option value="2" <%="2".equals(viewData.getString("useTypeCd"))?"selected":""%>>제조·생산·가공</option>
				<option value="3" <%="3".equals(viewData.getString("useTypeCd"))?"selected":""%>>정보처리·시뮬레이션</option>
				<option value="4" <%="4".equals(viewData.getString("useTypeCd"))?"selected":""%>>교육</option>
				<option value="5" <%="5".equals(viewData.getString("useTypeCd"))?"selected":""%>>기타</option>
				</select>
				<span id="spanUseTypeEtc" style="display:none">
				기타입력 :
				<input type="text" id="useTypeEtc" name="useTypeEtc" value="<%=viewData.getString("useTypeEtc")%>" style="width:150px;" />
				</span>
			</td>
		</tr>
		<tr>
			<th>이용기관유형 :</th>
			<td>
				<select id="useOrganTypeCd" name="useOrganTypeCd" class="sel">
				<option value="07" <%="07".equals(viewData.getString("useOrganTypeCd","1"))?"selected":""%>>2년제 국·공립대학</option>
				<option value="09" <%="09".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>2년제 사립대학</option>
				<option value="06" <%="06".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>4년제 국·공립대학</option>
				<option value="08" <%="08".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>4년제 사립대학</option>
				<option value="01" <%="01".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>국·공립 연구기관</option>
				<option value="02" <%="02".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>정부출연 연구기관</option>
				<option value="04" <%="04".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>연구조합</option>
				<option value="03" <%="03".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>지자체출연 연구기관</option>
				<option value="15" <%="15".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>시장형 공기업</option>
				<option value="16" <%="16".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>준시장형 공기업</option>
				<option value="14" <%="14".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>민간기업</option>
				<option value="10" <%="10".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>국·공립 의료기관</option>
				<option value="12" <%="12".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>국·공립대학 부속병원</option>
				<option value="11" <%="11".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>사립 의료기관</option>
				<option value="13" <%="13".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>사립대학 부속병원</option>
				<option value="17" <%="17".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>기타</option>
				<option value="05" <%="05".equals(viewData.getString("useOrganTypeCd"))?"selected":""%>>기타공공기관</option>
				</select>
			</td>
		</tr>
		</table>
	</td>
	</tr>
	</table>

	</form>

</body>
</html>



