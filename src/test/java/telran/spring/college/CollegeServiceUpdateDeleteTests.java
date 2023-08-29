package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.college.dto.PersonDto;
import telran.spring.college.entity.Student;
import telran.spring.college.entity.Subject;
import telran.spring.college.repository.MarkRepository;
import telran.spring.college.repository.StudentRepository;
import telran.spring.college.repository.SubjectRepository;
import telran.spring.college.service.CollegeService;
import telran.spring.exceptions.NotFoundException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CollegeServiceUpdateDeleteTests {
	
	static final String SUBJECT_ID = "S1";
	static final Long LECTURER_ID = 322l;
	static final int HOURS = 200;
	private static final Long STUDENT_NO_MARKS_ID = 128l;
	private static final Long STUDENT_REMOVED_ID_0 = 123l;
	
	
	@Autowired
	CollegeService collegeService;
	@Autowired
	SubjectRepository subjectRepo;
	@Autowired
	StudentRepository studentRepo;
	@Autowired
	MarkRepository markRepo;
	

	@Test
	@Order(1)
	@Sql(scripts = {"college-read-test-script.sql"})
	void updateHoursTest() {
		collegeService.updateHours(SUBJECT_ID, HOURS);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateHours(SUBJECT_ID + 10, HOURS));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = true)
	void checkUpdateHoursTest() {
		Subject subject = subjectRepo.findById(SUBJECT_ID).get();
		assertEquals(HOURS, subject.getHours());
	}
	
	@Test
	@Order(3)
	void updateLecturerTest() {
		collegeService.updateLecturer(SUBJECT_ID, LECTURER_ID);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateLecturer(SUBJECT_ID + 10, LECTURER_ID));
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateLecturer(SUBJECT_ID, LECTURER_ID + 1000));
		assertThrowsExactly(IllegalArgumentException.class, () -> collegeService.updateLecturer(SUBJECT_ID, null));
	}
	
	@Test
	@Order(4)
	@Transactional(readOnly = true)
	void checkUpdateLecturerTest() {
		Subject subject = subjectRepo.findById(SUBJECT_ID).get();
		assertEquals(LECTURER_ID, subject.getLecturer().getId());
	}
	
	@Test
	@Order(5)
	void removeStudentsNotMarksTest() {
		List<PersonDto> students = collegeService.removeStudentsNoMarks();
		assertEquals("Neuch", students.get(0).getName());
	}
	
	@Test
	@Order(6)
	@Transactional(readOnly = true)
	void removedStudentsNotMarksTest() {
		assertNull(studentRepo.findById(STUDENT_NO_MARKS_ID).orElse(null));
	}
	
	@Test
	@Order(7)
	@Transactional(readOnly = true,propagation = Propagation.NEVER)
	@Sql(scripts = {"college-read-test-script.sql"})
	void removeStudentLessMarks() {
		List<PersonDto> students = collegeService.removeStudentLessMarks(3);
		assertEquals(2, students.size());
		assertEquals("Yuri", students.get(0).getName());
	}
	
	@Test
	@Order(8)
	@Transactional(readOnly = true)
	void removeStudentLessMarksTest() {
		assertNull(studentRepo.findById(STUDENT_REMOVED_ID_0).orElse(null));
		assertEquals(0,markRepo.findMarkByStudentId(123l).size());
	}
		

}
