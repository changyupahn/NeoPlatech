
/**
 * 주민등록번호 체크
 * @param s1 주민번호 앞 6자리
 * @param s2 주민번호 뒤 7자리
 * @return
 */
function fnChkSSN(s1, s2) {
	
	if( s1 == null || s2 == null )
		return false;	
	if( s1.length != 6 || s2.length != 7 )
		return false;
	
	n = 2;
	sum = 0;
	for (i=0; i<s1.length; i++)
		sum += parseInt(s1.substr(i, 1)) * n++;
	for (i=0; i<s2.length-1; i++) {
		sum += parseInt(s2.substr(i, 1)) * n++;
		if (n == 10) n = 2;
	}
	c = 11 - sum % 11;
	if (c == 11) c = 1;
	if (c == 10) c = 0;
	if (c != parseInt(s2.substr(6, 1))) return false;
	else return true;
}

/**
 * 외국인등록번호 체크
 * @param s1 외국인번호 앞 6자리
 * @param s2 외국인번호 뒤 7자리
 * @return
 */
function fnChkFRN(s1, s2) { 
   var sum = 0; 
   var odd = 0; 
   buf = new Array(13);
   
   if( s1 == null || s2 == null )
	   return false;	
   if( s1.length != 6 || s2.length != 7 )
	   return false;
   
   var fgnno = "" + s1 + s2;

   for(i=0; i<13; i++) buf[i] = parseInt(fgnno.charAt(i)); 

   odd = buf[7]*10 + buf[8]; 

   if(odd%2 != 0) return false; 

   if((buf[11]!=6) && (buf[11]!=7) && (buf[11]!=8) && (buf[11]!=9)) return false; 

   multipliers = [2,3,4,5,6,7,8,9,2,3,4,5]; 

   for(i=0, sum=0; i<12; i++) sum += (buf[i] *= multipliers[i]); 

   sum = 11 - (sum%11); 

   if(sum >= 10) sum -= 10; 

   sum += 2; 
   if(sum >= 10) sum -= 10; 

   if(sum != buf[12]) return false;

   return true; 
}

/**
 * 법인등록번호 체크
 * @param s1 법인번호 앞 6자리
 * @param s2 법인번호 뒤 7자리
 * @return
 */
function fnChkCRN(s1, s2) {
	/***  법인번호 예시
	110111    1130494   1712110008647   1156110020253   1101110008262
	1101360027690   1101110196207   1201110010174   1101110303183
	1101111379464   1201110003822   1101110343014   1201110001371
	1101110030819   1901110002317
	*/
	var err = 0;
	
	if( s1 == null || s2 == null )
	   return false;	
   if( s1.length != 6 || s2.length != 7 )
	   return false;
	
	var objchar = s1;
	var objchar2 = s2;

	for(CB_i=0;CB_i<objchar2.value.length;CB_i++){
		var bubinnum=objchar2.value.charAt(CB_i);
		if (bubinnum < '0' || bubinnum > '9'){			
			return false;
		}
	}

	if(objchar2.value) {
		 if(objchar2.value.length == 7) {

			var fullbubin = objchar.value + objchar2.value;
			var hap = 0;
			var j = 0;

			for (CB_ii=0; CB_ii<12;CB_ii++){
				if(j < 1 || j > 2){ j=1; }
				hap = hap + (parseInt(fullbubin.charAt(CB_ii)) * j);
				j++;
			}
			
			if ((10 - (hap%10))%10 != parseInt(fullbubin.charAt(12))){
				err=1;
			}

			if (err == 1){
				return false;
			}
		}  
	}
	return true;
}

/**
 * 사업자등록번호 체크
 * @param strNumb
 * @return
 */
