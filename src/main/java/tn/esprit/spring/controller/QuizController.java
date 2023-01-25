package tn.esprit.spring.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.IQuizService;

import tn.esprit.spring.entity.Questions;
import tn.esprit.spring.entity.Quiz;
import tn.esprit.spring.entity.QuizAnalysis;
import tn.esprit.spring.entity.QuizzesUser;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	@Autowired
	private IQuizService service;
	
	@Autowired
	public JavaMailSender javaMailSender;

	
	
	
	// For teachers
	@PostMapping("/addQuiz")
	public Quiz addQuiz(@RequestBody Quiz quiz) {
		return service.addQuiz(quiz);
	}

	@PostMapping("/addQuestions")
	public List<Questions> addQuestionsForQuiz(@RequestBody List<Questions> questions, @RequestParam long quizId) {
		return service.addQuestionsForQuiz(questions, quizId);
	}

	@PostMapping("/addQuestion")
	public Questions addQuestionForQuiz(@RequestBody Questions question) {
		return service.addQuestionForQuiz(question);
	}

	// For Students
	@GetMapping("/details/{quizId}")
	public Quiz getQuizById(@PathVariable long quizId) {
		return service.getQuizById(quizId);
	}

	@GetMapping("/show/{userId}")
	public HashMap<String, List<Quiz>> getQuizzesForUser(@PathVariable int userId) {
		return service.getQuizzesByUserId(userId);
	}

	@GetMapping("/{quizId}")
	public List<Questions> getQuestionsForQuiz(@PathVariable long quizId) {
		return service.getQuestionsByQuizId(quizId);
	}

	@GetMapping("/evaluate/{quizId}/{trainingId}")
	public void evaluateAndUpdateResult(@PathVariable int trainingId, @PathVariable long quizId) {
		service.evaluateResult(quizId, trainingId);
	}

	@GetMapping("/result/{quizId}/{userId}")
	public QuizzesUser getQuizUser(@PathVariable long quizId, @PathVariable int userId) {
		return service.getQuizUserByQuizIdAndUserId(quizId, userId);
	}

	@GetMapping("/result/{quizId}")
	public QuizAnalysis getQuizAnalysis(@PathVariable long quizId) {
		return service.getQuizAnalysis(quizId);
	}
	
	
	@GetMapping("/sendemail")
	public String sendmail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("youssef.rakrouki@esprit.tn");
		message.setSubject("certificate of appreciation");
		message.setText("thank you ");
		javaMailSender.send(message);
		return "Successfuly sent email";
	}
	
	

}
