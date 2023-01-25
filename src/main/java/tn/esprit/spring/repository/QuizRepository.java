package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

}
