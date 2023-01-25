package tn.esprit.spring.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tn.esprit.spring.entity.FeedBack;
import tn.esprit.spring.entity.Poll;
import tn.esprit.spring.entity.User;

public interface IFeedBackService {

	// CRUD
	void createFeedBack(FeedBack feedBack);

	void deleteFeedBack(int idFeedBack);

	public List<FeedBack> getAllFeedBacks();

	public Set<FeedBack> getAllFeedBacksByIdUser(int userId);
	
	public ArrayList<FeedBack> getAllFeedBacksByIdEvent(int idEvent);


	FeedBack getFeedBackById(int idFeedBack);

	void addFeedBackToUser(FeedBack feedBack, int userId);

	void addFeedBackToEvent(FeedBack feedback, int eventId);

	// List<User> getUsersByFeedBacks(int idFeedBack);

}
