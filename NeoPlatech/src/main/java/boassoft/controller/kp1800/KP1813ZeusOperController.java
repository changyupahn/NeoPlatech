package boassoft.controller.kp1800;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.BatchService;
import boassoft.service.UserService;
import boassoft.service.ZeusAsListService;
import boassoft.service.ZeusOperListService;
import boassoft.service.ZeusService;
import boassoft.service.ZeusStatService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.ExcelUtil;
import boassoft.util.HttpZeusUtil;
import boassoft.util.PagingUtil;
import boassoft.util.SessionUtil;
import boassoft.util.StringUtil;
import egovframework.com.cmm.service.EgovProperties;

@Controller
public class KP1813ZeusOperController {

	@Resource(name = "CommonMap")
	private CommonMap commonMap;

	@Resource(name = "ZeusService")
	private ZeusService zeusService;

	@Resource(name = "ZeusStatService")
	private ZeusStatService zeusStatService;

	@Resource(name = "ZeusOperListService")
	private ZeusOperListService zeusOperListService;

	@Resource(name = "ZeusAsListService")
	private ZeusAsListService zeusAsListService;

	@Resource(name = "UserService")
	private UserService userService;

	@Resource(name = "BatchService")
	private BatchService batchService;

	/** log */
	protected static final Log LOG = LogFactory
			.getLog(KP1813ZeusOperController.class);

	@RequestMapping(value = "/kp1800/kp1813.do")
	public String kp1813(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
		cmap.put("pageSize", cmap.getString("pageSize", "50"));

		// 파라미터
		cmap.put("equipId", cmap.getString("equipId"));

		CommonMap viewData = new CommonMap();
		if (!"".equals(cmap.getString("equipId"))) {
			// 장비상세정보
			String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
			String url2 = String.format(
					"http://api.zeus.go.kr/api/eq/equips/%s/%s",
					cmap.getString("equipId"), key);
			String resultStr2 = HttpZeusUtil.get(url2);

			JSONObject resultObject2 = JSONObject.fromObject(resultStr2);

			viewData.put("equipId", resultObject2.getString("equipId"));
			viewData.put("equipNo", resultObject2.getString("equipNo"));
			viewData.put("korNm", resultObject2.getString("korNm"));
			viewData.put("assetNo", resultObject2.getString("fixedAsetNo"));
			viewData.put("registId", resultObject2.getString("registId"));
			viewData.put("registrentNm", SessionUtil.getString("userName"));
			viewData.put("journalSdt", DateUtil.getFormatDate("yyyy-MM-dd"));

			model.addAttribute("viewData", viewData);
		}

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1800/kp1813";
	}

	@RequestMapping(value = "/kp1800/kp1813Tab.do")
	public String kp1813Tab(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1800/kp1813Tab";
	}

	@RequestMapping(value = "/kp1800/kp1813Ajax.do")
	public String kp1813Ajax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		// cmap.put("dataOrder", cmap.getString("dataOrder", "registDt"));
		// cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "DESC"));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String
				.format("http://api.zeus.go.kr/api/oper/operating/%s?pageSize=100&keywords=%s",
						key,
						URLEncoder.encode(cmap.getString("equipNo"), "utf-8"));
		String resultStr = HttpZeusUtil.get(url);

		model.addAttribute("printString", resultStr);

