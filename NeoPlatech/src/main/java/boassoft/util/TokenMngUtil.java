package boassoft.util;

import org.apache.log4j.Logger;

public class TokenMngUtil {

private static final Logger log = Logger.getLogger(TokenMngUtil.class);
	
	private static final String[] RANDOM_TOKEN_TEXT = {
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",			
			"0","1","2","3","4","5","6","7","8","9"
	};
	
	public static String createAppToken(int tokenLength) {
		StringBuffer sb = new StringBuffer();		
		
		try {
			
			int len = RANDOM_TOKEN_TEXT.length;
			
			for (int i=0; i<tokenLength; i++) {
				int ran = (int)(Math.random() * len);
				sb.append(RANDOM_TOKEN_TEXT[ran]);
			}
			
		} catch (Exception e) {
			log.error("TokenMngUtil createToken 에러발생 : \n" + e.toString());
		}
		
		return sb.toString();
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		TokenMngUtil tokenMngUtil = new TokenMngUtil();
		String tkn = tokenMngUtil.createAppToken(32);
		System.out.println(tkn);
	}*/
}
