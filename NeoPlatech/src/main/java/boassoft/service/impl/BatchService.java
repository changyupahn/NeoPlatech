package boassoft.service.impl;

public class BatchService {

	
	@Resource(name="BatchDAO")
    private BatchDAO batchDAO;

	@Resource(name="MisDAO")
    private MisDAO misDAO;

	@Resource(name="ZeusDAO")
    private ZeusDAO zeusDAO;

	@Resource(name="ZeusOperDAO")
    private ZeusOperDAO zeusOperDAO;

	@Resource(name="ZeusOperListDAO")
    private ZeusOperListDAO zeusOperListDAO;

	@Resource(name="ZeusAsDAO")
    private ZeusAsDAO zeusAsDAO;

	@Resource(name="ZeusAsListDAO")
    private ZeusAsListDAO zeusAsListDAO;

	@Resource(name="ZeusCodeDAO")
    private ZeusCodeDAO zeusCodeDAO;

	@Resource(name = "UserDAO")
    private UserDAO userDAO;

	@Resource(name="ApprAssetDAO")
    private ApprAssetDAO apprAssetDAO;

	@Resource(name="AssetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name="ApprIoOutDAO")
    private ApprIoOutDAO apprIoOutDAO;

	@Resource(name="ApprIoInDAO")
    private ApprIoInDAO apprIoInDAO;

	@Resource(name="ApprIoExtDAO")
    private ApprIoExtDAO apprIoExtDAO;

	@Resource(name="ApprDisuseDAO")
    private ApprDisuseDAO apprDisuseDAO;

	@Resource(name="AssetDAO")
    private AssetDAO assetDAO;

	@Resource(name="BatchMysqlService")
    private BatchMysqlService batchMysqlService;

	/** 제우스 장비 동기화 */
	public void syncZeusAsset(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusAsset() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "0");

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s?page=%s&pageSize=%s"
				, key
				, cmap.getString("pageIdx")
				, cmap.getString("pageSize")
				);
		String resultStr = HttpZeusUtil.get(url);

		JSONObject resultObject = JSONObject.fromObject(resultStr);
		JSONArray resultArray = resultObject.getJSONArray("pageList");