function fnChkCPN(strNumb){   
    strNumb = strNumb.replace(/\D/gi, "");
    if (strNumb.length != 10) {   
        return false;   
    }  
  
    sumMod = 0;   
    sumMod += parseInt(strNumb.substring(0,1));   
    sumMod += parseInt(strNumb.substring(1,2)) * 3 % 10;   
    sumMod += parseInt(strNumb.substring(2,3)) * 7 % 10;   
    sumMod += parseInt(strNumb.substring(3,4)) * 1 % 10;   
    sumMod += parseInt(strNumb.substring(4,5)) * 3 % 10;   
    sumMod += parseInt(strNumb.substring(5,6)) * 7 % 10;   
    sumMod += parseInt(strNumb.substring(6,7)) * 1 % 10;   
    sumMod += parseInt(strNumb.substring(7,8)) * 3 % 10;   
    sumMod += Math.floor(parseInt(strNumb.substring(8,9)) * 5 / 10);   
    sumMod += parseInt(strNumb.substring(8,9)) * 5 % 10;   
    sumMod += parseInt(strNumb.substring(9,10));   
  
    if (sumMod % 10 != 0) {   
        return false;   
    }   
    return true;   
}

/**
 * 전화번호체크
 * @param n1 전화번호 앞 3자리
 * @param n2 전화번호 중간 3-4자리
 * @param n3 전화번호 끝 4자리
 * @return
 */
function fnChkTEL(n1, n2, n3){
	var regexTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
	var strNumb = "" + n1 + "-" + n2 + "-" + n3;
	return regexTel.test(strNumb);
}

/**
 * 휴대폰번호체크
 * @param n1 휴대폰번호 앞 3자리
 * @param n2 휴대폰번호 중간 3-4자리
 * @param n3 휴대폰번호 끝 4자리
 * @return
 */
function fnChkMOB(n1, n2, n3){
	var regexMob = /^\d{3}-\d{3,4}-\d{4}$/;
	var strNumb = "" + n1 + "-" + n2 + "-" + n3;
	return regexMob.test(strNumb);
}

/**
 * 쿠키 저장
 * @param name 쿠키명
 * @param value 쿠키값
 * @param expiredays 유효일( 일단위 )
 * @return
 */
