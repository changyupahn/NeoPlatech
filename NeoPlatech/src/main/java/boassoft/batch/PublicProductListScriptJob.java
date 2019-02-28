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

public class PublicProductListScriptJob implements Job {

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
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("egovBatchExecute() : " + DateUtil.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		
		//setBatchService((BatchService)jobContext.getJobDetail().getJobDataMap().get("batchService"));
		setBatchMssqlService((BatchMssqlService) jobContext.getJobDetail().getJobDataMap().get("batchMssqlService"));
		setBatchMysqlInterfaceService((BatchMysqlInterfaceService) jobContext.getJobDetail().getJobDataMap().get("batchMysqlInterfaceService"));
		
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
			
			batchMysqlInterfaceService.deletePublicProductList(cmap); 
			
			clist = batchMssqlService.getPublicProductList(cmap);
			
			if (!clist.isEmpty() && clist.size() > 0) {
				for (int i = 0; i < clist.size(); i++) {
					CommonMap gmap = clist.getMap(i);
					
					System.out.println(" row pnKey " + i +  " 번째  " +  " : " + gmap.getString("pnKey") + " ");
					System.out.println(" row barcodeNo " + i +  " 번째  " +  " : " + gmap.getString("barCodeNo") + " ");
					System.out.println(" row enterDate " + i +  " 번째  " +  " : " + gmap.getString("enterDate") + " ");
					System.out.println(" row enterTime " + i +  " 번째  " +  " : " + gmap.getString("enterTime") + " ");
					System.out.println(" row activeLocation " + i +  " 번째  " +  " : " + gmap.getString("activeLocation") + " ");
					System.out.println(" row activePosition " + i +  " 번째  " +  " : " + gmap.getString("activePosition") + " ");
					System.out.println(" row previousLocation " + i +  " 번째  " +  " : " + gmap.getString("previousLocation") + " ");
					System.out.println(" row daechaHo " + i +  " 번째  " +  " : " + gmap.getString("daechaHo") + " ");
					System.out.println(" row daechaKind " + i +  " 번째  " +  " : " + gmap.getString("daechaKind") + " ");
					System.out.println(" row teamName " + i +  " 번째  " +  " : " + gmap.getString("teamName") + " ");
					System.out.println(" row hoGi " + i +  " 번째  " +  " : " + gmap.getString("hoGi") + " ");
					System.out.println(" row goodsKind " + i +  " 번째  " +  " : " + gmap.getString("goodsKind") + " ");
					System.out.println(" row worker1 " + i +  " 번째  " +  " : " + gmap.getString("worker1") + " ");
					System.out.println(" row worker2 " + i +  " 번째  " +  " : " + gmap.getString("worker2") + " ");
					System.out.println(" row workOrder " + i +  " 번째  " +  " : " + gmap.getString("workOrder") + " ");
					System.out.println(" row partNo " + i +  " 번째  " +  " : " + gmap.getString("partNo") + " ");
					System.out.println(" row model " + i +  " 번째  " +  " : " + gmap.getString("model") + " ");
					System.out.println(" row tool " + i +  " 번째  " +  " : " + gmap.getString("tool") + " ");
					System.out.println(" row pitem " + i +  " 번째  " +  " : " + gmap.getString("pitem") + " ");
					System.out.println(" row qty " + i +  " 번째  " +  " : " + gmap.getString("qty") + " ");
					System.out.println(" row item1 " + i +  " 번째  " +  " : " + gmap.getString("item1") + " ");
					System.out.println(" row item2 " + i +  " 번째  " +  " : " + gmap.getString("item2") + " ");
					System.out.println(" row qty1 " + i +  " 번째  " +  " : " + gmap.getString("qty1") + " ");
					System.out.println(" row qty2 " + i +  " 번째  " +  " : " + gmap.getString("qty2") + " ");
					System.out.println(" row core " + i +  " 번째  " +  " : " + gmap.getString("core") + " ");
					System.out.println(" row resinCode " + i +  " 번째  " +  " : " + gmap.getString("resinCode") + " ");
					System.out.println(" row color " + i +  " 번째  " +  " : " + gmap.getString("color") + " ");
					System.out.println(" row remark " + i +  " 번째  " +  " : " + gmap.getString("remark") + " ");
					System.out.println(" row scanType " + i +  " 번째  " +  " : " + gmap.getString("scanType") + " ");
					System.out.println(" row scanTime " + i +  " 번째  " +  " : " + gmap.getString("scanTime") + " ");
					System.out.println(" row newTime " + i +  " 번째  " +  " : " + gmap.getString("newTime") + " ");
					System.out.println(" row indexKey " + i +  " 번째  " +  " : " + gmap.getString("indexKey") + " ");
					System.out.println(" row kanbanNo " + i +  " 번째  " +  " : " + gmap.getString("kanbanNo") + " ");
					System.out.println(" row chk " + i +  " 번째  " +  " : " + gmap.getString("chk") + " ");
					System.out.println(" row soKey " + i +  " 번째  " +  " : " + gmap.getString("soKey") + " ");
					System.out.println(" row outDate " + i +  " 번째  " +  " : " + gmap.getString("outDate") + " ");
					System.out.println(" row outChk " + i +  " 번째  " +  " : " + gmap.getString("outChk") + " ");
					System.out.println(" row workDept " + i +  " 번째  " +  " : " + gmap.getString("workDept") + " ");
					System.out.println(" row lineDept " + i +  " 번째  " +  " : " + gmap.getString("lineDept") + " ");
					System.out.println(" row nowPosition " + i +  " 번째  " +  " : " + gmap.getString("nowPosition") + " ");
					System.out.println(" row sDaechaNo " + i +  " 번째  " +  " : " + gmap.getString("sDaechaNo") + " ");
					System.out.println(" row preKey " + i +  " 번째  " +  " : " + gmap.getString("preKey") + " ");
					System.out.println(" row preBarcodeNo " + i +  " 번째  " +  " : " + gmap.getString("preBarcodeNo") + " ");
					System.out.println(" row inejctionDate " + i +  " 번째  " +  " : " + gmap.getString("inejctionDate") + " ");
					System.out.println(" row injectionBarcodeNo " + i +  " 번째  " +  " : " + gmap.getString("injectionBarcodeNo") + " ");
					System.out.println(" row inpKey " + i +  " 번째  " +  " : " + gmap.getString("inpKey") + " ");
					System.out.println(" row primKey " + i +  " 번째  " +  " : " + gmap.getString("primKey") + " ");
					System.out.println(" row qmKey " + i +  " 번째  " +  " : " + gmap.getString("qmKey") + " ");
					System.out.println(" row fChk " + i +  " 번째  " +  " : " + gmap.getString("fChk") + " ");
					System.out.println(" row building " + i +  " 번째  " +  " : " + gmap.getString("building") + " ");
					System.out.println(" row gKey " + i +  " 번째  " +  " : " + gmap.getString("gKey") + " ");
					System.out.println(" row demandId " + i +  " 번째  " +  " : " + gmap.getString("demandId") + " ");
					System.out.println(" row checkDate " + i +  " 번째  " +  " : " + gmap.getString("checkDate") + " ");
					System.out.println(" row barcodeFullNo " + i +  " 번째  " +  " : " + gmap.getString("barcodeFullNo") + " ");
					System.out.println(" row daechaType " + i +  " 번째  " +  " : " + gmap.getString("daechaType") + " ");
					
					
					batchMysqlInterfaceService.insertPublicProductList(gmap);
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
