package boassoft.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlUtils {

	public static void setChildText(Element element, String name, String text) {
		Element e = new Element(name);
		e.setText(text);
		
		element.addContent(e);
	}
	
	public static void setChild(Element fromElement, Element toElement) {
		fromElement.addContent(toElement);
	}
	
	public static String getChildText(Element element, String name) {
		return getChild(element, name).getText();
	}
	
	public static Element getChild(Element element, String name) {
		List<Element> childList = element.getChildren();
		
		for (int i = 0; i < childList.size(); i++) {
			String itemName = childList.get(i).getName();
			System.out.println(itemName);
			
			if (name.equals(itemName)) {
				return childList.get(i);
			}
		}
		
		return new Element("");
	}
	
	public static String documentToString(Document doc) throws Exception {
		String str = new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
		
		return str;
	}
	
	public static String documentToSendString(Document doc) throws Exception {
		String str = new XMLOutputter(Format.getCompactFormat()).outputString(doc);
		
		return str;
	}
	
	public static Document stringToDocument(String rawstr) {
		Document doc = null;
				
		try {
			SAXBuilder builder = new SAXBuilder();
			
			doc = builder.build(new ByteArrayInputStream(rawstr.getBytes("UTF-8")));

//			jdomdoc = builder.build(new java.net.URL("http://www.xxx.com"));
//			jdomdoc = builder.build(new InputSource(new FileReader(XML_FILE_PATH)));
			
//			Element root = jdomdoc.getRootElement();
//
//	 		Element SENDDATA = root.getChild("SENDDATA");
//	 		
//	 		String title = SENDDATA.getChildText("todotitle");
//	 		
//	 		System.out.println("title : " + title);
//	 		
//	 		Element decidelist = SENDDATA.getChild("decidelist");
//	 		
//	 		List<Element> LISTDATA = decidelist.getChildren("LISTDATA");
//	 		
//	 		for(int i=0; i<LISTDATA.size(); i++) {
//	 			Element decide = LISTDATA.get(i);
//	 			System.out.println("takeuser : " + decide.getChildText("takeuser"));
//	 		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public Document makeMessage(String title, String rqstno, String bpmno, String dutysrlno, Element decidelist) {
		Document doc = new Document();
		
		Element root = new Element("DATAROOT");
		
		doc.setContent(root);
		
		Element senddata = new Element("SENDDATA");
		
		XmlUtils.setChildText(senddata, "todotitle", title);
		XmlUtils.setChildText(senddata, "rqstno", rqstno);
		XmlUtils.setChildText(senddata, "bpmno", bpmno);
		XmlUtils.setChildText(senddata, "dutysrlno", dutysrlno);
		XmlUtils.setChildText(senddata, "bpmstatcd", "0130001");
		XmlUtils.setChild(senddata, decidelist);
		
		XmlUtils.setChild(root, senddata);
		
		return doc;
	}
	
	public static Document makeMessage(Map<String, Object> sendParamMap) {
		if (sendParamMap == null) return null;
		
		Document doc = new Document();
		
		Element root = new Element("DATAROOT");
		
		doc.setContent(root);
		
		Element senddata = new Element("SENDDATA");
		
		Iterator<String> itr = sendParamMap.keySet().iterator();
		
		while (itr.hasNext()) {
			String key = itr.next();
			
			Object obj = sendParamMap.get(key);
			
			if (obj instanceof Element) {
				XmlUtils.setChild(senddata, (Element) obj);
			}
			else {
				XmlUtils.setChildText(senddata, key, (String) obj);
			}
		}
		
		XmlUtils.setChild(root, senddata);
		
		return doc;
	}
	
	public static String sendXmlData(String url, String data, String contenttype) throws Exception {
		// 파일에서 읽어서 전송하는 경우 사용
		/*
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(XML_FILE_PATH), "utf-8"));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			fileData.append(buf, 0, numRead);
		}
		reader.close();

		String xml_string_to_send = fileData.toString();
		*/
		
		String xml_string_to_send = data;

		String returnString = "";

		HttpURLConnection connection = null;

		OutputStream os = null;

		try {
			System.out.println("connect url: " + url);
			
			URL searchUrl = new URL(url);	// 전송할 서버 url

			connection = (HttpURLConnection) searchUrl.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", contenttype);
			connection.setRequestProperty("Content-Length", Integer.toString(xml_string_to_send.length()));

			if (xml_string_to_send != "" && xml_string_to_send != null) {
				xml_string_to_send = xml_string_to_send.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");	// 자동으로 생기는 선언부 제거
				
				System.out.println("send: " + xml_string_to_send);
				
				os = connection.getOutputStream();
				os.write(xml_string_to_send.getBytes("utf-8"));
				os.flush();
				os.close();
			}

			int rc = connection.getResponseCode();	// 결과값 수신

			if (rc == 200) {
				InputStreamReader in = new InputStreamReader(connection.getInputStream(), "utf-8");

				BufferedReader br = new BufferedReader(in);

				String strLine;

				while ((strLine = br.readLine()) != null) {
					returnString = returnString.concat(strLine);
				}
				
				Document retDoc = XmlUtils.stringToDocument(returnString);
				String successMessage = XmlUtils.getChildText(XmlUtils.getChild(retDoc.getRootElement(), "SERVICEINFO"), "SUCCESSMESSAGE");
				String errorMessage = XmlUtils.getChildText(XmlUtils.getChild(retDoc.getRootElement(), "SERVICEINFO"), "ERRORMESSAGE");
				
				System.out.println(String.format("successMessage: %s, errorMessage: %s", successMessage, errorMessage));
				
				System.out.println("return: " + XmlUtils.documentToString(XmlUtils.stringToDocument(returnString)));	// 결과값출력
			} else {
				System.out.println("http response code error: " + rc + "\n");
			}
		} catch (IOException e) {
			System.out.println("search URL connect failed: " + e.getMessage());
			
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			
			connection.disconnect();
		}
		
		return returnString;
	}
}
