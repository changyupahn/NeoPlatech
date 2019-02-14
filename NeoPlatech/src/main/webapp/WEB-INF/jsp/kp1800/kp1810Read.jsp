<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "ZEUS국가연구시설장비 조회";
String curAction = "/kp1800/kp1810Read.do";
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
		.ar { background-color: #F1F1F1; padding: 15px 0 1px 0; border-radius: 10px; }
		.br { padding: 15px 0 1px 0; }
	</style>

	<script type="text/javascript">

		//var equipId = window.location.href.slice(window.location.href.indexOf('?')).split('=')[1];
		var equipId = util.getParameter("equipId");

		$(window).load(function() {
			util.equipRead(equipId);
		});

		function openEquipDivision(equipId, options) {
			var url = api.getEquipsUrl(equipId, options);
			if(options.returnType && options.returnType == 'down') {
				location.href = url;
			}
			else window.open(url);
		}

		function deleteEquip() {
			if(confirm('삭제 하시겠습니까?')) {
				$(this).ajaxSubmit({
					//url: api.getEquipsUrl(equipId),
					url: "/kp1800/kp1810DeleteProc.do?equipId=" + equipId,
					type: 'POST',
					data: {_method: 'DELETE'},
					dataType: "json",
					success: function(data) {
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
							alert("정상적으로 삭제되었습니다.");
							parent.fnGridList();
							parent.fnCloseLayerPopup();

						}
					}
				});
			}
		}

	</script>
</head>
<body>

