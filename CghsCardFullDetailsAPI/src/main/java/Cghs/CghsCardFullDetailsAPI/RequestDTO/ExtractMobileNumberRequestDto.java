package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractMobileNumberRequestDto {
  
	private int bulk_sms_request_id;
	private int target_recipient_only_mch;

}
