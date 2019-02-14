<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="boassoft.util.*"%>
<%
	CommonMap cmForward = RequestUtil.getCommonMap(request, "cmForward");
	//System.out.println("aaa" + cmForward.getString("forwardUrl"));
%>
<form id="sForm" name="sForm" method="post">
<%
		Set s1 = cmForward.keySet();
		Iterator it = s1.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
%>
		 	<input type="hidden" name="<%=key%>" value="<%=cmForward.getString(key)%>" />
<%
		}
%>
</form>
<script type="text/javascript" src="/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
try {
<% if( !"".equals(cmForward.getString("prevCustomScript"))){ %>
<%=cmForward.getString("prevCustomScript", false)%>
<% } %>
	
<% if( !"".equals(cmForward.getString("forwardMsg"))){ %>
	alert("<%=cmForward.getString("forwardMsg")%>");
<% } %>

<% if( !"".equals(cmForward.getString("customScript"))){ %>
<%=cmForward.getString("customScript")%>
<% } %>

<% if( !"".equals(cmForward.getString("forwardUrl"))){ %>
	<% if( "none".equals(cmForward.getString("forwardUrl"))){ %>
	<% } else if( "pop_close".equals(cmForward.getString("forwardUrl"))){ %>
		opener.fnReload();
		self.close();		
	<% }else{ %>
		<% if( !"".equals(cmForward.getString("forwardTarget"))){ %>
			window.open("", "<%=cmForward.getString("forwardTarget")%>");
			document.sForm.target = "<%=cmForward.getString("forwardTarget")%>";
		<% } %>
		document.sForm.target = "_self";
		document.sForm.action = "<%=cmForward.getString("forwardUrl")%>";
		document.sForm.submit();
	<% } %>
<% } else { %>
	location.href = "/";
<% } %>
} catch(e) {
	alert(e.description);
}
</script>