package boassoft.controller.kp1900;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.DeviceLogService;
import boassoft.service.DeviceService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP1930DeviceController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "deviceService")
    private DeviceService deviceService;

	@Resource(name = "deviceLogService")
    private DeviceLogService deviceLogService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1930DeviceController.class);

    @RequestMapping(value="/kp1900/kp1930.do")
	public String kp1930(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1930";
	}

    @RequestMapping(value="/kp1900/kp1930DeviceAjax.do")
	public String kp1930DeviceAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	
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
    	//CommonList resultList = new CommonList();
    	CommonList resultList = deviceService.getDeviceList(cmap);
    	System.out.println(" getDeviceList " + "  : " + resultList.toString());
    	System.out.println(" getDeviceList.size() " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1930LogAjax.do")
	public String kp1930LogAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	
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
    	//CommonList resultList = new CommonList();
    	CommonList resultList = deviceLogService.getDeviceLogList(cmap);
    	System.out.println(" resultList " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1930Proc.do")
	public String Kp1930Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("deviceno", cmap.getString("deviceno"));
	    	cmap.put("devicenm", cmap.getString("devicenm"));
	    	cmap.put("deviceInspName", cmap.getString("deviceInspName"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("deviceno"))
	    			|| "".equals(cmap.getString("devicenm"))
	    			|| "".equals(cmap.getString("deviceInspName"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//조회
	    	CommonMap device = deviceService.getDeviceView(cmap);

	    	if (device.isEmpty()) {
	    		//등록
		    	resultCnt = deviceService.insertDevice(cmap);
	    	} else {
	    		//수정
		    	resultCnt = deviceService.updateDevice(cmap);
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

    @RequestMapping(value="/kp1900/kp1930DelProc.do")
	public String Kp1930DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("deviceno", cmap.getString("deviceno"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("deviceno"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = deviceService.deleteDevice(cmap);

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
