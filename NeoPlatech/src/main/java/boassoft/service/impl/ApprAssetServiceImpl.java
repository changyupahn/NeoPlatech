package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import boassoft.mapper.ApprAssetMapper;
import boassoft.mapper.AssetMapper;
import boassoft.mapper.BatchMapper;
import boassoft.mapper.SndMisMapper;
import boassoft.service.ApprAssetService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.MessageUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;


@Service("apprAssetService")
public class ApprAssetServiceImpl extends EgovAbstractServiceImpl implements ApprAssetService {

	@Resource(name="ApprAssetMapper")
    private ApprAssetMapper apprAssetMapper;

	@Resource(name="AssetMapper")
    private AssetMapper assetMapper;

	@Resource(name="BatchMapper")
    private BatchMapper batchMapper;

	@Resource(name="SndMisMapper")
    private SndMisMapper sndMisMapper;

	@Resource(name = "sndSeqIdGnrService")
    private EgovIdGnrService sndSeqIdGnrService;
	
	@Override
	public CommonList getApprAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprAssetMapper.getApprAssetList(cmap);
		list.totalRow = apprAssetMapper.getApprAssetListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprAssetView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprAssetMapper.getApprAssetView(cmap);
	}

	@Override
	public void insertApprAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//신청번호 생성
    	cmap.put("rqstno", MessageUtils.getRqstNo());

    	apprAssetMapper.insertApprAsset(cmap);
	}

	@Override
	public int insertApprAssetAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("rqstno", cmap.getString("rqstno"));
    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("apprType", cmap.getString("apprType"));
    		param.put("disuseProcDt", cmap.getString("disuseProcDt"));
    		param.put("disuseProcTypeCd", cmap.getString("disuseProcTypeCd"));
    		param.put("disuseProcCont", cmap.getString("disuseProcCont"));
    		param.put("disuseProcAmt", cmap.getString("disuseProcAmt"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		apprAssetMapper.insertApprAsset(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updateApprAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprAssetMapper.updateApprAsset(cmap);
	}

	@Override
	public int updateApprAssetAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));
    		param.put("disuseYn", "Y");

    		resultCnt += apprAssetMapper.updateApprAsset(param);
    		apprAssetMapper.updateAsset(param);

//    		//자산상태변경 PRP000502, PRP000503
//    		param.put("assetStatusCd", param.getString("disuseProcTypeCd"));
//			batchDAO.updateAssetDisuse(param);

    		CommonMap viewData = assetMapper.getAssetDetail(param);

    		if (!viewData.isEmpty()) {
	    		//변경정보 MIS전송
		    	CommonMap sndMap = new CommonMap();
		    	sndMap.put("sndSeq", sndSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
		    	sndMap.put("sndDiv", "3"); //1:정보변경, 2:불용신청, 3:불용승인, 4:부외자산변경
		    	sndMap.put("assetNo", viewData.getString("etisAssetNo").replaceAll("-",""));
		    	sndMap.put("mgtDeptCd", null);
		    	sndMap.put("useDeptCd", null);
		    	sndMap.put("useEmpNo", null);
		    	sndMap.put("assetStatusCd", param.getString("disuseProcTypeCd"));
		    	sndMap.put("disuseDt", null);
		    	sndMap.put("disuseApprovalDt", param.getString("disuseProcDt").replaceAll("\\D", ""));
		    	sndMap.put("outAssetYn", null);
		    	sndMap.put("sndYn", "N");
		    	sndMisMapper.insertSndMis(sndMap);
    		}
    	}

    	return resultCnt;
	}

	@Override
	public void deleteApprAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprAssetMapper.deleteApprAsset(cmap);
	}

	@Override
	public int updateAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprAssetMapper.updateAsset(cmap);
	}

}
