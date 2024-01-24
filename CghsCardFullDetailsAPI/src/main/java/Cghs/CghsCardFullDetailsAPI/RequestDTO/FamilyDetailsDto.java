package Cghs.CghsCardFullDetailsAPI.RequestDTO;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import Cghs.CghsCardFullDetailsAPI.Model.CardHolderAddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDetailsDto {

   // private String ben_mem_type;
	MainCardHolderDto mainCardHolder;
	
	List<FamilyMemberDto> members;
	
	
	
	
	
	
}
