package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ApprAssetMapper;
import boassoft.mapper.ApprIoExtMapper;
import boassoft.mapper.ApprRqstMapper;
import boassoft.mapper.UserMapper;
import boassoft.service.ApprIoExtService;
import boassoft.service.AssetHistoryService;
import boassoft.util.ApprHttpUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("apprIoExtService")
public class ApprIoExtServiceImpl extends EgovAbstractServiceImpl implements ApprIoExtService {

	@Resource(name="ApprIoExtMapper")
    private ApprIoExtMapper apprIoExtMapper;

	@Resource(name="ApprRqstMapper")
    private ApprRqstMapper apprRqstMapper;

	@Resource(name = "UserMapper")
    private UserMapper userMapper;

	@Resource(name="ApprAssetMapper")
    private ApprAssetMapper apprAssetMapper;
	
	@Resource(name="assetHistoryService")
    private AssetHistoryService assetHistoryService;
	
	@Override
	public CommonList getApprIoExtList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprIoExtMapper.getApprIoExtList(cmap);
		list.totalRow = apprIoExtMapper.getApprIoExtListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprIoExtView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprIoExtMapper.getApprIoExtView(cmap);
	}

	@Override
	public int insertApprIoExt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//신청자 정보
    	CommonMap param = new CommonMap();
    	param.put("userNo", cmap.getString("rqstUserNo"));
    	CommonMap rqstUserView = userMapper.getUserView(param);

    	//승인상태 (승인진행중)
    	cmap.put("rqstStatusCd", "2");

    	//반입연장신청
    	apprIoExtMapper.insertApprIoExt(cmap);

    	//승인 (MIS)
    	String rqstno = cmap.getString("extRqstno");
    	String dutysrlno = "932"; //업무구분코드 (929:인수인계신청, 930:불용신청, 931:반출신청, 932:반출연장신청, 933:자산반입신청)
    	String docdivcd = "0030173"; //0030170:인수인계, 0030171:불용신청, 0030172:반출신청, 0030173:반출연장신청, 0030174:자산반입신청
    	String iframeaddr = EgovProperties.getProperty("Globals.Appr.Domain") + "/kp7000/kp7040.do?rqstno=" + rqstno;
    	ApprHttpUtil.Send(rqstno, dutysrlno, docdivcd, iframeaddr, rqstUserView, null, null, rqstUserView.getString("grantNo"));

    	//승인 자산 목록
    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

    	//승인신청 히스토리
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);

    		cmap.put("assetSeq", data.getString("assetSeq"));
        	cmap.put("histTypeCd", "5");
        	cmap.put("histContent", String.format("반입연장신청 - %s (승인)", cmap.getString("extReason")));
        	assetHistoryService.insertAssetHistory(cmap);
    	}

    	return 1;
	}

	@Override
	public void updateApprIoExt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprIoExtMapper.updateApprIoExt(cmap);
	}

	@Override
	public void deleteApprIoExt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprIoExtMapper.deleteApprIoExt(cmap);
	}

}
