package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Article;
import tn.esprit.spring.entity.Subject;
import tn.esprit.spring.entity.User;

@Repository
public interface ArticleRepository  extends CrudRepository<Article,Integer> {
	
	@Query("SELECT a FROM Article a WHERE a.subject = ?1 " )
	List<Article> findAllBySubject(Subject subject);

}
