package boassoft.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModifyTestKigam {

	 public static void main(String[] args) {
			String filePath = "C:/Temp/Desert.png";

	        try {
	            upload(filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void upload(String filePath) throws IOException {

	        String url = "http://api.zeus.go.kr/api/eq/equips/20180103000000213247/324bb7d3-7be0-4046-a397-1096a6d71748";
	        //String url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";

	        // 데이터 구분문자. 아무거나 정해도 상관없지만 꼭 나타날 수 없는 형태의 문자열로 정한다.
	        String delimeter = "---------------------------7d1539170136";

	 		byte[] newLineBytes = System.setProperty("line.separator","\r\n").getBytes();
	        byte[] delimeterBytes = delimeter.getBytes();
	        byte[] dispositionBytes = "Content-Disposition: form-data; name=".getBytes();
	        byte[] quotationBytes = "\"".getBytes();
	        byte[] contentTypeBytes = "Content-Type: image/jpeg".getBytes();
	        byte[] fileNameBytes = "; filename=".getBytes();
	        byte[] twoDashBytes = "--".getBytes();

	        // 커넥션 생성 및 설정
	        HttpURLConnection conn = (HttpURLConnection) new URL(url)
	                .openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type",
	                                "multipart/form-data; boundary="+delimeter);
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setUseCaches(false);

	        // 전송 작업 시작
	        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
	                conn.getOutputStream()));

	        String[][] param = {
	        		{"stLtype",""}
	        		,{"devSdt",""}
	        		,{"devEdt",""}
	        		,{"takeDt","2018-01-02"}
	        		,{"fixedAsetNo","kigam_test_9057"}
	        		,{"officeYn","1"}
	        		,{"rndList[0].officeCd","P09"}
	        		,{"stMtype",""}
	        		,{"rndList[0].sixTechCd",""}
	        		,{"rndList[0].subjectNm","111"}
	        		,{"rndList[0].organNm","333"}
	        		,{"devSpec",""}
	        		,{"lat",""}
	        		,{"equipId","20180103000000213247"}
	        		,{"useScopeMean","01"}
	        		,{"roomNo","111"}
	        		,{"lng",""}
	        		,{"rndYn","Y"}
	        		,{"devPrc",""}
	        		,{"useScopeRange","01"}
	        		,{"setupId","20171214000000009926"}
	        		,{"rndList[0].directorNm","444"}
	        		,{"rndList[0].subjectPcd","666"}
	        		,{"registCd","1"}
	        		,{"setupYn","Y"}
	        		,{"disposalDt",""}
	        		,{"takeCd","1"}
//	        		,{"method","PUT"}
	        		,{"areaCd","09"}
	        		,{"importCd","01"}
	        		,{"rfdEquipNoKeyword",""}
	        		,{"floorNo","1"}
	        		,{"rndList[0].subjectYn","N"}
	        		,{"disuseCd","01"}
	        		,{"idleDisuseCd","1"}
	        		,{"modelYn","Y"}
	        		,{"useScopeCd","2"}
	        		,{"useScopeReasonEtc",""}
	        		,{"recyclingDt",""}
	        		,{"manufactureNm",""}
	        		,{"rndList[0].rndPrc","600000"}
	        		,{"modelCd","20121227000000000171"}
	        		,{"scienceTechRegistNo",""}
	        		,{"buildSdt",""}
	        		,{"rndList[0].sixCd","0BT"}
	        		,{"example","테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트"}
	        		,{"madeCd","GH"}
	        		,{"transferDt",""}
	        		,{"groundYn","Y"}
	        		,{"capability","테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트"}
	        		,{"feature","테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트"}
	        		,{"useTypeCd","02"}
	        		,{"operTel","042-865-1234"}
	        		,{"rndList[0].rndYn","Y"}
	        		,{"rndList[0].busiNm","222"}
	        		,{"buildEdt",""}
	        		,{"branchCd","A"}
	        		,{"rfdSkey",""}
	        		,{"buildPrcFor","0"}
	        		,{"address2",""}
	        		,{"address1",""}
	        		,{"organCd","200702080236"}
	        		,{"modelNm",""}
	        		,{"rndList[0].endDt","2017-01-01"}
	        		,{"rndList[0].subjectOcd",""}
	        		,{"rndList[0].rndWeight","10"}
	        		,{"managerId",""}
	        		,{"takePrc","6000000"}
	        		,{"saleDt",""}
	        		,{"redYn","N"}
	        		,{"checkEquipSkey",""}
	        		,{"rfdEquipSkey",""}
	        		,{"rndList[0].startDt","2017-01-01"}
	        		,{"useTypeEtc",""}
	        		,{"korNm","테스트9022"}
	        		,{"buildPrcDom","0"}
	        		,{"rndList[0].porganNm","555"}
	        		,{"engNm","test9022"}
	        		,{"_method","PUT"}
	        };

	        for (int i=0; i<param.length; i++) {

	        	out.write(twoDashBytes);
	            out.write(delimeterBytes);
	            out.write(newLineBytes);

	            // 파라미터 이름 출력
	            out.write(dispositionBytes);
	            out.write(quotationBytes);
	            out.write( param[i][0].getBytes() );
	            out.write(quotationBytes);
	            out.write(newLineBytes);
	            out.write(newLineBytes);
	            // 값 출력
	            out.write( param[i][1].getBytes() );
	            out.write(newLineBytes);
	        }

	        // 파일 첨부
			if (filePath.length() > 0)
			{
				out.write(twoDashBytes);
	            out.write(delimeterBytes);
	            out.write(newLineBytes);

	            out.write(dispositionBytes);
	            out.write(quotationBytes);
	            out.write( "fileData".getBytes() );
	            out.write(quotationBytes);
				out.write(fileNameBytes);
	            out.write(quotationBytes);
	            out.write("201801020216086520.jpg".getBytes() );
	            out.write(quotationBytes);
				out.write(newLineBytes);
	            out.write(contentTypeBytes);
	            out.write(newLineBytes);
	            out.write(newLineBytes);

	            BufferedInputStream is = null;
	            try {
	                is = new BufferedInputStream(
	                         new FileInputStream(filePath));
	                byte[] fileBuffer = new byte[1024 * 8]; // 8k
	                int len = -1;
	                while ( (len = is.read(fileBuffer)) != -1) {
	                    out.write(fileBuffer, 0, len);
	                }
	            } finally {
	                if (is != null) try { is.close(); } catch(IOException ex) {}
	            }

				out.write(newLineBytes);
			}

			// 마지막 Delimeter 전송
			out.write(twoDashBytes);
	        out.write(delimeterBytes);
	        out.write(twoDashBytes);
	        out.write(newLineBytes);
	        out.flush();
	        out.close();

//	        DataInputStream dis = new DataInputStream(conn.getInputStream());
//	        int c;
//	        StringBuffer buf = new StringBuffer();
//	        while ((c = dis.read()) != -1) {
//	                buf.append((char)c);
//	        }
//	        System.out.println(buf.toString());
//	        conn.disconnect();

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(conn.getInputStream());
	        int c;
	        while ((c = dis.read()) != -1) {
	        	baos.write(c);
	        }
	        System.out.println(new String(baos.toByteArray(), "UTF-8"));
	        conn.disconnect();
	    }

}
