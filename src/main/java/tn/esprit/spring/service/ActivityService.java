package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IActivityService;
import tn.esprit.spring.entity.Activity;
import tn.esprit.spring.entity.Category;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ActivityRepository;
import tn.esprit.spring.repository.CategoryRepository;
import tn.esprit.spring.repository.UserRepository;


@Service
public class ActivityService implements IActivityService {
	
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	UserRepository userRepository;
	

	@Override
	public void createActivity(Activity activity,User user) {
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		activity.setDate(now);
		activity.setUser(user);
		activityRepository.save(activity);
		System.out.println("***********************************"+activity.getActivityId());
		findCategoryToActivity(activity.getActivityId());
		activityRepository.save(activity);
	}

	@Override
	public void deleteActivity(int idActivity) {
		Activity activity = activityRepository.findById(idActivity).orElse(null);
		activityRepository.delete(activity);
	}

	@Override
	public List<Activity> getAllActivitys() {
		List<Activity> activitys = (List<Activity>) activityRepository.findAll();
		return activitys;

	}
	
	@Override
	public Activity getActivityById(int idActivity) {
		Activity activity = activityRepository.findById(idActivity).orElse(null);
		return activity;

	}

	@Override
	public void updateActivity(Activity activity) {
		activityRepository.save(activity);

	}
	
	@Override
	public void affectActivityToCategory(int idActivity, int idCategory) {
		Category c = categoryRepository.findById(idCategory).orElse(null);
		Activity a = activityRepository.findById(idActivity).orElse(null);
		a.setCategory(c);
		c.getActivities().add(a);
		categoryRepository.save(c);
		activityRepository.save(a);
		
	}
	
	@Override
	public void affectUserToActivity(int idUser, int idActivity) {
		Activity a = activityRepository.findById(idActivity).orElse(null);
		User u = userRepository.findById(idUser).orElse(null);
		u.getActivities().add(a);
		a.setUser(u);
		activityRepository.save(a);
		userRepository.save(u);
		
	}

	@Override
	public void findCategoryToActivity(int idActivity) {
		String x="",str1="",str2="";
		int i;
		int id=-1;
		Activity a = activityRepository.findById(idActivity).orElse(null);
		List<Category> categorys = (List<Category>) categoryRepository.findAll();
		for(Category c: categorys)
		{
			////dictionary youfa b "-"
			if(c.getDictionary().endsWith("-"))
			{
				x = c.getDictionary();
				System.out.println("xxxxxxxxxxxxxx : " + x);
			}
			else
			{
				x = c.getDictionary()+"-";
				System.out.println("xxxxxxxxxxxxxx : " + x);
			}
			////////extraction des mots et comparaison
			do
			{
			i = x.indexOf("-");

			str1 = x.substring(0, i);
			str2 = x.replace(str1+"-","");
			x=str2;
			System.out.println("str1 : " + str1);
			System.out.println("str2 : " + str2);
			System.out.println("x : " + x);
			System.out.println(a.getDescription().contains(str1));
			}
			while(a.getDescription().contains(str1)== false && x.isEmpty()==false);
			if(a.getDescription().contains(str1))
				{
				
					id = c.getCategoryId();
					break;
				}
			
		}
		Activity aa = activityRepository.findById(idActivity).orElse(null);
		if(aa.getDescription().contains(str1)){
			affectActivityToCategory(idActivity, id);
		}else 
		{
			affectActivityToCategory(idActivity, categoryRepository.findByName("Other").getCategoryId());
		}
		
		System.out.println(aa.getCategory().getName());
		
			
		
	}

	@Override
	public Activity UserLikesActivity(int idUser, int idActivity) {
		Activity a = activityRepository.findById(idActivity).orElse(null);
		User u = userRepository.findById(idUser).orElse(null);
		if(a.getUsers().contains(u))
		{
			a.getUsers().remove(u);
			u.getActivitiesLiked().remove(a);
			a.setNbrLikes(a.getNbrLikes()-1);
		}
		else
		{
			a.getUsers().add(u);
			u.getActivitiesLiked().add(a);
			a.setNbrLikes(a.getNbrLikes()+1);
		}
		activityRepository.save(a);
		userRepository.save(u);
		return a;
		
	}
	


}
