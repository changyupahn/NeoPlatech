package boassoft.controller.kp1900;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.service.WareHouseService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;

/** 창고관리 */
@Controller
public class KP1970WareHouseController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;

	@Resource(name = "wareHouseService")
	private WareHouseService wareHouseService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "systemService")
	private SystemService systemService;

	/** log */
	protected static final Log LOG = LogFactory
			.getLog(KP1970WareHouseController.class);

	@RequestMapping(value = "/kp1900/kp1970.do")
	public String kp1970(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", cmap.getString("pageSize", "50"));
		cmap.put("pageLimit", pageLimit);

		// 화면표시관리 (부자재입고목록)
		cmap.put("dispType", "WARE_HOUSE_LIST");
		CommonList wareHouseList = systemService.getDispMngList(cmap);
		model.addAttribute("wareHouseList", wareHouseList);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1900/kp1970";
	}

	@RequestMapping(value = "/kp1900/kp1970Ajax.do")
	public String kp1970WareHouseAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
		cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
		

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}


		CommonList resultList = wareHouseService.getWareHouseList(cmap);
		CommonMap result = new CommonMap();
		result.put("resultList", resultList);
		result.put("totalRow", resultList.totalRow);
		model.addAttribute("printString", result.toJsonString());

		return "common/commonString";

	}

	@RequestMapping(value = "/kp1900/kp1970Search.do")
	public String kp2110Search(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1900/kp1970Search";
	}

	@RequestMapping(value = "/kp1900/kp1970Excel.do")
	public String kp2110Excel(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);

		int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", "999999");
		cmap.put("pageLimit", "0");
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		// 사용자 기본 파라미터 설정
		if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
				&& "USR".equals(cmap.getString("searchDiv"))) {
			cmap.put("sUserNo",
					cmap.getString("sUserNo", SessionUtil.getString("userNo")));
			cmap.put(
					"sUserName",
					cmap.getString("sUserName",
							SessionUtil.getString("userName")));
			cmap.put("sDeptNo",
					cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
			cmap.put(
					"sDeptName",
					cmap.getString("sDeptName",
							SessionUtil.getString("deptName")));
		}

		
		CommonList resultList = wareHouseService.getWareHouseList(cmap);
		

		// 화면표시관리 (자산목록)
		cmap.put("dispType", "WARE_HOUSE_LIST_EXCEL");
		CommonList dispMngList = systemService.getDispMngList(cmap);

		int headerSize = dispMngList.size();
		String[] headerListLgc1 = new String[headerSize];
		String[] headerListLgc2 = null;
		String[] headerListPhc = new String[headerSize];
		String[] headerListTyp = new String[headerSize];
		String[] headerListWidth = new String[headerSize];
		String[][] mergedRegion = null;
		int idx = 0;

		while (idx < dispMngList.size()) {
			CommonMap dispMng = dispMngList.getMap(idx);
			headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = ""
					+ Math.round(dispMng.getInt("default_width", 100) / 10);
			idx++;
		}

		ExcelUtil.write2(request, response, resultList, "창고그룹목록",
				headerListLgc1, headerListLgc2, headerListPhc, headerListTyp,
				mergedRegion, headerListWidth, 20);

		return null;

	}

	@RequestMapping(value = "/kp1900/kp1971.do")
	public String kp2111(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", cmap.getString("pageSize", "50"));
		cmap.put("pageLimit", pageLimit);

		// 화면표시관리 (부자재입고목록)
		cmap.put("dispType", "WARE_HOUSE_DETAIL_LIST");
		CommonList wareHouseDetailList = systemService.getDispMngList(cmap);
		model.addAttribute("wareHouseDetailList", wareHouseDetailList);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1900/kp1971";

	}

	@RequestMapping(value = "/kp1900/kp1971DetailAjax.do")
	public String kp1971WareHouseDetailAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
		cmap.put("pageLimit", cmap.getInt("pageLimit", 0));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);

		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		CommonList resultList = wareHouseService.getWareHouseDetailList(cmap);

		CommonMap result = new CommonMap();
		result.put("resultList", resultList);
		result.put("totalRow", resultList.totalRow);
		model.addAttribute("printString", result.toJsonString());

		return "common/commonString";
	}

	@RequestMapping(value = "/kp1900/kp1971Search.do")
	public String kp2131Search(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp1900/kp1971Search";
	}

	@RequestMapping(value = "/kp1900/kp1971Excel.do")
	public String kp2111Excel(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", "999999");
		cmap.put("pageLimit", "0");
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		// 사용자 기본 파라미터 설정
		if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
				&& "USR".equals(cmap.getString("searchDiv"))) {
			cmap.put("sUserNo",
					cmap.getString("sUserNo", SessionUtil.getString("userNo")));
			cmap.put(
					"sUserName",
					cmap.getString("sUserName",
							SessionUtil.getString("userName")));
			cmap.put("sDeptNo",
					cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
			cmap.put(
					"sDeptName",
					cmap.getString("sDeptName",
							SessionUtil.getString("deptName")));
		}
		
		CommonList resultList = wareHouseService.getWareHouseDetailList(cmap);

		System.out.println("  resultList.size()" + " : " + resultList.size());

		// 화면표시관리 (자산목록)
		cmap.put("dispType", "WARE_HOUSE_DETAIL_LIST_EXCEL");
		CommonList dispMngList = systemService.getDispMngList(cmap);

		int headerSize = dispMngList.size();
		String[] headerListLgc1 = new String[headerSize];
		String[] headerListLgc2 = null;
		String[] headerListPhc = new String[headerSize];
		String[] headerListTyp = new String[headerSize];
		String[] headerListWidth = new String[headerSize];
		String[][] mergedRegion = null;
		int idx = 0;

		while (idx < dispMngList.size()) {
			CommonMap dispMng = dispMngList.getMap(idx);
			headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = ""
					+ Math.round(dispMng.getInt("default_width", 100) / 10);
			idx++;
		}

		ExcelUtil.write2(request, response, resultList, "창고상세목록",
				headerListLgc1, headerListLgc2, headerListPhc, headerListTyp,
				mergedRegion, headerListWidth, 20);

		return null;

	}
	
	@RequestMapping(value="/address/AddressAPI.do")
    public String getAddressList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("addrUserId", SessionUtil.getString("userId"));
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	System.out.println(" address/AddressAPI.do cmap " + " : " + cmap.toString());
    	return "address/addressAPI";
	}
}
