package telran.spring.college.batch;

import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.dto.SubjectType;
import telran.spring.college.service.CollegeService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RandomDbCreation {
	
	final CollegeService collegeService;
	@Value("${spring.jpa.hibernate.ddl-auto:update}")
	String creationDb;
	@Value("${app.random.students.amount:100}")
	int nStudents;
	@Value("${app.random.subjects.amount:5}")
	int nSubjects;
	@Value("${app.random.lecturers.amount:5}")
	int nLecturers;
	@Value("${app.random.students.amount:500}")
	int nMarks;
	@Value("${app.random.students.initial-id:100000}")
	int initialStudentId;
	@Value("${app.random.lecturers.initial-id:500000}")
	int initialLecturerId;
	@Value("${app.random.cities.amount:10}")
	int nCities;
	int countErrors = 0;
	
	@PostConstruct
	void createDB() {
		
		if (creationDb.equals("create")) {
			log.info("Creation random databases begin");
				createStudents();
				log.info("Creation random student finished");
				createLecturers();
				log.info("Creation random lecturers finished");
				createSubjects();
				log.info("Creation random subjects finished");
				createMarks();
				log.info("Creation random marks finished");
			} 
			if (countErrors > 0) {
				log.warn("DB created with {} exceptions", countErrors);
			} else {
				log.info("DB created with out errors");
			}
	}


	private void createStudents() {
		IntStream.rangeClosed(initialStudentId, initialStudentId + nStudents).mapToObj(index -> getRandomPerson(index))
		.forEach(student -> {
			try {
				collegeService.addStudent(student);
			} catch (Exception e) {
				log.error("error: {} ", e);
				countErrors++;	
			}
		});
		
	}
	
	private void createLecturers() {
		IntStream.rangeClosed(initialLecturerId, initialLecturerId + nLecturers).mapToObj(index -> getRandomPerson(index))
		.forEach(lecturer -> {
			try {
				collegeService.addLecturer(lecturer);
			} catch (Exception e) {
				log.error("error: {} ", e);
				countErrors++;	
			}
		});
		
	}
	
	private PersonDto getRandomPerson(long index) {
		String name = "name" + index;
		String birthDate = getRandomDate();
		String city = "city" + getRandomNumber(1, nCities);
		String phone = getRandomPhone();
		return new PersonDto(index, name, birthDate, city, phone);
	}


	private String getRandomPhone() {
		String code = "05" + getRandomNumber(0, 10);
		int number = getRandomNumber(1000000, 10000000);
		return code + "-" + number;
	}


	private String getRandomDate() {
		int year = getRandomNumber(1990, 2003);
		int mounth = getRandomNumber(1, 12);
		int day = getRandomNumber(1, 28);
		
		return LocalDate.of(year, mounth, day).toString();
	}


	private void createSubjects() {
		IntStream.rangeClosed(1, nSubjects).mapToObj(i -> getRandomSubject(i)).forEach(subject -> {
			try {
				collegeService.addSubject(subject);
			} catch (Exception e) {
				log.error("error: {} ", e);
				countErrors++;		
			}
		});
		
	}
	
	private SubjectDto getRandomSubject(int index) {
		
		String id = "S" + index;
		String name = "subject " + id;
		int hours = getRandomNumber(50, 500);
		Long lecturer = randomLecturerId();
		SubjectType subjectType = randomSubjectType();
		
		return new SubjectDto(id , name, hours, lecturer, subjectType);
	}


	private SubjectType randomSubjectType() {
		SubjectType[] types = {SubjectType.BACK_END,SubjectType.FRONT_END,SubjectType.QUALITY};
		
		return types[getRandomNumber(0, 2)];
	}


	private Long randomLecturerId() {
		
		return (long) getRandomNumber(initialLecturerId, initialLecturerId + nLecturers + 1);
	}


	private void createMarks() {
		Stream.generate(() -> createRandomMark()).limit(nMarks).forEach(mark -> {
			try {
				collegeService.addMark(mark);
			} catch (Exception e) {
				log.error("error: {} ", e);
				countErrors++;
				
			}
		});
	}


	private MarkDto createRandomMark() {
			long studentId = randomStudentId();
			String subjectId = randomSubjectId();
			int mark = randomScore();
		
		return new MarkDto(null, studentId, subjectId, mark);
	}


	private int randomScore() {
		
		return getRandomNumber(60,100);
	}


	private int getRandomNumber(int min, int max) {
		
		return new Random().nextInt(min,max);
	}


	private String randomSubjectId() {
		
		return "S" + getRandomNumber(1, nSubjects);
	}


	private long randomStudentId() {
		
		return getRandomNumber(initialStudentId, initialStudentId + nStudents);
	}

}
