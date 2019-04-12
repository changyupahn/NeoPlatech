package boassoft.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import boassoft.service.BatchMssqlService;
import boassoft.service.BatchMysqlInterfaceService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

public class SubPartWOSendListCngScriptJob implements Job{

	/** batchMssqlService */
	private BatchMssqlService batchMssqlService;

	/** setBatchService */
	public void setBatchMssqlService(BatchMssqlService batchMssqlService) {
		this.batchMssqlService = batchMssqlService;
	}
	
	/** batchMysqlInterfaceService */
	private BatchMysqlInterfaceService batchMysqlInterfaceService;

	/** setBatchService */
	public void setBatchMysqlInterfaceService(
			BatchMysqlInterfaceService batchMysqlInterfaceService) {
		this.batchMysqlInterfaceService = batchMysqlInterfaceService;
	}
	
	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("egovBatchExecute() : "
				+ DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		
		setBatchMssqlService((BatchMssqlService) jobContext.getJobDetail()
				.getJobDataMap().get("batchMssqlService"));
		setBatchMysqlInterfaceService((BatchMysqlInterfaceService) jobContext
				.getJobDetail().getJobDataMap()
				.get("batchMysqlInterfaceService"));
		
		try {
			
			CommonMap cmap = new CommonMap();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1

			String strYesterDay = sdf.format(c1.getTime()); // String으로 저장

			Calendar c2 = Calendar.getInstance();
			String strToday = sdf.format(c2.getTime());

			cmap.put("startDate", strYesterDay);

			cmap.put("endDate", strToday);

			CommonList clist = new CommonList();
			
			clist =  batchMysqlInterfaceService.getSubPartWoSendListOdOnly(cmap);
			
			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					
					CommonMap gmap = clist.getMap(i);
					
					/*System.out.println(" OD_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("odId"));
					System.out.println(" DEMAND_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("demandId"));
					System.out.println(" LGE_DATE " + " : " + i + " + 번째 " + " : " + gmap.getString("lgeDate"));
					System.out.println(" NEO_DATE " + " : " + i + " + 번째 " + " : " + gmap.getString("neoDate"));
					System.out.println(" GAP_DAY " + " : " + i + " + 번째 " + " : " + gmap.getString("gayDay"));
					System.out.println(" LG_LINE " + " : " + i + " + 번째 " + " : " + gmap.getString("lgLine"));
					System.out.println(" TOOL " + " : " + i + " + 번째 " + " : " + gmap.getString("tool"));
					System.out.println(" M_PART_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("mPartNo"));
					System.out.println(" PLAN_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("planQty"));
					System.out.println(" LGM_PART_NAME " + " : " + i + " + 번째 " + " : " + gmap.getString("lgePartName"));
					
					System.out.println(" SUB_PART_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("subPartNo"));
					System.out.println(" SUB_PART_NAME " + " : " + i + " + 번째 " + " : " + gmap.getString("sbuPartName"));
					System.out.println(" BOM_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("bomQty"));
					System.out.println(" SUM_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("sumQty"));
					
					System.out.println(" SUM_QTY_CNG " + " : " + i + " + 번째 " + " : " + gmap.getString("sumQthCng"));
					System.out.println(" UNIT " + " : " + i + " + 번째 " + " : " + gmap.getString("unit"));
					System.out.println(" VENDOR " + " : " + i + " + 번째 " + " : " + gmap.getString("vendor"));
					System.out.println(" OSP " + " : " + i + " + 번째 " + " : " + gmap.getString("osp"));
					System.out.println(" OUT_PLACE " + " : " + i + " + 번째 " + " : " + gmap.getString("outPlace"));
					System.out.println(" CHK_DAY " + " : " + i + " + 번째 " + " : " + gmap.getString("chkDay"));
					System.out.println(" IN_DATE " + " : " + i + " + 번째 " + " : " + gmap.getString("inDate"));
					System.out.println(" QTY_ON_HAND " + " : " + i + " + 번째 " + " : " + gmap.getString("qtyOnHand"));
					System.out.println(" PRE_QTY_ON_HAND " + " : " + i + " + 번째 " + " : " + gmap.getString("preQtyOnHand"));
					System.out.println(" QTYINVOICED " + " : " + i + " + 번째 " + " : " + gmap.getString("qtyinvoiced"));*/
					
					if(("".equals(gmap.getString("qtyOnHand")) ||("0".equals(gmap.getString("qtyOnHand"))))){
						int qtyOnHand = gmap.getInt("preQtyOnHand") - gmap.getInt("qtyinvoiced");
						System.out.println(" 1111 qtyOnHand" + qtyOnHand);
						gmap.put("qtyOnHand",qtyOnHand);
					}
					batchMysqlInterfaceService.updateSubPartOdOnlyQtyOnHand(gmap);
					//System.out.println(" gmap insertSubPartWoSendListOdOnlyMStock " + " : " + i + " + 번째 " + " : " + " START " );
					batchMysqlInterfaceService.insertSubPartWoSendListOdOnlyMStock(gmap);
					//System.out.println(" gmap insertSubPartWoSendListOdOnlyMStock " + " : " + i + " + 번째 " + " : " + " END " );
					//System.out.println(" gmap insertSubPartWoSendListOdOnlyMTransaction " + " : " + i + " + 번째 " + " : " + " START " );
					batchMysqlInterfaceService.insertSubPartWoSendListOdOnlyMTransaction(gmap);
					//System.out.println(" gmap insertSubPartWoSendListOdOnlyMTransaction " + " : " + i + " + 번째 " + " : " + " END " );
					//System.out.println(" gmap insertSubPartWoSendListOdOnlyInputQty " + " : " + i + " + 번째 " + " : " + " START " );
					batchMssqlService.insertSubPartWoSendListOdOnlyInputQty(gmap);
					//System.out.println(" gmap insertSubPartWoSendListOdOnlyInputQty " + " : " + i + " + 번째 " + " : " + " END " );
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		
		// jobContext에 결과값을 저장한다.
		jobContext.setResult(true);
		System.out.println("egovBatchExecute() : OK "
				+ DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		
	}

}
