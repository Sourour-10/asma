package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IEnqueteQuestionsService;
import tn.esprit.spring.entity.EnqueteQuestions;
import tn.esprit.spring.repository.EnqueteQuestionsRepository;

@Service
public class EnqueteQuestionsService implements IEnqueteQuestionsService{

	@Autowired
	EnqueteQuestionsRepository enqueteQuestionsRepository ;
	
	@Override
	public void createEnqueteQuestions(EnqueteQuestions EnqueteQuestions) {
		enqueteQuestionsRepository.save(EnqueteQuestions);
	}

	@Override
	public void deleteEnqueteQuestions(int idEnqueteQuestions) {
		EnqueteQuestions EnqueteQuestions = enqueteQuestionsRepository.findById(idEnqueteQuestions).orElse(null);
		enqueteQuestionsRepository.delete(EnqueteQuestions);
	}

	@Override
	public List<EnqueteQuestions> getAllEnqueteQuestions() {
		List<EnqueteQuestions> EnqueteQuestions = (List<EnqueteQuestions>) enqueteQuestionsRepository.findAll();
		return EnqueteQuestions;

	}

	@Override
	public void updateEnqueteQuestions(EnqueteQuestions EnqueteQuestions) {
		enqueteQuestionsRepository.save(EnqueteQuestions);

	}

	/*
	 * @Override public List<User> getUsersByArticles(int idArticle) { // TODO
	 * Auto-generated method stub return null; }
	 */

}
