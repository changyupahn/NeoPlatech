package boassoft.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import boassoft.service.BatchMssqlService;
import boassoft.service.BatchMysqlInterfaceService;
import boassoft.service.BatchService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

public class PackingWoSendListScriptJob implements Job {

	/** batchService */
	private BatchService batchService;

	/** setBatchService */
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

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

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		System.out.println("egovBatchExecute() : "
				+ DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));

		// setBatchService((BatchService)jobContext.getJobDetail().getJobDataMap().get("batchService"));
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
			//System.out.println(" row startDate  : "+ strYesterDay + " ");
			cmap.put("endDate", strToday);
			//System.out.println(" row endDate  : "+ strToday + " ");
		
			
					
			CommonList clist = new CommonList();
			
			batchMysqlInterfaceService.deletePackingWoSendList(cmap);
			
			clist = batchMssqlService.getPackingWoSendList(cmap);
			
			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					CommonMap gmap = clist.getMap(i);
					
					/*System.out.println(" row odId " + i + " 번째  " + " : "+ gmap.getString("odId") + " ");
					System.out.println(" row demandId " + i + " 번째  " + " : "+ gmap.getString("demandId") + " ");
					System.out.println(" row lgPartNo " + i + " 번째  " + " : "+ gmap.getString("lgPartNo") + " ");
					System.out.println(" row lgOdQty " + i + " 번째  " + " : "+ gmap.getString("lgOdQty") + " ");
					System.out.println(" row toolName " + i + " 번째  " + " : "+ gmap.getString("toolName") + " ");
					System.out.println(" row drawNo " + i + " 번째  " + " : "+ gmap.getString("drawNo") + " ");
					System.out.println(" row vendor " + i + " 번째  " + " : "+ gmap.getString("vendor") + " ");
					System.out.println(" row subPartName " + i + " 번째  " + " : "+ gmap.getString("subPartName") + " ");
					System.out.println(" row partNumber " + i + " 번째  " + " : "+ gmap.getString("partNumber") + " ");
					System.out.println(" row desc " + i + " 번째  " + " : "+ gmap.getString("desc") + " ");
					System.out.println(" row resultQty " + i + " 번째  " + " : "+ gmap.getString("resultQty") + " ");
					System.out.println(" row rowNum " + i + " 번째  " + " : "+ gmap.getString("rowNum") + " ");
					System.out.println(" row calcuTrue " + i + " 번째  " + " : "+ gmap.getString("calcuTrue") + " ");
					System.out.println(" row odQtyId " + i + " 번째  " + " : "+ gmap.getString("odQtyId") + " ");
					System.out.println(" row neoOdTime " + i + " 번째  " + " : "+ gmap.getString("neoOdTime") + " ");
					System.out.println(" row neoOdDay " + i + " 번째  " + " : "+ gmap.getString("neoOdDay") + " ");
					System.out.println(" row neoOdQty " + i + " 번째  " + " : "+ gmap.getString("neoOdQty") + " ");
					System.out.println(" row finalVendor " + i + " 번째  " + " : "+ gmap.getString("finalVendor") + " "); */
					
					batchMysqlInterfaceService.insertPackingWoSendList(gmap);
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

