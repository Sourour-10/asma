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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import tn.esprit.spring.IService.IReclamationService;

import tn.esprit.spring.entity.Reclamation;

@RestController
@RequestMapping("/Reclamation")
public class ReclamationController {
	@Autowired
	IReclamationService ReclamationService;


	// http://localhost:8089/Reclamation/create/1/1/ReportPost

	@PostMapping("create/{num}/{UserId}/{complaintSubject}")
	@ResponseBody
	public void createReclamation(@PathVariable("UserId") int UserId,@PathVariable("num") int num,@PathVariable(value="complaintSubject")String complaintSubject ,@RequestBody String Content) {
		ReclamationService.createReclamation(UserId, num,complaintSubject,Content);
	}

	// http://localhost:8089/Badge/getAllBadges
	@JsonIgnoreProperties({ "user" })
	@GetMapping("/getAllReclamations")
	@ResponseBody
	public List<Reclamation> getAllReclamations() {

		List<Reclamation> Reclamation = ReclamationService.getAllReclamations();
		return Reclamation;

	}
	// http://localhost:8089/Reclamation/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteReclamation(@PathVariable("id") int ReclamationId) {

		ReclamationService.deleteReclamation(ReclamationId);		
	}
	// http://localhost:8089/Badge/update/1

	@PutMapping("/update")
	public void updateReclamation(@RequestBody Reclamation Reclamation) {
		ReclamationService.updateReclamation(Reclamation);
	}
	
	// http://localhost:8089/Reclamation/complaintsSystemDecision
	@GetMapping("/complaintsSystemDecision/{UserId}")
	@ResponseBody
	public void complaintsSystemDecision(@PathVariable("UserId") int UserId) {
		ReclamationService.complaintsSystemDecision(UserId);

	}

	// http://localhost:8089/Reclamation/getReclamationById
	@GetMapping("/getReclamationById/{recId}")
	@ResponseBody
	public Reclamation getReclamationById(@PathVariable("recId") int recId) {
		return ReclamationService.getReclamationById(recId);
		
	}

}
