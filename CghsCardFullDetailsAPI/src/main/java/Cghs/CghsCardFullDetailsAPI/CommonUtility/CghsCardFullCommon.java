package Cghs.CghsCardFullDetailsAPI.CommonUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyDetailsDto;

public class CghsCardFullCommon {
	static Connection con=null;
	static PreparedStatement pst=null;
	static ResultSet rs = null;
	public static ResultSet getSmsContentDetails(int bulkSmsRequestId) {
		String query=null;
		try {
			if(bulkSmsRequestId==0) {
				 query = "select bulk_sms_request_id,sms_content,template_id,webinar_series,sms_send_status_code,sms_send_target_date,target_recipient_groups from bulk_sms_request_details order by bulk_sms_request_id desc";
			}else {
				 query = "select bulk_sms_request_id,sms_content,template_id,webinar_series,sms_send_status_code,sms_send_target_date,target_recipient_groups from bulk_sms_request_details where bulk_sms_request_id='"+bulkSmsRequestId+"' order by bulk_sms_request_id desc";
			}
			 con = JDBCConnection.getDBConnection();
			
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}		
		return rs;
	}
	
	public static int generateCardNumber(FamilyDetailsDto familyDetailsDto) {
		int card_no = 0;
		//if i click in generate card no then by default it is 3||   generateCardNo=autoCardNo
		int generateCardNo=3;//just declaration of check-box value of defining the card type logic
		try {
			String cardType = null;
			 con = JDBCConnection.getDBConnection();
			cardType = familyDetailsDto.getMainCardHolder().getCard_type();
		int pen_auto=	familyDetailsDto.getMainCardHolder().getPen_auto();
			// int cardNo=cardHolderAddressDto.getCard_no();
			// int card_no=0;
			PreparedStatement pst2 = null;
			ResultSet rs2 = null;

			if ((cardType.equals("P") && pen_auto != 1) || cardType.equals("X") || cardType.equals("F")
					|| cardType.equals("J")) {
				pst2 = con.prepareStatement(
						"update max_card_no set card_no=card_no+1 where card_type='P' returning card_no");
				rs2 = pst2.executeQuery();
				if (rs2.next()) {
					card_no = rs2.getInt("card_no");
				}
			} else if (cardType.equals("P") && pen_auto == 1) {
				pst2 = con.prepareStatement(
						"update max_card_no set card_no=card_no+1 where card_type='Y' returning card_no");
				rs2 = pst2.executeQuery();
				if (rs2.next()) {
					card_no = rs2.getInt("card_no");
				}
			} else if (cardType.equals("S") && generateCardNo == 3) {
				pst2 = con.prepareStatement(
						"update max_card_no set card_no=card_no+1 where card_type='S' returning card_no");
				rs2 = pst2.executeQuery();
				if (rs2.next()) {
					card_no = rs2.getInt("card_no");
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return card_no;
	}
	
	
	public static String fetchCardTypeOfBeneficiary(String ben_id) {
		
		String card_type = null;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "select card_type from family_details where ben_id='" + ben_id + "' ";      
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next()) {
				card_type = rs.getString("card_type");
				System.out.println("card_type" + card_type);
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

		return card_type;
	}
	public static int getMaxumPatientNumber(String ben_id) {
		int patientNumber = 0;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "select max(patient_no) as patient_no from family_details where id=? ";
			pst = con.prepareStatement(query);
			pst.setString(1, ben_id);
			rs = pst.executeQuery();
			if (rs.next()) {
				patientNumber = rs.getInt("patient_no");
				//System.out.println("patient_no" + patientNumber);

				//System.out.println("dispensary_code" + dispensary_code);
			}
			if(patientNumber!=0) {
				return patientNumber;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;

	}

	public static String createDynamicTable(String suffix) {
		String tableName = null;
		 
		try {
			con = JDBCConnection.getDBConnection();
			tableName = "sms_bulk_list" + suffix;
			String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
					+ "    sms_id SERIAL PRIMARY KEY,\n" + "    sms_content VARCHAR(255),\n"
					+ "    template_id VARCHAR(255),\n" + "    mobile_no VARCHAR(20),\n" + "    sms_date DATE,\n"
					+ "    webinar_series VARCHAR(255),\n" + "    insertion_date DATE,\n" + "    user_id VARCHAR(50),\n"
					+ "    modify_date DATE,\n" + "    ipaddress VARCHAR(50),\n"
					+ "    target_cghs_beneficiary VARCHAR(255),\n" + "    target_doctor VARCHAR(255),\n"
					+ "    target_others VARCHAR(255)\n" + ")";
			PreparedStatement preparedStatement = con.prepareStatement(createTableSQL);
			preparedStatement.executeUpdate();
			System.out.println("New table created with name ::  " + tableName + "");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tableName;

	}

	public static String getPcValidity(String ben_id) {
		String pc_validity = null;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "select pc_validity from family_Details where ben_id='" + ben_id + "'";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next()) {
				pc_validity = rs.getString("pc_validity");

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return pc_validity;

	}

	public static void saveIntoPatientType(String ben_id, String operator, String pType) {
		int result=0;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "insert into patient_type (ben_id,operator,ptype) values ('" + ben_id + "',?,?)";// set parameterized
			pst = con.prepareStatement(query);
			pst.setString(1, operator);
			pst.setString(2, pType);
			result=pst.executeUpdate();// check here retun 0 or 1 
			
		} catch (Exception e) {
        e.printStackTrace();
		}
	}

	public static String getPtypeOfMainCardHolder(String ben_id) {
		//String pc_validity = null;
		String ptype = null;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "select ptype from patient_type where ben_id='" + ben_id + "'";// set parameterized
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			if (rs.next()) {
				ptype = rs.getString("ptype");
			}
		} catch (Exception e) {
		}
		return ptype;
	}
	public static String fetchDispensaryCodeOfBeneficiary(String ben_id) {
		String dispensary_code = null;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "select dispensary_code from family_details where ben_id='" + ben_id + "' ";// set parameterized

			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next()) {
				dispensary_code = rs.getString("dispensary_code");
				System.out.println("dispensary_code" + dispensary_code);
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return dispensary_code;
	}
	public static int fetchCardNumberOfBeneficiary(String ben_id) {
		int card_no = 0;
		try {
			 con = JDBCConnection.getDBConnection();
			String query = "select card_no from family_details where ben_id='" + ben_id + "' ";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			if (rs.next()) {
				card_no = rs.getInt("card_no");
				System.out.println("card_no" + card_no);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return card_no;
	}


}
