package telran.spring.college.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.PersonDto;

@Entity
@NoArgsConstructor
public class Lecturer extends Person {

	private Lecturer(PersonDto person) {
		super(person);
	}
	
	public static Lecturer of(PersonDto person) {
		
		return new Lecturer(person);
	}
	
	@OneToMany(mappedBy = "lecturer")
	List<Subject> subjects;

}
