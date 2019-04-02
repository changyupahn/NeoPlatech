package boassoft.common;

import java.util.ArrayList;
import java.util.List;

import boassoft.common.GoodsXml;

public class GoodsXmlList extends ArrayList<GoodsXml> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8473595255765995949L;
	private List<GoodsXml> list;
	
	public GoodsXmlList(){
        list = new ArrayList<GoodsXml>();
    }
	
	public boolean add(GoodsXml p){
        return list.add(p);
    }
	
	public void addList(List<GoodsXml> l){
	        list = l;
	}
	
	public int size(){
    	return list.size();
    }


}
