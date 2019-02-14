<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="/common/css/jqGrid/ui.jqgrid.css?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" />
<script type="text/javascript" src="/common/js/jqGrid/grid.locale-en.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>
<script type="text/javascript" src="/common/js/jqGrid/jquery.jqGrid.src.4.3.1.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" ></script>
<script type="text/javascript" src="/common/js/jqGrid/jquery.jqGrid.custom.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" ></script>
<script type="text/javascript" src="/common/js/jqGrid/jquery.jqGrid.init.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" ></script>
<script type="text/javascript" src="/common/js/jqGrid/json2.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" ></script>
<script type="text/javascript" src="/common/js/jquery.json.min.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>" ></script>

<script>
/* 크롬멀티 헤더 깨짐 방지 
 * trgetList : 타겟 리스트 명
 */
function nonBreakMultiHeader(trgetList)
{
	if (document.getElementById(trgetList)) {
		// 크롬멀티 헤더 깨짐방지
		var i = 0;
		if($.browser.webkit || $.browser.safari) {
	
			var colModel = $("#"+trgetList).getGridParam("colModel");
			var width = "";
			for(i =0; i < colModel.length ; i++){
	
				width = colModel[i].width + ""; 
				width = width.indexOf("px") > -1 ? width : width + "px"; 
				$("table[aria-labelledby*="+trgetList+"] > thead > tr[class=jqg-first-row-header] > th:eq("+ (i) + ") ").width(width);
				//$("table > thead > tr[class=jqg-first-row-header] > th:eq("+ (i) + ") ").width(width);
			}
		}
		$(".ui-first-th-ltr").attr("class","ui-state-default ui-th-column ui-th-ltr");
	}
}
</script>