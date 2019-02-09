package boassoft.util;

import java.util.List;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SessionUtil {

	public static boolean isEmpty(String name) {
		boolean result = false;

		try {
			if( RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION) == null )
				result = true;
			else
				result = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
    }

	public static boolean isNotEmpty(String name) {
		boolean result = false;

		try {
			if( RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION) != null )
				result = true;
			else
				result = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
    }

    @SuppressWarnings("rawtypes")
	public static List getList(String name) {
    	List list = null;

    	try {
    		list = (List) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	if( list == null ) list = new java.util.ArrayList();
    	return list;
    }

    public static CommonMap getCommonMap(String name) {
    	CommonMap cmap = null;

    	try {
    		cmap = (CommonMap) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	if( cmap == null ) cmap = new CommonMap();
    	return cmap;
    }

    public static CommonList getCommonList(String name) {
    	CommonList list = null;

    	try {
    		list = (CommonList) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	if( list == null ) list = new CommonList();
    	return list;
    }

    public static String getString(String name) {
		String ret = "";

		try {
			if(RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION) == null)
				ret = "";
			else
			{
				ret = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

    public static int getInt(String name) {
		int ret = 0;

		try {
			if(RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION) == null)
				ret = 0;
			else
			{
				ret = Integer.parseInt(String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
}
