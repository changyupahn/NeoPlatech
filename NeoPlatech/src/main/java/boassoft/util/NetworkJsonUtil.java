package boassoft.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;

public class NetworkJsonUtil {
	/** 에러코드 : URL연결실패 */
	public static String ERROR_CONNECTION_FAIL = "ER404";
	public static String ERROR_CONNECTION_FAIL_MSG = "ER404 : 서버 통신 에러";
	/** 에러코드 : 입출력에러  */
	public static String ERROR_IOEXCEPTION = "ER701";
	public static String ERROR_IOEXCEPTION_MSG = "ER701 : 입출력 에러";
	/** 에러코드 : JSON파싱에러  */
	public static String ERROR_JSONEXCEPTION = "ER702";
	public static String ERROR_JSONEXCEPTION_MSG = "ER702 : JSON 에러";

	//===================================================================================
	// getJsonUrl2Map : 로그인정보, 회원정보와 같이 단건의 정보 호출시 (HashMap)
	// getJsonUrl2List : 상품권목록 과 같은 리스트 호출시 (ArrayList)
	//
	// 실제 사용시는 getJsonUrl2Map 메소드를 바로 호출하지 말고 안의 내용만 가져다가 사용하여 예외처리까지 함.
	// 실제 사용시는 getJsonUrl2List 메소드를 바로 호출하지 말고 안의 내용만 가져다가 사용하여 예외처리까지 함.
	//===================================================================================

	/**
	 * JSON URL을 호출하여 HashMap 으로 변환하여 받음
	 * 호출URL 예) http://www.도메인.com/JSON호출경로
	 * 전송파라미터 예) 파라미터명1=값&파라미터명2=값
	 * 전송파라미터 예) String.format("param1=%s&param2=%s", URLEncoder.encode("파라미터값1", "UTF-8"), URLEncoder.encode("파라미터값2", "UTF-8"))
	 * @param url 호출URL
	 * @param parameter 전송파라미터
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public static HashMap<String, String> getJsonUrl2Map(String url, String parameter) throws Exception {
		HashMap<String, String> hmap = new HashMap<String, String>();

    	String result = getResultString(url, parameter);
    	String errMsg = "";

    	if (ERROR_CONNECTION_FAIL.equals(result)) {
    		errMsg = ERROR_CONNECTION_FAIL_MSG;
    	} else if (ERROR_IOEXCEPTION.equals(result)) {
    		errMsg = ERROR_IOEXCEPTION_MSG;
    	} else {
    		try {
    			hmap = jsonString2Map(result);
    		} catch (Exception e) {
    			errMsg = NetworkJsonUtil.ERROR_JSONEXCEPTION_MSG;
    		}
    	}

    	if (!"".equals(errMsg)) {
    		//TODO 에러 알림 System.out.println(errMsg);
    	} else {
    		//TODO 정상 처리
    	}

        return hmap;
    }

	/**
	 * JSON URL을 호출하여 ArrayList 로 변환하여 받음
	 * 호출URL 예) http://www.도메인.com/JSON호출경로
	 * 전송파라미터 예) 파라미터명1=값&파라미터명2=값
	 * 전송파라미터 예) String.format("param1=%s&param2=%s", URLEncoder.encode("파라미터값1", "UTF-8"), URLEncoder.encode("파라미터값2", "UTF-8"))
	 * @param url 호출URL
	 * @param parameter 전송파라미터
	 * @return ArrayList<HashMap<String,String>>
	 * @throws Exception
	 */
	public static ArrayList<HashMap<String,String>> getJsonUrl2List(String url, String parameter) throws Exception {
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    	String result = getResultString(url, parameter);
    	String errMsg = "";

    	if (ERROR_CONNECTION_FAIL.equals(result)) {
    		errMsg = ERROR_CONNECTION_FAIL_MSG;
    	} else if (ERROR_IOEXCEPTION.equals(result)) {
    		errMsg = ERROR_IOEXCEPTION_MSG;
    	} else {
    		try {
    			list = jsonArray2List(result);
    		} catch (Exception e) {
    			errMsg = ERROR_JSONEXCEPTION_MSG;
    		}
    	}

    	if (!"".equals(errMsg)) {
    		//TODO 에러 알림 System.out.println(errMsg);
    	} else {
    		//TODO 정상 처리
    	}

        return list;
    }

