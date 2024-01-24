package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMemberDto {

// private String ben_mem_type;

	// private Integer card_no;

	private String nameh;

	private String name;

	private String sex;

	private java.sql.Date date_of_birth;

	private Integer relation;

	private String ben_id;

	private String card_type;

	private String dispensary_code;

	private int patient_no;

	private String mobile;

	private String blood_group;
	
	private String operator;

    private String pc_validity;

//	private String delete_transfer;

//	private String card_dis_flag;

//	private Long sl_no;

//	private String privilige;
	
	private Date pc_valid_date;
	
	private Date pc_applied_date;

	private int if_disabled;
	
	private String remarks;

}
