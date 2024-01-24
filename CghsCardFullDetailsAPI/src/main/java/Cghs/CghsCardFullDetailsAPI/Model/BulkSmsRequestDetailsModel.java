package Cghs.CghsCardFullDetailsAPI.Model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bulk_sms_request_details")
public class BulkSmsRequestDetailsModel {
	@Id
	private int bulk_sms_request_id;
	@Column(name = "sms_content")
	private String smsContent;
	private String template_id;
	@Column(name = "webinar_series")
	private String webinarSeries;
	@Column(name = "sms_send_target_date")
	private Date smsSendTargetDate;
	private String user_id;
	private java.sql.Date modify_date;
	private String ipaddress;
	private Integer target_recipient_groups;
	private String remarks;
	private Integer sms_type_code;
	private Integer sms_send_status_code;
	private Time sms_send_target_time;
	private java.sql.Date insertion_date;
	private Integer message_medium_id;
	private Integer target_recipient_only_mch;
	private Integer send_failed_count;
	private Integer send_success_count;
	private Integer recipient_records_count;

}
