package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.LabelMapper;
import boassoft.service.LabelService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("LabelService")
public class LabelServiceImpl extends EgovAbstractServiceImpl implements LabelService {

	@Resource(name="labelMapper")
    private LabelMapper labelMapper;
	
	@Override
	public int insertLabel(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
        int labelSeq = 0;
    	
    	String[] objTitle = cmap.getArray("objTitle[]");
    	String[] objLcationNo = cmap.getArray("objLcationNo[]");
    	String[] objType = cmap.getArray("objType[]");
    	String[] objAamSeq = cmap.getArray("objAamSeq[]");
    	
    	if (objTitle != null
    			&& objTitle.length > 0
    			&& objTitle.length == objLcationNo.length
    			&& objTitle.length == objType.length
    			&& objTitle.length == objAamSeq.length) {
    		
    		//라벨 등록
    		labelSeq = labelMapper.insertLabel(cmap);
    		
    		//라벨 항목 등록    		
    		for (int i=0; i<objAamSeq.length; i++) {
    			if (!"".equals(objAamSeq[i])) {
	    			cmap.put("labelSeq", labelSeq);
	    			cmap.put("objAamSeq", objAamSeq[i]);
	    			cmap.put("objType", objType[i]);
	    			cmap.put("objTitle", objTitle[i]);
	    			cmap.put("objLcationNo", objLcationNo[i]);
	        		//(#labelSeq#, #objAamSeq#, #objType#, #objTitle#, #objLcationNo#)
	    			labelMapper.insertLabelCol(cmap);
    			}
    		}
    	}
		
		return labelSeq;
	}

	@Override
	public void insertLabelCol(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		labelMapper.insertLabelCol(cmap);
	}

	@Override
	public void deleteLabel(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		labelMapper.deleteLabel(cmap);
	}

	@Override
	public CommonList getLabelList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = labelMapper.getLabelList(cmap);
		list.totalRow = labelMapper.getLabelListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getLabelColList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = labelMapper.getLabelColList(cmap);
		return list;
	}

}
