<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/default.jsp" %>

<%
String curAction = "/kp2100/kp2151Search.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonMap viewData = RequestUtil.getCommonMap(request, "viewData"); //검색값 유지
int idx = 0;
int colmax = 0;
int colcnt = cmRequest.getInt("colcnt");

if (!ssAuthManager) {
	cmRequest.put("sUserNo", cmRequest.getString("sUserNo", SessionUtil.getString("userNo")));
	cmRequest.put("sUserName", cmRequest.getString("sUserName", SessionUtil.getString("userName")));
	cmRequest.put("sDeptNo", cmRequest.getString("sDeptNo", SessionUtil.getString("deptNo")));
	cmRequest.put("sDeptName", cmRequest.getString("sDeptName", SessionUtil.getString("deptName")));
}


String reqData = viewData.getString("LG_PART_NO","1234567890128");

%>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/common/js/jquery-barcode.js"></script>
<script type="text/javascript">
$("#bcTarget1").barcode("<%=reqData%>", "code128",{barWidth:2, barHeight:30});
</script>
<table class="search01">
	<colgroup>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
		<col width="100px" height = "100px"/>
		<col width="240px" height = "100px"/>
	</colgroup>
		<tr>
	 <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th height = "30px">바코드 :</th>
		<td height = "30px">
		    <div id="bcTarget1" style="height:50px;"></div>		
		</td> 
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th height = "30px">주문번호 :</th>
		<td height = "30px">
			<input type="text" id="sDemandId" name="sDemandId" value="<%=viewData.getString("DEMAND_ID")%>" class="def" readOnly="true" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th height = "30px">Part No :</th>
		<td height = "30px">
			<input type="text" id="sLgPartNo" name="sLgPartNo" value="<%=viewData.getString("LG_PART_NO")%>" class="def" readOnly="true"/>
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th height = "30px">수량 :</th>
		<td height = "30px">
			<input type="text" id="sBomQty" name="sBomQty" value="<%=viewData.getString("BOM_QTY")%>" class="def" readOnly="true"/>
		</td> 	
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th height = "30px">발행 PC :</th>
		<td height = "30px">
			<input type="text" id="sSendingPcName" name="sSendingPcName" value="<%=viewData.getString("SENDING_PC_NAME")%>" class="def" readOnly="true"/>
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th height = "30px">생산일자 :</th>
		<td height = "30px">
			<input type="text" id="sSendTime" name="sSendTime" value="<%=viewData.getString("SEND_TIME")%>" class="def" readOnly="true"/>
		</td> 				
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>	