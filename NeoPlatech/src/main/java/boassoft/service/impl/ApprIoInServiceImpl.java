package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import boassoft.mapper.ApprAssetMapper;
import boassoft.mapper.ApprIoInAssetMapper;
import boassoft.mapper.ApprIoInMapper;
import boassoft.mapper.ApprRqstMapper;
import boassoft.mapper.BatchMapper;
import boassoft.mapper.UserMapper;
import boassoft.service.ApprIoInService;
import boassoft.service.AssetHistoryService;
import boassoft.util.ApprHttpUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


@Service("apprIoInService")
public class ApprIoInServiceImpl extends EgovAbstractServiceImpl implements ApprIoInService{

	@Resource(name="ApprIoInMapper")
    private ApprIoInMapper apprIoInMapper;

	@Resource(name="ApprIoInAssetMapper")
    private ApprIoInAssetMapper apprIoInAssetMapper;

	@Resource(name="ApprRqstMapper")
    private ApprRqstMapper apprRqstMapper;

	@Resource(name = "UserMapper")
    private UserMapper userMapper;

	@Resource(name="ApprAssetMapper")
    private ApprAssetMapper apprAssetMapper;

	@Resource(name="assetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name="BatchMapper")
    private BatchMapper batchMapper;

	
	@Override
	public CommonList getApprIoInList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprIoInMapper.getApprIoInList(cmap);
		list.totalRow = apprIoInMapper.getApprIoInListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprIoInView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprIoInMapper.getApprIoInView(cmap);
	}

	/** 승인 반입신청 (사용안함) */
	@Override
	public int insertApprIoIn(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//신청자 정보
    	CommonMap param = new CommonMap();
    	param.put("userNo", cmap.getString("rqstUserNo"));
    	CommonMap rqstUserView = userMapper.getUserView(param);

		//승인상태 (승인진행중)
		cmap.put("rqstStatusCd", "2");

    	//반입신청
		apprIoInMapper.insertApprIoIn(cmap);

		//승인 (MIS)
    	String rqstno = cmap.getString("inRqstno");
    	String dutysrlno = "933"; //업무구분코드 (929:인수인계신청, 930:불용신청, 931:반출신청, 932:반출연장신청, 933:자산반입신청)
    	String docdivcd = "0030174"; //0030170:인수인계, 0030171:불용신청, 0030172:반출신청, 0030173:반출연장신청, 0030174:자산반입신청
    	String iframeaddr = EgovProperties.getProperty("Globals.Appr.Domain") + "/kp7000/kp7050.do?rqstno=" + rqstno;
    	ApprHttpUtil.Send(rqstno, dutysrlno, docdivcd, iframeaddr, rqstUserView, null, null, rqstUserView.getString("grantNo"));

    	//승인 자산 목록
    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

    	//승인신청 히스토리
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);

    		cmap.put("assetSeq", data.getString("assetSeq"));
        	cmap.put("histTypeCd", "5");
        	cmap.put("histContent", String.format("반입신청 - %s (승인)", cmap.getString("inRemark")));
        	assetHistoryService.insertAssetHistory(cmap);
    	}

    	return 1;
	}

	@Override
	public int insertApprIoInMng(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub

//		//신청자 정보
//    	CommonMap param = new CommonMap();
//    	param.put("userNo", cmap.getString("rqstUserNo"));

    	//반입신청
		apprIoInMapper.insertApprIoIn(cmap);

		//반입신청자산
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("inRqstno", cmap.getString("inRqstno"));
    		param.put("rqstno", cmap.getString("rqstno"));

    		apprIoInAssetMapper.insertApprIoInAsset(param);
    	}

    	//전체 자산이 반입신청 되었으면 승인완료 처리
    	CommonMap viewData2 = apprIoInAssetMapper.getApprIoInAssetAllYn(cmap);
    	if ("Y".equals(viewData2.getString("allInYn"))) {
    		//승인상태 (승인완료)
    		cmap.put("rqstStatusCd", "3");

    		//승인상태변경
    		batchMapper.updateApprRqst(cmap);
    	}

		//자산상태변경
    	batchMapper.updateAssetIoIn(cmap);

    	//승인 자산 목록
    	CommonList dataList = apprAssetMapper.getApprAssetList(cmap);

    	//승인신청 히스토리
    	for (int i=0; i<dataList.size(); i++) {
    		CommonMap data = dataList.getMap(i);

    		cmap.put("assetSeq", data.getString("assetSeq"));
        	cmap.put("histTypeCd", "5");
        	cmap.put("histContent", String.format("반입처리 - %s", cmap.getString("inRemark")));
        	assetHistoryService.insertAssetHistory(cmap);
    	}

    	return 1;
	}

	@Override
	public void updateApprIoIn(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprIoInMapper.updateApprIoIn(cmap);
	}

	@Override
	public void deleteApprIoIn(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprIoInMapper.deleteApprIoIn(cmap);
	}

}
