package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.ICoachService;
import tn.esprit.spring.IService.ITrainingService;

import tn.esprit.spring.entity.Coach;
import tn.esprit.spring.entity.Training;
import tn.esprit.spring.repository.CoachRepository;
import tn.esprit.spring.repository.TrainingRepository;

@RequestMapping("/Coach")
@RestController
public class CoachController {

	@Autowired
	ICoachService coachService;
	

	@Autowired
	TrainingRepository trainingRepository;
	
	@Autowired
	CoachRepository coachRepository;

	@Autowired
	ITrainingService trainingService;

	@PostMapping("/add-Coach")
	@ResponseBody
	public void addCoach(@RequestBody Coach coach) {
		coachService.addCoach(coach);

	}

	@DeleteMapping("/delete/{idCoach}")
	public void delete(@PathVariable("idCoach") int idCoach) {

		coachService.deleteCoach(idCoach);
	}
	

	@PutMapping("/update")
	public void updateCoach(@RequestBody Coach coach) {
		coachService.updateCoach(coach);
	}

	@GetMapping("/findAll")
	@ResponseBody
	public List<Coach> gettAll() {
		return coachService.getAllCoach();

	}

	@GetMapping("findbyDate/{idCoach}/{startDate}/{endDate}")
	public int getCoachRemunerationByDate(@PathVariable("idCoach") int idCoach,
			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		List<Training> trainings =  trainingService.getAllTraining();
		int sum = 0;
	
		Coach coach = coachRepository.findById(idCoach).orElse(null);
		for (Training training : trainings) {
			if ((training.getStartDate().after(startDate)) && (training.getStartDate().before(endDate))) {
				if (training.getCoach().getCoachId() == idCoach) {
					sum = coach.getHourlyRate() * training.getHoursNumbers();
				}
			}
		}

		return sum;
	}

}
