package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import tn.esprit.spring.IService.IPollService;
import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.entity.Poll;
import tn.esprit.spring.entity.User;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Poll")
public class PollController {
	@Autowired
	IPollService pollService;
	@Autowired
	IUserService userService;

	// http://localhost:8089/Badge/create
	@GetMapping("/getUserConnected")
	@ResponseBody
	public User currentUserUser(Authentication authentication) {
		User user = userService.findUserByUserName(authentication.getName());
		System.out.println("User in badge : " + user);
		return user;
	}
	// http://localhost:8089/Poll/create

	@PostMapping("create")
	@ResponseBody
	public void createPoll(@RequestBody Poll poll) {
		pollService.createPoll(poll);
	}

	// http://localhost:8089/Poll/getAllPolls
	@GetMapping("/getAllPolls")
	@ResponseBody
	public List<Poll> getAllPolls() {

		List<Poll> polls = pollService.getAllPolls();
		return polls;

	}

	// http://localhost:8089/Poll/getPollById/1
	@GetMapping("/getPollById/{id}")
	@ResponseBody
	public Poll getPollById(@PathVariable("id") int pollId) {

		Poll poll = pollService.getPollById(pollId);
		return poll;

	}
	// http://localhost:8089/Poll/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int pollId) {

		pollService.deletePoll(pollId);
	}
	// http://localhost:8089/Poll/update

	@PutMapping("/update")
	public void updatePoll(@RequestBody Poll poll) {
		pollService.updatePoll(poll);
	}

	// http://localhost:8089/Poll/nominatedEmployees/1/1
	@PutMapping("/nominatedEmployees/{idPoll}")
	@ResponseBody
	public void nominatedEmployees(Authentication authentication, @PathVariable("idPoll") int pollId) {
		User user = currentUserUser(authentication);

		pollService.nominatedEmployees(pollId, user.getUserId());
	}

	// http://localhost:8089/Poll/getWinner
	@GetMapping("/getWinner/{id}")
	@ResponseBody
	public User getWinner(@PathVariable("id") int idPoll) {
		User user = pollService.pollWinner(idPoll);

		return user;

	}

	// http://localhost:8089/Poll/showCurrentResult
	@GetMapping("/showCurrentResult/{id}")
	@ResponseBody
	public String showCurrentResult(@PathVariable("id") int idUser) {
		User user =  userService.GetUserById(idUser)  ;
		int vote = pollService.showCurrentResult(user.getUserId());
		return ""+vote ;

	}
}
