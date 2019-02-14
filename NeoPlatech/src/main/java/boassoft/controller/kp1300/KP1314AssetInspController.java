package boassoft.controller.kp1300;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetService;
import boassoft.service.ContractDtlService;
import boassoft.service.ContractService;
import boassoft.service.InspAssetService;
import boassoft.service.InspItemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.EgovMessageSource;

@Controller
public class KP1314AssetInspController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "contractService")
    private ContractService contractService;

	@Resource(name = "contractDtlService")
    private ContractDtlService contractDtlService;

	@Resource(name = "inspItemService")
    private InspItemService inspItemService;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1314AssetInspController.class);

    @RequestMapping(value="/kp1300/kp1314.do")
	public String kp1314(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산조회
    	CommonMap assetDetail = assetService.getAssetDetail(cmap);
    	model.addAttribute("assetDetail",assetDetail);

    	//계약정보
    	cmap.put("purno", assetDetail.getString("purno"));
    	CommonMap viewData = contractService.getContractView(cmap);
    	model.addAttribute("viewData",viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1314";
	}

    @RequestMapping(value="/kp1300/kp1314Ajax.do")
	public String kp1314Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "9999");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//파라미터
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	CommonList resultList = inspItemService.getInspItemList2(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

	
}
