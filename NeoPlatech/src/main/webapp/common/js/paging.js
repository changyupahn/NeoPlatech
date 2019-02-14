
var pagingUtil = {
}

pagingUtil.setHTML = function(pageIdx, pageSize, totalCount, targetObj) {
	var pager = "";
	
	pageIdx = parseInt(pageIdx);
	pageSize = parseInt(pageSize);
	totalCount = parseInt(totalCount);
	
	if (totalCount > 0) {
		var pageblock = parseInt(totalCount / pageSize) + 1;
		var blockSu = 10;
		
		// 버튼
		var btn_pre10 = "/images/btn/btn_pg_first.gif";
		var btn_pre = "/images/btn/btn_pg_pre.gif";
		var btn_next = "/images/btn/btn_pg_next.gif";
		var btn_next10 = "/images/btn/btn_pg_end.gif";
		
		if (totalCount % pageSize == 0)
			pageblock = pageblock - 1;
	
		var blockPage = parseInt((pageIdx-1) / blockSu) * blockSu + 1;
		
		//Left
		if(blockPage != 1){
			pager = pager + "<a class=\"pre_end\" href=\"javascript:fnGridReload('1');\"><img src=\""+ btn_pre10 +"\" alt=\"처음\" /></a>\n";
			pager = pager + "<a class=\"pre\" href=\"javascript:fnGridReload('"+(blockPage - 1)+"');\"><img src=\""+ btn_pre +"\" alt=\"이전\" /></a>\n";
		}
		
		//Center
		var i = 1;
		var frstClass = "";
		while (i <= blockSu && blockPage <= pageblock)
		{
			if (i == 1)
				frstClass = " frst";
			else
				frstClass = "";
			
			if (blockPage == pageIdx)
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
	}
	
	$('#'+targetObj).html(pager);	
}