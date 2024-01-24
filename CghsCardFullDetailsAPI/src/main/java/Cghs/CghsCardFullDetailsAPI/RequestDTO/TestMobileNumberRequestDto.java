package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestMobileNumberRequestDto {
	
	private int bulk_sms_request_id;
	private String mobile_no;

}
