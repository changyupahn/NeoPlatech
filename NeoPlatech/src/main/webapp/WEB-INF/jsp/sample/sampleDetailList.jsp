<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String curAction = "/sample/sampleDetailList.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList sampleDetailList = RequestUtil.getCommonList(request, "sampleDetailList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>


<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.action = "<%=curAction%>";
	frm.submit();
}
function fnView(col1){
	/* GET 방식 팝업 */
	fnOpenPop("/sample/sampleView.do?COL1="+col1, "SampleDetailPop", "500", "600");
	
	/* POST 방식 팝업
	fnOpenPop("", "SampleDetailPop", "500", "600");
	
	var frm = document.sForm;
	frm.COL1.value = col1;
	frm.action = "/sample/sampleView.do";
	frm.target = "SampleDetailPop";
	frm.submit();
	*/
}
function fnDetailAjax(col1){
	
	$.ajax({
		type : "POST",
		url : "/sample/sampleDetailViewAjax.do",
		data : {
			COL1 : col1
		},
		success:function(data)		
		{
			$('.trDetailClass').remove();
			$('#trList_'+col1).after(data);
			$('.trDetailClass').show();
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
		}
	});
	
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">샘플 목록</h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" name="pageSize" value="<%=cmRequest.getString("pageSize")%>" />
	<input type="hidden" name="COL1" value="" />

	<table width="100%" class="table-search" border="0">
	<colgroup>
		<col width="100px" />
		<col width="250px" />
		<col width="100px" />
		<col width="" />
	</colgroup>
	<tr>
	<th>기간</th>
	<td>
		<input type="text" id="startDt" name="startDt" value="<%=DateUtil.formatDate(cmRequest.getString("startDt"),"-")%>" class="datepicker" />
		~ <input type="text" id="endDt" name="endDt" value="<%=DateUtil.formatDate(cmRequest.getString("endDt"),"-")%>" class="datepicker" />
	</td>
	<th>검색어</th>
	<td>
		<select id="searchGubun" name="searchGubun">
			<option value="" <%="".equals(cmRequest.getString("searchGubun"))?"selected":""%>>전체</option>
			<option value="1" <%="1".equals(cmRequest.getString("searchGubun"))?"selected":""%>>제목</option>
			<option value="2" <%="2".equals(cmRequest.getString("searchGubun"))?"selected":""%>>작성자</option>
		</select>
		<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
	</td>
	</tr>
	</table>
	
	</form>

	<table width="100%" class="table-line" summary="" >
	<colgroup>
		<col width="60px" />
		<col width="" />
		<col width="100px" />
		<col width="120px" />
		<col width="100px" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col" class="title" nowrap>번호</th>
			<th scope="col" class="title" nowrap>제목</th>
			<th scope="col" class="title" nowrap>작성자</th>
			<th scope="col" class="title" nowrap>등록일</th>
			<th scope="col" class="title" nowrap>첨부파일</th>
		</tr>
	</thead>
	<tbody>
		<% if(sampleDetailList.size() == 0){ %>
		<tr>
			<td colspan="5">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(sampleDetailList.totalRow, sampleDetailList.pageIdx, sampleDetailList.pageSize);
		for(int i=0; i<sampleDetailList.size(); i++){
			CommonMap sampleDetail = sampleDetailList.getMap(i);
		%>
		<tr id="trList_<%=sampleDetail.getString("COL1")%>">
			<td><%=(pagingFristNo - i)%></td>
			<td><a href="javascript:fnDetailAjax('<%=sampleDetail.getString("COL1")%>');"><%=sampleDetail.getString("COL2")%></a></td>
			<td><%=sampleDetail.getString("COL3")%></td>
			<td><%=sampleDetail.getString("COL4")%></td>
			<td><%=sampleDetail.getString("ATCH_FILE_ID")%></td>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>
	
	<div class="paginate">
		<!-- pageing -->
		<%=PagingUtil.PagingDataB(sampleDetailList,"sForm",curAction)%>
		<!-- pageing -->
	</div>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

