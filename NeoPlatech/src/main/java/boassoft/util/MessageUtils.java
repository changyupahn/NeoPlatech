package boassoft.util;

import java.util.Random;

public class MessageUtils {

	public static int generateNumber(int length) {
		String numStr = "1";
		String plusNumStr = "1";

		for (int i = 0; i < length; i++) {
			numStr += "0";

			if (i != length - 1) {
				plusNumStr += "0";
			}
		}

		Random random = new Random();
		int result = random.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);

		if (result > Integer.parseInt(numStr)) {
			result = result - Integer.parseInt(plusNumStr);
		}

		return result;
	}

	public static String getRqstNo() {
		String str = "";

		// 형식 : System.currentTimeMillis() + 연계 구분값 + 랜덤값 6자리
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis()).append("A").append(generateNumber(6));

		str = sb.toString();

		return str;
	}
}
