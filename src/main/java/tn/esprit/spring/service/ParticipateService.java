package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IParticipateService;
import tn.esprit.spring.entity.Category;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.Participate;
import tn.esprit.spring.entity.Type;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.CategoryRepository;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.ParticipateRepository;


@Service
public class ParticipateService implements IParticipateService {
	
	@Autowired
	ParticipateRepository participateRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	EventRepository eventRepository;
	

	@Override
	public void createParticipate(Participate participate) {
		participateRepository.save(participate);
	}

	@Override
	public void deleteParticipate(int idParticipate) {
		Participate participate = participateRepository.findById(idParticipate).orElse(null);
		participateRepository.delete(participate);
	}

	@Override
	public List<Participate> getAllParticipates() {
		List<Participate> participates = (List<Participate>) participateRepository.findAll();
		return participates;

	}

	@Override
	public void updateParticipate(Participate participate) {
		participateRepository.save(participate);

	}

	@Override
	public void affectParticipateToUser(int idparticipate, int idUser) {
		Participate p = participateRepository.findById(idparticipate).orElse(null);
		User u = userRepository.findById(idUser).orElse(null);
		u.getParticipates().add(p);
		p.setUser(u);
		participateRepository.save(p);
		userRepository.save(u);
		
	}

	@Override
	public void affectParticipateToEvent(int idparticipate, int idEvent) {
		Participate p = participateRepository.findById(idparticipate).orElse(null);
		Event e = eventRepository.findById(idEvent).orElse(null);
		e.getParticipates().add(p);
		p.setEvent(e);
		participateRepository.save(p);
		eventRepository.save(e);
		
	}
	@Override
	public boolean alreadyParticipated(int idEvent, int idUser){
		Event event = eventRepository.findById(idEvent).orElse(null);
		User user = userRepository.findById(idUser).orElse(null);
		List<Participate> particiaptes = (List<Participate>) participateRepository.findAll();
		boolean result = false;
		for(Participate part:particiaptes){
		if(part.getEvent()==event && part.getUser()==user && part.getType()==Type.PARTICIPATE)
		{
			result=true;
			event.getParticipates().remove(part);
			deleteParticipate(part.getParticipateId());
		}
		}
		return result;
				
	}
	@Override
	public boolean alreadyInterrested(int idEvent, int idUser){
		Event event = eventRepository.findById(idEvent).orElse(null);
		User user = userRepository.findById(idUser).orElse(null);
		List<Participate> particiaptes = (List<Participate>) participateRepository.findAll();
		boolean result = false;
		for(Participate part:particiaptes){
		if(part.getEvent()==event && part.getUser()==user && part.getType()==Type.INTERRESTED)
		{
			result=true;
			event.getParticipates().remove(part);
			deleteParticipate(part.getParticipateId());
		}
		}
		return result;
								
	}
	
	


}
