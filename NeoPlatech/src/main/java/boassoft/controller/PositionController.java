package boassoft.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import boassoft.service.PositionService;

@Controller
public class PositionController {

	@Resource(name = "positionService")
    private PositionService positionService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(PositionController.class);
}
