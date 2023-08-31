package telran.spring.college.service;

import java.util.List;

import telran.spring.college.dto.AvgMark;
import telran.spring.college.dto.IdName;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.dto.SubjectType;

public interface CollegeService {
	
	PersonDto addStudent(PersonDto person);
	PersonDto addLecturer(PersonDto person);
	SubjectDto addSubject(SubjectDto subject);
	MarkDto addMark(MarkDto mark);
	List<IdName> bestStudentsOfLecturer(long lecturerId, int nStudents);
	//List students (id and name) who have average mark greater 
	//than average mark of college and number marks not less than nMarks
	List<IdName> studentsAvgMarksGreaterCollegeAvg(int nMarksThreshold);
	List<AvgMark> studentsAvgMark();
	SubjectDto updateHours(String subjectId, int hours);
	SubjectDto updateLecturer(String subjectId,Long lecturerId);
	List<PersonDto> removeStudentsNoMarks();
	List<PersonDto> removeStudentLessMarks(int nMarks);
	List<MarkDto> marksOfStudentBySubject(Long studentId,String subjectId); 
	List<IdName> studentsGreaterMarkBySubject(SubjectType type, int mark);
	PersonDto removeLecturer(long lecturerId);
}
