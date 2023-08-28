package telran.spring.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.college.entity.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

	
}
