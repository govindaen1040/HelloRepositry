package Cghs.CghsCardFullDetailsAPI.DaoImpl;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Cghs.CghsCardFullDetailsAPI.Dao.BulkSmsRequestDetailsDao;
import Cghs.CghsCardFullDetailsAPI.Repositries.BulkSmsTypeMasterRespositry;

@Repository
public class BulkSmsRequestDetailsDaoImpl implements BulkSmsRequestDetailsDao{
  
	@Autowired
	BulkSmsTypeMasterRespositry bulkSmsTypeMasterRespositry;
	
	@Override
	public ArrayList<String> getBulkSmsType() {
	ArrayList<String>  bulkSmsType =	bulkSmsTypeMasterRespositry.getBulkSmsType();
		return bulkSmsType;
	}

}