function fnSetCookie( name, value, expiredays ) {
    var todayDate = new Date();
    todayDate.setDate( todayDate.getDate() + expiredays );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

/**
 * 쿠키 불러오기
 * @param name 쿠키명
 * @return
 */
function fnGetCookie( name ) {
    var nameOfCookie = name + "=";
    var x = 0;
    while ( x <= document.cookie.length ) {
        var y = (x+nameOfCookie.length);
        if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                endOfCookie = document.cookie.length;

            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;

        if ( x == 0 )
        break;
    }

    return "";
}

/**
 * 윈도우 새창 호출
 * @param url 팝업창 URL
 * @param popupName 팝업창 이름
 * @param width 팝업창 가로 길이
 * @param height 팝업창 세로 길이
 * @return
 */
function fnOpenPop(url, popupName, width, height, feature){
	
	var winX = window.screenX || window.screenLeft || 0;// 현재창의 x좌표
	var winY = window.screenY || window.screenTop || 0;	// 현재창의 y좌표
	var winWidth = $(document).width();	// 현재창의 너비
	var winHeight = $(document).height();	// 현재창의 높이
	
	var oLeft = winX + (winWidth - width) / 2;
	var oTop = winY + (winHeight - height) / 2;
	
	if(feature == "") {
		feature = "menubar=no,status=no,titlebar=no,scrollbars=no,location=no,toolbar=no";
	}

	return window.open(url, popupName, "width="+width+",height="+height+","+"left="+oLeft+",top="+oTop+","+feature);
}
function fnOpenPop2(url, popupName){
	var winX = window.screenX || window.screenLeft || 0;// 현재창의 x좌표
	var winY = window.screenY || window.screenTop || 0;	// 현재창의 y좌표
	var oLeft = 50;
	var oTop = 50;
	var oWidth = $(document).width() - (oLeft * 2);
	var oHeight = $(document).height() - (oTop * 2);
	oLeft = oLeft + winX;
	oTop = oTop + winY;
	
	var feature = "width="+ oWidth +",height="+ oHeight +","+"left="+ oLeft +",top="+ oTop +",menubar=no,status=no,titlebar=no,scrollbars=yes,location=no,toolbar=no,resizable=yes";
	
	return window.open(url, popupName, feature);
}

function ShowPopup(url, width, height) {
    var first_Index = window.navigator.appVersion.indexOf("(");
    var next_Index = window.navigator.appVersion.lastIndexOf("(");
    if (first_Index == next_Index) {
        var version = window.navigator.appVersion;
    } else {
        var version = window.navigator.appVersion.substring(0, next_Index);
    }
    if (version.indexOf("MSIE 6.0") > 0) {
        width += 6;
        height += 46;
    }
    return window.showModalDialog(url, self, "dialogwidth:" + width + "px;dialogheight:" + height + "px;center:yes;help:no;resizable:no;scroll:yes;status:no;");
}

function ShowPopupReport(url) {
    var width = window.screen.width > 1024 ? 1024 : 800;
    var height = window.screen.height > 800 ? 768 : 600;
    var feature = "width=" + width + ",height=" + height + ",toolbar=no,directories=no,menubar=no,resizable=yes,scrollbars=no,status=no";
    
    window.open(url, 'self', feature, false).focus();
}

/**
 * 천단위 콤마 찍기
 * @param n
 * @return
 */
function fnSetComma(n) {
  var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
  n += '';                          // 숫자를 문자열로 변환

  while (reg.test(n))
    n = n.replace(reg, '$1' + ',' + '$2');

  return n;
}

/**
 * 천단위 콤마 제거
 * @param n
 * @return
 */
function fnRemoveComma(n) {
  return parseInt(n.replace(/,/g, ""));
}

/**
 * 문자열의 바이트 길이 계산
 * @param str 계산할 문자열
 * @return
 */
function fnGetByteLength(str){
	var st_len = 0;
	for(var i=0;i<obj.value.length;i++){
		es_len = escape(obj.value.charAt(i));
		if ( es_len.length == 1 ) st_len ++;
		else if ( es_len.indexOf("%u") != -1 ) st_len += 2;
		else if ( es_len.indexOf("%") != -1 ) st_len += es_len.length/3;
	}	
	return st_len;
}

/**
 * SMS 80byte 체크
 * @param obj Value값을 조회할 Object
 * @param innerId 결과값을 세팅할 Object ID
 * @return
 */
function fnChkSms(obj,innerId){
	innerObj = document.getElementById(innerId);
	var commentLenCheckTmpStr = "";
	if(innerObj!=null){ 
		var st_len = 0;
		if( obj.value.length == 0 ){
			innerObj.innerHTML = st_len + ' / 80 bytes (한글40자)';
		} else {
			for(var i=0;i<obj.value.length;i++){
				es_len = escape(obj.value.charAt(i));
				if ( es_len.length == 1 ) st_len ++;
				else if ( es_len.indexOf("%u") != -1 ) st_len += 2;
				else if ( es_len.indexOf("%") != -1 ) st_len += es_len.length/3;  
					
				if(st_len > 80){
					alert("최대 한글40자, 영문80 이내까지 입력 가능합니다.");
					obj.value = commentLenCheckTmpStr;
					break;
				}else{
					result = true;
					commentLenCheckTmpStr = commentLenCheckTmpStr + obj.value.charAt(i);
					innerObj.innerHTML = st_len + ' / 80 bytes (한글40자)';
				}
			}
		}
	}else{
		alert("페이지 구성에 실패했습니다.");
	}
}

/**
 * 숫자값만 입력
 * @param
 * @return
 */
function fnOnlyNumber() {
	if(
		(window.event.keyCode == 8) ||	//backspce
		(window.event.keyCode == 9) ||	//tab
		(window.event.keyCode == 35) || //end
		(window.event.keyCode == 36) || //home
		(window.event.keyCode == 46) || //del
		(window.event.keyCode >= 37 && window.event.keyCode <= 40) || //direction
		(window.event.keyCode >= 48 && window.event.keyCode <= 57) ||
		(window.event.keyCode >= 96 && window.event.keyCode <= 105)
	){
		window.event.returnValue=true;
	} else {
		window.event.returnValue=false;
	}
}

/**
 * 즐겨찾기 저장
 * @param url 즐겨찾기 저장할 URL
 * @param name 즐겨찾기 명칭
 * @return
 */
function fnAddFavorite(url, name){
	window.external.addfavorite(url, name);
}

function sleep(milliseconds) {
	var start = new Date().getTime();
	for (var i = 0; i < 1e7; i++) {
		if ((new Date().getTime() - start) > milliseconds){
			break;
		}
	}
}

//팝업창인지 체크하여 팝업창일시 사이즈조절. 팝업창을 가운데로 위치시킴.
function isPopChk() {
	if(opener != undefined) {
		var popWidth = document.body.scrollWidth + 10;
		var popHeight = document.body.scrollHeight + 35;

		self.moveTo(screen.width/2-(popWidth/2),screen.height/2-(popHeight/2));
		self.resizeTo(popWidth , popHeight);
	}
}

//로그아웃
function uf_logout(gubun) {
	if(gubun == 'timeout'){		// 세션타임아웃
		if(opener != null && opener != undefined) {	
			self.close();
		}
		else {
			try{  		
				var isIfrm = false;
				if(parent && parent.location.href+'' != location.href+''){
					var ifrms = parent.document.all.tags('IFRAME')
					if(ifrms.length > 0)
						isIfrm = true;
				}
				
				if(!isIfrm){
					hideIfr();
					
					var src = "/ibs/jsp/common/timeout.jsp";
					// 메신저뱅킹
					var url = location.href+'';
					if(url.indexOf('baromsg') > -1){
						src = "/ibs/baromsg/common/msgr_timeout.jsp";
					}

					document.body.style.overflow='hidden'
					
					var common_flash_txt = '<iframe name="ifrm_logout" src="' + src + '" width="' +document.body.clientWidth + '" height="' + document.body.clientHeight + '" border="0" frameBorder="0" frameSpacing="0" style="display:block;POSITION:absolute;">';
					common_flash_txt += '</iframe>';
					
					var obj = document.getElementById("div_logout");
				
					obj.innerHTML = common_flash_txt;
					obj.style.width = document.body.clientWidth;
					obj.style.height = document.body.clientHeight;
					obj.style.left = 0;
					obj.style.top = document.body.scrollTop;
					
					obj.style.visibility = "visible";
				}
			}catch(e){}
		}
	}else{		// 사이트를 이동할경우
		try{
			var sizeW = 400;
			var sizeH = 310;
			var nLeft  = screen.width/2 - sizeW/2 ;
			var nTop  = screen.height/2 - sizeH/2 ;
			window.open('/ibs/jsp/login/pop_info_logout.jsp', 'logoutWin', "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + ",toolbar=no,menubar=no,location=no,scrollbars=no,status=no" );
			//XecureNavigate('/ibs/jsp/login/pop_info_logout.jsp', 'logoutWin');
		}catch(e){}
	}
}

function fnFormatterDate(cellvalue, options, rowObject)
{
	var new_format_value = "";
	try {
		if (cellvalue) {
			if (cellvalue.length == 8)
				new_format_value = cellvalue.replace(/^(\d{4})(\d{2})(\d{2})$/gi, "$1-$2-$3");
			else if (cellvalue.length == 14)
				new_format_value = cellvalue.replace(/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/gi, "$1-$2-$3 $4:$5");
			else
				new_format_value = cellvalue;
		}
	} catch (e) {
		new_format_value = cellvalue;
	}
	return new_format_value
}