package Cghs.CghsCardFullDetailsAPI.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCConnection {
	static Connection con = null;
	private static ResourceBundle properties = null;
	private static String dbServerType = "";
	private static String dbName = "";
	private static String dbServerIP = "";
	private static String dbServerPort = "";
	private static String dbUser = "";
	private static String dbPassword = "";
	
	private static boolean init() {
		boolean getAttributes = false;
		try {
			properties = ResourceBundle.getBundle("Cghs.CghsCardFullDetailsAPI.Resource.applicationResources");
			if (properties != null) {
				if (properties.getString("db.server.type") != null) {
					dbServerType = properties.getString("db.server.type");
					 //System.out.println(dbServerType);
				}
				if (properties.getString("db.dbcon.name") != null) {
					dbName = properties.getString("db.dbcon.name");
					// System.out.println(dbName);
				}
				if (properties.getString("db.server.ip") != null) {
					dbServerIP = properties.getString("db.server.ip");
					// System.out.println(dbServerIP);
				}
				if (properties.getString("db.server.port") != null) {
					dbServerPort = properties.getString("db.server.port");
					// System.out.println(dbServerPort);
				}
				if (properties.getString("db.user.id") != null) {
					dbUser = properties.getString("db.user.id");
					// System.out.println(dbUser);
				}
				if (properties.getString("db.user.password") != null) {
					dbPassword = properties.getString("db.user.password");
					// System.out.println(dbPassword);
				}
				getAttributes = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return getAttributes;
	}
	
	public static Connection getDBConnection() throws SQLException,ClassNotFoundException{
		boolean readProperty = JDBCConnection.init();
		if (readProperty) {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://"+dbServerType+":"+dbServerPort+"/"+dbName+"", dbUser, dbPassword);
					
		}		
		return con;
		
	}
	
}
