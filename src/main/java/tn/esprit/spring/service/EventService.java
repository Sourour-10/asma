
package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IEventService;
import tn.esprit.spring.IService.INotificationService;
import tn.esprit.spring.IService.IParticipateService;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.Participate;
import tn.esprit.spring.entity.Type;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.FeedBackRepository;
import tn.esprit.spring.repository.ParticipateRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class EventService implements IEventService {
	@Autowired
	EventRepository eventRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	INotificationService notificationService;
	@Autowired
	FeedBackRepository feedRepository;
	ParticipateRepository participateRepository;
	@Autowired
	IParticipateService participateService;

	@Override
	public void createEvent(Event event, User user) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		event.setPublishDate(now);
		eventRepository.save(event);
		notificationService.notificationForAll("An event " + event.getTitle() + "was added", "Event", "EMPLOYEE");
	}
	@Override
	public void createEven(Event event) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		event.setPublishDate(now);
		eventRepository.save(event);
		notificationService.notificationForAll("An event " + event.getTitle() + "was added", "Event", "EMPLOYEE");
	}

	@Override
	public void deleteEvent(int idEvent) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		eventRepository.delete(event);
	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> events = eventRepository.findByIsApprooved(true);
		return events;

	}

	@Override
	public Event getEventById(int idEvent) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		return event;

	}

	@Override
	public void updateEvent(Event event, User user) {
		if (user.getRole().toString().equals("ADMIN")) {
			eventRepository.save(event);
		} else {
			System.out.println("access only for ADMIN");
		}

	}

	@Override
	public void proposeEvent(Event event, User user) {
		List<User> users = (List<User>) userRepository.findAll();
		List<User> ur = new ArrayList<User>();
		for (User u : users) {
			if (u.getRole().toString().equals("ADMIN"))
				ur.add(u);
		}
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		event.setPublishDate(now);
		event.setIsApprooved(false);
		eventRepository.save(event);
	 //sourour
		notificationService.notificationForAll("The event "+ event.getTitle()+" is waiting to be approved", "Event", "ADMIN");
		for (User u : ur) {
			System.out.println(u.getMail());
			String subject = "Event request";
			String body = user.getFirstName() + " has requested a new event called  " + event.getTitle();
			senderService.sendEmail(u.getMail(), subject, body);
		}
	}

	@Override
	public void approveEventByAdmin(Event event, User user) {
		event.setIsApprooved(true);
		//event.setUser(user);
	
		eventRepository.save(event);
		notificationService.notificationForAll("The event "+ event.getTitle()+"  was approved by Admin you can participate now", "Event", "EMPLOYEE");

	}	

	
	@Override
	public void affectUserToEvent(int idUser, int idEvent) {
		Event e = eventRepository.findById(idEvent).orElse(null);
		User u = userRepository.findById(idUser).orElse(null);
		u.getEvents().add(e);
		e.setUser(u);
		eventRepository.save(e);
		userRepository.save(u);

	}

	@Override
	public void TwoDaysEventMail(int idEvent) {
		List<User> users = (List<User>) userRepository.findAll();
		List<User> ur = new ArrayList<User>();
		for (User u : users) {
			if (u.getRole().toString().equals("EMPLOYEE"))
				ur.add(u);
		}
		Event event = eventRepository.findById(idEvent).orElse(null);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date eDay = event.getStartDate();
		Calendar c = Calendar.getInstance();
		c.setTime(eDay);
		c.add(Calendar.DATE, -2);
		eDay = c.getTime();
		System.out.println(eDay);
		System.out.println(now);
		int x = eDay.compareTo(now);
		System.out.println(x);

		if (x <= 0) {

			notificationService.notificationForAll("You have an event " + event.getTitle() + "  to approve", "EVENT",
					"ADMIN");
			for (User u : ur) {
				System.out.println(u.getMail());
				String subject = "Event : " + event.getTitle();
				String body = "The event " + event.getTitle() + "is sooner then never, in " + event.getStartDate();
				senderService.sendEmail(u.getMail(), subject, body);
			}
		}
	}

	@Override
	public void particiapteToEvent(int idEvent, int idUser, int pOrI) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		User user = userRepository.findById(idUser).orElse(null);
		Participate p = new Participate();
		if (pOrI == 0) {
			if (participateService.alreadyParticipated(idEvent, idUser)) {
				//System.out.println(user.getFirstName() + " no longer particiapted");
				//System.out.println("Number of participants : " + nbrParticiapants(idEvent));
			} else {
				p.setEvent(event);
				p.setUser(user);
				p.setType(Type.PARTICIPATE);
				participateService.createParticipate(p);
				participateService.affectParticipateToEvent(p.getParticipateId(), idEvent);
				participateService.affectParticipateToUser(p.getParticipateId(), idUser);
				//System.out.println(user.getFirstName() + " has now participated");
				//System.out.println("Number of participants now is: " + nbrParticiapants(idEvent));
			}
		} else if (pOrI == 1) {
			if (participateService.alreadyInterrested(idEvent, idUser)) {
				//System.out.println(user.getFirstName() + " no longer interrested");
				//System.out.println("Number of interrested : " + nbrInterrested(idEvent));
			} else {
				p.setEvent(event);
				p.setUser(user);
				p.setType(Type.INTERRESTED);
				participateService.createParticipate(p);
				participateService.affectParticipateToEvent(p.getParticipateId(), idEvent);
				participateService.affectParticipateToUser(p.getParticipateId(), idUser);
				//System.out.println(user.getFirstName() + " is now interrested");
				//System.out.println("Number of interrested now is : " + nbrInterrested(idEvent));
			}
		}

	}

	@Override
	public int nbrParticiapants(int idEvent) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		System.out.println(event);
		List<Participate> particiaptes = (List<Participate>) participateRepository.findAll();
		int result = 0;
		for (Participate part : particiaptes) {
			if (part.getEvent() == event && part.getType() == Type.PARTICIPATE) {
				result = result + 1;
			}
		}
		return result;

	}

	@Override
	public int nbrInterrested(int idEvent) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		List<Participate> particiaptes = (List<Participate>) participateRepository.findAll();
		int result = 0;
		for (Participate part : particiaptes) {
			if (part.getEvent() == event && part.getType() == Type.INTERRESTED) {
				result = result + 1;
			}
		}
		return result;

	}

	@Override
	public List<Event> getNonAproovedEvents(User user) {
		if (user.getRole().toString().equals("ADMIN")) {
			List<Event> events = (List<Event>) eventRepository.findByIsApprooved(false);
			return events;
		} else {
			System.out.println("Access allowed only for ADMIN");
			return null;
		}
	}


	// Anas
	@Override
	public String discountByPoints(int points, float tarif) {
		float discount = points / tarif;
		String s = String.valueOf(discount);
		String msg = "You have a discount " + s + " % ";
		return msg;
	}


}
