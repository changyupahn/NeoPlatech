package boassoft.util;

public class NumberUtil {

	public static String formatInteger (String numstr) {
		String result = "";
		
		if (numstr == null)
			return result;
		
		result = numstr.split(".")[0].replaceAll("\\D", "");
				
		return result;
	}
}
