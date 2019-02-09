package boassoft.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class ApprHttpUtil {

	/**
	 * 승인
	 * @param rqstno 요청번호
	 * @param dutysrlno 업무구분코드
	 * @param docdivcd 문서구분코드
	 * @param iframeaddr MIS용 IFRAME 주소
	 * @throws Exception
	 */
	public static void Send(String rqstno, String dutysrlno, String docdivcd, String iframeaddr, CommonMap rqstUserView, CommonMap aucUserView, CommonMap aucTopUserView, String grantNo) throws Exception {

		Map<String, Object> sendParamMap = new HashMap<String, Object>();
		Document sendDoc = null;
		Document retDoc = null;
		String url = "";

		String empno = rqstUserView.getString("empNo");

		/*
		 * 자산인수인계신청 : dutysrlno = 929
		 *
		 * 1) 신청문서 저장
		 * 2) 승인정보 조회
		 * 3) 상신정보에 추가 승인선 추가
		 * 4) 승인 요청
		 */
//		String rqstno = MessageUtils.getRqstNo();
//		String dutysrlno = "929";
//		String docdivcd = "0030170";
//		//String iframeaddr = "http://asset.kigam.re.kr/kp7000/kp7010.do?rqstno=" + rqstno;
//		String iframeaddr = "http://203.247.172.17:10010/kp7000/kp7010.do?rqstno=" + rqstno;

		//승인상태 (0040011 : 승인완료 상태값)

		// 전송할 XML 메시지 설정
		sendParamMap = new HashMap<String, Object>();
		//sendParamMap.put("empno", "2122");
		sendParamMap.put("empno", empno);
		sendParamMap.put("rqstno", rqstno);
		sendParamMap.put("iframeaddr", iframeaddr);
		sendParamMap.put("docdivcd", docdivcd);
		sendParamMap.put("docstatcd", "0040001");

		sendDoc = XmlUtils.makeMessage(sendParamMap);

		//신청정보 요청
		url = "http://203.247.176.227/MIS/INTERFACE-I015.flexdo";
		retDoc = XmlUtils.stringToDocument(XmlUtils.sendXmlData(url, XmlUtils.documentToSendString(sendDoc).trim(), "application/xml"));

		if (retDoc == null) {
			throw new Exception("http response code error");
		}

		Thread.sleep(1000);

		// 전송할 XML 메시지 설정
		sendParamMap = new HashMap<String, Object>();
		sendParamMap.put("empno", empno);
		sendParamMap.put("dutysrlno", dutysrlno);

		sendDoc = XmlUtils.makeMessage(sendParamMap);

		//승인선 정보 조회
		url = "http://203.247.176.227/MIS/INTERFACE-S005.flexdo";
		retDoc = XmlUtils.stringToDocument(XmlUtils.sendXmlData(url, XmlUtils.documentToSendString(sendDoc).trim(), "application/xml"));

		Thread.sleep(1000);

		Element resultDataElement = XmlUtils.getChild(retDoc.getRootElement(), "RESULTDATA");

		Element decideListElement = XmlUtils.getChild(resultDataElement, "decidelist").clone();
		Element decideListElement2 = new Element("decidelist");

		if ("929".equals(dutysrlno)) {

			//승인선 수정
			List<Element> decideList = decideListElement.getChildren();

			int procordnoCnt = 0;
			for (int i = 0; i < decideList.size(); i++) {
				Element decideElement = decideList.get(i).clone();

				if ("929".equals(dutysrlno)) {
					// (인수자, 인수부서장 추가)
					if (i == 1 && "MGR".equals(grantNo)) {
						//자산담당자가 승인 인수인계 신청시 신청부서장 승인 없음.
					} else {
						procordnoCnt++;
						decideElement.getChild("procordno").setText(""+procordnoCnt);
						decideListElement2.addContent(decideElement);
					}

					if (i == 1) {

						//인수자
						procordnoCnt++;
						Element decideElement2 = decideElement.clone();

						decideElement2.getChild("dutysrlno").setText("940"); // dutysrlno : 940 인수자승인, 941 인수부서장승인
						decideElement2.getChild("dutynm").setText("인수자승인");
						decideElement2.getChild("procordno").setText(""+procordnoCnt);
						decideElement2.getChild("takedeptcd").setText(aucUserView.getString("deptNo"));
						decideElement2.getChild("takeuserno").setText(aucUserView.getString("userNo"));
						decideElement2.getChild("takeuserhisno").setText(aucUserView.getString("userHisNo"));
						decideElement2.getChild("takeusertel").setText(aucUserView.getString("userTelNo"));
						decideElement2.getChild("takeusernm").setText(aucUserView.getString("userName"));
						decideElement2.getChild("takeempno").setText(aucUserView.getString("empNo"));
						decideElement2.getChild("takekordept").setText(aucUserView.getString("deptName"));
						decideElement2.getChild("takedepthisno").setText(aucUserView.getString("deptHisNo"));
						decideElement2.getChild("takeuser").setText(aucUserView.getString("userName")+"("+aucUserView.getString("empNo")+")");
						decideListElement2.addContent(decideElement2);

						//인수부서장
						procordnoCnt++;
						Element decideElement3 = decideElement.clone();
						decideElement3.getChild("dutysrlno").setText("941");
						decideElement3.getChild("dutynm").setText("인수부서장승인");
						decideElement3.getChild("procordno").setText(""+procordnoCnt);
						decideElement3.getChild("takedeptcd").setText(aucTopUserView.getString("deptNo"));
						decideElement3.getChild("takeuserno").setText(aucTopUserView.getString("userNo"));
						decideElement3.getChild("takeuserhisno").setText(aucTopUserView.getString("userHisNo"));
						decideElement3.getChild("takeusertel").setText(aucTopUserView.getString("userTelNo"));
						decideElement3.getChild("takeusernm").setText(aucTopUserView.getString("userName"));
						decideElement3.getChild("takeempno").setText(aucTopUserView.getString("empNo"));
						decideElement3.getChild("takekordept").setText(aucTopUserView.getString("deptName"));
						decideElement3.getChild("takedepthisno").setText(aucTopUserView.getString("deptHisNo"));
						decideElement3.getChild("takeuser").setText(aucTopUserView.getString("userName")+"("+aucTopUserView.getString("empNo")+")");
						decideListElement2.addContent(decideElement3);
					}
				} else {

				}

				//XmlUtils.setChildText(decideElement, "procordno", i+3+"");

				//String decideElementStr = XmlUtils.getChildText(decideList.get(i), "procordno");
				System.out.println("============================================================================================");
				System.out.println(decideElement.getChildText("procordno"));


				//		String itemName = childList.get(i).getName();
				//		//					if (name.equals(itemName)) {
				//			return childList.get(i);
				//		}
			}

			//Element decideListElement2 = new Element("decidelist");
			//decideListElement2.setContent(decideList2);

			System.out.println("============================================================================================");
			System.out.println("decideListElement2 : ");
			System.out.println("============================================================================================");
			System.out.println(new XMLOutputter().outputString(decideListElement2));
		}

		String bpmno = XmlUtils.getChildText(resultDataElement, "bpmno");

		// 전송할 XML 메시지 설정
		sendParamMap = new HashMap<String, Object>();
		sendParamMap.put("todotitle", "자산인수인계신청");
		sendParamMap.put("rqstno", rqstno);
		sendParamMap.put("bpmno", bpmno);
		sendParamMap.put("dutysrlno", dutysrlno);
		sendParamMap.put("bpmstatcd", "0130001");
		if ("929".equals(dutysrlno)) {
			sendParamMap.put("decidelist", decideListElement2);
		} else {
			sendParamMap.put("decidelist", decideListElement);
		}

		sendDoc = XmlUtils.makeMessage(sendParamMap);
//				sendDoc = XmlUtils.makeMessage("자산인수인계신청", rqstno, bpmno, dutysrlno, decideListElement);

		//승인요청 (승인)
		url = String.format("http://203.247.176.227/MIS/INTERFACE-I003.flexdo?empno=%s", empno, new Object[]{});
		retDoc = XmlUtils.stringToDocument(XmlUtils.sendXmlData(url, XmlUtils.documentToSendString(sendDoc).trim(), "application/xml"));

	}

//	public static void main(String[] args) throws Exception {
//		String rqstno = "1511942639822A242176";
//		String dutysrlno = "929";
//		String docdivcd = "0030170";
//		String iframeaddr = "http://203.247.172.17:10010/kp7000/kp7010.do?rqstno=" + rqstno;
//		String empno = "1903";
//		ApprHttpUtil.Send(rqstno, dutysrlno, docdivcd, iframeaddr, empno);
//	}
}
