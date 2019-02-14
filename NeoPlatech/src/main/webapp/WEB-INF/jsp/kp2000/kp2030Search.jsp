<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp2000/kp2020Search.do";
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
		<th>게이트 :</th>
		<td>
			<select id="sGateNo" name="sGateNo" class="impt" style="height:22px;">
				<option value="">전체</option>
				<option value="1">정문</option>
			</select>
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>품목명 :</th>
		<td>
			<input type="hidden" id="sItemSeq" name="sItemSeq" value="<%=cmRequest.getString("sItemSeq")%>" />
			<input type="text" id="sItemCd" name="sItemCd" value="<%=cmRequest.getString("sItemCd")%>" class="cd1" onclick="kp9010Pop();" />
			<input type="text" id="sItemName" name="sItemName" value="<%=cmRequest.getString("sItemName")%>" class="cd2" />
			<input type="hidden" id="sItemNoMulti" name="sItemNoMulti" value="" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>자산관리자 :</th>
		<td>
			<input type="text" id="sUserNo" name="sUserNo" value="<%=cmRequest.getString("sUserNo")%>" class="cd1" onclick="kp9030Pop();" />
			<input type="text" id="sUserName" name="sUserName" value="<%=cmRequest.getString("sUserName")%>" class="cd2" onchange="fnInitUserNo()" />
			<input type="hidden" id="sUserNoMulti" name="sUserNoMulti" value="" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>사용부서 :</th>
		<td>
			<input type="text" id="sDeptNo" name="sDeptNo" value="<%=cmRequest.getString("sDeptNo")%>" class="cd1" onclick="kp9020Pop();" />
			<input type="text" id="sDeptName" name="sDeptName" value="<%=cmRequest.getString("sDeptName")%>" class="cd2" onchange="fnInitDeptNo()" />
			<input type="hidden" id="sDeptNoMulti" name="sDeptNoMulti" value="" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchGubun" name="searchGubun" style="max-width:80px;">
				<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>자산번호</option>
				<option value="2" <%="2".equals(cmRequest.getString("searchGubun"))?"selected":""%>>상세명칭</option>
			</select>
		</th>
		<td>
			<textarea id="searchKeyword" name="searchKeyword" style="height:16px;width:95%;padding-top:5px;overflow:hidden;" onkeyup="fnSetSearchKeyword()"><%=cmRequest.getString("searchKeyword")%></textarea>
		</td>
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>
	</tr>
	</table>
