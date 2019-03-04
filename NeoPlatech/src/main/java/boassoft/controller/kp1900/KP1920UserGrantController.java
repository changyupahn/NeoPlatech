package boassoft.controller.kp1900;

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

import boassoft.service.GrantMenuService;
import boassoft.service.GrantService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;
import boassoft.util.StringUtil;

@Controller
public class KP1920UserGrantController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "grantService")
    private GrantService grantService;

	@Resource(name = "grantMenuService")
    private GrantMenuService grantMenuService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1920UserGrantController.class);

    @RequestMapping(value="/kp1900/kp1920.do")
	public String kp1920(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1920";
	}

    @RequestMapping(value="/kp1900/kp1920GrantAjax.do")
	public String kp1920GrantAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    	CommonList resultList = grantService.getGrantList(cmap);
    	CommonMap result = new CommonMap();
    	System.out.println(" assetList " + "  : " + resultList.toString());
    	System.out.println(" assetList.size() " + "  : " + resultList.size());
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1920MenuAjax.do")
	public String kp1920MenuAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    	CommonList resultList = grantMenuService.getGrantMenuList(cmap);
    	CommonMap result = new CommonMap();
    	System.out.println(" assetList " + "  : " + resultList.toString());
    	System.out.println(" assetList.size() " + "  : " + resultList.size());
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1920Proc.do")
	public String Kp1920Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("grantNo", cmap.getString("grantNo"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("grantNo"))
	    			|| "".equals(cmap.getString("saveJsonArray"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	CommonList paramList = new CommonList();
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("menuNo", StringUtil.nvl(obj.get("menuNo"),""));
	    		param.put("grantReadYn", StringUtil.nvl(obj.get("grantReadYn"),"N"));
	    		param.put("grantWriteYn", StringUtil.nvl(obj.get("grantWriteYn"),"N"));
	    		param.put("grantHreadYn", StringUtil.nvl(obj.get("grantHreadYn"),"N"));
	    		param.put("grantHwriteYn", StringUtil.nvl(obj.get("grantHwriteYn"),"N"));
	    		param.put("grantManagerYn", StringUtil.nvl(obj.get("grantManagerYn"),"N"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("menuNo"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}

	    	//저장
	    	resultCnt = grantMenuService.insertGrantMenuAll(cmap, paramList);

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
