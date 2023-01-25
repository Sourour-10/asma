package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.IEnqueteQuestionsService;
import tn.esprit.spring.entity.EnqueteQuestions;

@RestController
@RequestMapping("/EnqueteQuestions")
public class EnqueteQuestionsController {
	
	@Autowired
	IEnqueteQuestionsService EnqueteQuestionsService;

	// http://localhost:8089/EnqueteQuestions/create

	@PostMapping("create")
	@ResponseBody
	public void createEnqueteQuestions(@RequestBody EnqueteQuestions EnqueteQuestions) {
		EnqueteQuestionsService.createEnqueteQuestions(EnqueteQuestions);
	}

	// http://localhost:8089/EnqueteQuestions/getAllArticles
	@GetMapping("/getAllEnqueteQuestions")
	@ResponseBody
	public List<EnqueteQuestions> getAllEnqueteQuestions() {

		List<EnqueteQuestions> EnqueteQuestions = EnqueteQuestionsService.getAllEnqueteQuestions();
		return EnqueteQuestions;

	}
	// http://localhost:8089/EnqueteQuestions/delete/1

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int EnqueteQuestionsId) {

		EnqueteQuestionsService.deleteEnqueteQuestions(EnqueteQuestionsId);		
	}
	// http://localhost:8089/EnqueteQuestions/update/1

	@PutMapping("/update")
	public void updateEnqueteQuestions(@RequestBody EnqueteQuestions EnqueteQuestions) {
		EnqueteQuestionsService.updateEnqueteQuestions(EnqueteQuestions);
	}


}
