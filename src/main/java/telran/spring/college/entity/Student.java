package telran.spring.college.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.PersonDto;

@Entity
@NoArgsConstructor
public class Student extends Person {

	private Student(PersonDto person) {
		super(person);
	}
	
	public static Student of(PersonDto person) {
		
		return new Student(person);
	}
	
}
