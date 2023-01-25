package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Activity;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.User;

public interface IActivityService {

	// CRUD
	public void createActivity(Activity activity,User user);

	public void deleteActivity(int idActivity);

	public void updateActivity(Activity activity);

	public List<Activity> getAllActivitys();

	public void affectActivityToCategory(int idActivity, int idCategory);

	public Activity getActivityById(int idActivity);

	public void affectUserToActivity(int idUser, int idActivity);

	public void findCategoryToActivity(int idActivity);

	public Activity UserLikesActivity(int idUser, int idActivity);
	// List<User> getUsersByActivitys(int idActivity);

}
