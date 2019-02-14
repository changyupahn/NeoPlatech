package boassoft.controller.kp2000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetHistoryService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;

@Controller
public class KP2010AssetHistoryController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP2010AssetHistoryController.class);

    @RequestMapping(value="/kp2000/kp2010.do")
	public String kp2010(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp2000/kp2010";
	}

    @RequestMapping(value="/kp2000/kp2010Ajax.do")
	public String kp2010Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "1");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = assetHistoryService.getAssetHistoryList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp2000/kp2010Search.do")
	public String kp2010Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp2000/kp2010Search";
	}

    @RequestMapping(value="/kp2000/kp2010Excel.do")
	public String kp2010Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
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

    	CommonList resultList = assetHistoryService.getAssetHistoryList(cmap);

    	String[] headerListLgc1 = {"(회계)자산코드","자산번호","품명","규격","이력구분","변경일시","내용","변경자"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"topAssetNo","assetNo","assetName","assetSize","histTypeName","histDt","histContent","userName"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","TEXT","TEXT","histDt","DATE","TEXT"};
    	String[] headerListWidth = {"12","12","25","25","15","20","25","15"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "자산변경내역", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

}
