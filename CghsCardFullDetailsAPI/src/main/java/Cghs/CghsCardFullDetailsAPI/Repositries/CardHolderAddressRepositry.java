package Cghs.CghsCardFullDetailsAPI.Repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardHolderAddressDto;

@Repository
public interface CardHolderAddressRepositry extends JpaRepository<CardHolderAddressModel, String>{
	
	@Query("SELECT f FROM CardHolderAddressModel f " + "WHERE f.ben_id = :ben_id " +
			  "AND f.card_no = :card_no " + "AND f.dispensary_code = :dispensary_code")
			  Optional<CardHolderAddressModel> checkcardHolderAddressDataAvailability(@Param("ben_id") String ben_id,@Param("card_no") Integer card_no,@Param("dispensary_code") String dispensary_code );

	

	

}
