package Cghs.CghsCardFullDetailsAPI.CommonUtility;

import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;
import Cghs.CghsCardFullDetailsAPI.Model.IndexCardModel;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardHolderAddressDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyDetailsDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyMemberDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.IndexCardDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.MainCardHolderDto;

public class CghsCardFullDetailsValidation {
//	static String response = null;
	public static String cghsValidationMethodForFamilyDetails(FamilyDetailsDto familyDetailsDto) {
		String responsefd=null;
		MainCardHolderDto mainCardHolderDto = familyDetailsDto.getMainCardHolder();
		if (mainCardHolderDto.getCard_type() == null || mainCardHolderDto.getCard_type().length() > 2) {
			responsefd = "card_type should not be empty and length should not be more than 2";
		}else if (mainCardHolderDto.getName() == null || mainCardHolderDto.getName().length() > 100) {
			responsefd = "name should not empty and length should not exceed more than 100";
		}else if(mainCardHolderDto.getDate_of_birth()==null){
			
			responsefd="Date_of_birth should not be null";
		}else if(mainCardHolderDto.getSex()==null){
			
			responsefd="Gender should not be null";
		}
		
		else if (mainCardHolderDto.getDispensary_code() == null
				|| mainCardHolderDto.getDispensary_code().length() > 10) {
			responsefd = "dispensary_code should not be empty and length should not exceed more than 10";
		} /*
			 * else if (mainCardHolderDto.getOperator() == null ) { responsefd =
			 * "Operator should not be empty "; }
			 *//*
				 * else if(mainCardHolderDto.getPen_auto()==0){
				 * responsefd="pen_auto should not be empty"; }
				 */else if(mainCardHolderDto.getPc_validity()==null){
			responsefd="pc_validity should not be empty";
		}else if (mainCardHolderDto.getNameh().length() > 50) {
			responsefd = "nameh length should not exceed more than 50";
		}else if (mainCardHolderDto.getMobile().length() > 10) {
			responsefd = "mobile length should not exceed more than 10";
		}  
		
		
		/*
			 * else if(mainCardHolderDto.getPatient_no()==0) {
			 * responsefd="patient_no should not be empty"; }
			 */
		/*
		 * else if (mainCardHolderDto.getSex() == null ||
		 * mainCardHolderDto.getSex().length() > 2) { responsefd =
		 * "gender should not be empty and length should not exceed more than 2"; }else
		 * if (mainCardHolderDto.getRelation() == null) { responsefd =
		 * "relation should not be empty"; }else if
		 * (mainCardHolderDto.getE_mail().length() > 100) { responsefd =
		 * "e_mail length should not exceed more than 100"; }
		 */  /*
			 * else if (mainCardHolderDto.getAadhaar_verified_by() == null ||
			 * mainCardHolderDto.getAadhaar_verified_by().length() > 100) { responsefd =
			 * "aadhaar_verified_by length should not exceed more than 100"; }
			 *//*
				 * else if (mainCardHolderDto.getRemarks().length() > 100) { responsefd =
				 * "remarks length should not exceed more than 100"; }
				 *//*
			 * else if (mainCardHolderDto.getCard_dis_flag().length() > 2) { responsefd =
			 * "Card_dis_flag should not be more than 2"; }
			 *//*
				 * else if (mainCardHolderDto.getPc_applied_date() == null) { responsefd =
				 * "pc_applied_date should not empty"; }
				 */ /*
					 * else if (mainCardHolderDto.getPatient_no() == 0) { responsefd =
					 * "patient_no should not empty"; }
					 */ else {
			responsefd = "successfd";
		}

		if (familyDetailsDto.getMembers().size() != 0 && !familyDetailsDto.getMembers().isEmpty()) {
			for (int i = 0; i < familyDetailsDto.getMembers().size(); i++) {
				FamilyMemberDto familyMemberDto1 = familyDetailsDto.getMembers().get(i);
//				if (familyMemberDto1.getName() == null || familyMemberDto1.getName().length() > 100) {
//					responsefd = "name of family member should not empty and length should not exceed more than 100";
//				} else if (familyMemberDto1.getSex() == null || familyMemberDto1.getSex().length() > 2) {
//					responsefd = "Gender of family member should not be empty and length should not exceed more than 2";
//				} else if (familyMemberDto1.getRelation() == null) {
//					responsefd = "relation of family member should not be empty";
//				}else {
//					responsefd = "successfd";
//				}
			}
		}
		return responsefd;
	}

