package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.User;

@Repository
public interface CollaborationRepository extends CrudRepository<Collaboration, Integer> {

	@Query("SELECT c FROM Collaboration c WHERE c.companyName= :name")
	public Collaboration findCollabByCompanyName(@Param("name") String name);

	@Query("SELECT c FROM Collaboration c WHERE c.Activity= :activity")
	public Collaboration findCollabByActivity(@Param("activity") String activity);

	// Anas
	@Query("SELECT  companyName FROM Collaboration c order by c.rating")

	List<String> findAllOrderByRatingDesc();

}
