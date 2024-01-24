package Cghs.CghsCardFullDetailsAPI.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.SaveBulkRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.ExtractMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.TestMobileNumberRequestDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseAPIDetails;
import Cghs.CghsCardFullDetailsAPI.Service.CghsSendSmsWebinarService;

@RestController
@RequestMapping("/cghs")
public class CghsWebinarSendSms {
	
    @Autowired
	CghsSendSmsWebinarService cghsSendSmsWebinarService;
    
    ObjectMapper objMapper=new ObjectMapper();
    
	@RequestMapping(method =RequestMethod.POST, value = "/saveBulkSmsRequest")
	public ResponseAPIDetails insertSmsDetailsInDraft(@RequestBody SaveBulkRequestDto cghsWebinarDto,HttpServletRequest request) throws JsonProcessingException {
		
		ResponseAPIDetails  saveBulkReqquestApiResponse=cghsSendSmsWebinarService.saveBulkSmsRequest(cghsWebinarDto,request);	
	//String jsonRequestSaveBulkSmsApiResponse=	objMapper.writeValueAsString(saveBulkReqquestApiResponse);
	return saveBulkReqquestApiResponse;
	
	}
	
	@RequestMapping(method =RequestMethod.POST, value = "/freezeSmsContent/{bulkSmsRequestId}")
     public ResponseAPIDetails freezeSmsBulkRequestContent(@PathVariable int bulkSmsRequestId) throws JsonProcessingException {
		
		ResponseAPIDetails freezeContentApiResponse= 	cghsSendSmsWebinarService.freezeBulkSmsContent(bulkSmsRequestId);
	
	//	String  freezeContentApiResponseJson= objMapper.writeValueAsString(freezeContentApiResponse);
		return freezeContentApiResponse; 
     }
		
	@RequestMapping(method =RequestMethod.POST, value = "/showBulkSmsRequest/{bulkSmsRequestId}")
	public ResponseAPIDetails showBublkSmsRequest(@PathVariable(required = false) int bulkSmsRequestId) throws JsonProcessingException {

		ResponseAPIDetails getBulkSmsResponse=cghsSendSmsWebinarService.getBulkSmsRequestDetails(bulkSmsRequestId);	
		
	//String 	getBulkSmsResponseJson=objMapper.writeValueAsString(getBulkSmsResponse);
		return getBulkSmsResponse;
	
	}
	
	@RequestMapping(method =RequestMethod.POST, value = "/extractMobileNumber")
	public ResponseAPIDetails extractMobileNumberForSendSms(@RequestBody ExtractMobileNumberRequestDto extractMobileNumberDto) throws JsonProcessingException {
  
		ResponseAPIDetails extractMobileApiResponse=	cghsSendSmsWebinarService.extractMobileNumber(extractMobileNumberDto);		
        
//	String 	extractMobileApiResponseJson=objMapper.writeValueAsString(extractMobileApiResponse);
		
	return extractMobileApiResponse;
	}
	
	
	@RequestMapping(method =RequestMethod.POST, value = "/testMobileNumber")
	public ResponseAPIDetails testSmsToPerticularMobileNumber(@RequestBody TestMobileNumberRequestDto testMobileNumberRequestDto) throws JsonProcessingException {
		
		ResponseAPIDetails testMobileNumberApiResponse=	cghsSendSmsWebinarService.testMobileNumberForSms(testMobileNumberRequestDto);
//	String testMobileNumberApiResponseJson=	objMapper.writeValueAsString(testMobileNumberApiResponse);
		return testMobileNumberApiResponse;
	}
	
	@RequestMapping(method =RequestMethod.POST, value = "/sendSms/{bulkSmsRequestId}")
	public ResponseAPIDetails sendSmsToBeneficiary(@PathVariable int bulkSmsRequestId) throws JsonProcessingException {
	String mobile_no=null;
	ResponseAPIDetails sendSmsApiResponse=  cghsSendSmsWebinarService.sendSmsToBeneficiary(bulkSmsRequestId,mobile_no);
	//String sendSmsApiResponseJson=objMapper.writeValueAsString(sendSmsApiResponse);
		return sendSmsApiResponse;

	}
	
	
	@RequestMapping(method =RequestMethod.POST, value = "/fetchBulkSmsType")
	public ResponseAPIDetails geBulkSmsType() {
		
	ResponseAPIDetails	smsTypeApiResponse=cghsSendSmsWebinarService.getBulkSmsType();
		
		return smsTypeApiResponse;
	}
	
	
	

}
