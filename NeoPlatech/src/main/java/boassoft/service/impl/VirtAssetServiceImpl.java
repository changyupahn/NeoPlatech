package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.mapper.ContractDtlMapper;
import boassoft.mapper.InspItemMapper;
import boassoft.mapper.VirtAssetMapper;
import boassoft.service.VirtAssetService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.StringUtil;

@Service("VirtAssetService")
public class VirtAssetServiceImpl extends EgovAbstractServiceImpl implements VirtAssetService {

	@Resource(name="virtAssetMapper")
    private VirtAssetMapper virtAssetMapper;

	@Resource(name="inspItemMapper")
    private InspItemMapper inspItemMapper;
	
	@Resource(name="contractDtlMapper")
    private ContractDtlMapper contractDtlMapper;
	
	@Resource(name = "assetSeqIdGnrService")
    private EgovIdGnrService assetSeqIdGnrService;

	@Override
	public String getVirtAssetNo(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		String assetNo = "";
		String assetTypeCd = cmap.getString("assetTypeCd");
		String aqusitDt = cmap.getString("aqusitDt").replaceAll("\\D", "");
		String aqusitYearShort = "";
		String aqusitTypeCd = cmap.getString("aqusitTypeCd");

		if (assetTypeCd.length() != 2)
			return "";

		if (aqusitDt.length() != 8)
			aqusitDt = DateUtil.getFormatDate("yyyyMMdd");

		if (aqusitTypeCd.length() != 1)
			aqusitTypeCd = "A";

		int yearShortNum1 = Integer.parseInt(aqusitDt.substring(2,3));
		int yearShortNum2 = Integer.parseInt(aqusitDt.substring(3,4));
		String[] yearShortStr = {"A","B","C","D","E","F","G","H","I","J"};
		//취득년도 (자산번호용, 2005년 = A5, 2017년 = B7, 2020년 = C0, 2035년 = D5)
		aqusitYearShort = yearShortStr[yearShortNum1] + "" + yearShortNum2;

		cmap.put("assetTypeCd", assetTypeCd);
		cmap.put("aqusitYearShort", aqusitYearShort);
		//자산일련번호 조회
		CommonMap virtmap = virtAssetMapper.getVirtAssetNum(cmap);
		//자산번호 생성
		assetNo = assetTypeCd + aqusitYearShort + StringUtil.lpad(virtmap.getString("assetNum"), 4, "0") + aqusitTypeCd;

		//생성된 자산번호의 자릿수가 9가 아니라면
		if (assetNo.length() != 9)
			return "";

		return assetNo;
	}

	@Override
	public String getVirtRfidNo(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		String rfidNo = "";

		String assetNo = cmap.getString("assetNo");
		if ("".equals(assetNo))
			return "";
		
		rfidNo = StringUtil.stringToHex(StringUtil.lpad(assetNo, 16, "0").substring(0,16));

		if (rfidNo.length() != 32)
			return "";

		return rfidNo;
	}

	@Override
	public CommonList getVirtAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = virtAssetMapper.getVirtAssetList(cmap);
		list.totalRow = virtAssetMapper.getVirtAssetListCnt(cmap);
		return list;
	}

	@Override
	public int getVirtAssetListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return virtAssetMapper.getVirtAssetListCnt(cmap);
	}

	@Override
	public int insertVirtAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//납품내역 조회
    	CommonList list = inspItemMapper.getInspItemList2(cmap);

    	for (int i=0; i<list.size(); i++) {
    		CommonMap data = list.getMap(i);

    		//구매신청자 정보 조회
    		CommonMap contractDtlView = contractDtlMapper.getContractDtlView(data);

    		data.put("topDeptNo", contractDtlView.getString("topDeptNo"));
    		data.put("topDeptName", contractDtlView.getString("topDeptName"));
    		data.put("deptNo", contractDtlView.getString("deptNo"));
    		data.put("deptName", contractDtlView.getString("deptName"));
    		data.put("userNo", contractDtlView.getString("userNo"));
    		data.put("userName", contractDtlView.getString("userName"));
    		data.put("posNo", "");
    		data.put("posName", "");
    		data.put("capiTypeCd", "");
    		data.put("contReqUserName", contractDtlView.getString("userName"));
    		data.put("contNo", contractDtlView.getString("contNo"));
    		data.put("contName", contractDtlView.getString("contName"));
    		data.put("frstRegisterId", cmap.getString("ssUserNo"));
    		data.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		//수량
    		int assetCnt = data.getInt("assetCnt");

    		//분할등록이 [예] 라면
    		if ("Y".equals(data.getString("divRegiYn"))) {
        		for (int k=0; k<assetCnt; k++) {

        			//취득일
            		data.put("aqusitDt", DateUtil.getFormatDate("yyyyMMdd"));
            		//자산번호
            		String assetNo = getVirtAssetNo(data);
            		data.put("assetNo", assetNo);
            		//자산순번
            		String assetSubNo = "00";
            		data.put("assetSubNo", assetSubNo);
            		//RFID번호
            		String rfidNo = getVirtRfidNo(data);
            		data.put("rfidNo", rfidNo);

            		data.put("assetSeq", assetSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
            		data.put("barcNo", "");
            		data.put("assetCnt", "1");
            		data.put("aqusitAmt", data.getString("aqusitUnitAmt"));
            		data.put("aqusitUnitAmt", data.getString("aqusitUnitAmt"));
            		data.put("aqusitRemainAmt", data.getString("aqusitUnitAmt"));

            		virtAssetMapper.insertVirtAsset(data);
            		resultCnt++;
        		}

    		} else {
    			//분할등록이 [아니요] 라면

    			//취득일
        		data.put("aqusitDt", DateUtil.getFormatDate("yyyyMMdd"));
        		//자산번호
        		String assetNo = getVirtAssetNo(data);
        		data.put("assetNo", assetNo);
        		//자산순번
        		String assetSubNo = "00";
        		data.put("assetSubNo", assetSubNo);
        		//RFID번호
        		String rfidNo = getVirtRfidNo(data);
        		data.put("rfidNo", rfidNo);

    			data.put("assetSeq", assetSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
        		data.put("barcNo", "");
        		data.put("assetCnt", data.getString("assetCnt"));
        		data.put("aqusitAmt", data.getString("aqusitAmt"));
        		data.put("aqusitUnitAmt", data.getString("aqusitUnitAmt"));
        		data.put("aqusitRemainAmt", data.getString("aqusitAmt"));

        		virtAssetMapper.insertVirtAsset(data);
        		resultCnt++;
    		}
    	}

    	return resultCnt;
	}

	@Override
	public int deleteVirtAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return virtAssetMapper.deleteVirtAsset(cmap);
	}

	@Override
	public int deleteVirtAssetAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("purno", cmap.getString("purno"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		virtAssetMapper.deleteVirtAsset(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updateVirtAssetConfirm(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return virtAssetMapper.updateVirtAssetConfirm(cmap);
	}

	@Override
	public int updateVirtAssetCancel(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return virtAssetMapper.updateVirtAssetCancel(cmap);
	}
}
