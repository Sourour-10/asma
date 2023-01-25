package tn.esprit.spring.IService;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.Photo;

public interface IBadgeService {

	// CRUD
	//public ResponseEntity<byte[]> getPhotoByBadge(int badgeId)
	void createBadge(Badge badge);

	void deleteBadge(int idBadge);

	void updateBadge(Badge badge);

	public List<Badge> getAllBadges();

	public Set<Badge> getAllBadgesByIdUser(int userId);

	public void assignBadgeToUser(int idBadge, int idUser);

	public Badge findBadgeByName(String name);

	public ResponseEntity<byte[]> getPhotoByBadge(int badgeId);
	public Long getIdPhotoByIdBadge( int badgeId) ;

}
