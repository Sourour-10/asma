package tn.esprit.spring.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.IService.IPhotoService;
import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.Photo;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.BadgeRepository;
import tn.esprit.spring.repository.PhotoRepository;
import tn.esprit.spring.repository.PostRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class PhotoService implements IPhotoService {
	@Autowired
	PhotoRepository photoRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BadgeRepository badgeRepository;
	@Autowired
	PostRepository PostRepository;

	public List<Photo> GetAllPhotos() {

		return (List<Photo>) photoRepository.findAll();

	}

	@Override
	public ResponseEntity<byte[]> GetPhotoById(Long id) {
		final Optional<Photo> dbImage = photoRepository.findById(id);

		return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
				.body(dbImage.get().getImage());
	}

	@Override

	public void SavePhoto(MultipartFile file, int userId) throws IOException {
		User user = userRepository.findById(userId).orElse(null);
		Photo photo = Photo.builder().name(file.getOriginalFilename()).type(file.getContentType())
				.image(file.getBytes()).build();
		photoRepository.save(photo);
		user.setPhoto(photo);
		userRepository.save(user);

	}

	@Override
	public void DeletePhoto(long id) {
		Photo photo = photoRepository.findById(id).orElse(null);

		photoRepository.delete(photo);
	}

	// Anas

	@Override
	public void addBadgeWithPhoto(MultipartFile file, int badgeId) throws IOException {
		Badge badge = badgeRepository.findById(badgeId).orElse(null);
		Photo photo = Photo.builder().name(file.getOriginalFilename()).type(file.getContentType())
				.image(file.getBytes()).build();
		photoRepository.save(photo);
		badge.setPhoto(photo);
		badgeRepository.save(badge);

	}
	
	@Override
	public void SavePhotoToPost(MultipartFile file, int postId) throws IOException {
		
		Post post = PostRepository.findById(postId).orElse(null);
		Photo photo = Photo.builder().name(file.getOriginalFilename()).type(file.getContentType())
				.image(file.getBytes()).build();
		photoRepository.save(photo);
		post.setPhoto(photo);
		PostRepository.save(post);
		
		
	}

}
