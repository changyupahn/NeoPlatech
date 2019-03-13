<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %><%
String curAction = "/kp2100/kp2130Search.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
int idx = 0;
int colmax = 0;
int colcnt = cmRequest.getInt("colcnt");

if (!ssAuthManager) {
	cmRequest.put("sUserNo", cmRequest.getString("sUserNo", SessionUtil.getString("userNo")));
	cmRequest.put("sUserName", cmRequest.getString("sUserName", SessionUtil.getString("userName")));
	cmRequest.put("sDeptNo", cmRequest.getString("sDeptNo", SessionUtil.getString("deptNo")));
	cmRequest.put("sDeptName", cmRequest.getString("sDeptName", SessionUtil.getString("deptName")));
}
%>
<table class="search01">
	<colgroup>
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
		<col width="100px" />
		<col width="240px" />
	</colgroup>
		<tr>
		 <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>PO NO :</th>
		<td>
			<input type="text" id="sPkgPoNo" name="sPkgPoNo" value="<%=cmRequest.getString("sPkgPoNo")%>" class="def" />
		</td> 
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>제품번호 :</th>
		<td>
			<input type="text" id="sLgPartNo" name="sLgPartNo" value="<%=cmRequest.getString("sLgPartNo")%>" class="def" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>모델 :</th>
		<td>
			<input type="text" id="sToolName" name="sToolName" value="<%=cmRequest.getString("sToolName")%>" class="def" />
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>라인명 :</th>
		<td>
			<input type="text" id="sLine" name="sLine" value="<%=cmRequest.getString("sLine")%>" class="def" />
		</td> 	
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>일련번호 :</th>
		<td>
			<input type="text" id="sLgPartNo" name="sLgPartNo" value="<%=cmRequest.getString("sLgPartNo")%>" class="def" />
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>고유key :</th>
		<td>
			<input type="text" id="sOdQtyId" name="sOdQtyId" value="<%=cmRequest.getString("sOdQtyId")%>" class="def" />
		</td> 	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>주문번호 :</th>
		<td>
			<input type="text" id="sDemandId" name="sDemandId" value="<%=cmRequest.getString("sDemandId")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>업체명 :</th>
		<td>
			<input type="text" id="sFinalVendor" name="sFinalVendor" value="<%=cmRequest.getString("sFinalVendor")%>" class="def" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>포장재구분 :</th>
		<td>
			<input type="text" id="sClass" name="sClass" value="<%=cmRequest.getString("sClass")%>" class="def" />
		</td>				
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>	