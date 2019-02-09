package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface LabelService {

	public int insertLabel(CommonMap cmap) throws Exception;
	
	public void insertLabelCol(CommonMap cmap) throws Exception;
	
	public void deleteLabel(CommonMap cmap) throws Exception;
	
	public CommonList getLabelList(CommonMap cmap) throws Exception;
	
	public CommonList getLabelColList(CommonMap cmap) throws Exception;
	
	
}
