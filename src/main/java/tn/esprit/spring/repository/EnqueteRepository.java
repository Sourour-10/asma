package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Enquete;
import tn.esprit.spring.entity.EnqueteQuestions;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.TypeQuestion;

@Repository
public interface EnqueteRepository extends CrudRepository<Enquete,Integer>{

	@Query("SELECT q FROM EnqueteQuestions q WHERE q.typeQuestion = ?1 ")
	List<EnqueteQuestions> findEnqueteQuestionByTypeQuestion(TypeQuestion typeQuestion);

	
}
