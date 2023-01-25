package tn.esprit.spring.IService;

import java.util.HashMap;
import java.util.List;



import tn.esprit.spring.entity.Questions;
import tn.esprit.spring.entity.Quiz;
import tn.esprit.spring.entity.QuizAnalysis;
import tn.esprit.spring.entity.QuizzesUser;


public interface IQuizService {
	   List<Questions> addQuestionsForQuiz(List<Questions> questions, long quizId);
	   public Quiz   addQuiz(Quiz quiz);
	   Questions addQuestionForQuiz(Questions question);
	   Quiz getQuizById(long quizId);
	   HashMap<String, List<Quiz>> getQuizzesByUserId(int userId);
	   List<Questions> getQuestionsByQuizId(long quizId);
	   void evaluateResult(long quizId, int trainingId);
	   QuizzesUser getQuizUserByQuizIdAndUserId(long quizId, int userId);
	   QuizAnalysis getQuizAnalysis(long quizId);
}
