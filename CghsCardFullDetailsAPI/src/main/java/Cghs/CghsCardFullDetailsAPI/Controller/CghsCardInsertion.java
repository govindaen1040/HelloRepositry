package Cghs.CghsCardFullDetailsAPI.Controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Cghs.CghsCardFullDetailsAPI.Model.FamilyDetailsModel;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.BeneficiaryImageUploadDto;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.CardFullDetailsDTO;
import Cghs.CghsCardFullDetailsAPI.RequestDTO.FamilyMemberDto;
import Cghs.CghsCardFullDetailsAPI.ResponseDTO.ResponseAPIDetails;
import Cghs.CghsCardFullDetailsAPI.Service.CghsCardInsertionService;


@RestController
@RequestMapping(value = "/cghs")
public class CghsCardInsertion {

	@Value("${project.image}")
	private String path;
	
	
	@Autowired
	private CghsCardInsertionService cghsCardInsertionServiceObj;
	@Autowired
	private ObjectMapper objMapper;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/insertCghsCard")
	public ResponseAPIDetails CghsCardDetailsInsert(@RequestBody CardFullDetailsDTO cardFullDetailsDTO,
			HttpServletRequest request) throws JsonProcessingException {

		ResponseAPIDetails responseAPIDetails = cghsCardInsertionServiceObj.insertCghsCard(cardFullDetailsDTO, request);
		System.out.println( objMapper.writeValueAsString(responseAPIDetails));   
		return responseAPIDetails;
	}
	
	

	@RequestMapping(method = RequestMethod.POST, value = "/addFamilyMember")
	public ResponseAPIDetails addBeneficiaryFamilyMember(@RequestBody CardFullDetailsDTO cardFullDetailsDTO,
			HttpServletRequest request) throws JsonProcessingException {
		ResponseAPIDetails response = null;
		FamilyDetailsModel familyDetailsModelObj = null;
		
		if (!cardFullDetailsDTO.getFamilyDetails().getMembers().isEmpty()
				|| cardFullDetailsDTO.getFamilyDetails().getMembers() == null) {
			for (int i = 0; i < cardFullDetailsDTO.getFamilyDetails().getMembers().size(); i++) {
				FamilyMemberDto familyMemberDto = cardFullDetailsDTO.getFamilyDetails().getMembers().get(i);
				response= cghsCardInsertionServiceObj.addFamilyMember(familyDetailsModelObj, i, familyMemberDto, cardFullDetailsDTO, request);
			}
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateBeneficiary")
	public ResponseAPIDetails updateBeneficiary(@RequestBody CardFullDetailsDTO cardFullDetailsDTO) {
		
		return null;	
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadImage")
	public String beneficiaryImageUpload( @ModelAttribute BeneficiaryImageUploadDto beneficiaryImageUploadDto) {
	
	 String  imageUPloadApiResponse= cghsCardInsertionServiceObj.uploadBeneficiaryImage(beneficiaryImageUploadDto);
	
	
		return imageUPloadApiResponse;
	}
	

}
