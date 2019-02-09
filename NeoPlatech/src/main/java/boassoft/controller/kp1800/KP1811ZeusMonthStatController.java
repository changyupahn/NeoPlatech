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
public class KP1811ZeusMonthStatController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "ZeusService")
    private ZeusService zeusService;

	@Resource(name = "ZeusStatService")
    private ZeusStatService zeusStatService;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "BatchService")
    private BatchService batchService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1811ZeusMonthStatController.class);

    @RequestMapping(value="/kp1800/kp1811.do")
	public String kp1811(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//작성연도 목록
    	CommonList statYearList = zeusStatService.getZeusStatYearList(cmap);
    	model.addAttribute("statYearList",statYearList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1811";
	}

    @RequestMapping(value="/kp1800/kp1811Ajax.do")
	public String kp1811Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	CommonList resultList = zeusStatService.getZeusMonthStatList(cmap);

    	//순번(rnum) 붙이기
    	CommonList tempList = PagingUtil.setZeusPagingRnum(resultList, cmap);

    	CommonMap result = new CommonMap();
    	result.put("resultList", tempList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1800/kp1811Excel.do")
	public String kp1811Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	CommonList resultList = zeusStatService.getZeusMonthStatList(cmap);

    	//순번(rnum) 붙이기
    	CommonList tempList = PagingUtil.setZeusPagingRnum(resultList, cmap);

    	String[] headerListLgc1 = {"순번","장비등록번호","장비명","자산번호","일지구분",cmap.getString("sYear")+"년 월별 작성현황(일)","","","","","","","","","","","",""};
    	String[] headerListLgc2 = {"","","","","","1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월","계"};
    	String[] headerListPhc = {"rnum","equipNo","korNm","assetNo","gubnStr","cnt01","cnt02","cnt03","cnt04","cnt05","cnt06","cnt07","cnt08","cnt09","cnt10","cnt11","cnt12","sum01"};
    	String[] headerListTyp = {"DEFAULT","TEXT","TEXT","TEXT","TEXT","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"8","17","20","15","10","8","8","8","8","8","8","8","8","8","8","8","8","8"};
    	String[][] mergedRegion = {
    			{"0","0","1","0"},
    			{"0","1","1","1"},
    			{"0","2","1","2"},
    			{"0","3","1","3"},
    			{"0","4","1","4"},
    			{"0","5","0","17"}
    	};

    	ExcelUtil.write3(request, response, tempList, "ZEUS월별작성현황("+cmap.getString("sYear")+"년도)", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}


}
