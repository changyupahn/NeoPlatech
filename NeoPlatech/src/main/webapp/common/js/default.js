
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function fnSerializeObject() {
	var saveJsonObj = {};

	$('input[type="hidden"]').each(function() {
		saveJsonObj[$(this).attr("id")] = $(this).val();
	});

	$('input[type="text"]').each(function() {
		saveJsonObj[$(this).attr("id")] = $(this).val();
	});

	$('select').each(function() {
		saveJsonObj[$(this).attr("id")] = $(this).val();
	});

	$('textarea').each(function() {
		saveJsonObj[$(this).attr("id")] = $(this).val();
	});

	return saveJsonObj;
}

function fnLoadingS(msg1, statusUrl) {
	var oImgW = 317;
	var oImgH = 95;
	var oLeft = ($(window).width() - oImgW) / 2;
	var oTop = ($(window).height() - oImgH) / 2;

	var msgLine1 = "<p id=\"loading_p1\">처리 중 입니다.</p>";
	var msgLine2 = "";

	if (msg1) {
		msgLine1 = "<p id=\"loading_p1\">" + msg1 + "</p>";
	}

	if (statusUrl) {
		msgLine2 = "<p id=\"loading_p2\">0 / 0</p>";
	}

	$.blockUI({message:'<div class="loading">'+ msgLine1 + ' ' + msgLine2 +'<img id="displayBox" src="/images/contents/loading.gif" border="0"/></div>', css:{width:'333', top:oTop, left:oLeft}, fadeIn:0, fadeOut:40});

	sw_loading = true;

	setTimeout(function(){ fnLoadingStatus(statusUrl); }, 2000);
}

function fnLoadingStatus(statusUrl) {
	if (sw_loading == true) {
		$.ajax({
			type : "POST",
			url : statusUrl,
			dataType : "json",
			success:function(data)
			{
				if (data.result == "OK") {

					$('#loading_p2').html(data.status);

					setTimeout(function(){ fnLoadingStatus(statusUrl); }, 3000);
				} else {
					fnLoadingE();
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				sw_loading = false;
			},
			complete:function()
			{
			}
		});
	}
}

function fnLoadingE(statusCloseUrl) {
	sw_loading = false;

	if (statusCloseUrl) {
		$.ajax({
			type : "POST",
			url : statusCloseUrl,
			dataType : "json",
			success:function(data)
			{
				if (data.result == "OK") {
					$('#loading_p2').html(data.status);

					setTimeout(function(){ fnLoadingStatus(url); }, 3000);
				}
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				sw_loading = false;
			},
			complete:function()
			{
			}
		});
	}

	$.unblockUI();
}

function fnLoadingS2() {
	var oImgW = 317;
	var oImgH = 74;
	var oLeft = ($(window).width() - oImgW) / 2;
	var oTop = ($(window).height() - oImgH) / 2;

	$.blockUI({message:'<div class="loading"><img id="displayBox" src="/images/contents/loading2.gif" border="0"/></div>', css:{width:'360', top:oTop, left:oLeft}, fadeIn:0, fadeOut:40});
}

function fnLoadingE2() {
	$.unblockUI();
}

function fnGridInvalidSession(totalRow) {
	if (totalRow == -1) {
		alert("로그인 세션이 만료되었습니다. 다시 로그인 해주세요.");
	}
}

/* jQgrid 해당 컬럼에 동일한 값이 이어지면 rowspan 처리
 * gridName : jqgrid name
 * colName : colModel에서 정의한 name
 */
function jsGridRowspanCell(gridName, colName)
{
	var tmp = "####NNNN";
	var targetObj;
	var rowcnt = 1;
	var loopcnt = 0;
	var sw = false;
	var totalcnt = $('td[aria-describedby="'+ gridName +'_'+ colName +'"]').length;
	$('td[aria-describedby="'+ gridName +'_'+ colName +'"]').each(function(){

		if ($(this).attr("title") != tmp) {
			tmp = $(this).attr("title");

			if (loopcnt > 0) {
				targetObj.attr("rowspan", rowcnt);
				targetObj.css("vertical-align", "top");
				targetObj.css("padding-top", "5px");
				targetObj.css("padding-left", "5px");
				sw = false;
			}

			targetObj = $(this);
			rowcnt = 1;
			sw = true;

		} else {
			rowcnt++;
			$(this).hide();
		}

		loopcnt++;

		if (sw == true && loopcnt == totalcnt) {
			targetObj.attr("rowspan", rowcnt);
			targetObj.css("vertical-align", "top");
			targetObj.css("padding-top", "5px");
			targetObj.css("padding-left", "5px");
			sw = false;
		}

	});
}

/* jQgrid 해당 컬럼에 동일한 값이 이어지면 감춤처리 처리
* gridName : jqgrid name
* colName : colModel에서 정의한 name
*/
function jsGridHideCell(gridName, colName)
{
	var tmp = "####NNNN";
	$('td[aria-describedby="'+ gridName +'_'+ colName +'"]').each(function(){

		if ($(this).attr("title") != tmp) {
			tmp = $(this).attr("title");
		} else {
			$(this).html("");
		}
	});
}

/* jQgrid rowspan cell 값에 상관없이 3줄 단위로 rowspan
 * rowspan 설정 cell안에 -> cellattr:jsFormatterCell3Rowspan 설정
*/
function jsFormatterCell3Rowspan(rowid, val, rowObject, cm, rdata)
{
	var result;
	//현재 row를 3으로 나눈 나머지값을 구함
	var groupNo = rowid % 3;//rowObject.sortNum;
	if(chkcell.cellId != "undefined")
	{
		if(groupNo == "1")
		{
			//rowspan 준비
			var cellId = this.id + '_row_' + rowid + '-' + cm.name;
			result = ' rowspan = "1" id="' + cellId + '"';
		}
		else if(groupNo == "2" || groupNo == "0")
		{
			//display none 처리
			result = ' style="display:none" rowspanid="' + chkcell.cellId + '"';
		}
		else
		{
			result = ' rowspan = "1" id="rowsapnTrgt"';
		}
	}
	return result;
}

function fnSetSearchKeyword() {
	$('#searchKeyword').bind('keydown', function(e) {
		if (e.keyCode === 13) {
			fnSearch();
			e.preventDefault();
		}
	});
}