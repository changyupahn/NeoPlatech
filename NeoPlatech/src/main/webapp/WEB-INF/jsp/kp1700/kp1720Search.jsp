<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp1700/kp1720Search.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

CommonList invList = RequestUtil.getCommonList(request, "invList");

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
		<th>조사차수 :</th>
		<td>
			<select id="sInv" name="sInv" class="impt" style="height:22px;" onchange="fnInvChange();">
				<%
				for (int i=0; i<invList.size(); i++) {
					CommonMap inv = invList.getMap(i);
					String selectedStr ="";

					if (i==0) selectedStr ="selected";

					out.print(String.format("<option value=\"%s\" %s>%s</option>"
							, inv.getString("invYear")+"_"+inv.getString("invNo")
							, selectedStr
							, inv.getString("invYear")+"년도 "+inv.getString("invNo")+"차 "+inv.getString("invTypeName")
							));
				}
				%>
			</select>
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>자산분류 :</th>
		<td>
			<input type="text" id="sAssetTypeName" name="sAssetTypeName" value="<%=cmRequest.getString("sAssetTypeName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>품목분류 :</th>
		<td>
			<input type="text" id="sItemName" name="sItemName" value="<%=cmRequest.getString("sItemName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>자산명 :</th>
		<td>
			<input type="text" id="sAssetName" name="sAssetName" value="<%=cmRequest.getString("sAssetName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>규격 :</th>
		<td>
			<input type="text" id="sAssetSize" name="sAssetSize" value="<%=cmRequest.getString("sAssetSize")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>지역 :</th>
		<td>
			<input type="text" id="sTopDeptName" name="sTopDeptName" value="<%=cmRequest.getString("sTopDeptName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>부서(실) :</th>
		<td>
			<input type="text" id="sDeptName" name="sDeptName" value="<%=cmRequest.getString("sDeptName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>팀 :</th>
		<td>
			<input type="text" id="sPosName" name="sPosName" value="<%=cmRequest.getString("sPosName")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>자산상태 :</th>
		<td>
			<input type="text" id="sAssetStatusCd" name="sAssetStatusCd" value="<%=cmRequest.getString("sAssetStatusCd")%>" class="cd2" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>
			<select id="searchDtGubun" name="searchDtGubun" style="min-width:80px">
				<option value="1" <%="1".equals(cmRequest.getString("searchDtGubun","1"))?"selected":""%>>취득일자</option>
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
				<option value="1" <%="1".equals(cmRequest.getString("searchNumGubun","1"))?"selected":""%>>취득금액</option>
				<option value="3" <%="3".equals(cmRequest.getString("searchNumGubun"))?"selected":""%>>미상각액</option>
				<option value="5" <%="5".equals(cmRequest.getString("searchNumGubun"))?"selected":""%>>수량</option>
				<option value="7" <%="7".equals(cmRequest.getString("searchNumGubun"))?"selected":""%>>태그출력회수</option>
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
				<option value="1" <%="1".equals(cmRequest.getString("searchGubun","1"))?"selected":""%>>자산번호</option>
				<option value="12" <%="12".equals(cmRequest.getString("searchGubun"))?"selected":""%>>비고</option>				
			</select>
		</th>
		<td>
			<textarea id="searchKeyword" name="searchKeyword" style="height:16px;width:95%;padding-top:5px;overflow:hidden;" onkeyup="fnSetSearchKeyword()"><%=cmRequest.getString("searchKeyword")%></textarea>
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0; %></tr><tr><% } %>
		<th>재물조사일자 :</th>
		<td>
			<input type="text" id="sTagReadDtS" name="sTagReadDtS" value="<%=DateUtil.formatDate(cmRequest.getString("sTagReadDtS"),"-")%>" class="datepicker" style="width:80px;" />
			~ <input type="text" id="sTagReadDtE" name="sTagReadDtE" value="<%=DateUtil.formatDate(cmRequest.getString("sTagReadDtE"),"-")%>" class="datepicker" style="width:80px;" />
		</td>
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0; %></tr><tr><% } %>
		<th>재물조사여부 :</th>
		<td>
			<input type="radio" name="sTagReadYn" value="" <%="".equals(cmRequest.getString("sTagReadYn"))?"checked=\"checked\"":""%> />
			전체
			<input type="radio" name="sTagReadYn" value="Y" <%="Y".equals(cmRequest.getString("sTagReadYn"))?"checked=\"checked\"":""%> />
			완료
			<input type="radio" name="sTagReadYn" value="N" <%="N".equals(cmRequest.getString("sTagReadYn"))?"checked=\"checked\"":""%> />
			미완료
		</td>
	<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>
	</tr>
	</table>
