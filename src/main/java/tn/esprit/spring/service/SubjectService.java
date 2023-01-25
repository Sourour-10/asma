package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.INotificationService;
import tn.esprit.spring.IService.ISubjectService;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.Subject;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.SubjectRepository;

@Service
public class SubjectService implements ISubjectService {
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	INotificationService notificationService;

	@Override
	public void createSubject(Subject subject) {
		subject.setApproved(true);
		subjectRepository.save(subject);
	}

	@Override
	public void deleteSubject(int idSubject) {
		Subject subject = subjectRepository.findById(idSubject).orElse(null);
		subjectRepository.delete(subject);
	}

	@Override
	public List<Subject> getAllSubjects() {
		List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
		return subjects;

	}

	@Override
	public void updateSubject(Subject subject) {
		subjectRepository.save(subject);

	}

	@Override
	public void proposeSubject(Subject subject) {
		subject.setApproved(false);
		String suj = "The subject" + subject.getName() + " is waiting to be approved";
		notificationService.notificationForAll(suj, "Subject", "ADMIN");
		subjectRepository.save(subject);
	}

	@Override
	public void approveSubjectByAdmin(int idSubject) {
		Subject subject = subjectRepository.findById(idSubject).orElse(null);
		subject.setApproved(true);
		String suj = "A new subject added : " + subject.getName() + " check your forum";
		notificationService.notificationForAll(suj, "Subject", "EMPLOYEE");
		
	}

	/*
	 * @Override public List<User> getUsersBySubjects(int idSubject) { // TODO
	 * Auto-generated method stub return null; }
	 */

}
