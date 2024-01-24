package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexCardDto {
	
//	private String ben_id;         //N

//	private Integer card_no;         //N

	private String card_type;       

	private String dispensary_code;    

	private String no_of_beneficiaries;    

	private Date valid_from;           //N

	private Date valid_to;          //N

	private Integer department_code;        //N

//	private Date insertion_date;           //N

	private String operator;               //N

	private String ic_no;              //N

	private String zone;           

//	private Date modify_date;            //N

	private String entitlement;         //

	private String designation_code;        //

//	private String deputation_flag;          //

//	private Date completion_of_deputation_date;    //

	private String is_transferable;                 

//	private Integer pen_auto;                   //N

	private Integer pay_scale;               

	private Integer present_pay;

	private Integer last_pay;

//	private Long sl_no;            //N

	private String allow_depnd_only;           

	private Integer covid;       //

	private String cpao_diary_no;        //

	private String cpao_verify_status;          

//	private Date cpao_insertion_date;     // 
//need discussion
//	private Date card_validity;      

//	private Integer card_val_change_flag;         //N

	private String cpao_ppo_no;        //

	private String cpao_ssa_no;     

	private String cpao_cancel_remarks;      

	private String ssaskip_remarks;     

	private String payskip_remarks;     

	private String pay_level;     
}
