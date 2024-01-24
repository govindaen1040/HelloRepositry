package Cghs.CghsCardFullDetailsAPI.Repositries;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;

public interface FamilyDetailsRepositry extends JpaRepository<FamilyDetailsModel, String>{
	@Query("SELECT f FROM FamilyDetailsModel f " + "WHERE f.name = :name " +
			  "AND f.date_of_birth = :date_of_birth " + "AND f.sex = :sex")
			  Optional<FamilyDetailsModel> checkFamilyDetailsDataAvailability( @Param("name") String name,@Param("date_of_birth") Date date_of_birth,@Param("sex") String sex );

}
