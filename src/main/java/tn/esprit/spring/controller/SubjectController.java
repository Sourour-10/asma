package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.ISubjectService;
import tn.esprit.spring.entity.Subject;
import tn.esprit.spring.repository.SubjectRepository;

@RestController
@RequestMapping("/Subject")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class SubjectController {
	//Anas add
	@Autowired
	SubjectRepository subjectRepository ;
	
	//
	
	@Autowired
	ISubjectService subjectService;

	// http://localhost:8089/Subject/create

	@PostMapping("create")
	@ResponseBody
	public void createSubject(@RequestBody Subject subject) {
		subjectService.createSubject(subject);
	}

	// http://localhost:8089/Subject/getAllSubjects
	@GetMapping("/getAllSubjects")
	@ResponseBody
	public List<Subject> getAllSubjects() {

		List<Subject> subjects = subjectService.getAllSubjects();
		return subjects;

	}
	// http://localhost:8089/Subject/delete/1

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int subjectId) {

		subjectService.deleteSubject(subjectId);
	}
	// http://localhost:8089/Subject/update/1

	@PutMapping("/update")
	public void updateSubject(@RequestBody Subject subject) {
		subjectService.updateSubject(subject);
	}

	// http://localhost:8089/Subject/proposeSubject

	@PostMapping("/proposeSubject")
	@ResponseBody
	public void proposeSubject(@RequestBody Subject subject) {

		subjectService.proposeSubject(subject);
	}
//http://localhost:8089/Subject/approve
	@PutMapping("/approve/{id}")
	public void approveSubject(@PathVariable("id")int subjectId ) {
		subjectService.approveSubjectByAdmin(subjectId);
	}
	
	// http://localhost:8089/Subject/getSubjectById/{id}
		@GetMapping("/getSubjectById/{id}")
		@ResponseBody
		public Subject getSubjectById(@PathVariable("id")int subjectId ) {

			Subject subject = subjectRepository.findById(subjectId).orElse(null) ;
			return subject;

		}

	
}
