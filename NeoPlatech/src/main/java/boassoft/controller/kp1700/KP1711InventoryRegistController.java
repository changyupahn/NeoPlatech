package boassoft.controller.kp1700;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.InventoryService;
import boassoft.service.UserService;
import boassoft.util.CommonMap;

@Controller
public class KP1711InventoryRegistController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "InventoryService")
    private InventoryService inventoryService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1711InventoryRegistController.class);

    /** 재물조사 계획 생성 폼 */
    @RequestMapping(value="/kp1700/kp1711.do")
	public String kp1711(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1700/kp1711";
	}

    /** 재물조사 계획 생성 처리 */
    @RequestMapping(value="/kp1700/kp1711Proc.do")
	public String kp1711Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    		cmap.put("invType", cmap.getString("invType"));
    		cmap.put("invSummary", cmap.getString("invSummary"));
    		cmap.put("invStartDt", cmap.getString("invStartDt"));
    		cmap.put("invTargetYn", "N");
        	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

        	//필수 파라미터 체크
        	if ("".equals(cmap.getString("invType"))
        			|| cmap.getString("invStartDt").length() != 10
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}

    		resultCnt = inventoryService.insertInventoryMaster(cmap);

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
