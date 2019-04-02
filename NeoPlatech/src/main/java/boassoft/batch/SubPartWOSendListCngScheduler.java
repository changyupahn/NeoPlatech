package boassoft.batch;

import java.util.HashMap;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import boassoft.service.BatchMssqlService;
import boassoft.service.BatchMysqlInterfaceService;
import boassoft.util.DateUtil;

public class SubPartWOSendListCngScheduler {

	/*
	 * Quartz 스케줄러
	 */
	private Scheduler sched;
	
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
	 * 배치스케줄러에 batchSchdul 파라미터를 이용하여 Job , Trigger를 Add 한다.
	 *
	 * @param batchSchdul  배치스케줄러에 등록할 스케줄정보
	 * @exception Exception Exception
	 */
	public void insertBatchSchdul() throws Exception {
		
		System.out.println("insertBatchSchdul : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		
		String batchId = "batch_013";	
		String batchCycle = "* /5 * * * *"; //매  5분마다 
		
		HashMap<String, Object> jobMap = new HashMap<String, Object>();
		
		jobMap.put("batchMssqlService", batchMssqlService);
		jobMap.put("batchMysqlInterfaceService", batchMysqlInterfaceService);
		
		 JobDataMap jobDataMap = new JobDataMap(jobMap);
		 
		// Job 만들기
		JobDetail jobDetail = new JobDetail();
		jobDetail.setName(batchId);
		jobDetail.setJobClass(SubPartWOSendListCngScriptJob.class);
		jobDetail.setJobDataMap(jobDataMap);
		
		// Trigger 만들기		
		CronTrigger trigger = new CronTrigger(batchId, null, batchCycle);
		System.out.println("배치스케줄을 등록합니다. 배치스케줄ID : " + batchId );
		System.out.println(batchId + " - cronexpression : " + trigger.getCronExpression());
		trigger.setJobName(jobDetail.getName());
		jobDetail.addJobListener(SubPartWOSendListCngListener.class.getName());
		
		try {
			// 스케줄러에 추가하기
			sched.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			// SchedulerException 이 발생하면 로그를 출력하고 다음 배치작업으로 넘어간다.
			// 트리거의 실행시각이 현재 시각보다 이전이면 SchedulerException이 발생한다.
			System.out.println("스케줄러에 배치작업추가할때 에러가 발생했습니다. 배치스케줄ID : " + batchId );
			System.out.println("에러내용 : " + e.getMessage());
		}	
	}
	
	
}