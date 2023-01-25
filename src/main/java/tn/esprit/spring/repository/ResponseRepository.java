package tn.esprit.spring.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Article;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.Response;
import tn.esprit.spring.entity.Subject;
import tn.esprit.spring.entity.User;

@Repository
public interface ResponseRepository  extends CrudRepository<Response,Integer> {
	
	@Query("SELECT r FROM Response r WHERE r.article = ?1 " )
	List<Response> findAllByArticle(Article article);


	@Query("SELECT r FROM Response r ORDER BY r.Nlikes DESC " )
	List<Response> findByNlikes();

	//@Query("SELECT r FROM Response r ORDER BY r.publishDate DESC " )
	//List<Response> sortByDate();

	List<Response> findAllByOrderByPublishDateDesc() ;
	
	
	



}
