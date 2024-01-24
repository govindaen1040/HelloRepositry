package Cghs.CghsCardFullDetailsAPI.Model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationContextLocator;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "family_details")
public class FamilyDetailsModel {
	
	@Id
	private String ben_id;

	private Integer card_no;

	private String card_type;

	private String dispensary_code;

	private Integer patient_no;

	private String name;

	private String sex;

	private Integer relation;

	private String blood_group;

	private java.util.Date date_of_birth;

	private String flag;

	private String delete_transfer;

	private String id;

	@CreationTimestamp
	private Time insertion_time;

	private String operator;

	private java.util.Date modify_date;
	

	private Date card_dist;

	private String photo_upload;

	private String card_dis_flag;

	private Integer pen_auto;

	private Integer if_disabled;

	private String remarks;

	private String pc_validity;

	private Date pc_valid_date;
    
	//private Long sl_no;

	private java.util.Date pc_applied_date;

	private String privilige;

	private String password;

	private Date pass_update_date;

	private Date pc_verify_date;

	private String nameh;

	private String aadhar;

	private Date ad_verify_date;

	private Date da_verify_date;

	private Date aadhaar_verified_date;

	private String aadhaar_verified_by;

	private String mobile;

	private String e_mail;

	private Integer email_consent;
	
	

	private Date email_consent_verify_date;

	private String transfer_flag;

	private String m_pin;

	private String m_pin_token;

	private String healthidnumber;

	private String healthid;

	private String link_status;

	private String mobile_verified;
	
	//private String email_consent_ram_api;
	
	//private String off_locality;
	
	private java.util.Date insertion_date;

}
