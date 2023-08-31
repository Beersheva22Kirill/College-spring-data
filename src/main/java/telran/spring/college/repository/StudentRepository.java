package telran.spring.college.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import telran.spring.college.dto.AvgMark;
import telran.spring.college.dto.IdName;
import telran.spring.college.dto.SubjectType;
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
	
	
	@Query("SELECT "
			+ "student.id as id, "
			+ "student.name as name "
			+ "FROM Mark "
			+ "GROUP BY student.id, student.name "
			+ "HAVING COUNT(mark) > :nMarksThreshold AND AVG(mark) > ("
			+ "SELECT "
			+ "AVG(mark) as avg_mark "
			+ "FROM Mark) "
			+ "ORDER BY AVG(mark) desc ")
	List<IdName> findStudentsAvgMarksGreaterCollegeAvg(int nMarksThreshold);

	@Query(value = "SELECT "
			+ "sl.id as id,"
			+ "sl.name as name,"
			+ "ROUND(AVG(m.mark)) as avgmark "
			+ "FROM (SELECT * FROM students_lecturers WHERE dType = 'Student') sl "
			+ "LEFT JOIN marks m ON m.student_id = sl.id " 
			+ "GROUP BY sl.id, sl.name " 
			+ "ORDER BY AVG(m.mark) desc ", nativeQuery = true)
	List<AvgMark> findStudentsAvgMark();

	@Query("SELECT s FROM Student s WHERE size(marks) < :nMarks")
	List<Student> findStudentsLessMark(int nMarks);
	
	@Modifying
	@Query("DELETE FROM Student WHERE size(marks) < :nMarks ")
	void removeStudentsLessMark(int nMarks);
	
	List<IdName> findDistinctByMarksSubjectSubjectTypeAndMarksMarkGreaterThanOrderById(SubjectType type, int mark);
	
	
	
		
	
}