		return "common/commonString";
	}

	@RequestMapping(value = "/kp1800/kp1813Excel.do")
	public String kp1813Excel(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
		cmap.put("pageSize", "999999");

		CommonList resultList = zeusStatService.getZeusEquipStatList(cmap);

		// 순번(rnum) 붙이기
		CommonList tempList = PagingUtil.setPagingRnum(resultList, cmap);

		String[] headerListLgc1 = { "순번", "장비명", "자산번호", "장비등록번호", "취득일자",
				"운영시간", "운영일", "유지보수시간", "유지일" };
		String[] headerListLgc2 = null;
		String[] headerListPhc = { "rnum", "korNm", "assetNo", "equipNo",
				"takeDt", "operHour", "operDate", "asHour", "asDate" };
		String[] headerListTyp = { "DEFAULT", "TEXT", "TEXT", "TEXT", "TEXT",
				"NUMBER", "NUMBER", "NUMBER", "NUMBER" };
		String[] headerListWidth = { "8", "20", "15", "17", "10", "8", "8",
				"8", "8" };
		String[][] mergedRegion = null;

		ExcelUtil.write2(request, response, tempList,
				"ZEUS장비별운영현황(" + cmap.getString("sYear") + "년도)",
				headerListLgc1, headerListLgc2, headerListPhc, headerListTyp,
				mergedRegion, headerListWidth, 20);

		return null;
	}

	@RequestMapping(value = "/kp1800/kp1813Write.do")
	public String kp1813Write(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
		cmap.put("pageSize", cmap.getString("pageSize", "50"));

		// 파라미터
		cmap.put("equipId", cmap.getString("equipId"));
		cmap.put("journalSeq", cmap.getString("journalSeq"));

		CommonMap viewData = new CommonMap();
		if (!"".equals(cmap.getString("equipId"))) {
			// 장비상세정보
			String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
			String url2 = String.format(
					"http://api.zeus.go.kr/api/eq/equips/%s/%s",
					cmap.getString("equipId"), key);
			String resultStr2 = HttpZeusUtil.get(url2);

			JSONObject resultObject2 = JSONObject.fromObject(resultStr2);

			viewData.put("equipId", resultObject2.getString("equipId"));
			viewData.put("equipNo", resultObject2.getString("equipNo"));
			viewData.put("korNm", resultObject2.getString("korNm"));
			viewData.put("assetNo", resultObject2.getString("fixedAsetNo"));
			viewData.put("registId", resultObject2.getString("registId"));
			viewData.put("registrentNm", SessionUtil.getString("userName"));
			viewData.put("journalSdt", DateUtil.getFormatDate("yyyy-MM-dd"));

			model.addAttribute("viewData", viewData);

			// 일지상세정보
			if (!"".equals(cmap.getString("journalSeq"))) {
				String url3 = String.format(
						"http://api.zeus.go.kr/api/oper/operating/%s/%s",
						cmap.getString("journalSeq"), key);
				String resultStr3 = HttpZeusUtil.get(url3);

				JSONObject resultObject3 = JSONObject.fromObject(resultStr3);
				viewData.put("journalSeq",
						resultObject3.getString("journalSeq"));
				viewData.put("equipId", resultObject3.getString("equipId"));
				viewData.put("fixedAsetNo",
						resultObject3.getString("fixedAsetNo"));
				viewData.put("korNm", resultObject3.getString("korNm"));
				viewData.put("engNm", resultObject3.getString("engNm"));
				viewData.put("equipNo", resultObject3.getString("equipNo"));
				viewData.put("managerId", resultObject3.getString("managerId"));
				viewData.put("registrentId",
						resultObject3.getString("registrentId"));
				viewData.put("registrentScRegNo",
						resultObject3.getString("registrentScRegNo"));
				viewData.put("registrentNm",
						resultObject3.getString("registrentNm"));
				viewData.put("journalUserNm",
						resultObject3.getString("journalUserNm"));
				viewData.put("journalSdt",
						resultObject3.getString("journalSdt"));
				viewData.put("journalShour",
						resultObject3.getString("journalShour"));
				viewData.put("journalSminute",
						resultObject3.getString("journalSminute"));
				viewData.put("journalEdt",
						resultObject3.getString("journalEdt"));
				viewData.put("journalEhour",
						resultObject3.getString("journalEhour"));
				viewData.put("journalEminute",
						resultObject3.getString("journalEminute"));
				viewData.put("useOrganClassCd",
						resultObject3.getString("useOrganClassCd"));
				viewData.put("useOrganNm",
						resultObject3.getString("useOrganNm"));
				viewData.put("useDeptNm", resultObject3.getString("useDeptNm"));
				viewData.put("sampleCnt", resultObject3.getString("sampleCnt"));
				viewData.put("inputManHour",
						resultObject3.getString("inputManHour"));
				viewData.put("journalPrc",
						resultObject3.getString("journalPrc"));
				viewData.put("useTypeCd", resultObject3.getString("useTypeCd"));
				viewData.put("useTypeEtc",
						resultObject3.getString("useTypeEtc"));
			}
		}

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1800/kp1813Write";
	}

	@RequestMapping(value = "/kp1800/kp1813Proc.do")
	public String Kp1813Proc(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		int resultCnt = 0;

		try {
			// 세션 체크
			CommonMap procSessionChk = userService
					.procSessionChk(cmap, request);
			if (!procSessionChk.isEmpty()) {
				return procSessionChk.toJsonString(model);
			}

			// 파라미터
			cmap.put("journalSeq", cmap.getString("journalSeq"));
			cmap.put("equipId", cmap.getString("equipId"));
			cmap.put("registrentNm", cmap.getString("registrentNm"));
			cmap.put("journalUserNm", cmap.getString("journalUserNm"));
			cmap.put("journalSdt", cmap.getString("journalSdt"));
			cmap.put("journalShour", cmap.getString("journalShour"));
			cmap.put("journalSminute", cmap.getString("journalSminute"));
			cmap.put("journalEdt", cmap.getString("journalEdt"));
			cmap.put("journalEhour", cmap.getString("journalEhour"));
			cmap.put("journalEminute", cmap.getString("journalEminute"));
			cmap.put("useOrganClassCd", cmap.getString("useOrganClassCd"));
			cmap.put("useOrganNm", cmap.getString("useOrganNm"));
			cmap.put("useDeptNm", cmap.getString("useDeptNm"));
			cmap.put("sampleCnt", cmap.getString("sampleCnt"));
			cmap.put("inputManHour", cmap.getString("inputManHour"));
			cmap.put("journalPrc", cmap.getString("journalPrc"));
			cmap.put("useTypeCd", cmap.getString("useTypeCd"));
			cmap.put("useTypeEtc", cmap.getString("useTypeEtc"));
			cmap.put("useOrganTypeCd", cmap.getString("useOrganTypeCd"));
			cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
			cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

			// 숫자형 체크
			cmap.put("journalShour", StringUtil.nvl(
					cmap.getString("journalShour").replaceAll("\\D", ""), null));
			cmap.put(
					"journalSminute",
					StringUtil.nvl(
							cmap.getString("journalSminute").replaceAll("\\D",
									""), null));
			cmap.put("journalEhour", StringUtil.nvl(
					cmap.getString("journalEhour").replaceAll("\\D", ""), null));
			cmap.put(
					"journalEminute",
					StringUtil.nvl(
							cmap.getString("journalEminute").replaceAll("\\D",
									""), null));
			cmap.put("sampleCnt", StringUtil.nvl(cmap.getString("sampleCnt")
					.replaceAll("\\D", ""), null));
			cmap.put(
					"inputManHour",
					StringUtil.nvl(
							cmap.getString("inputManHour").replaceAll(
									"[^.0-9]", ""), null));
			cmap.put("journalPrc", StringUtil.nvl(cmap.getString("journalPrc")
					.replaceAll("\\D", ""), null));

			// 날짜형 체크
			cmap.put("journalSdt", DateUtil.formatDateTime(
					cmap.getString("journalSdt"), "-", ":", 8));
			cmap.put("journalEdt", DateUtil.formatDateTime(
					cmap.getString("journalEdt"), "-", ":", 8));

			// 필수 파라미터 체크
			if ("".equals(cmap.getString("equipId"))
					|| "".equals(cmap.getString("registrentNm"))
					|| "".equals(cmap.getString("journalUserNm"))
					|| "".equals(cmap.getString("journalSdt"))
					|| "".equals(cmap.getString("journalShour"))
					|| "".equals(cmap.getString("journalSminute"))
					|| "".equals(cmap.getString("journalEdt"))
					|| "".equals(cmap.getString("journalEhour"))
					|| "".equals(cmap.getString("journalEminute"))
					|| "".equals(cmap.getString("useOrganClassCd"))
					|| "".equals(cmap.getString("useOrganNm"))
					|| "".equals(cmap.getString("useDeptNm"))
					|| "".equals(cmap.getString("sampleCnt"))
					|| "".equals(cmap.getString("inputManHour"))
					|| "".equals(cmap.getString("journalPrc"))) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
				return resultMap.toJsonString(model);
			}

			if ("5".equals(cmap.getString("useTypeCd"))
					&& "".equals(cmap.getString("useTypeEtc"))) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "이용유형의 기타 입력값이 누락되었습니다.");
				return resultMap.toJsonString(model);
			}

			// 시간/분 자리수 체우기
			cmap.put("journalShour",
					StringUtil.lpad(cmap.getString("journalShour"), 2, "0"));
			cmap.put("journalSminute",
					StringUtil.lpad(cmap.getString("journalSminute"), 2, "0"));
			cmap.put("journalEhour",
					StringUtil.lpad(cmap.getString("journalEhour"), 2, "0"));
			cmap.put("journalEminute",
					StringUtil.lpad(cmap.getString("journalEminute"), 2, "0"));

			StringBuffer paramters = new StringBuffer();
			paramters.append("equipId="
					+ URLEncoder.encode(cmap.getString("equipId"), "utf-8"));
			paramters
					.append("&registrentNm="
							+ URLEncoder.encode(cmap.getString("registrentNm"),
									"utf-8"));
			paramters.append("&journalUserNm="
					+ URLEncoder.encode(cmap.getString("journalUserNm"),
							"utf-8"));
			paramters.append("&journalSdt="
					+ URLEncoder.encode(cmap.getString("journalSdt"), "utf-8"));
			paramters
					.append("&journalShour="
							+ URLEncoder.encode(cmap.getString("journalShour"),
									"utf-8"));
			paramters.append("&journalSminute="
					+ URLEncoder.encode(cmap.getString("journalSminute"),
							"utf-8"));
			paramters.append("&journalEdt="
					+ URLEncoder.encode(cmap.getString("journalEdt"), "utf-8"));
			paramters
					.append("&journalEhour="
							+ URLEncoder.encode(cmap.getString("journalEhour"),
									"utf-8"));
			paramters.append("&journalEminute="
					+ URLEncoder.encode(cmap.getString("journalEminute"),
							"utf-8"));
			paramters.append("&useOrganClassCd="
					+ URLEncoder.encode(cmap.getString("useOrganClassCd"),
							"utf-8"));
			paramters.append("&useOrganNm="
					+ URLEncoder.encode(cmap.getString("useOrganNm"), "utf-8"));
			paramters.append("&useDeptNm="
					+ URLEncoder.encode(cmap.getString("useDeptNm"), "utf-8"));
			paramters.append("&sampleCnt="
					+ URLEncoder.encode(cmap.getString("sampleCnt"), "utf-8"));
			paramters
					.append("&inputManHour="
							+ URLEncoder.encode(cmap.getString("inputManHour"),
									"utf-8"));
			paramters.append("&journalPrc="
					+ URLEncoder.encode(cmap.getString("journalPrc"), "utf-8"));

			String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
			String resultStr2 = "";
			String url2 = "";

			if (!"".equals(cmap.getString("journalSeq"))) {
				// 수정일 경우
				url2 = String.format(
						"http://api.zeus.go.kr/api/oper/operating/%s/%s",
						cmap.getString("journalSeq"), key);
				resultStr2 = HttpZeusUtil.put(url2, paramters.toString());
			} else {
				// 저장
				url2 = String.format(
						"http://api.zeus.go.kr/api/oper/operating/%s", key);
				resultStr2 = HttpZeusUtil.post(url2, paramters.toString());
			}

			JSONObject resultObject2 = JSONObject.fromObject(resultStr2);
			String errors = resultObject2.getString("errors");

			if (!"".equals(errors) && !"[]".equals(errors)) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "[오류]" + errors);
				return resultMap.toJsonString(model);
			} else {
				resultCnt = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultCnt > 0) {
			resultMap.put("ret", "OK");
			resultMap.put("retmsg", "성공");
		} else {
			resultMap.put("ret", "ERR");
			resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
		}

		return resultMap.toJsonString(model);
	}

	@RequestMapping(value = "/kp1800/kp1813DelProc.do")
	public String Kp1813DelProc(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		int resultCnt = 0;

		try {
			// 세션 체크
			CommonMap procSessionChk = userService
					.procSessionChk(cmap, request);
			if (!procSessionChk.isEmpty()) {
				return procSessionChk.toJsonString(model);
			}

			// 파라미터
			cmap.put("equipId", cmap.getString("equipId"));
			cmap.put("journalSeq", cmap.getString("journalSeq"));

			// 필수 파라미터 체크
			if ("".equals(cmap.getString("equipId"))
					|| "".equals(cmap.getString("journalSeq"))) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
				return resultMap.toJsonString(model);
			}

			// 삭제
			String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
			String url2 = String.format(
					"http://api.zeus.go.kr/api/oper/operating/%s/%s",
					cmap.getString("journalSeq"), key);

			String resultStr2 = HttpZeusUtil.delete(url2, "");

			JSONObject resultObject2 = JSONObject.fromObject(resultStr2);
			String errors = resultObject2.getString("errors");

			if (!"".equals(errors) && !"[]".equals(errors)) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "[오류]" + errors);
				return resultMap.toJsonString(model);
			} else {
				resultCnt = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultCnt > 0) {
			resultMap.put("ret", "OK");
			resultMap.put("retmsg", "성공");
		} else {
			resultMap.put("ret", "ERR");
			resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
		}

		return resultMap.toJsonString(model);
	}

}
