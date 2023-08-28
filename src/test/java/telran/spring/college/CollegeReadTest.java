package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.spring.college.dto.*;
import telran.spring.college.service.CollegeService;

@SpringBootTest
class CollegeReadTest {
	
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

}
