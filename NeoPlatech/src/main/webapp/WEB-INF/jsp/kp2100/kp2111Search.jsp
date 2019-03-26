<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp2100/kp2111.do";
String curComboItemAction =  "/kp2100/kp2111ComboItemAjax.do";
String curComboPNoAction =  "/kp2100/kp2111ComboPNoAjax.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
int idx = 0;
int colmax = 0;
int colcnt = 5 ; //cmRequest.getInt("colcnt");

if (!ssAuthManager) {
	cmRequest.put("sUserNo", cmRequest.getString("sUserNo", SessionUtil.getString("userNo")));
	cmRequest.put("sUserName", cmRequest.getString("sUserName", SessionUtil.getString("userName")));
	cmRequest.put("sDeptNo", cmRequest.getString("sDeptNo", SessionUtil.getString("deptNo")));
	cmRequest.put("sDeptName", cmRequest.getString("sDeptName", SessionUtil.getString("deptName")));
}
%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>
<%@ include file="/WEB-INF/jsp/common/jqGrid.jsp" %>
<script type="text/javascript"> 
$(document).ready(function(){
    alert("  aaaa ");
	$("#sRqstVendorCd").change(function(){
		alert("  bbb ");
		   if($("#sRqstVendorCd").val == ''){
			   $("#sRqstItemCd option[value='']").attr("select", "true");
			   $("#sRqstItemCd").attr("disabled", "true");
		  	   
		   }else{
			   alert(" cccc ");
			   $("select[name=sRqstItemCd]").removeAttr("disabled");
			   fnItemCdChange($(this).val());
	    
		   }	
		});
	alert(" ddd ");
		$("#sRqstItemCd").change(function(){
			alert(" eee");
			   if($("#sRqstPNoCd").val == ''){
				   $("#sRqstPNoCd option[value='']").attr("select", "true");
				   $("#sRqstPNoCd").attr("disabled", "true");
			   }else{
				   alert(" fff");	   
				   $("select[name=sRqstPNoCd]").removeAttr("disabled");
				   fnPNoChange($(this).val());
			   }	
			});
	
 });


function fnItemCdChange(obj){
	var value = "sRqstVendorCd=" + obj;
	alert("2222");
	$.ajax({
		type:'POST',
		url:'<%=curComboItemAction%>',
		dataType:'JSON',
		data:  {
			sRqstVendorCd : $("select[name=sRqstVendorCd]").val() 			
		},
		success:function(data){
			alert("333" + data.LIST);
			if(data.LIST.length > 0){
				$("#sRqstItemCd").find("option").remove().end().append("<option value=''>선택</option>");	
								
				$.each(data.LIST, function(key,value){
					alert("4444" + value.code);
					alert("55555" + value.codeName);
					$("#sRqstItemCd").append("<option vlaue=" + value.code + "'>" + value.codeName+ "<option>");					
				}); 								
	
			
			}else{
				$("#sRqstItemCd").find("option").remove().end().append("<option value=''>NO ITEM!</option>");
			
				return;
			}			
		},
		error:function(request, status, error){
			alert("에러발생");
		}
	});
	
}

function fnPNoChange(obj){
	
	var value = "sRqstPNoCd=" + obj;
	alert("111" + " : " + $("select[name=sRqstVendorCd]").val() );
	alert("222" + " : " + $("select[name=sRqstItemCd]").val());
	$.ajax({
		type:'POST',
		url:'<%=curComboPNoAction%>',
		dataType:'JSON',
		data:  {
			sRqstVendorCd : $("select[name=sRqstVendorCd]").val() ,
			sRqstItemCd : $("select[name=sRqstItemCd]").val()
		},					
		success:function(data){
			console.log(data); 
			alert(data.LIST.length );
			if(data.LIST.length > 0){
				$("#sRqstPNoCd").find("option").remove().end().append("<option value=''>선택</option>");
				alert("333");				
								
				$.each(data.LIST, function(key,value){
					alert("4444" + value.code);
					alert("55555" + value.codeName);
					$("#sRqstPNoCd").append("<option vlaue=" + value.code + "'>" + value.codeName+ "<option>");
					
				});
				alert("333");
			}else{
				$("#sRqstPNoCd").find("option").remove().end().append("<option value=''>NO PNO!</option>");
				alert("444");
				return;
			}			
		},
		error:function(request, status, error){
			alert("에러발생");
		}
	});
	
}



</script>
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
			<select id="sRqstVendorCd" name="sRqstVendorCd" style="min-width:80px" >
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
			<select id="sRqstItemCd" name="sRqstItemCd" style="min-width:80px" >
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
			<select id="sRqstPNoCd" name="sRqstPNoCd" style="min-width:80px" >
			<option value="">전체</option>
			<c:import url="/code/optionPNoList.do" charEncoding="utf-8">
			<c:param name="paramCodeId" value="" />
			<c:param name="paramSltValue" value="" />
			</c:import>
			</select>
		</td>		
	 <% idx++; if (idx % colcnt == 0) { colmax = idx; idx = 0;%></tr><tr><% } %>
		<th>입고량 :</th>
		<td>
			<input type="text" id="sReceiptCnt" name="sReceiptCnt" value="<%=cmRequest.getString("sReceiptCnt")%>" class="def" />
		</td>		    
<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>		