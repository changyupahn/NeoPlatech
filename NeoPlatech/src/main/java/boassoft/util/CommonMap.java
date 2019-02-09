package boassoft.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boassoft.common.FileManage;
import boassoft.security.KISA_SHA256;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.psl.dataaccess.util.CamelUtil;

@Component("CommonMap")
public class CommonMap extends ListOrderedMap {

	private static final long serialVersionUID = 1688829812124549585L;

    @Resource(name = "FileManage")
    private FileManage fileUtil;

	public CommonMap() {
	}
	
	public CommonMap(HttpServletRequest request) {
		super();
		this.addAll(request);

//		//set language
//		this.put("lang", LocaleUtil.getLanguage());
	}

	public CommonMap setMultipartFile(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo) throws Exception {
		return setMultipart(multiRequest, "file", keyStr, storePath, 150, 150, orgNo, "", "", "");
	}

	public CommonMap setMultipartXml(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo) throws Exception {
		return setMultipart(multiRequest, "xml", keyStr, storePath, 150, 150, orgNo, "", "", "");
	}

	public CommonMap setMultipartImage(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo) throws Exception {
		return setMultipart(multiRequest, "image", keyStr, storePath, 150, 150, orgNo, "", "", "");
	}

	public CommonMap setMultipartImage2(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo, String assetNo, String fileDt) throws Exception {
		return setMultipart(multiRequest, "image", keyStr, storePath, 150, 150, orgNo, assetNo, fileDt, "");
	}

	public CommonMap setMultipartImage3(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo, String assetNo, String fileDt, String inputKey) throws Exception {
		return setMultipart(multiRequest, "image", keyStr, storePath, 150, 150, orgNo, assetNo, fileDt, inputKey);
	}

	public CommonMap setMultipartExcel(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo) throws Exception {
		return setMultipart(multiRequest, "excel", keyStr, storePath, 150, 150, orgNo, "", "", "");
	}

	public CommonMap setMultipartZeusImage(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo) throws Exception {
		return setMultipart(multiRequest, "zeus", keyStr, storePath, 150, 150, orgNo, "", "", "");
	}

	public CommonMap setMultipartNoFile(final MultipartHttpServletRequest multiRequest, String keyStr, String storePath, String orgNo) throws Exception {
		return setMultipart(multiRequest, "none", keyStr, storePath, 150, 150, orgNo, "", "", "");
	}

