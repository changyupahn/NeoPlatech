package boassoft.common;

import java.io.Serializable;

import boassoft.util.DateUtil;
import boassoft.util.StringUtil;

@SuppressWarnings("serial")
public class CommonXml implements Serializable {

	private String asset_seq;
	private String asset_type;
	private String asset_type_name;
	private String asset_cate;
	private String item_name;	
	private String asset_no;
	private String rfid_no;
	private String asset_name;
	private String aqusit_dt;
	private String aqusit_amt;
	private String asset_size;
	private String org_no;
	private String org_name;
	private String top_dept_no;
	private String top_dept_name;
	private String top_user_no;
	private String top_user_name;
	private String dept_no;
	private String dept_name;
	private String user_no;
	private String user_name;
	private String bldng_no;
	private String bldng_name;
	private String floor_no;
	private String floor_name;
	private String hosil;
	private String pos_no;
	private String pos_name;
	private String remark;
	private String inv_year;
	private String inv_no;
	private String inv_start_dt;
	private String total_count;
	private String inv_target_count;
	private String cng_asset_size;
	private String cng_org_no;
	private String cng_org_name;
	private String cng_top_dept_no;
	private String cng_top_dept_name;
	private String cng_top_user_no;
	private String cng_top_user_name;
	private String cng_dept_no;
	private String cng_dept_name;
	private String cng_user_no;
	private String cng_user_name;
	private String cng_bldng_no;
	private String cng_bldng_name;
	private String cng_floor_no;
	private String cng_floor_name;
	private String cng_hosil;
	private String cng_pos_no;
	private String cng_pos_name;
	private String cng_remark;
	private String cng_basic_dt;
	private String tag_print_cnt;
	private String tag_read_yn;
	private String tag_read_dt;
	private String inv_target_yn;
	private String file_dt;
	private String file_path;
	private String file_nm;
	private String orignl_file_nm;
	private String file_extsn;
	private String use_yn;
	private String file_url;
	private String tag_insp_name;
	private String update_dt;
	private String print_seq;
	private String org;
	private String req_system;
	private String print_dt;
	private String print_type;
	private String print_yn;
	private String tmp_title;
	private String asset_cnt;
	private String barcode_no;
	private String image_yn;
	private String etc1;
	private String etc2;
	private String etc3;
	private String etc4;
	private String asset_status_cd;
	private String top_asset_no;
	private String disuse_dt;
	private String disuse_cont;

