package Cghs.CghsCardFullDetailsAPI.ServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Cghs.CghsCardFullDetailsAPI.CommonUtility.CghsCardFullCommon;
import Cghs.CghsCardFullDetailsAPI.CommonUtility.CghsSms;
import Cghs.CghsCardFullDetailsAPI.CommonUtility.CghsWebinarSendSmsValidation;
import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;
import Cghs.CghsCardFullDetailsAPI.Dao.BulkSmsRequestDetailsDao;
import Cghs.CghsCardFullDetailsAPI.Model.BulkSmsRequestDetailsModel;
import Cghs.CghsCardFullDetailsAPI.Repositries.BulkSmsRequestDetailsRepositries;
import Cghs.CghsCardFullDetailsAPI.Repositries.BulkSmsTypeMasterRespositry;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.SaveBulkRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.ExtractMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.TestMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.BulkSmsTypeMasterResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ExtractMobileNumberResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.FreezeContentResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseAPIDetails;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseMetaData;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.SaveBulkRequestResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.SendSmsResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ServiceToControllerParams;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ShowBulkRequestResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.SmsContentResponseDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.TestMobileNumberResponseDto;
import Cghs.CghsCardFullDetailsAPI.Service.CghsSendSmsWebinarService;


@Service
public class CghsSendSmsWebinarServiceImpl implements CghsSendSmsWebinarService {
	@Autowired
	private BulkSmsRequestDetailsRepositries bulkSmsRequestDetailsRepositries;
    @Autowired
	private BulkSmsRequestDetailsDao bulkSmsRequestDetailsDao;
	
	@Autowired
	private BulkSmsTypeMasterRespositry bulkSmsTypeMasterRespositry;
	
