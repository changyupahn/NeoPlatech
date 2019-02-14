package boassoft.controller.kp1100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetService;
import boassoft.service.CommonCodeService;
import boassoft.service.ItemService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1110ItemController {

	@Resource(name = "itemService")
    private ItemService itemService;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "itemSeqIdGnrService")
    private EgovIdGnrService itemSeqIdGnrService;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "commonCodeService")
    private CommonCodeService commonCodeService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1110ItemController.class);

    @RequestMapping(value="/kp1100/kp1110.do")
	public String kp1110(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("sItemType", cmap.getString("sItemType", "DEFAULT"));

    	cmap.put("paramCodeId", "COM007");
    	CommonList assetTypeCdList = commonCodeService.getCommonCodeList(cmap);
    	model.addAttribute("assetTypeCdList", assetTypeCdList);

    	CommonList dataList = itemService.getItemList(cmap);
    	model.addAttribute("dataList", dataList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1100/kp1110";
	}

    @RequestMapping(value="/kp1100/kp1110Ajax.do")
	public String Kp1110Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("itemSeq", cmap.getString("itemSeq"));

    	if (!"".equals(cmap.getString("itemSeq"))) {
        	CommonMap viewData = itemService.getItemView(cmap);
        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1110Ajax";
	}

    @RequestMapping(value="/kp1100/kp1110RegAjax.do")
	public String Kp1110RegAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("itemSeq", cmap.getString("sItemSeq"));

    	if (!"".equals(cmap.getString("itemSeq"))) {
        	CommonMap viewData = itemService.getItemView(cmap);
        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1110RegAjax";
	}

    @RequestMapping(value="/kp1100/kp1110Proc.do")
	public String Kp1110Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

	    	//파라미터
	    	cmap.put("parentItemSeq", cmap.getString("parentItemSeq"));
	    	cmap.put("assetTypeCd", cmap.getString("assetTypeCd"));
	    	cmap.put("itemType", cmap.getString("itemType", "DEFAULT"));
	    	cmap.put("itemSeq", cmap.getString("itemSeq"));
	    	cmap.put("itemCd", cmap.getString("itemCd").toUpperCase().replaceAll("[^A-Z]", ""));
	    	cmap.put("itemLevel", cmap.getString("itemLevel"));
	    	cmap.put("itemName", cmap.getString("itemName"));
	    	cmap.put("useYn", cmap.getString("useYn"));
	    	cmap.put("usefulLife", cmap.getString("usefulLife"));
	    	cmap.put("depreCd", cmap.getString("depreCd"));
	    	cmap.put("remark", cmap.getString("remark"));
	    	cmap.put("sortNum", cmap.getString("sortNum"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
	    	
	    	cmap.put("itemLevel", "1");
	    	cmap.put("usefulLife", "3");

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("itemCd"))
	    			|| "".equals(cmap.getString("itemLevel"))
	    			|| "".equals(cmap.getString("itemName"))
	    			|| "".equals(cmap.getString("useYn"))
	    			|| "".equals(cmap.getString("sortNum"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}
	    	
	    	//품목코드 체크
	    	if (cmap.getString("itemCd").length() != 2) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "물품분류는 대문자 2자리로 입력해주세요.");
	    		return resultMap.toJsonString(model);
	    	}

	    	if (!"".equals(cmap.getString("itemSeq"))) {
	    		//수정
	    		resultCnt = itemService.updateItem(cmap);
	    	} else {
	    		//신규
	    		cmap.put("itemSeq", itemSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
	    		resultCnt = itemService.insertItem(cmap);
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

    @RequestMapping(value="/kp1100/kp1110DelProc.do")
	public String Kp1110DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

	    	//파라미터
	    	cmap.put("itemSeq", cmap.getString("itemSeq"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("itemSeq"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//조회
	    	CommonMap viewData = itemService.getItemView(cmap);

	    	//조회 결과 없을 경우
	    	if (viewData.isEmpty()) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//해당 품목코드를 사용하고 있는 자산이 있을 경우
	    	CommonMap cmap2 = new CommonMap();
	    	cmap2.put("sItemCd", viewData.getString("itemCd"));
	    	int assetCnt = assetService.getAssetListCnt(cmap2);
	    	if (assetCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 해당 품목코드를 사용하고 있는 자산이 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//하위 품목코드가 있을 경우
	    	CommonMap cmap3 = new CommonMap();
	    	cmap3.put("sParentItemSeq", viewData.getString("itemSeq"));
	    	int itemCnt = itemService.getItemListCnt(cmap3);
	    	if (itemCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 하위 품목코드가 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = itemService.deleteItem2(cmap);

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
