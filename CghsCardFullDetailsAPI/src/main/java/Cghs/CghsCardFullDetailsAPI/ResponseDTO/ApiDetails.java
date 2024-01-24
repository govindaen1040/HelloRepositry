package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

import java.util.Date;

public class ApiDetails {
	ResponseAPIDetails ccdResponseDto=new ResponseAPIDetails();
	ResponseMetaData apiData=new ResponseMetaData();
  public ResponseMetaData setapiData(String apiVersion,String statusCode,String message ) {
	  ResponseMetaData apiDetails=new ResponseMetaData();
	  apiDetails.setApiVersion(apiVersion);
	  apiDetails.setMessage(message);
	  apiDetails.setStatusCode(statusCode);
	  apiDetails.setTimeStamp(new Date().toString());
	  
	return apiDetails;	  
  }
  
  ResponseAPIDetails checkAuthorizationMsg() {
	  apiData=new ApiDetails().setapiData("", "", "");
	  ccdResponseDto.setApiDetails(apiData);
	return ccdResponseDto;
	  
	  
  }
	
}
