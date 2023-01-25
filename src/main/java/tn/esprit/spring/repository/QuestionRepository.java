package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Questions;

@Repository
public interface QuestionRepository  extends JpaRepository<Questions ,Integer>  {
	  List<Questions> findByQuizId(long quizId);
}
