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
        width:500px;
        height:200px;
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
        background-position: 0px -28px;
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
  <script type="text/javascript" src="/common/js/jquery-1.4.min.js"></script>
  <script type="text/javascript" src="/common/js/jquery-ui-1.7.2.custom.min.js"></script>
  <script type="text/javascript" src="/common/js/jquery.Popup.js"></script>
  <script type="text/javascript">
  <!--

	$(document).ready(function()
	{
        var bg;
		$('td').unbind('click');
		$('b').unbind('click');

		$('td').bind('click', function(event){
			if(!event ) event = window.event;
			var target = (event.target) ? event.target : event.srcElement;
          	say(this.tagName  + '  is  Click Event   ' + target.id +	' !!!' );
		} );

		$('b').bind('click', function(event){
			if(!event ) event = window.event;
			var target = (event.target) ? event.target : event.srcElement;

			//event.preventDefault(); //이벤트의 캡쳐 기본 동작 막음
			event.stopPropagation(); //이벤트 버블링 전파를 막음
			say(this.tagName  + '  is  Click Event   ' +target.id + ' !!!' );
		} );

        //options add
		var opts = {
            'closeButton' :'#close',
            'active' : 'skyblue' ,
            'backgroundDisplay' : true
        }

		$(".popup").layerPopup("#popupLayer",opts);

        if(jQuery.ui) $("#popupLayer").draggable();

		//var text = $('#theList').text();
	});

	function say(text) {
		$('#console').append('<div>' + text + '</div>');
	}


  //-->
  </script>
 </head>

<body>

    <div id="popupLayer">
        <div id="close"></div>
        <div style="clear:both"></div>
        <div class="description">
            나는 팝업입니다.<br/> ESC 키를 누르셔도 팝업창을 닿을 실수 있습니다.
        </div>
    </div>


    * Modal Popup ...
    <br/>

    <table width='100%' align='center' border='0' cellspacing='1' cellpadding='3' bgcolor='#cccccc'>
    <tr bgcolor='#ffffff' id="trtag">
        <td height="50px" class="popup" value='1'><b class="popup"  >aaaa</b></td>
        <td class="popup" value="2" ><b class="popup" value='2009-10-2'>bbbb</b></td>
        <td class="popup" value="3" ><b class="popup" value='2009-10-3'>cccc</b></td>
        <td class="popup" value="4" ><b class="popup" value='2009-10-4'>dddd</b></td>
        <td class="popup" value="5" ><b class="popup" value='2009-10-5'>eeee</b></td>
    </tr>
    <tr bgcolor='#ffffff' id="trtag">
        <td height="50px" class="popup" ><b class="popup"  >fffff</b></td>
        <td class="popup" ><b class="popup" >ggggg</b></td>
        <td class="popup" ><b class="popup" value='2009-10-19'>hhhh</b></td>
        <td class="popup" ><b class="popup" value='2009-10-20'>iiii</b></td>
        <td class="popup" ><b class="popup" value='2009-10-21'>jjjj</b></td>
    </tr>
    </table>

    <ul id="theList">
        <li class="popup">하나</li>
        <li class="popup">둘</li>
        <li class="popup">세</li>
    </ul>
    <div id="console"></div>

 </body>
</html>
