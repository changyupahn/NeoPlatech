package boassoft.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import egovframework.com.utl.fcc.service.EgovDateUtil;
import boassoft.util.DateUtil;

public class DateUtil extends EgovDateUtil {

	public static String getFormatDate () {
		return getFormatDate("yyyyMMdd");
	}

	public static String getFormatDate (String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String formatDateTime(String sDate, String chDate, String chTime) {
		return formatDateTime(sDate, chDate, chTime, 14);
	}

	public static String formatDateTime(String sDate, String chDate, String chTime, int cutLength) {
		String yyyy = "";
        String mm = "";
        String dd = "";
        String hh24 = "";
        String mi = "";
        String ss = "";

        if (sDate == null) {
        	return "";
        } else {
        	sDate = sDate.replaceAll("\\D", "");
        }

        if (cutLength > 0 && sDate.length() > cutLength) {
        	sDate = sDate.substring(0, cutLength);
        }

        if (sDate.length() == 14) {
        	yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);
            hh24 = sDate.substring(8, 10);
            mi = sDate.substring(10, 12);
            ss = sDate.substring(12, 14);

            return yyyy + chDate + mm + chDate + dd + " " + hh24 + chTime + mi + chTime + ss;
        }
        else if (sDate.length() == 12) {
        	yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);
            hh24 = sDate.substring(8, 10);
            mi = sDate.substring(10, 12);

            return yyyy + chDate + mm + chDate + dd + " " + hh24 + chTime + mi;
        }
        else if (sDate.length() == 10) {
        	yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);
            hh24 = sDate.substring(8, 10);

            return yyyy + chDate + mm + chDate + dd + " " + hh24;
        }
        else if (sDate.length() == 8) {
        	yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);

            return yyyy + chDate + mm + chDate + dd;
        }
        else if (sDate.length() == 6) {
        	yyyy = sDate.substring(0, 2);
            mm = sDate.substring(2, 4);
            dd = sDate.substring(4, 6);

            return yyyy + chDate + mm;
        }
        else {
        	return "";
        }
	}

	/**
     * �Է¹��� ���� ���ڰ��� �������� ���� ��ȯ
     * @param sMonth ���� ��
     * @return �������� ��
     */
	public static String convertMonthEng(String sMonth) {
		String retStr = null;

		String m = StringUtil.lpad(sMonth, 2, "0");

		if (sMonth.length() == 8) {
			m = m.substring(4,6);
		}

		if        ("01".equals(m)   ) { retStr = "JAN";
		} else if ("02".equals(m)   ) { retStr = "FEB";
		} else if ("03".equals(m)   ) { retStr = "MAR";
		} else if ("04".equals(m)   ) { retStr = "APR";
		} else if ("05".equals(m)   ) { retStr = "MAY";
		} else if ("06".equals(m)   ) { retStr = "JUN";
		} else if ("07".equals(m)   ) { retStr = "JUL";
		} else if ("08".equals(m)   ) { retStr = "AUG";
		} else if ("09".equals(m)   ) { retStr = "SEP";
		} else if ("10".equals(m)   ) { retStr = "OCT";
		} else if ("11".equals(m)   ) { retStr = "NOV";
		} else if ("12".equals(m)   ) { retStr = "DEC";
		}

		return retStr;
	}

	/**
     * �Է¹��� ������ ���� Ȯ�� �� ��ȯ
     * @param sDate ���� ����
     * @return ���� ���� ��
     */
	public static String convertDateNum(String sDate) {

		String d = StringUtil.lpad(sDate, 2, "0");

		if (sDate.length() == 8) {
			d = d.substring(6,8);
		}

		return d;
	}

	/**
     * �Է¹��� ���� ���� Ȯ�� �� ��ȯ
     * @param sMonth ���� ��
     * @return ���� �� ��
     */
	public static String convertMonthNum(String sMonth) {

		String m = StringUtil.lpad(sMonth, 2, "0");

		if (sMonth.length() == 8) {
			m = m.substring(4,6);
		}

		return m;
	}

	/**
     * �Է¹��� ���� ���� Ȯ�� �� ��ȯ
     * @param sMonth ���� ��
     * @return ���� �� ��
     */
	public static String convertYearNum(String sYear) {

		String y = StringUtil.lpad(sYear, 4, "0");

		if (sYear.length() == 8) {
			y = y.substring(0,4);
		}

		return y;
	}

	public static int getLastDateOfMonth () {
		return getLastDateOfMonth(DateUtil.getFormatDate("yyyyMM"));
	}

	public static int getLastDateOfMonth (String yyyyMM) {

		if (yyyyMM == null || yyyyMM.length() != 6)
			return 1;

		int year = Integer.parseInt(yyyyMM.substring(0,4));
		int month = Integer.parseInt(yyyyMM.substring(4,6));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);

		return cal.getMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getHoursDiff(String sDate1, String sDate2) {
		int result = 0;

		try {
			sDate1 = sDate1.replaceAll("\\D", "");
			sDate2 = sDate2.replaceAll("\\D", "");

			if (sDate1.length() < 12 || sDate2.length() < 12) {
				return 0;
			}

			//요청시간 String String reqDateStr = "201702011535"
			//현재시간 Date Date curDate = new Date()
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
			//요청시간을 Date로 parsing 후 time가져오기 Date reqDate = dateFormat.parse(reqDateStr)
			long date1 = dateFormat.parse(sDate1).getTime();
			long date2 = dateFormat.parse(sDate2).getTime();

			if (date1 < date2) {
				return 0;
			}

			CommonMap cmap = new CommonMap();
			cmap.put("diff", ((date1 - date2) / 1000 / 60 / 60));

			result = cmap.getInt("diff");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
    }

	public static int getMinuteDiff(String startDate1, String endDate2) {
		int result = 0;

		try {
			startDate1 = startDate1.replaceAll("\\D", "");
			endDate2 = endDate2.replaceAll("\\D", "");

			if (startDate1.length() < 12 || endDate2.length() < 12) {
				return 0;
			}

			startDate1 = startDate1.substring(0,12);
			endDate2 = endDate2.substring(0,12);

			//요청시간 String String reqDateStr = "201702011535"
			//현재시간 Date Date curDate = new Date()
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			//요청시간을 Date로 parsing 후 time가져오기 Date reqDate = dateFormat.parse(reqDateStr)
			long date1 = dateFormat.parse(startDate1).getTime();
			long date2 = dateFormat.parse(endDate2).getTime();

			if (date1 > date2) {
				return 0;
			}

			CommonMap cmap = new CommonMap();
			cmap.put("diff", Math.round((date2 - date1) / 1000 / 60));

			result = cmap.getInt("diff");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
    }

}
