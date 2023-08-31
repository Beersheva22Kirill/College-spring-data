package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.college.dto.*;
import telran.spring.college.entity.Student;
import telran.spring.college.entity.Subject;
import telran.spring.college.repository.StudentRepository;
import telran.spring.college.repository.SubjectRepository;
import telran.spring.college.service.CollegeService;

@SpringBootTest
@Sql(scripts = {"college-read-test-script.sql"})
class CollegeServiceReadTest {
	
	@Autowired
	CollegeService collegeService;
	@Autowired
	SubjectRepository subjectRepo;
	@Autowired
	StudentRepository studentRepo;

	@Test
	void bestStudentsOfLectirerTest() {
		List<IdName> actualList = collegeService.bestStudentsOfLecturer(321, 2);
		assertEquals(2, actualList.size());
		assertEquals(124,actualList.get(0).getId());
		assertEquals("Kirill", actualList.get(0).getName());
		assertEquals(126, actualList.get(1).getId());
		assertEquals("Dmitri", actualList.get(1).getName());
	}
	
	@Test
	void studentsAvgMarksGreaterCollegeAvgTest() {
		List<IdName> actualList = collegeService.studentsAvgMarksGreaterCollegeAvg(2);
		assertEquals(3, actualList.size());
		assertEquals(124,actualList.get(0).getId());
		assertEquals("Kirill", actualList.get(0).getName());
		assertEquals(126, actualList.get(1).getId());
		assertEquals("Dmitri", actualList.get(1).getName());
		assertEquals(127, actualList.get(2).getId());
		assertEquals("David", actualList.get(2).getName());
	}
	
	@Test	
	void studentsAvgMarksTest() {
		List<AvgMark> actualList = collegeService.studentsAvgMark();
		assertEquals(6, actualList.size());
		
		assertEquals(124,actualList.get(0).getId());
		assertEquals("Kirill", actualList.get(0).getName());
		assertEquals(95, actualList.get(0).getAvgMark());
		assertEquals(128, actualList.get(5).getId());
		assertEquals("Neuch", actualList.get(5).getName());
		assertNull(actualList.get(5).getAvgMark());
	}
	
	@Test
	void studentMarksBySubjectTest() {
		List<MarkDto> marks = collegeService.marksOfStudentBySubject(124L, "S1");
		assertEquals(100, marks.get(0).getMark());
		assertEquals("S1", marks.get(0).getSubjectId());
		
	}
	
	@Test
	void studentMarksGreaterTest() {
		List<IdName> students = collegeService.studentsGreaterMarkBySubject(SubjectType.BACK_END, 89);
		assertEquals(3, students.size());
		assertEquals("Yuri", students.get(0).getName());
		assertEquals("Kirill", students.get(1).getName());
		assertEquals("Dmitri", students.get(2).getName());
	}
	
	@Test
	@Transactional(readOnly = true)
	void fethLecturerTest() {
		Subject subject = subjectRepo.findById("S3").orElse(null);
		assertEquals(322, subject.getLecturer().getId());
		assertEquals("Andrey Sobol", subject.getLecturer().getName());
	}
	
	@Test
	@Transactional(readOnly = true)
	void fethMarksTest() {
		Student student = studentRepo.findById(124L).orElse(null);
		assertEquals(4, student.getMarks().size());
	}
	
	@Test
	void fethLecturerLazyNoTransactionalTest() {
		Student student = studentRepo.findById(124L).orElse(null);
		assertThrows(Exception.class, () -> student.getMarks().size());	
	}
	
	@Test
	void fethMarksLazyNoTransactionalTest() {
		Subject subject = subjectRepo.findById("S3").orElse(null);
		assertEquals(322, subject.getLecturer().getId());
		assertThrows(Exception.class, () -> subject.getLecturer().getName());	
	}
	
	@Test
	void jpqlSingleProjectionTest() {
		String strQuery = "SELECT lect.id FROM Lecturer lect ORDER BY lect.id";
		List<String> res = collegeService.jpqlQuery(strQuery);
		assertEquals(3, res.size());
		String[] expected = {"321","322","323"};
		assertArrayEquals(expected, res.toArray(String[] :: new));
	}
	
	@Test
	void jpqlMultiProjectionTest() {
		String strQuery = "SELECT lect.id, lect.name FROM Lecturer lect ORDER BY lect.id";
		List<String> res = collegeService.jpqlQuery(strQuery);
		assertEquals(3, res.size());
		String[] expected = {"[321, Igor Korol]","[322, Andrey Sobol]","[323, Yana Polay]"};
		assertArrayEquals(expected, res.toArray(String[] :: new));
	}
	
	

}