		if (resultArray != null && resultArray.size() > 0) {
			zeusDAO.deleteZeusAll(cmap);

			for (int i=0; i<resultArray.size(); i++) {
				JSONObject resultObj = resultArray.getJSONObject(i);
				cmap.put("equipId", StringUtil.nvl2(resultObj.getString("equipId")));
				cmap.put("equipNo", StringUtil.nvl2(resultObj.getString("equipNo")));
				cmap.put("equipCd", StringUtil.nvl2(resultObj.getString("equipCd")));
				cmap.put("assetNo", StringUtil.nvl2(resultObj.getString("fixedAsetNo")));

				cmap.put("korNm", StringUtil.nvl2(resultObj.getString("korNm")));
				cmap.put("engNm", StringUtil.nvl2(resultObj.getString("engNm")));
				cmap.put("statusCd", StringUtil.nvl2(resultObj.getString("statusCd")));
				cmap.put("statusNm", StringUtil.nvl2(resultObj.getString("statusNm")));
				cmap.put("useScopeCd", StringUtil.nvl2(resultObj.getString("useScopeCd")));
				cmap.put("useScopeNm", StringUtil.nvl2(resultObj.getString("useScopeNm")));
				cmap.put("idleDisuseCd", StringUtil.nvl2(resultObj.getString("idleDisuseCd")));
				cmap.put("idleDisuseNm", StringUtil.nvl2(resultObj.getString("idleDisuseNm")));
				cmap.put("organNm", StringUtil.nvl2(resultObj.getString("organNm")));
				cmap.put("rndYn", StringUtil.nvl2(resultObj.getString("rndYn")));
				cmap.put("rndNm", StringUtil.nvl2(resultObj.getString("rndNm")));
				cmap.put("confirmYn", StringUtil.nvl2(resultObj.getString("confirmYn")));
				cmap.put("deleteYn", StringUtil.nvl2(resultObj.getString("deleteYn")));
				cmap.put("registId", StringUtil.nvl2(resultObj.getString("registId")));
				cmap.put("registNm", StringUtil.nvl2(resultObj.getString("registNm")));
				cmap.put("registDt", StringUtil.nvl2(resultObj.getString("registDt")));
				cmap.put("modifyDt", StringUtil.nvl2(resultObj.getString("modifyDt")));
				cmap.put("apiYn", StringUtil.nvl2(resultObj.getString("apiYn")));

				cmap.put("assetNo", cmap.getString("assetNo").replaceAll("-00", ""));
				cmap.put("assetNo", cmap.getString("assetNo").replaceAll("-", ""));
				cmap.put("assetNo", cmap.getString("assetNo").toUpperCase());
				cmap.put("photoWebPath", "");
				cmap.put("photoThumbWebPath", "");

				//상세정보 (photoWebPath 없는 경우의 예외 처리를 위하여 try catch 함)
				String url2 = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s"
						, cmap.getString("equipId")
						, key
						);
				String resultStr2 = HttpZeusUtil.get(url2);

				JSONObject resultObject2 = JSONObject.fromObject(resultStr2);
				try {
					cmap.put("takeDt", StringUtil.nvl2(resultObject2.get("takeDt")));
				} catch (Exception e) {
				}

				//try {
					if (StringUtil.nvl2(resultObject2.get("photoWebPath")).indexOf("http") > -1) {
						cmap.put("photoWebPath", StringUtil.nvl2(resultObject2.get("photoWebPath")));
					}
					if (StringUtil.nvl2(resultObject2.get("photoThumbWebPath")).indexOf("http") > -1) {
						cmap.put("photoThumbWebPath", StringUtil.nvl2(resultObject2.get("photoThumbWebPath")));
					}
				//} catch (Exception e) {
				//}

				zeusDAO.insertZeus(cmap);

//				if (!"".equals(cmap.getString("useScopeCd"))) {
//					if ("1".equals(cmap.getString("useScopeCd"))) {
//						cmap.put("aplctnRangeCd", "단독활용");
//					} else if ("2".equals(cmap.getString("useScopeCd"))) {
//						cmap.put("aplctnRangeCd", "공동활용허용");
//					} else if ("3".equals(cmap.getString("useScopeCd"))) {
//						cmap.put("aplctnRangeCd", "공동활용서비스");
//					}
//				}
			}
		}

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusAsset() - end");
	}

	/** 제우스 운영일지 동기화 */
	public void syncZeusOper(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusOper() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/oper/operating-equips/%s?page=%s&pageSize=%s"
				, key
				, cmap.getString("pageIdx")
				, cmap.getString("pageSize")
				);
		String resultStr = HttpZeusUtil.get(url);

		JSONObject resultObject = JSONObject.fromObject(resultStr);
		JSONArray resultArray = resultObject.getJSONArray("pageList");

		if (resultArray != null && resultArray.size() > 0) {
			zeusOperDAO.deleteZeusOperAll(cmap);

			for (int i=0; i<resultArray.size(); i++) {
				JSONObject resultObj = resultArray.getJSONObject(i);
				cmap.put("equipId", resultObj.getString("equipId"));
				cmap.put("equipCd", resultObj.getString("equipCd"));
				cmap.put("assetNo", resultObj.getString("fixedAsetNo"));
				cmap.put("korNm", resultObj.getString("korNm"));
				cmap.put("engNm", resultObj.getString("engNm"));
				cmap.put("registDt", resultObj.getString("registDt"));

				zeusOperDAO.insertZeusOper(cmap);
			}
		}

		//제우스 운영일지 동기화
		try {

			zeusOperListDAO.deleteZeusOperListAll(cmap);

			int pageIdx = 0;
			int pageCnt = 10;
			while (pageIdx < pageCnt) {

				pageIdx++;

				String url3 = String.format("http://api.zeus.go.kr/api/oper/operating/%s?page=%s&pageSize=%s&keywords=%s"
						, key
						, "" + pageIdx
						, "100"
						, "" //URLEncoder.encode(resultObj.getString("fixedAsetNo"), "utf-8")
						);
				String resultStr3 = HttpZeusUtil.get(url3);

				JSONObject resultObject3 = JSONObject.fromObject(resultStr3);
				JSONArray resultArray3 = resultObject3.getJSONArray("pageList");

				if (pageIdx <= 1)
					pageCnt = StringUtil.nvl(resultObject3.getString("pageCount"), 0);

				if (resultArray3 != null && resultArray3.size() > 0) {

					for (int k=0; k<resultArray3.size(); k++) {
						JSONObject resultObj3 = resultArray3.getJSONObject(k);
						CommonMap cmap3 = new CommonMap();
						cmap3.put("journalSeq", resultObj3.getString("journalSeq"));
						cmap3.put("equipId", resultObj3.getString("equipId"));
						cmap3.put("assetNo", resultObj3.getString("fixedAsetNo"));
						cmap3.put("korNm", resultObj3.getString("korNm"));
						cmap3.put("journalSdt", resultObj3.getString("journalSdt"));
						cmap3.put("journalEdt", resultObj3.getString("journalEdt"));
						cmap3.put("journalPrc", resultObj3.getString("journalPrc"));
						cmap3.put("registDt", resultObj3.getString("registDt"));

						//상세조회
						try {
							String url2 = String.format("http://api.zeus.go.kr/api/oper/operating/%s/%s"
									, cmap3.getString("journalSeq")
									, key
									);
							String resultStr2 = HttpZeusUtil.get(url2);

							JSONObject resultObject2 = JSONObject.fromObject(resultStr2);
							cmap3.put("journalShour", resultObject2.getString("journalShour"));
							cmap3.put("journalSminute", resultObject2.getString("journalSminute"));
							cmap3.put("journalEhour", resultObject2.getString("journalEhour"));
							cmap3.put("journalEminute", resultObject2.getString("journalEminute"));

							String sDate1 = cmap3.getString("journalSdt");
							String sDate2 = cmap3.getString("journalEdt");
							cmap3.put("diffMinute", DateUtil.getMinuteDiff(sDate1, sDate2));

							if (cmap3.getString("journalSdt").replaceAll("\\D","").length() >= 8
									&& cmap3.getString("journalEdt").replaceAll("\\D","").length() >= 8)
							{
								sDate1 = cmap3.getString("journalSdt").replaceAll("\\D","").substring(0,8);
								sDate2 = cmap3.getString("journalEdt").replaceAll("\\D","").substring(0,8);
								cmap3.put("diffDay", DateUtil.getDaysDiff(sDate1, sDate2) + 1);
							} else {
								cmap3.put("diffDay", 0);
							}

							System.out.println("diffMinute : " + cmap3.getString("diffMinute"));
							System.out.println("diffDay : " + cmap3.getString("diffDay"));

						} catch (Exception e) {
						}

						zeusOperListDAO.insertZeusOperList(cmap3);

						System.out.println("pageCnt : " + pageCnt);
						System.out.println("pageIdx : " + pageIdx);
					}

				} else {
					break;
				}
			}

		} catch (Exception e) {
		}

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusOper() - end");
	}

	/** 제우스 유지보수일지 동기화 */
	public void syncZeusAs(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusAs() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/main/maintenance-equips/%s?page=%s&pageSize=%s"
				, key
				, cmap.getString("pageIdx")
				, cmap.getString("pageSize")
				);
		String resultStr = HttpZeusUtil.get(url);

		JSONObject resultObject = JSONObject.fromObject(resultStr);
		JSONArray resultArray = resultObject.getJSONArray("pageList");

		if (resultArray != null && resultArray.size() > 0) {
			zeusAsDAO.deleteZeusAsAll(cmap);

			for (int i=0; i<resultArray.size(); i++) {
				JSONObject resultObj = resultArray.getJSONObject(i);
				cmap.put("equipId", resultObj.getString("equipId"));
				cmap.put("equipCd", resultObj.getString("equipCd"));
				cmap.put("assetNo", resultObj.getString("fixedAsetNo"));
				cmap.put("korNm", resultObj.getString("korNm"));
				cmap.put("engNm", resultObj.getString("engNm"));
				cmap.put("registDt", resultObj.getString("registDt"));

				zeusAsDAO.insertZeusAs(cmap);
			}
		}

		//제우스 유지보수일지 동기화
		try {

			zeusAsListDAO.deleteZeusAsListAll(cmap);

			int pageIdx = 0;
			int pageCnt = 10;
			while (pageIdx < pageCnt) {

				pageIdx++;

				String url3 = String.format("http://api.zeus.go.kr/api/main/maintenance/%s?page=%s&pageSize=%s&keywords=%s"
						, key
						, "" + pageIdx
						, "100"
						, "" //URLEncoder.encode(cmap.getString("equipNo"), "utf-8")
						);
				String resultStr3 = HttpZeusUtil.get(url3);

				JSONObject resultObject3 = JSONObject.fromObject(resultStr3);
				JSONArray resultArray3 = resultObject3.getJSONArray("pageList");

				if (pageIdx <= 1)
					pageCnt = StringUtil.nvl(resultObject3.getString("pageCount"), 0);

				if (resultArray3 != null && resultArray3.size() > 0) {

					for (int k=0; k<resultArray3.size(); k++) {
						JSONObject resultObj3 = resultArray3.getJSONObject(k);
						CommonMap cmap3 = new CommonMap();
						cmap3.put("journalSeq", resultObj3.getString("journalSeq"));
						cmap3.put("equipId", resultObj3.getString("equipId"));
						cmap3.put("assetNo", resultObj3.getString("fixedAsetNo"));
						cmap3.put("korNm", resultObj3.getString("korNm"));
						cmap3.put("journalSdt", resultObj3.getString("journalSdt"));
						cmap3.put("journalEdt", resultObj3.getString("journalEdt"));
						cmap3.put("journalPrc", resultObj3.getString("journalPrc"));
						cmap3.put("registDt", resultObj3.getString("registDt"));

						//상세조회
						try {
							String url2 = String.format("http://api.zeus.go.kr/api/main/maintenance/%s/%s"
									, cmap3.getString("journalSeq")
									, key
									);
							String resultStr2 = HttpZeusUtil.get(url2);

							JSONObject resultObject2 = JSONObject.fromObject(resultStr2);
							cmap3.put("journalShour", resultObject2.getString("journalShour"));
							cmap3.put("journalSminute", resultObject2.getString("journalSminute"));
							cmap3.put("journalEhour", resultObject2.getString("journalEhour"));
							cmap3.put("journalEminute", resultObject2.getString("journalEminute"));
							cmap3.put("registrentNm", resultObject2.getString("registrentNm"));
							cmap3.put("journalUserNm", resultObject2.getString("journalUserNm"));
							cmap3.put("contents", resultObject2.getString("contents"));

							String sDate1 = cmap3.getString("journalSdt");
							String sDate2 = cmap3.getString("journalEdt");
							cmap3.put("diffMinute", DateUtil.getMinuteDiff(sDate1, sDate2));

							if (cmap3.getString("journalSdt").replaceAll("\\D","").length() >= 8
									&& cmap3.getString("journalEdt").replaceAll("\\D","").length() >= 8)
							{
								sDate1 = cmap3.getString("journalSdt").replaceAll("\\D","").substring(0,8);
								sDate2 = cmap3.getString("journalEdt").replaceAll("\\D","").substring(0,8);
								cmap3.put("diffDay", DateUtil.getDaysDiff(sDate1, sDate2) + 1);
							} else {
								cmap3.put("diffDay", 0);
							}

							System.out.println("diffMinute : " + cmap3.getString("diffMinute"));
							System.out.println("diffDay : " + cmap3.getString("diffDay"));

						} catch (Exception e) {
						}

						zeusAsListDAO.insertZeusAsList(cmap3);

						System.out.println("pageCnt : " + pageCnt);
						System.out.println("pageIdx : " + pageIdx);
					}
				} else {
					break;
				}
			}

		} catch (Exception e) {
		}

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusAs() - end");
	}

	/** 제우스 코드 동기화 */
	public void syncZeusCode(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusCode() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "0");

    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/%s/%s"
				, "codes"
				, key
				);

		String paramters = "";

		String resultStr = HttpZeusUtil.get(url + paramters);
		System.out.println(resultStr);

		String[] codeIdArr = {"managers"};

		JSONObject resultObject = JSONObject.fromObject(resultStr);

		if (resultObject != null && !resultObject.isEmpty()) {
			zeusCodeDAO.deleteZeusCodeAll(cmap);
		}

		for (int k=0; k<codeIdArr.length; k++) {

			JSONArray resultArray = resultObject.getJSONArray(codeIdArr[k]);

			if (resultArray != null && resultArray.size() > 0) {

				for (int i=0; i<resultArray.size(); i++) {
					JSONObject resultObj = resultArray.getJSONObject(i);
					cmap.put("codeId", codeIdArr[k]);
					cmap.put("code", StringUtil.nvl2(resultObj.get("code")));
					cmap.put("name", StringUtil.nvl2(resultObj.get("name")));
					cmap.put("level", StringUtil.nvl(StringUtil.nvl2(resultObj.get("level")),null));
					cmap.put("codes", StringUtil.nvl2(resultObj.get("codes")));
					cmap.put("def", StringUtil.nvl2(resultObj.get("def")));

					zeusCodeDAO.insertZeusCode(cmap);
				}
			}
		}

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncZeusCode() - end");
	}

	/** 연계한 제우스 장비 목록과 자산번호를 매칭하여 자산의 장비ID를 업데이트 함. */
	public void syncZeusToAsset(CommonMap cmap) throws Exception {
		batchDAO.syncZeusToAsset(cmap);
	}

	/** 승인정보 동기화 */
	public void syncMisDoc(CommonMap cmap) throws Exception {

		String sRqstno = System.currentTimeMillis() - ((long)1000 * 60 * 60 * 24 * 7) + "";
		String eRqstno = System.currentTimeMillis() + "";

		cmap.put("sRqstno", cmap.getString("sRqstno", sRqstno));
		cmap.put("eRqstno", cmap.getString("eRqstno", eRqstno));

		CommonList misDocList = misDAO.getMisDocList(cmap);

		for (int i=0; i<misDocList.size(); i++) {
			CommonMap misDoc = misDocList.getMap(i);
			int updateCnt = batchDAO.updateDoc(misDoc);
			if (updateCnt == 0) {
				batchDAO.insertDoc(misDoc);
			}
		}
	}

	/** 승인정보 동기화 */
	public void syncMisDocTemp(CommonMap cmap) throws Exception {

		String sRqstno = System.currentTimeMillis() - ((long)1000 * 60 * 60 * 24 * 7) + "";
		String eRqstno = System.currentTimeMillis() + "";

		cmap.put("sRqstno", cmap.getString("sRqstno", sRqstno));
		cmap.put("eRqstno", cmap.getString("eRqstno", eRqstno));

		CommonList misDocList = misDAO.getMisDocList(cmap);

		for (int i=0; i<misDocList.size(); i++) {
			CommonMap misDoc = misDocList.getMap(i);

			misDoc.put("docstatcd", "0040005");
			misDoc.put("docstatnm", "처리완료");

			int updateCnt = batchDAO.updateDoc(misDoc);
			if (updateCnt == 0) {
				batchDAO.insertDoc(misDoc);
			}
		}
	}

	/** 승인정보 반영 */
	public void syncMisDocAppr(CommonMap cmap) throws Exception {

		CommonList docNList = batchDAO.getDocNList(cmap);
		int updateCnt = 0;

		for (int i=0; i<docNList.size(); i++) {
			CommonMap doc = docNList.getMap(i);

			if ("0040001".equals(doc.getString("docstatcd"))
					||"0040002".equals(doc.getString("docstatcd"))
					||"0040003".equals(doc.getString("docstatcd"))
					||"0040004".equals(doc.getString("docstatcd"))
					) {
				//초기값이 승인 진행중이므로 업데이트 하지 않도록 주석 처리
				//misDoc.put("rqstStatusCd", "2");
			} else if ("0040005".equals(doc.getString("docstatcd"))
					) {
				doc.put("rqstStatusCd", "3");

			} else if ("0040006".equals(doc.getString("docstatcd"))
					) {
				doc.put("rqstStatusCd", "4");
			}

			if (!"".equals(doc.getString("rqstno"))
					&& !"".equals(doc.getString("rqstStatusCd"))) {

				//인수인계 / 반출 / 불용 신청의 경우 업데이트 처리 (반입연장, 반입 신청을 따로 처리)
				if ("0030170".equals(doc.getString("docdivcd"))
						|| "0030172".equals(doc.getString("docdivcd"))
						|| "0030171".equals(doc.getString("docdivcd"))
						) {
					updateCnt = batchDAO.updateApprRqst(doc);
				}
			}

			//인수인계 승인완료
			if ("0030170".equals(doc.getString("docdivcd"))
					&& "3".equals(doc.getString("rqstStatusCd"))) {
				//사용자 조회
				CommonMap apprUsercngView = batchDAO.getApprUsercngView(doc);

				if (updateCnt > 0 && !apprUsercngView.isEmpty()) {

					doc.put("topDeptNo", apprUsercngView.getString("aucTopDeptNo"));
					doc.put("topDeptName", apprUsercngView.getString("aucTopDeptName"));
					doc.put("deptNo", apprUsercngView.getString("aucDeptNo"));
					doc.put("deptName", apprUsercngView.getString("aucDeptName"));
					doc.put("userNo", apprUsercngView.getString("aucUserNo"));
					doc.put("userName", apprUsercngView.getString("aucUserName"));

					int updateCnt2 = batchDAO.updateAssetUsercng(doc);

					if (updateCnt2 > 0) {
						//승인 자산 목록
				    	CommonList dataList = apprAssetDAO.getApprAssetList(doc);

				    	//승인신청 히스토리
				    	for (int k=0; k<dataList.size(); k++) {
				    		CommonMap data = dataList.getMap(k);

				    		doc.put("assetSeq", data.getString("assetSeq"));
				    		doc.put("histTypeCd", "4");
				    		doc.put("histContent", String.format("사용자 [%s]에서 [%s]로 인수인계 (승인완료)"
				    				, data.getString("userName")
				    				, apprUsercngView.getString("aucUserName")
				    				));
				    		doc.put("frstRegisterId", data.getString("registId"));
				        	assetHistoryService.insertAssetHistory(doc);
				    	}
					}
				}
			}

			//반출신청 승인완료
			if ("0030172".equals(doc.getString("docdivcd"))
					&& "3".equals(doc.getString("rqstStatusCd"))) {

				if (updateCnt > 0) {

					//자산상태변경
					batchDAO.updateAssetIoOut(doc);

					//반출정보
					CommonMap apprIoOutView = apprIoOutDAO.getApprIoOutView(doc);

					//승인 자산 목록
			    	CommonList dataList = apprAssetDAO.getApprAssetList(doc);

			    	//승인신청 히스토리
			    	for (int k=0; k<dataList.size(); k++) {
			    		CommonMap data = dataList.getMap(k);

			    		doc.put("assetSeq", data.getString("assetSeq"));
			    		doc.put("histTypeCd", "5");
			    		doc.put("histContent", String.format("반출신청 - %s (승인완료)", apprIoOutView.getString("reason")));
			    		doc.put("frstRegisterId", data.getString("registId"));
			        	assetHistoryService.insertAssetHistory(doc);
			    	}
				}
			}

			//반입연장신청 승인완료
			if ("0030173".equals(doc.getString("docdivcd"))
					&& "3".equals(doc.getString("rqstStatusCd"))) {

				updateCnt = batchDAO.updateApprIoExt(doc);

				if (updateCnt > 0) {

					//반입연장정보
					doc.put("extRqstno", doc.getString("rqstno"));
					CommonMap apprIoExtView = apprIoExtDAO.getApprIoExtView(doc);

					//승인 자산 목록
			    	CommonList dataList = apprAssetDAO.getApprAssetList(apprIoExtView);

			    	//승인신청 히스토리
			    	for (int k=0; k<dataList.size(); k++) {
			    		CommonMap data = dataList.getMap(k);

			    		doc.put("assetSeq", data.getString("assetSeq"));
			    		doc.put("histTypeCd", "5");
			    		doc.put("histContent", String.format("반입연장신청 - %s (승인완료)", apprIoExtView.getString("extReason")));
			    		doc.put("frstRegisterId", data.getString("registId"));
			        	assetHistoryService.insertAssetHistory(doc);
			    	}
				}
			}

			//반입신청 승인완료
			if ("0030174".equals(doc.getString("docdivcd"))
					&& "3".equals(doc.getString("rqstStatusCd"))) {

				updateCnt = batchDAO.updateApprIoIn(doc);

				if (updateCnt > 0) {

					//반입정보
					doc.put("inRqstno", doc.getString("rqstno"));
					CommonMap apprIoInView = apprIoInDAO.getApprIoInView(doc);

					//자산상태변경
					batchDAO.updateAssetIoIn(apprIoInView);

					//승인 자산 목록
			    	CommonList dataList = apprAssetDAO.getApprAssetList(apprIoInView);

			    	//승인신청 히스토리
			    	for (int k=0; k<dataList.size(); k++) {
			    		CommonMap data = dataList.getMap(k);

			    		doc.put("assetSeq", data.getString("assetSeq"));
			    		doc.put("histTypeCd", "5");
			    		doc.put("histContent", String.format("반입신청 - %s (승인완료)", apprIoInView.getString("inRemark")));
			    		doc.put("frstRegisterId", data.getString("registId"));
			        	assetHistoryService.insertAssetHistory(doc);
			    	}
				}
			}

			//불용신청 승인완료
			if ("0030171".equals(doc.getString("docdivcd"))
					&& "3".equals(doc.getString("rqstStatusCd"))) {

				if (updateCnt > 0) {

					//불용승인일자
					doc.put("disuseCompDt", DateUtil.getFormatDate("yyyyMMdd"));
					batchDAO.updateApprDisuse(doc);

					//자산상태변경
					batchDAO.updateAssetDisuse(doc);

					//불용정보
					CommonMap apprDisuseView = apprDisuseDAO.getApprDisuseView(doc);

					//승인 자산 목록
			    	CommonList dataList = apprAssetDAO.getApprAssetList(doc);

			    	//승인신청 히스토리
			    	for (int k=0; k<dataList.size(); k++) {
			    		CommonMap data = dataList.getMap(k);

			    		doc.put("assetSeq", data.getString("assetSeq"));
			    		doc.put("histTypeCd", "6");
			    		doc.put("histContent", String.format("불용확인 - %s (승인완료)", apprDisuseView.getString("reason")));
			    		doc.put("frstRegisterId", data.getString("registId"));
			        	assetHistoryService.insertAssetHistory(doc);
			    	}
				}
			}

			if (updateCnt > 0) {

				batchDAO.updateDocUpdateynY(doc);
			}

			//DOCDIVCD => 0030170:인수인계, 0030171:불용신청, 0030172:반출신청, 0030173:반출연장신청, 0030174:자산반입신청

			/*// MIS 전가승인 상태
			0040001	임시저장
			0040002	승인
			0040003	승인진행
			0040004	접수대기
			0040005	처리완료
			0040006	승인반려
			0040007	승인보완
			0040008	수신
			0040009	발신
			*/

			/*// RFID 승인 상태
			2	승인진행중
			3	승인완료
			4	승인반려
			*/
		}
	}

	/** 사용자정보 동기화 */
	public void syncUser(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncUser() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		batchDAO.deleteMisUserAll(cmap);
		batchDAO.insertMisUserAll(cmap);
		batchDAO.deleteUser(cmap);
		batchDAO.mergeUser(cmap);

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncUser() - end");
	}

	/** 부서정보 동기화 */
	public void syncDept(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncDept() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		batchDAO.deleteMisDeptAll(cmap);
		batchDAO.insertMisDeptAll(cmap);
		batchDAO.deleteDept(cmap);
		batchDAO.mergeDept(cmap);

		/*CommonList misDeptList = misDAO.getMisDeptList(cmap);
		if (misDeptList.size() > 0) {
			//부서 추가
			batchDAO.deleteMisDeptAll(cmap);
			batchMysqlService.loadDataFile("rfid_mis_dept", misDeptList);
			batchDAO.deleteDept(cmap);
			batchDAO.updateDept(cmap);
			batchDAO.insertDept(cmap);
		} else {
			System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "[ERROR] MIS조회 정보가 없습니다.");
		}*/

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncDept() - end");
	}

	/** 품목 동기화 */
	public void syncItem(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncItem() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		batchDAO.deleteMisItemAll(cmap);
		batchDAO.insertMisItemAll(cmap);
		batchDAO.deleteItem(cmap);
		batchDAO.mergeItem(cmap);

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncItem() - end");
	}

	/** 자산 동기화 */
	public void syncAsset(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncAsset() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		batchDAO.deleteMisAssetAll(cmap);
		batchDAO.insertMisAssetAll(cmap);
		batchDAO.mergeMisAsset(cmap); // 묶음자산 처리
		batchDAO.deleteAsset(cmap);
		batchDAO.mergeAsset(cmap);

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncAsset() - end");
	}

	/** 거래처(판매업체) 동기화 */
	public void syncCust(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncCust() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		CommonList misCustList = misDAO.getMisCustList(cmap);
		if (misCustList.size() > 0) {
			//거래처 추가
			batchDAO.deleteMisCustAll(cmap);
			batchMysqlService.loadDataFile("rfid_mis_cust", misCustList);
			batchDAO.deleteCust(cmap);
			batchDAO.updateCust(cmap);
			batchDAO.insertCust(cmap);
		} else {
			System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "[ERROR] MIS조회 정보가 없습니다.");
		}

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncCust() - end");
	}

	/** 계약정보 동기화 */
	public void syncContr(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncContr() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		CommonList misContrList = misDAO.getMisContrList(cmap);
		//사용자 추가
		batchDAO.deleteMisContrAll(cmap);
		batchMysqlService.loadDataFile("rfid_mis_contr", misContrList);
		//batchDAO.deleteContr(cmap); //전체 데이터를 끌어오는 것이 아니기 때문에 삭제는 하면 안된다.
		batchDAO.updateContr(cmap);
		batchDAO.insertContr(cmap);

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncContr() - end");
	}

	/** 계약상세정보 동기화 */
	public void syncContrdtl(CommonMap cmap) throws Exception {
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncContrdtl() - start");
		cmap.put("pageIdx", "1");
		cmap.put("pageSize", "999999");

		CommonList misContrdtlList = misDAO.getMisContrdtlList(cmap);
		//사용자 추가
		batchDAO.deleteMisContrdtlAll(cmap);
		batchMysqlService.loadDataFile("rfid_mis_contrdtl", misContrdtlList);
		//batchDAO.deleteContrdtl(cmap); //전체 데이터를 끌어오는 것이 아니기 때문에 삭제는 하면 안된다.
		batchDAO.updateContrdtl(cmap);
		batchDAO.insertContrdtl(cmap);

		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "BatchService.syncContrdtl() - end");
	}

}