	public static String cghsValidationMethodForIndexCard(IndexCardDto indexCardDto) {
		String responseic=null;

		 if (indexCardDto.getNo_of_beneficiaries() == null
				|| indexCardDto.getNo_of_beneficiaries().length() > 11) {
			responseic = "no_of_beneficiaries should not be empty and length should not be more than 11";
		}else if(indexCardDto.getValid_from()==null){
			responseic="Valid_from should not be empty";			
		}else if(indexCardDto.getValid_to()==null) {
			responseic=	"should not be empty";
		}	 
		 else if (indexCardDto.getDepartment_code() == null) {
			responseic = "department_code should not be Empty";
		}else if(indexCardDto.getIc_no()==null) {
			responseic="Ic_no should not be empty";
		}else if (indexCardDto.getEntitlement().length() > 50) {
			responseic = "entitlement length should not be more than 50";
		}else if(indexCardDto.getCpao_verify_status()==null) {
			responseic="Cpao_verify_status  should not be empty";
		}else if(indexCardDto.getCpao_ppo_no()==null) {
			responseic="Cpao_ppo_no  should not be empty";
		}else if(indexCardDto.getCpao_ssa_no()==null) {
			responseic="Cpao_ssa_no should not be empty";
		}else if(indexCardDto.getCpao_cancel_remarks()==null) {
			responseic="Cpao_cancel_remarks  should not be empty";
		}else if(indexCardDto.getSsaskip_remarks()==null) {
			responseic="Ssaskip_remarks should not be empty";
		}else if(indexCardDto.getPayskip_remarks()==null) {
			responseic="Payskip_remarks should not be empty";
		}
		 
		 
		 /*
			 * else if (indexCardDto.getPay_scale() == null) { responseic =
			 * "pay_scale shouuld not be empty"; }
			 */ /*else if (indexCardDto.getPresent_pay() == null) {
			responseic = "present_pay should not be empty";
		}*/ /*
			 * else if (indexCardDto.getLast_pay() == null) { responseic =
			 * "last_pay should not be empty"; }
			 *//*
				 * else if (indexCardDto.getZone().length() > 12) { responseic =
				 * "zone length should not be more than  12"; }
				 */ else {
			responseic = "successic";
		}
		return responseic;
	}

	public static String cghsValidationMethodForCardHolderAddress(CardHolderAddressDto cardHolderAddressDto) {
    String responsecha=null;

	  if (cardHolderAddressDto.getCity_code() == null || cardHolderAddressDto.getCity_code().length() > 5) {
			responsecha = "city_code should not be empty and length should not more than 5";
		} else if (cardHolderAddressDto.getDistrict() == null || cardHolderAddressDto.getDistrict().length() > 30) {
			responsecha = "district should not be empty and legth should not be more than 30";
		} else if (cardHolderAddressDto.getPin_code()==null || cardHolderAddressDto.getPin_code().length() > 7) {
			responsecha = "pin_code length should not be more than 7";
		} else if (cardHolderAddressDto.getAddress_1() == null || cardHolderAddressDto.getAddress_1().length() > 200) {
			responsecha = "address_1 should not empty and  address_1 length should not be more than 200";
		} else if (cardHolderAddressDto.getAddress_2() == null || cardHolderAddressDto.getAddress_2().length() > 100) {
			responsecha = "address_2 length should not be more than 100";
		} else if (cardHolderAddressDto.getLocality() == null || cardHolderAddressDto.getLocality().length() > 50) {
			responsecha = "locality length should not be more than 50";
		}else if (cardHolderAddressDto.getResidence_phone_no()==null ||
				  cardHolderAddressDto.getResidence_phone_no().length() > 11) { responsecha =
				  "residence_phone_no length should not be more than 11 or not be empty"; 
		} /*
			 * else if (cardHolderAddressDto.getOperator() == null ||
			 * cardHolderAddressDto.getOperator().length() > 30) { responsecha =
			 * "operator should not empty and length should not be more than 30"; }
			 */
				  else if (cardHolderAddressDto.getMobile_no() == null ||
						  cardHolderAddressDto.getMobile_no().length() > 20) { responsecha =
						  "mobile_no length should not be more than 10"; }
				  else if (cardHolderAddressDto.getE_mail().length() > 100) {
					  responsecha = "e_mail length should not exceed more than 100";
					}
	  /*
			 * else if (cardHolderAddressDto.getOffice_phone_no()==null ||
			 * cardHolderAddressDto.getOffice_phone_no().length() > 11) { responsecha =
			 * "office_phone_no length should not be more than 11 or should not be empty";
			 * 
			 * }
			 */ 
				  
				 
			 
//			  else if (cardHolderAddressDto.getOff_pin_code() == null
//				|| cardHolderAddressDto.getOff_pin_code().length() > 7) {
//			responsecha = "off_pin_code length should not be more than 7";
//		} 
//			  
//			  else if (cardHolderAddressDto.getOff_address_1() == null
//				|| cardHolderAddressDto.getOff_address_1().length() > 100) {
//			responsecha = "off_address_1 length should not be more than 100";
//		} else if (cardHolderAddressDto.getOff_address_2() == null
//				|| cardHolderAddressDto.getOff_address_2().length() > 100) {
//			responsecha = "off_address_2 should not empty and length should not be more than 100";
//		}

		else {
			responsecha = "successcha";
		}

		return responsecha;
	}
	
	public static String cghsValidationMethodForFamilyMember(CardFullDetailsDTO cardFullDetailsDTO) {
		String responsefd=null;
		if (cardFullDetailsDTO.getFamilyDetails().getMembers().size() != 0 && !cardFullDetailsDTO.getFamilyDetails().getMembers().isEmpty()) {
			for (int i = 0; i < cardFullDetailsDTO.getFamilyDetails().getMembers().size(); i++) {
				FamilyMemberDto familyMemberDto1 = cardFullDetailsDTO.getFamilyDetails().getMembers().get(i);
				if (familyMemberDto1.getName() == null || familyMemberDto1.getName().length() > 100) {
					responsefd = "name of family member should not empty and length should not exceed more than 100";
				} else if (familyMemberDto1.getSex() == null || familyMemberDto1.getSex().length() > 2) {
					responsefd = "Gender of family member should not be empty and length should not exceed more than 2";
				} else if (familyMemberDto1.getRelation() == null) {
					responsefd = "relation of family member should not be empty";
				}else {
					responsefd = "successfd";
				}
			}
		}
		
		
		return responsefd;
		
		
		
	}
	
	
	static String blanknull(String s) {
		if ((s == null) || (s.equals("null"))) {
			s = "";
		}
		return s;
	}

}
