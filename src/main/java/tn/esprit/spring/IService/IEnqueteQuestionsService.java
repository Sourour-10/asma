package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.EnqueteQuestions;

public interface IEnqueteQuestionsService {

	void createEnqueteQuestions(EnqueteQuestions EnqueteQuestions);

	void deleteEnqueteQuestions(int idEnqueteQuestions);

	List<EnqueteQuestions> getAllEnqueteQuestions();

	void updateEnqueteQuestions(EnqueteQuestions EnqueteQuestions);

}
