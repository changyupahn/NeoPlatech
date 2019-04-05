package boassoft.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.StringUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

@Component("goodsXmlManage")
public class GoodsXmlManage {

	public static final int BUFF_SIZE = 2048;
	
	private static final Logger LOG = Logger.getLogger(GoodsXmlManage.class.getName());
	
	 public void writeXml(CommonXmlList list, String downPath) throws Exception {
		 
		//String downPath = "D:/asset/xml/inventory.xml";
	    	
	    	OutputStream outStream = null;
	    	InputStream is = null;
	    	
	    	try {
	    		outStream = new BufferedOutputStream(new FileOutputStream(downPath));
	    		Writer writer = new OutputStreamWriter(outStream, "utf-8");
	    		
	    		XStream xstream = new XStream(new CustomizedXppDriver(new XmlFriendlyReplacer("__", "_")));
	    		
	    	 	xstream.alias("item", CommonXml.class);
	        	xstream.alias("data", CommonXmlList.class);
	        	xstream.addImplicitCollection(CommonXmlList.class, "list");
	        	
	        	xstream.toXML(list, writer);  
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	} finally {
	    		try { if( is != null ) is.close(); } catch (IOException e) {}
	    		try { if( outStream != null ) outStream.close(); } catch (IOException e) {}
	    	}
	 }
	 
	 public String writeXmlString(GoodsXmlList goodsXmlList) throws Exception {
		 
		 String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		 
		 XStream xstream = new XStream(new CustomizedXppDriver(new XmlFriendlyReplacer("__", "_")));
		 System.out.println(" aaa  2222 " + " : " + str );
		 xstream.alias("item", GoodsXml.class);
	     xstream.alias("data", GoodsXmlList.class);
	     xstream.addImplicitCollection(GoodsXmlList.class, "list");
	     System.out.println(" bbb 3333 " + " : " + str );
	     str = str + xstream.toXML(goodsXmlList);
	     System.out.println(" ccc 444 " + " : " + str );
	     return str;
	 }
	 
 public String writeXmlString(CommonXmlList commonXmlList) throws Exception {
		 
		 String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		 
		 XStream xstream = new XStream(new CustomizedXppDriver(new XmlFriendlyReplacer("__", "_")));
		 
		 xstream.alias("item", GoodsXml.class);
	     xstream.alias("data", GoodsXmlList.class);
	     xstream.addImplicitCollection(GoodsXmlList.class, "list");
	     
	     str = str + xstream.toXML(commonXmlList);
	     
	     return str;
	 }
	 
	 public void downXml(String fileUrl, String downPath) throws Exception {
		 
		//String fileUrl = "http://localhost:8080/xml/asset/rfid_asset.xml";
	    //String fileUrl = "http://intra.kordi.re.kr/intra2008/xml/rfid_asset.php";
	    //String fileUrl = "http://intra.kordi.re.kr/intra2008/xml/rfid_asset.php?status_gbn=Y";
	    //String fileUrl = "http://intra.kordi.re.kr/intra2008/xml/rfid_asset.php?status_gbn=Y&date_gbn=1&s_begdate=2006&s_enddate=2006";
	    //String fileUrl = "http://intra.kordi.re.kr/intra2008/xml/rfid_asset.php?status_gbn=N";
	    	
	    //String downPath = "D:/asset/xml/asset.xml";
		 
		OutputStream outStream = null;
	    URLConnection uCon = null;
	    InputStream is = null;
	    
	    try {
	    	LOG.debug("-------downXml Start : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
	    	
	    	URL Url;
    		byte[] buf;
    		int byteRead;
    		Url = new URL(fileUrl);
    		outStream = new BufferedOutputStream(new FileOutputStream(new File(downPath)));
    		
    		uCon = Url.openConnection();
    		is = uCon.getInputStream();
    		buf = new byte[BUFF_SIZE];
    		while ((byteRead = is.read(buf)) != -1) {
    			outStream.write(buf, 0, byteRead);
    		}
    		
    		LOG.debug("Successfully.");
    		LOG.debug("-------downXml End : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
	    	
	    } catch (Exception e) {
    		e.printStackTrace();
    		LOG.debug("-------downXml Error : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
    	} finally {
    		try { if( is != null ) is.close(); } catch (IOException e) {}
    		try { if( outStream != null ) outStream.close(); } catch (IOException e) {}
    	}
	 }
	 
	 @SuppressWarnings("unchecked")
	public CommonList getXmlList(String xmlPath) throws Exception {
		    CommonList list = new CommonList();
	    	//String xmlPath = "D:/asset/xml/asset.xml";			
		 
			 try {
		    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    		DocumentBuilder db = dbf.newDocumentBuilder();
		    		Document doc = db.parse(new File(xmlPath));
		    		doc.getDocumentElement().normalize();
		    		NodeList nodeLst = doc.getElementsByTagName("item");
		    		
		    		CommonMap inv = null;
		    		
		    		for ( int s=0; s<nodeLst.getLength(); s++ ) {
		    			Node fstNode = nodeLst.item(s);
		    			inv = new CommonMap();
		    			
		    			if ( fstNode.getNodeType () == Node.ELEMENT_NODE ) {
		    				
		    				Element fstElmnt = ( Element ) fstNode;    				
		    				NodeList nodeList = fstElmnt.getChildNodes();
		    				
		    				for (int k=0; k<nodeList.getLength(); k++) {
		    					
		    					Node node = nodeList.item(k);
		    					inv.put(node.getNodeName(), StringUtil.n2null(node.getTextContent()).trim());
		    				}
		    				
		    				list.add(inv);
		    			}
		    		}
			 } catch ( Exception e ) {
		      		e.printStackTrace();
		     }	
			 
			  System.out.println("parse OK!!");
			 
			 return list; 
	 }
	
}
