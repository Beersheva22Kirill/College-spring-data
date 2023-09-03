package telran.spring.college.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.college.dto.QueryDto;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.service.CollegeService;

@RestController
@CrossOrigin
@RequestMapping("college")
public class JPQLConsoleController {
	
	@Autowired
	CollegeService service;
	
	@PostMapping("/jpql")
	public List<String> jpqlQuery(@RequestBody QueryDto queryDto) {
		
		return service.jpqlQuery(queryDto.getQuery());
				
		
	}
	
	@PutMapping("/subjects/hours/{subjectId}")
	public SubjectDto updateHours(@RequestParam(name = "hours") int hours, @PathVariable String subjectId) {
		
		return service.updateHours(subjectId, hours);
	}
	
	
			

}
