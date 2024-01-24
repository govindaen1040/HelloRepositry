package Cghs.CghsCardFullDetailsAPI.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Cghs.CghsCardFullDetailsAPI.RequestDTO.SaveBulkRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.ExtractMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.TestMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseAPIDetails;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.SaveBulkRequestResponseDto;

public interface CghsSendSmsWebinarService {
	
	public ResponseAPIDetails saveBulkSmsRequest(SaveBulkRequestDto cghsWebinarDto,HttpServletRequest request);
	
	public ResponseAPIDetails extractMobileNumber(ExtractMobileNumberRequestDto extractMobileNumberDto);
	
	public ResponseAPIDetails sendSmsToBeneficiary(int bulk_sms_request_id,String mobile_no); 
	
    public ResponseAPIDetails getBulkSmsRequestDetails(int bulkSmsRequestId);

	public ResponseAPIDetails testMobileNumberForSms(TestMobileNumberRequestDto testMobileNumberRequestDto);

	public ResponseAPIDetails freezeBulkSmsContent(int bulkSmsRequestId);

	public ResponseAPIDetails getBulkSmsType();	

}
