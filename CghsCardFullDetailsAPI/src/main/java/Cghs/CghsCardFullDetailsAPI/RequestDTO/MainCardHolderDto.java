package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainCardHolderDto {
	
	private String ben_id;            //N

//	private Integer card_no;             //N

	private String card_type;         

	private String dispensary_code;        

	private Integer patient_no;

	private String name;    

	private String sex;     

	private Integer relation;

	private String blood_group;

	private Date date_of_birth;

//	private String flag;         //

//	private String delete_transfer;       //

//	private String id;              //

//	private Time insertion_time;         //

	private String operator;        

	//private Date modify_date;         //

//	private Date card_dist;           //

//	private String photo_upload;      //

//	private String card_dis_flag;      //

	private Integer pen_auto;       //

//	private Integer if_disabled;     //

	private String remarks;       

	private String pc_validity;        

//	private Date pc_valid_date;         //

//	private Long sl_no;                 //

//	private Date pc_applied_date;        //

//	private String privilige;           //

	private String password;          //

//	private java.util.Date pass_update_date;        //

//	private java.util.Date pc_verify_date;          //

	private String nameh;        

	private String aadhar;     //

	private java.util.Date ad_verify_date;      //

	private java.util.Date da_verify_date;        //

	//private java.util.Date aadhaar_verified_date;         //

	private String aadhaar_verified_by;              //

	private String mobile;       

	private String e_mail;

	private Integer email_consent;        //

//	private java.util.Date email_consent_verify_date;        //
 
//	private String transfer_flag;           //

	private String m_pin;              //

	private String m_pin_token;          //

	private String healthidnumber;          

	private String healthid;           

	private String link_status;        //

//	private String mobile_verified;          //
	
//	private String email_consent_ram_api;        //
	
	private String off_locality;           //
	
//	private java.util.Date insertion_date;          //


}
