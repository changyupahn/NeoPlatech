package boassoft.common;

import java.io.Serializable;

public class GoodsXml implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1449664292198310541L;

	private String pt_od_id;	
	private String sub_pt_od_id;
	private String demand_id;
	private String lg_part_no;
	private String lg_od_qty;
	private String prod_type;
	private String neo_line;
	private String neo_plan_key;
	private String send_time;
	private String sending_pc_name;
	private String go_with;
	private String re_print;
	private String sub_part_no;
	private String sub_part_desc;
	private String vendor;
	private String bom_qty;
	private String sub_unit;
	private String sub_sum_qty;
	private String osp;
	private String od_id;
	private String leg_date;
	private String neo_date;
	private String gap_day;
	private String tool;
	private String m_part_no;
	private String plan_qty;
	private String lgm_part_name;	
	private String sub_part_name;
	private String sum_qty;
	private String sum_qty_cng;
	private String unit;
	private String out_place;
	private String chk_day;
	private String in_date;
	private String od_key;
	private String prim_key;
	private String lg_line;	
	private String pg_od_id;
	private String od_qty_id;			
	private String pkg_po_no;
    private String tool_name;
    private String line;
    private String final_vendor;
    private String clazz;
    private String part_number;
    private String desc;
    private String result_qty;
    private String neo_od_time;
    private String neo_od_day;
    private String neo_od_qty;
    private String qty_on_hand;
    private String pre_qty_on_hand;   
	private String draw_no;
	private String row_num;
	private String calcu_true;
	private String demand_floor;
	private String rsltdate;	
	private String rsltstate;
	private String rslt_user_no;
	private String readdate;
	private String readstate;
	private String read_user_no;		
	private String code;
	private String code_name;
			
	
	public String getDemand_floor() {
		return demand_floor;
	}
	public void setDemand_floor(String demand_floor) {
		this.demand_floor = demand_floor;
	}
	public String getCalcu_true() {
		return calcu_true;
	}
	public void setCalcu_true(String calcu_true) {
		this.calcu_true = calcu_true;
	}
	public String getRow_num() {
		return row_num;
	}
	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}
	public String getPt_od_id() {
		return pt_od_id;
	}
	public void setPt_od_id(String pt_od_id) {
		this.pt_od_id = pt_od_id;
	}
	public String getSub_pt_od_id() {
		return sub_pt_od_id;
	}
	public void setSub_pt_od_id(String sub_pt_od_id) {
		this.sub_pt_od_id = sub_pt_od_id;
	}
	public String getDemand_id() {
		return demand_id;
	}
	public void setDemand_id(String demand_id) {
		this.demand_id = demand_id;
	}
	public String getLg_part_no() {
		return lg_part_no;
	}
	public void setLg_part_no(String lg_part_no) {
		this.lg_part_no = lg_part_no;
	}
	public String getLg_od_qty() {
		return lg_od_qty;
	}
	public void setLg_od_qty(String lg_od_qty) {
		this.lg_od_qty = lg_od_qty;
	}
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public String getNeo_line() {
		return neo_line;
	}
	public void setNeo_line(String neo_line) {
		this.neo_line = neo_line;
	}
	public String getNeo_plan_key() {
		return neo_plan_key;
	}
	public void setNeo_plan_key(String neo_plan_key) {
		this.neo_plan_key = neo_plan_key;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSending_pc_name() {
		return sending_pc_name;
	}
	public void setSending_pc_name(String sending_pc_name) {
		this.sending_pc_name = sending_pc_name;
	}
	public String getGo_with() {
		return go_with;
	}
	public void setGo_with(String go_with) {
		this.go_with = go_with;
	}
	public String getRe_print() {
		return re_print;
	}
	public void setRe_print(String re_print) {
		this.re_print = re_print;
	}
	public String getSub_part_no() {
		return sub_part_no;
	}
	public void setSub_part_no(String sub_part_no) {
		this.sub_part_no = sub_part_no;
	}
	public String getSub_part_desc() {
		return sub_part_desc;
	}
	public void setSub_part_desc(String sub_part_desc) {
		this.sub_part_desc = sub_part_desc;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getBom_qty() {
		return bom_qty;
	}
	public void setBom_qty(String bom_qty) {
		this.bom_qty = bom_qty;
	}
	public String getSub_unit() {
		return sub_unit;
	}
	public void setSub_unit(String sub_unit) {
		this.sub_unit = sub_unit;
	}
	public String getSub_sum_qty() {
		return sub_sum_qty;
	}
	public void setSub_sum_qty(String sub_sum_qty) {
		this.sub_sum_qty = sub_sum_qty;
	}
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	public String getOd_id() {
		return od_id;
	}
	public void setOd_id(String od_id) {
		this.od_id = od_id;
	}
	public String getLeg_date() {
		return leg_date;
	}
	public void setLeg_date(String leg_date) {
		this.leg_date = leg_date;
	}
	public String getNeo_date() {
		return neo_date;
	}
	public void setNeo_date(String neo_date) {
		this.neo_date = neo_date;
	}
	public String getGap_day() {
		return gap_day;
	}
	public void setGap_day(String gap_day) {
		this.gap_day = gap_day;
	}
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
	}
	public String getM_part_no() {
		return m_part_no;
	}
	public void setM_part_no(String m_part_no) {
		this.m_part_no = m_part_no;
	}
	public String getPlan_qty() {
		return plan_qty;
	}
	public void setPlan_qty(String plan_qty) {
		this.plan_qty = plan_qty;
	}
	public String getLgm_part_name() {
		return lgm_part_name;
	}
	public void setLgm_part_name(String lgm_part_name) {
		this.lgm_part_name = lgm_part_name;
	}
	public String getSub_part_name() {
		return sub_part_name;
	}
	public void setSub_part_name(String sub_part_name) {
		this.sub_part_name = sub_part_name;
	}
	public String getSum_qty() {
		return sum_qty;
	}
	public void setSum_qty(String sum_qty) {
		this.sum_qty = sum_qty;
	}
	public String getSum_qty_cng() {
		return sum_qty_cng;
	}
	public void setSum_qty_cng(String sum_qty_cng) {
		this.sum_qty_cng = sum_qty_cng;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getOut_place() {
		return out_place;
	}
	public void setOut_place(String out_place) {
		this.out_place = out_place;
	}
	public String getChk_day() {
		return chk_day;
	}
	public void setChk_day(String chk_day) {
		this.chk_day = chk_day;
	}
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	public String getOd_key() {
		return od_key;
	}
	public void setOd_key(String od_key) {
		this.od_key = od_key;
	}
	public String getPrim_key() {
		return prim_key;
	}
	public void setPrim_key(String prim_key) {
		this.prim_key = prim_key;
	}
	public String getLg_line() {
		return lg_line;
	}
	public void setLg_line(String lg_line) {
		this.lg_line = lg_line;
	}
	public String getPg_od_id() {
		return pg_od_id;
	}
	public void setPg_od_id(String pg_od_id) {
		this.pg_od_id = pg_od_id;
	}
	public String getPkg_po_no() {
		return pkg_po_no;
	}
	public void setPkg_po_no(String pkg_po_no) {
		this.pkg_po_no = pkg_po_no;
	}
	public String getTool_name() {
		return tool_name;
	}
	public void setTool_name(String tool_name) {
		this.tool_name = tool_name;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getFinal_vendor() {
		return final_vendor;
	}
	public void setFinal_vendor(String final_vendor) {
		this.final_vendor = final_vendor;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getPart_number() {
		return part_number;
	}
	public void setPart_number(String part_number) {
		this.part_number = part_number;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getResult_qty() {
		return result_qty;
	}
	public void setResult_qty(String result_qty) {
		this.result_qty = result_qty;
	}
	public String getNeo_od_time() {
		return neo_od_time;
	}
	public void setNeo_od_time(String neo_od_time) {
		this.neo_od_time = neo_od_time;
	}
	public String getNeo_od_day() {
		return neo_od_day;
	}
	public void setNeo_od_day(String neo_od_day) {
		this.neo_od_day = neo_od_day;
	}
	public String getNeo_od_qty() {
		return neo_od_qty;
	}
	public void setNeo_od_qty(String neo_od_qty) {
		this.neo_od_qty = neo_od_qty;
	}
	public String getQty_on_hand() {
		return qty_on_hand;
	}
	public void setQty_on_hand(String qty_on_hand) {
		this.qty_on_hand = qty_on_hand;
	}
	public String getPre_qty_on_hand() {
		return pre_qty_on_hand;
	}
	public void setPre_qty_on_hand(String pre_qty_on_hand) {
		this.pre_qty_on_hand = pre_qty_on_hand;
	}
	public String getOd_qty_id() {
		return od_qty_id;
	}
	public void setOd_qty_id(String od_qty_id) {
		this.od_qty_id = od_qty_id;
	}
	public String getDraw_no() {
		return draw_no;
	}
	public void setDraw_no(String draw_no) {
		this.draw_no = draw_no;
	}
	public String getRsltdate() {
		return rsltdate;
	}
	public void setRsltdate(String rsltdate) {
		this.rsltdate = rsltdate;
	}
	public String getRsltstate() {
		return rsltstate;
	}
	public void setRsltstate(String rsltstate) {
		this.rsltstate = rsltstate;
	}
	public String getRslt_user_no() {
		return rslt_user_no;
	}
	public void setRslt_user_no(String rslt_user_no) {
		this.rslt_user_no = rslt_user_no;
	}
	public String getReaddate() {
		return readdate;
	}
	public void setReaddate(String readdate) {
		this.readdate = readdate;
	}
	public String getReadstate() {
		return readstate;
	}
	public void setReadstate(String readstate) {
		this.readstate = readstate;
	}
	public String getRead_user_no() {
		return read_user_no;
	}
	public void setRead_user_no(String read_user_no) {
		this.read_user_no = read_user_no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	
}
