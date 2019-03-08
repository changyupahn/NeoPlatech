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

public class SubPartWoSendListODOnlyScriptJob implements Job {

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
	public void setBatchMysqlInterfaceService(BatchMysqlInterfaceService batchMysqlInterfaceService) {
		this.batchMysqlInterfaceService = batchMysqlInterfaceService;
	}
	
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
			
			batchMysqlInterfaceService.deleteSubPartWoSendListODOnlyList(cmap);
			
			clist = batchMssqlService.getSubPartWoSendListODOnlyList(cmap);
			
			if(!clist.isEmpty() && clist.size() > 0){
				for(int i = 0; i < clist.size() ; i++){
					 CommonMap gmap = clist.getMap(i);
					
					 System.out.println(" row odId " + i + " 번째  " + " : "+ gmap.getString("odId") + " ");
					 System.out.println(" row demandId " + i + " 번째  " + " : "+ gmap.getString("odId") + " ");
					 System.out.println(" row legDate " + i + " 번째  " + " : "+ gmap.getString("legDate") + " ");
					 System.out.println(" row neoDate " + i + " 번째  " + " : "+ gmap.getString("legDate") + " ");
					 System.out.println(" row gapDay " + i + " 번째  " + " : "+ gmap.getString("gapDay") + " ");
					 System.out.println(" row lgLine " + i + " 번째  " + " : "+ gmap.getString("tool") + " ");
					 System.out.println(" row tool " + i + " 번째  " + " : "+ gmap.getString("tool") + " ");
					 System.out.println(" row mPartNo " + i + " 번째  " + " : "+ gmap.getString("mPartNo") + " ");
					 System.out.println(" row planQty " + i + " 번째  " + " : "+ gmap.getString("planQty") + " ");
					 System.out.println(" row lgmPartName " + i + " 번째  " + " : "+ gmap.getString("lgmPartName") + " ");
					 System.out.println(" row subPartNo " + i + " 번째  " + " : "+ gmap.getString("subPartNo") + " ");
					 System.out.println(" row subPartName " + i + " 번째  " + " : "+ gmap.getString("subPartName") + " ");
					 System.out.println(" row bomQty " + i + " 번째  " + " : "+ gmap.getString("bomQty") + " ");
					 System.out.println(" row sumQty " + i + " 번째  " + " : "+ gmap.getString("sumQty") + " ");
					 System.out.println(" row sumQtyCng " + i + " 번째  " + " : "+ gmap.getString("sumQtyCng") + " ");
					 System.out.println(" row unit " + i + " 번째  " + " : "+ gmap.getString("unit") + " ");
					 System.out.println(" row vendor " + i + " 번째  " + " : "+ gmap.getString("vendor") + " ");
					 System.out.println(" row osp " + i + " 번째  " + " : "+ gmap.getString("osp") + " ");
					 System.out.println(" row outPlace " + i + " 번째  " + " : "+ gmap.getString("outPlace") + " ");
					 System.out.println(" row chkDay " + i + " 번째  " + " : "+ gmap.getString("chkDay") + " ");
					 System.out.println(" row myCom " + i + " 번째  " + " : "+ gmap.getString("myCom") + " ");
					 System.out.println(" row inDate " + i + " 번째  " + " : "+ gmap.getString("inDate") + " ");
					 
					 batchMysqlInterfaceService.insertSubPartWoSendListODOnlyList(gmap);
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
