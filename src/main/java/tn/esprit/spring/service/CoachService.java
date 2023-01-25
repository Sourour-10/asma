package tn.esprit.spring.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.IService.ICoachService;
import tn.esprit.spring.entity.Coach;

import tn.esprit.spring.repository.CoachRepository;


@Service
public class CoachService implements ICoachService {

	@Autowired
	CoachRepository coachRepository;

	
	@Override
	public void addCoach(Coach coach) {
		coachRepository.save(coach);
		
	}

	@Override
	public void deleteCoach(int idCoach) {
		Coach coach =coachRepository.findById(idCoach).orElse(null);
		coachRepository.delete(coach);
	}

	@Override
	public void updateCoach(Coach coach) {
		coachRepository.save(coach);
		
		
	}

	@Override
	public List<Coach> getAllCoach() {
		// TODO Auto-generated method stub
		return (List<Coach>) coachRepository.findAll();
	}

	

}
