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
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

@Controller
public class KP1710InventoryMasterController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "inventoryService")
    private InventoryService inventoryService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1710InventoryMasterController.class);

    @RequestMapping(value="/kp1700/kp1710.do")
	public String kp1710(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
    	cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	
    	//재물조사연도 목록
    	CommonList invYearList = inventoryService.getInventoryYearList(cmap);
    	model.addAttribute("invYearList", invYearList);

    	//마지막 재물조사 차수
    	CommonMap invLast = inventoryService.getInventoryLast(cmap);
    	if (invLast.isEmpty()) {
    		invLast.put("invYear", DateUtil.getFormatDate("yyyy"));
    		invLast.put("invNo", "0");
    	}
    	model.addAttribute("invLast", invLast);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1700/kp1710";
	}

    @RequestMapping(value="/kp1700/kp1710Ajax.do")
	public String kp1710Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",0));
    	
    	System.out.println(" dataOrder " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize " + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit " + "  : " + cmap.getInt("pageLimit"));
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	System.out.println(" cmap " + "  : " + cmap.toString());
    	CommonList resultList = inventoryService.getInventoryList(cmap);
    	CommonMap result = new CommonMap();
    	System.out.println(" getDeviceList " + "  : " + resultList.toString());
    	System.out.println(" getDeviceList.size() " + "  : " + resultList.size());
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1700/kp1710DelProc.do")
	public String kp1710DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    		cmap.put("invYear", cmap.getString("invYear").replaceAll("\\D", ""));
    		cmap.put("invNo", cmap.getString("invNo").replaceAll("\\D", ""));
        	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

        	//필수 파라미터 체크
        	if (cmap.getString("invYear").length() != 4
        			|| "".equals(cmap.getString("invNo"))
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}

    		resultCnt = inventoryService.deleteInventory(cmap);

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
