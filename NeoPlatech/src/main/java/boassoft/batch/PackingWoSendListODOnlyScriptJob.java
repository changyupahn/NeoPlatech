package boassoft.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import boassoft.service.BatchMssqlService;
import boassoft.service.BatchMysqlInterfaceService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

public class PackingWoSendListODOnlyScriptJob implements Job {

	
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
	
	/**
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("egovBatchExecute() : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		
		//setBatchService((BatchService)jobContext.getJobDetail().getJobDataMap().get("batchService"));
		setBatchMssqlService((BatchMssqlService)jobContext.getJobDetail().getJobDataMap().get("batchMssqlService"));
		setBatchMysqlInterfaceService((BatchMysqlInterfaceService)jobContext.getJobDetail().getJobDataMap().get("batchMysqlInterfaceService"));
		
		try {
			CommonMap cmap = new CommonMap();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    Calendar c1 = Calendar.getInstance();
			String strToday = sdf.format(c1.getTime());
		
			cmap.put("enterDate", strToday);
			
			CommonList clist = new CommonList();
			
            batchMysqlInterfaceService.deletePackingWoSendListODOnlyList(cmap);
			
			clist = batchMssqlService.getPackingWoSendListODOnlyList(cmap);
			System.out.println("clist.size()  : " + clist.size());
			if(!clist.isEmpty() && clist.size() > 0){
				for(int i = 0; i < clist.size() ; i++){
					 CommonMap gmap = clist.getMap(i);
					
					 
					 batchMysqlInterfaceService.insertPackingWoSendListODOnlyList(gmap);
				}
			}	
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		// jobContext에 결과값을 저장한다.
		jobContext.setResult(true);
		System.out.println("egovBatchExecute() : OK " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
	}

}