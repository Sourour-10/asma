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
import tn.esprit.spring.IService.IPublicityService;
import tn.esprit.spring.entity.Publicity;

@RestController
@RequestMapping("/Publicity")
public class PublicityController {
	@Autowired
	IPublicityService PublicityService;

	// http://localhost:8089/Publicity/create

	@PostMapping("create")
	@ResponseBody
	public void createPublicity(@RequestBody Publicity Publicity) {
		PublicityService.createPublicity(Publicity);
	}

	// http://localhost:8089/Publicity/getAllPublicitys
	@GetMapping("/getAllPublicities")
	@ResponseBody
	public List<Publicity> getAllPublicities() {

		List<Publicity> Publicity = PublicityService.getAllPublicities();
		return Publicity;

	}
	// http://localhost:8089/Publicity/delete/1

	@DeleteMapping("/delete/{id}")
	public void deletePublicity(@PathVariable("id") int PublicityId) {

		PublicityService.deletePublicity(PublicityId);		
	}
	// http://localhost:8089/Publicity/update/1

	@PutMapping("/update")
	public void updatePublicity(@RequestBody Publicity Publicity) {
		PublicityService.updatePublicity(Publicity);
	}


}
