
package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.IActivityService;
import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.entity.Activity;
import tn.esprit.spring.entity.User;

@RestController
@RequestMapping("/Activity")
public class ActivityController {
	@Autowired
	IActivityService activityService;
	@Autowired
	IUserService userService;
	
	@GetMapping("/getUserConnected")
	@ResponseBody
	public User currentUserUser(Authentication authentication) {
		User user = userService.findUserByUserName(authentication.getName());
		System.out.println("User in badge : " + user);
		return user;
	}


	// http://localhost:8089/Activity/create

	@PostMapping("create")
	@ResponseBody
	public void createActivity(Authentication authentication,@RequestBody Activity activity) {
		User user = userService.findUserByUserName(authentication.getName());
		activityService.createActivity(activity,user);
	}

	// http://localhost:8089/Activity/getAllActivitys
	@GetMapping("/getAllActivitys")
	@ResponseBody
	public List<Activity> getAllActivitys() {

		List<Activity> activitys = activityService.getAllActivitys();
		return activitys;

	}
	
	// http://localhost:8089/Activity/getActivityById/1
	@GetMapping("/getActivityById/{id}")
	@ResponseBody
	public Activity getActivityById(@PathVariable("id") int activityId) {

		Activity activity = activityService.getActivityById(activityId);
		return activity;

	}
	// http://localhost:8089/Activity/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int activityId) {

		activityService.deleteActivity(activityId);
		;
	}
	// http://localhost:8089/Activity/update

	@PutMapping("/update")
	public void updateActivity(@RequestBody Activity activity) {
		activityService.updateActivity(activity);
	}

	//http://localhost:8089/Activity/affectusertoactivity/1/1
	@PutMapping("/affectusertoactivity/{idUser}/{idActivity}")
	public void affectUserToActivity(@PathVariable("idUser") int idUser, @PathVariable("idActivity") int categoryId ) {
		activityService.affectActivityToCategory(idUser,categoryId);
	}
	
	// http://localhost:8089/Activity/LIKE/1/1
	@PutMapping("/LIKE/{UserId}/{activityId}")
	public void UserLikesActivity(@PathVariable("UserId") int UserId, @PathVariable("activityId") int activityId ) {
		activityService.UserLikesActivity(UserId,activityId);
	}
}

