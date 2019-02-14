/**
 * Copyright (c) 2013 kisystems, Inc.
 * All right reserved
 *
 * Revision History
 * Author           Date             Description
 * --------------   --------------   ------------------
 * kicompany        2014. 01. 21.    신규생성
 */

$(function() {
	try { api; }
	catch(e) { alert('API 스크립트를 호출할 수 없습니다.\n\n1. API KEY가 올바르게 등록되었는지 확인바랍니다.\n\n2. API URL이 올바르게 작성되었는지 확인바랍니다.\n\n3. API 사용권한이 정상적으로 처리되었는지 확인바랍니다.'); }
});

Handlebars.registerHelper('if_equal', function(variable, value, opts) {
	if(variable == value) return opts.fn(this);
	else return opts.inverse(this);
});

Handlebars.registerHelper('formatDate', function(date) {
	if(date != null && date != ''){
		var day = new Date(date.time);
		var dayWrapper = moment(day);
		var dayString = dayWrapper.format("YYYY-MM-DD HH:mm");
		return dayString;
	}
});

Handlebars.registerHelper('formatDt', function(date) {
	if(date != null && date != ''){
		var day = new Date(date);
		var dayWrapper = moment(day);
		var dayString = dayWrapper.format("YYYY-MM-DD HH:mm");
		return dayString;
	}
});

Handlebars.registerHelper('formatDay', function(date) {
	if(date != null && date != ''){
		var day = new Date(date.time);
		var dayWrapper = moment(day);
		var dayString = dayWrapper.format("YYYY-MM-DD");
		return dayString;
	}
});

Handlebars.registerHelper('formatHour', function(date) {
	if(date != null && date != ''){
		var day = new Date(date.time);
		var dayWrapper = moment(day);
		var dayString = dayWrapper.format("HH");
		return dayString;
	}
});

Handlebars.registerHelper('formatMin', function(date) {
	if(date != null && date != ''){
		var day = new Date(date.time);
		var dayWrapper = moment(day);
		var dayString = dayWrapper.format("mm");
		return dayString;
	}
});

Handlebars.registerHelper('select', function( value, options ){
	var $el = $('<select />').html( options.fn(this) );
	$el.find('[value=' + value + ']').attr({'selected':'selected'});
	return $el.html();
});

String.prototype.startsWith = function(str){
	if (this.length < str.length) { return false; }
	return this.indexOf(str) == 0;
}

String.prototype.endsWith = function(str){
	if (this.length < str.length) { return false; }
	return this.lastIndexOf(str) + str.length == this.length;
}

/**
 * Array 객체에 'jsonArrayToString' 메소드 추가
 * JSON 객체 배열을 문자열로 변환 (홑따옴표와 쌍따옴표 사용시 주의)
 */
Array.prototype.jsonArrayToString = function() {
	var str = '';
	$(this.fields).each(function(idx) {
		str += (idx == 0 ? '' : ',') + '{"field": "' + this.field + '", "req": ' + this.req + '}'
	});
	return '[' + str + ']';
};

var rndYCloneHtml = "";

var progress = new function() {
	var interval = null;
	var $modal = $(
			'<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">' +
				'<div class="modal-dialog modal-sm">' +
					'<div class="modal-content">' +
						'<div class="progress progress-striped active" style="margin: 20px;">' +
							'<div id="progress-bar" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>'
	);

	this.start = function() {
		$modal.modal('toggle');
		var width = 0;
		interval = setInterval(function() {
			$('#progress-bar').css('width', ++width + '%').html(width + '%');
			if(width >= 100) clearInterval(interval);
		}, 50);
	};

	this.stop = function() {
		clearInterval(interval);
		$modal.modal('toggle');
	};
};

