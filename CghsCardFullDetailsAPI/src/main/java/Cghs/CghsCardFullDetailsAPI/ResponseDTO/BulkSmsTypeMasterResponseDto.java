package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkSmsTypeMasterResponseDto {
 private  ArrayList<String> bulkSmsTypeNames;
}
