package tn.esprit.spring.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.IService.INotificationService;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.Notification;
import tn.esprit.spring.entity.Role;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.*;
import java.util.List;

import tn.esprit.spring.repository.NotificationRepository;

@Service
public class NotificationService implements INotificationService {
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	EventRepository eventRepository;

	@Transactional
	public void createNotification(Notification notification, int userId) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		notification.setDate(now);
		notification.setViewed(false);

		User user = userRepository.findById(userId).orElse(null);
		Set<Notification> notifications = user.getNotifications();
		notifications.add(notification);
		user.setNotifications(notifications);

		// userRepository.save(user) ;
		notificationRepository.save(notification);
	}




	@Override
	public void deleteNotification(int idNotification) {
		Notification notification = notificationRepository.findById(idNotification).orElse(null);
		notificationRepository.delete(notification);
	}
	public List<Notification> getAllNotifications() {
		List<Notification> notifications = (List<Notification>) notificationRepository.findAll();
		return notifications;

	}

	public Notification GetNotificationById(int id) {
		Notification notification = notificationRepository.findById(id).orElse(null);
		return notification;
	}

	public void viewNotification(int id) {
		Notification notification = notificationRepository.findById(id).orElse(null);
		notification.setViewed(true);

		notificationRepository.save(notification);
	}

	@Override
	public Notification ReminderEventNotif(Notification notification, int userId, Event event) {

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (event.getStartDate() == now) {
			notification.setDate(now);
			notification.setText("You have an event today");
			notification.setViewed(false);

			User user = userRepository.findById(userId).orElse(null);
			Set<Notification> notifications = user.getNotifications();
			notifications.add(notification);
			user.setNotifications(notifications);
			notificationRepository.save(notification);

		}
		return notification;
	}

	@Override
	public Notification EndEventNotif(Notification notification, int userId, Event event) {

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (event.getEndDate().after(now)) {
			notification.setDate(now);

			User user = userRepository.findById(userId).orElse(null);
			Set<Notification> notifications = user.getNotifications();
			notifications.add(notification);
			user.setNotifications(notifications);
			notificationRepository.save(notification);

		}
		return notification;
	}

	// public List<Notification> GetNotificationByUser(int userId){

	// User user = userRepository.findById(userId).orElse(null);

	// }

	@Override
	public Set<Notification> showAllMyNotifications(int userId) {
		User user = userRepository.findById(userId).orElse(null);

		return user.getNotifications();
	}

	@Override
	public Set<Notification> showNonViwedNotifications(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		Set<Notification> notifications = user.getNotifications();
		Set<Notification> nonViewedNotifications = new HashSet<Notification>();
		for (Notification n : notifications) {
			if (n.getViewed() == false)
				nonViewedNotifications.add(n);
		}
		return nonViewedNotifications;
	}
	
	
	@Override
	public int showNumberNonViwedNotifications(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		Set<Notification> notifications = user.getNotifications();
		int number=0;
		//Set<Notification> nonViewedNotifications = new HashSet<Notification>();
		for (Notification n : notifications) {
			if (n.getViewed() == false)
				number++;
		}
		return number;
	}

	@Override
	@Transactional
	public void makeNotification(int userId, String text, String type) {
		User user = userRepository.findById(userId).orElse(null);
		Set<Notification> notifications = user.getNotifications();
		user.setNotifications(notifications);
		Notification notification = new Notification();
		notification.setViewed(false);
		notification.setText(text);
		notification.setType(type);
		// Date System
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		notification.setDate(now);
		notifications.add(notification);
		notificationRepository.save(notification);

	}

	@Override
	@Transactional
	public void notificationForAll(String text, String type, String role) {
		Notification notification = new Notification();
		notification.setText(text);
		notification.setType(type);
		notification.setViewed(false);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		notification.setDate(now);

		List<User> employees = (List<User>) userRepository.findAll();
		for (User u : employees) {
			if (u.getRole().toString().equals(role)) {
				System.out.println(u.getRole());
				u.getNotifications().add(notification);
				userRepository.save(u);
				

			}
		}

	}

	@Override
	public void notificationForAnEmployee(String text, String type, int userId) {
		Notification notification = new Notification();
		notification.setText(text);
		notification.setType(type);
		notification.setViewed(false);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		notification.setDate(now);
		notification.setViewed(false);

		User user = userRepository.findById(userId).orElse(null);
		Set<Notification> notifications = user.getNotifications();
		notifications.add(notification);
		user.setNotifications(notifications);

		// userRepository.save(user) ;
		notificationRepository.save(notification);

	}
	
	public void DeleteAllNotificationByUser( int userId ){
		
		User user = userRepository.findById(userId).orElse(null);
		Set<Notification> notifications = user.getNotifications();
		notificationRepository.deleteAll(notifications);
		
		
		
	}
}
