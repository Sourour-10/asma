package tn.esprit.spring.IService;

import java.util.List;


import tn.esprit.spring.entity.Reclamation;

public interface IReclamationService {
	//CRUD
	void createReclamation(int UserId,int num,String complaintSubject ,String Content) ;
	void deleteReclamation(int  idreclamation) ;
	void updateReclamation(Reclamation Reclamation);
	public List<Reclamation> getAllReclamations();
	public Reclamation getReclamationById(int recId);
	public void complaintsSystemDecision(int UserId);

}
