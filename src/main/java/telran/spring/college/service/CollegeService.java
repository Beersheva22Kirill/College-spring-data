package telran.spring.college.service;

import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.SubjectDto;

public interface CollegeService {
	
	PersonDto addStudent(PersonDto person);
	PersonDto addLecturer(PersonDto person);
	SubjectDto addSubject(SubjectDto subject);
	MarkDto addMark(MarkDto mark);

}
