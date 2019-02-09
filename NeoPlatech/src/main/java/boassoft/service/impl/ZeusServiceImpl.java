package boassoft.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import boassoft.service.ZeusService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.MultipartUtility;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ZeusService")
public class ZeusServiceImpl extends EgovAbstractServiceImpl implements
		ZeusService {

	@Override
	public CommonList getZeusList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList resultList = new CommonList();
		// String key = EgovProperties.getProperty("Globals.Zeus.apiKey");;
		// String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s",
		// key);
		// String resultStr = ConvertAddress.conAddr(url);
		//
		// HashMap<String, String> resultMap =
		// NetworkJsonUtil.jsonString2Map(resultStr);

		return resultList;
	}

	@Override
	public String write(CommonMap cmap, CommonList paramList, String filePath,
			String fileName) throws Exception {
		// TODO Auto-generated method stub
		String result = "";

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s",
				key);
		// String url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";

		MultipartUtility mu = new MultipartUtility();
		mu.MultipartUtilityV2(url);

		// 파라미터
		for (int i = 0; i < paramList.size(); i++) {
			CommonMap param = paramList.getMap(i);

			mu.addFormField(param.getString("key"), param.getString("val"));
		}

		// 파일 첨부
		if (filePath.length() > 0) {
			mu.addFilePart("fileData", new File(filePath));
		}

		result = mu.finish();

		System.out.println("result : " + result);

		return result;
	}

	@Override
	public String modify(CommonMap cmap, CommonList paramList, String equipId)
			throws Exception {
		// TODO Auto-generated method stub
		String result = "";

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s",
				equipId, key);
		// String url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";

		MultipartUtility mu = new MultipartUtility();
		mu.MultipartUtilityV2(url);

		// 파라미터
		for (int i = 0; i < paramList.size(); i++) {
			CommonMap param = paramList.getMap(i);

			mu.addFormField(param.getString("key"), param.getString("val"));
		}

		result = mu.finish();

		System.out.println("result : " + result);

		return result;
	}

	@Override
	public String delete(CommonMap cmap, String equipId) throws Exception {
		// TODO Auto-generated method stub
		String result = "";

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s",
				equipId, key);
		// String url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";

		MultipartUtility mu = new MultipartUtility();
		mu.MultipartUtilityV2(url);

		mu.addFormField("_method", "DELETE");
		mu.addFormField("equipId", equipId);
		mu.addFormField("reqReason", "");

		result = mu.finish();

		System.out.println("result : " + result);

		return result;
	}

	@Override
	public String upload2(CommonMap cmap, CommonList paramList,
			String filePath, String fileName) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		 * //String url =
		 * String.format("http://api.zeus.go.kr/api/eq/equips/%s", key); String
		 * url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";
		 */
		String result = "";

		/*
		 * try { HttpClient client = new DefaultHttpClient(); HttpPost post =
		 * new HttpPost(url);
		 * 
		 * //FileBody bin = new FileBody(file);
		 * 
		 * MultipartEntity reqEntity = new
		 * MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); for (int i=0;
		 * i<paramList.size(); i++) { CommonMap param = paramList.getMap(i);
		 * reqEntity.addPart(param.getString("key"), new
		 * StringBody(param.getString("val"), Charset.forName("UTF-8"))); }
		 * //reqEntity.addPart("fileData", bin); post.setEntity(reqEntity);
		 * 
		 * HttpResponse response = client.execute(post); HttpEntity resEntity =
		 * response.getEntity();
		 * 
		 * if (resEntity != null) { result = EntityUtils.toString(resEntity); }
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		return result;
	}

	@Override
	public String upload3(CommonMap cmap, CommonList paramList,
			String filePath, String fileName, String url) throws Exception {
		// TODO Auto-generated method stub
		String result = "";

		HttpURLConnection conn = null;

		DataOutputStream out = null;
		BufferedOutputStream bos = null;
		OutputStream os = null;

		DataInputStream dis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;

		BufferedInputStream bis = null;
		FileInputStream fis = null;

		try {
			// 데이터 구분문자. 아무거나 정해도 상관없지만 꼭 나타날 수 없는 형태의 문자열로 정한다.
			String delimeter = "---------------------------7d1539170136";

			byte[] newLineBytes = System.setProperty("line.separator", "\r\n")
					.getBytes();
			byte[] delimeterBytes = delimeter.getBytes();
			byte[] dispositionBytes = "Content-Disposition: form-data; name="
					.getBytes();
			byte[] quotationBytes = "\"".getBytes();
			byte[] contentTypeBytes = "Content-Type: image/jpeg".getBytes();
			byte[] fileNameBytes = "; filename=".getBytes();
			byte[] twoDashBytes = "--".getBytes();

			// 커넥션 생성 및 설정
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + delimeter);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);

			// 전송 작업 시작
			os = conn.getOutputStream();
			bos = new BufferedOutputStream(os);
			out = new DataOutputStream(bos);

			for (int i = 0; i < paramList.size(); i++) {
				CommonMap param = paramList.getMap(i);

				out.write(twoDashBytes);
				out.write(delimeterBytes);
				out.write(newLineBytes);

				// 파라미터 이름 출력
				out.write(dispositionBytes);
				out.write(quotationBytes);
				out.write(param.getString("key").getBytes());
				out.write(quotationBytes);
				out.write(newLineBytes);
				out.write(newLineBytes);
				// 값 출력
				out.write(param.getString("val").getBytes());
				out.write(newLineBytes);

				System.out.println(param.getString("key") + "="
						+ param.getString("val"));
			}

			// 파일 첨부
			if (filePath.length() > 0) {
				out.write(twoDashBytes);
				out.write(delimeterBytes);
				out.write(newLineBytes);

				out.write(dispositionBytes);
				out.write(quotationBytes);
				out.write("fileData".getBytes());
				out.write(quotationBytes);
				out.write(fileNameBytes);
				out.write(quotationBytes);
				out.write(fileName.getBytes());
				out.write(quotationBytes);
				// out.write(newLineBytes);
				// out.write(contentTypeBytes);
				out.write(newLineBytes);
				out.write(newLineBytes);

				fis = new FileInputStream(filePath);
				bis = new BufferedInputStream(fis);
				byte[] fileBuffer = new byte[1024 * 8]; // 8k
				int len = -1;
				while ((len = bis.read(fileBuffer)) != -1) {
					out.write(fileBuffer, 0, len);
				}
				out.write(newLineBytes);
				// File uploadFile = new File(filePath);
				// byte[] bytes = Files.readAllBytes(uploadFile.toPath());
				// out.write(bytes);
				// out.write(newLineBytes);
			}

			// 마지막 Delimeter 전송
			out.write(twoDashBytes);
			out.write(delimeterBytes);
			out.write(twoDashBytes);
			out.write(newLineBytes);
			out.flush();
			out.close();

			is = conn.getInputStream();
			dis = new DataInputStream(is);

			int c;
			while ((c = dis.read()) != -1) {
				baos.write(c);
			}

			result = new String(baos.toByteArray(), "UTF-8");
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			conn.disconnect();

			if (bis != null)
				try {
					bis.close();
				} catch (Exception ex) {
				}
			if (fis != null)
				try {
					fis.close();
				} catch (Exception ex) {
				}
			if (dis != null)
				try {
					dis.close();
				} catch (Exception ex) {
				}
			if (baos != null)
				try {
					baos.close();
				} catch (Exception ex) {
				}
			if (is != null)
				try {
					is.close();
				} catch (Exception ex) {
				}
			if (out != null)
				try {
					out.close();
				} catch (Exception ex) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (Exception ex) {
				}
			if (os != null)
				try {
					os.close();
				} catch (Exception ex) {
				}
		}

		return result;
	}

}
