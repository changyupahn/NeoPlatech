<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
	String curAction = "/sample/sampleRowList.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList sampleRowList = RequestUtil.getCommonList(request, "sampleRowList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<script type="text/javascript">

var totalRowNo = <%=sampleRowList.size()%>;

function fnWrite(){
	
	var sw = 1;
	
	$('#tbodyRow tr').each(function(){
		if( sw == 1 ){
			var rno = $(this).attr("id").replace(/trRow_/gi, "");
			if( $('#COL2_'+rno).val() == "" ){
				alert(rno+"번 행의 제목을 입력해주세요.");
				sw = 0;
				return;
			}
			if( $('#COL3_'+rno).val() == "" ){
				alert(rno+"번 행의 작성자를 입력해주세요.");
				sw = 0;
				return;
			}
			if( $('#COL4_'+rno).val() == "" ){
				alert(rno+"번 행의 등록일을 입력해주세요.");
				sw = 0;
				return;
			}
		}
	});
	
	if( sw == 1 ){	
		var frm = document.sForm;
		frm.action = "/sample/sampleRowModifyProc.do";
		frm.submit();
	}
}
//행추가
function fnInsRow(){
	totalRowNo++;
	$.ajax({
		type : "POST",
		url : "/sample/sampleRowAjax.do",
		data : {
			num : totalRowNo
		},
		success:function(data)		
		{
			fnInitRowNum();
			$('#tbodyRow').append( data );
		}
	});
}

//행삭제
function fnDelRow(n){
	if( confirm("선택하신 행을 삭제하시겠습니까?") ){
		$('#trRow_'+n).remove();
	}
	fnInitRowNum();
}

//행번호 갱신
function fnInitRowNum(){
	var spanNo = 1;
	$('.spanNo').each(function(){
		$(this).html(""+spanNo);
		spanNo++;
	});
}
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left">ROW 추가/삭제 샘플</h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" name="pageIdx" value="<%=cmRequest.getString("pageIdx")%>" />
	<input type="hidden" name="pageSize" value="<%=cmRequest.getString("pageSize")%>" />

	<table width="100%" class="table-search" border="0">
	<colgroup>
		<col width="" />
	</colgroup>
	<tr>
	<th>관리</th>
	<td>
		<span class="button"><input type="button" value="등록" onclick="fnWrite();"></span>
		<span class="button"><input type="button" value="행추가" onclick="fnInsRow();"></span>
	</td>
	</tr>
	</table>

	<table width="100%" class="table-line" summary="" >
	<colgroup>
		<col width="80px" />
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
			<th scope="col" class="title" nowrap>행삭제</th>
		</tr>
	</thead>
	<tbody id="tbodyRow">
		<%
		int pagingFristNo = PagingUtil.getPagingFristNo(sampleRowList.totalRow, sampleRowList.pageIdx, sampleRowList.pageSize);
		for(int i=0; i<sampleRowList.size(); i++){
			CommonMap sampleRow = sampleRowList.getMap(i);
		%>
		<tr id="trRow_<%=(i+1)%>">
			<td>
				<input type="hidden" id="COL1_<%=(i+1)%>" name="COL1" value="<%=sampleRow.getString("COL1")%>" />
				<span class="spanNo"><%=(i+1)%></span>
			</td>
			<td><input type="text" id="COL2_<%=(i+1)%>" name="COL2" value="<%=sampleRow.getString("COL2")%>" /></td>
			<td><input type="text" id="COL3_<%=(i+1)%>" name="COL3" value="<%=sampleRow.getString("COL3")%>" /></td>
			<td><input type="text" id="COL4_<%=(i+1)%>" name="COL4" value="<%=sampleRow.getString("COL4")%>" /></td>
			<td><a href="javascript:fnDelRow('<%=(i+1)%>');">행삭제</a></td>
		</tr>
		<%
		}
		%>
	</tbody>
	</table>
	
	</form>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>
