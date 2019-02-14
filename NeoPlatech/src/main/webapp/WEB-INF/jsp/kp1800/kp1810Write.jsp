<%@page import="egovframework.com.cmm.service.EgovProperties"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "ZEUS국가연구시설장비 등록";
String curAction = "/kp1800/kp1810Modify.do";
String listAction = "/kp1800/kp1810Read.do";
String modifyAction = "/kp1800/kp1810Modify.do";
String writeAction = "/kp1800/kp1810Write.do";
String assetSearchAction = "/kp1800/kp1810Asset.do";
String detailAction = "/kp1800/kp1810Read.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지

String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Pragma" content="no-cache" />
	<title><%=pageTitle%></title>

	<script type="text/javascript" src="/common/js/jquery-1.7.1.min.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
	<script type="text/javascript" src="/common/js/utils.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
	<script type="text/javascript" src="/common/js/popup.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
	<%@ include file="/WEB-INF/jsp/common/jqCalendar.jsp" %>

	<link href="/common/zeus/jquery-ui.css" rel="stylesheet"/>
	<link href="/common/zeus/bootstrap.min.css" rel="stylesheet"/>
	<script src="/common/zeus/jquery.min.js" type="text/javascript"></script>
	<script src="/common/zeus/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/common/zeus/jquery.form.js" type="text/javascript" type="text/javascript"></script>
	<script src="/common/zeus/bootstrap.min.js" type="text/javascript"></script>
	<script src="/common/zeus/handlebars.min.js" type="text/javascript"></script>
	<script src="/common/zeus/moment.js" type="text/javascript"></script>
	<script src="/common/zeus/api.js" type="text/javascript"></script>
	<script src="/common/zeus/nfecutil.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" type="text/javascript"></script>

	<style type="text/css" >
		.ar { background-color: #F1F1F1; padding: 15px 0 1px 0; border-radius: 10px; margin:10px 0 0 0; }
		.br { padding: 15px 0 1px 0; }
		.table_type02{border-collapse:collapse; border-spacing:0; width:100%; margin:10px 0 10px 0; }
		.table_type02 th{text-align:center; padding:10px 5px; border-top:1px solid #e8e8e8; background:#f7f7f7;}
		.table_type02 td{padding:10px 5px; border-top:1px solid #e8e8e8; line-height:24px;text-align: center;}
		.table_type02 .n_btl{ border-top:0px; background:none; vertical-align:bottom; padding-bottom:6px; border-bottom:1px solid #7f7f7f; }
		.table_type02 th.n_btl { padding-bottom:8px; }
		.table_type02 .btl{ border-top:1px solid #7f7f7f; }
		.table_type02 .bbl{ border-bottom:1px solid #e8e8e8}
		.table-responsive{width:900px;}
	</style>


	<script type="text/javascript">
		var key = util.getParameter("key");

		$(window).load(function() {
			if(key){
				$("#accessKey").val(key);
			}

			util.renders([
				{$template: $('#tmp-takeCd'), data: api.getTakeCds('1')},
				{$template: $('#tmp-registCd'), data: api.getRegistCds('1')},
				{$template: $('#tmp-branchCd'), data: api.getBranchCds()},
				{$template: $('#tmp-useScopeCd'), data: api.getUseScopeCds('2')},
				{$template: $('#tmp-useTypeCd'), data: api.getUseTypeCds('02')},
				{$template: $('#tmp-idleDisuseCd'), data: api.getIdleDisuseCds('1')},
				{$template: $('#tmp-disuseCd'), data: api.getDisuseCds('01')},
				{$template: $('#tmp-disuseComCd'), data: api.getDisuseComCds('01')},
				{$template: $('#tmp-useScopeReasonCd'), data: api.getUseScopeReasonCds('01')},
				{$template: $('#tmp-devOpenYn'), data: api.getDevOpenYns()},
				{$template: $('#tmp-modelYn'), data: api.getModelYns('Y')},
				{$template: $('#tmp-setupYn'), data: api.getSetupYns('Y')},
				{$template: $('#tmp-groundYn'), data: api.getGroundYns('Y')},
				{$template: $('#tmp-madeCd'), data: api.getMadeCds()},
				{$template: $('#tmp-modelTakeCd'), data: api.getModelTakeCds()},
				{$template: $('#tmp-modelNmYn'), data: api.getModelNmYns()},
				//{$template: $('#tmp-buildCpCd'), data: api.getBuildCpCds()},
				{$template: $('#tmp-areaCd'), data: api.getAreaCds()},
				{$template: $('#tmp-rndYn'), data: api.getRndYns('Y')},
				{$template: $('#tmp-subjectYn'), data: api.getSubjectYns('Y')},
				{$template: $('#tmp-officeCd1'), data: api.getOffice1Cds()},
				{$template: $('#tmp-officeCd2'), data: api.getOffice2Cds()},
				{$template: $('#tmp-officeCd3'), data: api.getOffice3Cds()},
				//{$template: $('#tmp-governCd1'), data: api.getOffice1Cds()},
				//{$template: $('#tmp-governCd2'), data: api.getOffice2Cds()},
				//{$template: $('#tmp-governCd3'), data: api.getOffice3Cds()},
				{$template: $('#tmp-sixCd'), data: api.getSixCds()},
				{$template: $('#tmp-importCd'), data: api.getImportCds()},
				{$template: $('#tmp-managerId'), data: api.getManagers()},
				{$template: $('#tmp-useScopeRange'), data: api.getUseScopeRangeCds('01')},
				{$template: $('#tmp-useScopeMean'), data: api.getUseScopeMeanCds('01')},
				{$template: $('#tmp-redYn'), data: api.getRedYns('N')},
				//{$template: $('#tmp-disuseGb'), data: api.getDisuseGbs()},
				{$template: $('#tmp-disuseGb'), data: api.getDisuseGbs()},
				//{$template: $('#tmp-cpYn'), data: api.getCpYns('N')},
				{$template: $('#tmp-officeYn'), data: api.getOfficeYns()}
				//{$template: $('#tmp-governYn'), data: api.getOfficeYns()}
			]);

			util.addToggleEvents(['registCd', 'useScopeCd', 'useScopeRange', 'useScopeMean', 'useTypeCd', 'takeCd', 'idleDisuseCd', 'useScopeReasonCd' ,'disuseGb', 'disuseCd', 'disuseComCd', 'modelYn', 'setupYn', 'groundYn', 'devOpenYn', 'redYn', 'officeYn'/* , 'cpYn', 'governYn' */]);


			/** R&D과제 여부 초기화 시작 */
			$("#area-rndY").hide();
			$("#area-rndN").hide();

			$("#area-rndY").find(".rndSubjectDirect").hide();
			$("#area-rndY").find(".rndSubjectSelect").show();


			$("input:radio[name*='subjectYn'][value='Y']").prop('checked','true');
			/** R&D과제 여부 초기화 종료 */

			$('#equipForm').on('submit', function(e) {

				e.preventDefault();
				if(confirm('등록 하시겠습니까?')) {
					$(this).ajaxSubmit({
						//url: api.getEquipsUrl(),
						url: "/kp1800/kp1810WriteProc.do",
						dataType: "json",
						async: false,
						beforeSerialize : function($form, options){
							$("input:radio[name='rndYn']").removeAttr('disabled');

							/** 부처청 선택에 따른 다른 선택값 삭제 */
							var subjectYnCnt = $("input[name*='subjectYn'][value='N']:checked").length;
							var officeYnCnt = $("input[name*='officeYn']:checked").length;
							if(subjectYnCnt > 0 && officeYnCnt > 0){
								for(var i=0; i<subjectYnCnt; i++){
									var officeYnVal = $("input[name='rndList["+i+"].officeYn']:checked").val();
									$("input[name='rndList["+i+"].officeYn']").each(function(idx){
										if($(this).val() != officeYnVal){
											$(this).closest("#rndYDirect").find("#area-officeCd"+$(this).val()+" select[name*='officeCd']").attr('disabled',true);
										}
									});
								}
							}
						},
						success: function(data) {

							$('.has-error').toggleClass('has-error');
							if(data.errors.length > 0) {
								var message = '';
								var hideCnt = 0;
								var dupCnt = 0;
								var dupFields = '';
								$(data.errors).each(function() {

									if(dupFields.indexOf(',' + this.field + ',') > -1) {
										//console.log(this.field + ' : duplicate');
										dupCnt++;
									}
									else dupFields += ',' + this.field + ',';

									if($('[name=' + this.field + ']').is(":visible")) {
										$('[name=' + this.field + ']').closest('.form-group').toggleClass('has-error');
									}
									else {
										if($('#area-' + this.field) && $('#area-' + this.field).is(":visible")) {
											$('#area-' + this.field).toggleClass('has-error');
										}
										else {
											//console.log(this.field + ' : hide');
											hideCnt++;
										}
									}
									message = message +
										'<div class="row" style="margin: 0;">' +
											'<div class="col-md-2">' + this.code + '</div>' +
											'<div class="col-md-4">' + this.field + '</div>' +
											'<div class="col-md-6">' + this.message + '</div>' +
										'</div>';

								});
								$('<div class="container-fluid">' + message + '</div>').css({fontSize: '12px'}).dialog({
									title: 'ERROR : ' + data.errors.length + '건 (hide : ' + hideCnt + ', dup : ' + dupCnt + ')',
									width: '500px'
								});
							}
							else {
								alert("정상적으로 등록되었습니다.\n\nequipId : " + data.equipId);

								location.href = "<%=detailAction%>?equipId=" + data.equipId + "&key=<%=key%>";
							}
						}
					});
				}
			});

			rndYCloneHtml = $("#area-rndY").clone(true)
			$("#area-rndY").remove();

			$('select[name="managerId"]').val('<%=cmRequest.getString("managerId")%>');
		});
	</script>
</head>
<body>

<div class="container">
	<div class="header page-header">
		<ul class="nav nav-pills pull-right">
		</ul>
		<h3 class="text-muted"><%=pageTitle%></h3>
	</div>
	<form name="equipForm" id="equipForm" method="post" enctype="multipart/form-data" class="form-horizontal">
		<input type="hidden" id="accessKey" class="form-control input-sm" />

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">장비구분</label>
				<div id="tmp-registCd" class="col-sm-6">
	   				{{#.}}
						<label class="radio-inline">
							<input type="radio" name="registCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' /> {{name}}
						</label>
					{{/.}}
				</div>
			</div>
			<!--
			<div id="area-cpYn" class="form-group">
				<label class="col-sm-2 control-label">추후입력 여부</label>
				<div id="tmp-cpYn" class="col-sm-6">
					{{#.}}
					<label class="radio-inline">
						<input type="radio" name="cpYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' />{{name}}
					</label>
					{{/.}}
				</div>
			</div>
		 -->
			<div id="area-cpId" class="form-group">
				<label class="col-sm-2 control-label">주장비검색</label>
				<div class="col-sm-6">
					<input type="text" name="mainEquipSearchKeywords" class="form-control input-sm" />
					<!-- <input type="hidden" id="cpId" name="cpId"  /> -->
					<div id="rslt-cpId" class="table-responsive" style="font-size: 12px;"></div>
				</div>
				<%-- <button type="button" class="btn btn-sm" onclick="util.mainEquipSearch({korNm: $('input[name*=mainEquipSearchKeywords]').val()})">검색</button> --%>
				<input type="button" value="검색" onclick="util.mainEquipSearch({korNm: $('input[name*=mainEquipSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
			</div>

			<!-- <div id="area-cpYnData" class="form-group">
				<div id="area-cpYn" class="form-group">
					<label class="col-sm-2 control-label">주장비 추후입력 정보</label>
					<div class="col-sm-10">
						<div class="form-group">
							<label class="col-sm-2 control-label">등록 예상일자</label>
							<div class="col-sm-6">
								<input type="text" name="cpDt" class="form-control input-sm" placeholder="yyyy-MM-dd" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">추후입력 사유</label>
							<div class="col-sm-6">
								<input type="text" name="cpReason" class="form-control input-sm" />
							</div>
						</div>
					</div>
				</div>
			</div> -->
		</div>

		<div class="ar">
			<div id="area-redYn" class="form-group">
				<label class="col-sm-2 control-label">시설장비심의번호 여부</label>
				<div id="tmp-redYn" class="col-sm-8">
					{{#.}}
					<label class="radio-inline">
						<input type="radio" name="redYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
					</label>
					{{/.}}
				</div>
			</div>

			<div id="area-redY" class="form-group">
				<label class="col-sm-2 control-label">심의정보 검색</label>
				<div id="tmp-rfdEquipNo" class="col-sm-10">
					<div class="form-group">
						<label class="col-sm-2 control-label">심의번호</label>
						<div id="" class="col-sm-4">
							<input type="text" name="rfdEquipNoKeyword" class="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">심의장비명</label>
						<div id="" class="col-sm-4">
							<input type="text" name="rfdEquipNmKeyword" class="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">사업명</label>
						<div id="" class="col-sm-4">
							<input type="text" name="projDetailNameKeyword" class="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">요청기관명</label>
						<div id="" class="col-sm-4">
							<input type="text" name="reqInstNameKeyword" class="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">연구과제명</label>
						<div id="" class="col-sm-4">
							<input type="text" name="projectAnalysisNameKeyword" class="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">연구과제기관명</label>
						<div id="" class="col-sm-4">
							<input type="text" name="analysisInstNameKeyword" class="form-control input-sm" />
							<input type="hidden" name="rfdSkey" value="" />
							<input type="hidden" name="rfdEquipSkey" value="" />
							<input type="hidden" name="checkEquipSkey" value="" />
						</div>
						<%-- <button type="button" class="btn btn-sm" onclick="util.redSearch({rfdEquipNo : $('input[name=rfdEquipNoKeyword]').val(), rfdEquipNm : $('input[name=rfdEquipNmKeyword]').val(), projDetailName : $('input[name=projDetailNameKeyword]').val(), reqInstName : $('input[name=reqInstNameKeyword]').val(), projectAnalysisName : $('input[name=projectAnalysisNameKeyword]').val(), analysisInstName : $('input[name=analysisInstNameKeyword]').val()})">검색</button> --%>
						<input type="button" value="검색" onclick="util.redSearch({rfdEquipNo : $('input[name=rfdEquipNoKeyword]').val(), rfdEquipNm : $('input[name=rfdEquipNmKeyword]').val(), projDetailName : $('input[name=projDetailNameKeyword]').val(), reqInstName : $('input[name=reqInstNameKeyword]').val(), projectAnalysisName : $('input[name=projectAnalysisNameKeyword]').val(), analysisInstName : $('input[name=analysisInstNameKeyword]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
					</div>
					<div id="rslt-red" class="table-responsive" style="font-size: 12px;"></div>
				</div>

			</div>
		</div>

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">고정자산관리번호</label>
				<div class="col-sm-6">
					<input type="text" name="fixedAsetNo" class="form-control input-sm" />
				</div>
				<input type="button" value="자산조회" onclick="kp9080Pop()" class="btn btn-primary" style="padding:3px 6px;" />
				<input type="button" value="이중등록확인" onclick="util.validFixedAsetNo($('input[name=fixedAsetNo]').val())" class="btn btn-danger" style="padding:2px 6px;" />
			</div>

			<div class="form-group" >
				<label class="col-sm-2 control-label">한글명</label>
				<div class="col-sm-6">
					<input type="text" name="korNm" class="form-control input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">영문명</label>
				<div class="col-sm-6">
					<input type="text" name="engNm" class="form-control input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">장비사진</label>
				<div class="col-sm-6">
					<input type="file" name="fileData" class="form-control input-sm" />
				</div>
			</div>
		</div>

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">모델</label>
				<div id="tmp-modelYn" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="modelYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' /> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-modelCd" class="form-group">
				<label class="col-sm-2 control-label">모델검색</label>
				<div id="tmp-modelCd" class="col-sm-6">
					<input type="text" name="modelSearchKeywords" class="form-control input-sm" />
					<div id="rslt-modelCd" class="table-responsive" style="font-size: 12px;"></div>
				</div>
				<%-- <button type="button" class="btn btn-sm" onclick="util.modelSearch({modelNm: $('input[name=modelSearchKeywords]').val()})">검색</button> --%>
				<input type="button" value="검색" onclick="util.modelSearch({modelNm: $('input[name=modelSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
			</div>

			<div id="area-modelTakeCd"  class="form-group">
				<label class="col-sm-2 control-label">모델취득방법</label>
				<div id="tmp-modelTakeCd" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="modelTakeCd" value="{{code}}" data-def="{{def}}"/> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-modelNmYn" class="form-group">
				<label class="col-sm-2 control-label">모델명 유무</label>
				<div id="tmp-modelNmYn" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="modelNmYn" value="{{code}}" data-def="{{def}}" /> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-madeCd" class="form-group">
				<label class="col-sm-2 control-label">제작국가</label>
				<div id="tmp-madeCd" class="col-sm-6">
					<select name="madeCd" class="form-control input-sm">
						<option value="">제작국가를 선택하세요.</option>
						{{#.}}
							<option value="{{code}}">{{name}}&nbsp;({{code}})</option>
						{{/.}}
					</select>
				</div>
			</div>

			<div id="area-manufactureNm" class="form-group">
				<label class="col-sm-2 control-label">제작사명</label>
				<div id="tmp-manufactureNm" class="col-sm-6">
					<input type="text" name="manufactureNm" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-modelNm" class="form-group">
				<label class="col-sm-2 control-label">모델명</label>
				<div id="tmp-modelNm" class="col-sm-6">
					<input type="text" name="modelNm" class="form-control input-sm " />
				</div>
			</div>

			<div id="area-branchCd" class="form-group">
				<label class="col-sm-2 control-label">표준분류</label>
				<div id="tmp-branchCd" class="col-sm-6">
					<select name="branchCd" class="form-control input-sm" data-fields='{{fields.jsonArrayToString}}'>
						<option value="">표준분류를 선택하세요.</option>
						{{#.}}
							{{#if_equal level '1'}}
							<option value="{{code}}">{{name}}&nbsp;({{code}})</option>
							{{else}}
								{{#if_equal level '2'}}
								<option value="{{code}}">&nbsp;&nbsp;&nbsp;&nbsp;{{name}}&nbsp;({{code}})</option>
								{{else}}
									{{#if_equal level '3'}}
									<option value="{{code}}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{name}}&nbsp;({{code}})</option>
									{{/if_equal}}
								{{/if_equal}}
							{{/if_equal}}
						{{/.}}
					</select>
				</div>
			</div>
		</div>

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">취득방법</label>
				<div id="tmp-takeCd" class="col-sm-6">
					{{#.}}
						{{#if_equal code '3'}}
							<!-- <label class="radio-inline">
								<input type="radio" name="takeCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' disabled="disabled" /> {{name}}
							</label> -->
						{{else}}
							{{#if_equal code '4'}}
								<!-- <label class="radio-inline">
									<input type="radio" name="takeCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' disabled="disabled" /> {{name}}
								</label> -->
							{{else}}
							<label class="radio-inline">
								<input type="radio" name="takeCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' /> {{name}}
							</label>
							{{/if_equal}}
						{{/if_equal}}
					{{/.}}
				</div>
			</div>

			<div id="area-takeDt" class="form-group">
				<label class="col-sm-2 control-label">취득일자</label>
				<div id="tmp-takeDt" class="col-sm-2">
					<input type="text" name="takeDt" class="form-control input-sm datepicker3" placeholder="yyyy-MM-dd"/>
				</div>
			</div>

			<div id="area-takePrc" class="form-group">
				<label class="col-sm-2 control-label">취득금액</label>
				<div id="tmp-takePrc" class="col-sm-2">
					<input type="text" name="takePrc" class="form-control input-sm" placeholder = "원"/>
				</div>
			</div>

			<div id="area-devSdt" class="form-group">
				<label class="col-sm-2 control-label">개발일자</label>
				<div id="tmp-devSdt" class="col-sm-4">
					<div class="row">
						<div class="col-xs-6">
							<input type="text" name="devSdt" class="form-control input-sm datepicker3" placeholder="yyyy-MM-dd" />
						</div>
						<div class="col-xs-6 form-group" style="margin-bottom: 0;">
							<input type="text" name="devEdt" class="form-control input-sm datepicker3" placeholder="yyyy-MM-dd" />
						</div>
					</div>
				</div>
			</div>

			<div id="area-devPrc" class="form-group">
				<label class="col-sm-2 control-label">개발금액</label>
				<div id="tmp-devPrc" class="col-sm-2">
				   	<input type="text" name="devPrc" class="form-control input-sm" placeholder = "원"/>
				</div>
			</div>

			<div id="area-devOpenYn" class="form-group">
				<label class="col-sm-2 control-label">개발장비공개여부</label>
				<div id="tmp-devOpenYn" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="devOpenYn" value="{{code}}" data-def="{{def}}"/> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-devSpec" class="form-group">
				<label class="col-sm-2 control-label">개발장비비중</label>
				<div id="tmp-devSpec" class="col-sm-2">
					<input type="text" name="devSpec" class="form-control input-sm" placeholder = "%"/>
				</div>
			</div>

			<div id="area-buildSdt" class="form-group">
				<label class="col-sm-2 control-label">구축일자</label>
				<div id="tmp-buildSdt" class="col-sm-4">
					<div class="row">
						<div class="col-xs-6">
							<input type="text" name="buildSdt" class="form-control input-sm" placeholder="yyyy-MM-dd" />
						</div>
						<div class="col-xs-6 form-group" style="margin-bottom: 0;">
							<input type="text" name="buildEdt" class="form-control input-sm" placeholder="yyyy-MM-dd" />
						</div>
					</div>
				</div>
			</div>

			<div id="area-buildPrcDom" class="form-group">
				<label class="col-sm-2 control-label">국산금액</label>
				<div id="tmp-buildPrcDom" class="col-sm-2">
					<input type="text" name="buildPrcDom" class="form-control input-sm" placeholder="원"/>
				</div>
			</div>

			<div id="area-buildPrcFor" class="form-group">
				<label class="col-sm-2 control-label">외산금액</label>
				<div id="tmp-buildPrcFor" class="col-sm-2">
					<input type="text" name="buildPrcFor" class="form-control input-sm" placeholder="원"/>
				</div>
			</div>

			<!-- <div id="area-buildCpCd" class="form-group">
				<label class="col-sm-2 control-label">구성요소</label>
				<div id="tmp-buildCpCd" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="buildCpCd" value="{{code}}" /> {{name}}
						</label>
					{{/.}}
				</div>
			</div> -->
		</div>

		<div id="area-useScopeCd" class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">활용범위</label>
				<div id="tmp-useScopeCd" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="useScopeCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-useScopeReasonCd">
				<div class="form-group">
					<label class="col-sm-2 control-label">단독활용사유</label>
					<div id="tmp-useScopeReasonCd" class="col-sm-7">
						{{#.}}
							<label class="radio" style="font-weight: normal;">
								<input type="radio" name="useScopeReasonCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
							</label>
						{{/.}}
					</div>
				</div>

				<div id="area-useScopeReasonEtc" class="form-group">
					<label class="col-sm-2 control-label">기타사유입력</label>
					<div id="tmp-useScopeReasonEtc" class="col-sm-6">
						<input type="text" name="useScopeReasonEtc" class="form-control input-sm" />
					</div>
				</div>
			</div>

			<div id="area-useScopeRange">
				<div class="form-group">
					<label class="col-sm-2 control-label">공동활용범위</label>
					<div id="tmp-useScopeRange" class="col-sm-7">
						{{#.}}
							<label class="radio" style="font-weight: normal;">
								<input type="radio" name="useScopeRange" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
							</label>
						{{/.}}
					</div>
				</div>
			</div>

			<div id="area-useScopeMean">
				<div class="form-group">
					<label class="col-sm-2 control-label">공동활용방법</label>
					<div id="tmp-useScopeMean" class="col-sm-7">
						{{#.}}
							<label class="radio" style="font-weight: normal;">
								<input type="radio" name="useScopeMean" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
							</label>
						{{/.}}
					</div>
				</div>
			</div>
		</div>

		<div id="area-useTypeCd" class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">장비용도</label>
				<div id="tmp-useTypeCd" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="useTypeCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-useTypeEtc" class="form-group">
				<label class="col-sm-2 control-label">장비용도사유</label>
				<div id="tmp-useTypeEtc" class="col-sm-6">
					<input type="text" name="useTypeEtc" class="form-control input-sm" />
				</div>
			</div>
		</div>

		<div id="area-idleDisuseCd" class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">장비상태</label>
				<div id="tmp-idleDisuseCd" class="col-sm-6">
					{{#.}}
						{{#if_equal code '4' }}
						<label class="radio-inline">
							<input type="radio" name="idleDisuseCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' disabled="disabled"/> {{name}}
						</label>
						{{else}}
						<label class="radio-inline">
							<input type="radio" name="idleDisuseCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
						</label>
						{{/if_equal}}
					{{/.}}
				</div>
			</div>
			<div id="area-disuseGb">
				<div class="form-group">
					<label class="col-sm-2 control-label">불용구분</label>
					<div id="tmp-disuseGb" class="col-sm-6">
						{{#.}}
							<label class="radio-inline">
								<input type="checkbox" name="disuseGb" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
							</label>
						{{/.}}
					</div>
				</div>
			</div>

			<div id="area-disuseCd" class="ar">
				<div class="form-group">
					<label class="col-sm-2 control-label">불용사유(불용판정)</label>
					<div id="tmp-disuseCd" class="col-sm-6">
						{{#.}}
							<label class="radio-inline">
								<input type="radio" name="disuseCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
							</label>
						{{/.}}
					</div>
				</div>

				<div id="area-saleDt" class="form-group">
					<label class="col-sm-2 control-label">매각일자(불용판정)</label>
					<div id="tmp-saleDt" class="col-sm-2">
						<input type="text" name="saleDt" class="form-control input-sm" value=""/>
					</div>
				</div>

				<div id="area-recyclingDt" class="form-group">
					<label class="col-sm-2 control-label">재활용일자(불용판정)</label>
					<div id="tmp-recyclingDt" class="col-sm-2">
						<input type="text" name="recyclingDt" class="form-control input-sm" value=""/>
					</div>
				</div>

				<div id="area-disposalDt" class="form-group">
					<label class="col-sm-2 control-label">폐기일자(불용판정)</label>
					<div id="tmp-disposalDt" class="col-sm-2">
				   		<input type="text" name="disposalDt" class="form-control input-sm" value=""/>
					</div>
				</div>

				<div id="area-transferOrganCd" class="form-group">
					<label class="col-sm-2 control-label">양여기관(불용판정)</label>
					<div id="tmp-transferOrganCd" class="col-sm-6">
						<input type="text" name="grantOrganSearchKeywords" class="form-control input-sm"/>
						<div id="rslt-transferOrganCd" class="table-responsive" style="font-size: 12px;"></div>
					</div>
					<%-- <button type="button" class="btn btn-sm" onclick="util.grantOrganSearch(this,{organNm: $(this).parent().find('input[name=grantOrganSearchKeywords]').val()})">검색</button> --%>
					<input type="button" value="검색" onclick="util.grantOrganSearch(this,{organNm: $(this).parent().find('input[name=grantOrganSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
				</div>
				<div id="area-transferDt" class="form-group">
					<label class="col-sm-2 control-label">양여일자(불용판정)</label>
					<div id="tmp-transferDt" class="col-sm-2">
						<input type="text" name="transferDt" class="form-control input-sm" value=""/>
					</div>
				</div>
			</div>

			<div id="area-disuseComCd" class="ar" style="margin-top:10px;">
				<div class="form-group">
					<label class="col-sm-2 control-label">불용사유(처분완료)</label>
					<div id="tmp-disuseComCd" class="col-sm-6">
						{{#.}}
							<label class="radio-inline">
								<input type="radio" name="disuseComCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
							</label>
						{{/.}}
					</div>
				</div>

				<div id="area-saleComDt" class="form-group">
					<label class="col-sm-2 control-label">매각일자(처분완료)</label>
					<div id="tmp-saleComDt" class="col-sm-2">
						<input type="text" name="saleComDt" class="form-control input-sm" value=""/>
					</div>
				</div>

				<div id="area-recyclingComDt" class="form-group">
					<label class="col-sm-2 control-label">재활용일자(처분완료)</label>
					<div id="tmp-recyclingComDt" class="col-sm-2">
						<input type="text" name="recyclingComDt" class="form-control input-sm" value=""/>
					</div>
				</div>

				<div id="area-disposalComDt" class="form-group">
					<label class="col-sm-2 control-label">폐기일자(처분완료)</label>
					<div id="tmp-disposalComDt" class="col-sm-2">
						<input type="text" name="disposalComDt" class="form-control input-sm" value=""/>
					</div>
				</div>

				<div id="area-transferComOrganCd" class="form-group">
					<label class="col-sm-2 control-label">양여기관(처분완료)</label>
					<div id="tmp-transferComOrganCd" class="col-sm-6">
						<input type="text" name="grantOrganSearchKeywords" class="form-control input-sm"/>
						<div id="rslt-transferOrganCd" class="table-responsive" style="font-size: 12px;"></div>
					</div>
					<%-- <button type="button" class="btn btn-sm" onclick="util.grantComOrganSearch(this, {organNm: $(this).parent().find('input[name=grantOrganSearchKeywords]').val()})">검색</button> --%>
					<input type="button" value="검색" onclick="util.grantComOrganSearch(this, {organNm: $(this).parent().find('input[name=grantOrganSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
				</div>
				<div id="area-transferComDt" class="form-group">
					<label class="col-sm-2 control-label">양여일자(처분완료)</label>
					<div id="tmp-transferComDt" class="col-sm-2">
						<input type="text" name="transferComDt" class="form-control input-sm" value=""/>
					</div>
				</div>
			</div>
		</div>

		<div id="area-setupYn" class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">설치장소</label>
				<div id="tmp-setupYn" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="setupYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-setupId" class="form-group">
				<label class="col-sm-2 control-label">설치장소검색</label>
				<div id="tmp-setupId" class="col-sm-6">
					<input type="text" name="setupSearchKeywords" class="form-control input-sm" />
					<div id="rslt-setupId" class="table-responsive" style="font-size: 12px;"></div>
				</div>
				<%-- <button type="button" class="btn btn-sm" onclick="util.setupSearch({address: $('input[name=setupSearchKeywords]').val()})">검색</button> --%>
				<input type="button" value="검색" onclick="util.setupSearch({address: $('input[name=setupSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
			</div>

			<div id="area-areaCd"  class="form-group">
				<label class="col-sm-2 control-label">지역구분</label>
				<div id="tmp-areaCd" class="col-sm-2">
					<select name="areaCd" class="form-control input-sm">
						<option value="">지역구분을 선택하세요.</option>
						{{#.}}
							<option value="{{code}}">{{name}}</option>
						{{/.}}
					</select>
				</div>
			</div>

			<div id="area-zipCd" class="form-group">
				<label class="col-sm-2 control-label">우편번호검색</label>
				<div id="tmp-zipCd" class="col-sm-6">
					<input type="text" name="zipSearchKeywords" class="form-control input-sm"/>
					<div id="rslt-zip" class="table-responsive" style="font-size: 12px;"></div>
				</div>
				<%-- <button type="button" class="btn btn-sm" onclick="util.zipSearch({keyword: $('input[name=zipSearchKeywords]').val()})">검색</button> --%>
				<input type="button" value="검색" onclick="util.zipSearch({keyword: $('input[name=zipSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
			</div>

			<div id="area-address1" class="form-group">
				<label class="col-sm-2 control-label">주소</label>
				<div id="tmp-address1" class="col-sm-6">
					<input type="text" name="address1" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-address2" class="form-group">
				<label class="col-sm-2 control-label">상세주소</label>
				<div id="tmp-address2" class="col-sm-6">
					<input type="text" name="address2" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-stLtype" class="form-group">
				<label class="col-sm-2 control-label">기관명</label>
				<div id="tmp-stLtype" class="col-sm-2">
					<input type="text" name="stLtype" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-stMtype" class="form-group">
				<label class="col-sm-2 control-label">동/건물명</label>
				<div class="col-sm-2">
					<input type="text" name="stMtype" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-groundYn" class="form-group">
				<label class="col-sm-2 control-label">층수구분</label>
				<div id="tmp-groundYn" class="col-sm-6">
					{{#.}}
					<label class="radio-inline">
						<input type="radio" name="groundYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
					</label>
					{{/.}}
				</div>
			</div>

			<div id="area-floorNo" class="form-group">
				<label class="col-sm-2 control-label">층수</label>
				<div id="tmp-floorNo" class="col-sm-2">
					<input type="text" name="floorNo" class="form-control input-sm"/>
				</div>
			</div>

			<div id="area-roomNo" class="form-group">
				<label class="col-sm-2 control-label">호실</label>
				<div class="col-sm-2">
					<input type="text" name="roomNo" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-lat" class="form-group">
				<label class="col-sm-2 control-label">경도</label>
				<div class="col-sm-2">
					<input type="text" name="lat" class="form-control input-sm" />
				</div>
			</div>

			<div id="area-lng" class="form-group">
				<label class="col-sm-2 control-label">위도</label>
				<div class="col-sm-2">
					<input type="text" name="lng" class="form-control input-sm" />
				</div>
			</div>
		</div>

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">장비문의처</label>
				<div class="col-sm-2">
					<input type="text" name="operTel" class="form-control input-sm" placeholder="042-865-3483"/>
				</div>
			</div>
		</div>

		<div class="ar">
			<div id="area-rndYn" class="form-group">
				<label class="col-sm-2 control-label">연구시설&middot;장비 <br/>구입재원 구분</label>
				<div id="tmp-rndYn" class="col-sm-8">
					{{#.}}
					<label class="radio-inline">
						<input type="radio" name="rndYn" value="{{code}}" onclick="util.selRnd(this);" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
					</label>
					{{/.}}
				</div>
			</div>

			<div id="divRndYAdd" class="form-group" style="display:none;">
				<label class="col-sm-2 control-label">R&D과제정보</label>
				<div id="" class="col-sm-6">
					<button type="button" onclick="util.rndYAddRow(this,'rndY');" class="btn btn-sm">R&D과제 추가</button>
					<button type="button" onclick="util.rndYAddRow(this,'rndN');" class="btn btn-sm">비R&D과제 추가</button>
				</div>
			</div>

			<div id="divRndNAdd" class="form-group" style="display:none;">
				<label class="col-sm-2 control-label">비R&D과제정보</label>
				<div id="" class="col-sm-6">
					<button type="button" onclick="util.rndYAddRow(this,'rndY');" class="btn btn-sm">R&D과제 추가</button>
					<button type="button" onclick="util.rndYAddRow(this,'rndN');" class="btn btn-sm">비R&D과제 추가</button>
				</div>
			</div>

			<div id="area-rnd-div">
				<div id="area-rndY" class="form-group areaRndY" style="display:none;">
					<div id="divLine" style="display:none;border:0;width:90%;margin:0 auto 15px;height:1px;border-bottom:1px dotted #ccc;box-sizing:border-box"></div>
					<label class="col-sm-2 control-label">과제정보</label>
					<div class="col-sm-10" id="rndYDirect">
						<div class="form-group">
							<label class="col-sm-2 control-label">과제</label>
							<div id="tmp-subjectYn" class="col-sm-6">
								<label class="radio-inline">
									<input type="radio" name="rndList[0].subjectYn" onclick="util.subjectYnSel(this);" value="Y" />선택입력
								</label>
								<label class="radio-inline">
									<input type="radio" name="rndList[0].subjectYn" onclick="util.subjectYnSel(this);" value="N" />직접입력
								</label>
							</div>
						</div>
						<div class="form-group" style="display: none;">
							<input type="hidden" name="rndList[0].rndYn" value="Y"/>
						</div>
						<div id="area-subjectOcd" class="form-group rndSubjectSelect">
							<label class="col-sm-2 control-label">과제검색</label>
							<div class="col-sm-6">
								<input type="text" name="rndList[0].subjectSearchKeywords" data-index="0" class="form-control input-sm" />
								<div id="rslt-subjectOcd" class="table-responsive" style="font-size: 12px; width: 700px;"></div>
							</div>
							<%-- <button type="button" class="btn btn-sm" onclick="util.subjectSearch(this,{pjtNm: $(this).parent().find('input[name*=subjectSearchKeywords]').val()})">검색</button> --%>
							<input type="button" value="검색" onclick="util.subjectSearch(this,{pjtNm: $(this).parent().find('input[name*=subjectSearchKeywords]').val()})" class="btn btn-primary" style="padding:3px 12px;" />
						</div>

						<div id="tmp-rndPrc" class="form-group rndSubjectSelect rndSubjectDirect">
							<label class="col-sm-2 control-label">사용금액</label>
							<div id="tmp-rndPrc" class="col-sm-2">
								<input type="text" name="rndList[0].rndPrc" onblur="util.rndPerDivison(this);" class="form-control input-sm" placeholder = "원"/>
							</div>
						</div>

						<div id="tmp-rndWeight" class="form-group rndSubjectSelect rndSubjectDirect">
							<label class="col-sm-2 control-label">과제비중</label>
							<div id="tmp-rndWeight" class="col-sm-2">
								<input type="text" name="rndList[0].rndWeight" class="form-control input-sm" readonly="readonly" placeholder = "%"/>
							</div>
						</div>

						<div id="area-officeYn" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">과제수행부처&middot;청</label>
							<div class="row">
								<div id="tmp-officeYn" class="col-sm-9">
									{{#.}}
										<label class="radio-inline">
											<input type="radio" name="rndList[0].officeYn" onclick="util.officeYnSel(this);" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
										</label>
									{{/.}}
								</div>
							</div>
						</div>

						<div id="area-officeCd1" class="form-group rndSubjectDirect rndSubjectDirectOffice">
							<label class="col-sm-2 control-label"></label>
							<div id="tmp-officeCd1" class="col-sm-6">
								<select name="rndList[0].officeCd" class="form-control input-sm">
									<option value="">2014년 이후 과제수행부처&middot;청</option>
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>
						<div id="area-officeCd2" class="form-group rndSubjectDirect rndSubjectDirectOffice">
							<label class="col-sm-2 control-label"></label>
							<div id="tmp-officeCd2" class="col-sm-6">
								<select name="rndList[0].officeCd" class="form-control input-sm">
									<option value="">2008년~2012년 과제수행부처&middot;청</option>
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>
						<div id="area-officeCd3" class="form-group rndSubjectDirect rndSubjectDirectOffice">
							<label class="col-sm-2 control-label"></label>
							<div id="tmp-officeCd3" class="col-sm-6">
								<select name="rndList[0].officeCd" class="form-control input-sm">
									<option value="">2008년 이전 과제수행부처&middot;청</option>
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>

						<div id="area-subjectNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">세부과제명</label>
							<div id="tmp-subjectNm" class="col-sm-6">
								<input type="text" name="rndList[0].subjectNm" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-busiNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">세부사업명</label>
							<div id="tmp-busiNm" class="col-sm-6">
								<input type="text" name="rndList[0].busiNm" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-organNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">주관기관</label>
							<div id="tmp-organNm" class="col-sm-6">
									<input type="text" name="rndList[0].organNm" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-directorNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">연구책임자</label>
							<div id="tmp-directorNm" class="col-sm-6">
								<input type="text" name="rndList[0].directorNm" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-startDt" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label" style="padding-left: 0; font-size: 13px;">당해년도과제수행기간</label>
							<div class="col-sm-4">
								<div class="row">
									<div class="col-xs-6">
										<input type="text" name="rndList[0].startDt" class="form-control input-sm" onclick="fnInitCalcToShow('rndList[0].startDt')" placeholder="yyyy-MM-dd" />
									</div>
									<div class="col-xs-6 form-group" style="margin-bottom: 0;">
										<input type="text" name="rndList[0].endDt" class="form-control input-sm" onclick="fnInitCalcToShow('rndList[0].endDt')" placeholder="yyyy-MM-dd" />
									</div>
								</div>
							</div>
						</div>

						<div id="area-porganNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">전문기관</label>
							<div id="tmp-porganNm" class="col-sm-6">
								<input type="text" name="rndList[0].porganNm" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-subjectPcd" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">부처과제고유번호</label>
							<div id="tmp-subjectPcd" class="col-sm-6">
								<input type="text" name="rndList[0].subjectPcd" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-sixTechCd" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">6T기술코드</label>
							<div id="tmp-sixTechCd" class="col-sm-6">
								<input type="text" name="rndList[0].sixTechCd" class="form-control input-sm" />
							</div>
						</div>

						<div id="area-sixCd" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">6T분류</label>
							<div id="tmp-sixCd" class="col-sm-3">
								<select name="rndList[0].sixCd" class="form-control input-sm">
									<option value="">6T분류를 선택하세요.</option>
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>

 		<div class="br">
	 		<div class="form-group">
	 			<label class="col-sm-2 control-label">장비설명</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="feature" class="form-control input-sm"></textarea>
				</div>
	 		</div>

	 		<div class="form-group">
	 			<label class="col-sm-2 control-label">구성 및 성능</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="capability" class="form-control input-sm"></textarea>
				</div>
	 		</div>

	 		<div class="form-group">
	 			<label class="col-sm-2 control-label">사용예</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="example" class="form-control input-sm"></textarea>
				</div>
	 		</div>

	 		<div class="form-group">
				<label class="col-sm-2 control-label">5대 중점투자분야</label>
				<div id="tmp-importCd" class="col-sm-6">
					<select name="importCd" class="form-control input-sm">
						<option value="">5대 중점투자분야를 선택하세요.</option>
						{{#.}}
							<option value="{{code}}">{{name}}</option>
						{{/.}}
					</select>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">장비 담당자</label>
			<div id="tmp-managerId" class="col-sm-6">
				<select name="managerId" class="form-control input-sm">
					<option value="">장비담당자를 선택하세요</option>
					{{#.}}
						<option value="{{code}}">{{name}}({{code}})</option>
					{{/.}}
				</select>
			</div>
		</div>
		<div class="form-group">
 			<label class="col-sm-2 control-label">과학기술인등록번호</label>
			<div class="col-sm-6">
				<input type="text" name="scienceTechRegistNo" class="form-control input-sm"/>
			</div>
 		</div>

		<div class="form-group" style="margin-top: 20px;">
			<div class="col-sm-offset-5 col-sm-2">
				<input type="submit" value="장비 등록" class="btn btn-primary btn-block"/>
			</div>
		</div>
	</form>
</div>



<script type="text/javascript">
//자산조회 콜백
function fnSetKp9080(data) {
	$('input[name="fixedAsetNo"]').val(data.assetNo);
	$('input[name="korNm"]').val(data.assetName);
	$('input[name="engNm"]').val(data.assetEname);
	$('input[name="takeDt"]').val(data.aqusitDt);
	$('input[name="takePrc"]').val(data.aqusitAmt);
	$('input[name="devPrc"]').val(data.aqusitAmt);
}
</script>

</body>
</html>