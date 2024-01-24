package Cghs.CghsCardFullDetailsAPI.CommonUtility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class CghsSms {

	String senderId = "CGHLTH";
	String uname = "cghs.auth";
	String pinPassword = "Auth.cghs@123";
	String urllink = "https://smsgw.sms.gov.in/failsafe/HttpLink?";
	String dlt_entity_id = "1101717070000028993";
	
	public String sendSms(String Mobileno, String smsMessage, String dlt_template_id,int limitCount) {
		String msgstatus = "";
		try {
			
			String postData = "";
			URL smsurl = null;
			HttpsURLConnection urlconnection = null;

				postData = "username=" + URLEncoder.encode(uname, "UTF-8") + "&pin=" + pinPassword + "&message="
						+ URLEncoder.encode(smsMessage, "UTF-8") + "&mnumber=" + Mobileno + "&signature=" + senderId
						+ "&dlt_entity_id=" + dlt_entity_id + "&dlt_template_id=" + dlt_template_id;

				smsurl = new URL(urllink + postData);
				urlconnection = (HttpsURLConnection) smsurl.openConnection();
				urlconnection.setRequestMethod("POST");
				urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				urlconnection.setDoOutput(true);

				String decodedString;
				
				BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
				

					while ((decodedString = in.readLine()) != null) {
						if (decodedString.contains("API000")) {
							msgstatus = "API000";

						} else {
							int fromind = decodedString.indexOf('-');
							int toind = decodedString.indexOf('&');
							msgstatus = decodedString.substring(fromind + 1, toind);
						}
					}
					in.close();
					urlconnection.disconnect();
		} catch (Exception e) {
			System.out.println("Exception in sendSms----------" + e);
		}
		return msgstatus;
	}
	
	public String sendMobileOtp(String Mobileno, String smsMessage, String dlt_template_id,int limitCount) {
		
		Map<String, String> sendOtphm = new HashMap<String, String>();
		sendOtphm.put("senderId", "CGHLTH");
		sendOtphm.put("uname", "cghs.otp");
		sendOtphm.put("pinPassword", "B@k1J%239u");
		sendOtphm.put("urllink", "https://smsgw.sms.gov.in/failsafe/HttpLink?");
		sendOtphm.put("dlt_entity_id", "1101717070000028993");
	
		String msgstatus = "";
		try {
			String postData = "";
			URL smsurl = null;
			HttpsURLConnection urlconnection = null;

				postData = "username=" + URLEncoder.encode(sendOtphm.get("uname"), "UTF-8") + "&pin=" + sendOtphm.get("pinPassword") + "&message="
						+ URLEncoder.encode(smsMessage, "UTF-8") + "&mnumber=" + Mobileno + "&signature=" + sendOtphm.get("senderId")
						+ "&dlt_entity_id=" + sendOtphm.get("dlt_entity_id") + "&dlt_template_id=" + dlt_template_id;

				smsurl = new URL(sendOtphm.get("urllink") + postData);
			
				urlconnection = (HttpsURLConnection) smsurl.openConnection();
				urlconnection.setRequestMethod("POST");
				urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				urlconnection.setDoOutput(true);
			
				String decodedString;
			
				BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));

				while ((decodedString = in.readLine()) != null) {
					if (decodedString.contains("API000")) {
						msgstatus = "API000";

					} else {
						int fromind = decodedString.indexOf('-');
						int toind = decodedString.indexOf('&');
						msgstatus = decodedString.substring(fromind + 1, toind);
					}
				}
				
				in.close();
				urlconnection.disconnect();	
				
		}catch (Exception e) {
			System.out.println("Exception in sendSms----------" + e);			
		}
		
		return msgstatus;
	}
	

}
