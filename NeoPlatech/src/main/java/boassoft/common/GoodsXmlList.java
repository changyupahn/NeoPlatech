package boassoft.common;

import java.util.ArrayList;
import java.util.List;

import boassoft.common.GoodsXml;

public class GoodsXmlList  {

	private List<GoodsXml> list;
	
	public GoodsXmlList(){
        list = new ArrayList<GoodsXml>();
    }
	
	public void add(GoodsXml p){
        list.add(p);
    }
	
	public void addList(List<GoodsXml> l){
	        list = l;
	}
	
	public int size(){
    	return list.size();
    }


}
