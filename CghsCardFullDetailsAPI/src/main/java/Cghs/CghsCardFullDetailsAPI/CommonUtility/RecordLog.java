package Cghs.CghsCardFullDetailsAPI.CommonUtility;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardHolderAddressDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyDetailsDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.IndexCardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordLog {
//private Integer logid;
private String po_request;
private Timestamp api_calling_date_time;
private Long api_execution_time;
private String api_response;
private String api_status_code;
private String caller_ipaaddress;
private String user_id;

}
