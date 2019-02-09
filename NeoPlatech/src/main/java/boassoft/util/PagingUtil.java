package boassoft.util;

public class PagingUtil {

	/**
	 * 페이징 데이터 추출A 폼 이용
	 * @param list
	 * @param PageUrl
	 * @param AllParam
	 * @return
	 */
	public static String PagingDataA(CommonList list, String formName, String pageUrl)
	{
		int pageIdx = list.pageIdx;
		int totalCount = list.totalRow;
		int pageSize = list.pageSize;
		if( totalCount > 0 ){
			int pageblock = (totalCount / pageSize) + 1;
			int blockSu = 10;

			// 버튼
			String btn_pre = "/images/btn/btn_pre.gif";
			String btn_next = "/images/btn/btn_next.gif";
			String pager = "";

			if(totalCount % pageSize == 0)
				pageblock = pageblock - 1;

			int blockPage = ( (pageIdx-1) / blockSu ) * blockSu + 1;

			//Left
			if(blockPage != 1)
				pager = "<a class=\"direction\" href=\"javascript:commonPagingPost('"+formName+"','"+(blockPage - 1)+"','"+pageSize+"','"+pageUrl+"');\"><img src=\""+ btn_pre +"\" alt=\"이전\" />이전</a>\n";

			//Center
			int i = 1;
			String frstClass = "";
			while( i <= blockSu && blockPage <= pageblock )
			{
				if(blockPage == pageIdx)
					pager = pager + "<strong>" + blockPage + "</strong>\n";
				else
					pager = pager + "<a "+frstClass+" href=\"javascript:commonPagingPost('"+formName+"','"+blockPage+"','"+pageSize+"','"+pageUrl+"');\">" + blockPage + "</a>\n";

				blockPage = blockPage + 1;
				i = i + 1;
			}
			//Right
			if(blockPage <= pageblock)
				pager = pager + "<a class=\"direction\" href=\"javascript:commonPagingPost('"+formName+"','"+blockPage+"','"+pageSize+"','"+pageUrl+"');\">다음<img src=\"" + btn_next +"\" alt=\"다음\" /></a>\n";

			String formStr = PagingScriptString();
			pager = pager + formStr;

			return pager;
		} else {
			return "";
		}
	}

	/**
	 * 페이징 데이터 추출B 폼 이용
	 * @param list
	 * @param PageUrl
	 * @param AllParam
	 * @return
	 */
	public static String PagingDataB(CommonList list, String formName, String pageUrl)
	{
		int pageIdx = list.pageIdx;
		int totalCount = list.totalRow;
		int pageSize = list.pageSize;
		if( totalCount > 0 ){
			int pageblock = (totalCount / pageSize) + 1;
			int blockSu = 10;

			// 버튼
			String btn_pre10 = "/images/btn/btn_pg_first.gif";
			String btn_pre = "/images/btn/btn_pg_pre.gif";
			String btn_next = "/images/btn/btn_pg_next.gif";
			String btn_next10 = "/images/btn/btn_pg_end.gif";
			String pager = "";

			if(totalCount % pageSize == 0)
				pageblock = pageblock - 1;

			int blockPage = ( (pageIdx-1) / blockSu ) * blockSu + 1;

			//Left
			if(blockPage != 1){
				pager = pager + "<a class=\"pre_end\" href=\"javascript:commonPagingPost('"+formName+"','1','"+pageSize+"','"+pageUrl+"');\"><img src=\""+ btn_pre10 +"\" alt=\"처음\" /></a>\n";
				pager = pager + "<a class=\"pre\" href=\"javascript:commonPagingPost('"+formName+"','"+(blockPage - 1)+"','"+pageSize+"','"+pageUrl+"');\"><img src=\""+ btn_pre +"\" alt=\"이전\" /></a>\n";
			}

			//Center
			int i = 1;
			String frstClass = "";
			while( i <= blockSu && blockPage <= pageblock )
			{
				if( i == 1 )
					frstClass = " frst";
				else
					frstClass = "";

				if(blockPage == pageIdx)
					pager = pager + "<a class=\"one\">" + blockPage + "</a>\n";
				else
					pager = pager + "<a class=\"no"+ frstClass +"\" href=\"javascript:commonPagingPost('"+formName+"','"+blockPage+"','"+pageSize+"','"+pageUrl+"');\">" + blockPage + "</a>\n";

				blockPage = blockPage + 1;
				i = i + 1;
			}
			//Right
			if(blockPage <= pageblock){
				pager = pager + "<a class=\"next\" href=\"javascript:commonPagingPost('"+formName+"','"+blockPage+"','"+pageSize+"','"+pageUrl+"');\"><img src=\"" + btn_next +"\" alt=\"다음\" /></a>\n";
				pager = pager + "<a class=\"next_end\" href=\"javascript:commonPagingPost('"+formName+"','"+pageblock+"','"+pageSize+"','"+pageUrl+"');\"><img src=\"" + btn_next10 +"\" alt=\"마지막\" /></a>\n";
			}

			String formStr = PagingScriptString();
			pager = pager + formStr;

			return pager;
		} else {
			return "";
		}
	}

