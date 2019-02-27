package boassoft.batch;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import boassoft.service.BatchService;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

public class BatchShellScriptJob2 implements Job {

	/** batchService */
	private BatchService batchService;


	/** setBatchService */
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}
	
	/**
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("egovBatchExecute() : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));

		setBatchService((BatchService)jobContext.getJobDetail().getJobDataMap().get("batchService"));

		try {
			CommonMap cmap = new CommonMap();

//			//승인 결과 동기화
//	    	batchService.syncMisDoc(cmap);
//	    	batchService.syncMisDocAppr(cmap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// jobContext에 결과값을 저장한다.
		jobContext.setResult(true);
		System.out.println("egovBatchExecute() : OK " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
	}

}
