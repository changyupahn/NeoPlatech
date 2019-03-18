<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1900/kp1970Search.do";
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
		<th>부서명 :</th>
		<td>
			<input type="text" id="sOrgName" name="sOrgName" value="<%=cmRequest.getString("sOrgName")%>" class="def" />
		</td> 	
 <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>창고코드 :</th>
		<td>
			<input type="text" id="sWarehouseCode" name="sWarehouseCode" value="<%=cmRequest.getString("sWarehouseCode")%>" class="def" />
		</td>
<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>창고명 :</th>
		<td>
			<input type="text" id="sName" name="sName" value="<%=cmRequest.getString("sName")%>" class="def" />
		</td>		
<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>사용여부 :</th>
		<td>
				<select id="searchUseYn" name="searchUseYn" style="min-width:80px">
				<option value="Y" <%="Y".equals(cmRequest.getString("sUseYn","1"))?"selected":""%>>사용</option>
				<option value="N" <%="N".equals(cmRequest.getString("sUseYn"))?"selected":""%>>미사용</option>
			</select>
		</td> 		
<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>