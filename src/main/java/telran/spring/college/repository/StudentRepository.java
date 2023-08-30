package telran.spring.college.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.college.dto.AvgMark;
import telran.spring.college.dto.IdName;
import telran.spring.college.entity.Mark;
import telran.spring.college.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	
	@Query(value = "SELECT "
			+ "sl.id as id, "
			+ "sl.name as name "
			+ "FROM "
			+ "(SELECT * FROM students_lecturers WHERE dType = 'Student') sl "
			+ "JOIN marks m ON m.student_id = sl.id "
			+ "JOIN subjects sb ON sb.id = m.subject_id "
			+ "WHERE sb.lecturer_id = :lecturerId "
			+ "GROUP BY sl.id, sl.name " 
			+ "ORDER BY AVG(m.mark) desc "
			+ "LIMIT :nStudents", nativeQuery = true)
	List<IdName> findBestStudentsOfLecturer(long lecturerId, int nStudents);
	
	
	@Query(value = "SELECT "
			+ "sl.id as id,"
			+ "sl.name as name " 
			+ "FROM (SELECT * FROM students_lecturers WHERE dType = 'Student') sl "
			+ "JOIN marks m ON m.student_id = sl.id " 
			+ "GROUP BY sl.id, sl.name "
			+ "HAVING COUNT(m.mark) > :nMarksThreshold "
			+ "AND AVG(m.mark) > (SELECT AVG(mark) FROM marks) " 
			+ "ORDER BY AVG(m.mark) desc ", 
			nativeQuery = true)
	List<IdName> findStudentsAvgMarksGreaterCollegeAvg(int nMarksThreshold);
	
	@Query(value = "SELECT "
			+ "sl.id as id,"
			+ "sl.name as name,"
			+ "ROUND(AVG(m.mark)) as avgmark "
			+ "FROM (SELECT * FROM students_lecturers WHERE dType = 'Student') sl "
			+ "LEFT JOIN marks m ON m.student_id = sl.id " 
			+ "GROUP BY sl.id, sl.name " 
			+ "ORDER BY AVG(m.mark) desc "
			, nativeQuery = true)
	List<AvgMark> findStudentsAvgMark();


	
	
	@Query(value = 	"SELECT "
			+ "* "
			+ "FROM students_lecturers "
			+ "WHERE dType = 'Student' AND id in ("
			+ "SELECT sl.id "
			+ "FROM students_lecturers sl "
			+ "LEFT JOIN marks m ON m.student_id = sl.id "
			+ "GROUP BY sl.id "
			+ "HAVING COUNT(m.mark) < :nMarks) "
			, nativeQuery = true)
	List<Student> findStudentsLessMark(int nMarks);
	
}
