package boassoft.util;

import java.util.Collection;

import org.json.simple.JSONArray;

@SuppressWarnings("rawtypes")
public class CommonList extends java.util.ArrayList {
	private static final long serialVersionUID = -3769066081890880251L;
	public int totalRow = 0;
	public int totalRow2 = 0;
	public int pageIdx = 0;
	public int pageSize = 0;
	
	
	/**
	 * 
	 */
	public CommonList() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CommonList(int arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */	
	public CommonList(Collection arg0) {
		super(arg0);
	}
	
	
	public CommonMap getMap( int idx ){
		return (CommonMap)super.get(idx);
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
