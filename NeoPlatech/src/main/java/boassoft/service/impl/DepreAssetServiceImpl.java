package boassoft.service.impl;

import javax.annotation.Resource;

import boassoft.mapper.DepreAssetMapper;
import boassoft.service.DepreAssetService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

public class DepreAssetServiceImpl extends EgovAbstractServiceImpl implements DepreAssetService {

	@Resource(name="depreAssetMapper")
    private DepreAssetMapper depreAssetMapper;
	
	@Override
	public int getDepreAssetTargetCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.getDepreAssetTargetCnt(cmap);
	}

	@Override
	public int insertDepreAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
    	//감가상각처리
    	result = depreAssetMapper.insertDepreAsset(cmap);
    	//감가상각잔존가 1원 이하 0원으로 처리
    	depreAssetMapper.updateDepreAsset(cmap);
    	return result;
	}

	@Override
	public CommonList getDepreAssetTotal(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList resultList = depreAssetMapper.getDepreAssetTotal(cmap);

		//합계 조회
    	Double sum01 = 0.0;
		Double sum02 = 0.0;
		Double sum03 = 0.0;
		Double sum04 = 0.0;
		Double sum05 = 0.0;
		Double sum06 = 0.0;
		Double sum07 = 0.0;
		Double sum08 = 0.0;
		Double sum09 = 0.0;
		Double sum10 = 0.0;
		Double sum11 = 0.0;
		Double sum12 = 0.0;
		Double sum13 = 0.0;
    	for (int i=0; i<resultList.size(); i++) {
    		CommonMap result = resultList.getMap(i);

    		String col01 = result.getString("당년도기초수량", "0");
			String col02 = result.getString("당년도기초금액", "0");
			String col03 = result.getString("당년도증가수량", "0");
			String col04 = result.getString("당년도증가금액", "0");
			String col05 = result.getString("당년도불용수량", "0");
			String col06 = result.getString("당년도불용금액", "0");
			String col07 = result.getString("당년도기말수량", "0");
			String col08 = result.getString("당년도기말금액", "0");
			String col09 = result.getString("전년도누계감가상각비", "0");
			String col10 = result.getString("당년도증가감가상각비", "0");
			String col11 = result.getString("당년도감소감가상각비", "0");
			String col12 = result.getString("당년도누계감가상각비", "0");
			String col13 = result.getString("잔존가액", "0");

			sum01 += Double.parseDouble(col01);
			sum02 += Double.parseDouble(col02);
			sum03 += Double.parseDouble(col03);
			sum04 += Double.parseDouble(col04);
			sum05 += Double.parseDouble(col05);
			sum06 += Double.parseDouble(col06);
			sum07 += Double.parseDouble(col07);
			sum08 += Double.parseDouble(col08);
			sum09 += Double.parseDouble(col09);
			sum10 += Double.parseDouble(col10);
			sum11 += Double.parseDouble(col11);
			sum12 += Double.parseDouble(col12);
			sum13 += Double.parseDouble(col13);
    	}
    	CommonMap resultSum = new CommonMap();
    	resultSum.put("결산과목코드", "");
    	resultSum.put("결산과목명", "합계");
    	resultSum.put("당년도기초수량", sum01.toString());
    	resultSum.put("당년도기초금액", sum02.toString());
    	resultSum.put("당년도증가수량", sum03.toString());
    	resultSum.put("당년도증가금액", sum04.toString());
    	resultSum.put("당년도불용수량", sum05.toString());
    	resultSum.put("당년도불용금액", sum06.toString());
    	resultSum.put("당년도기말수량", sum07.toString());
    	resultSum.put("당년도기말금액", sum08.toString());
    	resultSum.put("전년도누계감가상각비", sum09.toString());
    	resultSum.put("당년도증가감가상각비", sum10.toString());
    	resultSum.put("당년도감소감가상각비", sum11.toString());
    	resultSum.put("당년도누계감가상각비", sum12.toString());
    	resultSum.put("잔존가액", sum13.toString());
    	resultList.add(resultSum);

		return resultList;
	}

	@Override
	public CommonMap getDepreAssetTotalSum(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.getDepreAssetTotalSum(cmap);
	}

	@Override
	public CommonList getDepreAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.getDepreAssetList(cmap);
	}

	@Override
	public int updateDepreAssetRemain(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.updateDepreAssetRemain(cmap);
	}

	@Override
	public int updateDepreAssetRemain2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.updateDepreAssetRemain2(cmap);
	}

	@Override
	public int updateDepreAssetRemain2Year(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.updateDepreAssetRemain2Year(cmap);
	}

	@Override
	public int deleteDepreAsset2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreAssetMapper.deleteDepreAsset2(cmap);
	}

}
