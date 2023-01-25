package tn.esprit.spring.IService;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.Photo;

public interface IPhotoService {
	public List<Photo> GetAllPhotos();

	public ResponseEntity<byte[]> GetPhotoById(Long id);

	public void SavePhoto(MultipartFile file, int userId) throws IOException;

	public void DeletePhoto(long id);



	// Anas

	void addBadgeWithPhoto(MultipartFile file, int badgeId) throws IOException;
	
	//yossr
	public void SavePhotoToPost(MultipartFile file, int postId) throws IOException;

}
