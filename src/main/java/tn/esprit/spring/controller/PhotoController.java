package tn.esprit.spring.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.IService.IPhotoService;

import tn.esprit.spring.IService.IUserService;

import tn.esprit.spring.entity.Photo;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.PhotoRepository;

@RestController
@RequestMapping("/Photo")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PhotoController {

	@Autowired
	PhotoRepository photoRepository;
	@Autowired
	IPhotoService photoService;
	@Autowired
	IUserService userService;

	@GetMapping("/getUserConnected")
	@ResponseBody
	public User currentUserUser(Authentication authentication) {

		User user = userService.findUserByUserName(authentication.getName());

		return user;
	}

	@PostMapping("/upload/image")
	public String uplaodImage(Authentication authentication, @RequestParam("photo") MultipartFile file)
			throws IOException {
		User user = currentUserUser(authentication);
		photoService.SavePhoto(file, user.getUserId());

		return "Image uploaded successfully: " + file.getOriginalFilename();
	}
	
	@PostMapping("/upload/photo/{idUser}")
	public String uplaodPhoto(@PathVariable("idUser") int idUser, @RequestParam("photo") MultipartFile file)
			throws IOException {
		
		photoService.SavePhoto(file, idUser);

		return "Image uploaded successfully: " + file.getOriginalFilename();
	}

	@GetMapping("/get/image/info/{name}")
	public Photo getImageDetails(@PathVariable("name") String name) throws IOException {

		final Optional<Photo> photo = photoRepository.findByName(name);

		return Photo.builder().name(photo.get().getName()).type(photo.get().getType()).image(photo.get().getImage())
				.build();
	}

	@GetMapping("/getImageById/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
		return photoService.GetPhotoById(id);
	}

	
	@PostMapping("/BadgeWithPhoto/{id}")
	public String addBadgeWithPhoto(@RequestParam("photo") MultipartFile file, @PathVariable("id") int badgeId)
			throws IOException {

		// photoService.SavePhoto(file,badgeId);

		photoService.addBadgeWithPhoto(file, badgeId);

		return "Image uploaded successfully: " + file.getOriginalFilename();
	}
	@PostMapping("/upload/image/{postId}")
	public String uplaodImageToPost(@PathVariable("postId") int postId, @RequestParam("photo") MultipartFile file)
			throws IOException {
		
		photoService.SavePhotoToPost(file, postId);

		return "Image uploaded to post successfully: " + file.getOriginalFilename();
	}

}
