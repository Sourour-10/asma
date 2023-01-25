package tn.esprit.spring.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.entity.*;
import tn.esprit.spring.repository.*;



import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.QuizzesUser;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.UserResponse;
import tn.esprit.spring.repository.CollaborationRepository;
import tn.esprit.spring.repository.QuizUserRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.UserResponseRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	CollaborationRepository collaborationRepository;
	@Autowired
	EventRepository eventRepository;
	@Autowired
	TrainingRepository trainingRepository;
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	PhotoRepository photoRepository;
	// Sourour

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	@Autowired
	UserResponseRepository   userResponseRepo;
	
	@Autowired
	QuizUserRepository quizzesUserRepo;

	public List<User> GetAllUsers() {

		return (List<User>) userRepository.findAll();

	}


	@Override
	public User GetUserById(int id) {
		User u = userRepository.findById(id).orElse(null);
		return u;
	}

	public User SaveUser(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole(Role.EMPLOYEE);
		user.setActive(true);
		User u = userRepository.save(user);
		String subject = "Welcome " + u.getFirstName() + " To VIWell";
		String body = "Hello  " + u.getFirstName() + " \n Thank you for subscribing to VIWell";
		senderService.sendEmail(u.getMail(), subject, body);

		return u;
	}

	@Override
	public void DeleteUser(int id) {
		// User user=userRepository.findById(id).orElse(null);

		userRepository.deleteById(id);
	}

	// resetPassword
	@Override
	public User findUserByMail(String mail) {

		return userRepository.findByMail(mail);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public String validatePasswordResetToken(String token) {
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

		if (passwordResetToken == null) {
			return "invalid";
		}

		User user = passwordResetToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
			passwordResetTokenRepository.delete(passwordResetToken);
			return "expired";
		}

		return "valid";
	}

	@Override
	public Optional<User> getUserByPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
	}

	@Override
	public void changePassword(User user, String newPassword) {
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	// Anas
	@Override
	public void rateCalculCollaboration(int idCollaboration, int rate) {
		Collaboration collaboration = collaborationRepository.findById(idCollaboration).orElse(null);
		int rattingNumber = collaboration.getRatingNumber();
		rattingNumber++;
		float eval = (rate + collaboration.getRating()) / rattingNumber;
		System.out.println("eval = " + eval);
		int rating = Math.round(eval);
		System.out.println("rating = " + rating);
		collaboration.setRating(rating);
		collaboration.setRatingNumber(rattingNumber);
		collaborationRepository.save(collaboration);

	}
	
	//Youssef
	public void uploadAnswers(List<UserResponse> responseList) {
        userResponseRepo.saveAll(responseList);
        // Updating the quiz status
        UserResponse testResponse = responseList.get(0);
        int userId = testResponse.getUserId();
             Long   quizId = testResponse.getQuizId();
        QuizzesUser quizzesUser = quizzesUserRepo.findByQuizIdAndUserId(quizId, userId);
        quizzesUser.setMarksObtained(-1); // quiz is attempted but not checked
        quizzesUserRepo.save(quizzesUser);

}
	public void assignQuizToUsers(long quizId, int schoolClass) {
        List<User> users = (List<User>) userRepository.findAll();
        List<QuizzesUser> quizzesUsers = new ArrayList<>();
        for (User user : users) {
            QuizzesUser quizzesUser = new QuizzesUser();
            quizzesUser.setQuizId(quizId);
            quizzesUser.setUserId(user.getUserId());
            
            quizzesUser.setMarksObtained(-3);
            quizzesUsers.add(quizzesUser);
        }
        quizzesUserRepo.saveAll(quizzesUsers);

    }	







	public User UpdateUser(User user, int userId) {
		Optional<User> u = userRepository.findById(userId);

		if (u == null)
			try {
				throw new Exception("User not found");
			} catch (Exception e) {
				e.printStackTrace();
			}
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		userRepository.save(user);
		return userRepository.findById(userId).get();

	}
	
	@Override
	public void update(User user) {
	
		userRepository.save(user);

	}
	
	 public User getUserByMail(String mail){
	        return this.userRepository.findByMail(mail);
	    }

	@Override
	public void rateColleague(int idEmployee, int rate) {
		User user = userRepository.findById(idEmployee).orElse(null);
		int rattingNumber = user.getRatingNumber();
		float eval ;
		if (rattingNumber==0) {
			eval = rate ;
		}else {
		//float eval = (( user.getRating()*rattingNumber)+(rate/(rattingNumber+1))) ;
		
		eval = ((user.getRating() * rattingNumber )+rate) / (rattingNumber+1) ;	
			
		}
		rattingNumber ++ ;

		
		int evaluation = Math.round(eval );
		System.out.println("evaluation = " + evaluation);
		user.setRating(evaluation);
		user.setRatingNumber(rattingNumber);
		userRepository.save(user);

	}

	@Override
	public void rateEvent(int idEvent, int rate) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		int rattingNumber = event.getRatingNumber();
		float eval ;
		if (rattingNumber==0) {
			eval = rate ;
		}else {
		//float eval = (( user.getRating()*rattingNumber)+(rate/(rattingNumber+1))) ;
			eval = ((event.getRating() * rattingNumber )+rate) / (rattingNumber+1) ;	
		
		}
		rattingNumber ++ ;
		int evaluation = Math.round(eval );
		System.out.println("evaluation = " + evaluation);
		event.setRating(evaluation);
		event.setRatingNumber(rattingNumber);
		eventRepository.save(event);
	}

	@Override
	public void rateTraining(int idTraining, int rate) {
		Training trainning = trainingRepository.findById(idTraining).orElse(null);
		int rattingNumber = trainning.getRatingNumber();
		float eval ;
		if (rattingNumber==0) {
			eval = rate ;
		}else {
		//float eval = (( user.getRating()*rattingNumber)+(rate/(rattingNumber+1))) ;
			eval = ((trainning.getRating() * rattingNumber )+rate) / (rattingNumber+1) ;	
		
		}
		
		rattingNumber ++ ;

		int evaluation = Math.round(eval );
		System.out.println("evaluation = " + evaluation);
		trainning.setRating(evaluation);
		trainning.setRatingNumber(rattingNumber);
		trainingRepository.save(trainning);

	}

	@Override
	public boolean voteTo(int idUser, int idCandidate) {
		// User user = userRepository.findById(idUser).orElse(null);
		User userConnected = userRepository.findById(idUser).orElse(null);

		User candidate = userRepository.findById(idCandidate).orElse(null);
		if (userConnected.isHasVoted()) {
			System.out.println("You have already voted");
			return false;
		}
		userConnected.setHasVoted(true);
		candidate.setNumberOfVote(candidate.getNumberOfVote() + 1);
		userRepository.save(userConnected);
		userRepository.save(candidate);
		return true;

	}

	@Override
	public List<String> sortEmployeeBypoints() {
		List<String> users = (List<String>) userRepository.sortEmployeeBypoints();
		return users;
	}

	@Override
	public List<String> top5Rated() {
		List<String> users = (List<String>) userRepository.top5Rated();
		return users;
	}

	@Override
	public ResponseEntity<byte[]> getPhotoByUser(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		Long idImage = user.getPhoto().getId();
		final Optional<Photo> dbImage = photoRepository.findById(idImage);

		return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
				.body(dbImage.get().getImage());
	}


	@Override
	public List<User> sortBypoints() {

      return (List<User>) userRepository.findTop3ByOrderByPointsDesc();
	}
	


	
}
