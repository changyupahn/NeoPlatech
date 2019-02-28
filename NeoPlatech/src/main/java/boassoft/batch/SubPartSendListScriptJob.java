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

public class SubPartSendListScriptJob implements Job {

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
		// TODO Auto-generated method stub
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
			batchMysqlInterfaceService.deleteSubPartSendList(cmap);

			clist = batchMssqlService.getSubPartSendList(cmap);

			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					CommonMap gmap = clist.getMap(i);

					System.out.println(" row ptOdId " + i + " 번째  " + " : "
							+ gmap.getString("ptOdId") + " ");
					System.out.println(" row subPtOdId " + i + " 번째  " + " : "
							+ gmap.getString("subPtOdId") + " ");
					System.out.println(" row demamdId " + i + " 번째  " + " : "
							+ gmap.getString("demamdId") + " ");
					System.out.println(" row lgPartNo " + i + " 번째  " + " : "
							+ gmap.getString("lgPartNo") + " ");
					System.out.println(" row lgOdQty " + i + " 번째  " + " : "
							+ gmap.getString("lgOdQty") + " ");
					System.out.println(" row prodType " + i + " 번째  " + " : "
							+ gmap.getString("prodType") + " ");
					System.out.println(" row neoLine " + i + " 번째  " + " : "
							+ gmap.getString("neoLine") + " ");
					System.out.println(" row neoPlanKey " + i + " 번째  " + " : "
							+ gmap.getString("neoPlanKey") + " ");
					System.out.println(" row sendTime " + i + " 번째  " + " : "
							+ gmap.getString("sendTime") + " ");
					System.out.println(" row sendingPcName " + i + " 번째  "
							+ " : " + gmap.getString("sendingPcName") + " ");
					System.out.println(" row goWith " + i + " 번째  " + " : "
							+ gmap.getString("goWith") + " ");
					System.out.println(" row rePrint " + i + " 번째  " + " : "
							+ gmap.getString("rePrint") + " ");
					System.out.println(" row subPartNo " + i + " 번째  " + " : "
							+ gmap.getString("subPartNo") + " ");
					System.out.println(" row subPartDesc " + i + " 번째  "
							+ " : " + gmap.getString("subPartDesc") + " ");
					System.out.println(" row vendor " + i + " 번째  " + " : "
							+ gmap.getString("vendor") + " ");
					System.out.println(" row bomQty " + i + " 번째  " + " : "
							+ gmap.getString("bomQty") + " ");
					System.out.println(" row subUnit " + i + " 번째  " + " : "
							+ gmap.getString("subUnit") + " ");
					System.out.println(" row subSumQty " + i + " 번째  " + " : "
							+ gmap.getString("subSumQty") + " ");
					System.out.println(" row demandFloor " + i + " 번째  "
							+ " : " + gmap.getString("demandFloor") + " ");
					System.out.println(" row osp " + i + " 번째  " + " : "
							+ gmap.getString("osp") + " ");

					batchMysqlInterfaceService.insertSubPartSendList(gmap);
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
