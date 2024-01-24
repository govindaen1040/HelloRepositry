package Cghs.CghsCardFullDetailsAPI.ServiceImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Cghs.CghsCardFullDetailsAPI.CommonUtility.CghsCardFullDetailsValidation;
import Cghs.CghsCardFullDetailsAPI.CommonUtility.RecordLog;
import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;
import Cghs.CghsCardFullDetailsAPI.Dao.CghsCardInsertionDao;
import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;
import Cghs.CghsCardFullDetailsAPI.Model.IndexCardModel;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.BeneficiaryImageUploadDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyMemberDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.AddFamilyMemberResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.CardInsertionUpdationResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseAPIDetails;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseMetaData;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ServiceToControllerParams;
import Cghs.CghsCardFullDetailsAPI.Service.CghsCardInsertionService;

@Service
@javax.transaction.Transactional
public class CghsCardInsertionServiceImpl implements CghsCardInsertionService {
	@Autowired
	private CghsCardInsertionDao cghsCardInsertionDaoObj;
	@Autowired
	private ObjectMapper objMapper;
	
	@PersistenceContext
    private EntityManager entityManager;
	

	public ResponseAPIDetails insertCghsCard(CardFullDetailsDTO cardFullDetailsDTO, HttpServletRequest request) {
		java.util.Date start_execution_time = new java.util.Date();
		ResponseAPIDetails responseSave = cghsCardValidationAndSaveRecord(cardFullDetailsDTO);
		String jsonRequest;
		try {
			jsonRequest = objMapper.writeValueAsString(cardFullDetailsDTO);
			String apiResponse = objMapper.writeValueAsString(responseSave);
			Timestamp currentdateTime = new Timestamp(new Date().getTime());
			Long apiExecutionTime = new Date().getTime() - start_execution_time.getTime();
			String statuscode = "";
			String ipAdd = request.getRemoteAddr();
			String userId = "";
		//	CghsCardInsertionServiceImpl.log(new RecordLog(jsonRequest, currentdateTime, apiExecutionTime, apiResponse,
	//				statuscode, ipAdd, userId));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return responseSave;

	}

	public ResponseAPIDetails cghsCardValidationAndSaveRecord(CardFullDetailsDTO cardFullDetailsDTO) {
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
		ResponseMetaData apiData;

		String familyDetailsStatus = CghsCardFullDetailsValidation
				.cghsValidationMethodForFamilyDetails(cardFullDetailsDTO.getFamilyDetails());
//
		String mchAddressStatus = CghsCardFullDetailsValidation
				.cghsValidationMethodForCardHolderAddress(cardFullDetailsDTO.getCardHolderAddress());
//
		String indexCardStatus = CghsCardFullDetailsValidation
				.cghsValidationMethodForIndexCard(cardFullDetailsDTO.getIndexCard());
		
		
	//	String familyDetailsStatus="successfd";
		//String mchAddressStatus="successcha";//
		//String indexCardStatus="successic";//successfd//successcha

		if (mchAddressStatus.matches("successcha") && indexCardStatus.matches("successic")
				&& familyDetailsStatus.matches("successfd")) {

			try {
				int count = duplicateCheckMCH(cardFullDetailsDTO);
				if (count > 0) {
					// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Record already
					// exists");
					apiData = new ApiDetails().setapiData("v1", "F", "Record already exist");
					resTestObj.setApiDetails(apiData);

					return resTestObj;
//						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//								.body("Record already exists. BeneficaryId: "
//										+ cardFullDetailsDTO.getCardHolderAddress().getBen_id() + ",  Card Number :"
//										+ cardFullDetailsDTO.getCardHolderAddress().getCard_no() + ",  DispensaryCode :"
//										+ cardFullDetailsDTO.getCardHolderAddress().getDispensary_code() + "");

				} else {
					CardHolderAddressModel savedCardHolderAddressModel = cghsCardInsertionDaoObj
							.saveCardHolderAddress(cardFullDetailsDTO);

					FamilyDetailsModel savedFamilyDetailsModel = cghsCardInsertionDaoObj
							.saveFamilyDetails(cardFullDetailsDTO);

					IndexCardModel savedIndexCardModel = cghsCardInsertionDaoObj.saveIndexCard(cardFullDetailsDTO);

					String saveCardFullDetailsstatus;

					if (savedCardHolderAddressModel == null) {
						saveCardFullDetailsstatus = "Data not inserted in card_holder_address";
					} else if (savedFamilyDetailsModel == null) {
						saveCardFullDetailsstatus = "Data not inserted in family_details";
					}  else if (savedIndexCardModel == null) {
						saveCardFullDetailsstatus = "Data not inserted in index_card";
					}else {
						saveCardFullDetailsstatus = "Data Successfully inserted  in All three table";
						apiData = new ApiDetails().setapiData("v1", "S", "Success");
						resTestObj.setApiDetails(apiData);
						CardInsertionUpdationResponseDto cardInsertionUpdationResponseDto = new CardInsertionUpdationResponseDto();
						cardInsertionUpdationResponseDto.setCardInsetionUpdationResponse(saveCardFullDetailsstatus);
						cardInsertionUpdationResponseDto.setBen_id(savedCardHolderAddressModel.getBen_id());	
						cardInsertionUpdationResponseDto.setCard_no(savedCardHolderAddressModel.getCard_no());

						List<CardInsertionUpdationResponseDto> cardInsertionUpdationResponseDtoListObj = new ArrayList<>();
						cardInsertionUpdationResponseDtoListObj.add(cardInsertionUpdationResponseDto);

						ServiceToControllerParams serviceToControllerParams = new ServiceToControllerParams();
						serviceToControllerParams.setResult(cardInsertionUpdationResponseDtoListObj);
						resTestObj.setResult(serviceToControllerParams.getResult());
                        
					}
					apiData = new ApiDetails().setapiData("v1", "F", saveCardFullDetailsstatus);
					resTestObj.setApiDetails(apiData);


					// System.out.println(responseMetaData+"responseMetaData");
					// resTestObj.setApiDetails(responseMetaData);
					// List beneficiaryDetails = new ArrayList();
					// beneficiaryDetails.add("ben_id :"+savedCardHolderAddressModel.getBen_id());
					// beneficiaryDetails.add("card_no :"+savedCardHolderAddressModel.getCard_no());
					// responseAPIDetails.setInsertCardApiRespnose(beneficiaryDetails);

					return resTestObj;
					// return ResponseEntity.ok("" + saveCardFullDetailsstatus + "");

				}

			} catch (Exception e) {

				e.printStackTrace();
				// return ResponseEntity.ok("Exception " + e.getMessage() + "");
				return null;
			}

		} else {
			String errorMessage = "";
			if (!mchAddressStatus.matches("successcha")) {
				errorMessage += " validation Failed in card_holder_address " + mchAddressStatus + " ";
//				return ResponseEntity.ok(" validation Failed in card_holder_address " + mchAddressStatus + " ");
			}
			if (!indexCardStatus.matches("successic")) {
				errorMessage += " validation Failed  in index_card" + indexCardStatus + " ";
//				return ResponseEntity.ok(" validation Failed  in index_card" + indexCardStatus + " ");
			}
			if (!familyDetailsStatus.matches("successfd")) {
				errorMessage += " validation Failed in famliy_details " + familyDetailsStatus + " ";
//				return ResponseEntity.ok(" validation Failed in famliy_details " + familyDetailsStatus + " ");
			}
			// return ResponseEntity.ok("Error:" + errorMessage);

			apiData = new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", errorMessage);
			resTestObj.setApiDetails(apiData);
			return resTestObj;
		}
//		return ResponseEntity.ok("Error-------:" + "Null");

//		return null;

	}

	/**
	 * @param cardFullDetailsDTO
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int duplicateCheckMCH(CardFullDetailsDTO cardFullDetailsDTO) throws SQLException, ClassNotFoundException {
		Connection conn = JDBCConnection.getDBConnection();
//		String query = "select  count(*) from family_details fd, index_card ic, card_holder_address ch, \r\n"
//				+ "city_master cm where ic.ben_id=fd.id and fd.id=ch.ben_id and \r\n"
//				+ "ch.city_code= cm.city_code and fd.ben_id=? and fd.card_no=? and fd.dispensary_code=?\r\n"
//				+ "and ch.city_code=?";// failed due to id,ben Id,card no. generated
//
		String query = "select count(*) from family_details fd where \r\n"
				+ "fd.card_type=? and fd.dispensary_code=? and name=? and date_of_birth=?\r\n" + "";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getCard_type());
		ps.setString(2, cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDispensary_code());
		ps.setString(3, cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getName());
		ps.setDate(4, cardFullDetailsDTO.getFamilyDetails().getMainCardHolder().getDate_of_birth());
		ResultSet rs = ps.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		System.out.println("count--------->" + count);

		return count;
	}

	// save Recrod into Log table
	public static void log(RecordLog recordLog) {
		Connection conn = null;
		try {
			if (recordLog != null) {
				conn = new JDBCConnection().getDBConnection();
				String sql = "INSERT INTO cghs_insert_record_log(po_request, api_calling_date_time, api_execution_time, api_response,api_status_code,ipaddress, userid) values (?::JSON,?,?,?::JSON,?,?,?)";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, recordLog.getPo_request());
				st.setTimestamp(2, recordLog.getApi_calling_date_time());
				st.setLong(3, recordLog.getApi_execution_time());
				st.setString(4, recordLog.getApi_response());
				st.setString(5, recordLog.getApi_status_code());
				st.setString(6, recordLog.getCaller_ipaaddress());
				st.setString(7, recordLog.getUser_id());
				st.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	@Override
	public ResponseAPIDetails addBeneficiaryFamilyMemberDetails(FamilyDetailsModel familyDetailsModelObj, int i,
			FamilyMemberDto familyMemberDto, CardFullDetailsDTO cardFullDetailsDTO, HttpServletRequest request) {

		String checkValidationRespoonse = CghsCardFullDetailsValidation
				.cghsValidationMethodForFamilyMember(cardFullDetailsDTO);
		ResponseAPIDetails responseAPIDetails = new ResponseAPIDetails();
		ResponseMetaData apiData;
		try {
			if (checkValidationRespoonse.matches("successfd")) {
				FamilyDetailsModel familyDetailsModel = cghsCardInsertionDaoObj.saveFamilyMember(familyDetailsModelObj,
						i, familyMemberDto, cardFullDetailsDTO);
				
				if (familyDetailsModel == null) {
					apiData = new ApiDetails().setapiData("v1", "F", "Data not saved in Family_details table");
					responseAPIDetails.setApiDetails(apiData);
				}else {
					apiData = new ApiDetails().setapiData("v1", "S", "Success");
					responseAPIDetails.setApiDetails(apiData);
					AddFamilyMemberResponseDto addFamilyMemberResponseDto = new AddFamilyMemberResponseDto();
					addFamilyMemberResponseDto.setBenId(familyDetailsModel.getBen_id());

					List<AddFamilyMemberResponseDto> AddFamilyMemberResponseDtoListObj = new ArrayList<>();
					AddFamilyMemberResponseDtoListObj.add(addFamilyMemberResponseDto);
					ServiceToControllerParams serviceToControllerParams = new ServiceToControllerParams();
					serviceToControllerParams.setResult(AddFamilyMemberResponseDtoListObj);
					responseAPIDetails.setResult(serviceToControllerParams.getResult());
				}

				// List beneficiaryDetails = new ArrayList();
				// beneficiaryDetails.add("Famliy Member Added with BeneficiaryId :"
				// +familyDetailsModel.getBen_id());
				// responseAPIDetails.setInsertCardApiRespnose(beneficiaryDetails);

			} else {
				apiData = new ApiDetails().setapiData("v1", "F", checkValidationRespoonse);
				responseAPIDetails.setApiDetails(apiData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return responseAPIDetails;
	}

	@Override
	public ResponseAPIDetails updateBeneficiary() {

		return null;
	}

	@Override
	public ResponseAPIDetails addFamilyMember(FamilyDetailsModel familyDetailsModelObj, int i,
			FamilyMemberDto familyMemberDto, CardFullDetailsDTO cardFullDetailsDTO, HttpServletRequest request) {
		Long apiExecutionTime = null;
		String statuscode = null;
		String ipAdd = request.getRemoteAddr();
		String userId = null;
		String apiResponse = null;
		java.util.Date start_execution_time = new java.util.Date();
		ResponseAPIDetails responseAddMember = addBeneficiaryFamilyMemberDetails(familyDetailsModelObj, i,
				familyMemberDto, cardFullDetailsDTO, request);
		apiExecutionTime = new Date().getTime() - start_execution_time.getTime();
		String jsonRequest = null;
		try {
			jsonRequest = objMapper.writeValueAsString(cardFullDetailsDTO);
			apiResponse = objMapper.writeValueAsString(responseAddMember);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Timestamp currentdateTime = new Timestamp(new Date().getTime());
//		CghsCardInsertionServiceImpl.log(
//				new RecordLog(jsonRequest, currentdateTime, apiExecutionTime, apiResponse, statuscode, ipAdd, userId));

		return responseAddMember;
	}

	@Override
	public ResponseAPIDetails uploadBeneficiaryImage(BeneficiaryImageUploadDto beneficiaryImageUploadDto) {

		String result= callExistingImageUploadApi(beneficiaryImageUploadDto);
		String imagUploadApiResponse=null;
		result=result.trim();
		if(result.equals("Yes")){

			if("jpg".equals(beneficiaryImageUploadDto.getFileUploadType())){

			        String Query = "INSERT INTO FamilyIndexCardLog \" +\r\n"
			        		+ "                      \"(cardNo, cardType, dispensaryCode, patientNo, name, sex, relation, bloodGroup, \" +\r\n"
			        		+ "                      \"dateOfBirth, flag, deleteTransfer, id, benId, operator, modifyDate, \" +\r\n"
			        		+ "                      \"noOfBeneficiaries, departmentCode, icNo, zone, validFrom, validTo, address1, \" +\r\n"
			        		+ "                      \"address2, locality, cityCode, district, pinCode, officePhoneNo, residencePhoneNo, \" +\r\n"
			        		+ "                      \"email, remarks, ifDisabled, mobileNo, mobile, photoUpload) \" +\r\n"
			        		+ "                      \"SELECT fm.cardNo, fm.cardType, fm.dispensaryCode, fm.patientNo, fm.name, fm.sex, \" +\r\n"
			        		+ "                      \"fm.relation, fm.bloodGroup, fm.dateOfBirth, fm.flag, fm.deleteTransfer, fm.id, \" +\r\n"
			        		+ "                      \"fm.benId, ch.operator, fm.modifyDate, ic.noOfBeneficiaries, ic.departmentCode, \" +\r\n"
			        		+ "                      \"ic.icNo, ic.zone, ic.validFrom, ic.validTo, ch.address1, ch.address2, ch.locality, \" +\r\n"
			        		+ "                      \"ch.cityCode, ch.district, ch.pinCode, ch.officePhoneNo, ch.residencePhoneNo, \" +\r\n"
			        		+ "                      \"fm.email, fm.remarks, fm.ifDisabled, ch.mobileNo, fm.mobile, fm.photoUpload \" +\r\n"
			        		+ "                      \"FROM FamilyDetailsModel fm JOIN IndexCardModel ic ON ic.benId = fm.id \" +\r\n"
			        		+ "                      \"JOIN CardHolderAddressModel ch ON ch.benId = fm.id \" +\r\n"
			        		+ "                      \"WHERE fm.benId = :benId";

			        entityManager.createQuery(Query)
                    .setParameter("benId", beneficiaryImageUploadDto.getBenid())
                    .executeUpdate();
	
			}else {
				imagUploadApiResponse="Image content is not correct.";
	
			}
	
		}
		if("No".equals(result)){  
			imagUploadApiResponse="File has not been uploaded.";                 			
		}
		
		
		

		return null;
	}
	
	
	
	   String callExistingImageUploadApi(BeneficiaryImageUploadDto beneficiaryImageUploadDto){
		   
		   String apiUrl = "http://10.246.75.194/cghsapp/fileUploadService/uploadimage";
			
			 RestTemplate restTemplate = new RestTemplate();
			 String apiResponse="No";
			 org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		        headers.setContentType(MediaType.MULTIPART_MIXED);
		        headers.add("Authorization", "6DDGS&$H647678G7fh7jdhj@dfHDG67@332agddej3j3F#GDHH4784H");
		        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		        headers.add("Pragma", "no-cache");
		        headers.add("Expires", "0");
	 
		        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		        body.add("uploadedFile", new FileSystemResource(new File("path/to/your/file.jpg")));
		        body.add("ben_id", beneficiaryImageUploadDto.getBenid());
		        body.add("dispensary_code", beneficiaryImageUploadDto.getDispensary_code());
		        body.add("contentType", beneficiaryImageUploadDto.getContentType());
		        body.add("fileUploadType", beneficiaryImageUploadDto.getFileUploadType()); 
			
		        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		        // Make the API call with POST method
		        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

		        // Print the response
		        System.out.println("Response: " + response.getBody());
		        
		         apiResponse=response.getBody();
		
		return apiResponse;
	}

	
}
