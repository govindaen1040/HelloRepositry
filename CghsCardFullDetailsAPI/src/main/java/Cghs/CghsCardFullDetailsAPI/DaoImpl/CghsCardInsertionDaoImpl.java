package Cghs.CghsCardFullDetailsAPI.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Cghs.CghsCardFullDetailsAPI.CommonUtility.CghsCardFullCommon;
import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;
import Cghs.CghsCardFullDetailsAPI.Dao.CghsCardInsertionDao;
import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;
import Cghs.CghsCardFullDetailsAPI.Model.IndexCardModel;
import Cghs.CghsCardFullDetailsAPI.Repositries.CardHolderAddressRepositry;
import Cghs.CghsCardFullDetailsAPI.Repositries.FamilyDetailsRepositry;
import Cghs.CghsCardFullDetailsAPI.Repositries.IndexCardRepositry;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.BeneficiaryImageUploadDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardHolderAddressDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyDetailsDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyMemberDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.IndexCardDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.MainCardHolderDto;

@Repository
//@Transactional
public class CghsCardInsertionDaoImpl implements CghsCardInsertionDao {
	@Autowired
	private CardHolderAddressRepositry cardHolderAddressRepositry;
	@Autowired
	private FamilyDetailsRepositry familyDetailsRepositry;
	@Autowired
	private IndexCardRepositry indexCardRepositry;
	@Autowired
	private ModelMapper modelMapperObj;

	@PersistenceContext
	private EntityManager entityManager;

