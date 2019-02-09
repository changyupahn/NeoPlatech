package boassoft.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import egovframework.com.utl.fcc.service.EgovStringUtil;

public class StringUtil extends EgovStringUtil{

	/**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String nvl(Object object) {
        return nvl(object, "");
    }
    public static String nvl2(Object object) {
    	String result = nvl(object, "");

    	if ("null".equalsIgnoreCase(result)) {
    		result = "";
    	}

        return result;
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 기본값으로 바꾸는 메서드
     * @param object 원본 객체
     * @param def 기본값
     * @return resultVal 문자열
     */
    public static String nvl(Object object, String def) {
    	String string = "";

        if (object != null) {
            string = object.toString().trim();
        }

        if ("".equals(string)) {
        	string = def;
        }

        return string;
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 기본값으로 바꾸는 메서드
     * @param object 원본 객체
     * @param def 기본값
     * @return resultVal 문자열
     */
    public static int nvl(Object object, int def) {
    	int result = 0;

    	try {
    		result = Integer.parseInt(object.toString());
		} catch (Exception ex) {
			result = def;
		}

        return result;
    }

    public static String n2br(String txt) {
    	String result = "";
    	if( txt != null ){
			result = txt.replaceAll("\r\n","<br />");
    		result = txt.replaceAll("\n","<br />");
    	}
    	return result;
    }

    public static String n2null(String txt) {
    	String result = "";
    	if( txt != null ){
//			result = txt.replaceAll("\\\r\\\n","");
//    		result = txt.replaceAll("\\\n","");
//    		result = txt.replaceAll("\\r\\n","");
//    		result = txt.replaceAll("\\n","");
    		result = txt.replaceAll("\r\n","");
    		result = txt.replaceAll("\n","");
    	}
    	return result;
    }

    public static String n2null2(String txt) {
    	String result = "";
    	if( txt != null ){
//			result = txt.replaceAll("\\\r\\\n","");
//    		result = txt.replaceAll("\\\n","");
    		result = txt.replaceAll("\\r\\n","");
    		result = txt.replaceAll("\\n","");
    		result = txt.replaceAll("\r\n","");
    		result = txt.replaceAll("\n","");
    	}
    	return result;
    }

    public static String n2null3(String txt) {
    	String result = "";
    	if( txt != null ){
			result = txt.replaceAll("\\\r\\\n","");
    		result = txt.replaceAll("\\\n","");
    		result = txt.replaceAll("\\r\\n","");
    		result = txt.replaceAll("\\n","");
    		result = txt.replaceAll("\r\n","");
    		result = txt.replaceAll("\n","");
    	}
    	return result;
    }

    /**
     * XSS 방지 처리.
     *
     * @param data
     * @return
     */
    public static String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }

    /**
     * 천단위 [,]콤마 기호 넣기
     * @param str
     * @return
     */
	public static String commaNum(String str){
		if( str == null || "".equals(str))
			return str;
		else {
			str = str.replaceAll(",", "");
			DecimalFormat df = new DecimalFormat("#,##0");
			double number = Double.parseDouble(str);
			return df.format(number);
		}
	}

	public static String commaNum(Double val){
		String str = "";
		str = "" + val;

		if( str == null || "".equals(str))
			return str;
		else {
			DecimalFormat df = new DecimalFormat("#,##0");
			double number = Double.parseDouble(str);
			return df.format(number);
		}
	}

	public static String commaNum2(String str){
		if( str == null || "".equals(str))
			return str;
		else {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			double number = Double.parseDouble(str);
			str = df.format(number);
			str = str.replace(".000", "");
			return str;
		}
	}

	public static String commaNum2(Double val){
		String str = "";
		str = "" + val;

		if( str == null || "".equals(str))
			return str;
		else {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			double number = Double.parseDouble(str);
			str = df.format(number);
			str = str.replace(".000", "");
			return str;
		}
	}

