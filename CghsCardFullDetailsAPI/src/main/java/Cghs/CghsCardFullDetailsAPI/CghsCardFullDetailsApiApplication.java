package Cghs.CghsCardFullDetailsAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.naming.spi.DirStateFactory.Result;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import Cghs.CghsCardFullDetailsAPI.DBConnection.JDBCConnection;

@SpringBootApplication
public class CghsCardFullDetailsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CghsCardFullDetailsApiApplication.class, args);
	   
	}
	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();	
	}
	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	


}
