package boassoft.util;

import java.util.Collection;

import org.json.simple.JSONArray;

@SuppressWarnings("rawtypes")
public class AppCommonList extends java.util.ArrayList{

	private static final long serialVersionUID = -82124388714156329L;
	public int totalRow = 0;
	public int pageIdx = 0;
	public int pageSize = 0;
	
	/**
	 * 
	 */
	public AppCommonList() {
		super();
	}

	/**
	 * @param arg0
	 */
	public AppCommonList(int arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	@SuppressWarnings("unchecked")
	public AppCommonList(Collection arg0) {
		super(arg0);
	}
	
	
	public AppCommonMap getMap( int idx ){
		return (AppCommonMap)super.get(idx);
	}
	
	public String toString(){
		String str = "totalRow=" + this.totalRow + ", pageIdx=" + this.pageIdx + ", pageSize=" + pageSize + "\n";
		str += super.toString();
		return str;
	}
	
	public String toJsonString(){
		return JSONArray.toJSONString(this);
	}
}
