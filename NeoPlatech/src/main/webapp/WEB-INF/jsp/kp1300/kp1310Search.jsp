<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1300/kp1310Search.do";
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
		<!--  --><col width="100px" />
		<col width="240px" />
	</colgroup>
	<tr>
		<th>네이밍 :</th>
		<td>
			<select id="sAssetTypeName" name="sAssetTypeName" style="min-width:80px">
				<option value="" <%="".equals(cmRequest.getString("sAssetTypeName",""))?"selected":""%>>전체</option>
				<option value="AA" <%="AA".equals(cmRequest.getString("sAssetTypeName"))?"selected":""%>>총조</option>
				<option value="II" <%="II".equals(cmRequest.getString("sAssetTypeName"))?"selected":""%>>반제품</option>
				<option value="IC" <%="IC".equals(cmRequest.getString("sAssetTypeName"))?"selected":""%>>사출 CKD</option>
				<option value="AC" <%="AC".equals(cmRequest.getString("sAssetTypeName"))?"selected":""%>>조립 CKD</option>
			</select>
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>P/No :</th>
		<td>
			<input type="text" id="sTopDeptName" name="sTopDeptName" value="<%=cmRequest.getString("sTopDeptName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>모델 :</th>
		<td>
			<input type="text" id="sAssetSize" name="sAssetSize" value="<%=cmRequest.getString("sAssetSize")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>Core :</th>
		<td>
			<input type="text" id="sDeptName" name="sDeptName" value="<%=cmRequest.getString("sDeptName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>품명 :</th>
		<td>
			<input type="text" id="sItemName" name="sItemName" value="<%=cmRequest.getString("sItemName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>Resin :</th>
		<td>
			<input type="text" id="sBldngName" name="sBldngName" value="<%=cmRequest.getString("sBldngName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>제품색상 :</th>
		<td>
			<input type="text" id="sUserNo" name="sUserNo" value="<%=cmRequest.getString("sUserNo")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>대차번호 :</th>
		<td>
			<input type="text" id="sOrgName" name="sOrgName" value="<%=cmRequest.getString("sOrgName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>매핑처리LINE :</th>
		<td>
			<input type="text" id="sPosName" name="sPosName" value="<%=cmRequest.getString("sPosName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>태그이름 :</th>
		<td>
			<input type="text" id="sAssetName" name="sAssetName" value="<%=cmRequest.getString("sAssetName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>상세위치 :</th>
		<td>
			<input type="text" id="sHosil" name="sHosil" value="<%=cmRequest.getString("sHosil")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchDtGubun" name="searchDtGubun" style="min-width:80px">
				<option value="1" <%="1".equals(cmRequest.getString("searchDtGubun","1"))?"selected":""%>>태그매칭시간</option>
				<option value="5" <%="5".equals(cmRequest.getString("searchDtGubun"))?"selected":""%>>등록일자</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchDtKeywordS" name="searchDtKeywordS" value="<%=cmRequest.getString("searchDtKeywordS")%>" class="datepicker dt1" />
			- <input type="text" id="searchDtKeywordE" name="searchDtKeywordE" value="<%=cmRequest.getString("searchDtKeywordE")%>" class="datepicker dt2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchNumGubun" name="searchNumGubun" style="min-width:80px">
				<option value="5" <%="5".equals(cmRequest.getString("searchNumGubun"))?"selected":""%>>수량</option>
				<option value="8" <%="8".equals(cmRequest.getString("searchNumGubun"))?"selected":""%>>사진수</option>
			</select>
		</th>
		<td>
			<input type="text" id="searchNumKeywordS" name="searchNumKeywordS" value="<%=cmRequest.getString("searchNumKeywordS")%>" class="amt1" />
			- <input type="text" id="searchNumKeywordE" name="searchNumKeywordE" value="<%=cmRequest.getString("searchNumKeywordE")%>" class="amt2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchGubun" name="searchGubun" style="max-width:80px;">
				<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>바코드</option>
			</select>
		</th>
		<td>
			<textarea id="searchKeyword" name="searchKeyword" style="height:16px;width:95%;padding-top:5px;overflow:hidden;" onkeyup="fnSetSearchKeyword()"><%=cmRequest.getString("searchKeyword")%></textarea>
		</td>
	<% if ("1".equals(cmRequest.getString("sEtcDiv"))) { // 구분이 재물조사대상 설정이면 %>
		<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
			<th>조사대상 :</th>
			<td>
				<select id="sInvTargetYn" name="sInvTargetYn">
					<option value="" <%="".equals(cmRequest.getString("searchGubun",""))?"selected":""%>>전체</option>
					<option value="Y" <%="Y".equals(cmRequest.getString("searchGubun"))?"selected":""%>>예(Y)</option>
					<option value="N" <%="N".equals(cmRequest.getString("searchGubun"))?"selected":""%>>아니요(N)</option>
				</select>
			</td>
	<% } %>
		<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>
		</tr>
	</table>
