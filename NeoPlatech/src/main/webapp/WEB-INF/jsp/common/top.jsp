<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String topGrantReadYnArr = boassoft.util.SessionUtil.getString("grantReadYnArr");

String topUserId = boassoft.util.StringUtil.nvl((String)session.getAttribute("userNo"), "");
String topUserNm = boassoft.util.StringUtil.nvl((String)session.getAttribute("userName"), "");
boolean bManager = false;
if (!"".equals(topUserId)) {
	bManager = true;
} else {
	bManager = false;
	topUserNm = "사용자";
}
%>
<script type="text/javascript">
$(document).ready(function () {
	<% if (boassoft.util.RequestUtil.isMobile(request)) { %>
		$(".gnb ul > li").click(function() {
			$(".gnb ul li").removeClass("on");
			$(".gnb ol").removeClass("on");
			$(this).addClass("on");
			var obj = $(this).find("ol");
			obj.each(function () {
				$(this).addClass("on");
			});
		});
	<% } else { %>
		$(".gnb ul > li").mouseover(function() {
			$(".gnb ul li").removeClass("on");
			$(".gnb ol").removeClass("on");
			$(this).addClass("on");
			var obj = $(this).find("ol");
			obj.each(function () {
				$(this).addClass("on");
			});
		});
		$(".gnb").mouseout(function() {
			$(".gnb ul > li").removeClass("on");
			$(".gnb ol").removeClass("on");
		});
	<% } %>
});//ready

function fnTopMenu(url) {
	<% if (!boassoft.util.RequestUtil.isMobile(request)) { //웹환겨에서만 탑메뉴 링크 이동, 모바일 환경에서는 서브메뉴 표시를 위해 링크 이동 하지 않음. %>
		location.href = url;
	<% } %>
}

function fnLogout() {
	fnLoadingS2();

	if (!confirm("로그아웃 하시겠습니까?")) {
		fnLoadingE2();
		return;
	}

	$.ajax({
		type : "POST",
		url : "/kp1000/kp1011LogoutAjax.do",
		data : {
		},
		dataType : "json",
		success:function(data)
		{
			if (data.ret == "OK") {
				//alert("로그아웃 되었습니다.");
				location.href = "/index.do";
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
</script>
<div id="header">
	<a href="javascript:;" class="logo" ><img src="/images/logo/logo_main.png" alt="우체국금융개발원 RFID자산관리시스템 " /></a>
	<div class="login">
		<span><spring:message code="common.login.result" arguments="<%=topUserNm%>" /></span>
		<span class="btn_black"><a href="javascript:fnLogout();"><spring:message code="button.logout"/></a></span>
	</div>
	<div class="gnb">
		<ul>
		<% if (topGrantReadYnArr.indexOf("KP1300") > -1) { %>
		<li class="">
			<a href="javascript:;" onclick="fnTopMenu('/kp1300/kp1310.do')">자산취득관리</a>
			<ol>
				<% if (topGrantReadYnArr.indexOf("KP1310") > -1) { %>
				<li><a href="/kp1300/kp1310.do" style="color:#ffffff">자산현황</a></li>
				<% } %>
				<% if (topGrantReadYnArr.indexOf("KP1320") > -1) { %>
				<li><a href="/kp1300/kp1320.do" style="color:#ffffff">자산검수</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		<% if (topGrantReadYnArr.indexOf("KP1700") > -1) { %>
		<li class="">
			<a href="javascript:;" onclick="fnTopMenu('/kp1700/kp1710.do')">재물조사관리</a>
			<ol>
				<% if (topGrantReadYnArr.indexOf("KP1710") > -1) { %>
				<li><a href="/kp1700/kp1710.do" style="color:#ffffff">재물조사관리</a></li>
				<% } %>
				<% if (topGrantReadYnArr.indexOf("KP1720") > -1) { %>
				<li><a href="/kp1700/kp1720.do" style="color:#ffffff">재물조사내역</a></li>
				<% } %>
				<%-- if (topGrantReadYnArr.indexOf("KP1730") > -1) { %>
				<li><a href="/kp1700/kp1730.do" style="color:#ffffff">재물조사결과반영</a></li>
				<% } --%>
			</ol>
		</li>
		<% } %>
		<% if (topGrantReadYnArr.indexOf("KP2000") > -1) { %>
		<li class="">
			<a href="javascript:;" onclick="fnTopMenu('/kp2000/kp2020.do')">자산이력관리</a>
			<ol>
				<% if (topGrantReadYnArr.indexOf("KP2020") > -1) { %>
				<li><a href="/kp2000/kp2020.do" style="color:#ffffff">태그발행내역</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		<% if (topGrantReadYnArr.indexOf("KP1900") > -1) { %>
		<li class="">
			<a href="javascript:;" onclick="fnTopMenu('/kp1900/kp1910.do')">시스템관리</a>
			<ol>
				<% if (topGrantReadYnArr.indexOf("KP1910") > -1) { %>
				<li><a href="/kp1900/kp1910.do" style="color:#ffffff">관리자관리</a></li>
				<% } %>
				<% if (topGrantReadYnArr.indexOf("KP1920") > -1) { %>
				<li><a href="/kp1900/kp1920.do" style="color:#ffffff">메뉴별권한관리</a></li>
				<% } %>
				<% if (topGrantReadYnArr.indexOf("KP1960") > -1) { %>
				<li><a href="/kp1900/kp1960.do" style="color:#ffffff">담당지역관리</a></li>
				<% } %>
				<% if (topGrantReadYnArr.indexOf("KP1930") > -1) { %>
				<li><a href="/kp1900/kp1930.do" style="color:#ffffff">단말기관리</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		</ul>
	</div>
</div>
<div id="content" class="eb_css">