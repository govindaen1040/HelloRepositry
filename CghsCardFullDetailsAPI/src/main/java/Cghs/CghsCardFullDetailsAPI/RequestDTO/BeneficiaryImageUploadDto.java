package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryImageUploadDto {

	private String benid;
	private String dispensary_code;	  
	private String contentType;
	private String fileUploadType;
	private MultipartFile imageFile;
}