	public String getAsset_seq() {
		return asset_seq;
	}
	public void setAsset_seq(String asset_seq) {
		this.asset_seq = asset_seq;
	}
	public String getAsset_type() {
		return asset_type;
	}
	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type.trim();
	}
	public String getAsset_cate() {
		return asset_cate;
	}
	public void setAsset_cate(String asset_cate) {
		this.asset_cate = asset_cate.trim();
	}
	public String getAsset_no() {
		return asset_no;
	}
	public void setAsset_no(String asset_no) {
		this.asset_no = asset_no.trim();
	}
	public String getRfid_no() {
		return rfid_no;
	}
	public void setRfid_no(String rfid_no) {
		this.rfid_no = rfid_no.trim();
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = StringUtil.strCut(asset_name.trim(), 24);
	}
	public String getAqusit_dt() {
		return aqusit_dt;
	}
	public void setAqusit_dt(String aqusit_dt) {
		this.aqusit_dt = DateUtil.formatDate(aqusit_dt.trim(),"-");
	}
	public String getAsset_size() {
		return asset_size;
	}
	public void setAsset_size(String asset_size) {
		this.asset_size = asset_size.trim();
	}
	public String getOrg_no() {
		return org_no;
	}
	public void setOrg_no(String org_no) {
		this.org_no = org_no.trim();
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name.trim();
	}
	public String getTop_dept_no() {
		return top_dept_no;
	}
	public void setTop_dept_no(String top_dept_no) {
		this.top_dept_no = top_dept_no.trim();
	}
	public String getTop_dept_name() {
		return top_dept_name;
	}
	public void setTop_dept_name(String top_dept_name) {
		this.top_dept_name = top_dept_name.trim();
	}
	public String getTop_user_no() {
		return top_user_no;
	}
	public void setTop_user_no(String top_user_no) {
		this.top_user_no = top_user_no.trim();
	}
	public String getTop_user_name() {
		return top_user_name;
	}
	public void setTop_user_name(String top_user_name) {
		this.top_user_name = top_user_name.trim();
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no.trim();
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name.trim();
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no.trim();
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name.trim();
	}
	public String getBldng_no() {
		return bldng_no;
	}
	public void setBldng_no(String bldng_no) {
		this.bldng_no = bldng_no.trim();
	}
	public String getBldng_name() {
		return bldng_name;
	}
	public void setBldng_name(String bldng_name) {
		this.bldng_name = bldng_name.trim();
	}
	public String getFloor_no() {
		return floor_no;
	}
	public void setFloor_no(String floor_no) {
		this.floor_no = floor_no.trim();
	}
	public String getFloor_name() {
		return floor_name;
	}
	public void setFloor_name(String floor_name) {
		this.floor_name = floor_name.trim();
	}
	public String getHosil() {
		return hosil;
	}
	public void setHosil(String hosil) {
		this.hosil = hosil.trim().trim();
	}
	public String getPos_no() {
		return pos_no;
	}
	public void setPos_no(String pos_no) {
		this.pos_no = pos_no.trim();
	}
	public String getPos_name() {
		return pos_name;
	}
	public void setPos_name(String pos_name) {
		this.pos_name = pos_name.trim();
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark.trim();
	}
	public String getInv_year() {
		return inv_year;
	}
	public void setInv_year(String inv_year) {
		this.inv_year = inv_year.trim();
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no.trim();
	}
	public String getInv_start_dt() {
		return inv_start_dt;
	}
	public void setInv_start_dt(String inv_start_dt) {
		this.inv_start_dt = inv_start_dt.trim();
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count.trim();
	}
	public String getInv_target_count() {
		return inv_target_count;
	}
	public void setInv_target_count(String inv_target_count) {
		this.inv_target_count = inv_target_count.trim();
	}
	public String getCng_asset_size() {
		return cng_asset_size;
	}
	public void setCng_asset_size(String cng_asset_size) {
		this.cng_asset_size = cng_asset_size.trim();
	}
	public String getCng_org_no() {
		return cng_org_no;
	}
	public void setCng_org_no(String cng_org_no) {
		this.cng_org_no = cng_org_no.trim();
	}
	public String getCng_org_name() {
		return cng_org_name;
	}
	public void setCng_org_name(String cng_org_name) {
		this.cng_org_name = cng_org_name.trim();
	}
	public String getCng_top_dept_no() {
		return cng_top_dept_no;
	}
	public void setCng_top_dept_no(String cng_top_dept_no) {
		this.cng_top_dept_no = cng_top_dept_no.trim();
	}
	public String getCng_top_dept_name() {
		return cng_top_dept_name;
	}
	public void setCng_top_dept_name(String cng_top_dept_name) {
		this.cng_top_dept_name = cng_top_dept_name.trim();
	}
	public String getCng_top_user_no() {
		return cng_top_user_no;
	}
	public void setCng_top_user_no(String cng_top_user_no) {
		this.cng_top_user_no = cng_top_user_no.trim();
	}
	public String getCng_top_user_name() {
		return cng_top_user_name;
	}
	public void setCng_top_user_name(String cng_top_user_name) {
		this.cng_top_user_name = cng_top_user_name.trim();
	}
	public String getCng_dept_no() {
		return cng_dept_no;
	}
	public void setCng_dept_no(String cng_dept_no) {
		this.cng_dept_no = cng_dept_no.trim();
	}
	public String getCng_dept_name() {
		return cng_dept_name;
	}
	public void setCng_dept_name(String cng_dept_name) {
		this.cng_dept_name = cng_dept_name.trim();
	}
	public String getCng_user_no() {
		return cng_user_no;
	}
	public void setCng_user_no(String cng_user_no) {
		this.cng_user_no = cng_user_no.trim();
	}
	public String getCng_user_name() {
		return cng_user_name;
	}
	public void setCng_user_name(String cng_user_name) {
		this.cng_user_name = cng_user_name.trim();
	}
	public String getCng_bldng_no() {
		return cng_bldng_no;
	}
	public void setCng_bldng_no(String cng_bldng_no) {
		this.cng_bldng_no = cng_bldng_no.trim();
	}
	public String getCng_bldng_name() {
		return cng_bldng_name;
	}
	public void setCng_bldng_name(String cng_bldng_name) {
		this.cng_bldng_name = cng_bldng_name.trim();
	}
	public String getCng_floor_no() {
		return cng_floor_no;
	}
	public void setCng_floor_no(String cng_floor_no) {
		this.cng_floor_no = cng_floor_no.trim();
	}
	public String getCng_floor_name() {
		return cng_floor_name;
	}
	public void setCng_floor_name(String cng_floor_name) {
		this.cng_floor_name = cng_floor_name.trim();
	}
	public String getCng_hosil() {
		return cng_hosil;
	}
	public void setCng_hosil(String cng_hosil) {
		this.cng_hosil = cng_hosil.trim();
	}
	public String getCng_pos_no() {
		return cng_pos_no;
	}
	public void setCng_pos_no(String cng_pos_no) {
		this.cng_pos_no = cng_pos_no.trim();
	}
	public String getCng_pos_name() {
		return cng_pos_name;
	}
	public void setCng_pos_name(String cng_pos_name) {
		this.cng_pos_name = cng_pos_name.trim();
	}
	public String getCng_remark() {
		return cng_remark;
	}
	public void setCng_remark(String cng_remark) {
		this.cng_remark = cng_remark.trim();
	}
	public String getCng_basic_dt() {
		return cng_basic_dt;
	}
	public void setCng_basic_dt(String cng_basic_dt) {
		this.cng_basic_dt = cng_basic_dt.trim();
	}
	public String getTag_print_cnt() {
		return tag_print_cnt;
	}
	public void setTag_print_cnt(String tag_print_cnt) {
		this.tag_print_cnt = tag_print_cnt.trim();
	}
	public String getTag_read_yn() {
		return tag_read_yn;
	}
	public void setTag_read_yn(String tag_read_yn) {
		this.tag_read_yn = tag_read_yn.trim();
	}
	public String getTag_read_dt() {
		return tag_read_dt;
	}
	public void setTag_read_dt(String tag_read_dt) {
		this.tag_read_dt = tag_read_dt.trim();
	}
	public String getInv_target_yn() {
		return inv_target_yn;
	}
	public void setInv_target_yn(String inv_target_yn) {
		this.inv_target_yn = inv_target_yn.trim();
	}
	public String getFile_dt() {
		return file_dt;
	}
	public void setFile_dt(String file_dt) {
		this.file_dt = file_dt.trim();
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path.trim();
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm.trim();
	}
	public String getOrignl_file_nm() {
		return orignl_file_nm;
	}
	public void setOrignl_file_nm(String orignl_file_nm) {
		this.orignl_file_nm = orignl_file_nm.trim();
	}
	public String getFile_extsn() {
		return file_extsn;
	}
	public void setFile_extsn(String file_extsn) {
		this.file_extsn = file_extsn.trim();
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn.trim();
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url.trim();
	}
	public String getTag_insp_name() {
		return tag_insp_name;
	}
	public void setTag_insp_name(String tag_insp_name) {
		this.tag_insp_name = tag_insp_name.trim();
	}
	public String getUpdate_dt() {
		return update_dt;
	}
	public void setUpdate_dt(String update_dt) {
		this.update_dt = update_dt.trim();
	}
	public String getPrint_seq() {
		return print_seq;
	}
	public void setPrint_seq(String print_seq) {
		this.print_seq = print_seq.trim();
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org.trim();
	}
	public String getReq_system() {
		return req_system;
	}
	public void setReq_system(String req_system) {
		this.req_system = req_system.trim();
	}
	public String getPrint_dt() {
		return print_dt;
	}
	public void setPrint_dt(String print_dt) {
		this.print_dt = print_dt.trim();
	}
	public String getPrint_yn() {
		return print_yn;
	}
	public void setPrint_yn(String print_yn) {
		this.print_yn = print_yn.trim();
	}
	public String getPrint_type() {
		return print_type;
	}
	public void setPrint_type(String print_type) {
		this.print_type = print_type.trim();
	}
	public String getTmp_title() {
		return tmp_title;
	}
	public void setTmp_title(String tmp_title) {
		this.tmp_title = tmp_title.trim();
	}
	public String getAsset_cnt() {
		return asset_cnt;
	}
	public void setAsset_cnt(String asset_cnt) {
		this.asset_cnt = asset_cnt.trim();
	}
	public String getBarcode_no() {
		return barcode_no;
	}
	public void setBarcode_no(String barcode_no) {
		this.barcode_no = barcode_no.trim();
	}
	public String getImage_yn() {
		return image_yn;
	}
	public void setImage_yn(String image_yn) {
		this.image_yn = image_yn.trim();
	}
	public String getEtc1() {
		return etc1;
	}
	public void setEtc1(String etc1) {
		this.etc1 = etc1.trim();
	}
	public String getEtc2() {
		return etc2;
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2.trim();
	}
	public String getEtc3() {
		return etc3;
	}
	public void setEtc3(String etc3) {
		this.etc3 = StringUtil.commaNum(etc3.trim());
	}
	public String getEtc4() {
		return etc4;
	}
	public void setEtc4(String etc4) {
		this.etc4 = etc4.trim();
	}
	public String getAsset_status_cd() {
		return asset_status_cd;
	}
	public void setAsset_status_cd(String asset_status_cd) {
		this.asset_status_cd = asset_status_cd.trim();
	}
	public String getTop_asset_no() {
		return top_asset_no;
	}
	public void setTop_asset_no(String top_asset_no) {
		this.top_asset_no = top_asset_no.trim();
	}
	public String getDisuse_dt() {
		return disuse_dt;
	}
	public void setDisuse_dt(String disuse_dt) {
		this.disuse_dt = disuse_dt.trim();
	}
	public String getDisuse_cont() {
		return disuse_cont;
	}
	public void setDisuse_cont(String disuse_cont) {
		this.disuse_cont = disuse_cont.trim();
	}
	public String getAsset_type_name() {
		return asset_type_name;
	}
	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name.trim();
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name.trim();
	}
	public String getAqusit_amt() {
		return aqusit_amt;
	}
	public void setAqusit_amt(String aqusit_amt) {
		this.aqusit_amt = aqusit_amt.trim();
	}
}
