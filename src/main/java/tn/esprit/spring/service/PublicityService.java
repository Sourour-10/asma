package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IPublicityService;
import tn.esprit.spring.entity.Publicity;
import tn.esprit.spring.repository.PublicityRepository;

@Service
public class PublicityService implements IPublicityService{
	
	@Autowired
	PublicityRepository publicityRepository;

	@Override
	public void createPublicity(Publicity publicity) {
		// TODO Auto-generated method stub
		publicityRepository.save(publicity);
		
	}

	@Override
	public void deletePublicity(int idpublicity) {
		// TODO Auto-generated method stub
		Publicity p = publicityRepository.findById(idpublicity).orElse(null);
		publicityRepository.delete(p);
		
	}

	@Override
	public void updatePublicity(Publicity publicity) {
		// TODO Auto-generated method stub
		publicityRepository.save(publicity);
		
	}

	@Override
	public List<Publicity> getAllPublicities() {
		// TODO Auto-generated method stub
		List<Publicity> publicities = (List<Publicity>) publicityRepository.findAll();
		return publicities;
	}

}
