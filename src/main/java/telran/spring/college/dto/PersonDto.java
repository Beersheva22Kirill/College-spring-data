package telran.spring.college.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonDto {
	
	Long id;
	String name;
	@Pattern(regexp = "\\d(4)-\\d(2)-\\d(2)")
	String birthDate;
	String city;
	String phone;
	

}
