package telran.spring.college.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.PersonDto;

@Entity
@NoArgsConstructor
@Data
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) Annotation for all entity extended in a separate table
@Table(name = "students_lecturers")
public abstract class Person {
	@Id
	Long id;
	String name;
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	LocalDate birthDate;
	@Column(nullable = true)
	String city;
	@Column(nullable = true)
	String phone;
	
	protected Person(PersonDto person) {
		id = person.getId();
		name = person.getName();
		birthDate = LocalDate.parse(person.getBirthDate());
		city = person.getCity();
		phone = person.getPhone();
	}
	
	public PersonDto build() {
		return new PersonDto(id, name, birthDate.toString(), city, phone);
	}
}
