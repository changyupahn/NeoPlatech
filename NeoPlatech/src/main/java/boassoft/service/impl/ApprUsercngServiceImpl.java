package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.ApprAssetMapper;
import boassoft.mapper.ApprRqstMapper;
import boassoft.mapper.ApprUsercngMapper;
import boassoft.mapper.BatchMapper;
import boassoft.mapper.UserMapper;
import boassoft.service.ApprUsercngService;
import boassoft.service.AssetHistoryService;
import boassoft.service.SndMisService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("ApprUsercngService")
public class ApprUsercngServiceImpl extends EgovAbstractServiceImpl implements ApprUsercngService{

	@Resource(name="apprUsercngMapper")
    private ApprUsercngMapper apprUsercngMapper;

	@Resource(name="apprRqstMapper")
    private ApprRqstMapper apprRqstMapper;

	@Resource(name = "userMapper")
    private UserMapper userMapper;

	@Resource(name = "batchMapper")
    private BatchMapper batchMapper;

	@Resource(name="apprAssetMapper")
    private ApprAssetMapper apprAssetMapper;

	@Resource(name="assetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "sndSeqIdGnrService")
    private EgovIdGnrService sndSeqIdGnrService;

	@Resource(name = "SndMisService")
    private SndMisService sndMisService;
	
	@Override
	public CommonList getApprUsercngList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprUsercngMapper.getApprUsercngList(cmap);
		list.totalRow = apprUsercngMapper.getApprUsercngListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getApprUsercngAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprUsercngMapper.getApprUsercngAssetList(cmap);
		list.totalRow = apprUsercngMapper.getApprUsercngAssetListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprUsercngView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprUsercngMapper.getApprUsercngView(cmap);
	}

	@Override
	public int insertApprUsercng(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//신청자 정보
    	CommonMap param = new CommonMap();
    	param.put("userNo", cmap.getString("rqstUserNo"));
    	CommonMap rqstUserView = userMapper.getUserView(param);

    	//부서장 정보
    	cmap.put("rqstTopUserNo", rqstUserView.getString("deptHeadUserNo"));
    	cmap.put("rqstTopUserName", rqstUserView.getString("deptHeadUserName"));

    	//인수자 정보
    	param.put("userNo", cmap.getString("aucUserNo"));
    	CommonMap aucUserView = userMapper.getUserView(param);

    	//인수자 부서장 정보
    	param.put("userNo", aucUserView.getString("deptHeadUserNo"));
    	CommonMap aucTopUserView = userMapper.getUserView(param);

    	if (rqstUserView.isEmpty()
    			|| aucUserView.isEmpty()
    			|| aucTopUserView.isEmpty()) {
    		return 0;
    	}

    	//승인 신청 정보
    	apprRqstMapper.insertApprRqst(cmap);

    	//인수인계 정보
    	apprUsercngMapper.insertApprUsercng(cmap);

    	//자산담당자면 바로 결재승인 처리
    	//cmap.put("ssGrantRead", "GRANT_MGR");
    	if ("GRANT_MGR".equals(cmap.getString("ssGrantRead"))) {
    		//승인상태 (승인완료)
    		cmap.put("rqstStatusCd", "3");

    		//승인상태변경
    		batchMapper.updateApprRqst(cmap);

    		//승인일자수정
    		cmap.put("aucCompDt", DateUtil.getFormatDate("yyyyMMdd"));
    		apprUsercngMapper.updateApprUsercng2(cmap);

    		//자산사용자 변경 처리
    		this.updateApprUsercngConfirm(cmap);
    	}

//    	//승인 (MIS)
//    	String rqstno = cmap.getString("rqstno");
//    	String dutysrlno = "929"; //업무구분코드 (929:인수인계신청, 930:불용신청, 931:반출신청, 932:반출연장신청, 933:자산반입신청)
//    	String docdivcd = "0030170"; //0030170:인수인계, 0030171:불용신청, 0030172:반출신청, 0030173:반출연장신청, 0030174:자산반입신청
//    	String iframeaddr = EgovProperties.getProperty("Globals.Appr.Domain") + "/kp7000/kp7010.do?rqstno=" + rqstno;
//    	ApprHttpUtil.Send(rqstno, dutysrlno, docdivcd, iframeaddr, rqstUserView, aucUserView, aucTopUserView, rqstUserView.getString("grantNo"));

    	//승인 자산 목록
    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

    	//승인신청 히스토리
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);

