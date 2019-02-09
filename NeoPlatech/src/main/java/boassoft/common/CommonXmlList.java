package boassoft.common;

import java.util.ArrayList;
import java.util.List;

public class CommonXmlList {

	private List<CommonXml> list;

    public CommonXmlList(){
        list = new ArrayList<CommonXml>();
    }

    public void add(CommonXml p){
        list.add(p);
    }
    
    public void addList(List<CommonXml> l){
        list = l;
    }
    
    public int size(){
    	return list.size();
    }
}
