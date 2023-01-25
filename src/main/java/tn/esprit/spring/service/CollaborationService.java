package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.ICollaborationService;
import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.Offer;
import tn.esprit.spring.entity.Notification;
import tn.esprit.spring.repository.CollaborationRepository;
import tn.esprit.spring.repository.PostRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.ReactRepository;
import tn.esprit.spring.repository.NotificationRepository;

@Service
public class CollaborationService implements ICollaborationService {
	@Autowired
	CollaborationRepository collabroationRepository;

	@Autowired
	PostRepository PostRepository;

	@Autowired
	UserRepository UserRepository;

	@Autowired
	ReactRepository ReactRepository;

	@Autowired
	NotificationRepository NotificationRepository;

	@Override
	public void createCollaboration(Collaboration collaboration) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.print(timeStamp);
		Date now = new Date();
		try {
			now = new SimpleDateFormat("dd/MM/yyyy").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		collaboration.setDate(now);
		collabroationRepository.save(collaboration);

	}

	@Override
	public void deleteCollaboration(int idCollaboration) {

		Collaboration c = collabroationRepository.findById(idCollaboration).orElse(null);
		collabroationRepository.delete(c);
	}

	@Override
	public void updateCollaboration(Collaboration collaboration) {
	
		collabroationRepository.save(collaboration);

	}

	@Override
	public List<Collaboration> getAllCollaborations() {
		List<Collaboration> collaborations = (List<Collaboration>) collabroationRepository.findAll();
		return collaborations;
	}

	public List<String> getActivities() {
		List<String> Activities = new ArrayList<String>();
		List<Collaboration> collaborations = getAllCollaborations();
		for (Collaboration c : collaborations) {
			Activities.add(c.getActivity());
		}
		// remove redundant Activities
		Set<String> set = new HashSet<>(Activities);
		Activities.clear();
		Activities.addAll(set);
		return Activities;
	}

	@Override
	public void getLikesByActivity() {
		// TODO Auto-generated method stub
		List<Integer> NLikesPerPost = new ArrayList<Integer>();
		List<Integer> NLikesPerActivity = new ArrayList<Integer>();
		List<String> Activities = getActivities();
		for (String activity : Activities) {
			List<Post> postsActivity = PostRepository.findPostsByCollaborationActivity(activity);
			for (Post post : postsActivity) {
				NLikesPerPost.add(post.getNlikes());
			}
			Integer max = Collections.max(NLikesPerPost);
			int indexMax = NLikesPerPost.indexOf(max);
			NLikesPerActivity.add(max);
		}
		Integer maxPerActivity = Collections.max(NLikesPerActivity);
		int indexMaxPerActivity = NLikesPerActivity.indexOf(maxPerActivity);
		System.out.println("lactivité la plus tendance selon les likes des posts est :"
				+ Activities.get(indexMaxPerActivity) + " where le nb des likes est : " + maxPerActivity);
		List<Collaboration> collaborations = (List<Collaboration>) collabroationRepository
				.findCollabByActivity(Activities.get(indexMaxPerActivity));
		List<Offer> offreMostLiked = new ArrayList<Offer>();
		String collabNames = "";
		for (Collaboration c : collaborations) {
			collabNames += " " + c.getCompanyName() + " ,";
			List<Offer> offers = c.getOffers();
			for (Offer offer : offers) {
				offreMostLiked.add(offer);
			}
		}
		String contentOfNotif = "we found that the most liked posts are the posts that focus on the activity"
				+ Activities.get(indexMaxPerActivity)
				+ "which is based on the same field of activity of our collaborators:" + collabNames;
		Notification n = new Notification();
		n.setText(contentOfNotif);
		// create an LocalDateTime object
		n.setDate(new Date());
	}

	@Override
	public List<String> sortByPoints() {
		System.out.println("Entriiir");

		List<String> collaborations = collabroationRepository.findAllOrderByRatingDesc();
		System.out.println(collaborations);

		return collaborations;
	}

	@Override
	public Collaboration getColabById(int colabId) {
		// TODO Auto-generated method stub
		return collabroationRepository.findById(colabId).orElse(null);
	}

}
