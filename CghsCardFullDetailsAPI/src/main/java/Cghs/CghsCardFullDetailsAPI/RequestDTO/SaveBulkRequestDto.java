package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveBulkRequestDto {

   private String sms_content; 
   private String template_id;
   private java.sql.Date sms_send_target_date;
   private String webinar_series;
   private int sms_type_code;
   private int target_recipient_only_mch;
   private int target_recipent_group;
//   private int message_medium_id;

}
