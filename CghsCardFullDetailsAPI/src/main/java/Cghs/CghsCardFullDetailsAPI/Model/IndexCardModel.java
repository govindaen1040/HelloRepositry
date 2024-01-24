package Cghs.CghsCardFullDetailsAPI.Model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "index_card")
public class IndexCardModel {
	@Id
	private String ben_id;

	private Integer card_no;

	private String card_type;

	private String dispensary_code;

	private String no_of_beneficiaries;

	//@Temporal(TemporalType.DATE)
	private Date valid_from;

	//@Temporal(TemporalType.DATE)
	private Date valid_to;

	private Integer department_code;

	//@Temporal(TemporalType.TIMESTAMP)
	private Date insertion_date;

//	@PrePersist
//	protected void onCreate() {
//		insertion_date = new Date();
//	}

	private String operator;

	private String ic_no;

	private String zone;

	//@Temporal(TemporalType.DATE)
	private Date modify_date;

	private String entitlement;

	private String designation_code;

	private String deputation_flag;

	//@Temporal(TemporalType.DATE)
	private Date completion_of_deputation_date;

	private String is_transferable;

	private Integer pen_auto;

	private Integer pay_scale;

	private Integer present_pay;

	private Integer last_pay;

  //private Long sl_no;

	private String allow_depnd_only;

	private Integer covid;

	private String cpao_diary_no;

	private String cpao_verify_status;

	private Date cpao_insertion_date;

	private Date card_validity;

	private Integer card_val_change_flag;

	private String cpao_ppo_no;

	private String cpao_ssa_no;

	private String cpao_cancel_remarks;

	private String ssaskip_remarks;

	private String payskip_remarks;

	private String pay_level;

}
