package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Badge;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.React;

@Repository
public interface PostRepository  extends CrudRepository<Post,Integer>{
	
	@Query("SELECT p FROM Post p WHERE p.text LIKE %?1%")
	public List<Post> findPostsByCollaborationActivity( String activity);
	


	


}
