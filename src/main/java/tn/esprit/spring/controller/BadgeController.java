package tn.esprit.spring.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.IService.IBadgeService;
import tn.esprit.spring.IService.IUserService;
import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.User;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/Badge")
public class BadgeController {
	@Autowired
	IBadgeService badgeService;
	@Autowired
	IUserService userService;

	// http://localhost:8089/Badge/create
	@GetMapping("/getUserConnected")
	@ResponseBody
	public User currentUserUser(Authentication authentication) {
		User user = userService.findUserByUserName(authentication.getName());
		System.out.println("User in badge : " + user);
		return user;
	}


	@PostMapping("create")
	@ResponseBody
	public void createBadge(@RequestBody Badge badge) {
		badgeService.createBadge(badge);
	}
	// http://localhost:8089/Badge/getAllBadges
	@GetMapping("/getAllBadges")
	@ResponseBody
	public List<Badge> getAllBadges() {

		List<Badge> badges = badgeService.getAllBadges();
		return badges;

	}

	// http://localhost:8089/Badge/getMyBadges
	@GetMapping("/getMyBadges")
	@ResponseBody
	public Set<Badge> getAllBadgesByIdUser(Authentication authentication) {
		System.out.println("Authentificaaation    : " + authentication);
		User user = currentUserUser(authentication);
		Set<Badge> badges = badgeService.getAllBadgesByIdUser(user.getUserId());
		return badges;
	}
	// http://localhost:8089/Badge/getMyBadges
	@GetMapping("/getMyBadges/{id}")
	@ResponseBody
	public Set<Badge> getAllBadgesByIdUserConnected(@PathVariable("idUser") int userId ) {
		User user = userService.GetUserById(userId) ;
		Set<Badge> badges = badgeService.getAllBadgesByIdUser(user.getUserId());
		return badges;
	}
	// http://localhost:8089/Badge/getBadgesByIdUser/1
	@GetMapping("/getBadgesByIdUser/{idUser}")
	@ResponseBody
	public Set<Badge> getAllBadgesByIdUser(@PathVariable("idUser") int userId) {

		Set<Badge> badges = badgeService.getAllBadgesByIdUser(userId);
		System.out.println(badges );
		return badges;
	}

	// http://localhost:8089/Badge/getBadgeByName/1
	@GetMapping("/getBadgeByName/{name}")
	@ResponseBody
	public Badge getBadgeByName(@PathVariable("name") String name) {

		Badge badge = badgeService.findBadgeByName(name);
		return badge;
	}

	// http://localhost:8089/Badge/delete/1
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int badgeId) {

		badgeService.deleteBadge(badgeId);
	}
	// http://localhost:8089/Badge/update/1

	@PutMapping("/update")
	public void updateBadge(@RequestBody Badge badge) {
		badgeService.updateBadge(badge);
	}
	// http://localhost:8089/Badge/assignBadgeToUser/1/1

	@PutMapping("/assignBadgeToUser/{idBadge}/{idUser}")
	public void assignBadgeToUser(@PathVariable("idBadge") int badgeId, @PathVariable("idUser") int idUser) {
		badgeService.assignBadgeToUser(badgeId, idUser);
	}

	@PostMapping("/getBadgeByName")
	public Badge getBadgeByName(@RequestBody Badge badge) {
		System.out.println(badge);
		Badge badgee = badgeService.findBadgeByName(badge.getName());
		return badgee;
	}
	 @GetMapping("/getPhotoByBadge/{id}")
		@ResponseBody
		  public ResponseEntity<byte[]> getPhotoByBadge(@PathVariable("id") int id) throws IOException {
		 
		final ResponseEntity<byte[]> dbImage = badgeService.getPhotoByBadge(id);

		return dbImage;
			
		}

		  @GetMapping("/getIdPhotoByBadge/{id}")
			@ResponseBody
			  public Long getIdPhotoByBadge(@PathVariable("id") int id) throws IOException {
			 

			return badgeService.getIdPhotoByIdBadge(id);
				
			}


}
