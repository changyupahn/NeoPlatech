package boassoft.util;

public class SecureUtil {

	public static String removePunct(String key)
    {
		//System.out.println("ORI : "+key);
		//System.out.println("NEW : "+key.replaceAll("[<>(#)&]",""));
		if( key == null )
			return "";
		else
			return key.replaceAll("[<>(#)&]","");
    }

	public static String removeDefaultTag(String content)
	{
    	content = content.replaceAll("/body", "");
    	content = content.replaceAll("body", "");
    	content = content.replaceAll("/html", "");
    	content = content.replaceAll("html", "");
    	content = content.replaceAll("/head", "");
    	content = content.replaceAll("head", "");
    	content = content.replaceAll("/script", "");
    	content = content.replaceAll("script", "");
    	content = content.replaceAll("/vbscript", "");
    	content = content.replaceAll("vbscript", "");
    	content = content.replaceAll("/iframe", "");
    	content = content.replaceAll("iframe", "");
    	content = content.replaceAll("alert", "");
    	content = content.replaceAll(".asp", "");
    	content = content.replaceAll(".jsp", "");
    	content = content.replaceAll(".php", "");
    	content = content.replaceAll(".cgi", "");

    	content = content.replaceAll("document.cookie", "");

		return content;
	}

	public static String replaceDefaultTag(String value)
	{
		if(value==null){
			return null;
		}

		StringBuffer strBuff = new StringBuffer();

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '<':
				strBuff.append("&lt;");
				break;
			case '>':
				strBuff.append("&gt;");
				break;
//			case '&':
//				strBuff.append("&amp;");
//				break;
			case '"':
				strBuff.append("&quot;");
				break;
			case '\'':
				strBuff.append("&apos;");
				break;
			default:
				strBuff.append(c);
				break;
			}
		}

		value = strBuff.toString();

		return value;
	}

	public static String unreplaceDefaultTag(String value)
	{
		value = value.replaceAll("[&]lt[;]", "<");
		value = value.replaceAll("[&]gt[;]", ">");
		value = value.replaceAll("[&]quot[;]", "\"");
		value = value.replaceAll("[&]apos[;]", "\'");

		return value;
	}
}
