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
@Table(name = "bulk_sms_type_master")
public class BulkSmsTypeMaster {
	@Id
	private Integer sms_type_code;
	private String sms_type_name;

}
