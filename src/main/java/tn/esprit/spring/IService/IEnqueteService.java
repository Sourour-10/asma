package tn.esprit.spring.IService;

import java.util.List;

public interface IEnqueteService {
	
	public void createEnquete();

	List<String> remplirEnquete(int idEnquete, int val1, int val2, int val3);

}
