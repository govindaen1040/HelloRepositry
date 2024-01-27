package Cghs.CghsCardFullDetailsAPI.Service;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;
import Cghs.CghsCardFullDetailsAPI.Model.IndexCardModel;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.BeneficiaryImageUploadDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyDetailsDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyMemberDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.MainCardHolderDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseAPIDetails;

public interface CghsCardInsertionService {

	ResponseAPIDetails  insertCghsCard(CardFullDetailsDTO cardFullDetailsDTO,HttpServletRequest request);
	
	ResponseAPIDetails addBeneficiaryFamilyMemberDetails(FamilyDetailsModel familyDetailsModelObj, int i,
			FamilyMemberDto familyMemberDto,CardFullDetailsDTO cardFullDetailsDTO,HttpServletRequest request);
	
	ResponseAPIDetails addFamilyMember(FamilyDetailsModel familyDetailsModelObj, int i,
			FamilyMemberDto familyMemberDto,CardFullDetailsDTO cardFullDetailsDTO,HttpServletRequest request);
	
	ResponseAPIDetails updateBeneficiary();

	String uploadBeneficiaryImage(BeneficiaryImageUploadDto beneficiaryImageUploadDto);
	
	
	
	

}