	/**
	 * 페이징 데이터 추출B 폼 이용
	 * @param list
	 * @param PageUrl
	 * @param AllParam
	 * @return
	 */
	public static String PagingDataC(int pageIdx, int totalCount, int pageSize, String formName, String pageUrl)
	{
		if( totalCount > 0 ){
			int pageblock = (totalCount / pageSize) + 1;
			int blockSu = 10;

			// 버튼
			String btn_pre10 = "/images/btn/btn_pg_first.gif";
			String btn_pre = "/images/btn/btn_pg_pre.gif";
			String btn_next = "/images/btn/btn_pg_next.gif";
			String btn_next10 = "/images/btn/btn_pg_end.gif";
			String pager = "";

			if(totalCount % pageSize == 0)
				pageblock = pageblock - 1;

			int blockPage = ( (pageIdx-1) / blockSu ) * blockSu + 1;

			//Left
			if(blockPage != 1){
				pager = pager + "<a class=\"pre_end\" href=\"javascript:fnGridReload('1');\"><img src=\""+ btn_pre10 +"\" alt=\"처음\" /></a>\n";
				pager = pager + "<a class=\"pre\" href=\"javascript:fnGridReload('"+(blockPage - 1)+"');\"><img src=\""+ btn_pre +"\" alt=\"이전\" /></a>\n";
			}

			//Center
			int i = 1;
			String frstClass = "";
			while( i <= blockSu && blockPage <= pageblock )
			{
				if( i == 1 )
					frstClass = " frst";
				else
					frstClass = "";

				if(blockPage == pageIdx)
					pager = pager + "<a class=\"one\">" + blockPage + "</a>\n";
				else
					pager = pager + "<a class=\"no"+ frstClass +"\" href=\"javascript:fnGridReload('"+blockPage+"');\">" + blockPage + "</a>\n";

				blockPage = blockPage + 1;
				i = i + 1;
			}
			//Right
			if(blockPage <= pageblock){
				pager = pager + "<a class=\"next\" href=\"javascript:fnGridReload('"+blockPage+"');\"><img src=\"" + btn_next +"\" alt=\"다음\" /></a>\n";
				pager = pager + "<a class=\"next_end\" href=\"javascript:fnGridReload('"+pageblock+"');\"><img src=\"" + btn_next10 +"\" alt=\"마지막\" /></a>\n";
			}

			String formStr = PagingScriptString();
			pager = pager + formStr;

			return pager;
		} else {
			return "";
		}
	}

	public static String PagingDataAjax(CommonList list, String scriptName)
	{
		int pageIdx = list.pageIdx;
		int totalCount = list.totalRow;
		int pageSize = list.pageSize;
		if( totalCount > 0 ){
			int pageblock = (totalCount / pageSize) + 1;
			int blockSu = 10;

			// 버튼
			String btn_pre = "/HTML/images/btn_pre.gif";
			String btn_next = "/HTML/images/btn_next.gif";
			String pager = "";

			if(totalCount % pageSize == 0)
				pageblock = pageblock - 1;

			int blockPage = ( (pageIdx-1) / blockSu ) * blockSu + 1;

			//Left
			if(blockPage != 1)
				pager = "<a class=\"direction\" href=\"javascript:commonPagingAjax('"+(blockPage - 1)+"','"+pageSize+"','"+scriptName+"');\"><img src=\""+ btn_pre +"\" alt=\"이전\" />이전</a>\n";

			//Center
			int i = 1;
			String frstClass = "";
			while( i <= blockSu && blockPage <= pageblock )
			{
				if(blockPage == pageIdx)
					pager = pager + "<strong>" + blockPage + "</strong>\n";
				else
					pager = pager + "<a "+frstClass+" href=\"javascript:commonPagingAjax('"+blockPage+"','"+pageSize+"','"+scriptName+"');\">" + blockPage + "</a>\n";

				blockPage = blockPage + 1;
				i = i + 1;
			}
			//Right
			if(blockPage <= pageblock)
				pager = pager + "<a class=\"direction\" href=\"javascript:commonPagingAjax('"+blockPage+"','"+pageSize+"','"+scriptName+"');\">다음<img src=\"" + btn_next +"\" alt=\"다음\" /></a>\n";

			String formStr = PagingScriptAjax();
			pager = pager + formStr;

			return pager;
		} else {
			return "";
		}
	}

