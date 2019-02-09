package boassoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.common.CommonXmlList;
import boassoft.mapper.TabMapper;
import boassoft.service.TabService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("TabService")
public class TabServiceImpl extends EgovAbstractServiceImpl implements TabService{

	@Resource(name="tabMapper")
    private TabMapper tabMapper;
	
	@Override
	public CommonMap getRfidLoginView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getRfidLoginView(cmap);
	}

	@Override
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getInventoryLast(cmap);
	}

	@Override
	public CommonXmlList getAssetListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getAssetListXml(cmap) );
		return list;
	}

	@Override
	public int getAssetListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getAssetListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getInventoryListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getInventoryListXml(cmap) );
		return list;
	}

	@Override
	public int getInventoryListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getInventoryListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getInventoryDetailListXml(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getInventoryDetailListXml(cmap) );
		return list;
	}

	@Override
	public int getInventoryDetailListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getInventoryDetailListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getUserListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getUserListXml(cmap) );
		return list;
	}

	@Override
	public int getUserListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getUserListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getDeptListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getDeptListXml(cmap) );
		return list;
	}

	@Override
	public int getDeptListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getDeptListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getPosListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getPosListXml(cmap) );
		return list;
	}

	@Override
	public int getPosListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getPosListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getOrgListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getOrgListXml(cmap) );
		return list;
	}

	@Override
	public int getOrgListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getOrgListXmlCnt(cmap);
	}

	@Override
	public CommonXmlList getImageListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getImageListXml(cmap) );
		return list;
	}

	@Override
	public int getImageListXmlCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getImageListXmlCnt(cmap);
	}

	@Override
	public void insertAssetImage(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		tabMapper.insertAssetImage(cmap);
	}

	@Override
	public void deleteAssetImage(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		tabMapper.deleteAssetImage(cmap);
	}

	@Override
	public void updateInventoryDetail(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("update_dt"))) {
			tabMapper.updateInventoryDetail(cmap);
		}
	}

	@Override
	public CommonXmlList getNewAssetListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("pageLimit", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize",10));
		cmap.put("pageSize", cmap.getString("pageSize","10"));
		cmap.put("pageStartNum", (cmap.getInt("pageIdx") - 1) * cmap.getInt("pageSize") + 1 + "");
		cmap.put("pageEndNum", cmap.getInt("pageIdx") * cmap.getInt("pageSize") + "");

		CommonXmlList list = new CommonXmlList();
		list.addList( (List)tabMapper.getNewAssetListXml(cmap) );
		return list;
	}

	@Override
	public void insertNewAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		tabMapper.insertNewAsset(cmap);
	}

	@Override
	public CommonList getUserDeptList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return tabMapper.getUserDeptList(cmap);
	}

}
