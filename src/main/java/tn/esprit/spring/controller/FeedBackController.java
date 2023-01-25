package tn.esprit.spring.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import tn.esprit.spring.IService.IFeedBackService;
import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.entity.FeedBack;
import tn.esprit.spring.entity.User;
@RestController
@RequestMapping("/FeedBack")
@CrossOrigin(origins = "*", maxAge = 3600)

public class FeedBackController {
	@Autowired
	IFeedBackService feedBackService;
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

	// http://localhost:8089/FeedBack/create

	@PostMapping("create")
	@ResponseBody
	public void createFeedBack(@RequestBody FeedBack feedBack) {
		feedBackService.createFeedBack(feedBack);
	}

	// http://localhost:8089/FeedBack/getAllFeedBacks
	@GetMapping("/getAllFeedBacks")
	@ResponseBody
	public List<FeedBack> getAllFeedBacks() {

		List<FeedBack> feedBacks = feedBackService.getAllFeedBacks();
		return feedBacks;

	}
	// http://localhost:8089/FeedBack/getMyFeedBacks
	@GetMapping("/getMyFeedBacks/{id}")
	@ResponseBody
	public Set<FeedBack> getAllFeedBacksByIdUser(@PathVariable("id")int id  ) {
		
		Set<FeedBack> feedBacks = feedBackService.getAllFeedBacksByIdUser(id);
		return feedBacks;
	}

	// http://localhost:8089/FeedBack/getFeedBackById/1
	@GetMapping("/getFeedBackById/{id}")
	@ResponseBody
	public FeedBack getFeedBackById(@PathVariable("id") int feedBackId) {

		FeedBack feedBack = feedBackService.getFeedBackById(feedBackId);
		return feedBack;

	}
	// http://localhost:8089/FeedBack/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteFeedBack(@PathVariable("id") int feedBackId) {

		feedBackService.deleteFeedBack(feedBackId);
	}

	// http://localhost:8089/FeedBack/addFeedBackToUser/1

	@PutMapping("/addFeedBackToUser/{idUser}")
	public void addFeedBackToUser(@PathVariable("idUser") int userId, @RequestBody FeedBack feedBack) {
		feedBackService.addFeedBackToUser(feedBack, userId);
	}
	// http://localhost:8089/FeedBack/makeFeedBack/1
	@PutMapping("/makeFeedBack/{id}")
	public void makeFeedBack(@PathVariable("id")int eventId,@RequestBody FeedBack feedBack) {
		feedBackService.addFeedBackToEvent(feedBack, eventId);
	}
	
				

	
	@GetMapping("/getEventFeedBacks/{id}")
	@ResponseBody
	public ArrayList<FeedBack> getEventFeedBacks(@PathVariable("id")int id  ) {
		ArrayList<FeedBack> feedBacks = feedBackService.getAllFeedBacksByIdEvent(id);
		return feedBacks;
	}


}
