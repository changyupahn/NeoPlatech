<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp2100/kp2111Search.do";
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
		<th>업체명 :</th>
		<td>
			<select id="sRqstVendorCd" name="sRqstVendorCd" style="min-width:80px" onchange="fnVendorCdChange()">
			<option value="">전체</option>
			<c:import url="/code/optionVendorList.do" charEncoding="utf-8">
			<c:param name="paramCodeId" value="" />
			<c:param name="paramSltValue" value="" />
			</c:import>
			</select>
		</td>		
     <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
	    <th>ITEM :</th>
		<td>
			<select id="sRqstItemCd" name="sRqstItemCd" style="min-width:80px" onchange="fnItemCdChange()">
			<option value="">전체</option>
			<c:import url="/code/optionItemList.do" charEncoding="utf-8">
			<c:param name="paramCodeId" value="" />
			<c:param name="paramSltValue" value="" />
			</c:import>
			</select>
		</td>	
	<% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>P/No :</th>
		<td>
			<select id="sRqstPNoCd" name="sRqstPNoCd" style="min-width:80px" onchange="fnPNoCdChange()">
			<option value="">전체</option>
			<c:import url="/code/optionPNoList.do" charEncoding="utf-8">
			<c:param name="paramCodeId" value="" />
			<c:param name="paramSltValue" value="" />
			</c:import>
			</select>
		</td>	
<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>		