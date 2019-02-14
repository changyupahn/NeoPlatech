/**
 * Copyright (c) 2013 kisystems, Inc.
 * All right reserved
 *
 * Revision History
 * Author           Date             Description
 * --------------   --------------   ------------------
 * kicompany        2014. 01. 21.    신규생성
 */

// CROS(Cross-origin resource sharing) 지원여부 확인
if(!('withCredentials' in new XMLHttpRequest()) && typeof XDomainRequest == "undefined") {
	alert('크로스도메인을 지원하지 않는 브라우저입니다.\n\n크로스도메인을 지원하는 브라우저로 업그레이드 또는 변경바랍니다.\n\nCORS SUPPORT : IE8+, Chrome, Firefox3.5+, Safari4+');
}

function fn_Parameter(key){
	var _parammap = {};
	document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {

		function decode(s) {
			return decodeURIComponent(s.split("+").join(" "));
		}
		_parammap[decode(arguments[1])] = decode(arguments[2]);
	});

	return _parammap[key];
};

var api = new function() {
	//var prefixUrl = '${corsData.domain}/api/eq';
	var prefixUrl = 'http://api.zeus.go.kr/api/eq';
	//var key = '${corsData.key}';
	var key = fn_Parameter("key");
	//~ Instance fields =============================================================================================

	/**
	 * 공통코드 목록
	 */
	var codes;

	var flags = {
			modelYns: [{code: 'Y', name: '선택입력'}, {code: 'N', name: '직접입력'}],
			devOpenYns : [{code: 'Y', name: '공개'}, {code: 'N', name: '비공개'}],
			modelNmYns : [{code: 'Y', name: '있음'}, {code: 'N', name: '없음'}],
			setupYns : [{code: 'Y', name: '선택입력'}, {code: 'N', name: '직접입력'}],
			groundYns : [{code: 'Y', name: '지상'}, {code: 'N', name: '지하'}],
			subjectYns : [{code: 'Y', name: '선택입력'}, {code: 'N', name: '직접입력'}],
			rndYns : [{code:'Y', name: 'R&D 과제(연구개발사업 조사분석대상)'},{code:'N', name: '공공투자(비 R&D재원) 과제 '}],
			organYns : [{code: 'Y', name: '선택입력'}, {code: 'N', name: '직접입력'}],
			redYns : [{code: 'Y', name: '예'}, {code: 'N', name: '아니오'}],
			disuseGbs : [{code: 'ver', name: '불용판정'}, {code: 'com', name: '처분완료'}],
			cpYns : [{code: 'Y', name: '예'}, {code: 'N', name:'아니오'}],
			officeYns : [{code: '1', name: '2014년 이후 과제수행부처청'}, {code: '2', name:'2008년~2012년 과제수행부처청'}, {code: '3', name:'2008년 이전 과제수행부처청'}]
	};

	//$.ajax({url: prefixUrl + '/codes/' + key, async: false}).done(function(data) {
	$.ajax({url: "/kp1800/kp1810CmmnAjax.do?apiType=codes", dataType: "json", async: false}).done(function(data) {
		codes = data;
	});

	//~ Public Url Methods =============================================================================================

	/**
	 * 모델검색 API URL
	 */
	this.getModelsUrl = function() {
		return prefixUrl + '/models/' + key;
	};

	/**
	 * 주장비검색 API URL
	 */
	this.getMainEquipsUrl = function() {
		return prefixUrl + '/main-equips/' + key;
	};

	/**
	 * 과제검색 API URL
	 */
	this.getSubjectsUrl =  function() {
		return prefixUrl + '/subjects/' + key;
	};

	/**
	 * 기관검색 API URL
	 */
	this.getOrgansUrl =  function() {
		return prefixUrl + '/organs/' + key;
	};

	/**
	 * 설치장소검색 API URL
	 */
	this.getSetupsUrl =  function() {
		return prefixUrl + '/setups/' + key;
	};

	/**
	 * 우편번호검색 API URL
	 */
	this.getZipsUrl =  function() {
		return prefixUrl + '/zips/' + key;
	};

	/**
	 * 고정자산관리번호 중복 검사 API URL
	 */
	this.getValidFixedAsetNoUrl = function() {
		return prefixUrl + '/valid/' + key;
	};

	/**
	 * 장비 API URL
	 */
	this.getEquipsUrl = function(equipId, options) {
		if(!equipId) {
			return prefixUrl + '/equips/' + key;
		}
		else if(!options) {
			return prefixUrl + '/equips/' + equipId + '/' + key;
		}
		else {
			return prefixUrl + '/equips/' + equipId + '/' + key + '?' + $.param(options, true);
		}
	};

	/**
	 * 시설장비심의정보 검색 API URL
	 */
	this.getRedsUrl = function() {
		return prefixUrl + '/red/' + key;
	};


	//~ Public Code Methods =============================================================================================

	/**
	 * 표준분류코드 목록 조회
	 */
	this.getBranchCds = function() {
		var branchCds = codes.branchCds;
		$(branchCds).each(function () {
			if(this.level != '1') {
				if(this.code.substring(0,1) == "H") {
					this.fields = [
					        {field: 'takeDt', req: false},
							{field: 'takePrc', req: false},
							{field: 'devSdt', req: false},
							{field: 'devEdt', req: false},
							{field: 'devPrc', req: false},
							{field: 'devOpenYn', req: false},
							{field: 'devSpec', req: false},
							{field: 'buildSdt', req: true},
							{field: 'buildEdt', req: true},
							{field: 'buildPrcDom', req: true},
							{field: 'buildPrcFor', req: true}
							//{field: 'buildCpCd', req: true}
					];
				}
			}
		});
		return branchCds;
	};

	/**
	 * 취득방법코드 목록 조회
	 */
	this.getTakeCds = function(defaultCode) {
		var takeCds = codes.takeCds;
		$(takeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '1') {
				this.fields = [
						{field: 'takeDt', req: true},
						{field: 'takePrc', req: true},
						{field: 'devSdt', req: false},
						{field: 'devEdt', req: false},
						{field: 'devPrc', req: false},
						{field: 'devOpenYn', req: false},
						{field: 'devSpec', req: false},
						{field: 'buildSdt', req: false},
						{field: 'buildEdt', req: false},
						{field: 'buildPrcDom', req: false},
						{field: 'buildPrcFor', req: false}
						//{field: 'buildCpCd', req: false}
				];
			}
			else if(this.code == '2') {
				this.fields = [
						{field: 'takeDt', req: false},
						{field: 'takePrc', req: false},
						{field: 'devSdt', req: true},
						{field: 'devEdt', req: true},
						{field: 'devPrc', req: true},
						{field: 'devOpenYn', req: true},
						{field: 'devSpec', req: true},
						{field: 'buildSdt', req: false},
						{field: 'buildEdt', req: false},
						{field: 'buildPrcDom', req: false},
						{field: 'buildPrcFor', req: false}
						//{field: 'buildCpCd', req: false}
				];
			}
			else if(this.code == '3') {
				this.fields = [
						{field: 'takeDt', req: false},
						{field: 'takePrc', req: false},
						{field: 'devSdt', req: false},
						{field: 'devEdt', req: false},
						{field: 'devPrc', req: false},
						{field: 'devOpenYn', req: false},
						{field: 'devSpec', req: false},
						{field: 'buildSdt', req: true},
						{field: 'buildEdt', req: true},
						{field: 'buildPrcDom', req: true},
						{field: 'buildPrcFor', req: true}
						//{field: 'buildCpCd', req: true}
				];
			}
			else if(this.code == '4') {
				this.fields = [
						{field: 'takeDt', req: false},
						{field: 'takePrc', req: false},
						{field: 'devSdt', req: false},
						{field: 'devEdt', req: false},
						{field: 'devPrc', req: false},
						{field: 'devOpenYn', req: false},
						{field: 'devSpec', req: false},
						{field: 'buildSdt', req: true},
						{field: 'buildEdt', req: true},
						{field: 'buildPrcDom', req: true},
						{field: 'buildPrcFor', req: true}
						//{field: 'buildCpCd', req: true}
				];
			}
		});
		return takeCds;
	};

	/**
	 * 장비구분코드 목록 조회
	 */
	this.getRegistCds = function(defaultCode) {
		var registCds = codes.registCds;
		$(registCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '1') {
				this.fields = [
						{field: 'cpId', req: false},
						{field: 'useScopeCd', req: true},
						{field: 'useTypeCd', req: true},
						{field: 'idleDisuseCd', req: true},
						{field: 'setupYn', req: true},
						{field: 'cpYn', req: false},
						{field: 'cpYnData', req: false}
				];
			}
			else {
				this.fields = [
						{field: 'cpId', req: true},
						{field: 'useScopeCd', req: false},
						{field: 'useTypeCd', req: false},
						{field: 'idleDisuseCd', req: false},
						{field: 'setupYn', req: false},
						{field: 'cpYn', req: true},
						{field: 'cpYnData', req: false}
				];
			}
		});
		return registCds;
	};

	/**
	 * 활용범위코드 목록 조회
	 */
	this.getUseScopeCds = function(defaultCode) {
		var useScopeCds = codes.useScopeCds;
		$(useScopeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '1') {
				this.fields = [
				               {field: 'useScopeReasonCd', req: true},
				               {field: 'useScopeRange', req: false},
				               {field: 'useScopeMean', req: false}
				];
			}
			else if(this.code == '2'){
				this.fields = [
				               {field: 'useScopeReasonCd', req: false},
				               {field: 'useScopeRange', req: true},
				               {field: 'useScopeMean', req: true}
				];
			}
			else if(this.code == '3'){
				this.fields = [
				               {field: 'useScopeReasonCd', req: false},
				               {field: 'useScopeRange', req: true},
				               {field: 'useScopeMean', req: true}
				];
			}

		});
		return useScopeCds;
	};

	/**
	 * 단독활용사유코드 목록 조회
	 */
	this.getUseScopeReasonCds = function(defaultCode) {
		var useScopeReasonCds = codes.useScopeReasonCds;
		$(useScopeReasonCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '09') {
				this.fields = [{field: 'useScopeReasonEtc', req: true}];
			}
			else {
				this.fields = [{field: 'useScopeReasonEtc', req: false}];
			}
		});
		return useScopeReasonCds;
	};

	/**
	 * 공동활용허용범위코드 목록 조회
	 */
	this.getUseScopeRangeCds = function(defaultCode) {
		var useScopeRangeCds = codes.useScopeRangeCds;
		$(useScopeRangeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return useScopeRangeCds;
	};

	/**
	 * 공동활용사용방법코드 목록 조회
	 */
	this.getUseScopeMeanCds = function(defaultCode) {
		var useScopeMeanCds = codes.useScopeMeanCds;
		$(useScopeMeanCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return codes.useScopeMeanCds;
	};

	/**
	 * 장비용도코드 목록 조회
	 */
	this.getUseTypeCds = function(defaultCode) {
		var useTypeCds = codes.useTypeCds;
		$(useTypeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '06') {
				this.fields = [{field: 'useTypeEtc', req: true}];
			}
			else {
				this.fields = [{field: 'useTypeEtc', req: false}];
			}
		});
		return useTypeCds;
	};

	/**
	 * 불용코드(불용판정) 목록 조회
	 */
	this.getDisuseCds = function(defaultCode) {
		var disuseCds = codes.disuseCds;
		$(disuseCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '01') {
				this.fields = [
						{field: 'saleDt', req: true},
						{field: 'recyclingDt', req: false},
						{field: 'disposalDt', req: false},
						{field: 'transferOrganCd', req: false},
						{field: 'transferDt', req: false},
						{field: 'lendOrganCd', req: false},
						{field: 'lendDt', req: false}
				];
			}
			else if(this.code == '02') {
				this.fields = [
						{field: 'saleDt', req: false},
						{field: 'recyclingDt', req: true},
						{field: 'disposalDt', req: false},
						{field: 'transferOrganCd', req: false},
						{field: 'transferDt', req: false},
						{field: 'lendOrganCd', req: false},
						{field: 'lendDt', req: false}
				];
			}
			else if(this.code == '03') {
				this.fields = [
				 		{field: 'saleDt', req: false},
				 		{field: 'recyclingDt', req: false},
				 		{field: 'disposalDt', req: true},
						{field: 'transferOrganCd', req: false},
						{field: 'transferDt', req: false},
						{field: 'lendOrganCd', req: false},
						{field: 'lendDt', req: false}
				];
			}
			else if(this.code == '04') {
				this.fields = [
						{field: 'saleDt', req: false},
						{field: 'recyclingDt', req: false},
						{field: 'disposalDt', req: false},
						{field: 'transferOrganCd', req: true},
						{field: 'transferDt', req: true},
						{field: 'lendOrganCd', req: false},
						{field: 'lendDt', req: false}
				];
			}
		});
		return disuseCds;
	};

	/**
	 * 불용코드(처분완료) 목록 조회
	 */
	this.getDisuseComCds = function(defaultCode) {
		var disuseComCds = codes.disuseComCds;
		$(disuseComCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '01') {
				this.fields = [
						{field: 'saleComDt', req: true},
						{field: 'recyclingComDt', req: false},
						{field: 'disposalComDt', req: false},
						{field: 'transferComOrganCd', req: false},
						{field: 'transferComDt', req: false}
				];
			}
			else if(this.code == '02') {
				this.fields = [
						{field: 'saleComDt', req: false},
						{field: 'recyclingComDt', req: true},
						{field: 'disposalComDt', req: false},
						{field: 'transferComOrganCd', req: false},
						{field: 'transferComDt', req: false}
				];
			}
			else if(this.code == '03') {
				this.fields = [
				 		{field: 'saleComDt', req: false},
				 		{field: 'recyclingComDt', req: false},
				 		{field: 'disposalComDt', req: true},
						{field: 'transferComOrganCd', req: false},
						{field: 'transferComDt', req: false}
				];
			}
			else if(this.code == '04') {
				this.fields = [
						{field: 'saleComDt', req: false},
						{field: 'recyclingComDt', req: false},
						{field: 'disposalComDt', req: false},
						{field: 'transferComOrganCd', req: true},
						{field: 'transferComDt', req: true}
				];
			}
		});
		return disuseComCds;
	};

	/**
	 * 장비상태코드 목록 조회
	 */
	this.getIdleDisuseCds = function(defaultCode) {
		var idleDisuseCds = codes.idleDisuseCds;
		$(idleDisuseCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == '4') {
				this.fields = [
			               {field: 'disuseGb', req: true},
			               {field: 'disuseCd', req: false},
			               {field: 'disuseComCd', req: false}
		               ];
			}
			else {
				this.fields = [
			               {field: 'disuseGb', req: false},
			               {field: 'disuseCd', req: false},
			               {field: 'disuseComCd', req: false}
		               ];
			}
		});
		return idleDisuseCds;
	};

	/**
	 * 모델유무구분코드 목록 조회
	 */
	this.getModelYns = function(defaultCode) {
		var modelYns = flags.modelYns;
		$(modelYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'Y') {
				this.fields = [
						{field: 'modelCd', req: true},
						{field: 'modelTakeCd', req: false},
						{field: 'modelNmYn', req: false},
						{field: 'madeCd', req: false},
						{field: 'manufactureNm', req: false},
						{field: 'modelNm', req: false},
						{field: 'branchCd', req: false}
				];
			}
			else if(this.code == 'N') {
				this.fields = [
						{field: 'modelCd', req: false},
						{field: 'modelTakeCd', req: true},
						{field: 'modelNmYn', req: true},
						{field: 'madeCd', req: true},
						{field: 'manufactureNm', req: true},
						{field: 'modelNm', req: true},
						{field: 'branchCd', req: true}
				];
			}
		});
		return modelYns;
	};

	/**
	 * 설치장소유무구분코드 목록 조회
	 */
	this.getSetupYns = function(defaultCode) {
		var setupYns = flags.setupYns;
		$(setupYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'Y') {
				this.fields = [
						{field: 'setupId', req: true},
						{field: 'areaCd', req: false},
						{field: 'zipCd', req: false},
						{field: 'address1', req: false},
						{field: 'address2', req: false},
						{field: 'stLtype', req: false},
						{field: 'stMtype', req: false},
						{field: 'groundYn', req: true},
						{field: 'floorNo', req: true},
						{field: 'roomNo', req: true},
						{field: 'lat', req: false},
						{field: 'lng', req: false}
				];
			}
			else if(this.code == 'N') {
				this.fields = [
						{field: 'setupId', req: false},
						{field: 'areaCd', req: true},
						{field: 'zipCd', req: true},
						{field: 'address1', req: true},
						{field: 'address2', req: true},
						{field: 'stLtype', req: true},
						{field: 'stMtype', req: true},
						{field: 'groundYn', req: true},
						{field: 'floorNo', req: true},
						{field: 'roomNo', req: true},
						{field: 'lat', req: true},
						{field: 'lng', req: true}
				];
			}
		});
		return setupYns;
	};

	/**
	 * 지상/지하구분코드 목록 조회
	 */
	this.getGroundYns = function(defaultCode) {
		var groundYns = flags.groundYns;
		$(groundYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return groundYns;
	};

	/**
	 * 과제정보유무구분코드 목록 조회
	 */
	this.getSubjectYns = function(defaultCode) {
		var subjectYns = flags.subjectYns;
		$(subjectYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'Y') {
				this.fields = [
						{field: 'subjectOcd', req: true},
						{field: 'officeYn', req: false},
						{field: 'subjectNm', req: false},
						{field: 'busiNm', req: false},
						{field: 'organNm', req: false},
						{field: 'directorNm', req: false},
						{field: 'startDt', req: false},
						{field: 'endDt', req: false},
						{field: 'porganNm', req: false},
						{field: 'subjectPcd', req: false},
						{field: 'sixTechCd', req: false},
						{field: 'sixCd', req: false},
						{field: 'officeCd1', req: false},
						{field: 'officeCd2', req: false},
						{field: 'officeCd3', req: false}
				];
			}
			else if(this.code == 'N') {
				this.fields = [
						{field: 'subjectOcd', req: false},
						{field: 'officeYn', req: true},
						{field: 'subjectNm', req: true},
						{field: 'busiNm', req: true},
						{field: 'organNm', req: true},
						{field: 'directorNm', req: true},
						{field: 'startDt', req: true},
						{field: 'endDt', req: true},
						{field: 'porganNm', req: true},
						{field: 'subjectPcd', req: true},
						{field: 'sixTechCd', req: true},
						{field: 'sixCd', req: true},
						{field: 'officeCd1', req: false},
						{field: 'officeCd2', req: false},
						{field: 'officeCd3', req: false}
				];
			}
		});
		return subjectYns;
	};

	/**
	 * 개발장비공개여부구분코드 목록 조회
	 */
	this.getDevOpenYns = function(defaultCode) {
		var devOpenYns = flags.devOpenYns;
		$(devOpenYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return devOpenYns;
	};

	/**
	 * 제작국가코드 목록 조회
	 */
	this.getMadeCds = function(defaultCode) {
		var madeCds = codes.madeCds;
		$(madeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return madeCds;
	};

	/**
	 * 5대중점투자분야코드 목록 조회
	 */
	this.getImportCds = function(defaultCode) {
		var importCds = codes.importCds;
		$(importCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return importCds;
	};

	/**
	 * 구성요소코드 목록 조회
	 */
	this.getBuildCpCds = function(defaultCode) {
		var buildCpCds = codes.buildCpCds;
		$(buildCpCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return buildCpCds;
	};

	/**
	 * 모델취득방법코드 목록 조회
	 */
	this.getModelTakeCds = function(defaultCode) {
		var modelTakeCds = codes.modelTakeCds;
		$(modelTakeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return modelTakeCds;
	};

	/**
	 *  모델명유무코드 목록 조회
	 */
	this.getModelNmYns = function(defaultCode) {
		var modelNmYns = flags.modelNmYns;
		$(modelNmYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return modelNmYns;
	};

	/**
	 * 과제수행부처코드 목록 조회
	 */
	this.getOfficeCds = function(defaultCode) {
		var officeCds = codes.officeCds;
		$(officeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return officeCds;
	};

	/**
	 * 과제수행부처코드 목록 조회
	 */
	this.getOffice1Cds = function(defaultCode) {
		var officeCds = codes.officeCds;
		var office1Cds = new Array();
		$(officeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code.startsWith("P")){
				var objCode = new Object();;
				objCode.code = this.code;
				objCode.name = this.name;
				office1Cds.push(objCode);
			}
		});
		return office1Cds;
	};

	/**
	 * 과제수행부처코드 목록 조회
	 */
	this.getOffice2Cds = function(defaultCode) {
		var officeCds = codes.officeCds;
		var office2Cds = new Array();
		$(officeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code.startsWith("N")){
				var objCode = new Object();;
				objCode.code = this.code;
				objCode.name = this.name;
				office2Cds.push(objCode);
			}
		});
		return office2Cds;
	};

	/**
	 * 과제수행부처코드 목록 조회
	 */
	this.getOffice3Cds = function(defaultCode) {
		var officeCds = codes.officeCds;
		var office3Cds = new Array();
		$(officeCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(!this.code.startsWith("P") && !this.code.startsWith("N")){
				var objCode = new Object();;
				objCode.code = this.code;
				objCode.name = this.name;
				office3Cds.push(objCode);
			}
		});
		return office3Cds;
	};

	/**
	 * 6T코드 목록 조회
	 */
	this.getSixCds = function(defaultCode) {
		var sixCds = codes.sixCds;
		$(sixCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return sixCds;
	};

	/**
	 * 지역코드 목록 조회
	 */
	this.getAreaCds = function(defaultCode) {
		var areaCds = codes.areaCds;
		$(areaCds).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return areaCds;
	};

	/**
	 * 기관 유무 구분코드 목록 조회
	 */
	this.getOrganYns = function(defaultCode) {
		var organYns = flags.organYns;
		$(organYns).each(function() {
			if(this.code == defaultCode) { this.def = true;}
			if(this.code == 'Y') {
				this.fields = [
				    {field: 'organY', req: true},
				    {field: 'organN', req: false}
				];
			}
			else if(this.code == 'N') {
				this.fields = [
					{field: 'organY', req: false},
					{field: 'organN', req: true}
				];
			}
		});
		return organYns;
	};

	/**
	 * 재원구분 목록 조회
	 */
	this.getRndYns = function(defaultCode) {
		var rndYns = flags.rndYns;
		$(rndYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'Y') {
				this.fields = [
						{field: 'rndY', req: true},
						{field: 'rndN', req: false}
				];
			}
			else if(this.code == 'N') {
				this.fields = [
						{field: 'rndY', req: false},
						{field: 'rndN', req: true}
				];
			}
		});

		return rndYns;
	};
	/*
	this.photoYns = function() {
		var keyVal = '${corsData.key}';
		if(keyVal == '2e330935-d5de-4025-8500-e0062b2a170c') {
			this.fields = [
					{field: 'photoY', req: false},
					{field: 'photoN', req: true}
			];
		}
		else {
			this.fields = [
					{field: 'photoY', req: true},
					{field: 'photoN', req: false}
			];
		}
	};
	*/
	/**
	 * 장비담당자 목록 조회
	 */
	this.getManagers = function(defaultCode) {
		var managers = codes.managers;
		$(managers).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return managers;
	};

	/**
	 * 시설장비 심의번호 선택
	 */
	this.getRedYns = function(defaultCode) {
		var redYns = flags.redYns;
		$(redYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'Y') {
				this.fields = [
					{field: 'redY', req: true}
				];
			}
			else if(this.code == 'N') {
				this.fields = [
					{field: 'redY', req: false}
				];
			}
		});

		return redYns;
	};

	/**
	 * 불용구분(불용판정) 선택
	 */
	this.getDisuseGbs = function(defaultCode) {
		var disuseGbs = flags.disuseGbs;
		$(disuseGbs).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'ver') {
				this.fields = [
					{field: 'disuseCd', req: true},
					{field: 'disuseComCd', req: false}
				];
			}
			else if(this.code == 'com') {
				this.fields = [
					{field: 'disuseCd', req: false},
					{field: 'disuseComCd', req: true}
				];
			}
		});
		return disuseGbs;
	};

	/**
	 * 장비구분에 따른(보조장비,부대시설장비) 추후 입력 여부
	 */
	this.getCpYns = function(defaultCode) {
		var cpYns = flags.cpYns;
		$(cpYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
			if(this.code == 'Y') {
				this.fields = [
					{field: 'cpYnData', req: true},
					{field: 'cpId', req: false}
				];
			}
			else{
				this.fields = [
					{field: 'cpYnData', req: false},
					{field: 'cpId', req: true}
				];
			}
		});
		return cpYns;
	};

	/**
	 * 과제수행부처 년도별에 따른 선택 추가
	 */
	this.getOfficeYns = function(defaultCode) {
		var officeYns = flags.officeYns;
		$(officeYns).each(function() {
			if(this.code == defaultCode) { this.def = true; }
		});
		return officeYns;
	};

};

