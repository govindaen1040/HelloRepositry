package Cghs.CghsCardFullDetailsAPI.ResponseDTO;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsContentResponseDto {
	
	private int bulk_sms_request_id;
	private String  sms_content;
	private  String template_id;
	private String webinar_series;
	private String sms_send_status_name; 
	private Date sms_send_target_date;
	private String target_recipient_groups;

}
