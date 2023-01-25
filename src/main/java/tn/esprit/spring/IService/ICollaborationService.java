package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Collaboration;

public interface ICollaborationService {
	// CRUD
	void createCollaboration(Collaboration collaboration);

	void deleteCollaboration(int idCollaboration);

	void updateCollaboration(Collaboration collaboration);

	public List<Collaboration> getAllCollaborations();

	public void getLikesByActivity();
	public Collaboration getColabById(int colabId);

	// Anas
	List<String> sortByPoints();
}
