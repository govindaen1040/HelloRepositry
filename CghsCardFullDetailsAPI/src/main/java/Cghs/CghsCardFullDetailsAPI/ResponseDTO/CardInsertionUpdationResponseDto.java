package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInsertionUpdationResponseDto {
	private String cardInsetionUpdationResponse;
	
	private String ben_id;

	private Integer card_no;

}
