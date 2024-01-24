package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveBulkRequestResponseDto {
	
	private int bulk_sms_request_id;
	private Timestamp insertion_date;
	
}
