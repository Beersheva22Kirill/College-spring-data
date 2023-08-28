package telran.spring.college.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.SubjectDto;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {
	@Id
	String id;
	String name;
	int hours;
	@ManyToOne
	@JoinColumn(name = "lecturer_id",nullable = true)
	Lecturer lecturer;
	
	public SubjectDto build() {
		return new SubjectDto(id, name, hours, lecturer == null ? null : lecturer.id);
	}
	
	public static Subject of(SubjectDto subject) {
		return new Subject(subject.getId(), subject.getName(), subject.getHours(), null);
	}

}