	String ben_id = null;
	int card_no = 0;
	@Override
	public CardHolderAddressModel saveCardHolderAddress(CardFullDetailsDTO cardFullDetailsDTO) {
		CardHolderAddressDto cardHolderAddressDto = cardFullDetailsDTO.getCardHolderAddress();
		FamilyDetailsDto familyDetailsDto = cardFullDetailsDTO.getFamilyDetails();
		try {
			CardHolderAddressModel cardholderaddress = new CardHolderAddressModel();
			cardholderaddress.setAddress_1(cardHolderAddressDto.getAddress_1());
			cardholderaddress.setAddress_2(cardHolderAddressDto.getAddress_2());
			cardholderaddress.setLocality(cardHolderAddressDto.getLocality());
			cardholderaddress.setCity_code(cardHolderAddressDto.getCity_code());
			cardholderaddress.setPin_code(cardHolderAddressDto.getPin_code());
			cardholderaddress.setResidence_phone_no(cardHolderAddressDto.getResidence_phone_no());
			//Concatenate 91 with 10 digit mobile number
			cardholderaddress.setMobile_no("91"+familyDetailsDto.getMainCardHolder().getMobile());
			cardholderaddress.setDistrict(cardHolderAddressDto.getDistrict());
			cardholderaddress.setE_mail(cardHolderAddressDto.getE_mail());
			cardholderaddress.setPen_auto(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getPen_auto());
			cardholderaddress.setOperator(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getOperator());
			cardholderaddress.setCard_type(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getCard_type());
			cardholderaddress.setDispensary_code(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDispensary_code());
			ben_id = generateBeneficiaryId(familyDetailsDto);
			card_no = CghsCardFullCommon.generateCardNumber(familyDetailsDto);
			cardholderaddress.setBen_id(ben_id);
			cardholderaddress.setCard_no(card_no);
			
			//System.out.println("ben_id" + ben_id);
			//System.out.println("card_no" + card_no);
			
			//Getting from main card holder
			
//			Calendar calendar = Calendar.getInstance();
//			Date currentDateAndTime = calendar.getTime();
//			cardholderaddress.setModify_date(null);
//			cardholderaddress.setOff_address_1(cardHolderAddressDto.getOff_address_1());
//			cardholderaddress.setOff_address_2(cardHolderAddressDto.getOff_address_2());
//			cardholderaddress.setOff_pin_code(cardHolderAddressDto.getPin_code());
//			cardholderaddress.setOffice_phone_no(cardHolderAddressDto.getOffice_phone_no());
			//not null
		//	cardholderaddress.setOperator("");//when API User Login based then set User Id or Name of Logged in
			
		//	cardholderaddress.setE_mail(cardHolderAddressDto.getE_mail());
			System.out.println("cha----------->");
			return cardHolderAddressRepositry.save(cardholderaddress);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	// @Transactional
	public IndexCardModel saveIndexCard(CardFullDetailsDTO cardFullDetailsDTO) {
		IndexCardDto indexCard = cardFullDetailsDTO.getIndexCard();
		try {
			// IndexCardModel indexCardObj = this.modelMapperObj.map(indexCard,
			// IndexCardModel.class);
			IndexCardModel indexCardObj = new IndexCardModel();
			indexCardObj.setCard_type(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getCard_type());
			indexCardObj.setDispensary_code(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDispensary_code());
			indexCardObj.setNo_of_beneficiaries(indexCard.getNo_of_beneficiaries());
			indexCardObj.setValid_from(indexCard.getValid_from());// Take logic from cghs.nic.in and Discuss with Sarika mam ,DTO 
			indexCardObj.setValid_to(indexCard.getValid_to());// Take logic from cghs.nic.in and Discuss with Sarika mam ,DTO 
			indexCardObj.setDepartment_code(indexCard.getDepartment_code());	
			indexCardObj.setOperator(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getOperator());  // not null constrain
			indexCardObj.setIc_no(indexCard.getIc_no());
			indexCardObj.setEntitlement(indexCard.getEntitlement());
			indexCardObj.setCpao_verify_status(indexCard.getCpao_verify_status());//discussion
			indexCardObj.setCpao_ppo_no(indexCard.getCpao_ppo_no());//discussion
			indexCardObj.setCpao_ssa_no(indexCard.getCpao_ssa_no());//discussion
			indexCardObj.setCpao_cancel_remarks(indexCard.getCpao_cancel_remarks());//CPO cancel Remarks DTO
			indexCardObj.setSsaskip_remarks(indexCard.getSsaskip_remarks());// DTO value
			indexCardObj.setPayskip_remarks(indexCard.getPayskip_remarks());//DTO			
			indexCardObj.setPen_auto(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getPen_auto());	
			Calendar calendar = Calendar.getInstance();
			Date currentDateAndTime = calendar.getTime();
			indexCardObj.setInsertion_date(currentDateAndTime);
			indexCardObj.setCpao_insertion_date(currentDateAndTime);//discussion  in code it is now()	
			indexCardObj.setBen_id(ben_id);		
			indexCardObj.setCard_no(card_no);
			
			
			//not null	
			if(indexCard.getAllow_depnd_only()==null || indexCard.getAllow_depnd_only().matches("")) {
				//not null
				indexCardObj.setAllow_depnd_only("");// set default 0 or 1 as AD Provided  Take data from DTO	
			}else {
				indexCardObj.setAllow_depnd_only(indexCardObj.getAllow_depnd_only());        //not available in cardinsertionppo( method 
			}
	//		indexCardObj.setCard_val_change_flag(0);//default 0                    //not available in ppo( method
		//	indexCardObj.setCard_validity(null); // dicussion remain
		//	indexCardObj.setCompletion_of_deputation_date(null);//will discuss with pawan Sir    //NA
		//	indexCardObj.setCovid(indexCard.getCovid());                    //NA
			
		///	indexCardObj.setCpao_diary_no(indexCard.getCpao_diary_no());//CPO Diary no DTO		   NA
			
		//	indexCardObj.setDeputation_flag("");//discussion                      //NA
	//		indexCardObj.setDesignation_code(indexCard.getDesignation_code());     //NA
			
			
//			String cardType=cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDispensary_code();
//			if(cardType.matches("R") || cardType.matches("L")) {
//				indexCardObj.setIc_no(indexCard.getIc_no());//check beneficiary Card Type i.e R,L then set value here	
//			}else {
//				indexCardObj.setIc_no(null);
//			}
			
			
	//		indexCardObj.setIs_transferable(indexCard.getIs_transferable());// discuss with Sarika Mam and look its logic     //NA
	                //not null
					indexCardObj.setLast_pay(0);                       //NA
	//		indexCardObj.setModify_date(currentDateAndTime);// current time update API set its modify date            //NA
			
			
		
	//		indexCardObj.setPay_level(indexCard.getPay_level()); //NA
			//not null
			indexCardObj.setPay_scale(0);                     //NA
			
		//	not null
			indexCardObj.setPresent_pay(0);                    //NA
			//auto generate
			// indexCardObj.setSl_no(generateNextSlNoInIndexCard());
		
		
		//	indexCardObj.setZone(indexCard.getZone());
			System.out.println("ic---------->");//NA
			return indexCardRepositry.save(indexCardObj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public FamilyDetailsModel saveFamilyDetails(CardFullDetailsDTO cardFullDetailsDTO) {
		FamilyDetailsDto familyDetailsDto = cardFullDetailsDTO.getFamilyDetails();
		FamilyDetailsModel familyDetailsObj = new FamilyDetailsModel();
		MainCardHolderDto mainCardHolderDto = familyDetailsDto.getMainCardHolder();
		try {
			saveMainCardHolder(familyDetailsObj, mainCardHolderDto);

			if (familyDetailsDto.getMembers().size() != 0 && !familyDetailsDto.getMembers().isEmpty()) {
				for (int i = 0; i < familyDetailsDto.getMembers().size(); i++) {
					// familyDetailsObj = new FamilyDetailsModel();
					FamilyMemberDto familyMemberDto = familyDetailsDto.getMembers().get(i);
					familyDetailsObj = saveFamilyMember(familyDetailsObj, i, familyMemberDto, cardFullDetailsDTO);
				}
			}
			return familyDetailsObj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param familyDetailsModel
	 * @param mainCardHolderDto
	 */
	public FamilyDetailsModel saveMainCardHolder(FamilyDetailsModel familyDetailsModel,
			MainCardHolderDto mainCardHolderDto) {
		try {
			Calendar calendar = Calendar.getInstance();
			Date currentDateAndTime = calendar.getTime();
			familyDetailsModel.setId(ben_id);
			familyDetailsModel.setCard_type(mainCardHolderDto.getCard_type());
			familyDetailsModel.setName(mainCardHolderDto.getName());
			familyDetailsModel.setDate_of_birth(mainCardHolderDto.getDate_of_birth());
			familyDetailsModel.setSex(mainCardHolderDto.getSex());
			familyDetailsModel.setDispensary_code(mainCardHolderDto.getDispensary_code());
			familyDetailsModel.setRelation(1);
			familyDetailsModel.setPatient_no(1);   //by default it is 1 for main card holder
			
			familyDetailsModel.setOperator(mainCardHolderDto.getOperator());   //not null constrain
			familyDetailsModel.setPc_validity(mainCardHolderDto.getPc_validity());
			
			familyDetailsModel.setPen_auto(mainCardHolderDto.getPen_auto());
			familyDetailsModel.setNameh(mainCardHolderDto.getNameh());
			familyDetailsModel.setMobile(mainCardHolderDto.getMobile());
			familyDetailsModel.setInsertion_date(currentDateAndTime);
			familyDetailsModel.setBen_id(ben_id);
			familyDetailsModel.setCard_no(card_no);
			
			
			
	//		familyDetailsModel.setPc_valid_date((java.sql.Date) currentDateAndTime);	
			
			
			//not null	
			familyDetailsModel.setPrivilige("");	
		//	 familyDetailsModel.setAd_verify_date(mainCardHolderDto.getAd_verify_date());
//			familyDetailsModel.setBlood_group(mainCardHolderDto.getBlood_group());
			//not null
			familyDetailsModel.setCard_dis_flag("0");
//			familyDetailsModel.setCard_dist(null);
//		//	 familyDetailsModel.setDa_verify_date(mainCardHolderDto.getDa_verify_date());
			//not null
			familyDetailsModel.setDelete_transfer("0");		
//			familyDetailsModel.setE_mail(mainCardHolderDto.getE_mail());
//			familyDetailsModel.setEmail_consent(mainCardHolderDto.getEmail_consent());
//			// familyDetailsObj.setEmail_consent_verify_date(familyDetails.getMainCardHolder().getEmail_consent_verify_date());
//			familyDetailsModel.setFlag("0");
//			familyDetailsModel.setHealthid(mainCardHolderDto.getHealthid());
//			familyDetailsModel.setHealthidnumber(mainCardHolderDto.getHealthidnumber());
//			familyDetailsModel.setIf_disabled(0);
//			//
//			LocalTime localTime = LocalTime.now();
//			Time insertion_time = Time.valueOf(localTime);
//			familyDetailsModel.setInsertion_time(insertion_time);
//			 familyDetailsModel.setLink_status(mainCardHolderDto.getLink_status());
//			familyDetailsModel.setM_pin(mainCardHolderDto.getM_pin());
//			familyDetailsModel.setM_pin_token(mainCardHolderDto.getM_pin_token());	
		//	familyDetailsModel.setMobile_verified("");
//			Calendar calendar = Calendar.getInstance();
//			Date currentDateAndTime = calendar.getTime();
	//		familyDetailsModel.setModify_date(null);
			// System.out.println("ben_id"+ben_id);
			// System.out.println("card_no"+card_no);	
			//
			// familyDetailsObj.setPass_update_date(familyDetails.getMainCardHolder().getPass_update_date());
			// familyDetailsObj.setPassword(familyDetails.getPassword());
			
		
			//need discussion
//			if(mainCardHolderDto.getPen_auto()==1) {
//				familyDetailsModel.setPc_validity("F");
//			}else if(mainCardHolderDto.getDispensary_code().matches("CGHS")){
//				familyDetailsModel.setPc_validity("P");
//			}else {
//				familyDetailsModel.setPc_validity("");
//			}			
			//
			// familyDetailsObj.setPc_verify_date(familyDetails.getMainCardHolder().getPc_verify_date());
//			familyDetailsModel.setPhoto_upload("");
//			//not null
//			familyDetailsModel.setPrivilige("");
//			familyDetailsModel.setRemarks(null);
//			// familyDetailsModel.setSl_no(generateNextSlNoInFamilyDetails());
//			familyDetailsModel.setTransfer_flag("0");
		
			familyDetailsModel.setPc_applied_date(currentDateAndTime);
			// familyDetailsRepositry.flush();
			//hard Code ptype and operator
		//	CghsCardFullCommon.saveIntoPatientType(ben_id, "", "P");
			System.out.println("fd---------------------->");
			return familyDetailsRepositry.save(familyDetailsModel); 
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * @param familyDetailsModelObj
	 * @param i
	 * @param familyMemberDto
	 */

	public FamilyDetailsModel saveFamilyMember(FamilyDetailsModel familyDetailsModelObj, int i,
			FamilyMemberDto familyMemberDto, CardFullDetailsDTO cardFullDetailsDTO) {
		String ptye=null;
		try {
			Calendar calendar = Calendar.getInstance();
			Date currentDateAndTime = calendar.getTime();
			FamilyDetailsDto familyDetailsDto = cardFullDetailsDTO.getFamilyDetails();
			familyDetailsModelObj = new FamilyDetailsModel();
		  //  String generatedBenId=	generateBeneficiaryId(familyDetailsDto);	    
			familyDetailsModelObj.setName(familyMemberDto.getName());
			familyDetailsModelObj.setSex(familyMemberDto.getSex());// check validation
			familyDetailsModelObj.setDate_of_birth(familyMemberDto.getDate_of_birth());// validation DOB
			familyDetailsModelObj.setRelation(familyMemberDto.getRelation());
			familyDetailsModelObj.setTransfer_flag("0");//set these value when main card holder approved and want to transfer to other city
			familyDetailsModelObj.setInsertion_date(currentDateAndTime);
           //generate card no
			if (familyDetailsDto.getMainCardHolder().getBen_id() == null) {
				familyDetailsModelObj.setId(ben_id);
				familyDetailsModelObj.setCard_no(card_no);
				System.out.println("dispcode--------------------"+
						cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDispensary_code());
				
				familyDetailsModelObj.setDispensary_code(
						cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDispensary_code());
				familyDetailsModelObj
						.setCard_type(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getCard_type());
				//condition check ben_id
				familyDetailsModelObj.setBen_id(generateBeneficiaryId(familyDetailsDto));
			//	if(CghsCardFullCommon.getMaxumPatientNumber(ben_id)!=0) {
				System.out.println("MAx---------------->"+CghsCardFullCommon.getMaxumPatientNumber(ben_id)+1);
				familyDetailsModelObj.setPatient_no(CghsCardFullCommon.getMaxumPatientNumber(ben_id)+1);
			//	}
				if(familyMemberDto.getMobile()==null) {
					familyDetailsModelObj.setMobile(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getMobile());	
				}else {
					familyDetailsModelObj.setMobile(familyMemberDto.getMobile());
				}
				familyDetailsModelObj.setPen_auto(cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getPen_auto());
				
			} else {
				familyDetailsModelObj
						.setCard_no(CghsCardFullCommon.fetchCardNumberOfBeneficiary(familyDetailsDto.getMainCardHolder().getBen_id()));
				familyDetailsModelObj
						.setCard_type(CghsCardFullCommon.fetchCardTypeOfBeneficiary(familyDetailsDto.getMainCardHolder().getBen_id()));
				familyDetailsModelObj.setDispensary_code(
						CghsCardFullCommon.fetchDispensaryCodeOfBeneficiary(familyDetailsDto.getMainCardHolder().getBen_id()));
				familyDetailsModelObj.setId(familyDetailsDto.getMainCardHolder().getBen_id());
				//condiution change because it is already generating int Registration Bean
				familyDetailsModelObj.setBen_id(familyMemberDto.getBen_id());
				familyDetailsModelObj.setPatient_no(
						CghsCardFullCommon.getMaxumPatientNumber(familyDetailsDto.getMainCardHolder().getBen_id())+1);
				
				//if familyMemberDto.getMobile()==null then insert mobile no from fmaily_detials and save
				familyDetailsModelObj.setMobile(familyMemberDto.getMobile());
			 ptye=	CghsCardFullCommon.getPtypeOfMainCardHolder(familyDetailsDto.getMainCardHolder().getBen_id());
			 
			 familyDetailsModelObj.setNameh(familyMemberDto.getNameh());
	
			}
		//	familyDetailsModelObj.setPatient_no(familyMemberDto.getPatient_no());
			
			System.out.println("familyMemberDto.getRemarks()------------"+familyMemberDto.getRemarks());
			familyDetailsModelObj.setRemarks(familyMemberDto.getRemarks());
			
			
			familyDetailsModelObj.setBlood_group(familyMemberDto.getBlood_group());
			familyDetailsModelObj.setOperator("");      // not null constrain
			familyDetailsModelObj.setIf_disabled(familyMemberDto.getIf_disabled());
			//not null
			familyDetailsModelObj.setDelete_transfer("0");//set this flag as per main card holder
			//not null
			familyDetailsModelObj.setCard_dis_flag("0");
			//not null
			familyDetailsModelObj.setPrivilige("");// set as per AD Verification i.e AD,DA
			familyDetailsModelObj.setFlag("0");
			//need discussion
			//familyDetailsModelObj.setPc_validity(CghsCardFullCommon.getPcValidity(familyDetailsDto.getMainCardHolder().getBen_id()));
			familyDetailsModelObj.setPc_validity(familyMemberDto.getPc_validity());
			familyDetailsModelObj.setPc_valid_date((java.sql.Date) familyMemberDto.getPc_valid_date());		
			// Calendar calendar = Calendar.getInstance();
			// Date currentDateAndTime = calendar.getTime();
			//not null
			familyDetailsModelObj.setPc_applied_date(currentDateAndTime);
			familyDetailsModelObj.setIf_disabled(0);// if AD verify its handicaped/Physically handicaped then 1 
		
			// ------
			// System.out.println("Member Number >>" + i);
			// System.out.println("Name>>" + familyMemberDto.getName());
			// familyDetailsRepositry.flush();
			
	//		String operator="";
			//save into patient_type master
		//	CghsCardFullCommon.saveIntoPatientType(generatedBenId,operator,ptye);
			System.out.println("save family member method end");
			return familyDetailsRepositry.save(familyDetailsModelObj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}

	}



	// Generate Ben_id based on Conditions
	public String generateBeneficiaryId(FamilyDetailsDto familyDetailsDto) {
		String cardType = null;
		if (familyDetailsDto.getMainCardHolder().getBen_id() == null) {
			cardType = familyDetailsDto.getMainCardHolder().getCard_type();
		} else {
			cardType = CghsCardFullCommon.fetchCardTypeOfBeneficiary(familyDetailsDto.getMainCardHolder().getBen_id());
		}
		System.out.println("cardType" + cardType);
		ResultSet rs = null;
		PreparedStatement pst = null;
		String ben_id = null;
		try {
			Connection con = JDBCConnection.getDBConnection();

			if (cardType.equals("L") || cardType.equals("R") || cardType.equals("X")) {

				pst = con.prepareStatement(
						"update max_ben_id set ben_id=ben_id::bigint+1 where card_type='M' returning ben_id");
				rs = pst.executeQuery();
				if (rs.next()) {
					ben_id = rs.getString("ben_id");
				}

			} else {

				pst = con.prepareStatement(
						"update max_ben_id set ben_id=ben_id::bigint+1 where card_type='O' returning ben_id");
				rs = pst.executeQuery();
				if (rs.next()) {
					ben_id = rs.getString("ben_id");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ben_id;

	}

	@Override
	public FamilyDetailsModel updateFamilyDetailsTableBasedOnBenId(
			BeneficiaryImageUploadDto beneficiaryImageUploadDto) {
		
	    Optional<FamilyDetailsModel>  familyDetails= 	familyDetailsRepositry.findById(beneficiaryImageUploadDto.getBenid());
		
	  if(familyDetails.isPresent()) {
			Calendar calendar = Calendar.getInstance();
			Date currentDateAndTime = calendar.getTime();
		 FamilyDetailsModel familyDetailModelObj=     familyDetails.get();
		 
		 familyDetailModelObj.setPhoto_upload("1");
		 
		 familyDetailModelObj.setModify_date(currentDateAndTime);
		 
		 familyDetailModelObj.setOperator("testing");
		 
		 familyDetailsRepositry.save(familyDetailModelObj);
		  }
	    
	    
	    
	
		return null;
	}


}
