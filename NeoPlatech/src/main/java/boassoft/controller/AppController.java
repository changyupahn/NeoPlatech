package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import boassoft.service.AppService;
import boassoft.service.AssetService;
import boassoft.service.DeviceService;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.TokenMngUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;

@Controller
public class AppController {

	@Resource(name = "CommonMap")
	private CommonMap commonMap;

	@Resource(name = "AppService")
	private AppService appService;

	@Resource(name = "DeviceService")
	private DeviceService deviceService;

	@Resource(name = "AssetService")
	private AssetService assetService;

	/** log */
	protected static final Log LOG = LogFactory.getLog(AppController.class);

	protected static final String RET_SUCCESS = "OK";
	protected static final String RET_ERROR = "ER-10000";
	protected static final String RET_ERROR_MSG = "처리 중 에러가 발생하였습니다";

	/**
	 * [101] 인증요청 (로그인)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/rfid/login.do", method = RequestMethod.POST)
	public String appRfidLogin(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/app/rfid/login.do" + " - " + cmap);
		cmap.put("userId", cmap.getString("userId", ""));
		cmap.put("userPw", cmap.getString("userPw", ""));
		cmap.put("deviceno", cmap.getString("deviceno", ""));

		CommonMap result = new CommonMap();

		try {

			// 필수 파라미터 체크
			if ("".equals(cmap.getString("userId"))
					|| "".equals(cmap.getString("userPw"))
					|| "".equals(cmap.getString("deviceno"))) {
				result.put("ret", "ER-10100");
				result.put("retmsg", "필수 파라미터 체크 하십시오.");
			} else {

				boolean sw = true;

				// 비밀번호 암호화
				cmap.put("userPw",
						EgovFileScrty.encryptPassword(cmap.getString("userPw")));

				// 사용자 정보 조회
				CommonMap admin = appService.getRfidAdminView(cmap);

				// 디바이스 정보 조회
				CommonMap device = appService.getRfidDeviceView(cmap);

				// 계정 체크 > cnfmStatusCd : Y승인완료, N승인거부, R승인대기, C승인취소
				if (admin.isEmpty()) {
					result.put("ret", "ER-10111");
					result.put("retmsg",
							"일치하는 고객 정보가 없습니다.\n아이디 확인후 다시 로그인해 주십시오.");
					sw = false;
				} else if (!admin.getString("userPw").equals(
						cmap.getString("userPw"))) {
					result.put("ret", "ER-10112");
					result.put("retmsg",
							"일치하는 고객 정보가 없습니다.\n비밀번호 확인후 다시 로그인해 주십시오.");
					sw = false;
				} else if ("R".equals(admin.getString("cnfmStatusCd"))) {
					// 승인대기
					result.put("ret", "ER-10121");
					result.put("retmsg", "승인 대기 중인 사용자 입니다.");
					sw = false;
				} else if ("N".equals(admin.getString("cnfmStatusCd"))) {
					// 승인거부
					result.put("ret", "ER-10122");
					result.put("retmsg", "승인 거부 된 사용자 입니다.");
					sw = false;
				} else if ("C".equals(admin.getString("cnfmStatusCd"))) {
					// 승인취소
					result.put("ret", "ER-10123");
					result.put("retmsg", "승인 취소 된 사용자 입니다.");
					sw = false;
				} else if (!"Y".equals(admin.getString("cnfmStatusCd"))) {
					// 승인코드 값 오류
					result.put("ret", "ER-10131");
					result.put("retmsg", "승인 코드 값 오류");
					sw = false;
				} else if (admin.getString("cnfmStartDt").length() != 8
						|| admin.getString("cnfmEndDt").length() != 8) {
					// 승인기간 값 오류
					result.put("ret", "ER-10132");
					result.put("retmsg", "승인 기간 값 오류");
					sw = false;
				} else if (admin.getInt("cnfmStartDt") > Integer
						.parseInt(DateUtil.getFormatDate("yyyyMMdd"))
						|| admin.getInt("cnfmEndDt") < Integer
								.parseInt(DateUtil.getFormatDate("yyyyMMdd"))) {
					// 승인완료 인데, 승인기간 아닐 경우
					result.put("ret", "ER-10141");
					result.put("retmsg", "승인된 기간이 아닙니다.");
					sw = false;
				}

				if (sw == true) {

					int useDeviceQnt = admin.getInt("useDeviceQnt");
					int maxDeviceQnt = admin.getInt("maxDeviceQnt");

					if (device.isEmpty()
							&& !"".equals(cmap.getString("deviceno"))
							&& maxDeviceQnt > 0) {
						if (useDeviceQnt < maxDeviceQnt) {
							// 초기 디바이스 인증
							cmap.put("devicenm", cmap.getString("userId") + "-"
									+ cmap.getString("deviceno"));
							cmap.put("orgNo", admin.getString("orgNo"));
							deviceService.insertDevice(cmap);
							device = appService.getRfidDeviceView(cmap);
						} else if (useDeviceQnt >= maxDeviceQnt) {
							// 디바이스 수량 MAX
							result.put("ret", "ER-10151");
							result.put(
									"retmsg",
									"이미 "
											+ useDeviceQnt
											+ "대의 인증된 디바이스가 있어 추가로 인증할 수 없습니다. WEB관리자 디바이스 설정을 참조하세요.");
							sw = false;
						}
					}
				}

				if (sw == true) {
					// 디바이스 체크
					if (device.isEmpty()) {
						// 디바이스 인증 실패
						result.put("ret", "ER-10152");
						result.put("retmsg", "인증된 디바이스가 아닙니다.");
						sw = false;
					} else if (!admin.getString("orgNo").equals(
							device.getString("orgNo"))) {
						// 디바이스 인증 실패 (승인 기관이 다름)
						result.put("ret", "ER-10153");
						result.put("retmsg", "다른 기관에 인증된 디바이스 입니다.");
						sw = false;
					}
				}

				if (sw == true) {
					// 토큰 생성
					String deviceToken = TokenMngUtil.createAppToken(32);

					// 토큰 저장
					cmap.put("deviceno", device.getString("deviceno"));
					cmap.put("deviceToken", deviceToken);
					cmap.put("tokenInvalidDt", DateUtil.addDay(
							DateUtil.getFormatDate("yyyyMMdd"), 7)); // 토큰 생성 후
																		// 7일
					int updateCnt = appService.updateRfidDeviceToken(cmap);

					if (updateCnt > 0) {
						// 정상로그인
						result.put("ret", RET_SUCCESS);
						result.put("retmsg", "로그인 처리 되었습니다.");
						result.put("orgNo", admin.getString("orgNo"));
						result.put("deviceToken", deviceToken);
					} else {
						result.put("ret", "ER-10161");
						result.put("retmsg", "토큰 갱신에 실패하였습니다.");
					}
				}
			}

			model.addAttribute("printString", result.toJsonString());

		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
			result.put("ret", RET_ERROR);
			result.put("retmsg", RET_ERROR_MSG);
			model.addAttribute("printString", result.toJsonString());
		}

		return "common/commonString";
	}
}