	/**
	 * 문자열 자르기
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static String strCut(String str,int maxLength){
		return fullStrCut(str, null, maxLength, 0, true, false);
	}

	/**
	 * 문자열 자르기
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static String strCut(String str, int maxLength, boolean isAdddot){
		return fullStrCut(str, null, maxLength, 0, true, isAdddot);
	}

	/**
	 * 문자열 자르기
	 * @param szText
	 * @param szKey
	 * @param nLength
	 * @param nPrev
	 * @param isNotag
	 * @param isAdddot
	 * @return
	 */
	public static String fullStrCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot){  // 문자열 자르기
		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE);  // 태그제거 패턴
		if(isNotag) {r_val = p.matcher(r_val).replaceAll("");}  // 태그 제거
		r_val = r_val.replaceAll("&amp;", "&");
		r_val = r_val.replaceAll("(!/|\r|\n|&nbsp;)", "");  // 공백제거
		try {
		byte[] bytes = r_val.getBytes("UTF-8");     // 바이트로 보관
		if(szKey != null && !szKey.equals("")) {
		nLengthPrev = (r_val.indexOf(szKey) == -1)? 0: r_val.indexOf(szKey);  // 일단 위치찾고
		nLengthPrev = r_val.substring(0, nLengthPrev).getBytes("MS949").length;  // 위치까지길이를 byte로 다시 구한다
		nLengthPrev = (nLengthPrev-nPrev >= 0)? nLengthPrev-nPrev:0;    // 좀 앞부분부터 가져오도록한다.
		}
		// x부터 y길이만큼 잘라낸다. 한글안깨지게.
		int j = 0;
		if(nLengthPrev > 0) while(j < bytes.length) {
		if((bytes[j] & 0x80) != 0) {
		oF+=2; rF+=3; if(oF+2 > nLengthPrev) {break;} j+=3;
		} else {if(oF+1 > nLengthPrev) {break;} ++oF; ++rF; ++j;}
		}
		j = rF;
		while(j < bytes.length) {
		if((bytes[j] & 0x80) != 0) {
		if(oL+2 > nLength) {break;} oL+=2; rL+=3; j+=3;
		} else {if(oL+1 > nLength) {break;} ++oL; ++rL; ++j;}
		}
		r_val = new String(bytes, rF, rL, "UTF-8");  // charset 옵션
		if(isAdddot && rF+rL+3 <= bytes.length) {r_val+="...";}  // ...을 붙일지말지 옵션
		} catch(UnsupportedEncodingException e){ e.printStackTrace(); }
		return r_val;
	}

	/**
	 * 금액을 이미지형 금액으로 변경
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static String imgPrice(String price){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<price.length(); i++){
			char priceChar = price.charAt(i);
			if( price.charAt(i) == ',' )
				priceChar = 'c';
			sb.append("<span class=\"no_");
			sb.append(priceChar);
			sb.append(" no_fnt\">");
			sb.append(priceChar);
			sb.append("</span>");
		}
		return sb.toString();
	}

	/**
	 * 아이디로 사용해도 되는 문자열인지 체크
	 * @param id
	 * @return 사용가능:true 사용불가능:false
	 */
	public static boolean isAllowId(String id){
		id = nvl(id);
		String id2 = id.replaceAll("[^0-9a-zA-Z]", "");
		if( !"".equals(id2) && id.length() == id2.length() && id.length() > 5 && id.length() < 17 )
			return true;
		else
			return false;
	}

	/**
	 * 도매몰 주소로 사용해도 되는 문자열인지 체크
	 * @param id
	 * @return 사용가능:true 사용불가능:false
	 */
	public static boolean isAllowDomain(String dname){
		dname = nvl(dname);
		String dname2 = dname.replaceAll("[^0-9a-z]", "");
		if( !"".equals(dname2) && dname.length() == dname2.length() && dname.length() < 9 && !"www".equals(dname) )
			return true;
		else
			return false;
	}

	/**
	 * 도매 상가 주소로 사용해도 되는 문자열인지 체크
	 * @param id
	 * @return 사용가능:true 사용불가능:false
	 */
	public static boolean isAllowMallName(String dname){
		dname = nvl(dname);
		String dname2 = dname.replaceAll("[^0-9a-zA-Z가-힣 ]", "");
		if( !"".equals(dname2) && dname.length() == dname2.length() )
			return true;
		else
			return false;
	}

	/**
	 * 패스워드 * 처리
	 * @param pass 패스워드
	 * @param startNumber 히든 처리를 시작할 자리수
	 * @return
	 */
	public static String hiddenPassword(String pass, int startNumber){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<pass.length(); i++){
			char passChar = pass.charAt(i);
			if( i < startNumber )
				sb.append(passChar);
			else
				sb.append("*");
		}
		return sb.toString();
	}

	/**
	 * 숫자로 사용가능한지 확인
	 * @param id
	 * @return 사용가능:true 사용불가능:false
	 */
	public static boolean isAllowNumber(String num){
		num = nvl(num);
		String num2 = num.replaceAll("[^0-9]", "");
		if( !"".equals(num2) && num.length() == num2.length() )
			return true;
		else
			return false;
	}

	public static String lpad(String str, int len, String padChar) {

		if (str == null)
			return "";

		if (str.length() >= len)
			return str;

		for (int i=str.length(); i<len; i++) {
			str = padChar + str;
		}

		return str;
	}

	public static String rpad(String str, int len, String padChar) {

		if (str == null)
			return "";

		if (str.length() >= len)
			return str;

		for (int i=str.length(); i<len; i++) {
			str = str + padChar;
		}

		return str;
	}

	public static String stringToHex(String s) {
		String result = "";

	    for (int i = 0; i < s.length(); i++) {
	      result += String.format("%02X", (int) s.charAt(i));
	    }

	    return result;
	}


	public static String stringToHex0x(String s) {
	    String result = "";

	    for (int i = 0; i < s.length(); i++) {
	      result += String.format("0x%02X", (int) s.charAt(i));
	    }

	    return result;
	}
}
