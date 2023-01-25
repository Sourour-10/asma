package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Training;

public interface ITrainingService {

	void createTraining(Training training);

	void addAndAffectTrainingToCoach(Training training, int idCoach);

	double getIncomeByTraining(int idTraining);
	
	void getNbrUserByTraining();
	
	void affectUserToTraining(int idUser, int idTraining);

	void deleteTraining(int idTraining);

	void updateTraining(Training training);

	public List<Training> getAllTraining();

}
