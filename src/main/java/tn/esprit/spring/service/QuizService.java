package tn.esprit.spring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import tn.esprit.spring.IService.IQuizService;
import tn.esprit.spring.entity.Questions;
import tn.esprit.spring.entity.Quiz;
import tn.esprit.spring.entity.QuizAnalysis;
import tn.esprit.spring.entity.QuizzesUser;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.UserResponse;
import tn.esprit.spring.repository.QuestionRepository;
import tn.esprit.spring.repository.QuizRepository;
import tn.esprit.spring.repository.QuizUserRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.UserResponseRepository;

@Service
public class QuizService implements IQuizService {
	
	
	 @Autowired
	    private UserRepository userRepo;
	    @Autowired
	    private QuizUserRepository quizzesUserRepo;
	    @Autowired
	    private QuizRepository quizRepo;
	    @Autowired
	    private QuestionRepository questionsRepo;
	    @Autowired
	    private UserResponseRepository userResponseRepo; // for handling answers

	    // For Teachers
	    public Quiz   addQuiz(Quiz quiz) {
	        return quizRepo.save(quiz);
	    }

	  

	    public List<Questions> addQuestionsForQuiz(List<Questions> questions, long quizId) {
	        for (Questions question : questions) {
	            question.setQuizId(quizId);
	        }
	        return questionsRepo.saveAll(questions);
	    }

	    public Questions addQuestionForQuiz(Questions question) {
	        return questionsRepo.save(question);
	    }


	    // For Students
	    public Quiz getQuizById(long quizId) {
	        return quizRepo.findById(quizId).orElse(null);
	    }
	    /**
	     * Fetches the records by the roll from the quizStudent table, then gets the required quizzes by matching the respective quizId
	     *
	     * @param roll Roll no of the user
	     */
	    public HashMap<String, List<Quiz>> getQuizzesByUserId(int userId) {


	        List<QuizzesUser> quizUserInfos = quizzesUserRepo.findByUserId(userId);
	        List<Quiz> missedQuizzes = new ArrayList<>();
	        List<Quiz> attemptedQuizzes = new ArrayList<>();
	        List<Quiz> liveQuizzes = new ArrayList<>();
	        List<Quiz> upcomingQuizzes = new ArrayList<>();

	        // Finding the quizzes by matching the id from the info and adding it to the quizzes list
	        for (QuizzesUser info : quizUserInfos) {
	            Quiz quiz = quizRepo.findById(info.getQuizId()).orElse(null);
	            int marksObtained = info.getMarksObtained();


	            if (quiz != null) {
	                // Classifying quizzes according to their types

	                if (marksObtained >= 0) {
	                    attemptedQuizzes.add(quiz);
	                }
	                // for unattempted quizzes marks = -3
	                else if (marksObtained == -3) {

	                    LocalDateTime now = LocalDateTime.now();
	                    // if the current datetime is after the start time and before the end time
	                    if (now.isAfter(quiz.getDateTimeFrom()) && now.isBefore(quiz.getDateTimeTo())) {
	                        liveQuizzes.add(quiz);
	                    } else if (now.isBefore(quiz.getDateTimeFrom())) { // if current time is before the start time
	                        upcomingQuizzes.add(quiz);
	                    } else if (now.isAfter(quiz.getDateTimeTo())) { // if current time is after the start time
	                        missedQuizzes.add(quiz);
	                    }

	                }
	            }
	        }

	        HashMap<String, List<Quiz>> allSortedQuizzes = new HashMap<>();
	        allSortedQuizzes.put("missed", missedQuizzes);
	        allSortedQuizzes.put("attempted", attemptedQuizzes);
	        allSortedQuizzes.put("live", liveQuizzes);
	        allSortedQuizzes.put("upcoming", upcomingQuizzes);
	        return allSortedQuizzes;
	    }

	    /**
	     * Gets the question of the desired quiz
	     *
	     * @param quizId id of the quiz whose questions you want
	     * @return List of questions
	     */
	    public List<Questions> getQuestionsByQuizId(long quizId) {
	        return questionsRepo.findByQuizId(quizId);
	    }


	    // Additional functions

	    /**
	     * Evaluates the result of a particular class of the school
	     *
	     * @param schoolClass Class whose result is to be evaluated
	     */
	    public void evaluateResult(long quizId, int userId) {
	       
	        List<Questions> questions = questionsRepo.findByQuizId(quizId);

	        
	            // Calculating score of user
	            List<UserResponse> answers = userResponseRepo.findByUserIdAndQuizId(userId, quizId);
	            int score = getIndividualScore(questions, answers);

	           
	            QuizzesUser quizzesUser = quizzesUserRepo.findByQuizIdAndUserId(quizId, userId);
	            quizzesUser.setMarksObtained(score);
	            quizzesUserRepo.save(quizzesUser);
	        

	    }

	    private int getIndividualScore(List<Questions> questions, List<UserResponse> answers) {
	    	 int score = 0;
	         // matching the ans for each question
	        
	        	 for (Questions question : questions)
	        	    {
	        	    for (UserResponse answer : answers)
	        	    {
	            
	             // evaluating
	             if ((answer.getOptionSelected() == question.getCorrectOption())&&(answer.getQuestionId()==question.getId())) {
	                 score++;
	             }
	         }
	         
	    }return score;
	    }

	    public QuizzesUser getQuizUserByQuizIdAndUserId(long quizId, int userId) {
	        return quizzesUserRepo.findByQuizIdAndUserId(quizId, userId);
	    }
	    
	    public QuizAnalysis getQuizAnalysis(long quizId) {
	        QuizAnalysis analysis = new QuizAnalysis();
	        ArrayList<User> leaderBoard = new ArrayList<>();
	        // Getting top scorers along with their details from the students table
	        for (int userId : quizzesUserRepo.getLeaderboardByQuiz(quizId)) {
	            leaderBoard.add(userRepo.findById(userId).orElse(null));
	        }

	        // Calculating attempting rate
	        int allQuizzesCount = quizzesUserRepo.countAllByQuizId(quizId),
	                attemptedQuizCount = quizzesUserRepo.getAttemptedCountByQuizId(quizId);
	        long attemptionRate = Math.floorDiv(attemptedQuizCount * 100, allQuizzesCount);

	        // Preparing frequency distribution table(marks, no of student who obtained those marks)
	        long[][] table = quizzesUserRepo.getMarksAndFrequency(quizId);


	        // Populating analysis object
	        analysis.setClassMarksAvg(quizzesUserRepo.getQuizAvg(quizId));
	        analysis.setLeaderBoard(leaderBoard);
	        analysis.setAttemptRateInPercent(attemptionRate);
	        analysis.setMarksFrequencyTable(table);

	        return analysis;
	    }
	
	

}
