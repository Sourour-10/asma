package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.EnqueteQuestions;

@Repository
public interface EnqueteQuestionsRepository extends CrudRepository<EnqueteQuestions,Integer>{

}
