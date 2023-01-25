package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.IEnqueteQuestionsService;
import tn.esprit.spring.IService.IEnqueteService;
import tn.esprit.spring.entity.Enquete;
import tn.esprit.spring.entity.Event;

@RestController
@RequestMapping("/Enquete")
public class EnqueteController {

	@Autowired
	IEnqueteService enqueteService;

	
	//x
	@GetMapping("/remplirEnquete/{enqueteId}/{val1}/{val2}/{val3}")
	@ResponseBody
	public List<String> remplirEnquete(@PathVariable("enqueteId") int enqueteId, @PathVariable("val1") int val1,
			@PathVariable("val2") int val2, @PathVariable("val3") int val3) {
		
		List<String> messages = enqueteService.remplirEnquete(enqueteId, val1, val2, val3);
		return messages;

	}

	@PostMapping("create")
	@ResponseBody
	public void createEnquete() {
		enqueteService.createEnquete();
	}

}
