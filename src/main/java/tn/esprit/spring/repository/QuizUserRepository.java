package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.QuizzesUser;

@Repository
public interface QuizUserRepository extends JpaRepository<QuizzesUser, Long> {
	 List<QuizzesUser> findByUserId(int userId);


	 QuizzesUser findByQuizIdAndUserId(long quizId, int userId);


	    List<QuizzesUser> findByQuizId(long quizId);

	    // to eliminate the students who haven't attempted the quiz
	    @Query("SELECT AVG(marksObtained) FROM QuizzesUser WHERE quizId=?1 AND marksObtained >= 0")
	    float getQuizAvg(long quizId);

	    @Query("SELECT userId FROM QuizzesUser WHERE quizId=?1 AND marksObtained>=0 ORDER BY marksObtained DESC")
	    int[] getLeaderboardByQuiz(long quizId);


	    int countAllByQuizId(long quizId);


	    @Query("SELECT COUNT(userId) FROM QuizzesUser WHERE quizId=?1 AND marksObtained >= 0")
	        // marks = -3 if quiz is unattempted
	    int getAttemptedCountByQuizId(long quizId);


	    @Query("SELECT marksObtained, COUNT(marksObtained)  FROM QuizzesUser WHERE quizId=?1 AND marksObtained>=0 GROUP BY marksObtained")
	    long[][] getMarksAndFrequency(long quizId);
}
