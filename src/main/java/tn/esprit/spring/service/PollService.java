package tn.esprit.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IPollService;
import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.Poll;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.*;

import tn.esprit.spring.repository.BadgeRepository;
import tn.esprit.spring.repository.PollRepository;


@Service
public class PollService implements IPollService {
	@Autowired
	PollRepository pollRepository;

	@Autowired
	BadgeRepository badgeRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public void createPoll(Poll poll) {
		Badge badge = new Badge();
		badge.setName(poll.getBadgeName());
		badge.setPoints(poll.getPoints());
		badgeRepository.save(badge);

		pollRepository.save(poll);
	}

	@Override
	public void deletePoll(int idPoll) {
		Poll poll = pollRepository.findById(idPoll).orElse(null);
		pollRepository.delete(poll);
	}

	@Override
	public List<Poll> getAllPolls() {
		List<Poll> polls = (List<Poll>) pollRepository.findAll();
		return polls;

	}

	@Override
	public void updatePoll(Poll poll) {

		pollRepository.save(poll);

	}
	@Override
	public Poll getPollById(int idPoll) {
		Poll p = pollRepository.findById(idPoll).orElse(null);
		return p;

	}

	@Override
	public void nominatedEmployees(int idPoll, int idUser) {
		User user = userRepository.findById(idUser).orElse(null);
		user.setNumberOfVote(0);
		Poll poll = pollRepository.findById(idPoll).orElse(null);
		Set<Poll> polls = user.getPolls();
		polls.add(poll);
		user.setPolls(polls);
		// Set<User> users = poll.getUsers();
		// users.add(user);
		// poll.setUsers(users);
		pollRepository.save(poll);
	}

	@Override
	public User pollWinner(int idPoll) {
		Poll poll = pollRepository.findById(idPoll).orElse(null);
		int badgeId = badgeRepository.findBadgeByName(poll.getBadgeName());
		Badge badge = badgeRepository.findById(badgeId).orElse(null);

		List<User> users = (List<User>) userRepository.findAll();
		int topVotes = -1;
		User topUser = null;
		// Boolean tie = false ;
		for (User u : users) {
			System.out.println(u.getFirstName());
			if (u.getNumberOfVote() > topVotes) {
				topVotes = u.getNumberOfVote();
				topUser = u;
			}
		}
		Set<Badge> badges = topUser.getBadges();
		badges.add(badge);
		topUser.setBadges(badges);
		
		for (User u : users) {
			u.setNumberOfVote(0);
			u.setHasVoted(false);
			userRepository.save(u);
		}
	

		return topUser;

	}

	@Override
	public int showCurrentResult(int idUser) {
		User user = userRepository.findById(idUser).orElse(null);
		return user.getNumberOfVote();
	}


}
