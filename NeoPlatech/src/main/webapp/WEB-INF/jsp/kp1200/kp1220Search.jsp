<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1200/kp1220Search.do";
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
		<th>계약구분 :</th>
		<td>
			<select id="sContrdiv" name="sContrdiv" style="min-width:80px">
			<option value="">전체</option>
			<option value="내자" <%="내자".equals(cmRequest.getString("sPurnm"))?"selected=\"selected\"":""%>>내자/외자</option>
			<option value="제작" <%="제작".equals(cmRequest.getString("sPurnm"))?"selected=\"selected\"":""%>>제작</option>
			</select>
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>계약번호 :</th>
		<td>
			<input type="text" id="sContrno" name="sContrno" value="<%=cmRequest.getString("sContrno")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>계약명 :</th>
		<td>
			<input type="text" id="sPurnm" name="sPurnm" value="<%=cmRequest.getString("sPurnm")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchDtGubun" name="searchDtGubun" style="min-width:80px">
				<option value="1" <%="1".equals(cmRequest.getString("searchDtGubun","1"))?"selected":""%>>계약일자</option>
				<option value="2" <%="2".equals(cmRequest.getString("searchDtGubun"))?"selected":""%>>검수일자</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchDtKeywordS" name="searchDtKeywordS" value="<%=cmRequest.getString("searchDtKeywordS")%>" class="datepicker dt1" />
			- <input type="text" id="searchDtKeywordE" name="searchDtKeywordE" value="<%=cmRequest.getString("searchDtKeywordE")%>" class="datepicker dt2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>계약금액 :</th>
		<td>
			<input type="text" id="sContramtS" name="sContramtS" value="<%=cmRequest.getString("sContramtS")%>" class="amt1" />
			- <input type="text" id="sContramtE" name="sContramtE" value="<%=cmRequest.getString("sContramtE")%>" class="amt2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>거래처 :</th>
		<td>
			<input type="text" id="sCompCd" name="sCompCd" value="<%=cmRequest.getString("sCompCd")%>" class="cd1" onclick="" />
			<input type="text" id="sCompName" name="sCompName" value="<%=cmRequest.getString("sCompName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>구매신청자 :</th>
		<td>
			<input type="text" id="sUserNo" name="sUserNo" value="<%=cmRequest.getString("sUserNo")%>" class="cd1" />
			<input type="text" id="sUserName" name="sUserName" value="<%=cmRequest.getString("sUserName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>가자산코드 :</th>
		<td>
			<input type="text" id="sVirtAssetNo" name="sVirtAssetNo" value="<%=cmRequest.getString("sVirtAssetNo")%>" class="def" />
		</td>
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>
	</tr>
	</table>
