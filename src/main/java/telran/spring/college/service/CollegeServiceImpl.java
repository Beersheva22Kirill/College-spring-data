package telran.spring.college.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.*;
import telran.spring.college.entity.Lecturer;
import telran.spring.college.entity.Mark;
import telran.spring.college.entity.Student;
import telran.spring.college.entity.Subject;
import telran.spring.college.repository.*;
import telran.spring.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CollegeServiceImpl implements CollegeService {

	final StudentRepository studentRepo;
	final SubjectRepository subjectRepo;
	final LecturerRepository lecturerRepo;
	final MarkRepository markRepo;
	@Value("${app.person.id.minimum:100000}")
	long minId;
	@Value("${app.person.id.minimum:999999}")
	long maxId;
	
	@Override
	@Transactional(readOnly=false)
	public PersonDto addStudent(PersonDto person) {
		if(person.getId() == null) {
			person.setId(getStudentUniqueId());
		}
		
		Student student = Student.of(person);
		if (studentRepo.existsById(student.getId())) {
			throw new IllegalStateException("Student with given id already exists" + student.getId());
		}
		PersonDto res = studentRepo.save(student).build();
		log.debug("Student added {}", res);
		return res;
	}

	private  Long getStudentUniqueId() {
		return getId(studentRepo);
	}

	private <T> Long getId(JpaRepository<T, Long> repository) {
		long res = 0;
		do {
			res = new Random().nextLong(minId, maxId + 1);
		}while(repository.existsById(res));
		return res;
	}

	@Override
	@Transactional(readOnly=false)
	public PersonDto addLecturer(PersonDto person) {
		if(person.getId() == null) {
			person.setId(getLecturerUniqueId());
		}
		Lecturer lecturer = Lecturer.of(person);
		if (lecturerRepo.existsById(lecturer.getId())) {
			throw new IllegalStateException("Lecturer with given id already exists" + lecturer.getId());
		}
		PersonDto res = lecturerRepo.save(lecturer).build();
		log.debug("Lecturer added {}", res);
		return res;
	}

	private Long getLecturerUniqueId() {
		
		return getId(lecturerRepo);
	}

	@Override
	@Transactional(readOnly=false)
	public SubjectDto addSubject(SubjectDto subject) {
		if (subjectRepo.existsById(subject.getId())) {
			throw new IllegalStateException("Subject with given id exists " + subject.getId());
		}
		Lecturer lecturer = null;
		Long lecturerId = subject.getLecturerId();
		if(lecturerId != null) {
			lecturer = lecturerRepo.findById(lecturerId).orElseThrow(() ->
			new NotFoundException(String.format("Lecturer with id %d doen't exist", lecturerId)));
		}
		Subject subjectEntity = Subject.of(subject);
		subjectEntity.setLecturer(lecturer);
		SubjectDto res = subjectRepo.save(subjectEntity).build();
		log.debug("subject added {}", res);
		return res;
	}

	@Override
	@Transactional(readOnly=false)
	public MarkDto addMark(MarkDto mark) {
		long studentId = mark.getStudentId();
		Student student = studentRepo.findById(studentId).orElseThrow(() ->
		new NotFoundException(String.format("Student with id %d doesn't exist in DB", studentId)));
		String subjectId = mark.getSubjectId();
		Subject subject = subjectRepo.findById(subjectId)
				.orElseThrow(() -> new NotFoundException(String.format("Subject with id %s doesn't exist in DB", subjectId)));
		Mark markEntity = new Mark(student, subject, mark.getMark());
		MarkDto res = markRepo.save(markEntity).build();
		log.debug("Mark added {}", res);
		return res;
	}

	@Override
	public List<IdName> bestStudentsOfLecturer(long lecturerId, int nStudents) {
	
		return studentRepo.findBestStudentsOfLecturer(lecturerId, nStudents);
	}

	@Override
	public List<IdName> studentsAvgMarksGreaterCollegeAvg(int nMarksThreshold) {
		
		return studentRepo.findStudentsAvgMarksGreaterCollegeAvg(nMarksThreshold);
	}

	@Override
	public List<AvgMark> studentsAvgMark() {
		
		return studentRepo.findStudentsAvgMark();
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto updateHours(String subjectId, int hours) {
		Subject subject = subjectRepo.findById(subjectId)
				.orElseThrow(() -> new NotFoundException(String.format("Subject with id:%s not found", subjectId)));
		subject.setHours(hours);
		log.debug("Subject updated: hours - {}",subject.getHours());
		return subject.build();
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto updateLecturer(String subjectId, Long lecturerId) {
		Subject subject = subjectRepo.findById(subjectId)
				.orElseThrow(() -> new NotFoundException(String.format("Subject with id:%s not found", subjectId)));
		Lecturer lecturer = null;
		if(lecturerId != null) {
			lecturer = lecturerRepo.findById(lecturerId)
					.orElseThrow(() -> new NotFoundException(String.format("Lecturer with id:%d not found", lecturerId)));
		} 
		
		subject.setLecturer(lecturer);
		log.debug("Subject updated: id lecturer - {} ",lecturer.getId());
		
		return subject.build();
	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> removeStudentsNoMarks() {
		return removeStudentLessMarks(1);
	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> removeStudentLessMarks(int nMarks) {
		List<Student> students = studentRepo.findStudentsLessMark(nMarks);

		students.forEach(s -> {	
			log.debug("Student with id:{} is going to be deleted",s.getId());
			studentRepo.delete(s);	
		});
		log.debug("{} students are going to be deleted",students.size());
		return students.stream().map(Student :: build).toList();
	}

}
