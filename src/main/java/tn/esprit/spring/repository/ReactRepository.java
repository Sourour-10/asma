package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.Response;

import java.util.List;

import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.User;

@Repository
public interface ReactRepository extends CrudRepository<React, Integer> {
	@Query("SELECT r FROM React r WHERE r.ResponseLike.responseId = :response and r.UserLike.userId = :user")
	React findReactByResponseLikeAndUserLike(@Param("response") int responseId, @Param("user") int userId);

	@Query("SELECT r FROM React r WHERE r.ResponseLike= ?1")
	public List<React> findResponseReacts(Response response);

	
	@Query("SELECT r FROM React r WHERE r.PostLike= :post")
	public List<React> findReactByPostId(@Param("post") Post Post);

	@Query("SELECT r FROM React r WHERE r.UserLike= :user")
	public List<React> findReactByUserId(@Param("user") User User);

}
