<%@ page pageEncoding="UTF-8" %>


<script type="text/javascript" src="/common/js/jquery-ui-1.8.16.custom.min.js?<%=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())%>"></script>

<style type="text/css">
.datepicker {
	width:80px;
	height:15px;
}
.datepicker2 {
	height:15px;
}
</style>

<script type="text/javascript">
function fnInitCalc(){
	$(".datepicker").datepicker({
		dayNamesMin : ["일","월","화","수","목","금","토"]
		, monthNames : ["년 1월","년 2월","년 3월","년 4월","년 5월","년 6월","년 7월","년 8월","년 9월","년 10월","년 11월","년 12월"]
		, monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
		, showOn : "both"
		, duration : 200
		, dateFormat : "yy-mm-dd"
		, buttonImage : '/common/jqueryui/calendar.gif'
		, buttonText : '달력'
		, buttonImageOnly : true
		, showMonthAfterYear : true
		, yearRange: "1950:+2"
		, changeYear : true
		, changeMonth : true
		, currentText : '오늘'
		, closeText : '닫기'
		, showButtonPanel : true
	});

	$(".datepicker2").datepicker({
		dayNamesMin : ["일","월","화","수","목","금","토"]
		, monthNames : ["년 1월","년 2월","년 3월","년 4월","년 5월","년 6월","년 7월","년 8월","년 9월","년 10월","년 11월","년 12월"]
		, monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
		, duration : 200
		, dateFormat : "yy-mm-dd"
		, showMonthAfterYear : true
		, yearRange: "1950:+2"
		, changeYear : true
		, changeMonth : true
		, currentText : '오늘'
		, closeText : '닫기'
	});

	$(".datepicker3").datepicker({
		dayNamesMin : ["일","월","화","수","목","금","토"]
		, monthNames : ["년 1월","년 2월","년 3월","년 4월","년 5월","년 6월","년 7월","년 8월","년 9월","년 10월","년 11월","년 12월"]
		, monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
		//, showOn : "both"
		, duration : 200
		, dateFormat : "yy-mm-dd"
		//, buttonImage : false
		//, buttonText : '달력'
		//, buttonImageOnly : false
		, showMonthAfterYear : true
		, yearRange: "1950:+2"
		, changeYear : true
		, changeMonth : true
		, currentText : '오늘'
		, closeText : '닫기'
		//, showButtonPanel : true
	});
}
function fnInitCalcToShow(nm) {

	$('input[name="'+ nm +'"]').datepicker({
		dayNamesMin : ["일","월","화","수","목","금","토"]
		, monthNames : ["년 1월","년 2월","년 3월","년 4월","년 5월","년 6월","년 7월","년 8월","년 9월","년 10월","년 11월","년 12월"]
		, monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
		//, showOn : "both"
		, duration : 200
		, dateFormat : "yy-mm-dd"
		//, buttonImage : false
		//, buttonText : '달력'
		//, buttonImageOnly : false
		, showMonthAfterYear : true
		, yearRange: "1950:+2"
		, changeYear : true
		, changeMonth : true
		, currentText : '오늘'
		, closeText : '닫기'
		, onSelect : function(dateText, inst) {
			//alert(this);
			//$('input[name="'+ $(ths).attr("nm") +'"]').val(dateText);
			//$(this).val(dateText);
		}
		//, showButtonPanel : true
	}).datepicker('show');
}
$(document).ready(function() {
	fnInitCalc();
});
</script>