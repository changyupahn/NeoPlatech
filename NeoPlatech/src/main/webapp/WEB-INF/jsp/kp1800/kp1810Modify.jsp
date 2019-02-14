<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "ZEUS국가연구시설장비 수정";
String curAction = "/kp1800/kp1810Modify.do";
String listAction = "/kp1800/kp1810Read.do";
String modifyAction = "/kp1800/kp1810Modify.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Pragma" content="no-cache" />
	<title><%=pageTitle%></title>

	<script type="text/javascript" src="/common/js/jquery-1.7.1.min.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
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

	<style type="text/css">
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

		$(window).load(function() {

			//var equipId = window.location.href.slice(window.location.href.indexOf('?')).split('=')[1];
			var equipId = util.getParameter("equipId");
			util.equipMod(equipId);

			$('#equipModForm').on('submit', function(e) {
				e.preventDefault();
				if(confirm('수정 하시겠습니까?')) {
					$(this).ajaxSubmit({
						//url: api.getEquipsUrl(equipId),
						url: "/kp1800/kp1810ModifyProc.do?equipId=" + equipId,
						dataType: "json",
						async: false,
						beforeSerialize : function($form, options){
							$("input:radio[name='rndYn']").removeAttr('disabled');

							/** 부처청 선택에 따른 다른 선택값 삭제 */
							var subjectYnCnt = $("input[name*='subjectYn'][value='N']:checked").length;
							var officeYnCnt = $("input[name*='officeYn']:checked").length;
							if(subjectYnCnt > 0 && officeYnCnt > 0){
								for(var i=0; i<subjectYnCnt; i++){
									var officeYnVal = $("input[name*='officeYn']:checked").val();
									$("input[name*='officeYn']").each(function(idx){
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
								alert("정상적으로 수정되었습니다.");
								location.reload();
							}
						}
					});
				}
			});

		});

	</script>
</head>
<body>

<div class="container">
	<form name="equipModForm" id="equipModForm" method="post" enctype="multipart/form-data" class="form-horizontal">
	<input type="hidden" name="_method" value="PUT"/>
	<input type="hidden" id="confirmYn" value="{{confirmYn}}" />
	<div id="templateMod">
		<div class="header page-header">
			<ul class="nav nav-pills pull-right">
				<li class="active">
					<button class="btn btn-primary btn-block" type="submit">&nbsp;&nbsp;연구시설장비 수정&nbsp;&nbsp;</button>
				</li>
			</ul>
			<h3 class="text-muted"><%=pageTitle%></h3>
		</div>

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

			<div id="area-cpId" class="form-group">
				<label class="col-sm-2 control-label">주장비 검색</label>
				<div class="col-sm-6">
					<input type="text" name="mainEquipSearchKeywords" class="form-control input-sm" />
					<div id="rslt-cpId" class="table-responsive" style="font-size: 12px;"></div>
					{{#if cpId}}
						<table class="table_type02" id="cpIdDev">
							<colgroup>
								<col style="width:10%;"/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th scope="row" class="btl">선택</th>
									<th scope="row" class="btl">주장비명(영문명)</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td><input type="radio" name="cpId" value="{{cpId}}" title="{{cpId}}" checked/></td>
								<td>{{mainKorNm}}({{mainEngNm}})</td>
								</tr>
							</tbody>
						</table>
					{{/if}}
				</div>
				<button type="button" class="btn btn-sm" onclick="util.mainEquipSearch({korNm: $('input[name*=mainEquipSearchKeywords]').val()})">검색</button>
			</div>
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
						<button type="button" class="btn btn-sm" onclick="util.redSearch({rfdEquipNo : $('input[name=rfdEquipNoKeyword]').val(), rfdEquipNm : $('input[name=rfdEquipNmKeyword]').val(), projDetailName : $('input[name=projDetailNameKeyword]').val(), reqInstName : $('input[name=reqInstNameKeyword]').val(), projectAnalysisName : $('input[name=projectAnalysisNameKeyword]').val(), analysisInstName : $('input[name=analysisInstNameKeyword]').val()})">검색</button>
					</div>

					<div id="rslt-red" class="table-responsive" style="font-size: 12px;"></div>
					{{#if rfdEquipNo}}
						<table class="table_type02" style="width:530px;" id="redDiv">
							<colgroup>
								<col style="width:10%;"/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th scope="row" class="btl">선택</th>
									<th scope="row" class="btl">심의번호</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td><input type="radio" name="rfdEquipNo" value="{{rfdEquipNo}}" title="{{rfdEquipNo}}" checked/></td>
								<td>{{rfdEquipNo}}</td>
								</tr>
							</tbody>
						</table>
					{{/if}}
				</div>

			</div>
		</div>

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">고정자산관리번호</label>
				<div class="col-sm-6">
					<input type="text" name="fixedAsetNo" class="form-control input-sm" value="{{fixedAsetNo}}"/>
				</div>
				<button type="button" class="btn btn-sm" onclick="util.validFixedAsetNo($('input[name=fixedAsetNo]').val(), '{{equipId}}')">이중등록확인</button>
			</div>

			<div class="form-group" >
				<label class="col-sm-2 control-label">한글명</label>
				<div class="col-sm-6">
					<input type="text" name="korNm" class="form-control input-sm" value="{{korNm}}"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">영문명</label>
				<div class="col-sm-6">
					<input type="text" name="engNm" class="form-control input-sm" value="{{engNm}}"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">장비사진</label>
				<div class="col-sm-6">
					<input type="file" name="fileData" class="form-control input-sm" />{{photoWebPath}}
				</div>
			</div>

		</div>

		<div class="ar">
			<div class="form-group">
				<label class="col-sm-2 control-label">모델</label>
				<div id="tmp-modelYn" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="modelYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
						</label>
					{{/.}}
				</div>
			</div>

			<div id="area-modelCd" class="form-group">
				<label class="col-sm-2 control-label">모델검색</label>
				<div id="tmp-modelCd" class="col-sm-6">
					<input type="text" name="modelSearchKeywords" class="form-control input-sm"/>
					<div id="rslt-modelCd" class="table-responsive" style="font-size: 12px;"></div>
					<table class="table_type02" id="modelDiv">
						<colgroup>
							<col style="width:10%;"/>
							<col/>
							<col/>
						</colgroup>
						<thead>
							<tr>
								<th scope="row" class="btl">선택</th>
								<th scope="row" class="btl">모델명</th>
								<th scope="row" class="btl">제작사명</th>
							</tr>
						</thead>
						<tbody>
							<tr>
							<td><input type="radio" name="modelCd" value="{{modelCd}}" checked/></td>
							<td>{{modelNm}}</td>
							<td>{{manufactureNm}}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<button type="button" class="btn btn-sm" onclick="util.modelSearch({modelNm: $('input[name=modelSearchKeywords]').val()})">검색</button>
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
					{{#.}}
						<option value="{{code}}">{{name}}</option>
					{{/.}}
				</select>
				</div>
			</div>
			<div id="area-manufactureNm" class="form-group">
				<label class="col-sm-2 control-label">제작사명</label>
				<div id="tmp-manufactureNm" class="col-sm-6">
					<input type="text" name="manufactureNm" class="form-control input-sm"/>
				</div>
			</div>
			<div id="area-modelNm" class="form-group">
				<label class="col-sm-2 control-label">모델명</label>
				<div id="tmp-modelNm" class="col-sm-6">
					<input type="text" name="modelNm" class="form-control input-sm"/>
				</div>
			</div>
			<div id="area-branchCd" class="form-group">
				<label class="col-sm-2 control-label">표준분류</label>
				<div id="tmp-branchCd" class="col-sm-6">
					<select name="branchCd" class="form-control input-sm" data-fields='{{fields.jsonArrayToString}}'>
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
					<input type="text" name="takeDt" class="form-control input-sm" placeholder="ex) xxxx-xx-xx" value="{{takeDt}}"/>
				</div>
			</div>

			<div id="area-takePrc" class="form-group">
				<label class="col-sm-2 control-label">취득금액</label>
				<div id="tmp-takePrc" class="col-sm-2">
					<input type="text" name="takePrc" class="form-control input-sm" placeholder = "원" value="{{takePrc}}"/>
				</div>
			</div>

			<div id="area-devSdt" class="form-group">
				<label class="col-sm-2 control-label">개발일자</label>
				<div id="tmp-devSdt" class="col-sm-4">
					<div class="row">
						<div class="col-xs-6">
							<input type="text" name="devSdt" class="form-control input-sm" placeholder="From" value="{{devSdt}}"/>
						</div>
						<div id="area-devEdt" class="form-group">
							<div class="col-xs-6">
								<input type="text" name="devEdt" class="form-control input-sm" placeholder="To" value="{{devEdt}}"/>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div id="area-devPrc" class="form-group">
				<label class="col-sm-2 control-label">개발금액</label>
				<div id="tmp-devPrc" class="col-sm-2">
					<input type="text" name="devPrc" class="form-control input-sm" placeholder = "원" value="{{devPrc}}"/>
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
					<input type="text" name="devSpec" class="form-control input-sm" placeholder = "%" value="{{devSpec}}"/>
				</div>
			</div>

			<div id="area-buildSdt" class="form-group">
				<label class="col-sm-2 control-label">구축일자</label>
				<div id="tmp-buildSdt" class="col-sm-4">
					<div class="row">
						<div class="col-xs-6">
							<input type="text" name="buildSdt" class="form-control input-sm" placeholder="From" value="{{buildSdt}}"/>
						</div>
						<div id="area-buildEdt" class="form-group">
							<div class="col-xs-6">
								<input type="text" name="buildEdt" class="form-control input-sm" placeholder="To" value="{{buildEdt}}"/>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div id="area-buildPrcDom" class="form-group">
				<label class="col-sm-2 control-label">국산금액</label>
				<div id="tmp-buildPrcDom" class="col-sm-2">
					<input type="text" name="buildPrcDom" class="form-control input-sm" placeholder = "원" value="{{buildPrcDom}}"/>
				</div>
			</div>

			<div id="area-buildPrcFor" class="form-group">
				<label class="col-sm-2 control-label">외산금액</label>
				<div id="tmp-buildPrcFor" class="col-sm-2">
					<input type="text" name="buildPrcFor" class="form-control input-sm" placeholder = "원" value="{{buildPrcFor}}"/>
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

		<div class="ar" id="area-useScopeCd">
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
						<input type="text" name="useScopeReasonEtc" class="form-control input-sm" value="{{useScopeReasonEtc}}"/>
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

		<div class="ar" id="area-useTypeCd">
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
					<input type="text" name="useTypeEtc" class="form-control input-sm" value="{{useTypeEtc}}"/>
				</div>
			</div>
		</div>

		<div class="ar" id="area-idleDisuseCd">
			<div class="form-group">
				<label class="col-sm-2 control-label">장비상태</label>
				<div id="tmp-idleDisuseCd" class="col-sm-6">
					{{#.}}
						<label class="radio-inline">
							<input type="radio" name="idleDisuseCd" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
						</label>
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
						<input type="text" name="saleDt" class="form-control input-sm" value="{{saleDt}}"/>
					</div>
				</div>

				<div id="area-recyclingDt" class="form-group">
					<label class="col-sm-2 control-label">재활용일자(불용판정)</label>
					<div id="tmp-recyclingDt" class="col-sm-2">
						<input type="text" name="recyclingDt" class="form-control input-sm" value="{{recyclingDt}}"/>
					</div>
				</div>

				<div id="area-disposalDt" class="form-group">
					<label class="col-sm-2 control-label">폐기일자(불용판정)</label>
					<div id="tmp-disposalDt" class="col-sm-2">
						<input type="text" name="disposalDt" class="form-control input-sm" value="{{disposalDt}}"/>
					</div>
				</div>

				<div id="area-transferOrganCd" class="form-group">
					<label class="col-sm-2 control-label">양여기관(불용판정)</label>
					<div id="tmp-transferOrganCd" class="col-sm-6">
						<input type="text" name="grantOrganSearchKeywords" class="form-control input-sm"/>
						<div id="rslt-transferOrganCd" class="table-responsive" style="font-size: 12px;"></div>
						{{#if transferOrganCd}}
						<table class="table_type02" style="width:530px;" id="transferOrganDiv">
							<colgroup>
								<col style="width:10%;"/>
								<col/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th scope="row" class="btl">선택</th>
									<th scope="row" class="btl">양여기관</th>
									<th scope="row" class="btl">양여기관코드</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td><input type="radio" name="transferOrganCd" value="{{transferOrganCd}}" checked/></td>
								<td>{{transferOrganNm}}</td>
								<td>{{transferOrganCd}}</td>
								</tr>
							</tbody>
						</table>
						{{/if}}
					</div>
					<button type="button" class="btn btn-sm" onclick="util.grantOrganSearch(this,{organNm: $(this).parent().find('input[name=grantOrganSearchKeywords]').val()})">검색</button>
				</div>
				<div id="area-transferDt" class="form-group">
					<label class="col-sm-2 control-label">양여일자(불용판정)</label>
					<div id="tmp-transferDt" class="col-sm-2">
						<input type="text" name="transferDt" class="form-control input-sm" value="{{transferDt}}"/>
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
						<input type="text" name="saleComDt" class="form-control input-sm" value="{{saleComDt}}"/>
					</div>
				</div>

				<div id="area-recyclingComDt" class="form-group">
					<label class="col-sm-2 control-label">재활용일자(처분완료)</label>
					<div id="tmp-recyclingComDt" class="col-sm-2">
						<input type="text" name="recyclingComDt" class="form-control input-sm" value="{{recyclingComDt}}"/>
					</div>
				</div>

				<div id="area-disposalComDt" class="form-group">
					<label class="col-sm-2 control-label">폐기일자(처분완료)</label>
					<div id="tmp-disposalComDt" class="col-sm-2">
						<input type="text" name="disposalComDt" class="form-control input-sm" value="{{disposalComDt}}"/>
					</div>
				</div>

				<div id="area-transferComOrganCd" class="form-group">
					<label class="col-sm-2 control-label">양여기관(처분완료)</label>
					<div id="tmp-transferComOrganCd" class="col-sm-6">
						<input type="text" name="grantOrganSearchKeywords" class="form-control input-sm"/>
						<div id="rslt-transferOrganCd" class="table-responsive" style="font-size: 12px;"></div>
						{{#if transferOrganCd}}
						<table class="table_type02" style="width:530px;" id="transferOrganDiv">
							<colgroup>
								<col style="width:10%;"/>
								<col/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th scope="row" class="btl">선택</th>
									<th scope="row" class="btl">양여기관</th>
									<th scope="row" class="btl">양여기관코드</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td><input type="radio" name="transferComOrganCd" value="{{transferComOrganCd}}" checked/></td>
								<td>{{transferOrganNm}}</td>
								<td>{{transferOrganCd}}</td>
								</tr>
							</tbody>
						</table>
						{{/if}}
					</div>
					<button type="button" class="btn btn-sm" onclick="util.grantComOrganSearch(this, {organNm: $(this).parent().find('input[name=grantOrganSearchKeywords]').val()})">검색</button>
				</div>
				<div id="area-transferComDt" class="form-group">
					<label class="col-sm-2 control-label">양여일자(처분완료)</label>
					<div id="tmp-transferComDt" class="col-sm-2">
						<input type="text" name="transferComDt" class="form-control input-sm" value="{{transferComDt}}"/>
					</div>
				</div>
			</div>
		</div>

		<div class="ar" id="area-setupYn">
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
			<div id="area-setupId">
				<div class="form-group">
					<label class="col-sm-2 control-label">설치장소 검색</label>
					<div id="tmp-setupId" class="col-sm-6">
						<input type="text" name="setupSearchKeywords" class="form-control input-sm"/>
						<div id="rslt-setupId" class="table-responsive" style="font-size: 12px;"></div>
						<table class="table_type02" id="setupDiv">
							<colgroup>
								<col style="width:10%;"/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th scope="row" class="btl">선택</th>
									<th scope="row" class="btl">설치장소</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td><input type="radio" name="setupId" value="{{setupId}}" checked/></td>
								<td>{{location}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<button type="button" class="btn btn-sm" onclick="util.setupSearch({address: $('input[name=setupSearchKeywords]').val()})">검색</button>
				</div>
			</div>

			<div id="area-areaCd"  class="form-group">
				<label class="col-sm-2 control-label">지역구분</label>
				<div id="tmp-areaCd" class="col-sm-2">
					<select name="areaCd" class="form-control input-sm">
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
				<button type="button" class="btn btn-sm" onclick="util.zipSearch({keyword: $('input[name=zipSearchKeywords]').val()})">검색</button>
			</div>
			<div id="area-address1" class="form-group">
				<label class="col-sm-2 control-label">주소</label>
				<div id="tmp-address1" class="col-sm-6">
					<input type="text" name="address1" class="form-control input-sm" />
				</div>
			</div>
			<div id= "area-address2" class="form-group">
				<label class="col-sm-2 control-label">상세주소</label>
				<div id="tmp-address2" class="col-sm-6">
					<input type="text" name="address2" class="form-control input-sm" />
				</div>
			</div>
			<div id= "area-stLtype" class="form-group">
				<label class="col-sm-2 control-label">기관명</label>
				<div id="tmp-stLtype" class="col-sm-2">
					<input type="text" name="stLtype" class="form-control input-sm" />
				</div>
			</div>
			<div id= "area-stMtype" class="form-group">
				<label class="col-sm-2 control-label">동/건물명</label>
				<div class="col-sm-2">
					<input type="text" name="stMtype" class="form-control input-sm" />
				</div>
			</div>

			<div id= "area-groundYn" class="form-group">
				<label class="col-sm-2 control-label">층수구분</label>
				<div id="tmp-groundYn" class="col-sm-6">
					{{#.}}
					<label class="radio-inline">
						<input type="radio" name="groundYn" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
					</label>
					{{/.}}
				</div>
			</div>
			<div id= "area-floorNo" class="form-group">
				<label class="col-sm-2 control-label">층수</label>
				<div id="tmp-floorNo" class="col-sm-2">
					<input type="text" name="floorNo" class="form-control input-sm" value="{{floorNo}}"/>
				</div>
			</div>
			<div id= "area-roomNo" class="form-group">
				<label class="col-sm-2 control-label">호실</label>
				<div class="col-sm-2">
					<input type="text" name="roomNo" class="form-control input-sm" value="{{roomNo}}"/>
				</div>
			</div>
			<div id= "area-lat" class="form-group">
				<label class="col-sm-2 control-label">경도</label>
				<div class="col-sm-2">
					<input type="text" name="lat" class="form-control input-sm" />
				</div>
			</div>
			<div id= "area-lng" class="form-group">
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
					<input type="text" name="operTel" class="form-control input-sm" placeholder="ex) 000-0000-0000" value="{{operTel}}"/>
				</div>
			</div>
 		</div>

		<div class="ar">
			<div id = "area-rndYn" class="form-group">
				<label class="col-sm-2 control-label">연구시설&middot;장비 <br/>구입재원 구분</label>
				<div id="tmp-rndYn" class="col-sm-6">
					{{#.}}
					<label class="radio-inline">
						<input type="radio" name="rndYn" value="{{code}}" onclick="util.selRnd(this);" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}' /> {{name}}
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
				{{#equipRndList}}
				<div id="area-rndY" class="form-group areaRndY">
					{{#if_equal @index '0'}}
					<div id="divLine" style="display:none;border:0;width:90%;margin:0 auto 15px;height:1px;border-bottom:1px dotted #ccc;box-sizing:border-box"></div>
					{{else}}
					<div id="divLine" style="display:block;border:0;width:90%;margin:0 auto 15px;height:1px;border-bottom:1px dotted #ccc;box-sizing:border-box"></div>
					{{/if_equal}}
					{{#if rndAcq}}
					{{else}}
					<label class="col-sm-2 control-label">과제정보</label>
					<div class="col-sm-10" id="rndYDirect">
						<div class="form-group">
							<label class="col-sm-2 control-label" data-index="{{@index}}">과제</label>
							<div id="tmp-subjectYn" class="col-sm-6">
								<label class="radio-inline">
									<input type="radio" name="rndList[{{@index}}].subjectYn" onclick="util.subjectYnSel(this);" value="Y" /> 선택입력
								</label>
								<label class="radio-inline">
									<input type="radio" name="rndList[{{@index}}].subjectYn" onclick="util.subjectYnSel(this);" value="N" /> 직접입력
								</label>
							</div>
						</div>
						<div class="form-group" style="display: none;">
							<input type="hidden" name="rndList[{{@index}}].rndYn" value="{{rndYn}}"/>
						</div>

						<div id="area-subjectOcd" class="form-group rndSubjectSelect">
							<label class="col-sm-2 control-label">과제검색</label>
							<div class="col-sm-6">
								<input type="text" name="rndList[{{@index}}].subjectSearchKeywords" data-index="{{@index}}" class="form-control input-sm"/>
								<div id="rslt-subjectOcd" class="table-responsive" style="width:700px; font-size: 12px;"></div>
								<table class="table_type02" style="width:700px" id="subjectDiv">
									<colgroup>
										<col style="width:10%;"/>
										<col />
										<col style="width:25%;"/>
										<col style="width:15%;"/>
									</colgroup>
									<thead>
										<tr>
											<th scope="row" class="btl">선택</th>
											<th scope="row" class="btl">과제명</th>
											<th scope="row" class="btl">기관명</th>
											<th scope="row" class="btl">총괄책임자명</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="radio" name="rndList[{{@index}}].subjectOcd" value="{{subjectOcd}}" checked/></td>
											<td>{{subjectNm}}</td>
											<td>{{organNm}}</td>
											<td>{{directorNm}}</td>
										</tr>
									</tbody>
								</table>
							</div>
							<button type="button" class="btn btn-sm" onclick="util.subjectSearch(this,{pjtNm: $(this).parent().find('input[name*=subjectSearchKeywords]').val()})">검색</button>
						</div>

						<div id="tmp-rndPrc" class="form-group rndSubjectSelect rndSubjectDirect">
							<label class="col-sm-2 control-label">사용금액</label>
							<div id="tmp-rndPrc" class="col-sm-2">
								<input type="text" name="rndList[{{@index}}].rndPrc" onblur="util.rndPerDivison(this);" class="form-control input-sm" placeholder = "원" value="{{rndPrc}}"/>
							</div>
						</div>

						<div id="tmp-rndWeight" class="form-group rndSubjectSelect rndSubjectDirect">
							<label class="col-sm-2 control-label">과제비중</label>
							<div id="tmp-rndWeight" class="col-sm-2">
								<input type="text" name="rndList[{{@index}}].rndWeight" class="form-control input-sm" readonly="readonly" placeholder = "%" value="{{rndWeight}}"/>
							</div>
						</div>

						<div id="area-officeYn" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">과제수행부처&middot;청</label>
							<div class="row">
								<div id="tmp-officeYn" class="col-sm-9 officeYn{{@index}}">
									{{#.}}
										<label class="radio-inline">
											<input type="radio" name="officeYn" onclick="util.officeYnSel(this);" value="{{code}}" data-def="{{def}}" data-fields='{{fields.jsonArrayToString}}'/> {{name}}
										</label>
									{{/.}}
								</div>
							</div>
						</div>

						<div id="area-officeCd1" class="form-group rndSubjectDirect rndSubjectDirectOffice">
							<label class="col-sm-2 control-label" data-index="{{@index}}"></label>
							<div id="tmp-officeCd1" class="col-sm-6">
								<select name="rndList[{{@index}}].officeCd" class="form-control input-sm officeCd1">
									<option value="111">2014년 이후 과제수행부처&middot;청</option>
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>
						<div id="area-officeCd2" class="form-group rndSubjectDirect rndSubjectDirectOffice">
							<label class="col-sm-2 control-label" data-index="{{@index}}"></label>
							<div id="tmp-officeCd2" class="col-sm-6">
								<select name="rndList[{{@index}}].officeCd" class="form-control input-sm officeCd2">
									<option value="">2008년~2012년 과제수행부처&middot;청</option>
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>
						<div id="area-officeCd3" class="form-group rndSubjectDirect rndSubjectDirectOffice">
							<label class="col-sm-2 control-label" data-index="{{@index}}"></label>
							<div id="tmp-officeCd3" class="col-sm-6">
								<select name="rndList[{{@index}}].officeCd" class="form-control input-sm officeCd3">
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
								<input type="text" name="rndList[{{@index}}].subjectNm" class="form-control input-sm" value="{{subjectNm}}" />
							</div>
						</div>
						<div id="area-busiNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">세부사업명</label>
							<div id="tmp-busiNm" class="col-sm-6">
								<input type="text" name="rndList[{{@index}}].busiNm" class="form-control input-sm" value="{{busiNm}}"/>
							</div>
						</div>
						<div id="area-organNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">주관기관</label>
							<div id="tmp-organNm" class="col-sm-6">
									<input type="text" name="rndList[{{@index}}].organNm" class="form-control input-sm" value="{{organNm}}" />
							</div>
						</div>
						<div id="area-directorNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">연구책임자</label>
							<div id="tmp-directorNm" class="col-sm-6">
								<input type="text" name="rndList[{{@index}}].directorNm" class="form-control input-sm" value="{{directorNm}}" />
							</div>
						</div>
						<div id="area-startDt" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label" style="padding-left: 0; font-size: 13px;">당해년도 과제수행기간</label>
							<div id="tmp-startDt" class="col-sm-4">
								<div class="row">
									<div class="col-xs-6">
										<input type="text" name="rndList[{{@index}}].startDt" class="form-control input-sm" placeholder="From" value="{{startDt}}" />
									</div>
									<div class="col-xs-6">
										<input type="text" name="rndList[{{@index}}].endDt" class="form-control input-sm" placeholder="To" value="{{endDt}}" />
									</div>
								</div>
							</div>
						</div>
						<div id="area-porganNm" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">전문기관</label>
							<div id="tmp-porganNm" class="col-sm-6">
								<input type="text" name="rndList[{{@index}}].porganNm" class="form-control input-sm"  value="{{porganNm}}"/>
							</div>
						</div>
						<div id="area-subjectPcd" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">부처과제고유번호</label>
							<div id="tmp-subjectPcd" class="col-sm-6">
								<input type="text" name="rndList[{{@index}}].subjectPcd" class="form-control input-sm"  value="{{subjectPcd}}"/>
							</div>
						</div>
						<div id="area-sixTechCd" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label">6T기술코드</label>
							<div id="tmp-sixTechCd" class="col-sm-6">
								<input type="text" name="rndList[{{@index}}].sixTechCd" class="form-control input-sm"  value="{{sixTechCd}}"/>
							</div>
						</div>
						<div id="area-sixCd" class="form-group rndSubjectDirect">
							<label class="col-sm-2 control-label" data-index="{{@index}}">6T분류</label>
							<div id="tmp-sixCd" class="col-sm-2">
								<select name="rndList[{{@index}}].sixCd" class="form-control input-sm">
									{{#.}}
										<option value="{{code}}">{{name}}</option>
									{{/.}}
								</select>
							</div>
						</div>
					</div>
					{{/if}}
				</div>
				{{/equipRndList}}
				{{#equipRndList}}
					{{#if rndAcq}}
					<div id="area-rndN" class="form-group areaRndN">
						<label class="col-sm-2 control-label">과제정보</label>
						<div class="col-sm-10">
							<div class="form-group" style="display: none;"><input type="hidden" name="rndList[{{@index}}].rndYn" value="N"/></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">취득재원</label>
								<div class="col-sm-6">
									<input type="text" name="rndList[{{@index}}].rndAcq" class="form-control input-sm" value="{{rndAcq}}"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">집행금액</label>
								<div class="col-sm-6">
									<input type="text" name="rndList[{{@index}}].rndPrc" class="form-control input-sm" onblur="util.rndPerDivison(this);" placeholder="(원)" value="{{rndPrc}}" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">과제비중</label>
								<div class="col-sm-6">
									<input type="text" name="rndList[{{@index}}].rndWeight" class="form-control input-sm" placeholder="%" readonly="readonly" value="{{rndWeight}}" />
								</div>
							</div>
						</div>
					</div>
					{{/if}}
				{{/equipRndList}}
			</div>
		</div>

		<div class="br">
			<div class="form-group">
				<label class="col-sm-2 control-label">장비설명</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="feature" class="form-control input-sm" >{{feature}}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">구성 및 성능</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="capability" class="form-control input-sm">{{capability}}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">사용예</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="example" class="form-control input-sm">{{example}}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">5대 중점투자분야</label>
				<div id="tmp-importCd" class="col-sm-6">
					<select name="importCd" class="form-control input-sm">
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

		{{#if_equal statusCd '05'}}
		<div class="ar">
	 		<div class="form-group">
	 			<label class="col-sm-2 control-label">반려 소명사유</label>
				<div class="col-sm-6">
					<textarea rows="5" cols="20" name="reqReason" class="form-control input-sm"></textarea>
				</div>
	 		</div>
		</div>
		{{/if_equal}}

		<div class="form-group">
 			<label class="col-sm-2 control-label">과학기술인등록번호</label>
			<div class="col-sm-6">
				<input type="text" name="scienceTechRegistNo" class="form-control input-sm"/>
			</div>
 		</div>

		<div class="form-group" style="margin-top: 20px;">
			<div class="col-sm-offset-5 col-sm-2">
				<button class="btn btn-primary btn-block" type="submit">연구시설장비 수정</button>
			</div>
		</div>
		<input type="hidden" name="organCd"  value="{{organCd}}"/>
	</div>
	</form>
</div>
</body>
</html>