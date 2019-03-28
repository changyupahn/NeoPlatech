package boassoft.controller.kp2000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import boassoft.service.PrintHistoryService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;

@Controller
public class KP2020PrintHistoryController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "printHistoryService")
    private PrintHistoryService printHistoryService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP2020PrintHistoryController.class);

    @RequestMapping(value="/kp2000/kp2020.do")
	public String kp2020(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
    	cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);     	

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp2000/kp2020";
	}

    @RequestMapping(value="/kp2000/kp2020Ajax.do")
	public String kp2020Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	cmap.put("sAssetDiv", "1");
    	
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
    	CommonList resultList = printHistoryService.getPrintHistoryList(cmap);    	
    	System.out.println(" resultList " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp2000/kp2020Search.do")
	public String kp2020Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp2000/kp2020Search";
	}

    @RequestMapping(value="/kp2000/kp2020Excel.do")
	public String kp2020Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
    	
    	cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", "0"); 
    	cmap.put("sAssetDiv", "1");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		CommonMap cmForward = new CommonMap();
        	cmForward.put("forwardUrl", "");
        	cmForward.put("forwardMsg", "조회 권한이 없거나 로그인 세션이 만료 되었습니다. 다시 접속해주세요.");
        	model.addAttribute("cmForward", cmForward);
        	return "common/commonOk";
    	}

    	CommonList resultList = printHistoryService.getPrintHistoryList(cmap);

    	String[] headerListLgc1 = {"자산번호","품명","규격","출력요청일시","상태"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"etc2","assetName","assetSize","printDt","printYnStr"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","DATE","TEXT"};
    	String[] headerListWidth = {"20","25","25","20","20"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "태그발행내역", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

}
