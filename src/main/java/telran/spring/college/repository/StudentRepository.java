package telran.spring.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.college.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