	/**
	 * 페이징 스크립트 생성
	 * @return
	 */
	public static String PagingScriptString(){
		StringBuffer sb = new StringBuffer();
		sb.append("<script> \n");
		sb.append("function commonPagingPost( formName, pageIdx, pageSize, pageUrl ) { \n");
		sb.append("var f = eval('document.' + formName) \n");
		sb.append("f.pageIdx.value = pageIdx; \n");
		sb.append("f.pageSize.value = pageSize; \n");
		sb.append("f.action = pageUrl; \n");
		sb.append("f.target = \"_self\"; \n");
		sb.append("f.submit(); \n");
		sb.append("} \n");
		sb.append("</script>");

		return sb.toString();
	}

	/**
	 * 페이징 스크립트 생성( ajax )
	 * @return
	 */
	public static String PagingScriptAjax(){
		StringBuffer sb = new StringBuffer();
		sb.append("<script> \n");
		sb.append("function commonPagingAjax( pageIdx, pageSize, scriptName ) { \n");
		sb.append("document.getElementById('pageIdx').value = pageIdx \n");
		sb.append("document.getElementById('pageSize').value = pageSize \n");
		sb.append("eval(scriptName) \n");
		sb.append("} \n");
		sb.append("</script>");

		return sb.toString();
	}

	/**
	 * 필요없는 파라미터를 제거하여 리턴
	 * @param strTemp
	 * @param strValue
	 * @return
	 */
	public static String getParameterDelete(String strTemp, String strValue)
	{
		try{
		//strTemp = strTemp.startsWith("?") ? strTemp.substring(1) : strTemp;
		strTemp = strTemp.replaceAll("^\\?","&");
		String strTemp2[] = strTemp.split("&");
		String result ="";

		for(int i=0; i<strTemp2.length;i++) {
			if(strTemp2[i].indexOf(strValue + "=") == -1){
				result += strTemp2[i] + "&";
			}
		}
		return result.substring(0, result.length()-1);
		}catch(StringIndexOutOfBoundsException ex) { return ""; }
	}

	/**
	 * 페이징 처리시 Line 번호 호출
	 * @param strTemp
	 * @param strValue
	 * @return
	 */
	public static int getPagingFristNo(int totalRow, int pageIdx, int pageSize)
	{
		return (totalRow - (pageSize * (pageIdx - 1)));
	}

	public static CommonList setPagingRnum(CommonList resultList, CommonMap cmap) {
		CommonList tempList = new CommonList();

		try {
			//리스트에 순번 붙이기
	    	int totalRow = resultList.totalRow;
	    	for (int i=0; i<resultList.size(); i++) {
	    		CommonMap result = resultList.getMap(i);
	    		int k = i;
	    		k += cmap.getInt("pageSize") * (cmap.getInt("pageIdx", 1) - 1);
	    		result.put("rnum", (totalRow - k));
	    		tempList.add(result);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			tempList = resultList;
		}

    	return tempList;
	}

	/** ZEUS 월별작성현황 전용 */
	public static CommonList setZeusPagingRnum(CommonList resultList, CommonMap cmap) {
		CommonList tempList = new CommonList();

		try {
			//리스트에 순번 붙이기
	    	int totalRow = resultList.totalRow;
	    	for (int i=0; i<resultList.size(); i++) {
	    		CommonMap result = resultList.getMap(i);
	    		int k = i / 2 * 2;
	    		k += cmap.getInt("pageSize") * (cmap.getInt("pageIdx", 1) - 1);
	    		result.put("rnum", Math.floor((totalRow - k) / 2));
	    		tempList.add(result);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			tempList = resultList;
		}

    	return tempList;
	}
}
