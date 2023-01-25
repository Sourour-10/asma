package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Subject;

public interface ISubjectService {

	// CRUD
	void createSubject(Subject Subject);

	void deleteSubject(int idSubject);

	void updateSubject(Subject Subject);

	public List<Subject> getAllSubjects();

	void proposeSubject(Subject subject);

	public void approveSubjectByAdmin(int idSubject);

}
