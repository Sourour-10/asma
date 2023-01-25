package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Participate;


public interface IParticipateService {

	//CRUD
	public void createParticipate(Participate participate) ;
	public void deleteParticipate(int  idParticipate) ;
	public void updateParticipate(Participate participate);
	public List<Participate> getAllParticipates();
	public void affectParticipateToUser(int idparticipate, int idUser);
	public void affectParticipateToEvent(int idparticipate, int idEvent);
	public boolean alreadyParticipated(int idEvent, int idUser);
	public boolean alreadyInterrested(int idEvent, int idUser);

}
