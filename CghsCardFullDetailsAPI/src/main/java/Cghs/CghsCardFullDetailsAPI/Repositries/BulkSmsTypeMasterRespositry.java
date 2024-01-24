package Cghs.CghsCardFullDetailsAPI.Repositries;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Cghs.CghsCardFullDetailsAPI.Model.BulkSmsTypeMaster;
@Repository
public interface BulkSmsTypeMasterRespositry extends JpaRepository<BulkSmsTypeMaster, Integer> {

    @Query("SELECT b.sms_type_name FROM BulkSmsTypeMaster b")
    ArrayList<String> getBulkSmsType();
}