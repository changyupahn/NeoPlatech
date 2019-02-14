<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE> iframe으로 layer popup 구성하기 </TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">

<SCRIPT LANGUAGE="JavaScript">
<!--
 function displayIframeLayer(layerIfrName, useForm) 
 {
  if(useForm){
   //form을 통해 보낼 경우
   var v_frm = document.getElementsByName("frm")[0];
   v_frm.target = "ifr_form"; //target을 iframe으로
   v_frm.action = "/kp200/kp211.do";

   document.getElementById(layerIfrName).style.display = "block";

   v_frm.submit();
  }//아래로
  else{
   //form을 사용하지 않는 경우
   document.frames["ifr"].location.href = "/kp200/kp211.do";
   document.getElementById(layerIfrName).style.display = "block";
  }
 }
//-->
</SCRIPT>
</HEAD>

<BODY>

<form name="frm">
</form>

<input type="button" value="iframe layer 팝업" onClick="displayIframeLayer('layer_ifr', false);"/>
<!-- iframe - S -->
<div id="layer_ifr" style="display:none; position:absolute; top:111px; left:50%;margin-left:-200px; width:400px; height:284px;">
 <iframe name="ifr" style="width:100%; height:100%" frameborder="1" scrolling="no"></iframe>
</div>
<!-- iframe - E -->

<input type="button" value="iframe layer 팝업 form 사용하기" onClick="displayIframeLayer('layer_ifr_form', true);"/>
<!-- iframe - S -->
<div id="layer_ifr_form" style="display:none; position:absolute; top:111px; left:50%;margin-left:-200px; width:400px; height:284px;">
 <iframe name="ifr_form" style="width:100%; height:100%" frameborder="1" scrolling="no"></iframe>
</div>
<!-- iframe - E -->
</BODY>
</HTML>


