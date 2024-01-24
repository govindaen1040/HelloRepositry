package Cghs.CghsCardFullDetailsAPI.Repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Cghs.CghsCardFullDetailsAPI.Model.IndexCardModel;

public interface IndexCardRepositry extends JpaRepository<IndexCardModel, String>{
	@Query("SELECT f FROM IndexCardModel f " + "WHERE f.ben_id = :ben_id " +
			  "AND f.card_no = :card_no " + "AND f.dispensary_code = :dispensary_code")
			  Optional<IndexCardModel> checkIndexCardDataAvailability(@Param("ben_id") String ben_id,@Param("card_no") Integer card_no,@Param("dispensary_code") String dispensary_code );
	
	

}
