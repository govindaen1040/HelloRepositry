package Cghs.CghsCardFullDetailsAPI.Model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "family_index_card_log")
public class FamilyIndexCardLog {
	    @Id
	    private String ben_id;

	    private Date insertion_date;

	    private Date insertion_time;

	    private Integer card_no;
    
	    private String card_type;

	    private String dispensary_code;

	    private Integer patient_no;

	    private String name;

	    private String sex;

	    private String relation;

	    private String blood_group;

	    private Date date_of_birth;

	    private String flag;

	    private String delete_transfer;

	    private String id;
	    
	    private Date modify_date;

	    private String no_of_beneficiaries;

	    private Integer department_code;

	    private String ic_no;

	    private String zone;

       private  Date valid_from;
       
       private Date valid_to;
       
       private String address1;
       
       private String address2;
       
       private String locality;
       
       private String city_code;
       
       private String district;
       
       private String pin_code;
       
       private String office_phone_no;
       
       private String residence_phone_no;
       
       @Column(name="e-mail")
       private String email;
            
       private String remarks;
       
       private Integer if_disabled;
       
       private String mobile_no;
       
       private String mobile;
       
       private String photo_upload;
       
       private Integer covid;
       
       private String cpao_diary_no;
       
       private String cpao_verify_status;
              
       private Timestamp cpao_insertion_date;
       
       private Integer pay_scale;
       
       private String allow_depnd_only;
       
       private String entitlement;
   
}
