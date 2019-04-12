<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title> jQuery numberKeypad demo</title>
	<link rel="stylesheet" href="/common/css/base-min.css"/>
	<link rel="stylesheet" href="/common/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link href="/common/css/jquery.numberKeypad.css" rel="stylesheet" type="text/css" />
	<script src="/common/css/jquery-latest.min.js"></script>
	<script type="text/javascript" src="/common/js/jquery.numberKeypad.js"></script>
	<script type="text/javascript">
		$(function(){
			$('input').numberKeypad({
				wrap: '.wrap'
			});
		});
		
		function fnSave(){
			//alert(  document.getElementById('qtyinvoiced').value);
			
			var qtyinvoiced = document.getElementById('qtyinvoiced').value;
			
			var myTimer = setTimeout(function() {
				  // Timer codes
				opener.fnQtyRecallInvoiced(qtyinvoiced);  
				window.close();
				}, 30);
			
			
		}    
	</script>
</head>
<body>
	<div class="wrap">
		수량  : <input type="text" id="qtyinvoiced" name="qtyinvoiced"/>
	</div>
	<div class="buttonDiv">
		<span class="button"><input type="button" value="<spring:message code="button.save"/>" onclick="fnSave();"></span>
		<span class="button"><input type="button" value="<spring:message code="button.close"/>" onclick="javascript:window.close();"></span>
	</div>	
</body>
</html>