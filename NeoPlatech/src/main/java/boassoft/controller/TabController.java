package boassoft.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boassoft.common.CommonXmlList;
import boassoft.common.CommonXmlManage;
import boassoft.service.DeviceLogService;
import boassoft.service.DeviceService;
import boassoft.service.TabService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class TabController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "tabService")
    private TabService tabService;

	@Resource(name = "deviceService")
    private DeviceService deviceService;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "commonXmlManage")
    private CommonXmlManage commonXmlManage;
	
	@Resource(name = "deviceLogService")
    private DeviceLogService deviceLogService;

	@Resource(name = "deviceLogSeqIdGnrService")
    private EgovIdGnrService deviceLogSeqIdGnrService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(TabController.class);


    @RequestMapping(value="/rfid/login.do")
	public String rfidLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/rfid/login.do" + " - " + cmap);

    	cmap.put("user_id", cmap.getString("user_id", "").trim());
    	cmap.put("user_pw", cmap.getString("user_pw", "").trim());
    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	
    	//사용자ID/비밀번호 대문자 변환
    	cmap.put("user_id", cmap.getString("user_id").toUpperCase());
    	cmap.put("user_pw", cmap.getString("user_pw").toUpperCase());

    	String xmlString = "";

    	try{    		
    		//디바이스 접근 로그 기록
    		cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("accessIp", request.getRemoteAddr());
    		cmap.put("frstRegistPnttm", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		deviceLogService.insertDeviceLog(cmap);

    		//디바이스번호 확인
	    	CommonMap deviceView = deviceService.getDeviceView(cmap);
	    	if (deviceView.isEmpty()) {
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
	    		model.addAttribute("xmlString", xmlString);
	    		return "common/commonXml";
	    	}
	    	
	    	if (!"".equals(cmap.getString("user_pw"))) {
	    		cmap.put("user_pw", EgovFileScrty.encryptPassword(cmap.getString("user_pw")));
//	    		System.out.println("user_pw : " + cmap.getString("user_pw"));
	    	}
    		
			CommonMap view = tabService.getRfidLoginView(cmap);
			if (view == null || view.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>[로그인실패] 아이디 또는 비밀번호가 맞지 않습니다.</retmsg></data>";
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>OK</ret><retmsg>로그인 성공</retmsg><user_key>"+ view.getString("userKey") +"</user_key></data>";
			}
    			
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		LOG.debug(e.toString());
    		//e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    /*
    @RequestMapping(value="/rfid/print.do")
	public String rfidPrint(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/rfid/print.do" + " - " + cmap);

    	CommonMap printInfo = new CommonMap();
    	printInfo.put("asset_no", cmap.getString("asset_no", "").trim());
    	printInfo.put("asset_name", cmap.getString("asset_name", "").trim());
    	printInfo.put("asset_size", cmap.getString("asset_size", "").trim());
    	printInfo.put("print_type", cmap.getString("print_type", "N").trim()); //N,S

    	String xmlString = "";

    	try{
    		printService.insertHistory(printInfo);
    		xmlString = "1";
    	}catch(Exception e){
    		xmlString = "-2";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}*/

    @RequestMapping(value="/asset/selectListXml.do")
	public String assetSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/asset/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = tabService.getAssetListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/inventory/master/selectListXml.do")
	public String inventoryMasterSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/inventory/master/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("inv_year", cmap.getString("inv_year", ""));
    	cmap.put("dataOrder", cmap.getString("sort_option", ""));
    	cmap.put("dataOrderArrow", cmap.getString("order", ""));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = tabService.getInventoryListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/inventory/detail/selectListXml.do")
	public String inventoryDetailSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/inventory/detail/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("inv_year", cmap.getString("inv_year", ""));
    	cmap.put("inv_no", cmap.getString("inv_no", ""));
    	cmap.put("dataOrder", cmap.getString("sort_option", ""));
    	cmap.put("dataOrderArrow", cmap.getString("order", ""));
    	cmap.put("user_key", cmap.getString("user_key", ""));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		
    		//디바이스 접근 로그 기록
    		cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("accessIp", request.getRemoteAddr());
    		cmap.put("frstRegistPnttm", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		deviceLogService.insertDeviceLog(cmap);

    		//디바이스번호 확인
	    	CommonMap deviceView = deviceService.getDeviceView(cmap);
	    	if (deviceView.isEmpty()) {
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
	    		model.addAttribute("xmlString", xmlString);
	    		return "common/commonXml";
	    	}
	    	
	    	//사용자 확인
	    	CommonMap userView = userService.getUserKeyLoginView(cmap);
	    	if (userView.isEmpty()) {
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 사용자 접근입니다.</retmsg></data>";
	    		model.addAttribute("xmlString", xmlString);
	    		return "common/commonXml";
	    	}
	    	
	    	if (!"MGR".equals(userView.getString("grantNo"))) {
	    		//사용자 부서 권한 확인
		    	CommonList userDeptList = tabService.getUserDeptList(userView);
		    	
		    	if (userDeptList.isEmpty()) {
		    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>부서 권한이 없습니다. (관리자 권한 작업이 필요합니다.)</retmsg></data>";
		    		model.addAttribute("xmlString", xmlString);
		    		return "common/commonXml";
		    	} else {
		    		String deptNameArr = "";
		    		for (int k=0; k<userDeptList.size(); k++) {
		    			CommonMap userDept = userDeptList.getMap(k);
		    			deptNameArr += "," + userDept.getString("deptName");
		    		}
		    		deptNameArr = deptNameArr.replaceAll("^,", "");
		    		cmap.put("deptNameArr", deptNameArr.split(","));
		    	}
	    	}

    		//최신 차수 조회하기
    		if ("".equals(cmap.getString("inv_year")) || "".equals(cmap.getString("inv_no"))) {
    			CommonMap invLast = tabService.getInventoryLast(cmap);
    			cmap.put("inv_year", invLast.getString("inv_year"));
    			cmap.put("inv_no", invLast.getString("inv_no"));
    		}

    		commonXmlList = tabService.getInventoryDetailListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/user/selectListXml.do")
	public String userSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/user/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("user_name", cmap.getString("user_name", ""));
    	cmap.put("dept_no", cmap.getString("dept_no", ""));
    	cmap.put("dept_name", cmap.getString("dept_name", ""));
    	cmap.put("org_no", cmap.getString("org_no", ""));
    	cmap.put("org_name", cmap.getString("org_name", ""));
    	cmap.put("dataOrder", cmap.getString("sort_option", ""));
    	cmap.put("dataOrderArrow", cmap.getString("order", ""));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = tabService.getUserListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/dept/selectListXml.do")
	public String deptSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/dept/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("dept_name", cmap.getString("dept_name", ""));
    	cmap.put("org_no", cmap.getString("org_no", ""));
    	cmap.put("org_name", cmap.getString("org_name", ""));
    	cmap.put("dataOrder", cmap.getString("sort_option", ""));
    	cmap.put("dataOrderArrow", cmap.getString("order", ""));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = tabService.getDeptListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/pos/selectListXml.do")
	public String posSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/pos/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("pos_name", cmap.getString("pos_name", ""));
    	cmap.put("dataOrder", cmap.getString("sort_option", ""));
    	cmap.put("dataOrderArrow", cmap.getString("order", ""));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = tabService.getPosListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/org/selectListXml.do")
	public String orgSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/org/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("org_name", cmap.getString("org_name", ""));
    	cmap.put("dataOrder", cmap.getString("sort_option", ""));
    	cmap.put("dataOrderArrow", cmap.getString("order", ""));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = tabService.getOrgListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/asset/image/selectListXml.do")
	public String assetImageSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/asset/image/selectListXml.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
    	cmap.put("pageSize", cmap.getString("page_size", "10"));
    	cmap.put("asset_no", cmap.getString("asset_no", ""));
    	cmap.put("file_dt", cmap.getString("file_dt", ""));
    	cmap.put("sHttpDomain", EgovProperties.getProperty("Globals.Domain"));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		
    		//디바이스 접근 로그 기록
    		cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("accessIp", request.getRemoteAddr());
    		cmap.put("frstRegistPnttm", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		deviceLogService.insertDeviceLog(cmap);

    		//디바이스번호 확인
	    	CommonMap deviceView = deviceService.getDeviceView(cmap);
	    	if (deviceView.isEmpty()) {
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
	    		model.addAttribute("xmlString", xmlString);
	    		return "common/commonXml";
	    	}
	    	
    		commonXmlList = tabService.getImageListXml(cmap);

	    	if( commonXmlList.size() > 0 )
	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
	    	else
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/asset/image/upload.do")
	public String assetImageUpload(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(multiRequest);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/asset/image/upload.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("asset_no", cmap.getString("asset_no", ""));
    	cmap.put("file_dt", cmap.getString("file_dt", ""));

    	String xmlString = "";

    	try{

    		if (!"".equals(cmap.getString("asset_no"))
    				&& !"".equals(cmap.getString("file_dt"))
    				) {

    			cmap = commonMap.setMultipartImage(multiRequest, "", "Globals.Asset.Img.fileStorePath", "");

    			if (!"".equals(cmap.getString("fileFileStreCours"))
        				&& !"".equals(cmap.getString("fileStreFileNm"))
        				&& !"".equals(cmap.getString("fileOrignlFileNm"))
        				) {

        			cmap.put("filePath", cmap.getString("fileFileStreCours"));
    				cmap.put("fileNm", cmap.getString("fileStreFileNm"));
    				cmap.put("orignlFileNm", cmap.getString("fileOrignlFileNm"));
    				cmap.put("fileExt", cmap.getString("fileFileExtsn"));
    		    	cmap.put("webFilePath", "/appimg/" + DateUtil.getFormatDate("yyyy"));

    				String[] asset_no_arr = cmap.getArray("asset_no");
    				for (int i=0; i<asset_no_arr.length; i++) {
    					cmap.put("asset_no", asset_no_arr[i]);
    					tabService.insertAssetImage(cmap);
    				}
    				xmlString = "1";
    			} else {
    				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    			}
    		} else {
    			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		}
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/asset/image/delete.do")
	public String assetImageDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/asset/image/delete.do" + " - " + cmap);

    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
    	cmap.put("asset_no", cmap.getString("asset_no", ""));
    	cmap.put("file_dt", cmap.getString("file_dt", ""));

    	String xmlString = "";

    	try{

    		if (!"".equals(cmap.getString("file_dt"))
    				&& !"".equals(cmap.getString("asset_no"))
    				) {

    			tabService.deleteAssetImage(cmap);
    			xmlString = "1";

    		} else {
    			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		}
    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

    @RequestMapping(value="/inventory/sync/upload.do")
	public String inventorySyncUpload(final MultipartHttpServletRequest multiRequest, HttpServletRequest request, ModelMap model) throws Exception {
    	String xmlString = "";

    	try{

			CommonMap cmap = commonMap.setMultipartXml(multiRequest, "", "Globals.Asset.Xml.fileStorePath", "");
	    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/inventory/sync/upload.do" + " - " + cmap);

	    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
	    	
	    	//디바이스 접근 로그 기록
    		cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("accessIp", request.getRemoteAddr());
    		cmap.put("frstRegistPnttm", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		deviceLogService.insertDeviceLog(cmap);

	    	//디바이스번호 확인
	    	CommonMap deviceView = deviceService.getDeviceView(cmap);
	    	if (deviceView.isEmpty()) {
	    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
	    		model.addAttribute("xmlString", xmlString);
	    		return "common/commonXml";
	    	}

	    	//디바이스번호로 조사자명 가져오기
			cmap.put("tagInspName", deviceView.getString("devicenm"));

			if (!"".equals(cmap.getString("fileStreFileNm"))) {
				String filePath = cmap.getString("fileFileStreCours");
				String fileNm = cmap.getString("fileStreFileNm");
				//String fileExt = cmap.getString("fileFileExtsn");
				String xmlPath = filePath + File.separatorChar + fileNm;
				CommonList xmlList = commonXmlManage.getXmlList(xmlPath);
				System.out.println("xmlList.size() : " + xmlList.size());

				if (xmlList != null) {

					//재물조사 업데이트
					for (int i=0; i<xmlList.size(); i++) {
						CommonMap inv = xmlList.getMap(i);
						inv.put("tagInspName", cmap.getString("tagInspName", cmap.getString("deviceno")));
						tabService.updateInventoryDetail(inv);
					}
				}
			}

			xmlString = "1";

    	}catch(Exception e){
    		xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		e.printStackTrace();
    	}

    	model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

//    @RequestMapping(value="/new_asset/selectListXml.do")
//	public String newAssetSelectListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//    	CommonMap cmap = new CommonMap(request);
//    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/new_asset/selectListXml.do" + " - " + cmap);
//
//    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
//    	cmap.put("pageIdx", cmap.getString("page_idx", "1"));
//    	cmap.put("pageSize", cmap.getString("page_size", "10"));
//
//    	CommonXmlList commonXmlList = new CommonXmlList();
//    	String xmlString = "";
//
//    	try{
//    		commonXmlList = tabService.getNewAssetListXml(cmap);
//
//	    	if( commonXmlList.size() > 0 )
//	    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
//	    	else
//	    		xmlString = "-1";
//    	}catch(Exception e){
//    		xmlString = "-2";
//    		e.printStackTrace();
//    	}
//
//    	model.addAttribute("xmlString", xmlString);
//
//		return "common/commonXml";
//	}

//    @RequestMapping(value="/new_asset/sync/upload.do")
//	public String newAssetSyncUpload(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
//    	String xmlString = "";
//
//    	try{
//
//			CommonMap cmap = commonMap.setMultipartXml(multiRequest, "", "Globals.Asset.Xml.fileStorePath", "");
//	    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/new_asset/sync/upload.do" + " - " + cmap);
//
//	    	cmap.put("deviceno", cmap.getString("deviceno", "").trim());
//
//			if ("true".equals(cmap.getString("file_RST"))) {
//				String filePath = cmap.getString("file_PATH");
//				String fileNm = cmap.getString("file_NFNM");
//				String fileExt = cmap.getString("file_EXT");
//				String xmlPath = filePath + File.separatorChar + fileNm + "." + fileExt;
//				CommonList xmlList = commonXmlManage.getXmlList(xmlPath);
//				System.out.println("xmlList.size() : " + xmlList.size());
//
//				if (xmlList != null) {
//					for (int i=0; i<xmlList.size(); i++) {
//						CommonMap inv = xmlList.getMap(i);
//						tabService.insertNewAsset(inv);
//					}
//				}
//			}
//
//			xmlString = "1";
//
//    	}catch(Exception e){
//    		xmlString = "-2";
//    		e.printStackTrace();
//    	}
//
//    	model.addAttribute("xmlString", xmlString);
//
//		return "common/commonXml";
//	}

//    //테스트용
//    @RequestMapping(value="/inventory/sync/upload2.do")
//	public String inventorySyncUpload2(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//    	CommonMap cmap = new CommonMap(request);
//    	String xmlString = "";
//
//    	try{
//
//    		cmap.put("file_RST", "true");
//    		cmap.put("file_PATH", "/data/home/rfid/jsphome/asset/xml/upload/2014");
//    		cmap.put("file_NFNM", "201404170821274160");
//    		cmap.put("file_EXT", "xml");
//
//	    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/inventory/sync/upload.do" + " - " + cmap);
//
//			if ("true".equals(cmap.getString("file_RST"))) {
//				String filePath = cmap.getString("file_PATH");
//				String fileNm = cmap.getString("file_NFNM");
//				String fileExt = cmap.getString("file_EXT");
//				String xmlPath = filePath + File.separatorChar + fileNm + "." + fileExt;
//				CommonList xmlList = commonXmlManage.getXmlList(xmlPath);
//				System.out.println("xmlList.size() : " + xmlList.size());
//
//				if (xmlList != null) {
//					for (int i=0; i<xmlList.size(); i++) {
//						CommonMap inv = xmlList.getMap(i);
//						tabService.updateInventoryDetail(inv);
//						System.out.println("count : " + (i+1) + " : " + inv);
//					}
//				}
//			}
//
//			xmlString = "1";
//
//    	}catch(Exception e){
//    		xmlString = "-2";
//    		e.printStackTrace();
//    	}
//
//    	model.addAttribute("xmlString", xmlString);
//
//		return "common/commonXml";
//	}

}
