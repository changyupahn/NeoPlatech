<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1400/kp1420Search.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
int idx = 0;
int colmax = 0;
int colcnt = cmRequest.getInt("colcnt");
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
		<th>
			<select id="searchGubun" name="searchGubun" style="max-width:80px;">
				<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>자산코드</option>
				<option value="10" <%="10".equals(cmRequest.getString("searchGubun"))?"selected":""%>>MIS자산코드</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>품목명 :</th>
		<td>
			<input type="hidden" id="sItemSeq" name="sItemSeq" value="<%=cmRequest.getString("sItemSeq")%>" />
			<input type="text" id="sItemCd" name="sItemCd" value="<%=cmRequest.getString("sItemCd")%>" class="cd1" onclick="kp9010Pop();" />
			<input type="text" id="sItemName" name="sItemName" value="<%=cmRequest.getString("sItemName")%>" class="cd2" />
		</td>
	<%-- <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>상세명칭 :</th>
		<td>
			<input type="text" id="sAssetName" name="sAssetName" value="<%=cmRequest.getString("sAssetName")%>" class="def" />
		</td> --%>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>신청자 :</th>
		<td>
			<input type="text" id="sRqstUserNo" name="sRqstUserNo" value="<%=cmRequest.getString("sRqstUserNo")%>" class="cd1" onclick="kp9030Pop()" />
			<input type="text" id="sRqstUserName" name="sRqstUserName" value="<%=cmRequest.getString("sRqstUserName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>반출처 :</th>
		<td>
			<input type="text" id="sOutPlace" name="sOutPlace" value="<%=cmRequest.getString("sOutPlace")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchDtGubun" name="searchDtGubun" style="min-width:80px">
				<option value="1" <%="1".equals(cmRequest.getString("searchDtGubun","1"))?"selected":""%>>반출일자</option>
				<option value="2" <%="2".equals(cmRequest.getString("searchDtGubun"))?"selected":""%>>반입예정일자</option>
				<option value="3" <%="3".equals(cmRequest.getString("searchDtGubun"))?"selected":""%>>실반입일자</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchDtKeywordS" name="searchDtKeywordS" value="<%=cmRequest.getString("searchDtKeywordS")%>" class="datepicker dt1" />
			- <input type="text" id="searchDtKeywordE" name="searchDtKeywordE" value="<%=cmRequest.getString("searchDtKeywordE")%>" class="datepicker dt2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>신청부서 :</th>
		<td>
			<input type="text" id="sRqstDeptNo" name="sRqstDeptNo" value="<%=cmRequest.getString("sRqstDeptNo")%>" class="cd1" onclick="kp9020Pop();" />
			<input type="text" id="sRqstDeptName" name="sRqstDeptName" value="<%=cmRequest.getString("sRqstDeptName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>반출사유 :</th>
		<td>
			<input type="text" id="sReason" name="sReason" value="<%=cmRequest.getString("sReason")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>승인상태 :</th>
		<td>
			<select id="sRqstStatusCd" name="sRqstStatusCd" style="min-width:80px">
			<option value="">전체</option>
			<c:import url="/code/optionList.do" charEncoding="utf-8">
			<c:param name="paramCodeId" value="COM008" />
			<c:param name="paramSltValue" value='<%=cmRequest.getString("sRqstStatusCd")%>' />
			</c:import>
			</select>
		</td>
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>
	</tr>
	</table>
