<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/system/userManage.do";
String curDetailAjax = "/system/userManageDetailAjax.do";
String curWriteAction = "/system/userManageProc.do";
String curDelAction = "/system/userManageDelProc.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList adminList = RequestUtil.getCommonList(request, "adminList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=curAction%>";
	frm.submit();
}
function fnDetailAjax(user_id){
	$.ajax({
		type : "POST",
		url : "<%=curDetailAjax%>",
		data : {
			user_id : user_id
		},
		dataType: 'json',
		success:function(data)		
		{
			if( data.user_id != "" ){
				$('#user_id').val(data.userId);
				//document.getElementById("user_id").value = data.user_id;
				$('#user_name').val(data.userName);
				
				$('#procMode').val("U");
				$('#user_id').attr("readonly",true);
				$('#btn_new_save').val("수정");
				$('#div_btn_del').show();
			}
		}
	});
}
function fnSubmit(){
	
	if( $('#user_id').val() == "" ){
		alert("아이디 필수입력입니다.");
		return;
	}
	if( $('#procMode').val() == "I" && $('#user_pw').val() == "" ){
		alert("비밀번호 필수입력입니다.");
		return;
	}
	if( $('#user_name').val() == "" ){
		alert("관리자명 필수입력입니다.");
		return;
	}
	if( $('#org_no').val() == "" ){
		alert("기관번호 필수입력입니다.");
		return;
	}
	
	if( confirm("저장하시겠습니까?") ){
		var frm = document.wForm;
		frm.action = "<%=curWriteAction%>";
		frm.submit();
	}
}
function fnDelete(){
	if( confirm("정말 삭제하시겠습니까?\n삭제시 해당 계정으로는 더이상 로그인이 불가합니다.") ){
		var frm = document.wForm;
		frm.action = "<%=curDelAction%>";
		frm.submit();
	}
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">시스템 관리</h2>
	
	<table border="0" cellspacing="0" cellpadding="0" summary="사용자관리, 디바이스관리 보기탭 입니다.">
	<tr>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center">
			<a href="/system/userManage.do"><b>사용자관리</b></a>
		</td>
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center">
			<a href="/system/deviceManage.do"><b>디바이스관리</b></a>
		</td>
		<!-- 
		<td height="20px" width="1x" bgcolor="#FFFFFF" ></td>
		<td height="20px" width="100px" bgcolor="#DDDDDD" style="cursor:hand;cursor:pointer;" align="center">
			<a href="/system/syncManage.do"><b>동기화관리</b></a>
		</td> -->
		<td height="20px" width="1x" bgcolor="#FFFFFF"> </td>
	</tr>
	</table>

	<table width="100%">
	<colgroup>
		<col width="49%" />
		<col width="1%" />
		<col width="50%" />
	</colgroup>
	<tbody>
		<tr>
			<td valign="top">
				<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
				<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
				<input type="hidden" name="pageSize" value="<%=cmRequest.getString("pageSize")%>" />
	
				<table width="100%" class="table-search" border="0">
				<colgroup>
					<col width="100px" />
					<col width="250px" />
					<col width="" />
				</colgroup>
				<tr>
				<th>검색어</th>
				<td>
					<select id="searchGubun" name="searchGubun" style="width:100px;">
						<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>아이디</option>
						<option value="2" <%="2".equals(cmRequest.getString("searchGubun"))?"selected":""%>>관리자명</option>
					</select>
					<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
				</td>
				<th class="l">
					<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
				</th>
				</tr>
				</table>
				
				</form>
			</td>
			<td valign="top">&nbsp;</td>
			<td valign="top">
				<table width="100%" class="table-search" border="0">
				<tr>
				<th class="l">
					<span class="button"><input type="button" id="btn_new_save" value="신규저장" onclick="fnSubmit();"></span>
				</th>
				<th class="r">
					<div id="div_btn_del" style="display:none;">
					<span class="button"><input type="button" value="삭제" onclick="fnDelete();"></span>
					</div>
				</th>
				</tr>
				</table>
			</td>
		</tr>
	</tbody>
	</table>

	<table width="100%">
	<colgroup>
		<col width="49%" />
		<col width="1%" />
		<col width="50%" />
	</colgroup>
	<tbody>
		<tr>
			<td valign="top">
				<table width="100%" class="table-line" summary="" >	
				<colgroup>
					<col width="80px" />
					<col width="100px" />
					<col width="" />
					<col width="100px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="title" nowrap>NO</th>
						<th scope="col" class="title" nowrap>아이디</th>
						<th scope="col" class="title" nowrap>성명</th>
					</tr>
				</thead>
				<tbody>
					<% if(adminList.size() == 0){ %>
					<tr>
						<td colspan="50">목록이 없습니다.</td>
					</tr>
					<% } %>
					<%
					int pagingFristNo = PagingUtil.getPagingFristNo(adminList.totalRow, adminList.pageIdx, adminList.pageSize);
					for(int i=0; i<adminList.size(); i++){
						CommonMap admin = adminList.getMap(i);
					%>
					<tr>
						<td><%=(pagingFristNo - i)%></td>
						<td><a onclick="fnDetailAjax('<%=admin.getString("user_id")%>');"><%=admin.getString("user_id")%></a></td>
						<td><%=admin.getString("user_name")%></td>
					</tr>
					<%
					}
					%>
				</tbody>
				</table>
			</td>
			<td valign="top">&nbsp;</td>
			<td valign="top">
				<form name="wForm" method="post" action="<%=curWriteAction%>">
				<input type="hidden" id="procMode" name="procMode" value="I" />
				<input type="hidden" id="org_no" name="org_no" value="1" />			
				
				<table width="100%" class="table-register">
				<colgroup>
					<col width="120px" />
					<col width="" />
				</colgroup>
				<tr>
				<th>아이디 : </th>
				<td><input type="text" id="user_id" name="user_id" value="" /></td>
				</tr>
				<tr>
				<th>비밀번호 : </th>
				<td>
					<input type="password" id="user_pw" name="user_pw" value="" />
					*변경시에만 입력
				</td>
				</tr>
				<tr>
				<th>관리자명 : </th>
				<td><input type="text" id="user_name" name="user_name" value="" /></td>
				</tr>
				</table>
				
				</form>
			</td>
		</tr>
	</tbody>
	</table>
	
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td width="50%">
		<div class="paginate">
		<!-- pageing -->
		<%=PagingUtil.PagingDataB(adminList,"sForm",curAction)%>
		<!-- pageing -->
		</div>
	</td>
	<td width="50%" class="r">
	</td>
	</tr>
	</table>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

