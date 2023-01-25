package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IFeedBackService;
import tn.esprit.spring.entity.*;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.FeedBackRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class FeedBackService implements IFeedBackService {
	@Autowired
	FeedBackRepository feedBackRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	EventRepository eventRepository;

	@Override
	public void createFeedBack(FeedBack feedBack) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

		System.out.print(timeStamp);
		Date now = new Date();

		try {
			now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		feedBack.setDate(now);
		feedBackRepository.save(feedBack);
	}

	@Override
	public void deleteFeedBack(int idFeedBack) {
		FeedBack feedBack = feedBackRepository.findById(idFeedBack).orElse(null);
		feedBackRepository.delete(feedBack);
	}

	@Override
	public List<FeedBack> getAllFeedBacks() {
		List<FeedBack> feedBacks = (List<FeedBack>) feedBackRepository.findAll();
		return feedBacks;

	}

	@Override
	@Transactional
	public void addFeedBackToUser(FeedBack feedBack, int userId) {
		User user = userRepository.findById(userId).orElse(null);

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		feedBack.setDate(now);
		// feedBack.setUser(user);

		feedBackRepository.save(feedBack);
		Set<FeedBack> feedbacks = user.getFeedBacks();
		feedbacks.add(feedBack);
		user.setFeedBacks(feedbacks);
		userRepository.save(user);

	}

	@Override
	public FeedBack getFeedBackById(int idFeedBack) {
		FeedBack feedBack = feedBackRepository.findById(idFeedBack).orElse(null);
		return feedBack;

	}

	@Override
	public Set<FeedBack> getAllFeedBacksByIdUser(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		Set<FeedBack> feedBacks = user.getFeedBacks();
		return feedBacks;
	}

	@Override
	public void addFeedBackToEvent(FeedBack feedback, int eventId) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();

		try {
			now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		feedback.setDate(now);
		Event event = eventRepository.findById(eventId).orElse(null);
		feedback.setEvent(event);
		feedBackRepository.save(feedback);
	}

	@Override
	public ArrayList<FeedBack> getAllFeedBacksByIdEvent(int idEvent) {
		Event event = eventRepository.findById(idEvent).orElse(null);
		List<FeedBack> feedBacks = (List<FeedBack>) feedBackRepository.findAll();
		ArrayList<FeedBack> eventFeedBacks = new ArrayList<FeedBack>();
   	
		for(FeedBack feedback:feedBacks)
		     if(feedback.getEvent().getEventId()==idEvent)
		    	{
		    	System.out.println("Feedback = "+feedback);
		    	 eventFeedBacks.add(feedback) ;
		    	}
		return eventFeedBacks ;
	}

}
