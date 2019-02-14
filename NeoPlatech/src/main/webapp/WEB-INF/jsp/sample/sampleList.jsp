<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String curAction = "/sample/sampleList.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList sampleList = RequestUtil.getCommonList(request, "sampleList");
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
	var frm = document.sForm;
	frm.COL1.value = col1;
	frm.action = "/sample/sampleView.do";
	frm.submit();
}
function fnWrite(){
	var frm = document.sForm;
	frm.action = "/sample/sampleWriteForm.do";
	frm.submit();
}
function fnFileWrite(){
	var frm = document.sForm;
	frm.action = "/sample/sampleFileWriteForm.do";
	frm.submit();
}
function fnSampleAjax(){
	$.ajax({
		type : "POST",
		url : "/sample/sampleAjax.do",
		data : {
			AAAA : $('#AAAA').val()
		},
		//dataType : "json",
		success:function(data)
		{
			$('#ajaxResult').append(data);
		},
		error:function(xhr, ajaxOptions, thrownError)
		{
		},
		complete:function()
		{
		}
	});
}
function fnSampleJsonp(){
	$.ajax({
		type : "POST",
		url : "http://boassoft/kp300/kp319Validation.do",
		async : false,
		data : {
			GYEYAG_NO : 'L201000001',
			SEQ : '1'
		},
		dataType: 'jsonp',
		success:function(data)
		{
			if( data.result == "Y" )
			{
				//승인 Script
			}
			else if( data.result == "N" )
			{
				alert("사용자 및 NTIS 등록을 진행해 주세요.");
			}
			else
			{
				alert("승인오류. 전산담당자에게 문의해주세요.");
			}
		},
		jsonpCallback: 'mycallback'
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
		<span class="button"><input type="button" value="일반등록" onclick="fnWrite();"></span>
		<span class="button"><input type="button" value="파일첨부등록" onclick="fnFileWrite();"></span>
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
		<% if(sampleList.size() == 0){ %>
		<tr>
			<td colspan="5">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(sampleList.totalRow, sampleList.pageIdx, sampleList.pageSize);
		for(int i=0; i<sampleList.size(); i++){
			CommonMap sample = sampleList.getMap(i);
		%>
		<tr>
			<td><%=(pagingFristNo - i)%></td>
			<td><a href="javascript:fnView('<%=sample.getString("COL1")%>');"><%=sample.getString("COL2")%></a></td>
			<td><%=sample.getString("COL3")%></td>
			<td><%=sample.getString("COL4")%></td>
			<td><%=sample.getString("ATCH_FILE_ID")%></td>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>

	<div class="paginate">
		<!-- pageing -->
		<%=PagingUtil.PagingDataB(sampleList,"sForm",curAction)%>
		<!-- pageing -->
	</div>

	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>

</body>
</html>



