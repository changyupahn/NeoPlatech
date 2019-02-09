package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("LabelMapper")
public interface LabelMapper {

	public int insertLabel(CommonMap cmap) throws Exception;
	
	public void insertLabelCol(CommonMap cmap) throws Exception;
	
	public void deleteLabel(CommonMap cmap) throws Exception;
	
	public CommonList getLabelList(CommonMap cmap) throws Exception;
	
	public int getLabelListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getLabelColList(CommonMap cmap) throws Exception;
}
