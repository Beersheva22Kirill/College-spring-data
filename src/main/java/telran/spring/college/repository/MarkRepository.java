package telran.spring.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.college.entity.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

}
