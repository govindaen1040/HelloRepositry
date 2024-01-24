package Cghs.CghsCardFullDetailsAPI.RequestDTO;
import java.util.Date;

import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardHolderAddressDto {
//	private Integer card_no;      //N
    private String card_type;     
    private String dispensary_code;     
    private String address_1; 
    private String address_2;
    private String locality;
    private String city_code;
    private String district;
    private String pin_code;
    private String office_phone_no;
    private String residence_phone_no;
    private String e_mail;
    private String operator;       //N
   // private String ben_id;         //N 
 //   private Date modify_date;      //N
    private String mobile_no;
    private String off_address_1;
    private String off_address_2;
    private String off_pin_code;
 //   private Integer pen_auto;       
 //   private Long sl_no;            //N
    
    
}
