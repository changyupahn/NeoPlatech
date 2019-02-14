<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/print/selectList.do";
String xlsDnAction = "/print/selectListXls.do";
String tagPrintAction = "/print/exec.do";
String tagRePrintAction = "/print/rePrint.do";
String tagRePrintAjaxAction = "/print/rePrintAjax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList printHistoryList = RequestUtil.getCommonList(request, "printHistoryList");
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
function fnXlsDn(){
	var frm = document.sForm;
	frm.pageIdx.value = "1";
	frm.action = "<%=xlsDnAction%>";
	frm.target = "_self";
	frm.submit();
}
function fnTagRePrint(printSeq){
	var frm = document.sForm;
	frm.printSeq.value = printSeq;
	frm.action = "<%=tagRePrintAction%>";
	frm.target = "_self";
	frm.submit();
}

function fnTagRePrintAjax(printSeq){
	$.ajax({
		type : "POST",
		url : "<%=tagRePrintAjaxAction%>",
		dataType : "json",
		data : {
			printSeq : printSeq
		},		
		success:function(data)		
		{
			alert("재출력 요청되었습니다.");
		}
	});
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">태그발행내역</h2>

	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" id="dataOrder" name="dataOrder" value="<%=cmRequest.getString("dataOrder")%>" />
	<input type="hidden" id="dataOrderArrow" name="dataOrderArrow" value="<%=cmRequest.getString("dataOrderArrow", "asc")%>" />
	
	<input type="hidden" id="printSeq" name="printSeq" value="" />
	
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
			<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>물품번호</option>
			<option value="2" <%="2".equals(cmRequest.getString("searchGubun"))?"selected":""%>>물품명</option>
			<option value="3" <%="3".equals(cmRequest.getString("searchGubun"))?"selected":""%>>규격</option>
		</select>
		<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
	</td>
	<td>
		<span class="button"><input type="submit" value="검색" onclick="fnSearch();"></span>
		<span class="button"><input type="button" value="엑셀다운로드" onclick="fnXlsDn();"></span>
	</td>
	</tr>
	</table>
	
	<p>총 <strong><%=printHistoryList.totalRow%></strong> 건 </p>

	<table width="100%" class="table-line" summary="" >	
	<colgroup>
		<col width="50px" />
		<col width="150px" />
		<col width="150px" />
		<col width="180px" />
		<col width="100px" />
		<col width="120px" />
		<col width="120px" />
		<col width="150px" />
		<col width="80px" />
		<col width="80px" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col" class="title dataOrder" nowrap>No</th>
			<th scope="col" class="title dataOrder" dataOrder="asset_no" nowrap>물품번호</th>
			<th scope="col" class="title dataOrder" dataOrder="asset_name" nowrap>물품명</th>
			<th scope="col" class="title dataOrder" dataOrder="asset_size" nowrap>규격</th>
			<th scope="col" class="title dataOrder" dataOrder="asset_type" nowrap>물품구분</th>
			<th scope="col" class="title dataOrder" dataOrder="aqusit_dt" nowrap>취득일</th>
			<th scope="col" class="title dataOrder" dataOrder="tmp_title" nowrap>비고</th>
			<th scope="col" class="title dataOrder" dataOrder="print_dt" nowrap>출력요청일시</th>
			<th scope="col" class="title dataOrder" dataOrder="print_yn" nowrap>상태</th>
			<th scope="col" class="title dataOrder" nowrap>재출력</th>
		</tr>
	</thead>
	<tbody>
		<% if(printHistoryList.size() == 0){ %>
		<tr>
			<td colspan="50">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(printHistoryList.totalRow, printHistoryList.pageIdx, printHistoryList.pageSize);
		for(int i=0; i<printHistoryList.size(); i++){
			CommonMap inout = printHistoryList.getMap(i);
			
			String print_st_name = "";
			if ("N".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄대기중";
			} else if ("R".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄중";
			} else if ("Y".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄완료";
			} else if ("F".equals(inout.getString("print_yn"))) {
				print_st_name = "인쇄실패";
			}
		%>
		
		<tr>
			<td><%=(pagingFristNo - i)%></td>
			<td><%=inout.getString("asset_no")%></td>
			<td><%=inout.getString("asset_name")%></td>
			<td><%=inout.getString("asset_size")%></td>
			<td><%=inout.getString("asset_type")%></td>
			<td>
				<%=DateUtil.formatDate(inout.getString("aqusit_dt"), "-")%>
			</td>
			<td><%=inout.getString("tmp_title")%></td>
			<td>
				<%=DateUtil.formatDate(inout.getString("print_dt"), "-")%>
				<%=DateUtil.formatTime(inout.getString("print_dt"), ":")%>
			</td>
			<td><%=print_st_name%></td>		
			<td>
				<span class="button"><input type="submit" value="재출력" onclick="fnTagRePrintAjax('<%= inout.getString("print_seq")%>');"></span>
				<!-- <span class="button"><input type="submit" value="재출력" onclick="fnTagRePrint('<%= inout.getString("print_seq") %>');"></span> -->
			</td>
		</tr>
		<%
		}
		%>
		
	</tbody>
	</table>
	
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td width="">
		<div class="paginate">
		<!-- pageing -->
		<%=PagingUtil.PagingDataB(printHistoryList,"sForm",curAction)%>
		<!-- pageing -->
	</div>
	</td>
	<td width="150px" class="r">
		<select id="pageSize" name="pageSize" onchange="fnSearch()" style="border:1px solid gray;">
		<option value="15" <%="15".equals(cmRequest.getString("pageSize","15"))?"selected":""%>>페이지당 15개</option>
		<option value="30" <%="30".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 30개</option>
		<option value="50" <%="50".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 50개</option>
		<option value="100" <%="100".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 100개</option>
		<option value="200" <%="200".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 200개</option>
		<option value="300" <%="300".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 300개</option>
		<option value="1000" <%="1000".equals(cmRequest.getString("pageSize"))?"selected":""%>>페이지당 1000개</option>
		</select>
	</td>
	</tr>
	</table>
	
	</form>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>

		

