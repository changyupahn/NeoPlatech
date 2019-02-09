package boassoft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HttpJsonRequestUtil {

	public static String conAddr(String address) {

		/* http://dna.daum.net/myapi/dataapi/new 에서 발급받은 키를 입력 */

		String apiKey = "";

		// String address = "경기도 성남시 분당구 판교역로 235 에이치스퀘어 N동 6층";

		String inputLine;

		BufferedReader bufferedReader = null;

		String resultSet = null;

		try {

			// 링크 주소 만들기

			String requestUrl = "https://apis.daum.net/local/geo/addr2coord";

			requestUrl += "?apikey=" + apiKey; // 발급된 키

			requestUrl += "&q=" + URLEncoder.encode(address, "UTF-8");

			requestUrl += "&output=" + "JSON";

			URL url = new URL(requestUrl);

			URLConnection conn = url.openConnection();

			bufferedReader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			// System.out.println(bufferedReader.readLine());

			resultSet = parseJSONData(bufferedReader);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				bufferedReader.close();

			} catch (IOException e) {

			}

			return resultSet;

		}

	} // conAddr -END-

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

	} // getPoint -END-
} // ConvertAddress -END-
