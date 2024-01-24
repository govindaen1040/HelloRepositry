package Cghs.CghsCardFullDetailsAPI.CommonUtility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.SaveBulkRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.ExtractMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.TestMobileNumberRequestDto;

public class CghsWebinarSendSmsValidation {
	
	public static String validationForTestMObileNumber(TestMobileNumberRequestDto testMobileNumberRequestDto) {
		String validatoinReponse=null;
		try {
		if(testMobileNumberRequestDto.getMobile_no()==null) {
//			validatoinReponse="Please Enter mobile Number";
		}else if(testMobileNumberRequestDto.getMobile_no().length()>10 || testMobileNumberRequestDto.getMobile_no().length()<10) {
			validatoinReponse="Please Enter 10 digit mobile number";
		}else if(testMobileNumberRequestDto.getBulk_sms_request_id()==0) {
			validatoinReponse="Please provide Bulk_sms_request_id";
		}else {
			validatoinReponse="success";
		}
		}catch (Exception e) {
			return validatoinReponse="";
		}
		return validatoinReponse;
		
	}
	
	
	
	
	public static String extractMobileNumberApiValidatoin(ExtractMobileNumberRequestDto extractMobileNumberRequestDto) {
		String response=null;
		try {
		if(extractMobileNumberRequestDto.getBulk_sms_request_id()==0) {
			response="Please provide bulk_sms_request_id";
		}else if(extractMobileNumberRequestDto.getTarget_recipient_only_mch()==0) {
			response="Please provide Target_recipient";
		}else {
			response="success";
		}
		}catch (Exception e) {
		return response="";
		}
		
		return response;
	}
	
	

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	public static boolean isValidSqlDateFormat(Date date) {
		String formattedDate = dateFormat.format(date);
		return formattedDate.equals(date.toString());
	}

	public static int updateStatusCodeInBulkSmsRequest(int bulk_sms_request_id,int sms_send_status_code) {
		int count=0;
		try {
			Connection Connection5 = null;
			Connection5 = JDBCConnection.getDBConnection();
			String updateBulkRequestTable2 = "update bulk_sms_request_details set sms_send_status_code=? where bulk_sms_request_id=?";
			PreparedStatement updateBulkSmsRequestStatement5 = Connection5.prepareStatement(updateBulkRequestTable2);
			updateBulkSmsRequestStatement5.setInt(1, sms_send_status_code);
			updateBulkSmsRequestStatement5.setInt(2, bulk_sms_request_id);
		 count=	updateBulkSmsRequestStatement5.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return count;
	}
	public static void updateSuccesFailedCountInBulkSmsRequest(int bulk_sms_request_id,int send_success_count,int send_failed_count) {
		try {
			Connection Connection5 = null;
			Connection5 = JDBCConnection.getDBConnection();
			String updateBulkRequestTable2 = "update bulk_sms_request_details set send_success_count=?,send_failed_count=? where bulk_sms_request_id=?";
			PreparedStatement updateBulkSmsRequestStatement5 = Connection5.prepareStatement(updateBulkRequestTable2);
			updateBulkSmsRequestStatement5.setInt(1, send_success_count);
			updateBulkSmsRequestStatement5.setInt(2, send_failed_count);
			updateBulkSmsRequestStatement5.setInt(3, bulk_sms_request_id);
			updateBulkSmsRequestStatement5.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	public static String bulksmsRequestValidation(SaveBulkRequestDto cghsWebinarDto) {
		String validationResponse = null;
		try {

			if (cghsWebinarDto.getSms_content() == null) {
				validationResponse = "Please provide sms_content";
			} else if (cghsWebinarDto.getTemplate_id() == null) {
				validationResponse = "Please Provide template_id";
			} else if (cghsWebinarDto.getSms_send_target_date() == null) {
				validationResponse = "please Select target Date";
			} else if (!isValidSqlDateFormat(cghsWebinarDto.getSms_send_target_date())) {
				validationResponse = "Sms date is not in proper format";
			} else if (cghsWebinarDto.getWebinar_series() == null) {
				validationResponse = "Please provide Webinar Series";
			} else if (cghsWebinarDto.getSms_type_code() == 0) {
				validationResponse = "Please select sms type";
			} else if (cghsWebinarDto.getTarget_recipent_group() == 0) {
				validationResponse = "Please provie Target Recipient";
			} else if (cghsWebinarDto.getTarget_recipient_only_mch() == 0) {
				validationResponse = "Please provide Target Beneficiary";
			} else {
				validationResponse = "success";
			}
		} catch (Exception e) {
			validationResponse = "";
		}
		return validationResponse;
	}
}
