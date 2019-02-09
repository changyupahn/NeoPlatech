package boassoft.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import egovframework.com.cmm.service.EgovProperties;

public class MultipartUtility {

	private HttpURLConnection httpConn;
    private DataOutputStream request;
    private final String boundary =  "*****";
    private final String crlf = "\r\n";
    private final String twoHyphens = "--";

    /**
     * This constructor initializes a new HTTP POST request with content type
     * is set to multipart/form-data
     *
     * @param requestURL
     * @throws IOException
     */
    public void MultipartUtilityV2(String requestURL)
            throws IOException {

        // creates a unique boundary based on time stamp
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);

        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        System.out.println("Connection: "+ "Keep-Alive");
        httpConn.setRequestProperty("Cache-Control", "no-cache");
        System.out.println("Cache-Control: "+ "no-cache");
        httpConn.setRequestProperty(
            "Content-Type", "multipart/form-data;boundary=" + this.boundary);
        System.out.println("Content-Type: "+ "multipart/form-data;boundary=" + this.boundary);

        request =  new DataOutputStream(httpConn.getOutputStream());
    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */
    public void addFormField(String name, String value)throws IOException  {
        request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
        System.out.print(this.twoHyphens + this.boundary + this.crlf);
        request.write(("Content-Disposition: form-data; name=\"" + name + "\""+ this.crlf).getBytes("EUC-KR"));
        System.out.print("Content-Disposition: form-data; name=\"" + name + "\""+ this.crlf);
        request.writeBytes("Content-Type: text/plain; charset=EUC-KR" + this.crlf);
        System.out.print("Content-Type: text/plain; charset=EUC-KR" + this.crlf);
        request.writeBytes(this.crlf);
        System.out.print(this.crlf);
        //request.writeBytes(value+ this.crlf);
        request.write(value.getBytes("EUC-KR"));
        System.out.print(value);
        request.writeBytes(this.crlf);
        System.out.print(this.crlf);
        request.flush();
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName  name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
        System.out.print(this.twoHyphens + this.boundary + this.crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
                fieldName + "\";filename=\"");
        System.out.print("Content-Disposition: form-data; name=\"" +
                fieldName + "\";filename=\"");
        request.write(fileName.getBytes("EUC-KR"));
        System.out.print(fileName);
        request.writeBytes("\"" + this.crlf);
        System.out.print("\"" + this.crlf);
        request.writeBytes(this.crlf);
        System.out.print(this.crlf);

        byte[] bytes = Files.readAllBytes(uploadFile.toPath());
        request.write(bytes);
    }

    /**
     * Completes the request and receives response from the server.
     *
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public String finish() throws IOException {
        String response ="";

        //request.writeBytes(this.crlf);
        //System.out.print(this.crlf);
        request.writeBytes(this.twoHyphens + this.boundary +
                this.twoHyphens + this.crlf);
        System.out.print(this.twoHyphens + this.boundary +
                this.twoHyphens + this.crlf);

        request.flush();
        request.close();

        // checks server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {

        	BufferedReader responseStreamReader = null;

        	try {
	            InputStream responseStream = new
	                    BufferedInputStream(httpConn.getInputStream());

	            responseStreamReader =
	                    new BufferedReader(new InputStreamReader(responseStream));

	            String line = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ((line = responseStreamReader.readLine()) != null) {
	                stringBuilder.append(line).append("\n");
	            }
	            responseStreamReader.close();

	            response = stringBuilder.toString();
	            httpConn.disconnect();
        	} catch (Exception e) {
        		e.printStackTrace();
        	} finally {
        		try { if (responseStreamReader != null) responseStreamReader.close(); } catch (Exception e) {}
        	}
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }

        return response;
    }

    public static void main(String[] args) throws Exception {

    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
        String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s", key);

		MultipartUtility mu = new MultipartUtility();
		mu.MultipartUtilityV2(url);
//		mu.addFormField("token", "YVHVDAhQNfi6fQcB2Tp6GDEdnBvl5eQo");
//		mu.addFormField("deviceno", "326797f80960a0b1");
//		mu.addFormField("assetNo", "HAA-WM8540");
//		mu.addFormField("fileDt", "20170207164525");
//		mu.addFilePart("file", new File("D:\\tmp0b8d2565-1e3d-4d31-914b-5930cb9062c0.png"));
		String response = mu.finish();

		System.out.println("response : " + response);

	}

    /*public static void main(String[] args) throws Exception {

		MultipartUtility mu = new MultipartUtility();
		mu.MultipartUtilityV2("http://boassoft.iptime.org:9777/app/rfid/uploadImage.do");
		mu.addFormField("token", "YVHVDAhQNfi6fQcB2Tp6GDEdnBvl5eQo");
		mu.addFormField("deviceno", "326797f80960a0b1");
		mu.addFormField("assetNo", "HAA-WM8540");
		mu.addFormField("fileDt", "20170207164525");
		mu.addFilePart("file", new File("D:\\tmp0b8d2565-1e3d-4d31-914b-5930cb9062c0.png"));
		mu.finish();

	}*/
}
