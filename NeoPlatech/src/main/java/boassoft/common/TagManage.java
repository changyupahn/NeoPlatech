package boassoft.common;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import boassoft.util.CommonMap;

@Component("TagManage")
public class TagManage {

	private String fixassetCode;
	private String fixassetName;
	private String fixassetSize;
	private String empName;
	private String usePositionName;
	private String rfidCode;
	
	public void setTagInfo(CommonMap hm){
		this.fixassetCode		= (hm.getString("asset_no")).replaceAll("\\D", "");
		this.fixassetName		= (hm.getString("asset_name"));
		this.fixassetSize		= (hm.getString("asset_size"));
		this.empName      		= (hm.getString("user_name"));
		this.usePositionName	= (hm.getString("dept_name"));
		this.rfidCode			= "2CB4832D4A" + this.fixassetCode;
	}
	
	public List<String> assetTagListN() {
		List<String> list = new ArrayList<String>();
		
		list.add("~R200");
		list.add("^L");
		//list.add("AW,68,120,7,1,0,0,부서명");
		//list.add("AW,280,120,7,3,0,0," + this.usePositionName);
		list.add("AW,68,220,7,1,0,0,물품번호");
		list.add("AW,280,220,7,3,0,0," + this.fixassetCode);
		list.add("AW,68,270,7,1,0,0,자 산 명");
		list.add("AW,280,270,7,3,0,0," + this.fixassetName);
		list.add("AW,68,320,7,1,0,0,규    격");
		list.add("AW,280,320,7,3,0,0," + this.fixassetSize);
		list.add("AW,68,370,7,1,0,0,사 용 자");
		list.add("AW,280,370,7,3,0,0," + this.empName);
		list.add("WB4,14,2," + this.rfidCode);
		list.add("E");
		
		return list;
	}
	
	public List<String> assetTagListS() {
		List<String> list = new ArrayList<String>();
		
		list.add("~R200");
		list.add("^L");
		//list.add("AW,80,120,7,1,0,0,부서명");
		//list.add("AW,270,120,7,3,0,0," + this.usePositionName);
		list.add("AW,80,210,7,1,0,0,물품번호");
		list.add("AW,270,210,7,3,0,0," + this.fixassetCode);
		list.add("AW,80,275,7,1,0,0,자 산 명");
		list.add("AW,270,275,7,3,0,0," + this.fixassetName);
		list.add("AW,80,335,7,1,0,0,사 용 자");
		list.add("AW,270,335,7,3,0,0," + this.empName);
		list.add("WB4,14,2," + this.rfidCode);
		list.add("E");
		
		return list;
	}
}