	/**
	 * URL을 호출하여 출력된 내용을 문자열 값으로 얻는다.
	 * @param url 호출URL
	 * @param parameter 전송파라미터
	 * @return String 호출결과 문자열
	 */
	public static String getResultString(String url, String parameter) {
		String resultString = "";
		BufferedReader in = null;

		try {
			URL targetURL = new URL(url);
	    	URLConnection urlConn = targetURL.openConnection();
	    	HttpURLConnection hurlc = (HttpURLConnection) urlConn;

	    	hurlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    	hurlc.setRequestMethod("POST");
	    	hurlc.setDoOutput(true);
	    	hurlc.setDoInput(true);
	    	hurlc.setUseCaches(false);
	    	hurlc.setDefaultUseCaches(false);

	    	OutputStream opstrm = hurlc.getOutputStream();
	    	opstrm.write(parameter.getBytes());
	    	opstrm.flush();
	    	opstrm.close();

	    	String buffer = null;
	    	in = new BufferedReader(new InputStreamReader(hurlc.getInputStream()));

	    	while ((buffer = in.readLine()) != null) {
	    		resultString += buffer;
	    	}

		} catch (Exception e) {
			e.printStackTrace();
			resultString = ERROR_CONNECTION_FAIL;
		} finally {
			if (in != null) {
				try { in.close(); } catch (Exception e) {
					resultString = ERROR_IOEXCEPTION;
				}
			}
		}

		return resultString;
	}

	public static String getResultString2(String url, String parameter) {
		String resultString = "";
		BufferedReader in = null;

		try {
//			URL url = new URL("http://www.example.com/resource");
//			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
//			httpCon.setDoOutput(true);
//			httpCon.setRequestProperty(
//			    "Content-Type", "application/x-www-form-urlencoded" );
//			httpCon.setRequestMethod("DELETE");
//			httpCon.connect();

			URL targetURL = new URL(url);
	    	URLConnection urlConn = targetURL.openConnection();
	    	HttpURLConnection hurlc = (HttpURLConnection) urlConn;

	    	hurlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    	hurlc.setRequestMethod("DELETE");
	    	hurlc.setDoOutput(true);
	    	//hurlc.setDoInput(true);
	    	//hurlc.setUseCaches(false);
	    	//hurlc.setDefaultUseCaches(false);

	    	OutputStream opstrm = hurlc.getOutputStream();
	    	opstrm.write(parameter.getBytes());
	    	opstrm.flush();
	    	opstrm.close();

	    	String buffer = null;
	    	in = new BufferedReader(new InputStreamReader(hurlc.getInputStream()));

	    	while ((buffer = in.readLine()) != null) {
	    		resultString += buffer;
	    	}

		} catch (Exception e) {
			e.printStackTrace();
			resultString = ERROR_CONNECTION_FAIL;
		} finally {
			if (in != null) {
				try { in.close(); } catch (Exception e) {
					resultString = ERROR_IOEXCEPTION;
				}
			}
		}

		return resultString;
	}

	/**
	 * JSON 형태의 문자열을 해쉬맵으로 변환
	 * @param jsonString JSON 문자열
	 * @return HashMap<String, String> 해쉬맵
	 * @throws JSONException
	 */
	public static HashMap<String, String> jsonString2Map( String jsonString ) throws Exception{
		HashMap<String, String> keys = new HashMap<String, String>();

        org.json.JSONObject jsonObject = new org.json.JSONObject( jsonString ); // HashMap
        Iterator<?> keyset = jsonObject.keys(); // HM

        while (keyset.hasNext()) {
            String key =  (String) keyset.next();
            String value = String.valueOf(jsonObject.get(key));
            keys.put( key, value );
        }
        return keys;
    }

	/**
	 * JSON 형태의 문자열을 리스트형으로 변환
	 * @param jsonListString JSON LIST 문자열
	 * @return ArrayList<HashMap<String,String>> 리스트
	 * @throws JSONException
	 */
	public static ArrayList<HashMap<String,String>> jsonArray2List( String jsonListString ) throws Exception{
		JSONArray arrayOFKeys = new JSONArray(jsonListString);

		ArrayList<HashMap<String,String>> array2List = new ArrayList<HashMap<String,String>>();
        for ( int i = 0; i < arrayOFKeys.length(); i++ )  {
        	array2List.add( jsonString2Map(arrayOFKeys.optString(i)) );
        }
        return array2List;
    }
}
