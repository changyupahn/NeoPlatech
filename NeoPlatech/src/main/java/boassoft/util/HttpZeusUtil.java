package boassoft.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HttpZeusUtil {

	public static String get(String requestUrl) {

		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream is = null;
		String resultSet = null;

		try {

			URL url = new URL(requestUrl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			resultSet = parseJSONData(br);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (br != null) br.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if (isr != null) isr.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if (is != null) is.close(); } catch (Exception e) { e.printStackTrace(); }
		}

		return resultSet;
	}

	public static String post(String url, String parameter) {
		return NetworkJsonUtil.getResultString(url, parameter);
	}

	public static String put(String url, String parameter) {

		if (parameter == null || "".equals(parameter)) {
			parameter = "_method=PUT";
		} else {
			parameter = "_method=PUT&" + parameter;
		}

		return NetworkJsonUtil.getResultString(url, parameter);
	}

	public static String delete(String url, String parameter) {

		if (parameter == null || "".equals(parameter)) {
			parameter = "_method=DELETE";
		} else {
			parameter = "_method=DELETE&" + parameter;
		}

		return NetworkJsonUtil.getResultString(url, parameter);
	}

	public static String parseJSONData(BufferedReader bufferedReader) {
		String resultSet = null;

		try {

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser
					.parse(bufferedReader);
			resultSet = jsonObject.toJSONString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultSet;
	}
}
