package tn.esprit.spring.IService;

import java.util.List;
import java.util.Set;

import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.Notification;
import tn.esprit.spring.entity.Role;

public interface INotificationService {
	public void createNotification(Notification notification, int userId);

	public void deleteNotification(int idNotification);

	public List<Notification> getAllNotifications();

	public void viewNotification(int id);

	public Notification GetNotificationById(int id);

	public Notification EndEventNotif(Notification notification, int userId, Event event);

	public Notification ReminderEventNotif(Notification notification, int userId, Event event);

	public Set<Notification> showAllMyNotifications(int userId);

	public Set<Notification> showNonViwedNotifications(int userId);

	public void notificationForAll(String text, String type, String role);

	public void notificationForAnEmployee(String text, String type, int userId);

	public void makeNotification(int userId, String text, String type);

	public void DeleteAllNotificationByUser(int userId);
	public int showNumberNonViwedNotifications(int userId);

}