var util = new function() {

	//~ Instance fields =============================================================================================

	/**
	 * 페이지네비게이션 템플릿 렌더링
	 * @param naviAction 페이지 버튼 클릭시 실행할 메소드
	 * @param jsonData 검색결과 JSON Object
	 * @param jsonParam 파라메터 JSON Object
	 */
	var renderNavi = function(naviAction, jsonData, jsonParam) {
		$navi = $('<div class="btn-group btn-group-xs col-sm-offset-3 col-sm-8" style="margin-top:10px;">');
		if(jsonData.pageCount > 1) {
			if(jsonData.firstLinkedPage > 1) {
				var prevBtnTmp = '<button type="button" class="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span></button>';
				$navi.append(renderNaviPageBtn(prevBtnTmp, naviAction, jsonParam, jsonData.firstLinkedPage - 1));
			}

			var pageBtnTmp = '<button type="button" class="btn btn-default">{{page}}</button>';
			for(var i = jsonData.firstLinkedPage; i <= jsonData.lastLinkedPage; i++) {
				$btn = renderNaviPageBtn(pageBtnTmp, naviAction, jsonParam, i);
				if(jsonData.page == i) $btn.addClass('active');
				$navi.append($btn);
			}

			if(jsonData.lastLinkedPage < jsonData.pageCount) {
				var nextBtnTmp = '<button type="button" class="btn btn-default"><span class="glyphicon glyphicon-chevron-right"></span></button>';
				$navi.append(renderNaviPageBtn(nextBtnTmp, naviAction, jsonParam, jsonData.lastLinkedPage + 1));
			}
		}
		else if(jsonData.total == 0) {
			$navi.append('검색결과가 없습니다.');
		}

		return $navi;
	};

	/**
	 * 페이지네비게이션 버튼 템플릿 렌더링
	 * @param pageBtnTmp 렌더링할 버튼 템플릿
	 * @param naviAction 페이지 버튼 클릭시 실행할 메소드
	 * @param jsonParam 파라메터 JSON Object
	 * @param page 버튼 클릭시 이동할 페이지
	 */
	var renderNaviPageBtn = function(pageBtnTmp, naviAction, jsonParam, page) {
		var template = Handlebars.compile(pageBtnTmp);
		var $btn = $(template({page: page}));
		$btn.on('click', function() {
			jsonParam.page = page;
			if(!util.isEmpty(jsonParam.pjtNm)){
				var $element = $("input[name='rndList["+jsonParam.idx+"].subjectSearchKeywords']");
				naviAction($element,jsonParam);
			}else{
				naviAction(jsonParam);
			}
		});
		return $btn;
	};

	/**
	 * 대상을 렌더링 후 렌더링 결과로 교체
	 * @param $template 렌더링할 대상 Jquery Object
	 * @param json 렌더링할 데이터
	 */
	this.render = function($template, data) {
		var template = Handlebars.compile($template.html());
		$template.html(template(data))
	};

	/**
	 * 대상을 렌더링 후 렌더링 결과로 교체
	 * @param datas 렌더링할 대상 엘리먼트와 데이터 배열
	 */
	this.renders = function(datas) {
		$(datas).each(function() {
			util.render(this.$template, this.data);
		});
	};

	/**
	 * 대상을 숨기거나 표시, 입력된 값이 있을 경우 삭제
	 * @param $obj 대상 Jquery Object
	 * @param showOrHide ture : 표시, false : 숨김
	 */
	this.toggle = function($obj, showOrHide) {
		if(showOrHide) $obj.show();
		else $obj.hide();
	};

	/**
	 * 대상에 토글이벤트 추가
	 * @param fieldName 대상 필드
	 */
	this.addToggleEvent = function(fieldName) {

		$("input[name*='"+ fieldName + "']").on('change', function() {
			if($(this).prop('checked') && $(this).val() == 'ver'){
				$($(this).data('fields')).each(function() {

					util.toggle($('#area-disuseCd'), true);
				});
			}else if(!$(this).prop('checked') && $(this).val() == 'ver'){
				$($(this).data('fields')).each(function() {
					util.toggle($('#area-disuseCd'), false);
				});
			}else if($(this).prop('checked') && $(this).val() == 'com'){
				$($(this).data('fields')).each(function() {

					util.toggle($('#area-disuseComCd'), true);
				});
			}else if(!$(this).prop('checked') && $(this).val() == 'com'){
				$($(this).data('fields')).each(function() {
					util.toggle($('#area-disuseComCd'), false);
				});
			}else{
				$($(this).data('fields')).each(function() {
					util.toggle($('#area-' + this.field), this.req);
				});
			}
		});

		$("input[name*='"+ fieldName + "']").each(function() {
			if($(this).data('def')) {
				$(this).click();
			}
		});
	};

	/**
	 * 대상에 토글이벤트 추가
	 * @param fieldNames 대상 필드 배열
	 */
	this.addToggleEvents = function(fieldNames) {
		$(fieldNames).each(function() {
			util.addToggleEvent(this);
		});
	};

	//~ Public Search methods =============================================================================================

	this.isEmpty =function(str) {
		return (!str || !jQuery.trim(str));
	};

	/**
	 * 페이지 이동에 따른 파라미터 셋팅.
	 */
	this.pageMove = function(param){
		var key = fn_Parameter("key");
		var equipId = fn_Parameter("equipId");
		if(key == "" || key == null || key == "undefined") key = "";
		if(param == "add") location.href = "/kp1800/kp1810Write.do?key="+key;
		else if(param == "search") location.href = "/kp1800/kp1810.do?key="+key;
		else if(param == "read") location.href = "/kp1800/kp1810Read.do?equipId="+equipId+"&key="+key;
		else if(param == "mod") location.href = "/kp1800/kp1810Modify.do?equipId="+equipId+"&key="+key;
	};

	/**
	 * 모델검색
	 * @param params {modelNm: 모델명, manufactureNm: 제작사명, page: 페이지}
	 */
	this.modelSearch = function(params) {

		progress.start();
		params = $.extend({apiType: "models", modelNm: null, manufactureNm: null, page: 1}, params);

		$.ajax({
			//url: api.getModelsUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col style="width:15%;"/> '+
						'<col style="width:15%;"/> '+
						'<col style="width:15%;"/> '+
						'<col/>' +
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">모델명</th>' +
							'<th scope="row" class="btl">제작사</th>' +
							'<th scope="row" class="btl">제작국가</th>' +
							'<th scope="row" class="btl">표준분류</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="modelCd" value="{{modelCd}}" title="{{branchCd}}"></td>' +
						'<td>{{modelNm}}</td>' +
						'<td>{{manufactureNm}}</td>' +
						'<td>{{madeNm}}</td>' +
						'<td>{{branchNm}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';
			$('#modelDiv').remove();
			$('#rslt-modelCd')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.modelSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 주장비검색
	 * @param params {korNm: 장비명, fixedAsetNo: 고정자산관리번호, operNm: 담당자명, page: 페이지}
	 */
	this.mainEquipSearch = function(params) {

		progress.start();
		/*var selector = params.selector;
		params = {
					korNm: $("#"+params.selector).find("input[name='korNm']").val(),
					fixedAsetNo: $("#"+params.selector).find("input[name='fixedAsetNo']").val(),
					operNm: $("#"+params.selector).find("input[name='operNm']").val()
		};
		params = $.extend({apiType: "main-equips", korNm: null, fixedAsetNo: null, operNm: null, page: 1}, params);*/
		params = $.extend({apiType: "main-equips", page: 1}, params);
		$.ajax({
			//url: api.getMainEquipsUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col style="width:20%;"/> '+
						'<col/>' +
						'<col style="width:20%;"/> '+
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">취득구분명</th>' +
							'<th scope="row" class="btl">장비명(영문명)</th>' +
							'<th scope="row" class="btl">담당자명</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="cpId" value="{{equipId}}"></td>' +
						'<td>{{equipNm}}</td>' +
						'<td>{{korNm}}({{engNm}})</td>' +
						'<td>{{operNm}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';
			$('#cpIdDiv').remove();
			$('#rslt-cpId')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.mainEquipSearch, data, params));

			$("#resultMainEquipSearch").append(template);
			progress.stop();
		});
	};

	/**
	 * 과제검색
	 * @param params {stanYear: 기준년도, pjtNm: 과제명, mainRschOrgNm: 주관기관명, rschDirectorNm: 연구책임자명, page: 페이지}
	 */
	this.subjectSearch = function(element, params) {

		progress.start();
		var idx = $(element).parent().find("input[name*='subjectSearchKeywords']").data('index');
		params = $.extend({apiType: "subjects", stanYear: null , pjtNm: null, mainRschOrgNm: null, rschDirectorNm: null, idx:idx, page: 1}, params);
		$.ajax({
			//url: api.getSubjectsUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col style="width:20%;"/> '+
						'<col style="width:20%;"/> '+
						'<col/>' +
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">국가과제고유번호</th>' +
							'<th scope="row" class="btl">사업명</th>' +
							'<th scope="row" class="btl">과제명</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="rndList['+idx+'].subjectOcd" value="{{subjectOcd}}"></td>' +
						'<td>{{subjectOcd}}</td>' +
						'<td>{{busiNm}}</td>' +
						'<td>{{subjectNm}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';
			$(element).parent().find('#subjectDiv').remove();
			$(element).parent().find('#rslt-subjectOcd')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.subjectSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 불용판정 - 양여기관 검색
     * @param params {organNm: 기관명, organClassNm: 기관유형명, page: 페이지}
	 */
	this.grantOrganSearch = function(element, params) {

		progress.start();
		params = $.extend({apiType: "organs", organNm: null, organClassNm: null, page: 1}, params);

		$.ajax({
			//url: api.getOrgansUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col style="width:20%;"/> '+
						'<col style="width:20%;"/> '+
						'<col style="width:10%;"/> '+
						'<col/>' +
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">양여기관명</th>' +
							'<th scope="row" class="btl">양여기관유형명</th>' +
							'<th scope="row" class="btl">지역명</th>' +
							'<th scope="row" class="btl">주소</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="transferOrganCd" value="{{organCd}}"></td>' +
						'<td>{{organNm}}</td>' +
						'<td>{{organClassNm}}</td>' +
						'<td>{{areaNm}}</td>' +
						'<td>{{address1}} {{address2}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';
			/*var template =
				'{{#pageList}}' +
					'<div class="radio">' +
						'<label>' +
							'<input type="radio" name="transferOrganCd" value="{{organCd}}">' +
							'{{organNm}} / {{organClassNm}} / {{areaNm}} / {{address1}} {{address2}}' +
						'</label>'+
					'</div>' +
				'{{/pageList}}';*/

			$(element).parent().find('#transferOrganDiv').remove();
			$(element).parent().find('#rslt-transferOrganCd')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.grantOrganSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 처분완료 - 양여기관 검색
     * @param params {organNm: 기관명, organClassNm: 기관유형명, page: 페이지}
	 */
	this.grantComOrganSearch = function(element, params) {

		progress.start();
		params = $.extend({apiType: "organs", organNm: null, organClassNm: null, page: 1}, params);

		$.ajax({
			//url: api.getOrgansUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col style="width:20%;"/> '+
						'<col style="width:20%;"/> '+
						'<col style="width:10%;"/> '+
						'<col/>' +
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">양여기관명</th>' +
							'<th scope="row" class="btl">양여기관유형명</th>' +
							'<th scope="row" class="btl">지역명</th>' +
							'<th scope="row" class="btl">주소</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="transferComOrganCd" value="{{organCd}}"></td>' +
						'<td>{{organNm}}</td>' +
						'<td>{{organClassNm}}</td>' +
						'<td>{{areaNm}}</td>' +
						'<td>{{address1}} {{address2}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';

			$(element).parent().find('#transferOrganDiv').remove();
			$(element).parent().find('#rslt-transferOrganCd')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.grantOrganSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 대여기관 검색
     * @param params {organNm: 기관명, organClassNm: 기관유형명, page: 페이지}
	 */
	this.rentalOrganSearch = function(params) {

		progress.start();
		params = $.extend({apiType: "organs", organNm: null, organClassNm: null, page: 1}, params);

		$.ajax({
			//url: api.getOrgansUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'{{#pageList}}' +
					'<div class="radio">' +
						'<label>' +
							'<input type="radio" name="lendOrganCd" value="{{organCd}}">' +
							'{{organNm}} / {{organClassNm}} / {{areaNm}} / {{address1}} {{address2}}' +
						'</label>'+
					'</div>' +
				'{{/pageList}}';

			$('#lendOrganDiv').remove();
			$('#rslt-lendOrganCd')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.rentalOrganSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 설치장소 검색
	 * @param params {address: 주소, page: 페이지}
	 */
	this.setupSearch = function(params) {

		progress.start();
		params = $.extend({apiType: "setups", address: null, page: 1}, params);

		$.ajax({
			//url: api.getSetupsUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col/>' +
						'<col style="width:15%;"/> '+
						'<col style="width:35%;"/> '+
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">주소</th>' +
							'<th scope="row" class="btl">기관명</th>' +
							'<th scope="row" class="btl">동/건물명</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="setupId" value="{{setupId}}"/></td>' +
						'<td>{{address1}} {{address2}}</td>' +
						'<td>{{organNm}}</td>' +
						'<td>{{placeNm}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';
			$('#setupDiv').remove();
			$('#rslt-setupId')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.setupSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 우편번호 검색
	 * @param params {address: 주소, page: 페이지}
	 */
	this.zipSearch = function(params) {

		progress.start();
		params = $.extend({apiType: "zips", keyword: null, page: 1}, params);
		$.ajax({
			//url: api.getZipsUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			if(data.errors.length > 0) {
				var message = '';
				var hideCnt = 0;
				var dupCnt = 0;
				$(data.errors).each(function() {
					message = message +
					'<div class="row" style="margin: 0;">' +
						'<div class="col-md-4">' + this.field + '</div>' +
						'<div class="col-md-6">' + this.message + '</div>' +
						'<div class="col-md-2">' + this.code + '</div>' +
					'</div>';
				});
				$('<div class="container-fluid">' + message + '</div>').css({fontSize: '12px'}).dialog({
					title: 'ERROR : ' + data.errors.length + '건 (hide : ' + hideCnt + ', dup : ' + dupCnt + ')',
					width: '500px'
				});
			}
			else {
				var template =
					'<table class="table_type02">' +
						'<colgroup>' +
							'<col style="width:10%;"/> '+
							'<col style="width:15%;"/> '+
							'<col/>' +
						'</colgroup>' +
						'<thead>' +
							'<tr>' +
								'<th scope="row" class="btl">선택</th>' +
								'<th scope="row" class="btl">우편번호</th>' +
								'<th scope="row" class="btl">도로명주소</th>' +
							'</tr>' +
						'</thead>' +
						'<tbody>' +
							'{{#juso}}' +
							'<tr>' +
							'<td><input type="radio" name="zipCd" value="{{zipNo}}"></td>' +
							'<td>{{zipNo}}</td>' +
							'<td>{{roadAddr}}</td>' +
							'</tr>' +
							'{{/juso}}' +
						'</tbody>' +
					'</table>';
				$('#rslt-zip')
					.html(Handlebars.compile(template)(data))
					.append(renderNavi(util.zipSearch, data, params));
			}
			progress.stop();
		});
	};

	/**
	 * 심의정보 검색
	 * @param params {rfdEquipNo:심의번호, rfdEquipNm:심의장비명, projDetailName:사업명, reqInstName:요청기관명, projectAnalysisName:연구과제명, analysisInstName:연구과제기관명,  page: 페이지}
	 */
	this.redSearch = function(params) {

		progress.start();
		params = $.extend({apiType: "red", rfdEquipNo:null, rfdEquipNm:null, projDetailName:null, reqInstName:null, projectAnalysisName:null, analysisInstName:null,  page: 1}, params);

		$.ajax({
			//url: api.getRedsUrl(),
			url: "/kp1800/kp1810CmmnAjax.do",
			dataType: "json",
			data: params
		}).done(function(data) {
			var template =
				'<table class="table_type02">' +
					'<colgroup>' +
						'<col style="width:10%;"/> '+
						'<col style="width:15%;"/> '+
						'<col/>' +
						'<col style="width:15%;"/> '+
						'<col style="width:15%;"/> '+
						'<col style="width:15%;"/> '+
					'</colgroup>' +
					'<thead>' +
						'<tr>' +
							'<th scope="row" class="btl">선택</th>' +
							'<th scope="row" class="btl">장비명</th>' +
							'<th scope="row" class="btl">세부사업명</th>' +
							'<th scope="row" class="btl">연구과제명</th>' +
							'<th scope="row" class="btl">연구과제기관명</th>' +
							'<th scope="row" class="btl">요청기관명</th>' +
						'</tr>' +
					'</thead>' +
					'<tbody>' +
						'{{#pageList}}' +
						'<tr>' +
						'<td><input type="radio" name="rfdEquipNo" onclick="util.redDataSel(this);" data-rfdEquipSkey="{{rfdEquipSkey}}" data-rfdSkey="{{rfdSkey}}" data-checkEquipSkey="{{checkEquipSkey}}" value="{{rfdEquipNo}}" title="{{rfdEquipNo}}"/></td>' +
						'<td>{{nameKor}}</td>' +
						'<td>{{projDetailName}}</td>' +
						'<td>{{projectAnalysisName}}</td>' +
						'<td>{{analysisInstName}}</td>' +
						'<td>{{reqInstName}}</td>' +
						'</tr>' +
						'{{/pageList}}' +
					'</tbody>' +
				'</table>';
			$('#redDiv').remove();
			$('#rslt-red')
				.html(Handlebars.compile(template)(data))
				.append(renderNavi(util.redSearch, data, params));
			progress.stop();
		});
	};

	/**
	 * 연구장비 검색
	 * @param params {fixedAsetNo: 고정자산관리번호, equipNm: 장비명, registId: 등록자 아이디, registNm: 등록자 성명, equipNo: 장비등록번호, keywords: 검색어, page: 페이지}
	 */
	this.equipSearch = function(params) {

		var key = fn_Parameter("key");

		progress.start();
		params = $.extend({keywords : null, sort: "registDt", pageSize: 10, page :1}, params);

		$.ajax({
			url : api.getEquipsUrl(),
			data: params
		}).done(function(data) {
			if(data.errors && data.errors.length > 0) {
				var message = '';
				$(data.errors).each(function() {
					message += this.field + ' : ' + this.message + ' (' + this.code + ')' + '\n\n';
				});
				alert(message);
			} else {
				var template =
					'{{#pageList}}' +
							'<tr>'+
								'<td><a href="api-read.html?equipId={{equipId}}&key='+key+'"><strong>{{korNm}}</strong></a><div style="color:#999;">{{engNm}}</div></td>' +
								'<td class="text-center">{{organNm}}</td>' +
								'<td class="text-center">{{fixedAsetNo}}</td>' +
								'<td class="text-center">{{equipNo}}</td>' +
								'<td class="text-center">{{useScopeNm}}</td>' +
								'<td class="text-center">{{idleDisuseNm}}</td>' +
								'<td class="text-center">{{registId}}</td>' +
								'<td class="text-center">{{registDt}}</td>' +
								'<td class="text-center">{{statusNm}}</td>' +
								'<td class="text-center">{{apiYn}}</td>' +
							'</tr>' +
					'{{/pageList}}';

				$('#rslt-search').html(Handlebars.compile(template)(data));
				$('#rstl-navi').html(renderNavi(util.equipSearch, data, params).removeClass('btn-group-xs col-sm-offset-3 col-sm-8').addClass('btn-group-sm'));
			}
			progress.stop();
		});
	};

	/**
	 * 연구장비 조회
	 * @param params {equipId: 장비아이디}
	 */
	this.equipRead = function(equipId) {
		progress.start();
		$.ajax({
			type : "POST",
			url : "/kp1800/kp1810ReadAjax.do",
			data : {
				equipId : equipId
			},
			dataType : "json",
			success:function(data)
			{
				if(data.errors && data.errors.length > 0) {
					var message = '';
					$(data.errors).each(function() {
						message += this.message + ' (' + this.code + ')' + '\n\n';
					});
					alert(message);
				} else {
					$('#template').html(Handlebars.compile($('#template').html())(data));

					//ZZZZ 삭제 권한 추가 START
					if (data.confirmYn == "Y") {
						$('#liDeleteBtn').hide();
					}
					//ZZZZ 삭제 권한 추가 END
				}
				progress.stop();
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("error : " + thrownError);
			},
			complete:function()
			{
			}
		});


		/*$.ajax({
			//url : api.getEquipsUrl(equipId)
			url : "/kp1800/kp1810ReadAjax.do",
			data : {
				equipId : equipId
			}
		}).done(function(data) {
			if(data.errors && data.errors.length > 0) {
				var message = '';
				$(data.errors).each(function() {
					message += this.message + ' (' + this.code + ')' + '\n\n';
				});
				alert(message);
			} else {
				$('#template').html(Handlebars.compile($('#template').html())(data));

				//ZZZZ 삭제 권한 추가 START
				if (data.confirmYn == "Y") {
					$('#liDeleteBtn').hide();
				}
				//ZZZZ 삭제 권한 추가 END
			}
			progress.stop();
		});*/
	};

	/**
	 * 연구장비 수정
	 * @param params {equipId: 장비아이디}
	 */
	this.equipMod = function(equipId) {

		progress.start();
		$.ajax({
			type : "POST",
			url : "/kp1800/kp1810ReadAjax.do",
			data : {
				equipId : equipId
			},
			dataType : "json",
			success:function(data)
			{
				if(data.errors && data.errors.length > 0) {
					var message = '';
					$(data.errors).each(function() {
						message += this.message + ' (' + this.code + ')' + '\n\n';
					});
					alert(message);
				} else {
					var disuseCdYn = "Y";
					var disuseComCdYn = "Y";
					if(data.disuseCd  == null){
						data.disuseCd = "01";
						disuseCdYn = "N";
					}
					if(data.disuseComCd  == null){
						data.disuseComCd = "01";
						disuseComCdYn = "N";
					}

					util.renders([
						{$template: $('#tmp-takeCd'), data: api.getTakeCds(data.takeCd)},
						{$template: $('#tmp-registCd'), data: api.getRegistCds(data.registCd)},
						{$template: $('#tmp-branchCd'), data: api.getBranchCds(data.branchCd)},
						{$template: $('#tmp-useScopeCd'), data: api.getUseScopeCds(data.useScopeCd)},
						{$template: $('#tmp-useTypeCd'), data: api.getUseTypeCds(data.useTypeCd)},
						{$template: $('#tmp-idleDisuseCd'), data: api.getIdleDisuseCds(data.idleDisuseCd)},
						{$template: $('#tmp-disuseCd'), data: api.getDisuseCds(data.disuseCd)},
						{$template: $('#tmp-disuseComCd'), data: api.getDisuseComCds(data.disuseComCd)},
						{$template: $('#tmp-useScopeReasonCd'), data: api.getUseScopeReasonCds(data.useScopeReasonCd)},
						{$template: $('#tmp-devOpenYn'), data: api.getDevOpenYns(data.devOpenYn)},
						{$template: $('#tmp-modelYn'), data: api.getModelYns('Y')},
						{$template: $('#tmp-setupYn'), data: api.getSetupYns('Y')},
						{$template: $('#tmp-groundYn'), data: api.getGroundYns(data.groundYn)},
						{$template: $('#tmp-madeCd'), data: api.getMadeCds(data.madeCd)},
						{$template: $('#tmp-modelTakeCd'), data: api.getModelTakeCds()},
						{$template: $('#tmp-modelNmYn'), data: api.getModelNmYns()},
						//{$template: $('#tmp-buildCpCd'), data: api.getBuildCpCds()},
						{$template: $('#tmp-areaCd'), data: api.getAreaCds()},
						{$template: $('#tmp-rndYn'), data: api.getRndYns('Y')},
//						{$template: $('#tmp-subjectYn'), data: api.getSubjectYns()},
						//{$template: $('#tmp-officeCd'), data: api.getOfficeCds()},
						{$template: $('#tmp-officeCd1'), data: api.getOffice1Cds()},
						{$template: $('#tmp-officeCd2'), data: api.getOffice2Cds()},
						{$template: $('#tmp-officeCd3'), data: api.getOffice3Cds()},
						{$template: $('#tmp-sixCd'), data: api.getSixCds()},
						{$template: $('#tmp-importCd'), data: api.getImportCds(data.importCd)},
						{$template: $('#tmp-managerId'), data: api.getManagers(data.managerId)},
						{$template: $('#tmp-useScopeRange'), data: api.getUseScopeRangeCds(data.useScopeRange)},
						{$template: $('#tmp-useScopeMean'), data: api.getUseScopeMeanCds(data.useScopeMean)},
						{$template: $('#tmp-redYn'), data: api.getRedYns(data.rfdEquipNo != null ? 'Y' : 'N' )},
						{$template: $('#tmp-disuseGb'), data: api.getDisuseGbs(data.disuseCd != null ? 'ver' : '')},
						{$template: $('#tmp-disuseGb'), data: api.getDisuseGbs(data.disuseComCd != null ? 'com' : '')},
						{$template: $('#tmp-officeYn'), data: api.getOfficeYns()}
					]);
					$('#templateMod').html(Handlebars.compile($('#templateMod').html())(data));
					util.addToggleEvents(['registCd', 'useScopeCd', 'useScopeRange', 'useScopeMean', 'useTypeCd', 'takeCd', 'idleDisuseCd', 'useScopeReasonCd' , 'disuseGb', 'disuseCd', 'disuseComCd', 'modelYn', 'setupYn', 'groundYn', 'devOpenYn', 'redYn', 'officeYn']);

					$('input:radio[name="rndYn"][value="'+data.rndYn+'"]').prop('checked','true');
					$("input:radio[name='rndYn']").attr('disabled',true);
					$("#divRnd"+data.rndYn+"Add").css('display','block');

					/** 불용선택에따른 체크박스 초기화 */
					if(disuseCdYn == "N"){
						$("input[name='disuseGb']").eq(0).attr('checked', false);
						$("#area-disuseCd").hide();
					}
					if(disuseComCdYn == "N"){
						$("input[name='disuseGb']").eq(1).attr('checked', false);
						$("#area-disuseComCd").hide();
					}


					/** 과제 선택 및 직접 입력 초기화 **/
					$('input[name*="subjectYn"], select[name*="officeCd"], select[name*="sixCd"]').each(function(){
						var idx = $(this).parent().parent().parent().find("label").data("index");
						var name = $(this).attr("name");
						var prefix = name.substring(0, name.indexOf('[')+1);
						var suffix = name.substring(name.indexOf(']'));
						$(this).attr('name', prefix + idx + suffix);
					});

					$(data.equipRndList).each(function(idx){
						if(this.selInpYn){
							$('input:radio[name="rndList['+idx+'].subjectYn"][value="'+this.selInpYn+'"]').prop('checked',true);
							util.subjectYnSel($('input:radio[name="rndList['+idx+'].subjectYn"][value='+this.selInpYn+']'));
						}
						if(this.officeCd){
							var offceYnSel = "";
							if(this.officeCd.startsWith("P")){
								offceYnSel = "1";
							}else if(this.officeCd.startsWith("N")){
								offceYnSel = "2";
							}else if(!this.officeCd.startsWith("P") && !this.officeCd.startsWith("N")){
								offceYnSel = "3";
							}
							$('.officeYn'+idx).find('input:radio[name=officeYn][value='+offceYnSel+']').attr('checked',true);
							if(this.selInpYn == "N") util.officeYnSel($('.officeYn'+idx).find('input:radio[name=officeYn][value='+offceYnSel+']'));
							$('.officeYn'+idx).parent().parent().parent().find('select.officeCd'+offceYnSel+' > option[value='+this.officeCd+']').attr('selected','selected');
						}
					});

					//ZZZZ 수정 권한 추가 START
					if (data.confirmYn == "Y") {
						$('input[type="radio"]:not(:checked)').attr("disabled", "disabled");
						$('input[type="file"]').attr("disabled", "disabled");
						$('input[type="text"]').attr("readonly", "readonly");
						$('textarea').attr("readonly", "readonly");
						$('select').attr("readonly", "readonly");

						$('select[name="managerId"]').attr("readonly", false);
						$('input[name="operTel"]').attr("readonly", false);
						$('input[name="setupYn"]').attr("disabled", false);
						$('input[name="setupSearchKeywords"]').attr("readonly", false);
						$('input[name="groundYn"]').attr("disabled", false);
						$('input[name="floorNo"]').attr("readonly", false);
						$('input[name="roomNo"]').attr("readonly", false);
						$('input[name="lat"]').attr("readonly", false);
						$('input[name="lng"]').attr("readonly", false);
						$('input[name="areaCd"]').attr("readonly", false);
						$('input[name="zipSearchKeywords"]').attr("readonly", false);
						$('input[name="address1"]').attr("readonly", false);
						$('input[name="address2"]').attr("readonly", false);
						$('input[name="stLtype"]').attr("readonly", false);
						$('input[name="stMtype"]').attr("readonly", false);
					}
					//ZZZZ 수정 권한 추가 END
				}
				progress.stop();
			},
			error:function(xhr, ajaxOptions, thrownError)
			{
				alert("error : " + thrownError);
			},
			complete:function()
			{
			}
		});
	};

	//~ Public Valid methods =============================================================================================

	/**
	 * 고정자산관리번호 중복성 검토
	 * @param fixedAsetNo 고정자산관리번호
	 */
	this.validFixedAsetNo = function(fixedAsetNo, equipId) {
		if(fixedAsetNo == null || fixedAsetNo == "") {
			alert("고정자산관리번호를 입력하세요.");
		}
		else {
			$.ajax({
				url: api.getValidFixedAsetNoUrl() + '?fixedAsetNo=' + fixedAsetNo + '&equipId=' +equipId
			}).done(function(data) {
				if(data.errors.length > 0) {
					var message = '';
					$(data.errors).each(function() {
						message += this.field + ' : ' + this.message + ' (' + this.code + ')' + '\n\n';
					});
					alert(message);
				}
				else {
					alert('사용가능한 고정자산관리번호입니다.');
				}
			});
		}
	};

	/**
	 * R&D과제일때 R&D과제 및 비R&D과제 추가
	 * @param rndYn R&D여부
	 */
	this.rndYAddRow = function(element, rndYn){

		var idxRndY = 0;
		var idxRndN = 0;
		$("#area-rnd-div").find(".areaRndY").each(function(){
			if($(this).css('display') == 'block'){
				idxRndY++;
			}
		});
		$("#area-rnd-div").find(".areaRndN").each(function(){
			if($(this).css('display') == 'block'){
				idxRndN++;
			}
		});

		var idx = idxRndY + idxRndN;

		if(rndYn == 'rndY'){
			var cloneHtml;
			if(idx > 0 && idxRndY > 0) cloneHtml = $(element).parent().parent().parent().find("#area-rndY").clone(true);
			else cloneHtml = rndYCloneHtml;

			var btnHtml = '<div class="form-group delBtnDiv" style="text-align:center"><button type="button" onclick="util.rndDel(this,\'Y\');" class="btn btn-sm">삭제</button></div>';
			$(cloneHtml).find("input[name*='rndList'], select[name*='rndList']").each(function(){
				var name = $(this).attr("name");
				var prefix = name.substring(0, name.indexOf('[')+1);
				var suffix = name.substring(name.indexOf(']'));

				if(suffix == '].rndYn'){
					$(this).attr('name', prefix + idx + suffix).val("Y");
				}else if(suffix == '].subjectYn'){
					$(this).attr('name', prefix + idx + suffix).val($(this).val());
				}else if(suffix == '].subjectSearchKeywords'){
					$(this).data('index', idx);
					$(this).attr('name', prefix + idx + suffix).val("");
				}else if(suffix == '].officeYn'){
					$(this).attr('name', prefix + idx + suffix).val($(this).val());
				}else if(suffix == '].officeCd'){
					$(this).attr('name', prefix + idx + suffix).val();
					$(this).removeAttr('disabled');
				}else{
					$(this).attr('name', prefix + idx + suffix).val("");
				}
			});
			if(idx > 0) $(cloneHtml).find("#divLine").css('display','block');
			if(idx > 0) $(cloneHtml).append(btnHtml);
			$(cloneHtml).css('display','block');
			$(cloneHtml).find('input:radio[name*=officeYn]').removeAttr('checked');
			$(cloneHtml).find("div.rndSubjectDirectOffice").css('display','none');
			$(cloneHtml).find("#rslt-subjectOcd").children().remove();
			$(cloneHtml).find("#subjectDiv").remove();

			$("#area-rnd-div").append(cloneHtml);

			if (idx > 0) {
				$('input[name="rndList['+idx+'].startDt"]').unbind();
				$('input[name="rndList['+idx+'].startDt"]').removeClass('hasDatepicker');
				$('input[name="rndList['+idx+'].startDt"]').attr("id", "");
				$('input[name="rndList['+idx+'].startDt"]').attr("onclick", "fnInitCalcToShow('rndList["+idx+"].startDt')");

				$('input[name="rndList['+idx+'].endDt"]').unbind();
				$('input[name="rndList['+idx+'].endDt"]').removeClass('hasDatepicker');
				$('input[name="rndList['+idx+'].endDt"]').attr("id", "");
				$('input[name="rndList['+idx+'].endDt"]').attr("onclick", "fnInitCalcToShow('rndList["+idx+"].endDt')");
			}

		}else{
			var lineBlock = "none";
			if(idx > 0) lineBlock = "block";
			var rndNHtml = '<div id="area-rndN" class="form-group areaRndN">'
						+ '<div id="divLine" style="display:'+lineBlock+'; border:0; width:90%; margin:0 auto 15px; height:1px; border-bottom:1px dotted #ccc;box-sizing:border-box"></div>'
						+ '<label class="col-sm-2 control-label">과제정보</label>'
						+ '<div class="col-sm-10">'
						+ '<div class="form-group" style="display: none;"><input type="hidden" name="rndList['+idx+'].rndYn" value="N"/></div>'
						+ '<div class="form-group">'
						+ '<label class="col-sm-2 control-label">비R&amp;D 재원명</label>'
						+ '<div class="col-sm-6">'
						+ '<input type="text" name="rndList['+idx+'].rndAcq" class="form-control input-sm">'
						+ '</div>'
						+ '</div>'
						+ '<div class="form-group">'
						+ '<label class="col-sm-2 control-label">집행금액</label>'
						+ '<div class="col-sm-6">'
						+ '<input type="text" name="rndList['+idx+'].rndPrc" class="form-control input-sm" onblur="util.rndPerDivison(this);" placeholder="(원)" />'
						+ '</div>'
						+ '</div>'
						+ '<div class="form-group">'
						+ '<label class="col-sm-2 control-label">과제비중</label>'
						+ '<div class="col-sm-6">'
						+ '<input type="text" name="rndList['+idx+'].rndWeight" class="form-control input-sm" placeholder="%" readonly="readonly" />'
						+ '</div>'
						+ '</div>'
						+ '</div>';
			if(idx > 0) rndNHtml    += '<div class="form-group delBtnDiv" style="text-align:center"><button type="button" onclick="util.rndDel(this,\'N\');" class="btn btn-sm">삭제</button></div>';
			rndNHtml	+= '</div>';
			$("#area-rnd-div").append(rndNHtml);
		}

		$("#area-rnd-div").find(".delBtnDiv").css('display','none');
		$("#area-rnd-div").find(".delBtnDiv:last").css('display','block');

		$("input:radio[name='rndYn']").attr('disabled',true);
	};

	/**
	 * 재원구분 라디오 선택에 따른 화면 여부
	 * @param
	 */
	this.selRnd = function(element){

		if($(element).val() == 'Y'){
			$("#divRndYAdd").show();
			$("#divRndNAdd").hide();
		}else{
			$("#divRndYAdd").hide();
			$("#divRndNAdd").show();
		}
	};

	/**
	 * 과제 선택입력 및 직접입력 여부
	 * @param
	 */
	this.subjectYnSel = function(element){
		if($(element).val() == 'Y'){
			$(element).parent().parent().parent().parent().find(".rndSubjectDirect").hide();
			$(element).parent().parent().parent().parent().find(".rndSubjectSelect").show();
			$(element).parent().parent().parent().parent().find(".rndSubjectDirectOffice").hide();
		}else{
			$(element).parent().parent().parent().parent().find(".rndSubjectSelect").hide();
			$(element).parent().parent().parent().parent().find(".rndSubjectDirect").show();
			$(element).parent().parent().parent().parent().find(".rndSubjectDirectOffice").hide();
		}
	};

	/**
	 * 시설장비심의번호 검색결과 라디오 버튼 선택 데이터 셋팅
	 * @param
	 */
	this.redDataSel = function(element){
		$("#area-redY").find('input[name="rfdSkey"]').val($(element).data('rfdskey'));
		$("#area-redY").find('input[name="rfdEquipSkey"]').val($(element).data('rfdequipskey'));
		$("#area-redY").find('input[name="checkEquipSkey"]').val($(element).data('checkequipskey'));
	};

	/**
	 * 파라미터 가져오기
	 * @param parameterText
	 */
	this.getParameter = function(key){
		var _parammap = {};
		document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {

			function decode(s) {
				return decodeURIComponent(s.split("+").join(" "));
			}
			_parammap[decode(arguments[1])] = decode(arguments[2]);
		});

		return _parammap[key];
	};

	/**
	 * R&D 멀티 삭제
	 */
	this.rndDel = function(element, flag){
		$(element).parent().parent().prev("div").find('.delBtnDiv').css('display','block');
		$parent = $(element).parent().parent(".areaRnd"+flag);
		$parent.remove();
	};

	/**
	 * 과제수행부처청 라디오 선택 버튼 클릭
	 */
	this.officeYnSel = function(element){
		$(element).closest("#rndYDirect").find(".rndSubjectDirectOffice").hide();
		$(element).closest("#rndYDirect").find("#area-officeCd"+$(element).val()).show();
	};

	/**
	 * 정부/지자체 라디오 버튼 선택
	 */
	this.governGubunSel = function(element){
		if($(element).val() == "Y"){
			$(".rndNGovernSel").show();
			$(".rndNMunic").hide();
		}
		else {
			$(".rndNGovern").hide();
			$(".rndNGovernSel").hide();
			$(".rndNMunic").show();
		}
	}

	/**
	 * 년도에 따른 과제수행부처청 라디오 버튼 선택
	 */
	this.governYnSel = function(element){
		$govern = $(element).closest("#area-rndN");

		$govern.find(".rndNGovern").hide();
		$govern.find(".rndNGovernSel").show();
		$govern.find("#area-governCd"+$(element).val()).show();
	}


	/**
	 * 과제가중치 자동입력 (사용금액 - 취득금액)
	 */
	this.rndPerDivison = function(element) {
		var idx = $(element).attr("name").replace("rndList[","").replace("].rndPrc","");
		var val = $(element).val();
		var divVal = null;

		if(!util.isEmpty(val) && !util.isEmpty($('input[name=takePrc]').val())) {
			var value = val.replace(/,/g,"");
			var takePrc = $('input[name=takePrc]').val().replace(/,/g,"");
			divVal = parseInt((value / takePrc) * 100);
		}

		if(divVal > 100) {
			alert("과제비중이 100%를 넘을수 없습니다.");
			$("input[name='rndList["+idx+"].rndPrc']").val("");
			$("input[name='rndList["+idx+"].rndWeight']").val("");
			$("input[name='rndList["+idx+"].rndPrc']").focus();
			return false;
		}
		else $("input[name='rndList["+idx+"].rndWeight']").val(divVal);
	}

};