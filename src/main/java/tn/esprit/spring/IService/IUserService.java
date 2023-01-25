package tn.esprit.spring.IService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.UserResponse;

public interface IUserService {
	
	public User getUserByMail(String mail);
	// Sourour
	public List<User> GetAllUsers();

	public User GetUserById(int id);
	
	public void update(User user);

	public User SaveUser(User user);

	public void DeleteUser(int id);

	public User UpdateUser(User user, int userId);

	public User findUserByUserName(String userName);

	public User findUserByMail(String mail);

	public void createPasswordResetTokenForUser(User user, String token);

	public String validatePasswordResetToken(String token);

	public Optional<User> getUserByPasswordResetToken(String token);

	public void changePassword(User user, String newPassword);

	public ResponseEntity<byte[]> getPhotoByUser(int userId);

	// Anas
	// Fonctionalité pour l'admin
	public void rateCalculCollaboration(int idCollab, int rate);

	// Employee
	public void rateColleague(int idEmployee, int rate);

	public void rateEvent(int idEvent, int rate);

	public void rateTraining(int idTraining, int rate);

	public List<String> sortEmployeeBypoints();
	
	public List<User> sortBypoints();


	public List<String> top5Rated();

	public boolean voteTo( int idUser, int idCandidate);
	

	// youssef
	void uploadAnswers(List<UserResponse> responseList);

	void assignQuizToUsers(long quizId, int schoolClass);
	 
}
