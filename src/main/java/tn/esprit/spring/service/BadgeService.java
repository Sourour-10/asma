package tn.esprit.spring.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.*;
import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.FeedBack;
import tn.esprit.spring.entity.Photo;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.BadgeRepository;
import tn.esprit.spring.repository.PhotoRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class BadgeService implements IBadgeService {
	@Autowired
	BadgeRepository badgeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PhotoRepository photoRepository;
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	INotificationService notificationService;
	@Autowired
	IPhotoService photoService;
	
	

	@Override
	public void createBadge(Badge badge) {
		badgeRepository.save(badge);
	}

	@Override
	public void deleteBadge(int idBadge) {
		Badge badge = badgeRepository.findById(idBadge).orElse(null);
		badgeRepository.delete(badge);
	}

	@Override
	public List<Badge> getAllBadges() {
		List<Badge> badges = (List<Badge>) badgeRepository.findAll();
		return badges;

	}

	@Override
	public void updateBadge(Badge badge) {
		badgeRepository.save(badge);

	}

	@Transactional
	@Override
	public void assignBadgeToUser(int idBadge, int idUser) {
		Badge badge = badgeRepository.findById(idBadge).orElse(null);
		User user = userRepository.findById(idUser).orElse(null);
		// Affecter les points du badge à l'utilisateur
		user.setPoints(user.getPoints() + badge.getPoints());
		Set<Badge> badges = user.getBadges();
		// Set<User> users = badge.getUsers();
		badges.add(badge);
		// users.add(user);
		user.setBadges(badges);
		// badge.setUsers(users);
		badgeRepository.save(badge);
		userRepository.save(user);
		String subject = "You won a new Badge";
		String body = "Hi" + user.getFirstName() + "\nYou won a new Badge\n You are now " + badge.getName();
		senderService.sendEmail(user.getMail(), subject, body);
		// Sourour
		notificationService.notificationForAnEmployee("You won a new Badge", "BADGE", idUser);

	}

	@Override
	public Set<Badge> getAllBadgesByIdUser(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		Set<Badge> badges = user.getBadges();
		return badges;
	}

	@Override
	public Badge findBadgeByName(String name) {
		int badgeId = badgeRepository.findBadgeByName(name);
		Badge badge = badgeRepository.findById(badgeId).orElse(null);
		return badge;
	}
	/*
	 * @Override public ResponseEntity<byte[]> getBadgeImage(int id) { Badge
	 * badge = badgeRepository.findById(id).orElse(null); ResponseEntity<byte[]>
	 * badgeImage = photoService.GetPhotoById(badge.get) return null; }
	 */

	@Override
	public ResponseEntity<byte[]> getPhotoByBadge(int badgeId) {
		Badge badge = badgeRepository.findById(badgeId).orElse(null);
		Long idImage = badge.getPhoto().getId();
		final Optional<Photo> dbImage = photoRepository.findById(idImage);

		return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
				.body(dbImage.get().getImage());
	}

	@Override
	public Long getIdPhotoByIdBadge(int badgeId) {
		Badge badge = badgeRepository.findById(badgeId).orElse(null);
		Long idImage = badge.getPhoto().getId();

		return idImage;
	}
}
