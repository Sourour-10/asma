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

import tn.esprit.spring.IService.ICollaborationService;
import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.repository.*;


@RestController
@RequestMapping("/Collaboration")
public class CollaborationController {
	@Autowired
	ICollaborationService CollaborationService;
	@Autowired
	ReactRepository ReactRepository;

	// http://localhost:8089/Collaboration/create

	@PostMapping("create")
	@ResponseBody
	public void createCollaboration(@RequestBody Collaboration Collaboration) {
		CollaborationService.createCollaboration(Collaboration);
	}

	// http://localhost:8089/Collaboration/getAllCollaborations
	@GetMapping("/getAllCollaborations")
	@ResponseBody
	public List<Collaboration> getAllCollaborations() {

		List<Collaboration> Collaborations = CollaborationService.getAllCollaborations();
		return Collaborations;

	}
	// http://localhost:8089/Collaboration/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteCollaboration(@PathVariable("id") int CollaborationId) {

		CollaborationService.deleteCollaboration(CollaborationId);		
	}
	// http://localhost:8089/Collaboration/update/

	@PutMapping("/update")
	public void updateCollaboration(@RequestBody Collaboration Collaboration) {
		CollaborationService.updateCollaboration(Collaboration);
	}
	//http://localhost:8089/Collaboration/getLikesByActivity
	@GetMapping("/getLikesByActivity")
	@ResponseBody
	public void getLikesByActivity(){
		CollaborationService.getLikesByActivity();
	}
	
	//http://localhost:8089/Collaboration/getColabById
	@GetMapping("/getColabById/{colabId}")
	@ResponseBody
	public Collaboration getColabById(@PathVariable("colabId") int colabId) {
		return CollaborationService.getColabById(colabId);
	}
	
	
	
	//Anas
	//http://localhost:8089/Collaboration/sortCollabByRatting
	@GetMapping("/sortCollabByRatting")
	@ResponseBody
	public List<String> sortCollabByRatting() {

		List<String> collaborations = CollaborationService.sortByPoints();
		return collaborations;

	} 	
	
}
