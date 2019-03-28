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
			//System.out.println(" row startDate  : "+ strYesterDay + " ");
			cmap.put("endDate", strToday);
			//System.out.println(" row endDate  : "+ strToday + " ");
			
			CommonList clist = new CommonList();
			
			clist =  batchMysqlInterfaceService.getSubPartWoSendListAll(cmap);
			
			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					
					CommonMap gmap = clist.getMap(i);
					batchMysqlInterfaceService.insertSubPartWoSendListAllMStock(gmap);
					batchMysqlInterfaceService.insertSubPartWoSendListAllMTransaction(gmap);
					batchMssqlService.insertSubPartWoSendListAllInputQty(gmap);
					
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
