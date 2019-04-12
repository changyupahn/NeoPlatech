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

public class PackingWoSendListAllCngScriptJob  implements Job {

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
			//System.out.println(" row startDate  : "+ strYesterDay + " ");
			cmap.put("endDate", strToday);
			//System.out.println(" row endDate  : "+ strToday + " ");
			
			CommonList clist = new CommonList();
			
			clist =  batchMysqlInterfaceService.getPackingWoSendListAllCng(cmap);
			
			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					
					CommonMap gmap = clist.getMap(i);
					
					/*System.out.println(" OD_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("odId"));
					System.out.println(" OD_QTY_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("odQtyId"));
					System.out.println(" PKG_PO_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("pkgPoNo"));
					System.out.println(" DEMAND_ID " + " : " + i + " + 번째 " + " : " + gmap.getString("demandId"));
					System.out.println(" TOOL_NAME " + " : " + i + " + 번째 " + " : " + gmap.getString("toolName"));
					System.out.println(" LG_PART_NO " + " : " + i + " + 번째 " + " : " + gmap.getString("lgPartNo"));
					System.out.println(" LG_OD_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("lgOdQty"));
					System.out.println(" LINE " + " : " + i + " + 번째 " + " : " + gmap.getString("line"));
					System.out.println(" FINAL_VENDOR " + " : " + i + " + 번째 " + " : " + gmap.getString("finalVendor"));
					
					System.out.println(" CLASS " + " : " + i + " + 번째 " + " : " + gmap.getString("class"));
					System.out.println(" PART_NUMBER " + " : " + i + " + 번째 " + " : " + gmap.getString("partNumber"));
					System.out.println(" DESC " + " : " + i + " + 번째 " + " : " + gmap.getString("desc"));
					System.out.println(" RESULT_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("resultQty"));
					
					System.out.println(" NEO_OD_TIME " + " : " + i + " + 번째 " + " : " + gmap.getString("neoOdTime"));
					System.out.println(" NEO_OD_DAY " + " : " + i + " + 번째 " + " : " + gmap.getString("neoOdDay"));
					System.out.println(" NEO_OD_QTY " + " : " + i + " + 번째 " + " : " + gmap.getString("neoOdQty"));
					System.out.println(" QTY_ON_HAND " + " : " + i + " + 번째 " + " : " + gmap.getString("qtyOnHand"));
					
					System.out.println(" PRE_QTY_ON_HAND " + " : " + i + " + 번째 " + " : " + gmap.getString("preQtyOnHand"));
					System.out.println(" QTYINVOICED " + " : " + i + " + 번째 " + " : " + gmap.getString("qtyinvoiced"));
*/				
					if(("".equals(gmap.getString("qtyOnHand")) ||("0".equals(gmap.getString("qtyOnHand"))))){
						int qtyOnHand = gmap.getInt("preQtyOnHand") - gmap.getInt("qtyinvoiced");
						System.out.println(" 1111 qtyOnHand" + qtyOnHand);
						gmap.put("qtyOnHand",qtyOnHand);
					}
					batchMysqlInterfaceService.updatePackingWoSendListQtyOnHand(gmap);
					//System.out.println(" gmap insertPackingWoSendListAllCngMStock " + " : " + i + " + 번째 " + " : " + " START " );
					batchMysqlInterfaceService.insertPackingWoSendListAllCngMStock(gmap);
					//System.out.println(" gmap insertPackingWoSendListAllCngMStock " + " : " + i + " + 번째 " + " : " + " END " );
					//System.out.println(" gmap insertPackingWoSendListAllCngMTransaction " + " : " + i + " + 번째 " + " : " + " START " );
					batchMysqlInterfaceService.insertPackingWoSendListAllCngMTransaction(gmap);
					//System.out.println(" gmap insertPackingWoSendListAllCngMTransaction " + " : " + i + " + 번째 " + " : " + " END " );
					//System.out.println(" gmap insertPackingWoSendListAllCngInputQty " + " : " + i + " + 번째 " + " : " + " START " );
					batchMssqlService.insertPackingWoSendListAllCngInputQty(gmap);
					//System.out.println(" gmap insertPackingWoSendListAllCngInputQty " + " : " + i + " + 번째 " + " : " + " END " );
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