	@Override
	public ResponseAPIDetails saveBulkSmsRequest(SaveBulkRequestDto cghsWebinarDto, HttpServletRequest request) {
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
		ResponseMetaData apiData;	
		String saveBulkSmsRequestResponse = null;
		java.sql.Timestamp currentTimestamp = null;
		Connection connection = null;
		try {
			connection = JDBCConnection.getDBConnection();
			int bulk_sms_request_id = 0;

			String validationMethodResponse = CghsWebinarSendSmsValidation.bulksmsRequestValidation(cghsWebinarDto);
			currentTimestamp = new java.sql.Timestamp(new Date().getTime());
			if (validationMethodResponse.matches("success")) {
				Optional<BulkSmsRequestDetailsModel> existingEntityBulkSmsRequestDetails = bulkSmsRequestDetailsRepositries
						.findBySmsContentOrWebinarSeriesOrSmsSendTargetDate(cghsWebinarDto.getSms_content(),
								cghsWebinarDto.getWebinar_series(), cghsWebinarDto.getSms_send_target_date());

				if (existingEntityBulkSmsRequestDetails.isPresent()) {
					saveBulkSmsRequestResponse = "Record Already exist";
					System.out.println("checkDuplicacy------------->" + saveBulkSmsRequestResponse);

					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", saveBulkSmsRequestResponse);
					resTestObj.setApiDetails(apiData);
					return resTestObj;
					
				} else {
					String sqlQuery = "INSERT INTO bulk_sms_request_details(sms_content,template_id,webinar_series,sms_type_code,sms_send_target_date,ipaddress,sms_send_status_code,insertion_date,target_recipient_groups,target_recipient_only_mch,message_medium_id) VALUES (?,?,?,?,?,?,?,?,?,?,?) returning bulk_sms_request_id";
					PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
					preparedStatement.setString(1, cghsWebinarDto.getSms_content());
					preparedStatement.setString(2, cghsWebinarDto.getTemplate_id());
					preparedStatement.setString(3, cghsWebinarDto.getWebinar_series());
					preparedStatement.setInt(4, cghsWebinarDto.getSms_type_code());
					preparedStatement.setDate(5, cghsWebinarDto.getSms_send_target_date());
					preparedStatement.setString(6, request.getRemoteAddr());
					preparedStatement.setInt(7, 1); // 1 means save in to draft
					preparedStatement.setTimestamp(8, currentTimestamp);
					preparedStatement.setInt(9, cghsWebinarDto.getTarget_recipent_group());
					preparedStatement.setInt(10, cghsWebinarDto.getTarget_recipient_only_mch());
					preparedStatement.setInt(11, 1);
					ResultSet rs = preparedStatement.executeQuery();
					if (rs.next()) {
						bulk_sms_request_id = rs.getInt("bulk_sms_request_id");
						apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
						resTestObj.setApiDetails(apiData);
						ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
						SaveBulkRequestResponseDto SaveBulkResponseDto=new SaveBulkRequestResponseDto();
						SaveBulkResponseDto.setBulk_sms_request_id(bulk_sms_request_id);
						SaveBulkResponseDto.setInsertion_date(currentTimestamp);
						
						List<SaveBulkRequestResponseDto> saveBulkResponseDtoListObj=new ArrayList<>();
						saveBulkResponseDtoListObj.add(SaveBulkResponseDto);
						serviceToControllerParams.setResult(saveBulkResponseDtoListObj);
						resTestObj.setResult(serviceToControllerParams.getResult());
					return resTestObj;
					}
				}
			} else {
				apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", validationMethodResponse);		
				resTestObj.setApiDetails(apiData);
			}
		} catch (Exception e) {
	
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", e.getMessage());		
			resTestObj.setApiDetails(apiData);

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resTestObj;
	}

	@Override
	public ResponseAPIDetails extractMobileNumber(ExtractMobileNumberRequestDto extractMobileNumberDto) {
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
		ResponseMetaData apiData;
		String mobileExtractionResponse = null;
		String extractMobileNumberValidationResponse = CghsWebinarSendSmsValidation
				.extractMobileNumberApiValidatoin(extractMobileNumberDto);
		if (extractMobileNumberValidationResponse.matches("success")) {
			Connection connection = null, connection2 = null, connection3 = null;
			int relation = extractMobileNumberDto.getTarget_recipient_only_mch();
			;
			String selectQuery = null;
			PreparedStatement updateStatement = null;
			try {
				if (relation == 1) {
					selectQuery = "select distinct(mobile),ben_id,name,sex from family_details where relation=1 and delete_transfer!='D' and mobile not in('9999999999','0000000000') and mobile!='' and length(mobile)=10";
				} else {
					selectQuery = "select distinct(mobile),ben_id,name,sex from family_details where delete_transfer!='D' and mobile not in('9999999999','0000000000') and mobile!='' and length(mobile)=10";
				}

				connection = JDBCConnection.getDBConnection();

				connection2 = JDBCConnection.getDBConnection();

				connection3 = JDBCConnection.getDBConnection();
	
				String insertQuery = "INSERT INTO bulk_sms_recipient_list (bulk_sms_request_id,mobile_no,ben_id,name,gender,insertion_date,sent_sms_flag) VALUES (?,?,?,?,?,?,?)";

				String updateQuery = "update bulk_sms_request_details set sms_send_status_code=?,recipient_records_count=? where bulk_sms_request_id=?";

				PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
				PreparedStatement insertStatement = connection2.prepareStatement(insertQuery);
				updateStatement = connection3.prepareStatement(updateQuery);
				ResultSet rs = selectStatement.executeQuery();
				int count = 0;
				while (rs.next()) {
					String mobile_no = rs.getString("mobile");
					String ben_id = rs.getString("ben_id");
					String name = rs.getString("name");
					String gender = rs.getString("sex");
					insertStatement.setInt(1, extractMobileNumberDto.getBulk_sms_request_id());
					insertStatement.setString(2, mobile_no);
					insertStatement.setString(3, ben_id);
					insertStatement.setString(4, name);
					insertStatement.setString(5, gender);
					insertStatement.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
					insertStatement.setInt(7, 0);
					insertStatement.executeUpdate();

					count++;
				}

				if (count > 0) {
					updateStatement.setInt(1, 3);
					updateStatement.setInt(2, count);
					updateStatement.setInt(3, extractMobileNumberDto.getBulk_sms_request_id());
					updateStatement.executeUpdate();
					// System.out.println("Mobile number successfully inserted into table with " +
					// count + " records");
					mobileExtractionResponse = "Mobile number successfully inserted into table with " + count
							+ " records";
					
					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
					resTestObj.setApiDetails(apiData);
					
					ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
					List<ExtractMobileNumberResponseDto> extractMobileNumberResponseDtoListObj=new ArrayList<>();
					ExtractMobileNumberResponseDto extractMobileNumberResponseDto=new ExtractMobileNumberResponseDto();
					extractMobileNumberResponseDto.setExtractMobileNumberResposne(mobileExtractionResponse);
					extractMobileNumberResponseDtoListObj.add(extractMobileNumberResponseDto);
					
					serviceToControllerParams.setResult(extractMobileNumberResponseDtoListObj);
					resTestObj.setResult(extractMobileNumberResponseDtoListObj);
					
					return resTestObj;
				} else {
				//	System.out.println("Mobile number not found");
					mobileExtractionResponse = "Mobile not found";
					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", mobileExtractionResponse);		
					resTestObj.setApiDetails(apiData);
					return resTestObj;
				}

			} catch (Exception e) {

				//e.printStackTrace();
				apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", e.getMessage());		
				resTestObj.setApiDetails(apiData);
				return resTestObj;	
		
			} finally {
				try {
					connection.close();
					connection2.close();
					connection3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			mobileExtractionResponse = extractMobileNumberValidationResponse;
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", mobileExtractionResponse);		
			resTestObj.setApiDetails(apiData);
			return resTestObj;	
			
			
		}
	
	}

	@Override
	public ResponseAPIDetails sendSmsToBeneficiary(int bulk_sms_request_id, String mobile_no) {
		String sendSmsApiResponse = null;
		Connection connection = null;
		Connection Connection2 = null;
		Connection Connection3 = null;
		int sendSmsSuccesscount = 0;
		int sendSmsFailedcount = 0;
		int apiHitCount = 0;
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
        ResponseMetaData apiData;
	//	String optionalTestMObileNumber = mobile_no;
		try {
			connection = JDBCConnection.getDBConnection();
			// Connection2 = JDBCConnection.getDBConnection();
			// Connection3 = JDBCConnection.getDBConnection();
			// if(mobile_no==null) {
			 CghsWebinarSendSmsValidation.updateStatusCodeInBulkSmsRequest(bulk_sms_request_id, 4);// update sms_send_status_code 4 Ready to send
			// 
			// 
			// }

			String selectQueryForMobile = "select mobile_no from bulk_sms_recipient_list where sent_sms_flag=? and bulk_sms_request_id=? ";
			// String selectQueryForMobile="select * from sms_bulk_list order by id limit
			// 5";// for test

			String smsContentSelectQuery = "select sms_content,template_id from bulk_sms_request_details where sms_send_status_code=? and bulk_sms_request_id=?";
			String smsContent = null;
			String dlt_template_id = null;

			PreparedStatement selectMobileStatement = connection.prepareStatement(selectQueryForMobile);
			PreparedStatement smsContentStatemnet = connection.prepareStatement(smsContentSelectQuery);
			smsContentStatemnet.setInt(1, 4);
			smsContentStatemnet.setInt(2, bulk_sms_request_id);
			ResultSet smsContentResultset = smsContentStatemnet.executeQuery();

//			String status="select sms_send_status_code from bulk_sms_request_details where bulk_sms_request_id";
//			PreparedStatement statusPs = connection.prepareStatement(status);
//			statusPs.setInt(1, bulk_sms_request_id);
//			
//			ResultSet rs=statusPs.executeQuery();
//			while(rs.next()) {
//			int smsCodeStatus=	rs.getInt("sms_send_status_code");
//				System.out.println("smsCodeStatus>>"+smsCodeStatus);
//			}

			ResultSet rsMobileNumber = null;
			while (smsContentResultset.next()) {
				smsContent = smsContentResultset.getString("sms_content");
				dlt_template_id = smsContentResultset.getString("template_id");
				// System.out.println("smsContent" + smsContent);
				// System.out.println("dlt_template_id" + dlt_template_id);
				selectMobileStatement.setInt(1, 0);
				selectMobileStatement.setInt(2, bulk_sms_request_id);
				rsMobileNumber = selectMobileStatement.executeQuery();
			}
			apiHitCount++;
			// System.out.println("api hit cont "+apiHitCount);

			// if (apiHitCount == 1) {
			synchronized (this) {

				System.out.println("first entered>>" + apiHitCount);
				if ((rsMobileNumber != null)/* || optionalTestMObileNumber!=null */ ) {
					while (rsMobileNumber.next()/* || optionalTestMObileNumber!=null */) {

						// if(optionalTestMObileNumber==null || optionalTestMObileNumber=="") {
						String mobilenumber = rsMobileNumber.getString("mobile_no").trim();
						// }else {
						// mobilenumber=optionalTestMObileNumber;
						// }

						// System.out.println("mobile number from sensms table"+mobilenumber);
						CghsSms cghsSms = new CghsSms();
					//	String sendSmsResponse = "API000";
				//		Thread.sleep(50000);
						 String sendSmsResponse = cghsSms.sendSms(mobilenumber, smsContent, dlt_template_id, 0);
						if (sendSmsResponse.equals("API000")) {
							// if(mobile_no==null) {
							CghsWebinarSendSmsValidation.updateStatusCodeInBulkSmsRequest(bulk_sms_request_id, 5);// 5
																													// Sending
							// }

							// Thread.sleep(2000);
							String upateSmstableQuery = "update bulk_sms_recipient_list set sent_sms_flag=?,sms_sent_date_time=? where mobile_no=? and bulk_sms_request_id=?";
							PreparedStatement updateStatement = connection.prepareStatement(upateSmstableQuery);
							updateStatement.setInt(1, 1);
							updateStatement.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
							updateStatement.setString(3, mobilenumber);
							updateStatement.setInt(4, bulk_sms_request_id);
							int isTableUpdated = updateStatement.executeUpdate();
							// int isTableUpdated=0;
							if (isTableUpdated > 0) {
								// System.out.println("sms_send_mobile table has been updated and sms has been
								// sent");
								// sendSmsApiResponse="sms has been sent";
							} else {
								System.out.println("sms send but table not updated ");
								// sendSmsApiResponse="sms send but table not updated ";
							}
							sendSmsSuccesscount++;
							// System.out.println("count of success.."+sendSmsSuccesscount);
							// if(mobile_no==null) {
							// CghsWebinarSendSmsValidation.updateSuccesFailedCountInBulkSmsRequest(bulk_sms_request_id,
							// sendSmsSuccesscount, sendSmsFailedcount);
							// return sendSmsApiResponse = sendSmsSuccesscount + " sms has been sent ";
							// }

						} else {
							sendSmsFailedcount++;
							// System.out.println("count of failed.."+sendSmsFailedcount);
							// if(mobile_no==null) {
							// CghsWebinarSendSmsValidation.updateSuccesFailedCountInBulkSmsRequest(bulk_sms_request_id,
							// sendSmsSuccesscount, sendSmsFailedcount);
							// return sendSmsApiResponse = sendSmsFailedcount+ "sms has not sent";
							// }

						}
					}
				} else {
					// System.out.println("Api is already Running or Record not found");
					Thread.sleep(50000);
					 sendSmsApiResponse = "Mobile number not found";
					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", sendSmsApiResponse);		
					resTestObj.setApiDetails(apiData);
					
					return resTestObj;
		
				}

			}
//			} else {
//				if (apiHitCount > 1) {
//					return sendSmsApiResponse = "SMS has been already Sent";
//				}
//			}
		} catch (Exception e) {
			// if(mobile_no==null) {
			CghsWebinarSendSmsValidation.updateStatusCodeInBulkSmsRequest(bulk_sms_request_id, 6);
			ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
			List<SaveBulkRequestResponseDto> saveBulkResponseDtoListObj=new ArrayList<>();
			
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", sendSmsApiResponse);		
			resTestObj.setApiDetails(apiData);

			return resTestObj;

		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// if(mobile_no==null) {
		CghsWebinarSendSmsValidation.updateSuccesFailedCountInBulkSmsRequest(bulk_sms_request_id, sendSmsSuccesscount,
				sendSmsFailedcount);
		CghsWebinarSendSmsValidation.updateStatusCodeInBulkSmsRequest(bulk_sms_request_id, 7);
		// }
		
		sendSmsApiResponse=	sendSmsSuccesscount + " Sms has been sent";
		apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
		resTestObj.setApiDetails(apiData);
		SendSmsResponseDto sendSmsResponseDto=new SendSmsResponseDto();
		sendSmsResponseDto.setSendSmsRsponse(sendSmsApiResponse);
		ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
		
		
		
		List<SendSmsResponseDto> sendSmsResponseDtoListObj=new ArrayList<>();
		sendSmsResponseDtoListObj.add(sendSmsResponseDto);
		serviceToControllerParams.setResult(sendSmsResponseDtoListObj);
		
		resTestObj.setResult(serviceToControllerParams.getResult());
	
		return resTestObj;

	}

	/**
	 * @param bulk_sms_request_id
	 */

	@Override
	public ResponseAPIDetails getBulkSmsRequestDetails(
			int bulkSmsRequestId) {
	//	List<SmsContentResponseDto> smsContentList = new ArrayList<>();
		List showBulkRequestResponseDto=new ArrayList<>();
		Connection connection = null,connection2=null;
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
		ResponseMetaData apiData;
		try {
			connection = JDBCConnection.getDBConnection();
			connection2 = JDBCConnection.getDBConnection();
			
			String getStatusNameQuery = "select sms_send_status_name from bulk_sms_request_details bsrd inner join bulk_sms_send_status_master bsssm\r\n"
					+ "on bsrd.sms_send_status_code=bsssm.sms_send_status_code where bsrd.bulk_sms_request_id=?";
			String targetBeneficiaryGroupNameQuery="select target_recipient_groups_name from bulk_sms_request_details bsrd inner join target_recipient_groups_master trgm\r\n"
					+ "on  bsrd.target_recipient_groups=trgm.target_recipient_groups where bsrd.bulk_sms_request_id=?";
			
			
			ResultSet rs = CghsCardFullCommon.getSmsContentDetails(bulkSmsRequestId);

			while (rs.next()) {
				
				int bulk_sms_request_id = rs.getInt("bulk_sms_request_id");
				String sms_content = rs.getString("sms_content");
				String template_id = rs.getString("template_id");
				String webinar_series = rs.getString("webinar_series");
				int target_recipient_groups=rs.getInt("target_recipient_groups");
				PreparedStatement getStatusNameStatement = connection.prepareStatement(getStatusNameQuery);
				PreparedStatement targetBeneficiaryGroupNameStatement=  	connection2.prepareStatement(targetBeneficiaryGroupNameQuery);;
                if(bulkSmsRequestId==0) {
				getStatusNameStatement.setInt(1, bulk_sms_request_id);
				targetBeneficiaryGroupNameStatement.setInt(1, bulk_sms_request_id);
                }else {
                	getStatusNameStatement.setInt(1, bulkSmsRequestId);
                	targetBeneficiaryGroupNameStatement.setInt(1, bulkSmsRequestId);
                }
				ResultSet statusNameRs = getStatusNameStatement.executeQuery();
				ResultSet statutargetBeneficiaryGroupRs=  	targetBeneficiaryGroupNameStatement.executeQuery();

				while (statusNameRs.next() && statutargetBeneficiaryGroupRs.next()) {

						String targetBeneficiaryGroupName=statutargetBeneficiaryGroupRs.getString("target_recipient_groups_name");
					SmsContentResponseDto smsContentResponseDto = new SmsContentResponseDto(bulk_sms_request_id,
							sms_content, template_id, webinar_series, statusNameRs.getString("sms_send_status_name"),
							rs.getDate("sms_send_target_date"), targetBeneficiaryGroupName);
					showBulkRequestResponseDto.add(smsContentResponseDto);
					ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
					serviceToControllerParams.setResult(showBulkRequestResponseDto);
				//	serviceToControllerParams.setResult(smsContentList);
					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
					resTestObj.setApiDetails(apiData);
					resTestObj.setResult(serviceToControllerParams.getResult());
				//	return resTestObj;
				}	
			}
			if(resTestObj.getApiDetails().getApiVersion()==null) {
				apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", "Record not available");		
				resTestObj.setApiDetails(apiData);	
				return resTestObj;
			}
			
		} catch (Exception e) {
     //    e.printStackTrace();
 		apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", e.getMessage());	
 		resTestObj.setApiDetails(apiData);
		}
		return resTestObj;
	}

	@Override
	public ResponseAPIDetails testMobileNumberForSms(TestMobileNumberRequestDto testMobileNumberRequestDto) {
		String testMobileNumberResponse = null;
		Connection connection = null;
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
        ResponseMetaData apiData;
		// String smsContent = null;
		// String dlt_template_id = null;
		String validationResponse = CghsWebinarSendSmsValidation
				.validationForTestMObileNumber(testMobileNumberRequestDto);
		try {
			connection = JDBCConnection.getDBConnection();
			if (validationResponse.matches("success")) {
				String mobilenumber = testMobileNumberRequestDto.getMobile_no();
				int bulk_sms_request_id = testMobileNumberRequestDto.getBulk_sms_request_id();
				String insertQuery = "INSERT INTO bulk_sms_recipient_list (bulk_sms_request_id,mobile_no,insertion_date,sent_sms_flag,name) VALUES (?,?,?,?,?)";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setInt(1, testMobileNumberRequestDto.getBulk_sms_request_id());
				insertStatement.setString(2, mobilenumber);
				insertStatement.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
				insertStatement.setInt(4, 0);
				insertStatement.setString(5, "testing");
				int insertCount = insertStatement.executeUpdate();
				String smsContent = null;
				String dlt_template_id = null;
				ResultSet smsContentResultset = null;
				if (insertCount > 0) {

					String smsContentSelectQuery = "select sms_content,template_id from bulk_sms_request_details where bulk_sms_request_id=?";
//			smsContentStatemnet.setInt(1, 4);
					PreparedStatement smsContentStatemnet = connection.prepareStatement(smsContentSelectQuery);
					// smsContentStatemnet = connection.prepareStatement(smsContentSelectQuery);
					smsContentStatemnet.setInt(1, bulk_sms_request_id);
					smsContentResultset = smsContentStatemnet.executeQuery();

				}
				while (smsContentResultset.next()) {
					smsContent = smsContentResultset.getString("sms_content");
					dlt_template_id = smsContentResultset.getString("template_id");

				}
				
				String sendSmsResponse=null;
                if(smsContent!=null) {
				CghsSms cghsSms = new CghsSms();
				 sendSmsResponse = "API000";
                // String sendSmsResponse = cghsSms.sendSms(mobilenumber, smsContent,// dlt_template_id, 0);
               
                
                }else {
                	testMobileNumberResponse="Content not found";
                	apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", testMobileNumberResponse);		
					resTestObj.setApiDetails(apiData);	
                	
					return resTestObj;
                }
				
				if (sendSmsResponse.matches("API000")) {

					String upateSmstableQuery = "update bulk_sms_recipient_list set sent_sms_flag=?,sms_sent_date_time=? where mobile_no=? and bulk_sms_request_id=? and name='testing'";
					PreparedStatement updateStatement = connection.prepareStatement(upateSmstableQuery);
					updateStatement.setInt(1, 1);
					updateStatement.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
					updateStatement.setString(3, mobilenumber);
					updateStatement.setInt(4, bulk_sms_request_id);
					int isTableUpdated = updateStatement.executeUpdate();

					testMobileNumberResponse = "Sms has been sent";
					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
					resTestObj.setApiDetails(apiData);
					
					TestMobileNumberResponseDto testMobileNumberResponseDto=new TestMobileNumberResponseDto();
					testMobileNumberResponseDto.setTestMobileNumberResponse(testMobileNumberResponse);
					
					ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
					List<TestMobileNumberResponseDto> TestMobileNumberResponseDtoListObj=new ArrayList<>();
					TestMobileNumberResponseDtoListObj.add(testMobileNumberResponseDto);
					
					serviceToControllerParams.setResult(TestMobileNumberResponseDtoListObj);
					resTestObj.setResult(serviceToControllerParams.getResult());
					
					
					
				} else {
					testMobileNumberResponse = "Sms not sent";
					
					apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", testMobileNumberResponse);		
					resTestObj.setApiDetails(apiData);					
					
				}

			} else {
				testMobileNumberResponse = validationResponse;
				
				apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", testMobileNumberResponse);		
				resTestObj.setApiDetails(apiData);		
				
		//	}
	} /*
		 * else {
		 * testMobileNumberResponse="Sms Content  not found of Requested bulk_sms_request_id"
		 * ; apiData=new
		 * Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F",
		 * testMobileNumberResponse); resTestObj.setApiDetails(apiData);
		 * 
		 * }
		 */

		} catch (Exception e) {
			// e.printStackTrace();
			testMobileNumberResponse = e.getMessage();
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", testMobileNumberResponse);		
			resTestObj.setApiDetails(apiData);
		}
		return resTestObj;
	}

	@Override
	public ResponseAPIDetails freezeBulkSmsContent(int bulkSmsRequestId) {
		String isStatusUpdated = null;
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
        ResponseMetaData apiData;
        
        
		int updateCount = CghsWebinarSendSmsValidation.updateStatusCodeInBulkSmsRequest(bulkSmsRequestId, 2);
		if (updateCount > 0) {
			isStatusUpdated = "Content has been freezed ";
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
			resTestObj.setApiDetails(apiData);
			FreezeContentResponseDto freezeContentResponseDto=new FreezeContentResponseDto();
			freezeContentResponseDto.setFreecontentRespsone(isStatusUpdated);
			ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
			List<FreezeContentResponseDto> FreezeContentResponseDtoListObj=new ArrayList<>();
			FreezeContentResponseDtoListObj.add(freezeContentResponseDto);
			serviceToControllerParams.setResult(FreezeContentResponseDtoListObj);
			resTestObj.setResult(serviceToControllerParams.getResult());
		
		} else {
			isStatusUpdated = "Not freezed";
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", isStatusUpdated);		
			resTestObj.setApiDetails(apiData);
			
			
		}

		return resTestObj;
	}

	@Override
	public ResponseAPIDetails getBulkSmsType() {
		ResponseAPIDetails resTestObj = new ResponseAPIDetails();
        ResponseMetaData apiData;
		
		try {
	ArrayList<String>	bulkSmsType=bulkSmsRequestDetailsDao.getBulkSmsType();

	
	BulkSmsTypeMasterResponseDto bulkSmsTypeMasterResponseDto=new BulkSmsTypeMasterResponseDto();
	
	List<BulkSmsTypeMasterResponseDto> BulkSmsTypeMasterResponseDtoListObj=new ArrayList<>();
		if(bulkSmsType.size()!=0 || !bulkSmsType.isEmpty()) {
		for(int i=0;i<bulkSmsType.size();i++) {
			bulkSmsTypeMasterResponseDto.setBulkSmsTypeNames(bulkSmsType);
		}
		BulkSmsTypeMasterResponseDtoListObj.add(bulkSmsTypeMasterResponseDto);
			
		}
	
	ServiceToControllerParams serviceToControllerParams=new ServiceToControllerParams();
	serviceToControllerParams.setResult(BulkSmsTypeMasterResponseDtoListObj);
	resTestObj.setResult(serviceToControllerParams.getResult());
	String str=null;
	apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "S", "success");		
	resTestObj.setApiDetails(apiData);
		}catch (Exception e) {
			apiData=new Cghs.CghsCardFullDetailsAPI.ResponseDTO.ApiDetails().setapiData("v1", "F", e.getMessage());		
			resTestObj.setApiDetails(apiData);
		
		}
		return resTestObj;
	}

}
