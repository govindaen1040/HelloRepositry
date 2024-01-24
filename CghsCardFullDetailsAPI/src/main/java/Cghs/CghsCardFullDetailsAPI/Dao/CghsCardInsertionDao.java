package Cghs.CghsCardFullDetailsAPI.Dao;

import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;
import Cghs.CghsCardFullDetailsAPI.Model.IndexCardModel;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.BeneficiaryImageUploadDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardHolderAddressDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyDetailsDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyMemberDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.IndexCardDto;

public interface CghsCardInsertionDao {
	
	CardHolderAddressModel saveCardHolderAddress(CardFullDetailsDTO cardFullDetailsDTO);

	IndexCardModel saveIndexCard(CardFullDetailsDTO cardFullDetailsDTO);

	FamilyDetailsModel saveFamilyDetails(CardFullDetailsDTO cardFullDetailsDTO);
	
	FamilyDetailsModel saveFamilyMember(FamilyDetailsModel familyDetailsModelObj, int i,
			FamilyMemberDto familyMemberDto,CardFullDetailsDTO cardFullDetailsDTO);
	
	FamilyDetailsModel updateFamilyDetails(BeneficiaryImageUploadDto beneficiaryImageUploadDto);
	
	
	//FamilyDetailsModel saveFamilyDetails();
	
//	IndexCardModel saveIndexCArd();

}
