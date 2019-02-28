package boassoft.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class PublicProductListListner implements JobListener {

	 /**
     * Job Listener 이름을 리턴한다. 
     * @see org.quartz.JobListener#getName()
     */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	/**
	 * Batch 작업을 실행하기전에 Batch결과 '수행중'상태로 저장한다. 
	 * 
	 * @param jobContext JobExecutionContext 
	 * @see org.quartz.JobListener#jobToBeExecuted(JobExecutionContext jobContext) 
	 */
	@Override
	public void jobToBeExecuted(JobExecutionContext jobContext) {
		// TODO Auto-generated method stub
		System.out.println("job[" + jobContext.getJobDetail().getName() + "] " + "jobToBeExecuted ");
	}

	/**
	 * Batch 작업을 실행한 후에 Batch결과 '에러'상태로 저장한다.
	 * 
	 * @param jobContext JobExecutionContext
	 * 
	 * @see org.quartz.JobListener#jobExecutionVetoed(JobExecutionContext jobContext) 
	 */
	@Override
	public void jobExecutionVetoed(JobExecutionContext jobContext) {
		// TODO Auto-generated method stub
		System.out.println("job[" + jobContext.getJobDetail().getName() + "] " + "jobExecutionVetoed ");
	}

	/**
	 * Batch 작업을 완료한후 Batch결과 '완료'상태로 저장한다. 
	 * 
	 * @param jobContext JobExecutionContext 
	 * @see org.quartz.JobListener#jobWasExecuted(JobExecutionContext jobContext) 
	 */
	@Override
	public void jobWasExecuted(JobExecutionContext jobContext,
			JobExecutionException jee) {
		// TODO Auto-generated method stub
		System.out.println("job[" + jobContext.getJobDetail().getName() + "] " + "jobWasExecuted ");
		System.out.println("job[" + jobContext.getJobDetail().getName() + "] " + "수행시간 : " + jobContext.getFireTime() + "," + jobContext.getJobRunTime());
	}

}
