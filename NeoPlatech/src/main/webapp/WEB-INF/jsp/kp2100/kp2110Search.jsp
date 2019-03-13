<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp2100/kp2110Search.do";
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
			<input type="text" id="sPrimKey" name="sPrimKey" value="<%=cmRequest.getString("sPrimKey")%>" class="def" />
		</td> 
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>lg측제번 :</th>
		<td>
			<input type="text" id="sDemandId" name="sDemandId" value="<%=cmRequest.getString("sDemandId")%>" class="def" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>모델 :</th>
		<td>
			<input type="text" id="sModel" name="sModel" value="<%=cmRequest.getString("sModel")%>" class="def" />
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>라인명 :</th>
		<td>
			<input type="text" id="sLgLine" name="sLgLine" value="<%=cmRequest.getString("sLgLine")%>" class="def" />
		</td> 	
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>제품일련번호 :</th>
		<td>
			<input type="text" id="sAssetName" name="sAssetName" value="<%=cmRequest.getString("sAssetName")%>" class="def" />
		</td> 
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>suffix :</th>
		<td>
			<input type="text" id="sSuffix" name="sSuffix" value="<%=cmRequest.getString("sSuffix")%>" class="def" />
		</td> 	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>Tool :</th>
		<td>
			<input type="text" id="sTool" name="sTool" value="<%=cmRequest.getString("sTool")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>구매처 :</th>
		<td>
			<input type="text" id="sBuyer" name="sBuyer" value="<%=cmRequest.getString("sBuyer")%>" class="def" />
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>원재료명 :</th>
		<td>
			<input type="text" id="sResinCore" name="sResinCore" value="<%=cmRequest.getString("sResinCore")%>" class="def" />
		</td>		
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchDtGubun" name="searchDtGubun" style="min-width:80px">
				<option value="1" <%="1".equals(cmRequest.getString("searchDtGubun","1"))?"selected":""%>>생산계획일자</option>
				<option value="5" <%="5".equals(cmRequest.getString("searchDtGubun"))?"selected":""%>>등록일자</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchDtKeywordS" name="searchDtKeywordS" value="<%=cmRequest.getString("searchDtKeywordS")%>" class="datepicker dt1" />
			- <input type="text" id="searchDtKeywordE" name="searchDtKeywordE" value="<%=cmRequest.getString("searchDtKeywordE")%>" class="datepicker dt2" />
		</td>	 		
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>	