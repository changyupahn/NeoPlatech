package boassoft.util;

public class DynmTableUtil {

	/**
     * 테이블 컬럼명 앞에 테이블 alias 붙이기	
     * @param columnName
     * @param prependStr
     * @return
     */
    public static String columnName(String prependStr, String columnName) {
    	String result = "";
    	
    	if (columnName == null)
    		return result;
    	
    	if (columnName.indexOf(".") == -1)
    		result = prependStr + "." + columnName;
    	else
    		result = columnName;
    	
        return result;
    }
}
