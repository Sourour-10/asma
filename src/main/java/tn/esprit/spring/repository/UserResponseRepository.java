package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.UserResponse;


@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
	  
	@Query("SELECT DISTINCT userId FROM UserResponse WHERE TrainingId=?1")
	    List<Integer> getAllUserIdByTrainingId(int trainingId);

	    List<UserResponse> findByUserIdAndQuizId(int userId, long quizId);
}