	public CommonMap setMultipart(final MultipartHttpServletRequest multiRequest, String extsnType, String keyStr, String storePath, int thumbWidth, int thumbHeight, String orgNo, String assetNo, String fileDt, String inputKey) throws Exception {
		CommonMap cmResult = new CommonMap();
		List<FileVO> result = null;

		Enumeration<String> keys = multiRequest.getParameterNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String[] val = multiRequest.getParameterValues(key);

			key = CamelUtil.convert2CamelCase((String) key);

			if(val == null)
				cmResult.put(key, "");
			else if(val.length == 1)
				cmResult.put(key, val[0]);
			else {
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<val.length; i++){
					sb.append(val[i].trim());
					sb.append(",");
				}
				cmResult.put(key, sb.toString().replaceAll(",$",""));
			}
		}

		final MultiValueMap<String, MultipartFile> files = multiRequest.getMultiFileMap();
	    if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, extsnType, keyStr, storePath, thumbWidth, thumbHeight, orgNo, assetNo, fileDt, inputKey);
			//fileMngService.insertFileInfs(result);
			for(int k=0; k<result.size(); k++){
				cmResult.put(result.get(k).getInputKey(), result.get(k).getStreFileNm());
				cmResult.put(result.get(k).getInputKey()+"_file_sn", result.get(k).getFileSn());
				cmResult.put(result.get(k).getInputKey()+"_file_extsn", result.get(k).getFileExtsn());
				cmResult.put(result.get(k).getInputKey()+"_file_stre_cours", result.get(k).getFileStreCours());
				cmResult.put(result.get(k).getInputKey()+"_stre_file_nm", result.get(k).getStreFileNm());
				cmResult.put(result.get(k).getInputKey()+"_orignl_file_nm", result.get(k).getOrignlFileNm());
				cmResult.put(result.get(k).getInputKey()+"_file_size", result.get(k).getFileMg());
			}
	    }

		return cmResult;
	}

	public String toJsonString(){
		return JSONObject.toJSONString(this);
	}

	public String toJsonString(ModelMap model){

		model.put("jsonString", JSONObject.toJSONString(this));

		return "common/commonJson";
	}

	public String getString(String key){
		return getString(key, "", true);
	}

	public String getString(String key, String defaultValue){
		return getString(key, defaultValue, true);
	}

	public String getString(String key, boolean bReplaceTag){
		return getString(key, "", bReplaceTag);
	}

	public String getString(String key, String def, boolean bReplaceTag) {
		String ret = "";

		key = CamelUtil.convert2CamelCase(key);

		if(this.get(key) != null)
			ret = String.valueOf(this.get(key));
		else if(this.get(key.toUpperCase()) != null)
			ret = String.valueOf(this.get(key.toUpperCase()));
		else if(this.get(key.toLowerCase()) != null)
			ret = String.valueOf(this.get(key.toLowerCase()));
		else
			ret = def;

		if ("".equals(ret))
			ret = def;

		if( bReplaceTag )
			ret = SecureUtil.replaceDefaultTag(ret);

		return ret;
	}

	public String getSha256Encrypt(String key) throws Exception {
		byte[] plainText = getString(key, "", false).getBytes("UTF-8");
		byte[] bszDigest = new byte[32];
		KISA_SHA256.SHA256_Encrpyt(plainText, plainText.length, bszDigest);
		String result = "";
		for(int i=0; i<bszDigest.length; i++) {
			result = result + toHex(bszDigest[i]);
		}

		return result;
	}

	public String getSha256PasswdEncrypt(String key) throws Exception {
		byte[] plainText = ("SALT" + getString(key, "", false)).getBytes("UTF-8");
		byte[] bszDigest = new byte[32];
		KISA_SHA256.SHA256_Encrpyt(plainText, plainText.length, bszDigest);
		String result = "";
		for(int i=0; i<bszDigest.length; i++) {
			result = result + toHex(bszDigest[i]);
		}

		return result;
	}

	public String toHex(int b) {
		char c[] = new char[2];
		c[0] = toHexNibble((b>>4) & 0x0f);
		c[1] = toHexNibble(b & 0x0f);
		return new String(c);
	}

	public char toHexNibble(int b) {
		if(b >= 0 && b <= 9)
			return (char)(b + '0');
		if(b >= 0x0a && b <= 0x0f)
			return (char)(b + 'A' - 10);
		return '0';
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int def) {
		int ret = 0;

		try {

			key = CamelUtil.convert2CamelCase(key);

			if(this.get(key) != null)
				ret = Integer.parseInt(this.get(key).toString());
			else if(this.get(key.toUpperCase()) != null)
				ret = Integer.parseInt(this.get(key.toUpperCase()).toString());
			else if(this.get(key.toLowerCase()) != null)
				ret = Integer.parseInt(this.get(key.toLowerCase()).toString());
			else
				ret = def;

		} catch (Exception ex) {
			ret = def;
		}

		return ret;
	}

	public long getLong(String key) {
		return getLong(key, 0);
	}

	public long getLong(String key, long def) {
		long ret = 0;

		try {

			key = CamelUtil.convert2CamelCase(key);

			if(this.get(key) != null)
				ret = Long.parseLong(this.get(key).toString());
			else if(this.get(key.toUpperCase()) != null)
				ret = Long.parseLong(this.get(key.toUpperCase()).toString());
			else if(this.get(key.toLowerCase()) != null)
				ret = Long.parseLong(this.get(key.toLowerCase()).toString());
			else
				ret = def;

		} catch (Exception ex) {
			ret = def;
		}

		return ret;
	}

	public double getDouble(String key) {
		return getDouble(key, 0);
	}

	public double getDouble(String key, double def) {
		double ret = 0;

		try {

			key = CamelUtil.convert2CamelCase(key);

			if(this.get(key) != null)
				ret = Double.parseDouble(this.get(key).toString());
			else if(this.get(key.toUpperCase()) != null)
				ret = Double.parseDouble(this.get(key.toUpperCase()).toString());
			else if(this.get(key.toLowerCase()) != null)
				ret = Double.parseDouble(this.get(key.toLowerCase()).toString());
			else
				ret = def;

		} catch (Exception ex) {
			ret = def;
		}

		return ret;
	}

	public String[] getArray(String key) {
		return this.getArray(key, ",");
	}

	public String[] getArray(String key, String g) {
		String[] ret = null;

		key = CamelUtil.convert2CamelCase(key);

		if( !"".equals(this.getString(key))){
			ret = this.getString(key).split(g, -1);
		}

		if( ret == null )
			ret = new String[]{};

		return ret;
	}

	public void addAll(HttpServletRequest request) {
		if (request.getParameterMap() == null)
			return;

		Iterator<Entry<String,String[]>> i$ = request.getParameterMap().entrySet().iterator();
		while(i$.hasNext()){
			Entry<String,String[]> entry = i$.next();
			Object value = entry.getValue();
			if (value != null) {
				Object toadd;
				if (value instanceof String[]) {
					String[] values = (String[]) value;

					StringBuffer sb = new StringBuffer();
					for(int i=0; i<values.length; i++){
						sb.append(values[i].trim());
						sb.append(",");
					}
					toadd = sb.toString().replaceAll(",$","");
				} else {
					toadd = value;
				}
				this.put((String)entry.getKey(), toadd);
			}
		};
	}

	public Object put(Object key, Object value){
		return super.put(CamelUtil.convert2CamelCase((String) key), value);
	}

	public Object put(Object key, Object value, boolean isCamelCase){
		if (isCamelCase) {
			return put(key, value);
		} else {
			return super.put((String) key, value);
		}
	}

	public Object get(String key){
		return super.get(key);
	}

}