    		String tmpStr = "승인대기중";
    		if ("GRANT_MGR".equals(cmap.getString("ssGrantRead"))) {
    			tmpStr = "승인완료";
    		}

    		cmap.put("assetSeq", data.getString("assetSeq"));
        	cmap.put("histTypeCd", "4");
        	cmap.put("histContent", String.format("사용자 [%s]에서 [%s]로 인수인계신청 (승인대기중)"
    				, data.getString("userName")
    				, aucUserView.getString("userName")
    				));
        	assetHistoryService.insertAssetHistory(cmap);
    	}

    	//변경정보 MIS전송
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);
	    	CommonMap sndMap = new CommonMap();
	    	sndMap.put("sndSeq", sndSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
	    	sndMap.put("sndDiv", "1"); //1:정보변경, 2:불용신청, 3:불용승인, 4:부외자산변경
	    	sndMap.put("assetNo", data.getString("etisAssetNo").replaceAll("-",""));
	    	sndMap.put("mgtDeptCd", data.getString("posNo"));
	    	sndMap.put("useDeptCd", data.getString("deptNo"));
	    	sndMap.put("useEmpNo", data.getString("userNo"));
	    	sndMap.put("assetStatusCd", null);
	    	sndMap.put("disuseDt", null);
	    	sndMap.put("disuseApprovalDt", null);
	    	sndMap.put("outAssetYn", null);
	    	sndMap.put("sndYn", "N");
	    	sndMisService.insertSndMis(sndMap);
    	}

    	return 1;
	}

	@Override
	public void updateApprUsercng(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprUsercngMapper.updateApprUsercng(cmap);
	}

	@Override
	public void updateApprUsercng2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprUsercngMapper.updateApprUsercng2(cmap);
	}

	@Override
	public void deleteApprUsercng(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprUsercngMapper.deleteApprUsercng(cmap);
	}

	@Override
	public void updateApprUsercngConfirm(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//사용자 조회
				CommonMap apprUsercngView = batchMapper.getApprUsercngView(cmap);

				if (!apprUsercngView.isEmpty()) {

					cmap.put("topDeptNo", apprUsercngView.getString("aucTopDeptNo"));
					cmap.put("topDeptName", apprUsercngView.getString("aucTopDeptName"));
					cmap.put("deptNo", apprUsercngView.getString("aucDeptNo"));
					cmap.put("deptName", apprUsercngView.getString("aucDeptName"));
					cmap.put("posNo", apprUsercngView.getString("aucDeptNo"));
					cmap.put("posName", apprUsercngView.getString("aucDeptName"));
					cmap.put("userNo", apprUsercngView.getString("aucUserNo"));
					cmap.put("userName", apprUsercngView.getString("aucUserName"));

					int updateCnt2 = batchMapper.updateAssetUsercng(cmap);

					if (updateCnt2 > 0) {
						//승인 자산 목록
				    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

				    	//승인신청 히스토리
				    	for (int k=0; k<dataList.size(); k++) {
				    		CommonMap data = dataList.getMap(k);

				    		cmap.put("assetSeq", data.getString("assetSeq"));
				    		cmap.put("histTypeCd", "4");
				    		cmap.put("histContent", String.format("사용자 [%s]에서 [%s]로 인수인계 (승인완료)"
				    				, data.getString("userName")
				    				, apprUsercngView.getString("aucUserName")
				    				));
				    		cmap.put("frstRegisterId", data.getString("registId"));
				        	assetHistoryService.insertAssetHistory(cmap);
				    	}
					}
				}
	}

}
