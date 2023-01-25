package tn.esprit.spring.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.dto.Response.AccountResponse;
import tn.esprit.spring.dto.Response.NewPassword;
import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.PasswordModel;
import tn.esprit.spring.entity.Photo;
import tn.esprit.spring.entity.Role;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.service.EmailSenderService;
import tn.esprit.spring.entity.UserResponse;

@RestController
@RequestMapping("/User")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
	@Autowired
	IUserService userService;
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	UserRepository userRepository;
	
	
	@RequestMapping("/user")
	  public Principal user(Principal user) {
	    return user;
	  }
	
	//LOGIN
	// http://localhost:8089/User/Login
		@PostMapping("/Login")
		public User Login(@RequestBody User user) {
			String username = user.getUserName();
			String password = user.getPassword();
			User userobj = null;
			if (username != null && password != null) {
				userobj = userRepository.findByUserNameAndPassword(username, password);
			}
			return userobj;
		}

	@GetMapping("/getUserConnected")
	@ResponseBody
	public User currentUserUser(Authentication authentication) {

		User user = userService.findUserByUserName(authentication.getName());

		return user;
	}
	// http://localhost:8089/User/UserUpdate

	@PutMapping("/userUpdate/{id}")
	public void updateUser(@PathVariable(value = "id") int userId,
			@Valid @RequestBody User userDetails) {
		User user = userRepository.findById(userId).orElse(null);
		// .orElseThrow(() -> new ResourceNotFoundException("Employee not found
		// for this id :: " + employeeId));

		user.setMail(userDetails.getMail());
		
		user.setCin(userDetails.getCin());
		user.setBirthdate(userDetails.getBirthdate());
		user.setUserName(userDetails.getUserName());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPhoneNumber(userDetails.getPhoneNumber());
        userRepository.save(user);
		//return ResponseEntity.ok(updatedUser);
	}
	@PutMapping("/update")
	public void update(@RequestBody User user) {
		userService.update(user);
	}
	// http://localhost:8089/User/getAllUsers
	@GetMapping("/getAllUsers")
	@ResponseBody
	public List<User> getAllUsers() {
		List<User> users = userService.GetAllUsers();
		return users;
	}
	
	@GetMapping("/getUsersByPoints")
	@ResponseBody
	public List<User> getByPoints() {
		List<User> users = userService.sortBypoints();
		return users;
	}
	

	// http://localhost:8089/User/GetUserById
	@GetMapping("/GetUserById/{userId}")
	@ResponseBody
	public User GetUserById(@PathVariable("userId") int userId) {

		User user = userService.GetUserById(userId);
		return user;

	}

	// http://localhost:8089/User/delete/1
	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int userId) {

		userService.DeleteUser(userId);
		;
	}
	// http://localhost:8089/User/update/1

	@PutMapping("/update/{userId}")
	public void updateUser(@RequestBody User user, @PathVariable("userId") int UserId) {
		userService.UpdateUser(user, UserId);

	}

	// http://localhost:8089/User/registration
	@PostMapping("/registration")
	public String createNewUser(@RequestBody User user) {
		String msg = "";
		User userExists = userService.findUserByUserName(user.getUserName());
		if (userExists != null) {
			msg = "There is already a user registered with the user name provided";
		} else {
			userService.SaveUser(user);
			msg = "OK";
		}
		return msg;
	}

	// ResetPassword
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody PasswordModel passwordModel) {
		System.out.println("passsww");
		User user = userService.findUserByMail(passwordModel.getMail());
		String url = "";
		if (user != null) {
			String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user, token);
			url = passwordResetTokenMail(user, token);
		}
		return url;
	}

	@PostMapping("/savePassword")
	public String savePassword(@RequestBody PasswordModel passwordModel) {
		System.out.println("passsww");
		String result = userService.validatePasswordResetToken(passwordModel.getToken());
		if (!result.equalsIgnoreCase("valid")) {
			return "Invalid Token";
		}
		Optional<User> user = userService.getUserByPasswordResetToken(passwordModel.getToken());
		if (user.isPresent()) {
			userService.changePassword(user.get(), passwordModel.getNewPassword());
			return "Password Reset Successfully";
		} else {
			return "Invalid Token";
		}
	}

	private String passwordResetTokenMail(User user, String token) {

		System.out.println("passsww");
		String subject = "Reset Password";

		String url = token;

		senderService.sendEmail(user.getMail(), subject, url);
		return url;
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@PostMapping("/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "You have successfully logged out";
	}

	@GetMapping("/getPhotoByUser/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getPhotoByUser(@PathVariable("id") int id) throws IOException {

		// User user=currentUserUser(authentication);
		final ResponseEntity<byte[]> dbImage = userService.getPhotoByUser(id);

		return dbImage;

	}

	@GetMapping("/getPhotoUser/{idUser}")
	@ResponseBody
	public ResponseEntity<byte[]> getUserPhoto(@PathVariable("idUser") int idUser) throws IOException {

		final ResponseEntity<byte[]> dbImage = userService.getPhotoByUser(idUser);

		return dbImage;

	}

	// Anas
	// http://localhost:8089/User/rateCollaboration/1/1=>5
	@PutMapping("/rateCollaboration/{id}/{rate}")
	public void rateCollaboration(@PathVariable("id") int idCollab, @PathVariable("rate") int rate) {
		userService.rateCalculCollaboration(idCollab, rate);
	}
	// http://localhost:8089/User/rateColleague/1/1=>5

	@PutMapping("/rateColleague/{id}/{rate}")
	public void rateColleague(@PathVariable("id") int id, @PathVariable("rate") int rate) {
		userService.rateColleague(id, rate);
	}
	// http://localhost:8089/User/rateEvent/1/1=>5

	@PutMapping("/rateEvent/{id}/{rate}")
	public void rateEvent(@PathVariable("id") int id, @PathVariable("rate") int rate) {
		userService.rateEvent(id, rate);
	}

	// http://localhost:8089/User/rateTrainning/1/1
	@PutMapping("/rateTrainning/{id}/{rate}")
	public void rateTrainning(@PathVariable("id") int id, @PathVariable("rate") int rate) {
		userService.rateTraining(id, rate);
	}

	@PutMapping("/voteTo/{id}/{idCandidate}")

	public boolean voteTo(@PathVariable("id") int id , @PathVariable("idCandidate") int idCandidate) {

		boolean vb = userService.voteTo( id, idCandidate);
		return vb;

	}

	// TOP
	// http://localhost:8089/User/sortEmployeeBypoints
	@GetMapping("/sortEmployeeBypoints")
	@ResponseBody
	public List<String> sortEmployeeBypoints() {

		List<String> users = userService.sortEmployeeBypoints();
		return users;

	}

	// http://localhost:8089/User/top5Rated
	@GetMapping("/top5Rated")
	@ResponseBody
	public List<String> top5Rated() {

		List<String> users = userService.top5Rated();
		return users;

	}

	//// LINKEDIN
	@GetMapping("/linkedInLogin")
	public URI ShareOnLinkedin() throws URISyntaxException {
		String myUrl = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=789ampg7y98avo&redirect_uri=https://oauth.pstmn.io/v1/callback&state=foobar&scope=r_liteprofile%20r_emailaddress";

		URI myURI = new URI(myUrl);
		return myURI;
	}

	@GetMapping("/getToken")
	public String getToken(@RequestBody String code) {
		System.out.println(code);

		try {
			String string = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=" + code
					+ "&redirect_uri=http://localhost:4200/linkedInLogin&client_id=789ampg7y98avo&client_secret=hZl1kWs9jO1XBYhf";
			System.out.println(string);
			URL url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				String token = output.substring(output.indexOf("\":\"") + 3, output.indexOf("\"expires_in\"") - 2);
				String email = getEmailUser(token);
				List<User> users = (List<User>) userRepository.findAll();

				List<String> addressUsers = new ArrayList<String>();

				for (User u : users) {

					addressUsers.add(u.getMail());
				}

				if (!addressUsers.contains(email)) {

					createLinkedInUser(token);

				}

				return output;

			}
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}
		return "Token has expired, get a new one.";
	}

	public String getEmailUser(String token) {
		try {

			String string = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))&oauth2_access_token="
					+ token;

			System.out.println(string);
			URL url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}

			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				String email = output.substring(output.indexOf("\"emailAddress\"") + 16, output.indexOf("\"},\""));

				return email;
			}
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}
		return null;

	}

	public void createLinkedInUser(String token) {

		try {

			String string = "https://api.linkedin.com/v2/me?projection=(id,firstName,lastName,emailAddress,profilePicture(displayImage~:playableStreams))&oauth2_access_token="
					+ token;

			System.out.println(string);
			URL url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}

			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);

				String id = output.substring(output.indexOf("\"id\"") + 6, output.indexOf("\"id\"") + 16);

				String firstname = output.substring(output.indexOf("\":\"") + 3, output.indexOf("\"},\""));
				int first = output.indexOf("preferredLocale");
				String Lastname = output.substring(output.indexOf("\"lastName\"") + 34,
						output.indexOf("preferredLocale", first + 1) - 4);
				String email = getEmailUser(token);

				User u = new User(firstname, Lastname, email, id, "changeit");
				// User u = new User(first, id, email, firstname, first,
				// Lastname, "changeit", email, null, first, null, null, email,
				// email, false, null, null);

				userRepository.save(u);

			}
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}
	}
}
