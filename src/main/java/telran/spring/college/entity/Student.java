package telran.spring.college.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import telran.spring.college.dto.PersonDto;

@Entity
public class Student extends Person {
	
	public Student() {
		
	}

	private Student(PersonDto person) {
		super(person);
	}
	
	public static Student of(PersonDto person) {
		
		return new Student(person);
	}
	
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	List<Mark> marks;

	public List<Mark> getMarks() {
		return marks;
	}
	
}
