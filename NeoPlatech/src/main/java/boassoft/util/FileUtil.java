package boassoft.util;

import java.util.HashMap;

public class FileUtil extends HashMap<Object, Object> {

	private static final long serialVersionUID = -486209195901605935L;

	public static boolean isAllowImage(String ext)
    {
		if( ext == null )
			return false;

		//return ext.toLowerCase().matches("bmp|gif|jpg|jpeg|png"); bmp 썸네일 생성이 안되서 제거...
		return ext.toLowerCase().matches("gif|jpg|jpeg|png");
    }

	public static boolean isAllowFile(String ext)
    {
		if( ext == null )
			return false;

		return ext.toLowerCase().matches("hwp|txt|ppt|doc|docx|xls|xlsx|swf|psd|pdd|iff|bmp|gif|eps|pix|pcx|pct|pic|png|raw|sct|tif|flm|pcd|ani|cal|fax|img|jbg|jpe|jpeg|jpg|mac|pbm|pgm|ppm|ras|tga|wmf|zip|alz|egg");
    }

	public static boolean isAllowAsset(String ext)
    {
		if( ext == null )
			return false;

		return ext.toLowerCase().matches("gif|jpg|jpeg|png|xml");
    }

	public static boolean isAllowExcel(String ext)
    {
		if( ext == null )
			return false;

		return ext.toLowerCase().matches("xls|xlsx");
    }

	public static boolean isAllowXml(String ext)
    {
		if( ext == null )
			return false;

		return ext.toLowerCase().matches("xml");
    }

}