<div class="container" id="template">
	<div class="header page-header">
		<ul class="nav nav-pills pull-right">
			<li><a href="javascript:util.pageMove('mod');"><strong>수정</strong></a></li>
			<li><a id="liDeleteBtn" href="javascript:void(0);" onclick="deleteEquip()"><strong>삭제</strong></a></li>
		</ul>
		<h3 class="text-muted"><%=pageTitle%></h3>
	</div>
		{{#if equipNo}}
			<div class="pull-right" style="margin: 15px 15px 0 0;">
				<button type="button" class="btn btn-warning btn-xs" onclick="openEquipDivision('{{equipId}}', {division: 'qrcode', width: 150, height: 150});">QRCODE <span style="font-size: 10px;">(150x150)</span></button>
				<button type="button" class="btn btn-warning btn-xs" onclick="openEquipDivision('{{equipId}}', {division: 'qrcode', width: 100, height: 100});">QRCODE <span style="font-size: 10px;">(100x100)</span></button>
				<button type="button" class="btn btn-warning btn-xs" onclick="openEquipDivision('{{equipId}}', {division: 'certificate', returnType: 'down'});">장비등록증 <span style="font-size: 10px;">(DOWN)</span></button>
				<button type="button" class="btn btn-warning btn-xs" onclick="openEquipDivision('{{equipId}}', {division: 'certificate', returnType: 'pdf'});">장비등록증 <span style="font-size: 10px;">(PDF)</span></button>
			</div>
		{{else}}
			<div class="pull-right" style="margin: 15px 15px 0 0;">
				<button class="btn btn-danger btn-xs disabled">미승인된 장비(장비등록번호가 발급되지 않은 장비)는 QRCODE 또는 장비등록증을 출력할 수 없습니다.</button>
			</div>
		{{/if}}
		<div class="ar">
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비등록번호</label>
				<p class="form-control-static">{{equipNo}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">고정자산관리번호</label>
				<p class="form-control-static">{{fixedAsetNo}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">한글장비명</label>
				<p class="form-control-static">{{korNm}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">영문장비명</label>
				<p class="form-control-static">{{engNm}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비사진(썸네일 URL)</label>

				<p class="form-control-static">{{photoThumbWebPath}}</p>

			</div>


			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비사진(원본 URL)</label>

				<p class="form-control-static">{{photoWebPath}}</p>

			</div>

		</div>

		<div class="br">
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">제작사</label>
				<p class="form-control-static">{{manufactureNm}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">모델명</label>
				<p class="form-control-static">{{modelNm}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">표준분류</label>
				<p class="form-control-static">{{branchNm}}</p>
			</div>
		</div>
		<div class="ar">
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">취득방법</label>
				<p class="form-control-static">{{takeNm}}</p>
			</div>
			{{#if_equal equipCd '01'}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">취득일자</label>
				<p class="form-control-static">{{takeDt}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">취득금액</label>
				<p class="form-control-static">{{takePrc}}원</p>
			</div>
			{{/if_equal}}
			{{#if_equal equipCd '02'}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">개발일자</label>
				<p class="form-control-static">{{devSdt}} ~ {{devEdt}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">개발금액</label>
				<p class="form-control-static">{{devPrc}}원</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">개발장비비중</label>
				<p class="form-control-static">{{devSpec}}%</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">개발장비공개여부</label>
				<p class="form-control-static">{{devOpenYn}}</p>
			</div>
			{{/if_equal}}
			{{#if_equal equipCd '03'}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">구축일자</label>
				<p class="form-control-static">{{buildSdt}} ~ {{buildEdt}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">국산금액</label>
				<p class="form-control-static">{{buildPrcDom}}원</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">외산금액</label>
				<p class="form-control-static">{{buildPrcFor}}원</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">구성요소</label>
				<p class="form-control-static">{{buildCpNm}}</p>
			</div>
			{{/if_equal}}
		</div>
		<div class="br">
			{{#if_equal registCd '1'}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">활용범위</label>
				<p class="form-control-static">{{useScopeNm}}</p>
			</div>
			{{#if_equal useScopeCd '1'}}
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">단독활용사유</label>
					<p class="form-control-static">{{useScopeReasonNm}}</p>
				</div>
				{{#if_equal useScopeReasonCd '09'}}
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">단독활용기타사유</label>
					<p class="form-control-static">{{useScopeReasonEtc}}</p>
				</div>
				{{/if_equal}}
			{{/if_equal}}
			{{#if_equal useScopeCd '2'}}
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">공동활용범위</label>
					<p class="form-control-static">{{useScopeRangeNm}}</p>
				</div>
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">공동활용방법</label>
					<p class="form-control-static">{{useScopeMeanNm}}</p>
				</div>
			{{/if_equal}}
			{{#if_equal useScopeCd '3'}}
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">공동활용범위</label>
					<p class="form-control-static">{{useScopeRangeNm}}</p>
				</div>
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">공동활용방법</label>
					<p class="form-control-static">{{useScopeMeanNm}}</p>
				</div>
			{{/if_equal}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비용도</label>
				<p class="form-control-static">{{useTypeNm}}</p>
			</div>
				{{#if_equal useTypeCd '06'}}
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label">장비용도사유</label>
					<p class="form-control-static">{{useTypeEtc}}</p>
				</div>
				{{/if_equal}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비상태</label>
				<p class="form-control-static">{{idleDisuseNm}}</p>
			</div>
			{{#if_equal idleDisuseCd '4'}}
				{{#if_equal disuseCd '01'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">매각일자(불용판정)</label>
						<p class="form-control-static">{{saleDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseCd '02'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">재활용일자(불용판정)</label>
						<p class="form-control-static">{{recyclingDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseCd '03'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">폐기일자(불용판정)</label>
						<p class="form-control-static">{{disposalDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseCd '04'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">양여기관(불용판정)</label>
						<p class="form-control-static">{{transferOrganNm}}</p>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">양여일자(불용판정)</label>
						<p class="form-control-static">{{transferDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseCd '05'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">대여기관(불용판정)</label>
						<p class="form-control-static">{{lendOrganNm}}</p>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">대여일자(불용판정)</label>
						<p class="form-control-static">{{lendDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseComCd '01'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">매각일자(처분완료)</label>
						<p class="form-control-static">{{saleComDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseComCd '02'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">재활용일자(처분완료)</label>
						<p class="form-control-static">{{recyclingComDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseComCd '03'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">폐기일자(처분완료)</label>
						<p class="form-control-static">{{disposalComDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseComCd '04'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">양여기관(처분완료)</label>
						<p class="form-control-static">{{transferComOrganNm}}</p>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">양여일자(처분완료)</label>
						<p class="form-control-static">{{transferComDt}}</p>
					</div>
				{{/if_equal}}
				{{#if_equal disuseComCd '05'}}
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">대여기관(처분완료)</label>
						<p class="form-control-static">{{lendOrganNm}}</p>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label">대여일자(처분완료)</label>
						<p class="form-control-static">{{lendDt}}</p>
					</div>
				{{/if_equal}}
			{{/if_equal}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">설치장소</label>
				<p class="form-control-static">{{location}}</p>
			</div>
			{{else}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">주장비</label>
				<p class="form-control-static">
					<a href="javascript:util.pageMove('read');">{{mainKorNm}}({{mainEngNm}})</a>
				</p>
			</div>
			{{/if_equal}}
		</div>
		{{#equipRndList}}
		<div class="ar">
			{{#if_equal @index '0'}}
			<div id="divLine" style="display:none;border:0;width:98%;margin:0 auto 15px;height:1px;border-bottom:1px dotted #ccc;box-sizing:border-box"></div>
			{{else}}
			<div id="divLine" style="display:block;border:0;width:98%;margin:0 auto 15px;height:1px;border-bottom:1px dotted #ccc;box-sizing:border-box"></div>
			{{/if_equal}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">과제번호</label>
				<p class="form-control-static " id="subjectOcd">{{subjectOcd}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">사용금액</label>
				<p class="form-control-static" id="rndPrc">{{rndPrc}}원</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">과제가중치</label>
				<p class="form-control-static" id="rndPrc">{{rndWeight}}%</p>
			</div>
		</div>
		{{/equipRndList}}
		{{#if rfdEquipNo}}
		<div class="br"></div>
		<div class="ar">
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">심의번호</label>
				<p class="form-control-static " id="rfdEquipNo">{{rfdEquipNo}}</p>
			</div>
		</div>
		{{/if}}
		<div class="br">
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비설명</label>
				<p class="form-control-static col-sm-offset-2">{{feature}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">구성 및 성능</label>
				<p class="form-control-static col-sm-offset-2">{{capability}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">사용예</label>
				<p class="form-control-static col-sm-offset-2">{{example}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">5대중점투자분야</label>
				<p class="form-control-static">{{importNm}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비문의처</label>
				<p class="form-control-static">{{operTel}}</p>
			</div>
		</div>
		<div class="ar">
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">등록자</label>
				<p class="form-control-static">{{registId}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">등록일</label>
				<p class="form-control-static">{{registDt}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">수정자</label>
				<p class="form-control-static">{{modifyId}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">수정일</label>
				<p class="form-control-static">{{modifyDt}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">장비상태</label>
				<p class="form-control-static">{{statusNm}}</p>
			</div>
			{{#if_equal statusCd '05'}}
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">반려사유</label>
				<p class="form-control-static">{{errorNm}}</p>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-2 control-label">반려의견</label>
				<p class="form-control-static col-sm-offset-2">{{acqReason}}</p>
			</div>
			{{/if_equal}}
		</div>
	</div>
</body>
</html>