package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.CommonCodeService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class CommonCodeController {

	@Resource(name = "CommonCodeService")
    private CommonCodeService commonCodeService;
	
	/** log */
    protected static final Log LOG = LogFactory.getLog(CommonCodeController.class);
    
    @RequestMapping(value="/code/optionList.do")
	public String codeList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));

    	CommonList commonCodeList = commonCodeService.getCommonCodeList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "code/optionList";
	}
}
