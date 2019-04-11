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

public class SubPartWOSendListAllCngScriptJob implements Job {

	/** batchMssqlService */
	private BatchMssqlService batchMssqlService;

	/** setBatchService */
	public void setBatchMssqlService(BatchMssqlService batchMssqlService) {
		this.batchMssqlService = batchMssqlService;
	}
	
	/** batchMysqlInterfaceService */
	private BatchMysqlInterfaceService batchMysqlInterfaceService;
	
	/** setBatchService */
	public void setBatchMysqlInterfaceService(BatchMysqlInterfaceService batchMysqlInterfaceService) {
		this.batchMysqlInterfaceService = batchMysqlInterfaceService;
	}

	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
		System.out.println("egovBatchExecute() : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
	
		setBatchMssqlService((BatchMssqlService)jobContext.getJobDetail().getJobDataMap().get("batchMssqlService"));
		setBatchMysqlInterfaceService((BatchMysqlInterfaceService)jobContext.getJobDetail().getJobDataMap().get("batchMysqlInterfaceService"));
	
		try {
			CommonMap cmap = new CommonMap();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1	
			
            String strYesterDay = sdf.format(c1.getTime()); // String으로 저장
			
			Calendar c2 = Calendar.getInstance();
			String strToday = sdf.format(c2.getTime());

			cmap.put("startDate", strYesterDay);
			System.out.println(" row startDate  : "+ strYesterDay + " ");
			cmap.put("endDate", strToday);
			System.out.println(" row endDate  : "+ strToday + " ");
			
			CommonList clist = new CommonList();
			
			clist =  batchMysqlInterfaceService.getSubPartWoSendListAll(cmap);
			
			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					
					CommonMap gmap = clist.getMap(i);
					
					System.out.println(" PT_OD_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("ptOdId"));
					System.out.println(" SUB_PT_OD_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("subPtOdId"));
					System.out.println(" DEMAND_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("demandId"));
					System.out.println(" LG_PART_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("lgPartNo"));
					System.out.println(" LG_OD_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("lgOdQty"));
					System.out.println(" PROD_TYPE " + " : " + i + " + 번째 " + " : " + gmap.getString("prodType"));
					System.out.println(" NEO_LINE " + " : " + i + " + 번째 " + " : " + gmap.getString("neoLine"));
					System.out.println(" NEO_PLAN_KEY " + " : " + i + " + 번째 " + " : " + gmap.getString("neoPlanKey"));
					System.out.println(" SEND_TIME " + " : " + i + " + 번째 " + " : " + gmap.getString("sendTime"));
					System.out.println(" SENDING_PC_NAME " + " : " + i + " + 번째 " + " : " + gmap.getString("sendingPcName"));
					System.out.println(" GO_WITH " + " : " + i + " + 번째 " + " : " + gmap.getString("goWith"));
					System.out.println(" RE_PRINT " + " : " + i + " + 번째 " + " : " + gmap.getString("rePrint"));
					System.out.println(" SUB_PART_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("subPartNo"));
					System.out.println(" SUB_PART_DESC " + " : " + i + " + 번째 " + " : " + gmap.getString("subPartDesc"));
					System.out.println(" VENDOR " + " : " + i + " + 번째 " + " : " + gmap.getString("vendor"));
					System.out.println(" BOM_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("bomQty"));
					System.out.println(" SUB_UNIT " + " : " + i + " + 번째 " + " : " + gmap.getString("subUnit"));
					System.out.println(" SUB_SUM_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("subSumQty"));
					System.out.println(" DEMAND_FLOOR " + " : " + i + " + 번째 " + " : " + gmap.getString("demandFloor"));
					System.out.println(" OSP " + " : " + i + " + 번째 " + " : " + gmap.getString("osp"));
					System.out.println(" RSLTDATE " + " : " + i + " + 번째 " + " : " + gmap.getString("rsltdate"));
					System.out.println(" RSLTSTATE " + " : " + i + " + 번째 " + " : " + gmap.getString("rsltstate"));
					System.out.println(" RSLT_USER_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("rsltUserNo"));
					System.out.println(" READDATE " + " : " + i + " + 번째 " + " : " + gmap.getString("readdate"));
					System.out.println(" READSTATE " + " : " + i + " + 번째 " + " : " + gmap.getString("readstate"));
					System.out.println(" READ_USER_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("readUserNo"));
					System.out.println(" QTY_ON_HAND " + " : " + i + " + 번째 " + " : " + gmap.getString("qtyOnHand"));
					System.out.println(" PRE_QTY_ON_HAND " + " : " + i + " + 번째 " + " : " + gmap.getInt("preQtyOnHand"));
					System.out.println(" QTYINVOICED " + " : " + i + " + 번째 " + " : " + gmap.getInt("qtyinvoiced"));
					
					if(("".equals(gmap.getString("qtyOnHand")) ||("0".equals(gmap.getString("qtyOnHand"))))){
						int qtyOnHand = gmap.getInt("preQtyOnHand") - gmap.getInt("qtyinvoiced");
						System.out.println(" 1111 qtyOnHand" + qtyOnHand);
						gmap.put("qtyOnHand",qtyOnHand);
					}
					
					batchMysqlInterfaceService.updateSubPartQtyOnHand(gmap);
					
					System.out.println(" gmap insertSubPartWoSendListAllMStock " + " : " + i + " + 번째 " + " : " + " START " );
					batchMysqlInterfaceService.insertSubPartWoSendListAllMStock(gmap);
					System.out.println(" gmap insertSubPartWoSendListAllMStock " + " : " + i + " + 번째 " + " : " + gmap.getString("osp"));
					System.out.println(" gmap insertSubPartWoSendListAllMStock " + " : " + i + " + 번째 " + " : " + " END " );
					System.out.println(" gmap insertSubPartWoSendListAllMTransaction " + " : " + i + " + 번째 " + " : " + " START " );
					batchMysqlInterfaceService.insertSubPartWoSendListAllMTransaction(gmap);
					System.out.println(" gmap insertSubPartWoSendListAllMTransaction " + " : " + i + " + 번째 " + " : " + gmap.getString("osp"));
					System.out.println(" gmap insertSubPartWoSendListAllMTransaction " + " : " + i + " + 번째 " + " : " + " END " );
					System.out.println(" gmap insertSubPartWoSendListAllInputQty " + " : " + i + " + 번째 " + " : " + " START " );
					batchMssqlService.insertSubPartWoSendListAllInputQty(gmap);
					System.out.println(" gmap insertSubPartWoSendListAllInputQty " + " : " + i + " + 번째 " + " : " + gmap.getString("osp"));
					System.out.println(" gmap insertSubPartWoSendListAllInputQty " + " : " + i + " + 번째 " + " : " + " END " );
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
