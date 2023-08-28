package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.dto.SubjectType;
import telran.spring.college.entity.Lecturer;
import telran.spring.college.entity.Student;
import telran.spring.college.repository.*;
import telran.spring.college.service.CollegeService;
import telran.spring.exceptions.NotFoundException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeServiceAddsTests {
	
	private static final long ID_LECTURER = 123;
	private static final long ID_STUDENT = 124;
	@Autowired
	CollegeService service;
	PersonDto lecturerDto = new PersonDto(ID_LECTURER, "Vasya", LocalDate.now().toString(), null, null);
	PersonDto lecturerDto1 = new PersonDto(null, "Sara", "2000-01-01", null, null);
	PersonDto studentDto = new PersonDto(ID_STUDENT, "Petya", LocalDate.now().toString(), null, null);
	PersonDto studentDto1 = new PersonDto(null, "Yosef", "2000-01-01", null, null);
	SubjectDto subjectDto = new SubjectDto("S1", "Java", 100, null,SubjectType.BACK_END);
	SubjectDto sujectDto1 = new SubjectDto("S2", "Java", 100, ID_LECTURER,SubjectType.BACK_END);
	SubjectDto sujectDto2 = new SubjectDto("S3", "Java", 100, ID_LECTURER + 10,SubjectType.BACK_END);
	SubjectDto subjectDto3 = new SubjectDto("S1", "Java", 100, null,SubjectType.BACK_END);
		
		@Test
		@Order(1)
		void addLecturer(){
			PersonDto lectureActual = service.addLecturer(lecturerDto);
			assertEquals(ID_LECTURER, lectureActual.getId());
			PersonDto lectureActual1 = service.addLecturer(lecturerDto1);
			assertEquals("Sara", lectureActual1.getName());
			assertThrows(Exception.class, () -> service.addLecturer(lecturerDto));
			
		}
		@Order(2)
		@Test
		void addStudent() {
			PersonDto studentActul = service.addStudent(studentDto);
			assertEquals(ID_STUDENT, studentActul.getId());
			PersonDto studentActual1 = service.addStudent(studentDto1);
			assertEquals("Yosef", studentActual1.getName());
			assertThrows(Exception.class, () -> service.addStudent(studentDto));
			
		}
		@Order(3)
		@Test
		void addSubject() {
			SubjectDto subjectActual = service.addSubject(subjectDto);
			assertEquals(subjectDto.getId(), subjectActual.getId());
			SubjectDto subjectActual1 = service.addSubject(sujectDto1);
			assertEquals(sujectDto1.getId(), subjectActual1.getId());
			assertThrowsExactly(NotFoundException.class, () -> service.addSubject(sujectDto2));
			assertThrowsExactly(IllegalStateException.class, () -> service.addSubject(subjectDto3));
			
		}
		@Order(4)
		@Test
		void addMark() {
			MarkDto markDto = new MarkDto(null, ID_STUDENT, subjectDto.getId(), 100);
			MarkDto markDtoNoStudent = new MarkDto(null, ID_STUDENT + 10, subjectDto.getId(), 100);
			MarkDto markDtoNoSubject = new MarkDto(null, ID_STUDENT, "XXXX", 100);
			MarkDto markDtoActual = service.addMark(markDto);
			assertEquals(1, markDtoActual.getId());
			assertThrowsExactly(NotFoundException.class, () -> service.addMark(markDtoNoStudent));
			assertThrowsExactly(NotFoundException.class, () -> service.addMark(markDtoNoSubject));
		}
	

}
