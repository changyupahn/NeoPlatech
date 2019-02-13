package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ReqstPrintMapper;
import boassoft.service.ReqstPrintService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("ReqstPrintService")
public class ReqstPrintServiceImpl extends EgovAbstractServiceImpl implements ReqstPrintService{

	@Resource(name="ReqstPrintMapper")
    private ReqstPrintMapper reqstPrintMapper;
	
	@Override
	public void insertReqstPrint(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		reqstPrintMapper.insertReqstPrint(cmap);
	}

	@Override
	public CommonList getReqstPrintList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
    	
    	CommonList list = reqstPrintMapper.getReqstPrintList(cmap);
    	list.totalRow = reqstPrintMapper.getReqstPrintListCnt(cmap);
		return list;
	}

	@Override
	public int getReqstPrintListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return reqstPrintMapper.getReqstPrintListCnt(cmap);
	}

	@Override
	public void deleteReqstPrint(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		reqstPrintMapper.deleteReqstPrint(cmap);
	}

	@Override
	public int updatePrintStatusCd_R(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return reqstPrintMapper.updatePrintStatusCd_R(cmap);
	}

}
