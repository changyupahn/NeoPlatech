<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp2100/kp2150Search.do";
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
		<th>고유key :</th>
		<td>
			<input type="text" id="sNeoPlanKey" name="sNeoPlanKey" value="<%=cmRequest.getString("sNeoPlanKey")%>" class="def" />
		</td> 
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>주문번호 :</th>
		<td>
			<input type="text" id="sDemandId" name="sDemandId" value="<%=cmRequest.getString("sDemandId")%>" class="def" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>제품번호 :</th>
		<td>
			<input type="text" id="sLgPartNo" name="sLgPartNo" value="<%=cmRequest.getString("sLgPartNo")%>" class="def" />
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>라인명 :</th>
		<td>
			<input type="text" id="sNeoLine" name="sNeoLine" value="<%=cmRequest.getString("sNeoLine")%>" class="def" />
		</td> 	
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>품번 :</th>
		<td>
			<input type="text" id="sSubPartNo" name="sSubPartNo" value="<%=cmRequest.getString("sSubPartNo")%>" class="def" />
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>재발행이력 :</th>
		<td>
			<input type="text" id="sRePrint" name="sRePrint" value="<%=cmRequest.getString("sRePrint")%>" class="def" />
		</td> 	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>규격번호 :</th>
		<td>
			<input type="text" id="sSubPartDesc" name="sSubPartDesc" value="<%=cmRequest.getString("sSubPartDesc")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>생산지 :</th>
		<td>
			<input type="text" id="sVendor" name="sVendor" value="<%=cmRequest.getString("sVendor")%>" class="def" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>발주구분 :</th>
		<td>
			<input type="text" id="sGoWith" name="sGoWith" value="<%=cmRequest.getString("sGoWith")%>" class="def" />
		</td>			
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>	