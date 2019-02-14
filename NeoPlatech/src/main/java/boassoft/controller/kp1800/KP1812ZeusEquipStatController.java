package boassoft.controller.kp1800;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.BatchService;
import boassoft.service.UserService;
import boassoft.service.ZeusService;
import boassoft.service.ZeusStatService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.PagingUtil;
import boassoft.util.SessionUtil;

@Controller
public class KP1812ZeusEquipStatController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "zeusService")
    private ZeusService zeusService;

	@Resource(name = "zeusStatService")
    private ZeusStatService zeusStatService;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "batchService")
    private BatchService batchService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1812ZeusEquipStatController.class);

    @RequestMapping(value="/kp1800/kp1812.do")
	public String kp1812(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//작성연도 목록
    	CommonList statYearList = zeusStatService.getZeusStatYearList(cmap);
    	model.addAttribute("statYearList",statYearList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1812";
	}

    @RequestMapping(value="/kp1800/kp1812Ajax.do")
	public String kp1812Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
//    	cmap.put("dataOrder", cmap.getString("dataOrder", "registDt"));
//    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "DESC"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	String ssGrantRead = cmap.getString("ssGrantRead");

    	if (!"GRANT_MGR".equals(ssGrantRead)) {
    		cmap.put("sRegistId", SessionUtil.getString("userEmail"));

    		if ("".equals(cmap.getString("sRegistId"))) {
    			cmap.put("sRegistId", "99999999@99999999");
    		}
    	}

    	CommonList resultList = zeusStatService.getZeusEquipStatList(cmap);

    	//순번(rnum) 붙이기
    	CommonList tempList = PagingUtil.setPagingRnum(resultList, cmap);

    	CommonMap result = new CommonMap();
    	result.put("resultList", tempList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1800/kp1812Excel.do")
	public String kp1812Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	String ssGrantRead = cmap.getString("ssGrantRead");

    	if (!"GRANT_MGR".equals(ssGrantRead)) {
    		cmap.put("sRegistId", SessionUtil.getString("userEmail"));

    		if ("".equals(cmap.getString("sRegistId"))) {
    			cmap.put("sRegistId", "99999999@99999999");
    		}
    	}

    	CommonList resultList = zeusStatService.getZeusEquipStatList(cmap);

    	//순번(rnum) 붙이기
    	CommonList tempList = PagingUtil.setPagingRnum(resultList, cmap);

    	String[] headerListLgc1 = {"순번","장비명","자산번호","장비등록번호","취득일자","운영시간","운영일","유지보수시간","유지일"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"rnum","korNm","assetNo","equipNo","takeDt","operHour","operDate","asHour","asDate"};
    	String[] headerListTyp = {"DEFAULT","TEXT","TEXT","TEXT","TEXT","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"8","20","15","17","10","8","8","8","8"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, tempList, "ZEUS장비별운영현황("+cmap.getString("sYear")+"년도)", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

	
}
