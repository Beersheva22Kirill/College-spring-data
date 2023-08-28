package telran.spring.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.college.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {

}
