package boassoft.batch;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import boassoft.service.BatchService;
import boassoft.util.DateUtil;
import egovframework.com.cmm.service.EgovProperties;

public class BatchShellScriptJob implements Job {

	/** batchService */
	private BatchService batchService;

	/** setBatchService */
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		System.out.println("egovBatchExecute() : "
				+ DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));

		setBatchService((BatchService) jobContext.getJobDetail()
				.getJobDataMap().get("batchService"));

		try {
			// 데이터베이스 백업
			executeBackup();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// jobContext에 결과값을 저장한다.
		jobContext.setResult(true);
		System.out.println("egovBatchExecute() : OK "
				+ DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
	}

	private int executeBackup() {

		int result = 0;
		try {
			Process p = null;
			// String cmdStr = batchProgrm + " " + paramtr;
			String cmdStr = EgovProperties
					.getProperty("Globals.BackupBatchPath");
			String backupDir = EgovProperties.getProperty("Globals.BackupPath");
			if (cmdStr != null && !"".equals(cmdStr) && backupDir != null
					&& !"".equals(backupDir)) {

				System.out.println("executeBackup() Start");
				File fd = new File(backupDir);
				if (!fd.exists()) {
					fd.mkdirs();
				}

				p = Runtime.getRuntime().exec(cmdStr);
				p.waitFor();
				result = p.exitValue();
				System.out.println("p.exitValue() : " + p.exitValue());
				// //프로세스 에러시 종료
				// if (p.exitValue() != 0) {
				//
				// }
				// //프로세스 실행 성공시 결과 확인
				// else {
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
