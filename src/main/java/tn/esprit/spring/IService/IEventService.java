package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.FeedBack;
import tn.esprit.spring.entity.User;

public interface IEventService {


	// CRUD
	public void createEven(Event event) ;
	public void createEvent(Event event, User user);

	public void deleteEvent(int idEvent);

	public void updateEvent(Event event, User user);

	public List<Event> getAllEvents();

	public List<Event> getNonAproovedEvents(User user);

	public void proposeEvent(Event event, User user);

	public void approveEventByAdmin(Event event, User user);

	public void affectUserToEvent(int idUser, int idEvent);

	public Event getEventById(int idEvent);

	public void TwoDaysEventMail(int idEvent);

	public void particiapteToEvent(int idEvent, int idUser, int type);

	public int nbrParticiapants(int idEvent);

	public int nbrInterrested(int idEvent);

	// List<User> getUsersByEvents(int idEvent);


	// Anas
	public String discountByPoints(int points, float tarif);

}
