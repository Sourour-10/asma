package tn.esprit.spring.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entity.Photo;
import tn.esprit.spring.entity.Role;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.PhotoRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.IService.IPhotoService;
import tn.esprit.spring.Security.jwt.JwtUtils;
import tn.esprit.spring.Security.services.UserDetailsImpl;
import tn.esprit.spring.service.UserService;
import tn.esprit.spring.dto.Request.LoginRequest;
import tn.esprit.spring.dto.Request.Register;
import tn.esprit.spring.dto.Response.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/Auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEnco;

	@Autowired
	UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	IPhotoService photoservice;
	@Autowired
	PhotoRepository photoRepository;

	@PostMapping("/Register")

	public ResponseEntity<ResponseMessage> register(@Valid @RequestBody Register registerRequest) {

		System.out.println("azerty" + registerRequest);
		if (userRepository.existsByUserName(registerRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Error: Username is already taken!"));
		}

		if (userRepository.existsByMail(registerRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Error: Email is already in use!"));
		}

		User user = new User(registerRequest.getFirstname(), registerRequest.getLastname(), registerRequest.getEmail(),
				registerRequest.getUsername(), registerRequest.getBirthdate(), registerRequest.getCin(),
				registerRequest.getPhoneNumber(),passwordEnco.encode(registerRequest.getPassword()));

		Set<String> strRoles = registerRequest.getRole();
		user.setRole(Role.EMPLOYEE);
Long id=new Long(8);
System.out.println("idd"+id);
Photo photo=photoRepository.findById(id).orElse(null);
user.setPhoto(photo);

		userRepository.save(user);

		return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(userDetails);
		System.out.println(userDetails.getIdPhoto());
		if (userDetails.getIdPhoto()>0) {
			
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), userDetails.getPassword(), userDetails.getFirstname(),
					userDetails.getLastname(), userDetails.getPhoneNumber(), userDetails.getBirthdate(),
					userDetails.getIdPhoto(), userDetails.getAuthorities())); }
			
		 else {
			return ResponseEntity
					.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
							userDetails.getPassword(), userDetails.getFirstname(), userDetails.getLastname(),
							userDetails.getPhoneNumber(), userDetails.getBirthdate(), userDetails.getAuthorities()));			
		} 
	}
	@GetMapping("/getAllUsers")
	@ResponseBody
	public List<User> getAllUsers() {
		List<User> users = userService.GetAllUsers();
		return users;
	}

	// http://localhost:8089/User/update/1

	@PutMapping("/update/{userId}")
	public void updateUser(@PathVariable("userId") int UserId, @RequestBody User user) {
		userService.UpdateUser(user, UserId);

	}

	// http://localhost:8089/User/delete/
	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int userId) {

		userService.DeleteUser(userId);
		;
	}

	/*
	 * @GetMapping("/getUserbyUserName/{userName}")
	 * 
	 * @ResponseBody public User GetUserById(@PathVariable("userName") String
	 * userName) {
	 * 
	 * User user = userRepository.findByUserName(userName); return user;
	 * 
	 * }
	 */

}
