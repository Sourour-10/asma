package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Publicity;

public interface IPublicityService {

	//CRUD
	void createPublicity(Publicity publicity) ;
	void deletePublicity(int  idpublicity) ;
	void updatePublicity(Publicity publicity);
	public List<Publicity> getAllPublicities();
}
