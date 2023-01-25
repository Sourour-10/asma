package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import tn.esprit.spring.IService.ITrainingService;
import tn.esprit.spring.entity.Coach;

import tn.esprit.spring.entity.Training;
import tn.esprit.spring.repository.CoachRepository;

import tn.esprit.spring.repository.TrainingRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class TrainingService implements ITrainingService {

	@Autowired
	TrainingRepository trainingRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CoachRepository coachRepository;

	@Override
	public void addAndAffectTrainingToCoach(Training training, int idCoach) {
		// TODO Auto-generated method stub

		Coach coach = coachRepository.findById(idCoach).orElse(null);
		training.setCoach(coach);
		trainingRepository.save(training);
	}

	@Override
	public double getIncomeByTraining(int idTraining) {
		Training training = trainingRepository.findById(idTraining).orElse(null);
		return (training.getUsers().size() * training.getCosts());

	}

	@Override
	public void affectUserToTraining(int idUser, int idTraining) {
		// TODO Auto-generated method stub

				
		Training training=trainingRepository.findById(idTraining).orElse(null);
		if(training.getMaxParticipant()>training.getUsers().size()){
			
			training.getUsers().add(userRepository.findById(idUser).orElse(null));
		
		}
		trainingRepository.save(training);
		
		
		
	}

	@Override
	public List<Training> getAllTraining() {
		
		return  (List<Training>) trainingRepository.findAll();
	}

	@Override
	public void updateTraining(Training training) {
		trainingRepository.save(training);

	}

	@Override
	public void createTraining(Training training) {
		trainingRepository.save(training);

	}

	@Override
	public void deleteTraining(int idTraining) {
		
		Training training = trainingRepository.findById(idTraining).orElse(null);
		trainingRepository.delete(training);

	}
	
	@Override
	public void getNbrUserByTraining() {
		// TODO Auto-generated method stub 
		List<Training> trainings= (List<Training>) trainingRepository.findAll();
		for(Training training:trainings){
			System.out.println("the training :"+training.getTitle()+" contains :"+training.getUsers().size()+"employee" );
		}
		
	}
	
	
	
	
	
	
	
		
	}

	


