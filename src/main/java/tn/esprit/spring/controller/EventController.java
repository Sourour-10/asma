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

import tn.esprit.spring.IService.IEventService;
import tn.esprit.spring.IService.IParticipateService;
import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.FeedBack;
import tn.esprit.spring.entity.User;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/Event")
public class EventController {
	@Autowired
	IEventService eventService;
	@Autowired
	IUserService userService;
	@Autowired
	IParticipateService participateService;

	// http://localhost:8089/Badge/create
	@GetMapping("/getUserConnected")
	@ResponseBody
	public User currentUserUser(Authentication authentication) {
		User user = userService.findUserByUserName(authentication.getName());
		System.out.println("User in badge : " + user);
		return user;
	}

	// http://localhost:8089/Event/create

	@PostMapping("/create/{id}")
	@ResponseBody

	public void createEvent(@PathVariable("id") int id, @RequestBody Event event) {
		User user = userService.GetUserById(id);
		eventService.createEvent(event, user);

	}
	@PostMapping("/createEvent")
	@ResponseBody

	public void createEven( @RequestBody Event event) {
		//User user = currentUserUser(authentication);
		eventService.createEven(event);

	}

	// http://localhost:8089/Event/getAllEvents
	@GetMapping("/getAllEvents")
	@ResponseBody
	public List<Event> getAllEvents() {
		List<Event> events = eventService.getAllEvents();
		return events;

	}

	// http://localhost:8089/Event/getNonAproovedEvents
	@GetMapping("/getNonAproovedEvents/{id}")
	@ResponseBody
	public List<Event> getNonAproovedEvents(@PathVariable("id") int id) {
		User user = userService.GetUserById(id);
		List<Event> events = eventService.getNonAproovedEvents(user);
		return events;

	}

	// http://localhost:8089/Event/getEventById/1
	@GetMapping("/getEventById/{id}")
	@ResponseBody
	public Event getEventById(@PathVariable("id") int eventId) {

		Event event = eventService.getEventById(eventId);
		return event;

	}
	// http://localhost:8089/Event/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int eventId) {

		eventService.deleteEvent(eventId);
		;
	}
	// http://localhost:8089/Event/update

	@PutMapping("/update/{id}")
	public void updateEvent(@PathVariable("id") int id, @RequestBody Event event) {
		User user = userService.GetUserById(id);

		eventService.updateEvent(event, user);
	}

	// http://localhost:8089/Event/propose
	@PostMapping("propose/{id}")
	@ResponseBody
	public void propose(@PathVariable("id") int id, @RequestBody Event event) {
		User user = userService.GetUserById(id);

		eventService.proposeEvent(event, user);
	}

	// http://localhost:8089/Event/approve
	@PutMapping("/approve/{id}")
	public void approveEventByAdmin(@PathVariable("id") int id, @RequestBody Event event) {
		User user = userService.GetUserById(id);
		eventService.approveEventByAdmin(event, user);
	}

	// http://localhost:8089/Event/affectusertoevent/1/1
	@PutMapping("/affectusertoevent/{idUser}/{idEvent}")
	public void affectUserToEvent(@PathVariable("idUser") int userId, @PathVariable("idEvent") int eventId) {
		eventService.affectUserToEvent(userId, eventId);
	}

	// http://localhost:8089/Event/particiapteToEvent/1/1/0
	@PutMapping("/particiapteToEvent/{idEvent}/{idUser}/{type}")
	public void particiapteToEvent(@PathVariable("idEvent") int idEvent, @PathVariable("idUser") int idUser,
			@PathVariable("type") int type) {
		eventService.particiapteToEvent(idEvent, idUser, type);
	}

	// http://localhost:8089/Event/TwoDaysEventMail/1
	@PutMapping("/TwoDaysEventMail/{idEvent}")
	public void TwoDaysEventMail(@PathVariable("idEvent") int idEvent) {
		eventService.TwoDaysEventMail(idEvent);
	}
	// Anas
		// http://localhost:8089/Event/discountBypoints/{id}
		@GetMapping("/discountBypoints/{idUser}/{id}")
		@ResponseBody
		public String discountBypoints(@PathVariable("idUser") int idUser,@PathVariable("id") int id ) {
			User user = userService.GetUserById(idUser) ;
			Event event = eventService.getEventById(id);
			return eventService.discountByPoints(user.getPoints(), event.getTarif());
		}
}
