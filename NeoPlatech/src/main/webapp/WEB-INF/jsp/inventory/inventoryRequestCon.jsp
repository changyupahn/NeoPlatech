<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/inventory/selectRequestCon.do";
String xlsDnAction2 = "/inventory/selectRequestConXls.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList requestCon = RequestUtil.getCommonList(request, "requestCon");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<script type="text/javascript">

function fnSelectRequestConXls(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction2%>";
	frm.target = "_self";
	frm.submit();
}

function fnClose(){
	
	window.close();
}

</script>

</head>
<body>

	<h2 class="title_left">갱신요청내용확인</h2>
	
	<table width="100%" class="table-line" summary="" >
	<colgroup>
		<col width="60px" />
		<col width="100px" />
		<col width="" />
		<col width="170px" />
		<col width="80px" />
		<col width="80px" />
		<col width="80px" />
		<col width="80px" />
	</colgroup>
	<thead>
		<tr>    
			<th scope="col" class="title" nowrap>번호</th>
			<th scope="col" class="title" nowrap>물품번호</th>
			<th scope="col" class="title" nowrap>물품명</th>
			<th scope="col" class="title" nowrap>기관명</th>
			<th scope="col" class="title" nowrap>관리자</th>
			<th scope="col" class="title" nowrap>호실</th>
			<th scope="col" class="title" nowrap>변경기준일</th>
			<th scope="col" class="title" nowrap>생성일</th>
		</tr>
	</thead>
	<tbody>
		<% if(requestCon.size() == 0){ %>
		<tr>
			<td colspan="50">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(requestCon.totalRow, requestCon.pageIdx, requestCon.pageSize);
		for(int i=0; i<requestCon.size(); i++){
			CommonMap rcmap = requestCon.getMap(i);
		%>
		<tr>
			<td><%=(pagingFristNo - i)%></td>
			<td><%=rcmap.getString("asset_no")%></td>
			<td><%=rcmap.getString("asset_name")%></td>
			<td><%=rcmap.getString("org_name")%></td>
			<td><%=rcmap.getString("user_name")%></td>
			<td><%=rcmap.getString("hosil")%></td>
			<td><%=DateUtil.formatDate(rcmap.getString("cng_basic_dt"), "-") %></td>
			<td><%=DateUtil.formatDate(rcmap.getString("create_dt"), "-") %></td>
		</tr>
		<%
		}
		%>
	</tbody>
	
	</table>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" name="pageSize" value="<%=cmRequest.getString("pageSize")%>" />
	</form>
	
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
		<tr>
		<td width="30px" class="l" style="padding-top:5px;">
			<span class="button"><input type="button" value="엑셀다운로드" onClick="fnSelectRequestConXls()"></span>&nbsp;
		</td>
		<td width="" style="padding-top:5px;">
			<div class="paginate">
				<!-- pageing -->
				<%=PagingUtil.PagingDataB(requestCon,"sForm",curAction)%>
				<!-- pageing -->
			</div>
		</td>
		<td width="30px" style="padding-top:5px;">
			<span class="button"><input type="button" value="닫기" onClick="fnClose()"></span>
		</td>
		
		</tr>
	</table>
</body>
</html>

		

