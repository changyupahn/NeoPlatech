package boassoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.common.CommonXmlList;
import boassoft.common.GoodsXml;
import boassoft.common.GoodsXmlList;
import boassoft.mapper.TabletMapper;
import boassoft.service.TabletService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("tabletService")
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

	
	@Override
	public GoodsXmlList getPackingReceptListXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
		
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		
		System.out.println("  dataOrder " + " : " +cmap.getString("dataOrder", ""));
		System.out.println(" dataOrderArrow " + " : " + cmap.getString("dataOrderArrow", ""));
    	System.out.println(" pageLimit " + " : " + cmap.getString("pageLimit", ""));
    	System.out.println(" pageSize " + " : " + cmap.getString("pageSize", ""));
    	System.out.println(" pageStartNum " + " : " + cmap.getString("pageStartNum", ""));
    	System.out.println(" pageEndNum " + " : " + cmap.getString("pageEndNum", ""));
    	
		GoodsXmlList list = new GoodsXmlList();		
		
        list.addList( (List)tabletMapper.getPackingReceptListXml(cmap) );
		
		System.out.println("  ccc " + " : " +list.size());
		if(list != null ){
			for(int i = 0; i < list.size();i++){
				
				System.out.println("  ddd " + " : " +list.size());
			   
			}
			
		}
		
		return list;
				
	}

	@Override
	public CommonList getPackingShipmentOutListXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
		
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		
		CommonList list = tabletMapper.getPackingShipmentOutListXml(cmap);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GoodsXmlList goodsShipmentDetailKitItemOutXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("  aaa " + " : " +cmap);
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
    	System.out.println("  bbb " + " : " +cmap);
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		System.out.println("  ccc " + " : " +cmap);
		GoodsXmlList list = new GoodsXmlList();
		list.addList( (List<GoodsXml>)tabletMapper.goodsShipmentDetailKitItemOutXml(cmap) );
		
		System.out.println("  ddd " + " : " +list.size());
		if(list != null ){
			for(int i = 0; i < list.size();i++){
				
				System.out.println("  eee " + " : " +list.size());
			   
			}
			
		}
		return list;
	}

	
	@Override
	public GoodsXmlList goodsShipmentDetailRefItemOutXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("  aaa " + " : " +cmap);
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
    	System.out.println("  bbb " + " : " +cmap);
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		System.out.println("  ccc " + " : " +cmap);
		GoodsXmlList list = new GoodsXmlList();
		list.addList( (List<GoodsXml>)tabletMapper.goodsShipmentDetailRefItemOutXml(cmap) );
		
		System.out.println("  ddd " + " : " +list.size());
		if(list != null ){
			for(int i = 0; i < list.size();i++){
				
				System.out.println("  eee " + " : " +list.size());
			   
			}
			
		}
		return list;
	}

	
	@Override
	public GoodsXmlList optionVendorListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("  aaa " + " : " +cmap);
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
    	System.out.println("  bbb " + " : " +cmap);
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		System.out.println("  ccc " + " : " +cmap);
		GoodsXmlList list = new GoodsXmlList();
						
		list.addList( (List<GoodsXml>)tabletMapper.optionVendorListXml(cmap) );
		
		System.out.println("  ddd " + " : " +list.size());
		if(list != null ){
			for(int i = 0; i < list.size();i++){
				
				System.out.println("  eee " + " : " +list.size());
			   
			}
			
		}
		return list;
	}

	@Override
	public GoodsXmlList optionPartNoListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("  aaa " + " : " +cmap);
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
    	System.out.println("  bbb " + " : " +cmap);
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		System.out.println("  ccc " + " : " +cmap);
		
		GoodsXmlList list = new GoodsXmlList();
		
		list.addList( (List<GoodsXml>)tabletMapper.optionPartNoListXml(cmap) );
		
		System.out.println("  ddd " + " : " +list.size());
		if(list != null ){
			for(int i = 0; i < list.size();i++){
				
				System.out.println("  eee " + " : " +list.size());
			   
			}
			
		}
		
		return list;
	}

	@Override
	public CommonList getGoodsShipmentOutDetailListXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));
		
    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");
		
		CommonList list = tabletMapper.getPackingShipmentOutDetailListXml(cmap);
		
		return list;
	}

	
	
}
