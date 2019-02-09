package boassoft.controller.kp1200;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.CommonCodeService;
import boassoft.service.ContractDtlService;
import boassoft.service.ContractService;
import boassoft.service.InspAssetService;
import boassoft.service.InspItemService;
import boassoft.service.PrintHistoryService;
import boassoft.service.UserService;
import boassoft.service.VirtAssetService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP1213InspAssetController {

	@Resource(name = "CommonMap")
	private CommonMap commonMap;

	@Resource(name = "ContractService")
	private ContractService contractService;

	@Resource(name = "ContractDtlService")
	private ContractDtlService contractDtlService;

	@Resource(name = "InspItemService")
	private InspItemService inspItemService;

	@Resource(name = "InspAssetService")
	private InspAssetService inspAssetService;

	@Resource(name = "VirtAssetService")
	private VirtAssetService virtAssetService;

	@Resource(name = "PrintHistoryService")
	private PrintHistoryService printHistoryService;

	@Resource(name = "CommonCodeService")
	private CommonCodeService commonCodeService;

	@Resource(name = "UserService")
	private UserService userService;

	/** log */
	protected static final Log LOG = LogFactory
			.getLog(KP1213InspAssetController.class);

	/** 검수 */
	@RequestMapping(value = "/kp1200/kp1213.do")
	public String kp1213(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);

		// 계약정보
		CommonMap viewData = contractService.getContractView(cmap);
		model.addAttribute("viewData", viewData);

		// 공통코드
		model.addAttribute("com001Str",
				commonCodeService.getCommonCodeJqGridStr("COM001")); // 상각법
		model.addAttribute("com002Str",
				commonCodeService.getCommonCodeJqGridStr("COM002")); // 사용여부
		model.addAttribute("com005Str",
				commonCodeService.getCommonCodeJqGridStr("COM005")); // 활용범위
		model.addAttribute("com006Str",
				commonCodeService.getCommonCodeJqGridStr("COM006")); // 취득방법

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1200/kp1213";
	}

	/** 검수내역 상세 목록 */
	@RequestMapping(value = "/kp1200/kp1213VirtAjax.do")
	public String kp1213VirtAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		CommonList resultList = virtAssetService.getVirtAssetList(cmap);
		CommonMap result = new CommonMap();
		result.put("resultList", resultList);
		result.put("totalRow", resultList.totalRow);
		model.addAttribute("printString", result.toJsonString());

		return "common/commonString";
	}

	/** 검수처리 */
	@RequestMapping(value = "/kp1200/kp1213Proc.do")
	public String Kp1213Proc(HttpServletRequest request,
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
			cmap.put("purno", cmap.getString("purno"));
			cmap.put("inspDt", cmap.getString("inspDt").replaceAll("\\D", ""));
			cmap.put("aqusitDt",
					cmap.getString("aqusitDt").replaceAll("\\D", ""));
			cmap.put("inspUserName", cmap.getString("inspUserName"));
			cmap.put("inspRemark", cmap.getString("inspRemark"));
			cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
			cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

			// 필수 파라미터 체크
			if ("".equals(cmap.getString("purno"))
					|| cmap.getString("inspDt").length() != 8
					|| cmap.getString("aqusitDt").length() != 8
					|| "".equals(cmap.getString("inspUserName"))
					|| "".equals(cmap.getString("saveJsonArray"))) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
				return resultMap.toJsonString(model);
			}

			JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString(
					"saveJsonArray", "[]", false));
			CommonList paramList = new CommonList();
			for (int i = 0; i < saveJsonArray.size(); i++) {
				JSONObject obj = saveJsonArray.getJSONObject(i);
				CommonMap param = new CommonMap();
				param.put("purno", obj.get("purno"));
				param.put("rqstno", obj.get("rqstno"));
				param.put("prodno", obj.get("prodno"));
				param.put("assetSeq", obj.get("assetSeq"));

				// 필수 파라미터 체크
				if ("".equals(param.getString("purno"))
						|| "".equals(param.getString("rqstno"))
						|| "".equals(param.getString("prodno"))
						|| "".equals(param.getString("assetSeq"))) {
					resultMap.put("ret", "ERR");
					resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
					return resultMap.toJsonString(model);
				}

				paramList.add(param);
			}

			// 저장
			resultCnt = inspAssetService.insertInspAssetAll(cmap, paramList);

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

	/** 검수취소 */
	@RequestMapping(value = "/kp1200/kp1213DelProc.do")
	public String Kp1213DelProc(HttpServletRequest request,
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
			cmap.put("purno", cmap.getString("purno"));
			cmap.put("inspDt", cmap.getString("inspDt"));
			cmap.put("inspUserName", cmap.getString("inspUserName"));
			cmap.put("inspRemark", cmap.getString("inspRemark"));
			cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
			cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

			// 필수 파라미터 체크
			if ("".equals(cmap.getString("purno"))
					|| "".equals(cmap.getString("inspDt"))
					|| "".equals(cmap.getString("inspUserName"))
					|| "".equals(cmap.getString("saveJsonArray"))) {
				resultMap.put("ret", "ERR");
				resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
				return resultMap.toJsonString(model);
			}

			JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString(
					"saveJsonArray", "[]", false));
			CommonList paramList = new CommonList();
			for (int i = 0; i < saveJsonArray.size(); i++) {
				JSONObject obj = saveJsonArray.getJSONObject(i);
				CommonMap param = new CommonMap();
				param.put("assetSeq", obj.get("assetSeq"));

				// 필수 파라미터 체크
				if ("".equals(param.getString("assetSeq"))) {
					resultMap.put("ret", "ERR");
					resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
					return resultMap.toJsonString(model);
				}

				paramList.add(param);
			}

			// 삭제
			resultCnt = inspAssetService.deleteInspAsset2All(cmap, paramList);

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
