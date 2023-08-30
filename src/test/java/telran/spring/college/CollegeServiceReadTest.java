package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.hibernate.dialect.SybaseDriverKind;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.spring.college.dto.*;
import telran.spring.college.entity.Student;
import telran.spring.college.service.CollegeService;

@SpringBootTest
class CollegeServiceReadTest {
	
	@Autowired
	CollegeService collegeService;

	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void bestStudentsOfLectirerTest() {
		List<IdName> actualList = collegeService.bestStudentsOfLecturer(321, 2);
		assertEquals(2, actualList.size());
		assertEquals(124,actualList.get(0).getId());
		assertEquals("Kirill", actualList.get(0).getName());
		assertEquals(126, actualList.get(1).getId());
		assertEquals("Dmitri", actualList.get(1).getName());
	}
	
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
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
	@Sql(scripts = {"college-read-test-script.sql"})
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
	
	

}
