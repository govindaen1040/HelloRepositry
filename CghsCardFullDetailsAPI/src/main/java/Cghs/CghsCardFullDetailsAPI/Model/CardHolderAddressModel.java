package Cghs.CghsCardFullDetailsAPI.Model;

import java.util.Date;

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
@Table(name="card_holder_address")
public class CardHolderAddressModel {
	private Integer card_no;
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
    private String operator;
    @Id
    private String ben_id;
    private Date modify_date;
    private String mobile_no;
    private String off_address_1;
    private String off_address_2;
    private String off_pin_code;
    private Integer pen_auto;
   // private Long sl_no;

}
