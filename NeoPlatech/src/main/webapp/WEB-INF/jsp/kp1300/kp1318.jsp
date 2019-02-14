<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "자산관리카드";
String curAction = "/kp1300/kp1318.do";

CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData");
CommonList histList = RequestUtil.getCommonList(request, "histList");
CommonList depreList = RequestUtil.getCommonList(request, "depreList");
CommonList imgList = RequestUtil.getCommonList(request, "imgList");
%>

<html style="overflow:auto;">
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<link href="/common/css/print.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function fnPrint(){
	window.onbeforeprint = beforePrint;
	window.onafterprint = afterPrint;
	window.print();
}

function beforePrint()
{
	$('#btnLayer').hide();
}

function afterPrint()
{
	$('#btnLayer').show();
}
</script>

</head>
<body style="overflow:auto;">

	<div id="btnLayer" style="text-align:right">
		<span class="button"><input type="button" value="인쇄" onclick="fnPrint();"></span>
		<span class="button"><input type="button" value="닫기" onclick="self.close();"></span>
	</div>

	<h2><%= pageTitle %></h2>

	<br />

	<table class="table-print-summary" width="100%" border="0" cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="10%" />
		<col width="45%" />
		<col width="18%" />
		<col width="27%" />
	</colgroup>
	<tr>
		<th>자산코드</th>
		<td>: <%= viewData.getString("assetNo") %></td>
		<th>관리부서</th>
		<td>: <%= viewData.getString("deptName") %></td>
	</tr>
	<tr>
		<th>취득일</th>
		<td>: <%= DateUtil.formatDate(viewData.getString("aqusitDt"), "-") %></td>
		<th>취급책임자</th>
		<td>: <%= viewData.getString("deptHeadUserName") %></td>
	</tr>
	<tr>
		<th>취득방법</th>
		<td>: <%= viewData.getString("aqusitTypeName") %></td>
		<th>실제사용자</th>
		<td>: <%= viewData.getString("userName") %></td>
	</tr>
	<tr>
		<th>계정번호</th>
		<td>: <%= viewData.getString("accntNum") %></td>
		<th>관리(분임)책임자</th>
		<td>: <%= viewData.getString("deptHeadUserName") %></td>
	</tr>
	</table>

	<br />

	<h3>1. 자산의 표시</h3>

	<table class="table-print-list" width="100%" border="1" cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="20%" />
		<col width="10%" />
		<col width="10%" />
		<col width="10%" />
		<col width="13%" />
		<col width="13%" />
		<col width="14%" />
		<col width="10%" />
	</colgroup>
	<thead>
		<tr>
			<th rowspan="2">품목명</th>
			<th rowspan="2">계약번호</th>
			<th rowspan="2">수량</th>
			<th rowspan="2">단가</th>
			<th colspan="2">취득가액</th>
			<th rowspan="2">제조국<br />(제조업체)</th>
			<th rowspan="2">내용년수</th>
		</tr>
		<tr>
			<th>원화</th>
			<th>외화</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><%= viewData.getString("itemName") %></td>
			<td class="c"><%= viewData.getString("contNo") %></td>
			<td class="c"><%= StringUtil.commaNum(viewData.getString("assetCnt")) %></td>
			<td class="c"><%= StringUtil.commaNum(viewData.getString("aqusitUnitAmt","0")) %></td>
			<td class="r"><%= StringUtil.commaNum(viewData.getString("aqusitAmt","0")) %></td>
			<td class="r">
				<%= StringUtil.commaNum2(viewData.getString("aqusitForeignAmt","0")) %>
				<%
				if (viewData.getDouble("aqusitForeignAmt") != 0
						&& !"".equals(viewData.getString("capiTypeCd"))) {
				%>
				<br/>(<%=viewData.getString("capiTypeCd")%>)
				<%
				}
				%>
			</td>
			<td class="c"><%= viewData.getString("mkNationName") %><br /><%= viewData.getString("mkCompanyName") %></td>
			<td class="c"><%= viewData.getString("usefulLife") %></td>
		</tr>
		<tr>
			<th>상세명칭</th>
			<td colspan="3"><%= viewData.getString("assetName") %></td>
			<th>설치장소</th>
			<td colspan="3">
				<%= viewData.getString("posName") %>
			</td>
		</tr>
	</tbody>
	</table>

	<br />

	<h3>2. 자산의 이력</h3>

	<table class="table-print-list" width="100%" border="1" cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="14%" />
		<col width="14%" />
		<col width="58%" />
		<col width="14%" />
	</colgroup>
	<thead>
		<tr>
			<th>구분</th>
			<th>변경일자</th>
			<th>변경내용</th>
			<th>변경자</th>
		</tr>
	</thead>
	<tbody>
		<% if(histList.size() == 0){ %>
		<tr>
			<td colspan="4">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		int sw = 0;
		for(int i=0; i<histList.size(); i++){
			CommonMap hist = histList.getMap(i);

			if (sw == 0) {
		%>
		<tr>
			<td><%=hist.getString("histTypeName") %></td>
			<td><%= DateUtil.formatDateTime(hist.getString("histDt"), "-", ":", 8) %></td>
			<td><%=hist.getString("histContent") %></td>
			<td><%=hist.getString("userName") %></td>
		</tr>
		<%
			}
		}
		%>
	</tbody>
	</table>

	<br />

	<h3>3. 자산의 상각내역</h3>

	<table class="table-print-list" width="100%" border="1" cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="25%" />
		<col width="25%" />
		<col width="25%" />
		<col width="25%" />
	</colgroup>
	<thead>
		<tr>
			<th>년도별</th>
			<th>당기상각액</th>
			<th>상각누계액</th>
			<th>잔존가</th>
		</tr>
	</thead>
	<tbody>
		<% if(depreList.size() == 0){ %>
		<tr>
			<td colspan="4">목록이 없습니다.</td>
		</tr>
		<% } %>
		<%
		double sumDepreAmt2 = 0;
		for(int i=0; i<depreList.size(); i++){
			CommonMap depre = depreList.getMap(i);
			sumDepreAmt2 += depre.getDouble("sumDepreAmt");
			depre.put("sumDepreAmt2", sumDepreAmt2);
		%>
		<tr>
			<td class="c"><%= depre.getString("depreYear") %></td>
			<td class="r"><%= StringUtil.commaNum(depre.getString("sumDepreAmt")) %></td>
			<td class="r"><%= StringUtil.commaNum(depre.getString("sumDepreAmt2")) %></td>
			<td class="r"><%= StringUtil.commaNum(depre.getString("remainAmt")) %></td>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>

	<br />

	<h3>4. 자산이미지</h3>

	<div id="layer_images">
		<%
		for (int i=0; i<imgList.size(); i++) {
			CommonMap img = imgList.getMap(i);
		%>
		<img src="<%=img.getString("webFilePath")%>/tn<%=img.getString("fileNm")%>" width="300" height="300" />
		<%
		}
		%>
	</div>

</body>
</html>



