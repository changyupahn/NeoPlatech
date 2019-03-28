package boassoft.batch;

import java.util.HashMap;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import boassoft.service.BatchMssqlService;
import boassoft.service.BatchMysqlInterfaceService;
import boassoft.util.DateUtil;


public class SubPartWOSendListAllCngScheduler {

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
		
	   String batchId = "batch_014";	
	   String batchCycle = "* /5 * * * *"; //매  5분마다
	   
	   HashMap<String, Object> jobMap = new HashMap<String, Object>();
	   
	   jobMap.put("batchMssqlService", batchMssqlService);
	   jobMap.put("batchMysqlInterfaceService", batchMysqlInterfaceService);
	   
	   JobDataMap jobDataMap = new JobDataMap(jobMap);
	   
	   // Job 만들기
	   JobDetail jobDetail = new JobDetail();
	   jobDetail.setName(batchId);
	   jobDetail.setJobClass(SubPartWOSendListAllCngScriptJob.class);
	   jobDetail.setJobDataMap(jobDataMap);
	   
	   // Trigger 만들기		
	   CronTrigger trigger = new CronTrigger(batchId, null, batchCycle);
	   
	   System.out.println("배치스케줄을 등록합니다. 배치스케줄ID : " + batchId );
	   System.out.println(batchId + " - cronexpression : " + trigger.getCronExpression());
	   trigger.setJobName(jobDetail.getName());
	   jobDetail.addJobListener(SubPartWOSendListAllCngListener.class.getName());
	   
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
	
	/**
	 * 배치스케줄러에 batchSchdul 파라미터를 이용하여 Job , Trigger를 갱신 한다.
	 *
	 * @param batchSchdul  배치스케줄러에 갱신할 스케줄정보
	 * @exception Exception Exception
	 */
	public void updateBatchSchdul() throws Exception {
		//TODO
	}
	
	/**
	 * 배치스케줄러에 batchSchdul 파라미터를 이용하여 Job , Trigger를 삭제한다.
	 *
	 * @param batchSchdul  배치스케줄러에 삭제할 스케줄정보
	 * @exception Exception Exception
	 */
	public void deleteBatchSchdul() throws Exception {
		//TODO
	}	
	
	/**
	 * 클래스 초기화메소드.
	 * 배치스케줄테이블을 읽어서 Quartz 스케줄러를 초기화한다.
	 *
	 */
	public void init() throws Exception {
		
		// 스케줄러 생성하기
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		sched = schedFact.getScheduler();		
	
		// Set up the listener
		SubPartWOSendListAllCngListener listener = new SubPartWOSendListAllCngListener();
		sched.addJobListener(listener);
		
		insertBatchSchdul();

		sched.start();
		
	}
	
	/**
	 * 클래스 destroy메소드.
	 * Quartz 스케줄러를 shutdown한다.
	 *
	 */
	public void destroy() throws Exception {
		sched.shutdown();
	}
}
