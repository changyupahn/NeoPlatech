<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="boassoft.util.*" %>
<%@ page import="egovframework.rte.psl.dataaccess.util.CamelUtil" %>
<%
String grantReadYnArr = boassoft.util.SessionUtil.getString("grantReadYnArr");
String grantWriteYnArr = boassoft.util.SessionUtil.getString("grantWriteYnArr");
String grantHreadYnArr = boassoft.util.SessionUtil.getString("grantHreadYnArr");
String grantHwriteYnArr = boassoft.util.SessionUtil.getString("grantHwriteYnArr");
String grantManagerYnArr = boassoft.util.SessionUtil.getString("grantManagerYnArr");
boolean ssAuthRead = true; //현재 페이지의 읽기권한
boolean ssAuthWrite = true; //현재 페이지의 쓰기권한
boolean ssAuthHRead = true; //현재 페이지의 부서 읽기 권한
boolean ssAuthHWrite = true; //현재 페이지의 부서 쓰기 권한
boolean ssAuthManager = true; //현재 페이지의 관리자 권한

//현재 페이지의 권한 체크 Start//
String requestURI = request.getRequestURI(); //요청 URI
int lastIdx = requestURI.lastIndexOf("/");
if( lastIdx > -1 )
	requestURI = requestURI.substring(lastIdx);
String menuCd = requestURI.replaceAll("\\D", "");
if( menuCd.length() < 4 )
	menuCd = "";
else
	menuCd = new StringBuffer().append(menuCd.substring(0, 3)).append("0").toString();

if( !"".equals(menuCd)){
	menuCd = new StringBuffer().append("[KP").append(menuCd).append("]").toString();
	if( grantReadYnArr.indexOf(menuCd) == -1 ){
		ssAuthRead = false;
	}
	if( grantWriteYnArr.indexOf(menuCd) == -1 ){
		ssAuthWrite = false;
	}
	if( grantHreadYnArr.indexOf(menuCd) == -1 ){
		ssAuthHRead = false;
	}
	if( grantHwriteYnArr.indexOf(menuCd) == -1 ){
		ssAuthHWrite = false;
	}
	if( grantManagerYnArr.indexOf(menuCd) == -1 ){
		ssAuthManager = false;
	}
}

if (menuCd.matches("^\\[KP70.+\\]$")) {
	ssAuthRead = true;
}

if( !ssAuthRead ){
	//out.print("조회 권한이 없거나 로그인 세션이 만료 되었습니다. 다시 접속해주세요.");
	response.sendRedirect("/loginForm.do");
	return;
}
//현재 페이지의 권한 체크 End//
%>