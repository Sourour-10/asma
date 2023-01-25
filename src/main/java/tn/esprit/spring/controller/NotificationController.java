
package tn.esprit.spring.controller;

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

import tn.esprit.spring.IService.INotificationService;

import tn.esprit.spring.entity.Event;

import tn.esprit.spring.IService.IUserService;

import tn.esprit.spring.entity.Notification;
import tn.esprit.spring.entity.User;

@RestController
@RequestMapping("/Notification")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class NotificationController {
	@Autowired
	INotificationService notificationService;
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

	// http://localhost:8089/Notification/create

	@PutMapping("create/{userId}")
	@ResponseBody
	public void createNotification(@RequestBody Notification notification, @PathVariable("userId") int userId) {
		notificationService.createNotification(notification, userId);
	}

	// http://localhost:8089/Notification/getAllNotifications
	@GetMapping("/getAllNotifications")
	@ResponseBody
	public List<Notification> getAllNotifications() {

		List<Notification> notifications = notificationService.getAllNotifications();
		return notifications;

	}
	// http://localhost:8089/Notification/getNotificationById/1

	@GetMapping("/getNotificationById/{id}")
	@ResponseBody
	public Notification getNotificationById(@PathVariable("id") int notificationId) {
		Notification notificaion = notificationService.GetNotificationById(notificationId);
		return notificaion;

	}
	// http://localhost:8089/Notification/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteNotification(@PathVariable("id") int notificationId) {

		notificationService.deleteNotification(notificationId);

	}
	// http://localhost:8089/Notification/update/1

	@PutMapping("/update/{id}")
	public void viewNotification(@PathVariable("id") int notificationId) {
		notificationService.viewNotification(notificationId);
	}

	// @PostMapping("")
	// public Notification ReminderEventNotif(@RequestBody Notification
	// notification,@PathVariable("userId")int userId,Event event){
	// notificationService.createNotification(notification,userId);
	// }

	// http://localhost:8089/Notification/showAllMyNotifications
	@GetMapping("/showAllMyNotifications")

	@ResponseBody
	public Set<Notification> showAllMyNotifications(Authentication authentication) {
		User user = currentUserUser(authentication);
		Set<Notification> notifications = notificationService.showAllMyNotifications(user.getUserId());
		return notifications;

	}

	// http://localhost:8089/Notification/showNonViwedNotifications
	@GetMapping("/showNonViwedNotifications")
	@ResponseBody
	public Set<Notification> showNonViwedNotifications(Authentication authentication) {
		User user = currentUserUser(authentication);
		Set<Notification> notifications = notificationService.showNonViwedNotifications(user.getUserId());
		return notifications;

	}
	
	// http://localhost:8089/Notification/showNonViwedNotifications
		@GetMapping("/showNonViwedNotification/{id}")
		@ResponseBody
		public Set<Notification> showNonViwedNotification(@PathVariable("id") int id ) {
			//User user = currentUserUser(authentication);
			Set<Notification> notifications = notificationService.showNonViwedNotifications(id);
			return notifications;

		}

	@GetMapping("/showNumberNonViwedNotifications/{id}")
	@ResponseBody
	public int showNumberNonViwedNotifications(@PathVariable("id") int id ) {
		//User user = userService.GetUserById(id);
		int notifications = notificationService.showNumberNonViwedNotifications(id);
		return notifications;

	}

	@DeleteMapping("/DeleteAllMyNotifications")
	public void DeleteAllNotificationByUser(Authentication authentication) {
		User user = currentUserUser(authentication);
		notificationService.DeleteAllNotificationByUser(user.getUserId());

	}
}
