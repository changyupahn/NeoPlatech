package boassoft.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerMapping;

public class RequestUtil {

	 public static String getServcDomain(HttpServletRequest request) {
	    	String servcDomain = "";
	    	String requestURL = request.getRequestURL().toString();
	    	boolean bResult = Pattern.matches("http:\\/\\/([a-zA-Z0-9]{1,14})[.]{1}i-order[.]{1}.*", requestURL);
	    	if(bResult)
	    		servcDomain = requestURL.replaceAll("http:\\/\\/([a-zA-Z0-9]{1,14})[.]{1}i-order[.]{1}.*", "$1");
	    	else {
	    		bResult = Pattern.matches("http:\\/\\/i-order[.]{1}.*", requestURL);
	    		if(bResult)
	    			servcDomain = "www";
	    	}

	    	return servcDomain;
	    }

	    public static List getList(HttpServletRequest request, String name) {
	    	List list = (List) request.getAttribute(name);
	    	if( list == null ) list = new java.util.ArrayList();
	    	return list;
	    }

	    public static CommonMap getCommonMap(HttpServletRequest request, String name) {
	    	CommonMap cmap = (CommonMap) request.getAttribute(name);
	    	if( cmap == null ) cmap = new CommonMap();
	    	return cmap;
	    }

	    public static CommonList getCommonList(HttpServletRequest request, String name) {
	    	CommonList list = (CommonList) request.getAttribute(name);
	    	if( list == null ) list = new CommonList();
	    	return list;
	    }

	    public static String getString(HttpServletRequest request, String name) {
	    	return getString(request,name,"");
		}
	    public static String getString(HttpServletRequest request, String name, String dft) {
			String ret = "";

			if(request.getAttribute(name) == null)
				ret = "";
			else
			{
				ret = String.valueOf(request.getAttribute(name));
			}
			if("".equals(ret)){
				ret = dft;
			}

			ret = SecureUtil.removeDefaultTag(ret);

			return ret;
		}

	    public static int getInt(HttpServletRequest request, String name) {
	    	return getInt(request,name,0);
	    }
	    public static int getInt(HttpServletRequest request, String name, int dft) {
			int ret = 0;

			if(request.getAttribute(name) == null)
				ret = 0;
			else
			{
				try{
					ret = Integer.parseInt(String.valueOf(request.getAttribute(name)));
				}catch(Exception e){
					ret = 0;
				}
			}
			if(ret==0){
				ret = dft;
			}

			return ret;
		}

	    public static boolean isMobile(HttpServletRequest request) throws Exception {

			String user_agent = request.getHeader("user-agent");

			if (user_agent.toUpperCase().indexOf("ANDROID") != -1) {
				return true;
			} else if (user_agent.toUpperCase().indexOf("IPHONE") != -1) {
				return true;
			} else if (user_agent.toUpperCase().indexOf("IPAD") != -1) {
				return true;
			} else {
				return false;
			}
		}

	    /**
		 * 클라이언트(Client)의 웹브라우저 종류를 조회하는 기능
		 * @param HttpServletRequest request Request객체
		 * @return String webKind 웹브라우저 종류( DB 공통코드 사용 )
		 * @exception Exception
		*/
		public static String getClntWebKindCd(HttpServletRequest request) throws Exception {

			String user_agent = request.getHeader("user-agent");

			// 웹브라우저 종류 조회
			//String webKind = "";
			String webKindCd = "";
			if (user_agent.toUpperCase().indexOf("GECKO") != -1) {
				if (user_agent.toUpperCase().indexOf("NESCAPE") != -1) {
					//webKind = "Netscape (Gecko/Netscape)";
					webKindCd = "011507";
				} else if (user_agent.toUpperCase().indexOf("FIREFOX") != -1) {
					//webKind = "Mozilla Firefox (Gecko/Firefox)";
					webKindCd = "011503";
				} else {
					//webKind = "Mozilla (Gecko/Mozilla)";
					webKindCd = "011501";
				}
			} else if (user_agent.toUpperCase().indexOf("MSIE") != -1) {
				if (user_agent.toUpperCase().indexOf("OPERA") != -1) {
					//webKind = "Opera (MSIE/Opera/Compatible)";
					webKindCd = "011506";
				} else {
					//webKind = "Internet Explorer (MSIE/Compatible)";
					webKindCd = "011502";
				}
			} else if (user_agent.toUpperCase().indexOf("SAFARI") != -1) {
				if (user_agent.toUpperCase().indexOf("CHROME") != -1) {
					//webKind = "Google Chrome";
					webKindCd = "011504";
				} else {
					//webKind = "Safari";
					webKindCd = "011505";
				}
			} else if (user_agent.toUpperCase().indexOf("THUNDERBIRD") != -1) {
				//webKind = "Thunderbird";
				webKindCd = "011508";
			} else {
				//webKind = "Other Web Browsers";
				webKindCd = "011501";
			}
			//return webKind;
			return webKindCd;
		}

		public static String getMenuNo(HttpServletRequest request) throws Exception {
			String result = "";

			String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

			if (restOfTheUrl == null) {
				return "";
			}

			Pattern p = Pattern.compile("[k][p][0-9][0-9][1-9][0-9]");
			Matcher m = p.matcher(restOfTheUrl);

			if (m.find()) {
				result = m.group(0);
				result = result.substring(0,5) + "0";
			}

			return result.toUpperCase();
		}
}
