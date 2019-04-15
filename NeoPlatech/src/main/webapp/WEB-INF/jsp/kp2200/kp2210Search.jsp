<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String curAction = "/kp2200/kp2210Search.do";
String curComboItemAction =  "/kp2100/kp2111ComboItemAjax.do";
String curComboPNoAction =  "/kp2100/kp2111ComboPNoAjax.do";
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
<script type="text/javascript"> 
$(document).ready(function(){
	
	
	$("#searchDtKeywordS").datepicker({
		dayNamesMin : ["일","월","화","수","목","금","토"]
		, monthNames : ["년 1월","년 2월","년 3월","년 4월","년 5월","년 6월","년 7월","년 8월","년 9월","년 10월","년 11월","년 12월"]
		, monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
		//, showOn : "both"
		, duration : 200
		, dateFormat : "yy-mm-dd"
		//, buttonImage : false
		//, buttonText : '달력'
		//, buttonImageOnly : false
		, showMonthAfterYear : true
		, yearRange: "1950:+2"
		, changeYear : true
		, changeMonth : true
		, currentText : '오늘'
		, closeText : '닫기'
		, onSelect : function(dateText, inst) {
			var minDate = $(this).datepicker('getDate');
            minDate.setDate(minDate.getDate()+2); //add two days
            $("#searchDtKeywordE").datepicker( "option", "minDate", minDate);
            $("#searchDtKeywordS").val(this.value);  
           var dt =  document.getElementById('searchDtKeywordS').value;
           //$('#searchDtKeywordE').val(dt);
           $('#searchDtKeywordS').datepicker('setDate', dt); 
		}
		, showButtonPanel : true
	}).datepicker();
	
	$("#searchDtKeywordE").datepicker({
		dayNamesMin : ["일","월","화","수","목","금","토"]
		, monthNames : ["년 1월","년 2월","년 3월","년 4월","년 5월","년 6월","년 7월","년 8월","년 9월","년 10월","년 11월","년 12월"]
		, monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
		//, showOn : "both"
		, duration : 200
		, dateFormat : "yy-mm-dd"
		//, buttonImage : false
		//, buttonText : '달력'
		//, buttonImageOnly : false
		, showMonthAfterYear : true
		, yearRange: "1950:+2"
		, changeYear : true
		, changeMonth : true
		, currentText : '오늘'
		, closeText : '닫기'
		, onSelect : function(dateText, inst) {
			var maxDate = $(this).datepicker('getDate');
            maxDate.setDate(maxDate.getDate()-2);
            $("#searchDtKeywordS").datepicker( "option", "maxDate", maxDate);
            var dt =  document.getElementById('searchDtKeywordE').value;
            //$('#searchDtKeywordE').val(dt);
            $('#searchDtKeywordE').datepicker('setDate', dt); 
		}
		, showButtonPanel : true
	}).datepicker();
	
	
	$("select#sRqstVendorCd").change(function(){
	
	
		   if($("#sRqstVendorCd").val == ''){	
			   $("#sRqstItemCd option[value='']").attr("select", "true");	
			   $("#sRqstItemCd").attr("disabled", "true");	
		   }else{	
			   $("select[name=sRqstItemCd]").removeAttr("disabled");
			   fnItemCdChange($(this).val());	
		   }	
		});
	
		$("select#sRqstItemCd").change(function(){			
			   if($("#sRqstPNoCd").val == ''){
				   $("#sRqstPNoCd option[value='']").attr("select", "true");
				   $("#sRqstPNoCd").attr("disabled", "true");
			   }else{
		   
				   $("select[name=sRqstPNoCd]").removeAttr("disabled");
				   fnPNoChange($(this).val());
			   }	
			});
	   
	
 });
function updateDtKeywordS(value){
	   
    $( "#searchDtKeywordS" ).datepicker("setDate", value );
   
}
function updateDtKeywordE(value){  
	
	$( "#searchDtKeywordS" ).datepicker("setDate", value );
}

function getDateStr(myDate){
	return (myDate.getFullYear() + '-' + (myDate.getMonth() + 1) + '-' + myDate.getDate())
}

/* 오늘로부터 1개월전 날짜 반환 */
function lastMonth() {
	 var d = new Date();
	    var lastDayofLastMonth = ( new Date( d.getYear(), d.getMonth(), 0) ).getDate();
	    if(d.getDate() > lastDayofLastMonth) {
	        d.setDate(lastDayofLastMonth);
	    }
	    var month = d.getMonth() -1;
	    d.setMonth(month);
  return getDateStr(d);
}

/* 오늘 날짜를 문자열로 반환 */
function today() {
  var d = new Date();
  return getDateStr(d);
}

function fnItemCdChange(obj){
	var value = "sRqstVendorCd=" + obj;
	
	$.ajax({
		type:'POST',
		url:'<%=curComboItemAction%>',
		dataType:'JSON',
		data:  {
			sRqstVendorCd : $("select[name=sRqstVendorCd]").val() 			
		},
		success:function(data){
	
			console.log(data.LIST)
	
			if(data.LIST != null){
					
				$("select#sRqstItemCd option").remove();
				$("select#sRqstItemCd").append("<option value=''>선택</option>");				
									
				$.each(data.LIST, function(key,value){
					
					$("select#sRqstItemCd").append("<option value='" + value.code + "'>" + value.codeName+"</option>");					
	
				}); 								
	
			
			}else{
				$("select#sRqstItemCd option").remove();
				$("select#sRqstItemCd").append("<option value=''>NO ITEM!</option>");						
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
	$.ajax({
		type:'POST',
		url:'<%=curComboPNoAction%>',
		dataType:'JSON',
		data:  {
			sRqstVendorCd : $("select[name=sRqstVendorCd]").val() ,
			sRqstItemCd : $("select[name=sRqstItemCd]").val()
		},					
		success:function(data){			 
			
			console.log(data.LIST)
			
			if(data.LIST != null){
				
				$("select#sRqstPNoCd option").remove();
				$("select#sRqstPNoCd").append("<option value=''>선택</option>");														
								
				$.each(data.LIST, function(key,value){
					$("select#sRqstPNoCd").append("<option value='" + value.code + "'>" + value.codeName+"</option>");					
					
				});
				
			}else{
				$("select#sRqstPNoCd option").remove();
				$("select#sRqstPNoCd").append("<option value=''>NO PNO!</option>");									
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
			<select id="sRqstVendorCd" name="sRqstVendorCd" style="min-width:80px" onchange="fnItemCdChange(this);">
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
			<select id="sRqstItemCd" name="sRqstItemCd" style="min-width:80px" onchange="fnPNoChange(this);">
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
		<th>
			날짜: 
		</th>				
		<td>
			<input type="text" id="searchDtKeywordS" name="searchDtKeywordS" value="<%=cmRequest.getString("searchDtKeywordS")%>" class="datepicker dt1" onclick="javascript:updateDtKeywordS(lastMonth());"/>
			- <input type="text" id="searchDtKeywordE" name="searchDtKeywordE" value="<%=cmRequest.getString("searchDtKeywordE")%>" class="datepicker dt2" onclick="javascript:updateDtKeywordE(today());"/>
		</td>			    
<% idx++; if (idx % colcnt > 0) { %><td colspan="<%=(colmax * 2) - ((idx % colcnt) * 2)%>">&nbsp;</td><% } %>		
		</tr>
	</table>		