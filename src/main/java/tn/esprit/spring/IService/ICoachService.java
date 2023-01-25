package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Coach;


public interface ICoachService {

	void addCoach(Coach coach);

	void deleteCoach(int idCoach);

	void updateCoach(Coach coach);

	public List<Coach> getAllCoach();
}
