package boassoft.util;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Component;

@Component("appCommonMap")
public class AppCommonMap extends ListOrderedMap{

	private static final long serialVersionUID = 1688829812124549585L;
	
	public String getString(String key, String def, boolean bReplaceTag) {
		String ret = "";
		
		key = key.toUpperCase();
		
		if(this.get(key) != null)
			ret = String.valueOf(this.get(key));			
		else if(this.get(key.toUpperCase()) != null)
			ret = String.valueOf(this.get(key.toUpperCase()));
		else if(this.get(key.toLowerCase()) != null)
			ret = String.valueOf(this.get(key.toLowerCase()));
		else
			ret = def;
		
		if ("".equals(ret))
			ret = def;
		
		if( bReplaceTag )
			ret = SecureUtil.replaceDefaultTag(ret);
		
		return ret;
	}
	
	public Object put(Object key, Object value){
		return super.put(((String) key).toUpperCase(), value);
	}
}
