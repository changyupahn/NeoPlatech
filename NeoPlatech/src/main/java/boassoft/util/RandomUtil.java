package boassoft.util;

import org.apache.log4j.Logger;

public class RandomUtil {

private static final Logger log = Logger.getLogger(RandomUtil.class);
	
	private static final String[] RANDOM_NUMBER = {			
			"1","2","3","4","5","6","7","8","9"
	};
	
	public static String createRandomNumber(int paramLength) {
		StringBuffer sb = new StringBuffer();		
		
		try {
			
			int len = RANDOM_NUMBER.length;
			
			for (int i=0; i<paramLength; i++) {
				int ran = (int)(Math.random() * len);
				sb.append(RANDOM_NUMBER[ran]);
			}
			
		} catch (Exception e) {
			log.error("RandomUtil createRandomNumber 에러발생 : \n" + e.toString());
		}
		
		return sb.toString();
	}
	
//	public static String createRqstno() {
//		StringBuffer sb = new StringBuffer();
//		int paramLength = 6;
//		String rqstno = "";
//		
//		try {
//			
//			rqstno = "" + System.currentTimeMillis() + "I" + RandomUtil.createRandomNumber(paramLength);
//			
//		} catch (Exception e) {
//			log.error("RandomUtil createRandomNumber 에러발생 : \n" + e.toString());
//		}
//		
//		return rqstno;
//	}

}
