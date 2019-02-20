<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<script type="text/javascript">
function forward(){
	location.href = "/adminIndex.do";
}
</script>
</head>
<body onload="forward()">
<iframe src="/validationQuery.do" width="0" height="0"></iframe>
</body>
</html>