package Cghs.CghsCardFullDetailsAPI.Repositries;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Cghs.CghsCardFullDetailsAPI.Model.BulkSmsRequestDetailsModel;

public interface BulkSmsRequestDetailsRepositries extends JpaRepository<BulkSmsRequestDetailsModel, Integer> {

	@Query("SELECT e FROM BulkSmsRequestDetailsModel e " +
		       "WHERE e.smsContent = :smsContent " +
		       "OR e.webinarSeries = :webinarSeries " +
		       "OR e.smsSendTargetDate = :smsSendTargetDate")
		Optional<BulkSmsRequestDetailsModel> findBySmsContentOrWebinarSeriesOrSmsSendTargetDate(
		        @Param("smsContent") String smsContent,
		        @Param("webinarSeries") String webinarSeries,
		        @Param("smsSendTargetDate") Date smsSendTargetDate);

	
	
	
	
}

