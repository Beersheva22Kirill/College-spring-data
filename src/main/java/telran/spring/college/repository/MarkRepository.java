package telran.spring.college.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.college.entity.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

	@Query(value = "SELECT "
			+ "* "
			+ "FROM marks m "
			+ "WHERE m.student_id = :studentId ", 
			nativeQuery = true)
	List<Mark> findMarkByStudentId(Long studentId);

}
