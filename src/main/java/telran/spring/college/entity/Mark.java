package telran.spring.college.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.MarkDto;

@Entity
@Table(name = "marks")
@NoArgsConstructor
public class Mark {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	Student student;
	@ManyToOne
	@JoinColumn(name = "subject_id", nullable = false)
	Subject subject;
	@Column(nullable = false)
	int mark;
	
	public Mark(Student student, Subject subject, int mark) {
		this.student = student;
		this.subject = subject;
		this.mark = mark;
	}
	
	public MarkDto build() {
		
		return new MarkDto(id, student.id, subject.id, mark);	
	}

	public int getId() {
		return id;
	}

	public Student getStudent() {
		return student;
	}

	public Subject getSubject() {
		return subject;
	}

	public int getMark() {
		return mark;
	}
	
	
	
	
	

}
