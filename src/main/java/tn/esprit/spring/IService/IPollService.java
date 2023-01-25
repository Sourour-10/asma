package tn.esprit.spring.IService;

import java.util.List; 

import tn.esprit.spring.entity.Poll;
import tn.esprit.spring.entity.User;

public interface IPollService {
	void createPoll(Poll poll);

	void deletePoll(int idPoll);

	Poll getPollById(int idPoll);

	void updatePoll(Poll poll);

	public List<Poll> getAllPolls();

	public void nominatedEmployees(int idPoll, int idUser);

	public User pollWinner(int idPoll);

	public int showCurrentResult(int idUser);
}
