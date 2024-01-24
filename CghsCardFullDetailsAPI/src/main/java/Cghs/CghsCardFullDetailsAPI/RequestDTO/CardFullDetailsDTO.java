package Cghs.CghsCardFullDetailsAPI.RequestDTO;

import java.util.Date;

import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardFullDetailsDTO {
	
	private CardHolderAddressDto cardHolderAddress;

	private FamilyDetailsDto familyDetails;
	
	private IndexCardDto indexCard;
	
}
