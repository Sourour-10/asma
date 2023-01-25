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


import tn.esprit.spring.IService.ITrainingService;


import tn.esprit.spring.entity.Training;



@RestController
@RequestMapping("/Training")
public class TrainingController {
	
	@Autowired
	ITrainingService trainingService;
	
	

	@PostMapping("/addAndAffect/{idCoach}")
	@ResponseBody
	public void addAndAffect(@RequestBody Training training, @PathVariable("idCoach") int idCoach ){
		trainingService.addAndAffectTrainingToCoach(training, idCoach);
	}
	

	@GetMapping("/getIncome/{idTraining}")
	public double getRevenueByformation(@PathVariable("idTraining")int idTraining){
		
		return trainingService.getIncomeByTraining(idTraining);
		
	}

@PutMapping("/affecterApprenantToForamtion/{idTraining}/{idUser}")
@ResponseBody
public void addEmployeeToFormation(@PathVariable("idTraining")int idTraining,@PathVariable("idUser")int idUser ){
	trainingService.affectUserToTraining(idUser, idTraining);
}


@PutMapping("/update")
public void updateCoach(@RequestBody Training  training) {
	trainingService.updateTraining(training);
}
@DeleteMapping("/delete/{idTraining}")
public void delete(@PathVariable("idTraining") int idTraining) {

	trainingService.deleteTraining(idTraining);
}
	
@GetMapping("/getAllTraining")
@ResponseBody
public List<Training> getAllTrainings() {

	List<Training> trainings = trainingService.getAllTraining();
	return trainings;

}


}
