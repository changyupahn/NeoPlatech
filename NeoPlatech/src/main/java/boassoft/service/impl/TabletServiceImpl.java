package boassoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import boassoft.common.CommonXmlList;
import boassoft.common.GoodsXmlList;
import boassoft.mapper.TabMapper;
import boassoft.mapper.TabletMapper;
import boassoft.service.TabletService;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

public class TabletServiceImpl extends EgovAbstractServiceImpl implements TabletService{

	@Resource(name="TabletMapper")
    private TabletMapper tabletMapper;

	@SuppressWarnings("unchecked")
	@Override
	public CommonXmlList getGoodsShipmentOutListXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
		
		cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		
		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabletMapper.getGoodsShipmentOutListXml(cmap) );
		return null;
	}
	
	@Override
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabletMapper.getGoodsShipmentOutListXmlCnt(cmap);
	}


	@Override
	public CommonXmlList getSubsiDiaryReceiptListXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGoodsShipment(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("rlst_date"))||!"".equals(cmap.getString("rlst_id")) ) {
			if (!"".equals(cmap.getString("read_date"))||!"".equals(cmap.getString("read_id")) ) {
			tabletMapper.updateIGoodsShipmentOut(cmap);
			}
		}
	}

	
}
