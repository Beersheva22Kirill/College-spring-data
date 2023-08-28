package telran.spring.college.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MarkDto {
	
	
	Integer id;
	long studentId;
	String subjectId;
	int mark;

}
