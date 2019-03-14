package boassoft.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public class AssetHandler extends DefaultHandler{

	private CommonList list = null;
	private CommonMap cmap = null;
	
	public CommonList getAssetList() {        
		return list;
    }
	
	private boolean org = false;
	private boolean asset_no = false;
	
	@Override    
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("item"))
    	{
           cmap = new CommonMap();
    		
    		if (list == null)
    			list = new CommonList();    	
    	}
		else if (qName.equalsIgnoreCase("org")){ org = true; }
    	else if (qName.equalsIgnoreCase("asset_no")){ asset_no = true; }
	}
	
	@Override    
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	if (qName.equalsIgnoreCase("item")) {
    		list.add(cmap);
        }    
    }
	
	@Override    
    public void characters(char ch[], int start, int length) throws SAXException {
    	if (org) { cmap.put("org", new String(ch, start, length)); org= false; }
    	if (asset_no) { cmap.put("asset_no", new String(ch, start, length)); asset_no= false; }
    }
}
