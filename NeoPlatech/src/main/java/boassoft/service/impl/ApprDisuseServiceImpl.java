package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.ApprAssetMapper;
import boassoft.mapper.ApprDisuseMapper;
import boassoft.mapper.ApprRqstMapper;
import boassoft.mapper.BatchMapper;
import boassoft.mapper.SndMisMapper;
import boassoft.mapper.UserMapper;
import boassoft.service.ApprDisuseService;
import boassoft.service.AssetHistoryService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("ApprDisuseService")
public class ApprDisuseServiceImpl extends EgovAbstractServiceImpl implements  ApprDisuseService {

	@Resource(name="apprDisuseMapper")
    private ApprDisuseMapper apprDisuseMapper;

	@Resource(name="apprRqstMapper")
    private ApprRqstMapper apprRqstMapper;

	@Resource(name = "userMapper")
    private UserMapper userMapper;

	@Resource(name = "batchMapper")
    private BatchMapper batchMapper;

	@Resource(name="apprAssetMapper")
    private ApprAssetMapper apprAssetMapper;

	@Resource(name="sndMisMapper")
    private SndMisMapper sndMisMapper;

	@Resource(name="AssetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "sndSeqIdGnrService")
    private EgovIdGnrService sndSeqIdGnrService;
	
	@Override
	public CommonList getApprDisuseList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprDisuseMapper.getApprDisuseList(cmap);
		list.totalRow = apprDisuseMapper.getApprDisuseListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getApprDisuseAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprDisuseMapper.getApprDisuseAssetList(cmap);
		list.totalRow = apprDisuseMapper.getApprDisuseAssetListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprDisuseView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprDisuseMapper.getApprDisuseView(cmap);
	}

	@Override
	public int insertApprDisuse(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//신청자 정보
    	CommonMap param = new CommonMap();
    	param.put("userNo", cmap.getString("rqstUserNo"));
    	CommonMap rqstUserView = userMapper.getUserView(param);

    	//부서장 정보
    	cmap.put("rqstTopUserNo", rqstUserView.getString("deptHeadUserNo"));
    	cmap.put("rqstTopUserName", rqstUserView.getString("deptHeadUserName"));

    	//승인상태 (승인진행중)
    	cmap.put("rqstStatusCd", "2");

    	//승인 신청 정보
    	apprRqstMapper.insertApprRqst(cmap);

    	//불용신청 정보
    	apprDisuseMapper.insertApprDisuse(cmap);

    	//자산담당자면 바로 결재승인 처리
    	//cmap.put("ssGrantRead", "GRANT_MGR");
    	if ("GRANT_MGR".equals(cmap.getString("ssGrantRead"))) {
    		//승인상태 (승인완료)
    		cmap.put("rqstStatusCd", "3");

    		//승인상태변경
    		batchMapper.updateApprRqst(cmap);

    		//승인일자수정
    		cmap.put("disuseCompDt", DateUtil.getFormatDate("yyyyMMdd"));
    		batchMapper.updateApprDisuse(cmap);

			//자산상태변경
    		cmap.put("disuseDt", DateUtil.getFormatDate("yyyyMMdd"));
			cmap.put("assetStatusCd", "PRP000505");
			batchMapper.updateAssetDisuse(cmap);

    	} else {
    		//자산상태변경
    		cmap.put("disuseDt", DateUtil.getFormatDate("yyyyMMdd"));
			cmap.put("assetStatusCd", "PRP000505");
			batchMapper.updateAssetDisuse(cmap);
    	}

    	//승인 자산 목록
    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

    	//승인신청 히스토리
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);

    		cmap.put("assetSeq", data.getString("assetSeq"));
        	cmap.put("histTypeCd", "6");
        	cmap.put("histContent", String.format("불용신청 - %s (승인)", cmap.getString("reason")));
        	assetHistoryService.insertAssetHistory(cmap);
    	}

    	//변경정보 MIS전송
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);
	    	CommonMap sndMap = new CommonMap();
	    	sndMap.put("sndSeq", sndSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
	    	sndMap.put("sndDiv", "2"); //1:정보변경, 2:불용신청, 3:불용승인, 4:부외자산변경
	    	sndMap.put("assetNo", data.getString("etisAssetNo").replaceAll("-",""));
	    	sndMap.put("mgtDeptCd", null);
	    	sndMap.put("useDeptCd", null);
	    	sndMap.put("useEmpNo", null);
	    	sndMap.put("assetStatusCd", cmap.getString("assetStatusCd"));
	    	sndMap.put("disuseDt", cmap.getString("disuseCompDt").replaceAll("\\D", ""));
	    	sndMap.put("disuseApprovalDt", null);
	    	sndMap.put("outAssetYn", null);
	    	sndMap.put("sndYn", "N");
	    	sndMisMapper.insertSndMis(sndMap);
    	}

    	return 1;
	}

	@Override
	public void updateApprDisuse(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprDisuseMapper.updateApprDisuse(cmap);
	}

	@Override
	public void deleteApprDisuse(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprDisuseMapper.deleteApprDisuse(cmap);
	}

}
