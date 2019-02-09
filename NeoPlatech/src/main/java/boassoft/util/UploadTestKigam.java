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

public class UploadTestKigam {

	public static void main(String[] args) {
        //String filePath = "C:/Users/moonyh/Downloads/TOAD11.jpg";
		String filePath = "D:/rfid_data/kigam/zeus/img/2018/201801020216086520.jpg";

        try {
            upload(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void upload(String filePath) throws IOException {
        //String url = "http://api.zeus.go.kr/api/eq/equips/cd843a8b-879c-41c8-bedf-7be915fac03d";
    	//String key = "324bb7d3-7be0-4046-a397-1096a6d71748";
        //String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s", key);

        String url = "http://api.zeus.go.kr/api/eq/equips/324bb7d3-7be0-4046-a397-1096a6d71748";

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
        		{"fixedAsetNo","kigam_test_9057"}
        		,{"korNm","테스트9022"}
        		,{"engNm","test9022"}
        		,{"modelYn","Y"}
        		,{"modelCd","20121227000000000171"}
        		,{"modelTakeCd",""}
        		,{"modelNmYn",""}
        		,{"manufactureNm",""}
        		,{"modelNm",""}
        		,{"branchCd",""}
        		,{"takeCd","1"}
        		,{"takeDt","2018-01-02"}
        		,{"takePrc","6000000"}
        		,{"devSdt",""}
        		,{"devEdt",""}
        		,{"devPrc",""}
        		,{"devOpenYn",""}
        		,{"devSpec",""}
        		,{"buildSdt",""}
        		,{"buildEdt",""}
        		,{"buildPrcDom",""}
        		,{"buildPrcFor",""}
        		,{"registCd","1"}
        		,{"cpId",""}
        		,{"useScopeCd","2"}
        		,{"useScopeRange","01"}
        		,{"useScopeMean","01"}
        		,{"useScopeReasonCd","01"}
        		,{"useScopeReasonEtc",""}
        		,{"useTypeCd","02"}
        		,{"useTypeEtc",""}
        		,{"setupYn","Y"}
        		,{"setupId","20171214000000009926"}
        		,{"areaCd",""}
        		,{"zipCd",""}
        		,{"address1",""}
        		,{"address2",""}
        		,{"stLtype",""}
        		,{"stMtype",""}
        		,{"lat",""}
        		,{"lng",""}
        		,{"groundYn","Y"}
        		,{"floorNo","1"}
        		,{"roomNo","111"}
        		,{"idleDisuseCd","1"}
        		,{"disuseCd","01"}
        		,{"saleDt",""}
        		,{"recyclingDt",""}
        		,{"disposalDt",""}
        		,{"transferOrganCd",""}
        		,{"transferDt",""}
        		,{"feature","테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 "}
        		,{"capability","테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 "}
        		,{"example","테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 "}
        		,{"importCd","01"}
        		,{"operTel","042-865-1234"}
        		,{"managerId","kigam3754@kigam"}
        		,{"scienceTechRegistNo",""}
        		,{"operNm",""}
        		,{"operMobile",""}
        		,{"operEmail",""}
        		,{"rndYn","Y"}
        		,{"rndList[0].rndYn","Y"}
        		,{"rndList[0].subjectYn","N"}
        		,{"subjectOcd",""}
        		,{"rndList[0].officeCd","P09"}
        		,{"rndList[0].porganNm","555"}
        		,{"rndList[0].busiNm","222"}
        		,{"rndList[0].subjectNm","111"}
        		,{"rndList[0].organNm","333"}
        		,{"rndList[0].directorNm","444"}
        		,{"rndList[0].startDt","2017-01-02"}
        		,{"rndList[0].endDt","2017-01-02"}
        		,{"rndList[0].sixCd","0BT"}
        		,{"rndList[0].sixTechCd","777"}
        		,{"rndList[0].subjectPcd","666"}
        		,{"rndAcq",""}
        		,{"rndList[0].rndPrc","600000"}
        		,{"rndList[0].rndWeight","10"}
        		,{"redYn","N"}
        		,{"rfdEquipNoKeyword",""}
        		,{"rfdSkey",""}
        		,{"rfdEquipSkey",""}
        		,{"checkEquipSkey",""}

//        		,{"saleComDt",""}
//        		,{"rndList[0].subjectSearchKeywords",""}
//        		,{"mainEquipSearchKeywords",""}
//        		,{"analysisInstNameKeyword",""}
//        		,{"rndList[0].officeYn","1"}
//        		,{"transferComDt",""}
//        		,{"rfdEquipNmKeyword",""}
//        		,{"recyclingComDt",""}
//        		,{"zipSearchKeywords",""}
//        		,{"modelSearchKeywords",""}
//        		,{"projDetailNameKeyword",""}
//        		,{"disuseComCd","01"}
//        		,{"disposalComDt",""}
//        		,{"grantOrganSearchKeywords",","}
//        		,{"setupSearchKeywords",""}
//        		,{"projectAnalysisNameKeyword",""}
//        		,{"reqInstNameKeyword",""}

//        		,{"filedataFileSn","0"}
//        		,{"filedataFileExtsn","jpg"}
//        		,{"filedataFileStreCours","D:/rfid_data/kigam/zeus/img/2018"}
//        		,{"filedataStreFileNm","201801020242546230.jpg"}
//        		,{"filedataOrignlFileNm","Desert.jpg"}
//        		,{"filedataFileSize","845941"}
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

//        DataInputStream dis = new DataInputStream(conn.getInputStream());
//        int c;
//        StringBuffer buf = new StringBuffer();
//        while ((c = dis.read()) != -1) {
//                buf.append((char)c);
//        }
//        System.out.println(buf.toString());
//        conn.disconnect();

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
