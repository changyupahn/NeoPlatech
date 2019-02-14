package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.ApprAssetMapper;
import boassoft.mapper.ApprIoOutMapper;
import boassoft.mapper.ApprRqstMapper;
import boassoft.mapper.UserMapper;
import boassoft.service.ApprIoOutService;
import boassoft.service.AssetHistoryService;
import boassoft.util.ApprHttpUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("apprIoOutService")
public class ApprIoOutServiceImpl extends EgovAbstractServiceImpl implements ApprIoOutService{

	@Resource(name="ApprIoOutMapper")
    private ApprIoOutMapper apprIoOutMapper;

	@Resource(name="ApprRqstMapper")
    private ApprRqstMapper apprRqstMapper;

	@Resource(name = "UserMapper")
    private UserMapper userMapper;

	@Resource(name="ApprAssetMapper")
    private ApprAssetMapper apprAssetMapper;

	@Resource(name="assetHistoryService")
    private AssetHistoryService assetHistoryService;
	
	@Override
	public CommonList getApprIoOutList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprIoOutMapper.getApprIoOutList(cmap);
		list.totalRow = apprIoOutMapper.getApprIoOutListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getApprIoOutAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprIoOutMapper.getApprIoOutAssetList(cmap);
		list.totalRow = apprIoOutMapper.getApprIoOutAssetListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprIoOutView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprIoOutMapper.getApprIoOutView(cmap);
	}

	@Override
	public int insertApprIoOut(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//신청자 정보
    	CommonMap param = new CommonMap();
    	param.put("userNo", cmap.getString("rqstUserNo"));
    	CommonMap rqstUserView = userMapper.getUserView(param);

    	//부서장 정보
    	cmap.put("rqstTopUserNo", rqstUserView.getString("deptHeadUserNo"));
    	cmap.put("rqstTopUserName", rqstUserView.getString("deptHeadUserName"));

    	//승인 신청 정보
    	apprRqstMapper.insertApprRqst(cmap);

    	//반출신청 정보
    	apprIoOutMapper.insertApprIoOut(cmap);

    	//승인 (MIS)
    	String rqstno = cmap.getString("rqstno");
    	String dutysrlno = "931"; //업무구분코드 (929:인수인계신청, 930:불용신청, 931:반출신청, 932:반출연장신청, 933:자산반입신청)
    	String docdivcd = "0030172"; //0030170:인수인계, 0030171:불용신청, 0030172:반출신청, 0030173:반출연장신청, 0030174:자산반입신청
    	String iframeaddr = EgovProperties.getProperty("Globals.Appr.Domain") + "/kp7000/kp7030.do?rqstno=" + rqstno;
    	ApprHttpUtil.Send(rqstno, dutysrlno, docdivcd, iframeaddr, rqstUserView, null, null, rqstUserView.getString("grantNo"));

    	//승인 자산 목록
    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

    	//승인신청 히스토리
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);

    		cmap.put("assetSeq", data.getString("assetSeq"));
        	cmap.put("histTypeCd", "5");
        	cmap.put("histContent", String.format("반출신청 - %s (승인)", cmap.getString("reason")));
        	assetHistoryService.insertAssetHistory(cmap);
    	}

    	return 1;
	}

	@Override
	public void updateApprIoOut(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprIoOutMapper.updateApprIoOut(cmap);
	}

	@Override
	public void deleteApprIoOut(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprIoOutMapper.deleteApprIoOut(cmap);
	}

}
