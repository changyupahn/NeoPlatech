<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<html>
<head>
	<title> Jquery PopUp </title>
	<style>
	body { height:100%; }
	/*
	popupLayer 위치
	*/
	#popupLayer
	{
		display:none;
		width:80%;
		height:80%;
		text-align:center;
		border:3px solid #DDDDDE;
		background-color:#ffffff;
		z-index:2;
	}

	#popupLayer #close {
		cursor:pointer;
		background: url(/images/btn/cee-close-btn.png) no-repeat;
		display:box;
		width:58px;
		height:28px;
		float:right;
		text-indent:-10000px;
		margin: 6px 10px 0 0;
	}
	#popupLayer #close:hover {
		background-position: 0px -18px;
	}
	#popupLayer .description {
		padding-left:5px;
		padding-right:5px;
		padding-bottom:5px;
	}

	.skyblue{
		background-color:skyblue;
	}
	</style>
	<script type="text/javascript" src="/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/common/js/jquery.Popup.js"></script>
	<script type="text/javascript">
	$(document).ready(function()
	{
		//options add
		var opts = {
			'closeButton' :'#close',
			'active' : 'skyblue' ,
			'backgroundDisplay' : true
		}

		$(".popup").layerPopup("#popupLayer",opts);
		
		if(jQuery.ui) $("#popupLayer").draggable();
		
		displayIframeLayer('layer_iframe', true);
	});
	function displayIframeLayer(layerIfrName, useForm) {
		if(useForm){
			//form을 통해 보낼 경우
			var v_frm = document.getElementsByName("frm")[0];
			v_frm.target = "iframe"; //target을 iframe으로
			v_frm.action = "/kp200/kp211.do";
			
			document.getElementById(layerIfrName).style.display = "block";
			
			v_frm.submit();
		} else {
			//form을 사용하지 않는 경우
			document.frames["iframe"].location.href = "/kp200/kp211.do";
			document.getElementById(layerIfrName).style.display = "block";
		}
	}
	</script>
</head>

<body>
	<form name="frm">
	</form>
	
	<div id="popupLayer">
		<div id="close"></div>
		<div style="clear:both"></div>
		<div class="description">
			<div id="layer_iframe" style="display:none; position:absolute; top:50px; left:0%;margin-left:0px; width:100%; height:100%;">
				<iframe name="iframe" style="width:100%; height:90%" frameborder="0" scrolling="no"></iframe>
			</div>
		</div>
	</div>

	<a href="#" class="popup">레이어 팝업 클릭</a>

</body>
</html>
